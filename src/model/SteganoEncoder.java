package model;

import java.awt.image.BufferedImage;
import java.util.BitSet;

import utils.BufferedImageUtil;


/**
* SteganoEncoder contient une méthode static permettant d'encoder un message sous forme
* de stegano dans une image.
* <p>Elle contient également des méthodes privées permettant de convertir un String en BitSet et d'écrire
* un BitSet dans une image.
*
* @author  Nicolas Gerard
* @version 0.1
* @since   19/11/2020
*/
public class SteganoEncoder implements iSteganoConstante{
	
	/**
	* Encode un message stegano dans la copie d'une image.
	* @param bufferedImage L'image dont une copie sera encodée.
	* @param msg le message à encoder
	* @return Copie encodée de la bufferedImage donnée en argument
	*/
	public static BufferedImage encode(BufferedImage bufferedImage, String msg) {
		String message = iSteganoConstante.encodedtag + msg.length() + iSteganoConstante.separator + msg; 
		
		BitSet bits = fromStringToBitSet(message);	
		BufferedImage copiedImage = BufferedImageUtil.deepCopy(bufferedImage);
		
		return writeBitSetIntoBufferedImage(copiedImage, bits);
	}
	
	/**
	* Renvoie un BitSet contenant le message sous forme binaire.
	* @param message  le message à convertir
	* @return Un BitSet créé sur base du String
	*/
	private static BitSet fromStringToBitSet(String message) {
		BitSet bits = new BitSet(message.length()*8);
		
		for(int i=0 ; i<message.length() ; i++) {
			char c = message.charAt(i);
			for(int j = 0 ; j < 8 ; j++) {
				int index = 8*i+j;
				boolean bit = ((c >> 7) & 0b1) == 1;
				bits.set(index, bit);
			}
		}
		
		return bits;
	}
	
	/**
	* Encode un bitSet sur le LSB de l'alphachannel des pixels d'une BufferedImage
	* @param outputImage Image encodée
	* @param bits BitSet a écrire
	* @return Copie encodée de la bufferedImage donnée en argument
	*/
	private static BufferedImage writeBitSetIntoBufferedImage(BufferedImage outputImage, BitSet bits) {

		int pixel, pixOut, count = 0;;
		loop: for(int i = 0; i < outputImage.getWidth(); i++) {
			for(int j = 0; j < outputImage.getHeight(); j++) {
				if(count < bits.size()) {
					pixel = outputImage.getRGB(i, j);
					pixOut = (pixel & 0xFFFFFFFE) | (bits.get(count++) ? 1 : 0);
					
					outputImage.setRGB(i, j, pixOut);
					
				} else {
					break loop;
				}
			}
			
		}
		
		return outputImage;
	}
}
