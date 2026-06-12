package Data;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import Entity.Entity;
import Main.GamePanel;
import Main.LockedBlock;
import Main.PlayManager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

public class SaveLoad {
	
	GamePanel gp;
	PlayManager pm;
	
	public SaveLoad(GamePanel gp,PlayManager pm) {
		this.gp = gp;
		this.pm=pm;
	}
	
	
	
public void save(ArrayList<LockedBlock> lockedBlocks) {
		
		try {
			
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
		
		
		
  
		oos.writeObject(lockedBlocks);
		oos.close();
		}
		catch(Exception e) {
			System.out.println("Save Exception!");
		}
}

public  ArrayList<LockedBlock> load(BufferedImage[] loadTiles) {
	ArrayList<LockedBlock> loaded = new ArrayList<>();
	
	try {
		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));

		
		
		
	    //Read the DataStorage object
		loaded= (ArrayList<LockedBlock>)ois.readObject();
		ois.close();
		
		// Reassign image to each block using colorCode
        for (LockedBlock lb : loaded) {
            lb.image = loadTiles[lb.colorCode];
        }
        
	}
	catch(Exception e) {
		System.out.println("Load Exception!");
		e.printStackTrace(); // show actual error
	}
	
	 return loaded;
  }
}
		



