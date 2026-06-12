package Main;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import Entity.Entity;

public class LockedBlock implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	public int x, y;
	public int colorCode;
	
	 // transient = will not be saved during serialization (BufferedImage is not Serializable)
    public transient BufferedImage image;
    
    public Rectangle solidArea;
    public int[][] shape;
    public int spacing=(Entity.SIZE)*2;

    public LockedBlock(int x, int y, int colorCode, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.colorCode = colorCode;
        this.image = image;
        this.solidArea = new Rectangle(x, y, spacing, spacing);//for space=Entity.SIZE *1,it will be new Rectangle(x+2, y+2, Entity.SIZE-1, Entity.SIZE-1);
    }
    
 
}
    
   

