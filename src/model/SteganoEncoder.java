package model;

import java.awt.image.BufferedImage;
import utils.BufferedImageUtil;


/**
* SteganoEncoder contient une m�thode permettant d'encoder un message sous forme
* de stegano dans une image.
* <p>Elle contient �galement des m�thodes priv�es permettant de convertir un String en boolean[] et d'�crire
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
	* @param bufferedImage L'image utilis�e pour les diff�rents traitements.
	*/
	public SteganoEncoder(BufferedImage bufferedImage) {
		this.bufferedImage=bufferedImage;
	}
	
	
	/**
	* Encode un message stegano dans la copie d'une image.
	* @param bufferedImage L'image dont une copie sera encod�e.
	* @param msg le message � encoder.
	* @return Copie encod�e de la bufferedImage donn�e en argument.
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
	* @param message  le message � convertir.
	* @return Un boolean[] cr�� sur base du String.
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
	* @param outputImage Image encod�e.
	* @param bits boolean[] � �crire.
	* @return Copie encod�e de la bufferedImage donn�e en argument.
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
	* Calcul la limite de caract�re stockable dans l'image.
	* @param message suppos� pour calculer l'espace restant
	* @return Le nombre maximum de caract�re stockable dans l'image.
	*/
	public int getMaxNumberOfChar(String msg) {
		int headerLength = (iSteganoConstante.encodedtag + msg.length() + iSteganoConstante.separator).length();
		return ((bufferedImage.getWidth()*bufferedImage.getHeight())/8)-headerLength*8;
	}
}
