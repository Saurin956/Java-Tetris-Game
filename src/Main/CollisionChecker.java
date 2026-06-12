package Main;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import Entity.Entity;
import Object.OBJ_I;

public class CollisionChecker {

	GamePanel gp;
	PlayManager pm;
	Graphics2D g2;

	
	public boolean leftCollision,rightCollision,bottomCollision;
	
	public Rectangle debugLeftRect = null;
	public Rectangle debugRightRect = null;


	
	public CollisionChecker(GamePanel gp,PlayManager pm) {
		this.gp=gp;
		this.pm=pm;

	}
	
    public void checkMovementCollision(Entity block) {
    	
    	leftCollision = false;
    	rightCollision=false;
    	bottomCollision=false;
    	
    	 for (int row = 0; row < block.shape.length; row++) {
    	        for (int col = 0; col < block.shape[row].length; col++) {
    	            if (block.shape[row][col] == 1) {
    	                int tileX = block.worldX + col * pm.space + PlayManager.OFFSET_X;
    	                int tileY = block.worldY + row * (pm.space/2) + PlayManager.OFFSET_Y;

    	
    	//Check Frame Collision
    	//Left Wall
    	if(block instanceof OBJ_I && (block.direction == 2||block.direction==4)) {
    		tileY+=30;
    	  }
    	else if(block instanceof OBJ_I) {
    		tileY+=45;
    	}
   
    	if(tileX -15  <= PlayManager.left_x) {
    		leftCollision = true;
    	}
    	if(tileX + Entity.SIZE >= PlayManager.right_x) {
    		rightCollision = true;
    	}

    	if(tileY + Entity.SIZE - 15 >= PlayManager.bottom_y) {//for space=Entity.SIZE*1, -45 instead of -15
    		bottomCollision = true;
    	}
   	}
    }
   }
  }
    

    public boolean checkBottomCollision(Entity block) {
        int[][] shape = block.shape;
        
        for (int col = 0; col < shape[0].length; col++) {
            for (int row = shape.length - 1; row >= 0; row--) {
                if (shape[row][col] == 1) {
                    // Pixel position of this tile
                    int tileX = block.worldX + col * pm.space + PlayManager.OFFSET_X;
                    int tileY = block.worldY + row * pm.space + PlayManager.OFFSET_Y;
         
                	if(block instanceof OBJ_I) {
                		tileY+=30;
                	}
                	
                    // Rectangle just below this tile (1 pixel tall)
                    Rectangle checkRect = new Rectangle(tileX, tileY+30, pm.space/2,1);
                    // Check against all locked blocks
                    for (LockedBlock lb : pm.lockedBlocks) {
                        if (checkRect.intersects(lb.solidArea)) {
                            return true;
                        }
                    }

                    break; // Only check bottom-most '1' in this column
                }
            }
        }

        return false;
    }



    public boolean checkLeftCollision(Entity block) {
        int[][] shape = block.shape;
        debugLeftRect = null;

        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[0].length; col++) {
                if (shape[row][col] == 1) {
                    // Pixel position of this tile
                    int tileX = block.worldX + col * pm.space + PlayManager.OFFSET_X;
                    int tileY = block.worldY + row * pm.space ;

                    // Optional offset adjustment for specific blocks (like OBJ_I)
                    if(block instanceof OBJ_I) {//only when space is *2
                		tileY+=30;
                	}
                    
                    // Rectangle just to the left of this tile
                    Rectangle checkRect = new Rectangle(tileX-Entity.SIZE , tileY-5, 1, pm.space/2);
                    debugLeftRect = checkRect;
                    
                    for (LockedBlock lb : pm.lockedBlocks) {
                        if (checkRect.intersects(lb.solidArea)) {
                            return true;
                        }
                    }

                    break; // Only check leftmost tile in this row
                }
            }
        }

        return false;
    }

    


    public boolean checkRightCollision(Entity block) {
        int[][] shape = block.shape;
        debugRightRect = null;

        for (int row = 0; row < shape.length; row++) {
            for (int col = shape[0].length - 1; col >= 0; col--) {
                if (shape[row][col] == 1) {
                    // Pixel position of this tile
                    int tileX = block.worldX + col * pm.space + PlayManager.OFFSET_X;
                    int tileY = block.worldY + row * pm.space ;

                    // Rectangle just to the right of this tile
                    Rectangle checkRect = new Rectangle(tileX+pm.space, tileY, 1, pm.space/2);//for space=Entity.SIZE*1,it is tileX-Entity.SIZE
                    debugRightRect = checkRect;
                    
                    for (LockedBlock lb : pm.lockedBlocks) {
                        if (checkRect.intersects(lb.solidArea)) {
                            return true;
                        }
                    }

                    break; // Only check rightmost tile in this row
                }
            }
        }

        return false;
    }

}
