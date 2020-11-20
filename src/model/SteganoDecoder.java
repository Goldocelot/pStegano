package model;

import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
* SteganoDecoder contient une m�thode permettant de decoder un message sous forme
* de stegano dans une image.
* <p>Elle contient �galement des m�thodes priv�es permettant de convertir un boolean[] en String ou en char, de lire
* un boolean[] dans une image, de v�rifier si l'image a �t� encod�e et de r�cup�rer la taille du message.
*
* @author  Nicolas Gerard
* @version 0.1
* @since   19/11/2020
*/
public class SteganoDecoder {

	private BufferedImage bufferedImage;
	
	private int positionX;
	private int positionY;
	
	/**
	* Constructeur permettant d'instancier un SteganoDecoder.
	* @param bufferedImage L'image utilis�e pour les diff�rents traitements.
	*/
	public SteganoDecoder(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
	
	/**
	* Decode un message stegano dans une image.
	* @return Le message que contenait l'image.
	*/
	public String decode() {
		positionX = 0;
		positionY = 0;
		
		if(!isEncoded()) return null;
		
		int messageLength = getEncodedLength();
		
		return decodeString(readBits(messageLength*8));
	}
	
	/**
	* V�rifie si l'image est bien encod�e.
	* @return true: l'image contient un message<br>false: l'image ne contient pas de message.
	*/
	private boolean isEncoded() {

		boolean[] bits = readBits(iSteganoConstante.encodedtag.length()*8);
		
		return decodeString(bits).equals(iSteganoConstante.encodedtag);
	}
	
	/**
	* Lit et renvoie la taille du message encod�e dans l'image (permet de ne pas consid�rer le s�parator dans l'image).
	* @return true: l'image contient un message.<br>false: l'image ne contient pas de message.
	*/
	private int getEncodedLength() {
		int index = 0;
		String lengthStr = "";

		do {
			lengthStr+=decodeOneChar(readBits(8));
		}while(lengthStr.charAt(index++) != iSteganoConstante.separator);
		
		
		return Integer.valueOf(lengthStr.substring(0, lengthStr.length()-1));
	}
	
	/**
	* Convertis un boolean[] en un String.
	* @param stringBits boolean[] contenant un String sous forme binaire.
	* @return La cha�ne de caract�res convertie.
	*/
	private String decodeString(boolean[] stringBits) {
		String decodedStr = "";
		
		for(int i = 0 ; i<stringBits.length ; i=i+8) {
			decodedStr+= decodeOneChar(Arrays.copyOfRange(stringBits, i,i+8));
		}
		
		return decodedStr;
	}
	
	/**
	* Convertis un boolean[] en un caract�re.
	* @param charBits boolean[] contenant un caract�re sous forme binaire.
	* @return Le caract�re convertit.
	*/
	private char decodeOneChar(boolean[] charBits) {
		byte oneCharByte=0;
		for(int i = 0 ; i<8 ; i++) {
			oneCharByte|= (charBits[i] ? 1 : 0)<< 7-i;
		}
		return (char) oneCharByte;
	}
	
	/**
	* Lit un certain nombre de bits dans les pixels de l'image.
	* @param numberOfBits nombre de bit � lire.
	* @return un boolean[] contenant les bits lus.
	*/
	private boolean[] readBits(int numberOfBits) {
		boolean[] bits = new boolean[numberOfBits];
		int i,j = 0, index = 0;
		
		loop: for(i = positionX; i < bufferedImage.getWidth(); i++) {
			for(j = positionY; j < bufferedImage.getHeight(); j++) {
				int pixel = bufferedImage.getRGB(i, j);		
				bits[index++] = (pixel & 0b1)==1;
				
				if(index == numberOfBits) {
					break loop;
				}

			}
			positionY=0;
		}
		
		positionX=i;
		positionY=j+1;
	
		return bits;	
	}
	
}
