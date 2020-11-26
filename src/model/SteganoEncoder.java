package model;

import java.awt.image.BufferedImage;
import utils.BufferedImageUtil;


/**
* SteganoEncoder contient une méthode permettant d'encoder un message sous forme
* de stegano dans une image.
* <p>Elle contient également des méthodes privées permettant de convertir un String en boolean[] et d'écrire
* un boolean[] dans une image.
*
* @author  Nicolas Gerard
* @version 0.1
* @since   19/11/2020
*/
public class SteganoEncoder {
	
	private BufferedImage bufferedImage;
	/**
	* Constructeur permettant d'instancier un SteganoEncoder.
	* @param bufferedImage L'image utilisée pour les différents traitements.
	*/
	public SteganoEncoder(BufferedImage bufferedImage) {
		this.bufferedImage=bufferedImage;
	}
	
	
	/**
	* Encode un message stegano dans la copie d'une image.
	* @param bufferedImage L'image dont une copie sera encodée.
	* @param msg le message à encoder.
	* @return Copie encodée de la bufferedImage donnée en argument.
	*/
	public BufferedImage encode(String msg) {
		String message = iSteganoConstante.encodedtag + msg.length() + iSteganoConstante.separator + msg; 
		
		System.out.println(message);
		
		boolean[] bits = fromStringToBitSet(message);	
		BufferedImage copiedImage = BufferedImageUtil.deepCopy(bufferedImage);
		
		return writeBitSetIntoBufferedImage(copiedImage, bits);
	}
	
	/**
	* Renvoie un boolean[] contenant le message sous forme binaire.
	* @param message  le message à convertir.
	* @return Un boolean[] créé sur base du String.
	*/
	private boolean[] fromStringToBitSet(String message) {
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
	* Encode un boolean[] sur le LSB de l'alphachannel des pixels d'une BufferedImage.
	* @param outputImage Image encodée.
	* @param bits boolean[] à écrire.
	* @return Copie encodée de la bufferedImage donnée en argument.
	*/
	private BufferedImage writeBitSetIntoBufferedImage(BufferedImage outputImage, boolean[] bits) {

		int pixel, pixOut, count = 0;;
		loop: for(int i = 0; i < outputImage.getWidth(); i++) {
			for(int j = 0; j < outputImage.getHeight(); j++) {
				if(count < bits.length) {
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
	
	/**
	* Calcul la limite de caractère stockable dans l'image.
	* @param message supposé pour calculer l'espace restant
	* @return Le nombre maximum de caractère stockable dans l'image.
	*/
	public int getMaxNumberOfChar(String msg) {
		int headerLength = (iSteganoConstante.encodedtag + msg.length() + iSteganoConstante.separator).length();
		return ((bufferedImage.getWidth()*bufferedImage.getHeight())/8)-headerLength*8;
	}
}
