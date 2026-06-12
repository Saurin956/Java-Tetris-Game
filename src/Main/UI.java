package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.InputStream;

import Entity.Entity;
import Object.OBJ_I;

public class UI {
	
	GamePanel gp;
	PlayManager pm;
	Graphics2D g2;
	public Font arial_40,purisaB;
	
	public int titleScreenState=0;
	public int subState=0;
	public int commandNum=0;
	int counter =0;
	public String value;
	public final int imX=104;
	public final int imY=75;
	public int max=0;
	public String value1;
	
	public UI(GamePanel gp,PlayManager pm) {
		this.gp=gp;
		this.pm=pm;
		
		
		arial_40 = new Font("Cambria",Font.PLAIN,30);
		
		try {
			InputStream is= getClass().getResourceAsStream("/Fonts/Purisa Bold.ttf");
			purisaB=Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void draw(Graphics2D g2) {
		
		this.g2=g2;
		
		g2.setFont(arial_40);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    g2.setColor(Color.white);
	    
	    //TITLE STATE
	    if(gp.gameState == gp.titleState) {
	    	drawTitleScreen();
	    }
	    if(gp.gameState == gp.playState) {
	    	drawPlayScreen();
	    }
	    if(gp.gameState == gp.optionState) {
	    	drawPlayScreen();

	    	
	    }
	    if(gp.gameState == gp.gameOverState) {
	    	drawPlayScreen();

	    	
	    }
	}
	
	public void drawTitleScreen() {
		
		if(titleScreenState == 0) {
			
			//BACKGROUND COLOR
			g2.setColor(new Color(33, 92, 109));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			
			//TITLE NAME
			g2.setFont(purisaB.deriveFont(Font.BOLD,40F));
			String text = "Tetris";
			int x = gp.tileSize;
			int y = gp.tileSize;
			int width = gp.tileSize*38;
			int height = gp.tileSize*4;
			drawSubWindow(x-12,y-10,width,height);
			g2.drawString(text, x+200, y+35); 
			
			 int[][] shape = gp.l.shape;
		        for (int row = 0; row < shape.length; row++) {
		            for (int col = 0; col < shape[0].length; col++) {
		                if (shape[row][col] == 1) {
		                    int tileX = imX + (int)(col * Entity.SIZE*2.2);
		                    int tileY =  imY + (int)(row * Entity.SIZE*2.2);
		                    g2.drawImage(gp.l.down1, tileX+115, tileY+30, (int)(Entity.SIZE*2.2), (int)(Entity.SIZE*2.2), null);

		                }
		            }
		        }

			//START
			g2.setFont(purisaB.deriveFont(Font.BOLD,48F));
			
			text = "NEW GAME";
			x=getXforCenteredText(text);
			y+=gp.tileSize*17;
			g2.drawString(text, x, y);
			if(commandNum == 0) {
		    	g2.drawString(">", x - gp.tileSize*2, y);
		    }
			
			text = "LOAD GAME";
			x=getXforCenteredText(text);
			y+=gp.tileSize*4;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
		    	g2.drawString(">", x - gp.tileSize*2, y);
		    }
			
			
			g2.setFont(purisaB.deriveFont(Font.BOLD,30F));
			
			text = "HIGH-SCORE:";
			x=getXforCenteredText(text)-gp.tileSize*10;
			y+=gp.tileSize*6;
			g2.drawString(text, x, y);
			if(max < pm.score) {
				max=pm.score;
			}
			
			value1=String.valueOf(max);
			x=getXforCenteredText(text)+gp.tileSize*7;
			if(pm.score >=0 && pm.score <=9)
			{
			g2.drawString(value1, x, y);
			}
			else if(pm.score > 9 && pm.score <=99) {
				g2.drawString(value1, x, y);
			}
			else if(pm.score > 99 && pm.score <=999) {
				g2.drawString(value1, x, y);
			}
			else if(pm.score > 999 && pm.score <=9999) {
				g2.drawString(value1, x, y);
			}
			else if(pm.score > 9999 && pm.score <=99999) {
				g2.drawString(value1, x, y);
			}
			else if(pm.score > 99999 && pm.score <=999999) {
				g2.drawString(value1, x, y);
			}
		}
		
	}
	
	public void drawPlayScreen() {
		
		//BACKGROUND COLOR
		g2.setColor(new Color(33, 92, 109));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		//Play Area
		int x = gp.tileSize;
		int y = gp.tileSize*5;
		int width = gp.tileSize*28;
		int height = gp.tileSize*42;
		drawPlayWindow(x-10,y+10,width,height);
//	    drawGrid(g2, x-10, y+10, width, height, pm.space); // overlay grid on top
		

		 // Draw current block using shape[][] and tileImage
	    if (pm.currentBlock != null && pm.currentBlock.shape != null ) {
	        int[][] shape = pm.currentBlock.shape;
	        for (int row = 0; row < shape.length; row++) {
	            for (int col = 0; col < shape[0].length; col++) {
	                if (shape[row][col] == 1) {
	                    int tileX = pm.currentBlock.worldX + col * pm.space+pm.OFFSET_X-10;
	                    int tileY = pm.currentBlock.worldY + row * pm.space+pm.OFFSET_Y;
	                    if(pm.currentBlock instanceof OBJ_I) {
	                    	g2.drawImage(pm.currentBlock.down1, tileX, tileY+30, pm.space,pm.space, null);
	                    }
	                    else  {
	                    	 g2.drawImage(pm.currentBlock.down1, tileX, tileY, pm.space,pm.space, null);
	                    }
	                }
	            }
	        }
	    }

		
		drawRec(x-13,y-70,width,height/8);//for background hiding
		
		//TITLE NAME
		g2.setFont(purisaB.deriveFont(Font.BOLD,40F));
		String text = "Tetris";
		x = gp.tileSize;
		y = gp.tileSize;
		width = gp.tileSize*38;
		height = gp.tileSize*4;
		drawSubWindow(x-12,y-8,width,height);
		g2.drawString(text, x+200, y+35); 
		
		//Score
		text = "Score:";
		x = gp.tileSize*28;
		y = gp.tileSize*7;
		width = gp.tileSize*10;
		height = gp.tileSize*10;
		drawSubWindow(x+9,y-6,width-8,height);
		g2.drawString(text, x+8, y+35); 
		g2.setColor(new Color(255, 200, 0));
		value=String.valueOf(pm.score);
		if(pm.score >=0 && pm.score <=9)
		{
		g2.drawString(value, x+70, y+100);
		}
		else if(pm.score > 9 && pm.score <=99) {
			g2.drawString(value, x+55, y+100);
		}
		else if(pm.score > 99 && pm.score <=999) {
			g2.drawString(value, x+49, y+100);
		}
		else if(pm.score > 999 && pm.score <=9999) {
			g2.drawString(value, x+38, y+100);
		}
		else if(pm.score > 9999 && pm.score <=99999) {
			g2.drawString(value, x+26, y+100);
		}
		else if(pm.score > 99999 && pm.score <=999999) {
			g2.drawString(value, x+15, y+100);
		}
		
		//Next
		g2.setColor(new Color(33, 92, 109));
		text = "Next:";
		x = gp.tileSize*28;
		y = gp.tileSize*17;
		width = gp.tileSize*10;
		height = gp.tileSize*10;
		drawSubWindow(x+9,y,width-8,height);
		g2.drawString(text, x+10, y+43); 
		
	}
	
	public void drawOptionScreen() {
		
		g2.setColor(Color.white);
		g2.setFont(arial_40);
		
		int x = gp.tileSize*5;
		int y = gp.tileSize*10;
		int width = gp.tileSize*20;
		int height = gp.tileSize*30;
		drawPlayWindow(x,y,width,height);
		
		switch (subState) {
		case 0: option_top(x,y); break;
		case 1:options_endGameConfirmation(x,y);break;
		}
	}
		
	public void option_top(int frameX,int frameY) {
		
		int textX;
		int textY;
		
		//TITLE
		String text ="Options";
		textX = getXforCenteredText(text)-75;
		textY = frameY+gp.tileSize*2;
		g2.drawString(text, textX, textY);
		
		//MUSIC
		textY +=gp.tileSize*8;
		g2.drawString("Music", textX-60, textY);
		if(commandNum == 1) {
				g2.drawString(">", textX-80, textY);
		}
		
		//SE
		textY +=gp.tileSize*4;
		g2.drawString("SE", textX-60, textY);
		if(commandNum == 2) {
			g2.drawString(">", textX-80, textY);
		}
		
		//SAVE
		textY +=gp.tileSize*4;
		g2.drawString("SAVE", textX-60, textY);
		if(commandNum == 3) {
				g2.drawString(">", textX-80, textY);
				if(gp.keyH.enterPressed == true) {
					gp.saveBlock.saveDat();
					gp.saveLoad.save(gp.pm.lockedBlocks);
					gp.keyH.enterPressed=false;
				}
		}
		
		//QUIT GAME
		textY +=gp.tileSize*7;
		g2.drawString("Quit Game", textX-60, textY);
		if(commandNum == 4) {
		g2.drawString(">", textX-80, textY);
		if(gp.keyH.enterPressed == true) {
				gp.gameState = gp.titleState;
				commandNum =0;
				gp.keyH.enterPressed=false;
			}
		}
		
		//MUSIC VOLUME
		textX = frameX+gp.tileSize*11;
		textY = frameY+(int)(gp.tileSize*8.5);
		g2.drawRect(textX, textY, 120, 24);//120/5 = 24
		int volumeWidth = 24 * gp.music.volumeScale;
		g2.fillRect(textX,textY,volumeWidth,24);
				
		//SE VOLUME
		textY +=gp.tileSize*4;
		g2.drawRect(textX, textY, 120, 24);
		volumeWidth = 24 * gp.se.volumeScale;
		g2.fillRect(textX,textY,volumeWidth,24);
				
		gp.config.saveConfig();
	}
		
	public void options_endGameConfirmation(int frameX,int frameY) {
		
	}
	
	public void drawGameOverScreen() {
		  
		  int y = gp.screenHeight/2-100;
		  Color c = new Color(255, 255, 255);
		  g2.setColor(c);
		  g2.setFont(g2.getFont().deriveFont(Font.PLAIN,40F));
		  String text ="Game Over\nPress Enter to Retutn to title Screen";
		  int x = getXforCenteredText(text)+400;
		  for(String line :text.split("\n")) {
			   
			  g2.drawString(line, x, y);
			  g2.setFont(g2.getFont().deriveFont(Font.PLAIN,24F));
			  y+=100;
			  x-=100;
			}

	}
	
	
	public void drawSubWindow(int x,int y,int width,int height) {
		
		Color c = new Color(51, 40, 40);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 25, 25);
		
		c = new Color(0,0,0);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(x, y, width, height, 25, 25);
	}
	
    public void drawPlayWindow(int x,int y,int width,int height) {
		
		Color c = new Color(0,0,0,220);
		g2.setColor(c);
		g2.fillRect(x, y, width, height);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(x, y, width, height);//for white border

	}
    
    public void drawRec(int x,int y,int width,int height) {
    	
    	Color c = new Color(33, 92, 109);
		g2.setColor(c);
		g2.fillRect(x, y, width, height);
		
    }
	
	public int getXforCenteredText(String text) {
		
		int length =(int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
	    int x = gp.screenWidth/2-length/2;
	    return x;
	    
	}
	
	//Grid to check pieces placement
/*	public void drawGrid(Graphics2D g2, int x, int y, int width, int height, int tileSize) {
	    g2.setColor(Color.GRAY); // light grid color

	    // Draw vertical lines
	    for (int i = 0; i <= width / tileSize; i++) {
	        int lineX = x + i * tileSize;
	        g2.drawLine(lineX, y, lineX, y + height);
	    }

	    // Draw horizontal lines
	    for (int i = 0; i <= height / tileSize; i++) {
	        int lineY = y + i * tileSize;
	        g2.drawLine(x, lineY, x+width , lineY);//this draws horizontal line from x coordinate to till x+ width,width=420
	    }
	}*/


}
