package Object;

import java.io.Serializable;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_T extends Entity  implements Serializable{

	public static final String objName="T";




	
	public OBJ_T(GamePanel gp) {
		super(gp);
		
		this.colorCode = 4;
		
		name=objName;
		down1=setup("/Asset/Yellow",gp.tileSize*4,gp.tileSize*4);
		// "L" shape: 3 vertical blocks, 1 to the right
		
		shape = new int[][] {
			{0,0,0},
		    {0,1,0},
		    {1,1,1},
		};

		
	}
	
	public void getDirection1() {
		temp=new int[][] {
			{0,1, 0},
		    {1,1, 1},
		    {0,0, 0},
		};
		updateXY(1);
				
	}
    public void getDirection2() {
    	temp=new int[][] {
			{0,1, 0},
		    {0,1, 1},
		    {0,1, 0},
		};
		updateXY(2);
    }
    public void getDirection3() {
    	temp=new int[][] {
			{0,0, 0},
		    {1,1, 1},
		    {0,1, 0},
		};
		updateXY(3);
    }
    public void getDirection4() {
    	temp=new int[][] {
			{0,1, 0},
		    {1,1, 0},
		    {0,1, 0},
		};
    }

}
