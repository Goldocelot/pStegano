package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
* Serialisation contient des méthodes permettant de charger et d'enregister des images
* @author  Nicolas Gerard
* @version 0.1
* @since   19/11/2020
*/
public class Serialisation {
	
	/**
	* Charge une image
	* @param fileName nom du fichier d'où provient l'image
	* @return BufferedImage de l'image chargée (null si aucune image trouvée)
	*/
	public BufferedImage loadImageFromFile(String fileName) {
		try {
			return ImageIO.read(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	* Sauvegarde une image
	* @param image image à sauvegarder
	* @param fileName nom du fichier dans lequel sauvegarder l'image
	* @return true = sauvegarde réussie<br>false = échec de la sauvegarde
	*/
	public boolean saveImageInFile(BufferedImage image, String fileName) {
		try {
			return ImageIO.write(image, "png", new File(fileName));	 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
