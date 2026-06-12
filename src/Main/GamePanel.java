package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Data.SaveLoad;
import Data.SaveBlock;
import Entity.Entity;
import Object.OBJ_I;
import Object.OBJ_L;
import Object.OBJ_R;
import Object.OBJ_S;
import Object.OBJ_T;

public class GamePanel extends JPanel implements Runnable {
	

	//Screen Settings
	final int originalTileSize = 5;//5*5
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale;//15*15
	public final double maxScreenCol = 38.4;
	public final double maxScreenRow=48;
	
	public final int screenWidth = (int)(tileSize*maxScreenCol);//576
	public final int screenHeight = (int)(tileSize*maxScreenRow);//720
	
	//Entity and Object
	public OBJ_L l=new OBJ_L(this);
	public OBJ_I i=new OBJ_I(this);
	public OBJ_R r=new OBJ_R(this);
	public OBJ_S s=new OBJ_S(this);
	public OBJ_T t=new OBJ_T(this);
	
	//SYSTEM
	SaveBlock saveBlock = new SaveBlock(this);
	public Config config = new Config(this);
	public CollisionChecker cChecker = new CollisionChecker(this,this.pm);
	public KeyHandler keyH = new KeyHandler(this);
	public Entity en = new Entity(this);
	public PlayManager pm = new PlayManager(this,this.en);
	
	public UI ui = new UI(this,this.pm);
	SaveLoad saveLoad = new SaveLoad(this,this.pm);
	Sound music = new Sound();
	Sound se=new Sound();
	
	Thread gameThread;
	
	//GAME STATE
	public int gameState;
	public final int titleState=0;
	public final int playState=1;
	public final int optionState=2;
	public final int gameOverState = 3;
	
	
	//FPS
	int FPS = 120;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setLayout(null);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		gameState=titleState;
	}

	public void startGameThread() {
		
		config.loadConfig();

		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		
		double drawInterval = 1000000000/FPS;
	
		double delta = 0;
		long lastTime=System.nanoTime();
		long timer = 0;
		int drawCount = 0;
		long currentTime;
		
		while(gameThread != null) {
			
			currentTime=System.nanoTime();
			delta +=(currentTime - lastTime) / drawInterval;
			timer += (currentTime- lastTime);
			lastTime= currentTime;
			
			if(delta >= 1) {
				
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS:" + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}
	
	 public void update() {
		 
		 if(gameState == playState) {
		 pm.update();
		 }
		
	 }
	 
	 public void playMusic(int i) {
			
			music.setFile(i);
			music.play();
			music.loop();
		}
		
		public void stopMusic() {
			
			music.stop();
		}
		
		public void playSE(int i) {//SE is sound effect
			
			se.setFile(i);
			se.play();
		}
		
		  public void setDefault() {
		    	
			  pm.currentBlock = pm.pickBlock();
			  pm.nextBlock = pm.pickBlock();
			  pm.currentBlock.setXY(pm.START_X, pm.START_Y);
      	    	pm.score=0;
      	    	if(pm.lockedBlocks.isEmpty()) {
      	    		
      	    	}
      	    	else {
      	    		pm.lockedBlocks.clear();//anaother way pm.lockedBlocks.removeAll(pm.lockedBlocks);
      	    	}    	
		    }

	 
	 public void paintComponent(Graphics g) {
		 

		 super.paintComponent(g);
		 
		 Graphics2D g2=(Graphics2D) g;
		 if(ui != null) {
			 ui.draw(g2);
		 }
		 if(gameState == playState ) {
		

			 pm.draw(g2);
			 pm.nextBlock(g2);
			 
	     }
		 if(gameState == optionState) {		 

			 pm.nextBlock(g2);
			 pm.draw(g2);
			 ui.drawOptionScreen();
	
		 }
		 
		 if(gameState == gameOverState) {		 

			 pm.nextBlock(g2);
			 pm.draw(g2);
			 ui.drawGameOverScreen();
	
		 }
	
	 }
}

