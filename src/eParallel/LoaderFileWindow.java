package eParallel;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.border.LineBorder;
//import java.awt.Color;
import java.awt.SystemColor;
import java.io.File;
import java.io.IOException;
//import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.nio.file.StandardCopyOption.*;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoaderFileWindow {

	private JFrame frmArchivosACargar;
	private JTextField textField;
	public String nameFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoaderFileWindow window = new LoaderFileWindow();
					window.frmArchivosACargar.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoaderFileWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmArchivosACargar = new JFrame();
		frmArchivosACargar.setTitle("Archivos a Copiar");
		frmArchivosACargar.setBounds(100, 100, 724, 485);
		// frmArchivosACargar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frmArchivosACargar.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmArchivosACargar.getContentPane().setLayout(null);

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setBorder(new LineBorder(SystemColor.scrollbar, 1, true));
		fileChooser.setBounds(10, 11, 688, 307);
		frmArchivosACargar.getContentPane().add(fileChooser);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(10, 371, 688, 20);
		frmArchivosACargar.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblArchivoSeleccionado = new JLabel("Archivo Seleccionado:");
		lblArchivoSeleccionado.setBounds(10, 352, 219, 14);
		frmArchivosACargar.getContentPane().add(lblArchivoSeleccionado);

		JButton btnNewButton = new JButton("Copiar");
		btnNewButton.setEnabled(false);

		btnNewButton.setBounds(10, 396, 89, 23);
		frmArchivosACargar.getContentPane().add(btnNewButton);

		int returnValue = fileChooser.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			nameFile = selectedFile.getName();
			textField.setText(selectedFile.getAbsolutePath() + " & Nombre: " + selectedFile.getName());
			btnNewButton.enableInputMethods(true);
			btnNewButton.setEnabled(true);
			Path FROM = Paths.get(selectedFile.getAbsolutePath());
			Path TODIR = Paths.get("C:\\Users\\emormau\\Documents\\empre\\db\\data\\work\\");
			System.out.println(selectedFile.getAbsolutePath());
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// CopyOption[] options = new
					// CopyOption[]{StandardCopyOption.REPLACE_EXISTING,
					// StandardCopyOption.COPY_ATTRIBUTES};
					try {
						Files.copy(FROM, TODIR.resolve(selectedFile.getName()));
					} catch (IOException e) {
						e.printStackTrace();
					}
					frmArchivosACargar.dispose();
				}
			});

		}

	}
}
