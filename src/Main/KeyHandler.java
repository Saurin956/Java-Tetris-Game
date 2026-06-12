package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	
	
	GamePanel gp;
	public static boolean upPressed,downPressed,leftPressed,rightPressed,enterPressed;
	
	//DEBUG
	public boolean showDebugText = false;
	public boolean godModeOn = false;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		
		if(gp.gameState == gp.titleState) {
			titleState(code);
		}
		
		else if(gp.gameState == gp.playState)
		{
			playState(code);
		
        }
		else if(gp.gameState == gp.optionState)
		{
			optionState(code);
		
        }
		else if(gp.gameState == gp.gameOverState)
		{
			gameOverState(code);
		
        }
		
	}
	
	public void titleState(int code) {
		
            if(gp.ui.titleScreenState == 0) {
			
			if(code == KeyEvent.VK_W) 
			{
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
			}
			if(code == KeyEvent.VK_S)
			{
               gp.ui.commandNum++;
               if(gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
			}
			if(code == KeyEvent.VK_ENTER ) {
				if(gp.ui.commandNum == 0) {
				    
					gp.setDefault();
					gp.gameState=gp.playState;
					gp.playMusic(0);
				}
				if(gp.ui.commandNum == 1) {
					
					gp.saveBlock.loadDat();
					gp.pm.lockedBlocks = gp.saveLoad.load(gp.pm.loadTiles); // ✅ load and assign
			
					gp.gameState = gp.playState;
					gp.playMusic(0);
				}
			}
        }
	}
	
public void playState(int code) {
		
		
		if(code == KeyEvent.VK_W) //this means if user press the W key
		{
			upPressed=true;
		}
		
		if(code == KeyEvent.VK_S)
		{
			downPressed=true;
		}
		
		if(code == KeyEvent.VK_A)
		{
			leftPressed=true;
		}
		
		if(code == KeyEvent.VK_D)
		{
			rightPressed=true;
		}

	    if(code == KeyEvent.VK_ENTER)
		{
		enterPressed = true;
		}

	    if(code == KeyEvent.VK_ESCAPE)
		{
	    gp.stopMusic();
	    gp.gameState = gp.optionState;
	    System.out.println("GameState="+gp.optionState);
		}


	    if(code == KeyEvent.VK_SPACE)
		{
//	    spacePressed = true;
		}
}

public void optionState(int code) {
	
	if(code == KeyEvent.VK_ESCAPE) {
		gp.playMusic(0);
		gp.gameState=gp.playState;
	}
	if(code == KeyEvent.VK_ENTER) {
		enterPressed = true;
	}
	
	if(code == KeyEvent.VK_W) 
	{
		gp.ui.commandNum--;
		if(gp.ui.commandNum < 0) {
			gp.ui.commandNum = 4;
		}
	}
	if(code == KeyEvent.VK_S)
	{
       gp.ui.commandNum++;
       if(gp.ui.commandNum > 4) {
			gp.ui.commandNum = 0;
		}
	}
	
	 if(code == KeyEvent.VK_A) {
     	if(gp.ui.subState == 0) {
     		if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
     			gp.music.volumeScale--;
     			gp.music.checkVolume();//this is for changing the volume when music is already being played so not needed in SE
     			gp.playSE(9);
     			System.out.println("Current Volume: " + gp.music.volume);

     		}
     		if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
     			gp.se.volumeScale--;
     			gp.playSE(9);
     			System.out.println("Current Volume: " + gp.music.volume);

     		}
     	}	
     }
     if(code == KeyEvent.VK_D) {
     	if(gp.ui.subState == 0) {
     		if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
     			gp.music.volumeScale++;
     			gp.music.checkVolume();
     			gp.playSE(9);
     			System.out.println("Current Volume: " + gp.music.volume);
     			
     		}
     		if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
     			gp.se.volumeScale++;
     			gp.playSE(9);
     			System.out.println("Current Volume: " + gp.music.volume);
     		}
     	}
     }
	

}

public void gameOverState(int code) {
	
	

	if(code == KeyEvent.VK_ENTER) {
		
		gp.stopMusic();
		gp.gameState=gp.titleState;
		if (gp.pm.score > gp.ui.max) {
		    gp.ui.max = gp.pm.score;
		    gp.config.saveConfig(); // save new high score
		}
		
	}
}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
