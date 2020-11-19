package model;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class SteganoDecoderVar {

	private static int positionX;
	private static int positionY;
	
	private static boolean isEncoded(BufferedImage bufferedImage) {

		boolean[] bits = readBits(bufferedImage,iSteganoConstante.encodedtag.length()*8);
		
		return decodeString(bits).equals(iSteganoConstante.encodedtag);
	}
	
	private static int getEncodedLength(BufferedImage bufferedImage) {
		int index = 0;
		String lengthStr = "";

		do {
			lengthStr+=decodeOneChar(readBits(bufferedImage,8));
		}while(lengthStr.charAt(index++) != iSteganoConstante.separator);
		
		
		return Integer.valueOf(lengthStr.substring(0, lengthStr.length()-1));
	}
	
	public static String decode(BufferedImage bufferedImage) {
		positionX = 0;
		positionY = 0;
		
		System.out.println("before Encod verif");
		if(!isEncoded(bufferedImage)) return null;
		
		System.out.println("brefore message Length");
		int messageLength = getEncodedLength(bufferedImage);
		
		System.out.println("before readin: "+messageLength);
		
		return decodeString(readBits(bufferedImage,messageLength*8));
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
		System.out.println((char) oneCharByte);
		return (char) oneCharByte;
	}
	
	private static boolean[] readBits(BufferedImage bufferedImage, int numberOfBits) {
		int index=0;
		boolean[] bits = new boolean[numberOfBits];
		
		loop: for(int i = positionX; i < bufferedImage.getWidth(); i++) {
			for(int j = positionY; j < bufferedImage.getHeight(); j++) {
				
				System.out.println(i+":"+j);
				int pixel = bufferedImage.getRGB(i, j);		
				bits[index++] = (pixel & 0b1)==1;
				
				
				if(index == numberOfBits) {
					positionX=i;
					positionY=j;
					break loop;
				}

			}			
		}
		
		return bits;
		
	}
	
}
