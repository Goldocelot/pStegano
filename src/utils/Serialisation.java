package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
* Serialisation contient des m�thodes permettant de charger et d'enregister des images
* @author  Nicolas Gerard
* @version 0.1
* @since   19/11/2020
*/
public class Serialisation {
	
	/**
	* Charge une image
	* @param fileName nom du fichier d'o� provient l'image
	* @return BufferedImage de l'image charg�e (null si aucune image trouv�e)
	*/
	public static BufferedImage loadImageFromFile(String fileName) {
		try {
			return ImageIO.read(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	* Sauvegarde une image
	* @param image image � sauvegarder
	* @param fileName nom du fichier dans lequel sauvegarder l'image
	* @return true = sauvegarde r�ussie<br>false = �chec de la sauvegarde
	*/
	public static boolean saveImageInFile(BufferedImage image, String fileName) {
		try {
			return ImageIO.write(image, "png", new File(fileName));	 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
