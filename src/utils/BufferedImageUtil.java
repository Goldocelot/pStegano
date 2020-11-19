package utils;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
* BufferedImageUtil contient une méthode permettant de copier une image
* @author  Nicolas Gerard
* @version 0.1
* @since   19/11/2020
*/
public class BufferedImageUtil {
	
	/**
	* Copie une image
	* @param bufferedImage image à copier
	* @return Copie de l'image
	*/
	public static BufferedImage deepCopy(BufferedImage bufferedImage) {
	    ColorModel cm = bufferedImage.getColorModel();
	    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
	    WritableRaster raster = bufferedImage.copyData(bufferedImage.getRaster().createCompatibleWritableRaster());
	    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
}
