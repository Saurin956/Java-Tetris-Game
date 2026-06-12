package Main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {

	public static JFrame window;
	
public static void main(String args[]) {
	
	System.setProperty("sun.java2d.dpiware", "true");
	
	window = new JFrame("Simple Tetris");
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	window.setResizable(false);
	window.setTitle("Tetris");
	new Main().setIcon();
	
	GamePanel gamePanel = new GamePanel();
	window.add(gamePanel);
	
//	gamePanel.getGraphicsConfiguration().loadConfig();

    window.pack();//size of GamePanel becomes size of window
    
    window.setLocationRelativeTo(null);
    window.setVisible(true);
    
	gamePanel.startGameThread();
}

public void setIcon() {
	
	ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("Asset/Blue.png"));
	window.setIconImage(icon.getImage());
	
}
}
