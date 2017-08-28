package eParallel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loadConsultarDB implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		mainHome.mainVisible(false);
		consultarDB.main("");
	}

}
