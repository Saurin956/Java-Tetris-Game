package Data;

import java.io.Serializable;
import java.util.ArrayList;

import Entity.Entity;
import Main.LockedBlock;

public class DataStorage implements Serializable {
	
	ArrayList<LockedBlock> lockedBlocks = new ArrayList<>();
	
	 public String currentBlock;
	 public String nextBlock;
	 public int currentX, currentY;
	 public int score;
	 

}
