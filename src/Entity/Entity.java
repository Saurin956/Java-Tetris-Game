package Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.PlayManager;
import Main.UtilityTool;

public class Entity {

	 GamePanel gp;
	

	public BufferedImage down1;
	
	public static final int SIZE=15;
	public int[][] shape;
	public int[][] temp;
	public int direction=1;
	public int colorCode;
	
	public int worldX;
	public int worldY;
		
	
	//CHARACTER ATTRIBUTES
	public String name;
	
	public Entity(GamePanel gp) {
		this.gp=gp;
	
	}
	
    public BufferedImage setup(String imagePath,int width,int height) {
	   	
	   	UtilityTool utool = new UtilityTool();
	   	BufferedImage scaledImage = null;
	   	
	   	try {
	   		scaledImage = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
	   		scaledImage = utool.scaleImage(scaledImage,width,height);
	   		
	   	}catch(IOException e) {
	   		e.printStackTrace();
	   	}
	   	return scaledImage;
    	
	}
    
 
    
    
    public void setXY(int x,int y) {
    	
    	this.worldX = x;
        this.worldY = y;
        
    }
    
    public void getDirection1() {}
    public void getDirection2() {}
    public void getDirection3() {}
    public void getDirection4() {}
    
    public void updateXY(int direction) {
    	
    	this.direction=direction;
    	shape=temp;
    }
    
    public void update() {
    	
 
    }
    
    public void draw(Graphics2D g2) {
    	
    }
    
    public int[][] rotateMatrixClockwise(int[][] m) {

        int rows = m.length;
        int cols = m[0].length;

        int[][] rotated = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = m[i][j];
            }
        }

        return rotated;
    }

    
    public Entity copyPiece() {
        try {
            Entity copy;

            // create same class instance
            copy = this.getClass().getDeclaredConstructor(GamePanel.class).newInstance(gp);

            // copy position
            copy.worldX = this.worldX;
            copy.worldY = this.worldY;
            copy.direction = this.direction;
            copy.colorCode = this.colorCode;

            // deep copy shape
            copy.shape = new int[this.shape.length][this.shape[0].length];
            for (int i = 0; i < shape.length; i++)
                for (int j = 0; j < shape[0].length; j++)
                    copy.shape[i][j] = shape[i][j];

            return copy;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
