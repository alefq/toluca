/*
 * Created on 27-dic-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package py.edu.uca.fcyt.toluca.sound;

/**
 * @author Alejandro Alliana
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.applet.*;
import java.net.*;

public class PlaySound {

	public static final String CARD_PLAYED_SOUND_URL = "http://truco.com.py/audio/carta.au";
	public static final String DEAL_SOUND_URL = "http://truco.com.py/audio/deal.au";
	
	
	
	public static void play(final String url) {

		new Thread(new Runnable() {
			public void run() {
				AudioClip onceClip; // onceClip is the File to play

				URL completeURL;

				try {
					completeURL = new URL(url); // The Location of the file
					onceClip = Applet.newAudioClip(completeURL);
					onceClip.play();
			
				} catch (MalformedURLException e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	
	}
}