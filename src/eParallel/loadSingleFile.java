package eParallel;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;

@SuppressWarnings({ "serial", "unused" })
public class loadSingleFile extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	public String nameFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loadSingleFile frame = new loadSingleFile();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public loadSingleFile() {
		setTitle("Copia Archivo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 773, 544);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSeleccionarElArchivo = new JLabel("Seleccionar el archivo a copiar en la carpeta de trabajo");
		lblSeleccionarElArchivo.setBounds(10, 11, 460, 30);
		contentPane.add(lblSeleccionarElArchivo);

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setBounds(0, 39, 730, 397);
		contentPane.add(fileChooser);

		textField = new JTextField();
		textField.setBounds(180, 435, 436, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblArchivoSeleccionado = new JLabel("Archivo Seleccionado");
		lblArchivoSeleccionado.setBounds(74, 437, 113, 17);
		contentPane.add(lblArchivoSeleccionado);

		JButton btnCopiar = new JButton("Cancelar Copia");
		btnCopiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				// mainHome.main(null);
			}
		});
		btnCopiar.setBounds(408, 466, 113, 23);
		contentPane.add(btnCopiar);

		JButton btnNewButton = new JButton("Copiar Archivo");
		btnNewButton.setBounds(238, 466, 121, 23);
		contentPane.add(btnNewButton);

		int returnValue = fileChooser.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			nameFile = selectedFile.getName();
			textField.setText(selectedFile.getAbsolutePath() + " & Nombre: " + selectedFile.getName());
			btnNewButton.enableInputMethods(true);
			btnNewButton.setEnabled(true);
			Path FROM = Paths.get(selectedFile.getAbsolutePath());
			// Path TODIR =
			// Paths.get("C:\\Users\\emormau\\Documents\\empre\\db\\data\\work\\");
			System.out.println("Ruta2:  " + Configuration.getInstance().getValue("DIR_WORK"));
			Path TODIR = Paths.get(Configuration.getInstance().getValue("DIR_WORK"));
			System.out.println(selectedFile.getAbsolutePath());
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CopyOption[] options = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING,
							StandardCopyOption.COPY_ATTRIBUTES };
					try {
						Files.copy(FROM, TODIR.resolve(selectedFile.getName()), options);

					} catch (IOException e) {
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Archivo copiado con Exito!!!", "Mensaje ",
							JOptionPane.INFORMATION_MESSAGE);
					dispose();
					// mainHomemain(null);
				}
			});

		}

	}
}
