package squaredungeon.main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import squaredungeon.net.Packet01Disconnect;

public class WindowHandler implements WindowListener{

	private final Main main;
	
	public WindowHandler(Main main) {
		this.main = main;
		this.main.frame.addWindowListener(this);
	}
	
	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if(main.socketC != null) {
		Packet01Disconnect packet = new Packet01Disconnect(this.main.player.getUsername());
		packet.writeData(this.main.socketC);
		}
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		
	}

}
