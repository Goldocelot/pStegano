package model;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class SteganoDecoder {
	
	private static boolean isEncoded(BufferedImage bufferedImage) {

		boolean[] bits = readBits(bufferedImage, 0, iSteganoConstante.encodedtag.length()*8-1);
		
		return decodeString(bits).equals(iSteganoConstante.encodedtag);
	}
	
	private static int getEncodedLength(BufferedImage bufferedImage) {
		int index = 0;
		String lengthStr = "";
		
		int afterEncodeTag = iSteganoConstante.encodedtag.length()*8;		

		do {
			lengthStr+=decodeOneChar(readBits(bufferedImage, afterEncodeTag+index*8,afterEncodeTag+7+index*8));
		}while(lengthStr.charAt(index++) != iSteganoConstante.separator);
		
		
		return Integer.valueOf(lengthStr.substring(0, lengthStr.length()-1));
	}
	
	public static String decode(BufferedImage bufferedImage) {
		System.out.println("before Encod verif");
		if(!isEncoded(bufferedImage)) return null;
		
		System.out.println("brefore message Length");
		int messageLength = getEncodedLength(bufferedImage);
		
		System.out.println("before readin: "+messageLength);
		int index = (iSteganoConstante.encodedtag.length()+messageLength/10+2)*8;
		
		return decodeString(readBits(bufferedImage, index, index-1+messageLength*8));
	}
	
	private static String decodeString(boolean[] stringBits) {
		String decodedStr = "";
		
		for(int i = 0 ; i<stringBits.length ; i=i+8) {
			decodedStr+= decodeOneChar(Arrays.copyOfRange(stringBits, i,i+8));
		}
		
		return decodedStr;
	}
	
	private static char decodeOneChar(boolean[] charBits) {
		byte oneCharByte=0;
		for(int i = 0 ; i<charBits.length ; i++) {
			oneCharByte|= (charBits[i] ? 1 : 0)<< 7-i;
		}
		return (char) oneCharByte;
	}
	
	private static boolean[] readBits(BufferedImage bufferedImage, int startIndex, int endIndex) {
		int index = startIndex;
		int[] pos = getStartPositionPixel(bufferedImage, startIndex);
		boolean[] bits = new boolean[endIndex+1-startIndex];
		
		loop: for(int i = pos[1]; i < bufferedImage.getWidth(); i++) {
			for(int j = pos[0]; j < bufferedImage.getHeight(); j++, index++) {
				
				int pixel = bufferedImage.getRGB(i, j);		
				bits[index-startIndex] = (pixel & 0b1)==1;
				
				
				if(index == endIndex) {
					break loop;
				}

			}			
		}
		
		return bits;
		
	}
	
	private static int[] getStartPositionPixel(BufferedImage bufferedImage, int startIndex){
		int[] pos = new int[2];
		pos[1] = startIndex/bufferedImage.getHeight();
		pos[0] = startIndex - (pos[1]*bufferedImage.getHeight());
		
		System.out.println(pos[0]+":"+pos[1]);
		
		return pos;
	}
}
