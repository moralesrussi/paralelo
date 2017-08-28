package eParallel;

import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.util.Properties;
import de.javasoft.plaf.synthetica.*;
import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;


@SuppressWarnings("unused")
public class mainHome {

	private JFrame frmEparallel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try { UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel");} 
					catch (Exception e){  e.printStackTrace();}
					mainHome window = new mainHome();
					window.frmEparallel.setVisible(true);
					// new Thread(this::ConversationListener).start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainHome() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// leerPropiedades();

		frmEparallel = new JFrame();
		frmEparallel.setTitle("eParallel ");
		frmEparallel.setBounds(100, 100, 835, 504);
		frmEparallel.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frmEparallel.getContentPane().setLayout(null);
		URL iconURL = getClass().getResource("/eparallel.png");
		ImageIcon icon = new ImageIcon(iconURL);
		frmEparallel.setIconImage(icon.getImage());
		

		JMenuBar menuBar = new JMenuBar();
		menuBar.setToolTipText("Menu Principal.\r\nCargar Archivos\r\nCargar Tablas\r\nCrear/Ejecutasr Jobs\r\nCrear Reglas\r\nGenerar Reportes\r\n");
		menuBar.setBounds(0, 0, 830, 21);
		frmEparallel.getContentPane().add(menuBar);

		JMenu mnFile = new JMenu("Menu");
		menuBar.add(mnFile);

		JMenuItem mntmAbrir = new JMenuItem("Abrir");
		mnFile.add(mntmAbrir);

		JMenuItem mntmCopiar = new JMenuItem("Copiar");
		mnFile.add(mntmCopiar);
		mntmCopiar.addActionListener(new loadWindowloadSinglefile());

		JMenuItem mntmCerrar = new JMenuItem("Cerrar");
		mnFile.add(mntmCerrar);
		mntmCerrar.addActionListener(new cerrarAplicacion());

		JMenu mnCargar = new JMenu("Archivos");
		menuBar.add(mnCargar);

		JMenuItem mntmCargarArchivos = new JMenuItem("Cargar archivos");
		mnCargar.add(mntmCargarArchivos);
		mntmCargarArchivos.addActionListener(new cargaArchivoLoader());
		
		JMenuItem mntmCrearFormato = new JMenuItem("Crear Fromato");
		mnCargar.add(mntmCrearFormato);
		mntmCrearFormato.addActionListener(new creaFormatoLoader());
		

		JMenuItem mntmBackup = new JMenuItem("Backup");
		mnCargar.add(mntmBackup);

		JMenu mnTablas = new JMenu("Tablas");
		menuBar.add(mnTablas);
		
		JMenuItem mntmConsultar = new JMenuItem("Consultar");
		mnTablas.add(mntmConsultar);
		mntmConsultar.addActionListener(new loadConsultarDB());
		
		JMenu mnJobs = new JMenu("Jobs & Reglas");
		menuBar.add(mnJobs);
		
		JMenuItem mntmCreaJob = new JMenuItem("Crear Job");
		mnJobs.add(mntmCreaJob);
		mntmCreaJob.addActionListener(new crearJobLoader());
		
		JMenuItem mntmConsuJob = new JMenuItem("Consultar Job");
		mnJobs.add(mntmConsuJob);
		mntmConsuJob.addActionListener(new consultarJobLoader());
		
		
		JMenuItem mntmCreaRegla = new JMenuItem("Crear Regla");
		mnJobs.add(mntmCreaRegla);
		mntmCreaRegla.addActionListener(new reglaLoader());

		JMenu mnConfirgurar = new JMenu("Configurar");
		menuBar.add(mnConfirgurar);

		JMenu mnReglas = new JMenu("Reglas");
		menuBar.add(mnReglas);
		
		
		menuBar.add(Box.createHorizontalGlue());
		JMenu mnAyuda = new JMenu("Ayuda ");
		mnAyuda.setHorizontalAlignment(SwingConstants.RIGHT);
		menuBar.add(mnAyuda);

		JMenuItem mntmManual = new JMenuItem("Manual");
		mnAyuda.add(mntmManual);

		JMenuItem mntmVersion = new JMenuItem("Version");
		mnAyuda.add(mntmVersion);

		JMenuItem mntmNosotros = new JMenuItem("Nosotros");
		mnAyuda.add(mntmNosotros);

	}

	public static void mainVisible(Boolean... args) {
		try {
			mainHome window = new mainHome();
			window.frmEparallel.setVisible(args[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// Runnable r = new Runnable() {
	// public void run() {
	// ConversationListener2();
	// }
	// };

}
