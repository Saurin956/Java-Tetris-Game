package Data;

import Main.GamePanel;
import Object.OBJ_I;
import Object.OBJ_L;
import Object.OBJ_R;
import Object.OBJ_S;
import Object.OBJ_T;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import Entity.Entity;

import java.io.File;
import java.io.FileInputStream;


public class SaveBlock  implements Serializable {
    private static final long serialVersionUID = 1L; 
 

	GamePanel gp;
	
	public SaveBlock(GamePanel gp) {
		this.gp = gp;
	}
	
    public void saveDat() {
		
		try {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("saveDat.dat")));
		
		DataStorage ds= new DataStorage();
		ds.currentBlock=gp.pm.currentBlock.name;
		ds.currentX = gp.pm.currentBlock.worldX;
		ds.currentY = gp.pm.currentBlock.worldY;
		ds.nextBlock=gp.pm.nextBlock.name;
		ds.score=gp.pm.score;
		
		oos.writeObject(ds);
		oos.close();
        System.out.println("Saved!");
}
		catch(Exception e) {
			System.out.println("Save Exception!");
		}
    }
    
    public void loadDat() {
    	
    	try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("saveDat.dat")));
		
		    //Read the DataStorage object
			DataStorage ds= (DataStorage)ois.readObject();
			 gp.pm.currentBlock = getBlockByName(ds.currentBlock, gp);
			 gp.pm.currentBlock.worldX = ds.currentX;
			 gp.pm.currentBlock.worldY = ds.currentY;
	         gp.pm.nextBlock = getBlockByName(ds.nextBlock, gp);
	         gp.pm.score=ds.score;
    }
    	catch(Exception e) {
			System.out.println("Load Exception!");
			e.printStackTrace(); // show actual error
		}
}
    
    // Factory method to recreate block object from name
    public static Entity getBlockByName(String name, GamePanel gp) {
        switch (name) {
            case "I": return new OBJ_I(gp);
            case "T": return new OBJ_T(gp);
            case "L": return new OBJ_L(gp);
            case "S": return new OBJ_S(gp);
            case "R": return new OBJ_R(gp);
            default: return null;
        }
    }
    
    
  
   
}
