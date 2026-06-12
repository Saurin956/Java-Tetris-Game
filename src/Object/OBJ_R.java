package Object;

import java.io.Serializable;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_R extends Entity implements Serializable {

	public static final String objName="R";

	
	
	public OBJ_R(GamePanel gp) {
		super(gp);
		
		this.colorCode = 2;
		
		name=objName;
		down1=setup("/Asset/Purple",gp.tileSize*4,gp.tileSize*4);
		// "L" shape: 3 vertical blocks, 1 to the right
		
		shape = new int[][] {
			{0,0,0},
			{0,1, 1,0},
		    {0,1, 1,0},
		};
		
	}

}
