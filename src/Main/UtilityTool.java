package Main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UtilityTool {

public BufferedImage scaleImage(BufferedImage original, int width , int height) {
		
		//for scaling of tiles to reduce draw time
		//syntax BufferedImage(int width,height,imagetype)
		BufferedImage scaledImage = new BufferedImage(width,height,original.getType());
		Graphics2D g2 = scaledImage.createGraphics();//BufferedImage.createGraphics() creates a Graphics2D , which can be used to draw into this bufferedImage
		g2.drawImage(original, 0, 0,width,height,null);
		g2.dispose();
		
		return scaledImage;
	}
}
