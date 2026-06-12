package Main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import Entity.Entity;
import Object.OBJ_I;
import Object.OBJ_L;
import Object.OBJ_R;
import Object.OBJ_S;
import Object.OBJ_T;

public class PlayManager {
	
	Graphics2D g2;
	GamePanel gp;
	Entity en;
	//Main Play Area
	final int WIDTH=420;
	final int HEIGHT=630;
	public static int left_x;
	public static int right_x;
	public static int top_y;
	public static int bottom_y;
	public int space=(int)(Entity.SIZE*2);
	
	//to use en we need to do this.en=currentBlock;
	public int score=0;
	
//	public static final int OFFSET_X = 19;
//	public static final int OFFSET_Y = -10;
	
	public static final int OFFSET_X = 19;
	public static final int OFFSET_Y = -10;
	
	public UI ui = new UI(this.gp,this);

	int temp=0;
	
	public ArrayList<LockedBlock> lockedBlocks = new ArrayList<>();
	public BufferedImage[] loadTiles = new BufferedImage[7];
	
	public ArrayList<Integer> fullRowsToClear = new ArrayList<>();
	private int clearDelayCounter = 0;
	private final int clearDelayFrames = 30; // e.g., 0.5 seconds at 60 FPS

	
	CollisionChecker cChecker;
	
	//Blocks
	public Entity currentBlock;
	public Entity nextBlock;
	public int START_X;
	public int START_Y;
	
	int autoDropCounter=0;


	
	
	//OTHERS
	public static int dropInterval = 60;
	
	public PlayManager(GamePanel gp,Entity en) {
		
	
        this.gp=gp;
        this.en=en;
        cChecker = new CollisionChecker(this.gp,this);
		
		left_x = (GamePanel.WIDTH);
		right_x = left_x + WIDTH-15;
		top_y=50;
		bottom_y=top_y + HEIGHT-30;
		START_X= left_x  - Entity.SIZE+190;
		START_Y= top_y + Entity.SIZE-60;
		currentBlock =pickBlock();
		nextBlock = pickBlock(); // Add this

		loadTiles[0] = en.setup("/Asset/Blue", gp.tileSize, gp.tileSize);     
		loadTiles[1] = en.setup("/Asset/Red", gp.tileSize, gp.tileSize);      
		loadTiles[2] = en.setup("/Asset/Purple", gp.tileSize, gp.tileSize);    
		loadTiles[3] = en.setup("/Asset/Green", gp.tileSize, gp.tileSize);
		loadTiles[4] = en.setup("/Asset/Yellow", gp.tileSize, gp.tileSize);


		
		currentBlock.setXY(START_X, START_Y);
		
	}
	
	public Entity pickBlock() {
		
		Entity Block = null;
		int i= new Random().nextInt(5);
		
		switch(i) {
		case 0: Block = new OBJ_L(gp);break;
		case 1: Block = new OBJ_I(gp);break;
		case 2: Block = new OBJ_R(gp);break;
		case 3: Block = new OBJ_S(gp);break;
		case 4: Block = new OBJ_T(gp);break;
		
		}
//		System.out.println("Picked block index: " + i);
		return Block;
		
	}
	

	
	public void lockBlock() {
	    int[][] shape = currentBlock.shape;

	    for (int row = 0; row < shape.length; row++) {
	        for (int col = 0; col < shape[0].length; col++) {
	            if (shape[row][col] == 1) {
	                // Corrected: Apply OFFSET only once
	                int tileX = currentBlock.worldX + col *((int)(space)) + PlayManager.OFFSET_X;
	                int tileY = currentBlock.worldY + row * ((int)(space)) + PlayManager.OFFSET_Y;

	                // Snap to grid cleanly
	                int snappedX = PlayManager.left_x + ((tileX - PlayManager.left_x) / (int)(space)) * (int)(space)+4;
	                int snappedY = PlayManager.top_y + ((tileY - PlayManager.top_y) / (int)(space)) * (int)(space)+5;
//	                System.out.println("Snapped Y: " + snappedY + " → Row: " + ((snappedY+5) - top_y) / Entity.SIZE);
	                if(currentBlock instanceof OBJ_I && currentBlock.direction==2 && currentBlock.direction==4) {
	                	 lockedBlocks.add(new LockedBlock(snappedX, snappedY+30,currentBlock.colorCode,currentBlock.down1));
	                }
	                else if(currentBlock instanceof OBJ_I) {
	                	 lockedBlocks.add(new LockedBlock(snappedX, snappedY+30,currentBlock.colorCode,currentBlock.down1));
	                }
	               
	                else {
	                	
	                lockedBlocks.add(new LockedBlock(snappedX, snappedY,currentBlock.colorCode,currentBlock.down1));
	                }
	            }
	        }
	    }
	   
	    
	    score+=10;
	}

	public void nextBlock(Graphics2D g2) {
		if (nextBlock != null && nextBlock.shape != null) {
	        int[][] shape = nextBlock.shape;
	        for (int row = 0; row < shape.length; row++) {
	            for (int col = 0; col < shape[0].length; col++) {
	                if (shape[row][col] == 1) {
	                    int tileX = gp.tileSize*28 + (int)(col * Entity.SIZE*1.2);
	                    int tileY = gp.tileSize*17 + (int)(row * Entity.SIZE*1.2);
	                    if(nextBlock instanceof OBJ_R || nextBlock instanceof OBJ_L) {
	                    	g2.drawImage(nextBlock.down1, tileX+45, tileY+60, (int)(Entity.SIZE*1.2),(int) (Entity.SIZE*1.2), null);
	                    }
	                    else {
	                    g2.drawImage(nextBlock.down1, tileX+55, tileY+60, (int)(Entity.SIZE*1.2),(int) (Entity.SIZE*1.2), null);
	                    }
	                }
	            }
	        }
	    }
	}
	
	public void update() {
		
		autoDropCounter++;//counter increases every frame
    	if(autoDropCounter >= dropInterval) {
    	  //block goes down	
   		cChecker.checkMovementCollision(currentBlock);
    		if(!cChecker.bottomCollision && !cChecker.checkBottomCollision(currentBlock)) {
    		currentBlock.worldY+=space;
    		System.out.println(currentBlock.worldY);
 //   		System.out.println(currentBlock.worldX);
    		}
    		else {
    			lockBlock();
    			clearFullRows();
    			spawnNewPiece();
  //  			nextBlock(g2,gp.tileSize*28,gp.tileSize*17,gp.tileSize*10,gp.tileSize*10);

    			
    			  		}
    
    		autoDropCounter=0;
    	}
    	
    	
    	if (KeyHandler.upPressed) {

    	    // 1) Copy the current block (same subclass)
    	    Entity test = currentBlock.copyPiece();

    	    // 2) Rotate the TEST piece
    	    switch (test.direction) {
    	        case 1: test.getDirection2(); test.direction = 2; break;
    	        case 2: test.getDirection3(); test.direction = 3; break;
    	        case 3: test.getDirection4(); test.direction = 4; break;
    	        case 4: test.getDirection1(); test.direction = 1; break;
    	    }

    	    // 3) Make sure rotation created a temp shape
    	    if (test.temp == null) {
    	        KeyHandler.upPressed = false;
    	        return;
    	    }

    	    // 4) Use rotated shape for collision testing
    	    test.shape = test.temp;

    	    // 5) CRITICAL — update collision flags for ROTATED TEST
    	    cChecker.checkMovementCollision(test);

    	    // 6) Now validate rotation only using ROTATED test
    	    boolean safe =
    	        !cChecker.bottomCollision &&
    	        !cChecker.leftCollision &&
    	        !cChecker.rightCollision &&
    	        !cChecker.checkBottomCollision(test) &&
    	        !cChecker.checkLeftCollision(test) &&
    	        !cChecker.checkRightCollision(test);

    	    // 7) If safe → apply rotation to the real block
    	    if (safe) {

    	        currentBlock.direction = test.direction;

    	        currentBlock.shape = new int[test.shape.length][test.shape[0].length];
    	        for (int i = 0; i < test.shape.length; i++)
    	            for (int j = 0; j < test.shape[0].length; j++)
    	                currentBlock.shape[i][j] = test.shape[i][j];
    	    }

    	    KeyHandler.upPressed = false;
    	}

    	
        if(KeyHandler.downPressed ) {
        	
         	cChecker.checkMovementCollision(currentBlock);
        	
   
        	 if (!cChecker.bottomCollision  && !cChecker.checkBottomCollision(currentBlock)){
        	        // Revert the move and lock
        	        currentBlock.worldY += space;
        	 }
        	 else {
        	        lockBlock();
        	        clearFullRows();
        	        spawnNewPiece();
        	    }
        	
        	autoDropCounter=0;
        	
        	KeyHandler.downPressed=false;
    	}

        else if(KeyHandler.leftPressed) {
        	
        	cChecker.checkMovementCollision(currentBlock);
        	
        	if (!cChecker.leftCollision && !cChecker.checkLeftCollision(currentBlock)) {
        	
            currentBlock.worldX-=space;
        	}
        	
        	autoDropCounter=0;
        	
        	KeyHandler.leftPressed=false;
        }
        
        if(KeyHandler.rightPressed) {
        	
        	cChecker.checkMovementCollision(currentBlock);
        	
        	if (!cChecker.rightCollision && !cChecker.checkRightCollision(currentBlock) ) {
        	
        	currentBlock.worldX+=space;
        	}
         	
         	autoDropCounter=0;
         	
         	KeyHandler.rightPressed=false;
    		
    	}
        
     // Add delay before clearing the row
        if (!fullRowsToClear.isEmpty()) {
            clearDelayCounter++;
            if (clearDelayCounter >= clearDelayFrames) { // do the real removal
                fullRowsToClear.clear();
                clearDelayCounter = 0;
            }
        }

    	
	}
	


	

	public void spawnNewPiece() {
		
		currentBlock = nextBlock;//in this the nextBlock is already initialized outside the method 
		nextBlock=pickBlock();
		currentBlock.worldX=START_X;
		currentBlock.worldY=START_Y;
		

	}	
	

	
	public void clearFullRows() {
	    int cols = (WIDTH / (space)); // typically 14
	    int rows =( HEIGHT / (space))+1;

	    // Track how many blocks are present per row
	    int[] blockCountPerRow = new int[rows];

	    // Count blocks per y-row
	    for (LockedBlock lb : lockedBlocks) {
//	    	System.out.println("lb.y="+lb.y);
	        int row = ((lb.y - top_y) / space);//for space=15,(lb.y-top_y+PlayManager.OFFSET_Y)

//	        System.out.println("LockedBlock Y: " + lb.y + " → Row: " + (lb.y - top_y) / space);
//	        System.out.println(row);
	        if (row >= 0 && row < rows) {
	            blockCountPerRow[row]++;
	        }
	    }
	    

	    // Check for full rows
	    for (int row = 0; row < rows; row++) {
//	    	System.out.println("Row " + row + ": " + blockCountPerRow[row] + " blocks");
	    	if(row==0 && blockCountPerRow[row] > 0) {
	    		
	    		gp.stopMusic();
	    		gp.playMusic(12);
	    		gp.gameState=gp.gameOverState;
	    	}

	        if (blockCountPerRow[row] == cols) {
	        	temp=1;
	        	score+=100;
	            int yToClear = top_y + row * space+5;//for space=15,it is +20,if not 5 then 35 for space=30
//	            System.out.println("yToClear="+yToClear);
	            
	            // Remove all locked blocks in that row
	            lockedBlocks.removeIf(lb -> lb.y==yToClear);
	            
//	            System.out.println("Remaining blocks: " + lockedBlocks.size());


	            // Move all blocks above down by one tile
	            for (LockedBlock lb : lockedBlocks) {
	                if (lb.y < yToClear) {
	                    lb.y += space;
	                    lb.solidArea.y += space;
	                }
	            }
	        }
	    }
	    if(temp==0) {
	    	gp.playSE(2);
	    }
	    else if(temp==1) {
	    	temp=0;
	    	gp.playSE(11);
	    }
	}
	
	
	public void draw(Graphics2D g2) {

	    // Draw all locked blocks
	    for (LockedBlock block : lockedBlocks) {
	        g2.drawImage(block.image, block.x, block.y, space, space, null);
	    }


	    //For debug
//	    for (LockedBlock lb : lockedBlocks) {
//	        g2.setColor(Color.RED);
//	        g2.draw(lb.solidArea); // draw the collision box
//	    }
	    
	    
	    // Draw current block using shape[][] and tileImage
//	    if (currentBlock != null && currentBlock.shape != null) {
//	        int[][] shape = currentBlock.shape;
//	        for (int row = 0; row < shape.length; row++) {
//	            for (int col = 0; col < shape[0].length; col++) {
//	                if (shape[row][col] == 1) {
//	                    int tileX = currentBlock.worldX + col * Entity.SIZE+OFFSET_X-10;
//	                    int tileY = currentBlock.worldY + row * Entity.SIZE+OFFSET_Y;
//	                    g2.drawImage(currentBlock.down1, tileX, tileY, Entity.SIZE, Entity.SIZE, null);

//	                }
//	            }
//	        }
//	    }  
	}
}

