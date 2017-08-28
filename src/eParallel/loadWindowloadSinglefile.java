package eParallel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loadWindowloadSinglefile implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		mainHome.mainVisible(false);
		loadSingleFile.main(null);
	}
}
