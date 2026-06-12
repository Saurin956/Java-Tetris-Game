package Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config  {

	GamePanel gp;
	
	public Config(GamePanel gp) {
		this.gp=gp;
	}
	
	public void saveConfig() {
		
		try {
			BufferedWriter bw=new BufferedWriter(new FileWriter("config.txt"));
		
			
			//Music Volume
			bw.write(String.valueOf(gp.music.volumeScale));
			bw.newLine();
			
			//SE Volume
			bw.write(String.valueOf(gp.se.volumeScale));
			bw.newLine();
			
			//SCORE
			bw.write(String.valueOf(gp.ui.max));
			bw.newLine();
			
			bw.close();
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadConfig() {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("config.txt"));
		
		    String s = br.readLine();//read what is written in config.txt
		
		    //Music Volume

		    gp.music.volumeScale = Integer.parseInt(s);
		    
		    //SE Volume
		    s=br.readLine();//read next line and get it as s
		    gp.se.volumeScale = Integer.parseInt(s);
		    
		    //Score
		    s=br.readLine();//read next line and get it as s
		    gp.ui.max = Integer.parseInt(s);
		    
		    br.close();
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
