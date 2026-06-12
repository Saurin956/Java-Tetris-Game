package Main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
//store the files as wav files for sound
//sound made using beepbox
public class Sound {

	Clip clip;
    URL soundURL[] = new URL[30];//Url is used to store the file path of the sound files
    FloatControl fc;
    int volumeScale = 3;
    float volume;
    
     public Sound() {
    	 
    	 soundURL[0] = getClass().getResource("/Sound/BlueBoyAdventure.wav");
    	 soundURL[1] = getClass().getResource("/Sound/coin.wav");
    	 soundURL[2] = getClass().getResource("/Sound/powerup.wav");
    	 soundURL[3] = getClass().getResource("/Sound/unlock.wav");
    	 soundURL[4] = getClass().getResource("/Sound/fanfare.wav");
    	 soundURL[5] = getClass().getResource("/Sound/hitmonster.wav");
    	 soundURL[6] = getClass().getResource("/Sound/receivedamage.wav");
    	 soundURL[7] = getClass().getResource("/Sound/speak.wav");
    	 soundURL[8] = getClass().getResource("/Sound/levelup.wav");
    	 soundURL[9] = getClass().getResource("/Sound/cursor.wav");
    	 soundURL[10] = getClass().getResource("/Sound/burning.wav");
    	 soundURL[11] = getClass().getResource("/Sound/cuttree.wav");
    	 soundURL[12] = getClass().getResource("/Sound/gameover.wav");
    	 soundURL[13] = getClass().getResource("/Sound/stairs.wav");
    	 soundURL[14] = getClass().getResource("/Sound/sleep.wav");
    	 soundURL[15] = getClass().getResource("/Sound/blocked.wav");
    	 soundURL[16] = getClass().getResource("/Sound/parry.wav");
    	 soundURL[17] = getClass().getResource("/Sound/Merchant.wav");
    	 soundURL[18] = getClass().getResource("/Sound/Dungeon.wav");
    	 soundURL[19] = getClass().getResource("/Sound/chipwall.wav");
    	 soundURL[20] = getClass().getResource("/Sound/dooropen.wav");
    	 soundURL[21] = getClass().getResource("/Sound/FinalBattle.wav");
    	 
    	 
     }
     
     public void setFile(int i) {
    	
    	 try {
    		 AudioInputStream ais =AudioSystem.getAudioInputStream(soundURL[i]);
    	     clip = AudioSystem.getClip();//to open audio file
    	     clip.open(ais);//to open audio file
    	     fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);//with this we can pass values so that sound volume changes
    	     //floatControl accepts numbers between -80f to 6f
    	     checkVolume();
    	 }catch(Exception e) {
    		 
    	 }
     }
     
     public void play() {
    	 
    	 clip.start();
     }
     
     public void loop() {
    	 
    	 clip.loop(Clip.LOOP_CONTINUOUSLY);
     }
     
     public void stop() {
    	 
    	 clip.stop();
     }
     
     public void checkVolume() {
    	 
    	 switch(volumeScale) {
    	 case 0: volume = -80f;break;
    	 case 1: volume = -20f;break;
    	 case 2: volume = -12f;break;
    	 case 3: volume = -5f;break;
    	 case 4: volume = 1f;break;
    	 case 5: volume = 6f;break;
    	 }
    	 fc.setValue(volume);
     }
}
