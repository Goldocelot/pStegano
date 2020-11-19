package model;

import java.awt.image.BufferedImage;
import utils.BufferedImageUtil;


/**
* SteganoEncoder contient une m�thode static permettant d'encoder un message sous forme
* de stegano dans une image.
* <p>Elle contient �galement des m�thodes priv�es permettant de convertir un String en BitSet et d'�crire
* un BitSet dans une image.
*
* @author  Nicolas Gerard
* @version 0.1
* @since   19/11/2020
*/
public class SteganoEncoder {
	
	/**
	* Encode un message stegano dans la copie d'une image.
	* @param bufferedImage L'image dont une copie sera encod�e.
	* @param msg le message � encoder
	* @return Copie encod�e de la bufferedImage donn�e en argument
	*/
	public static BufferedImage encode(BufferedImage bufferedImage, String msg) {
		String message = iSteganoConstante.encodedtag + msg.length() + iSteganoConstante.separator + msg; 
		System.out.println(message);
		
		boolean[] bits = fromStringToBitSet(message);	
		BufferedImage copiedImage = BufferedImageUtil.deepCopy(bufferedImage);
		
		return writeBitSetIntoBufferedImage(copiedImage, bits);
	}
	
	/**
	* Renvoie un boolean[] contenant le message sous forme binaire.
	* @param message  le message � convertir
	* @return Un boolean[] cr�� sur base du String
	*/
	private static boolean[] fromStringToBitSet(String message) {
		boolean[] bits = new boolean[message.length()*8];
		
		for(int i=0 ; i<message.length() ; i++) {
			char c = message.charAt(i);
			for(int j = 0 ; j < 8 ; j++) {
				bits[8*i+j] = ((c >> 7-j) & 0b1) == 1;
			}
		}
		return bits;
	}
	
	/**
	* Encode un boolean[] sur le LSB de l'alphachannel des pixels d'une BufferedImage
	* @param outputImage Image encod�e
	* @param bits boolean[] a �crire
	* @return Copie encod�e de la bufferedImage donn�e en argument
	*/
	private static BufferedImage writeBitSetIntoBufferedImage(BufferedImage outputImage, boolean[] bits) {

		int pixel, pixOut, count = 0;;
		loop: for(int i = 0; i < outputImage.getWidth(); i++) {
			for(int j = 0; j < outputImage.getHeight(); j++) {
				if(count < bits.length) {
					System.out.println("encoder: "+i+":"+j);
					pixel = outputImage.getRGB(i, j);
					pixOut = (pixel & 0xFFFFFFFE) | (bits[count++] ? 1 : 0);
					
					outputImage.setRGB(i, j, pixOut);
					
				} else {
					break loop;
				}
			}
			
		}
		
		return outputImage;
	}
}
