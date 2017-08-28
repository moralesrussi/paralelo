package eParallel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import de.javasoft.plaf.synthetica.*;
import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;

@SuppressWarnings({ "serial", "unused" })
public class cargaArchivo extends JFrame {

	private JPanel contentPane;
	private JTable table;
	public String[] dataFormato;
	Connection conn = null;
	static String tablaConsulta, nameFile, strJSON;
	private final DefaultTableModel tableModel = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			// segunda fila en adelante
			return row < 1;
		}
	};
	private File selectedFile;
	private String extension;
	@SuppressWarnings("unused")
	private Path filePath;
	private Vector<String> columnNames = new Vector<String>();
	private Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	private JTextField textField;
	private JTextField textField_1;
	public static JTextField textField_2;
	public static JTextField textField_3;
	public static JTextField textField_4;
	public static JTextField textField_5;
	public static JTextField textField_6;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_6;
	private static JButton btnCrearFrmTbl;
	private JButton btnCrearSoloTabla;
	private static JButton btnCrearFormato;
	private static cargaArchivo frame;

	// PgConnection copyOperationConnection = (PgConnection) connection;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try { 
						UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel");
						UIManager.put("Synthetica.extendedFileChooser.useSystemFileIcons", false);
						UIManager.put("Synthetica.extendedFileChooser.sortEnabled", false);
						UIManager.put("Synthetica.extendedFileChooser.rememberLastDirectory", false);
					
					} 
					catch (Exception e){ }
					frame = new cargaArchivo();
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public cargaArchivo() {
		// setIconImage(Toolkit.getDefaultToolkit().getImage(cargaArchivo.class.getResource("/com/sun/java/swing/plaf/motif/icons/DesktopIcon.gif")));
		// setIconImage(Toolkit.getDefaultToolkit().getImage(cargaArchivo.class.getResource("/resources/eParallel_v1.ico")));
		//Charset charset = Charset.forName("UTF-8");
		String[] col1 = { "text", "integer", "date", "boolean", "timestamp" };
		String[][] listData = { col1, col1 };

		//this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		
		
		setBounds(100, 100, 1004, 716);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setBounds(0, 0, 978, 269);
		contentPane.add(fileChooser);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	JOptionPane.showConfirmDialog(null, 
		                "Realmente desea cerrar esta ventana?", "Desea Cerrar?", 
		                JOptionPane.YES_NO_OPTION,
		                JOptionPane.QUESTION_MESSAGE);
		    			fileChooser.removeAll();
		    			contentPane.remove(fileChooser);
		    		getContentPane().removeAll();
		    		getContentPane().repaint();
		    		UIManager.removeAuxiliaryLookAndFeel(UIManager.getLookAndFeel());
		    		frame.setVisible(false);
		    		//dispose();
		    		//System.exit(0); 
		    }
		});
		

		fileChooser.setApproveButtonText("Leer");
		fileChooser.setApproveButtonMnemonic('a');
		// FileFilter textFilter = new ExtensionFileFilter(null, new String[] {
		// "CVS", "TXT" });
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos Planos", "txt", "text", "csv");
		fileChooser.setFileFilter(filter);

		JRadioButton rdbtnHeader = new JRadioButton("Header");
		rdbtnHeader.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnHeader.setBounds(480, 278, 109, 23);
		contentPane.add(rdbtnHeader);

		JRadioButton rdbtnCrearFormato = new JRadioButton("Crear Formato");
		rdbtnCrearFormato.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnCrearFormato.setBounds(480, 304, 150, 23);
		contentPane.add(rdbtnCrearFormato);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 267, 968, 2);
		contentPane.add(separator);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Separador", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(649, 280, 116, 43);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(6, 16, 100, 20);
		panel_1.add(comboBox);
		comboBox.addItem(";");
		comboBox.addItem(",");
		comboBox.addItem("|");
		comboBox.addItem(".");
		comboBox.addItem("Tab");
		comboBox.addItem("Espacio");

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Archivo a Cargar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(0, 363, 980, 182);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 16, 968, 155);
		panel.add(scrollPane);
		scrollPane.setAutoscrolls(true);

		table = new JTable();
		scrollPane.setViewportView(table);

		fileChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
					System.out.println("File selected: " + fileChooser.getSelectedFile());
					selectedFile = fileChooser.getSelectedFile();
					nameFile = selectedFile.getName();
					filePath = Paths.get(selectedFile.getAbsolutePath());
					extension = ((FileNameExtensionFilter) fileChooser.getFileFilter()).getExtensions()[0];

					String strSeparador = comboBox.getSelectedItem().toString();
					if (strSeparador == null)
						strSeparador = ";";
					if (strSeparador == "Tab")
						strSeparador = "\t";
					if (strSeparador == "Space")
						strSeparador = " ";
					if (strSeparador == ".")
						strSeparador = "\\.";
					if (strSeparador == ",")
						strSeparador = ",";
					Integer numeroColumnas = 0;

					FileInputStream fstream;
					try {
						fstream = new FileInputStream(selectedFile);
						@SuppressWarnings("resource")
						BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
						String strLine;
						Integer i = 0;
						tableModel.setRowCount(0);
						columnNames.setSize(0);
						Vector<Object> vector = new Vector<Object>();
						Vector<Object> vector_1 = new Vector<Object>();
						Vector<Object> vector_2 = new Vector<Object>();
						// Read File Line By Line
						while ((strLine = br.readLine()) != null && i <= 10) {
							// Print the content on the console

							String[] parts = strLine.split(Pattern.quote(strSeparador)); // SEPARADOR
							// System.out.println("Linea: " + i + " " + strLine
							// + "-" + strSeparador);
							// System.out.println("Size line: " + parts.length);
							if (i == 0) {
								// HEADERS
								numeroColumnas = parts.length;
								for (int k = 0; k < parts.length; k++) {
									columnNames.add(parts[k]);
								}
							} else if (i == 1) {
								// LISTA DE VALORES COMBOX
								if (parts.length != numeroColumnas) {
									throw new Exception("Numero de columnas del header diferente del numero de datos.");

								}

								for (int j = 0; j < parts.length; j++) {
									vector.add(listData[1]);
								}
								data.add(vector);
								// DATA 1

								for (int j = 0; j < parts.length; j++) {
									vector_1.add(parts[j]);
								}
								data.add(vector_1);

							} else {
								// DATOS DE TABLA
								//
								for (int j = 0; j < parts.length; j++) {
									vector_2.add(parts[j]);
								}
								data.add(vector_2);
							}
							// parts = null;
							i++;
						}
						br.close();
					} catch (Throwable e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, " Numero de columnas no coincide con datos",
								"Error leyendo archivo", JOptionPane.ERROR_MESSAGE);
					}
					
					tableModel.setDataVector(data, columnNames);
					table.setEnabled(true);
					btnCrearSoloTabla.setVisible(true); // BOTON DE CREAR
														// ARCHIVO

					TableColumnModel colModel = table.getColumnModel();
					Integer numColumns = table.getColumnCount();
					for (int j = 0; j < numColumns; j++) {
						TableColumn col = colModel.getColumn(j);
						col.setCellRenderer(new TableListRenderer(listData[1]));
						col.setCellEditor(new TableListEditor(listData[1]));
					}
					// set row height
					TableListRenderer renderer = (TableListRenderer) table.getCellRenderer(0, 0); // problem!!
					Dimension d = renderer.comboBox.getPreferredSize();
					int rowHeight = d.height;
					table.setRowHeight(rowHeight);

				}
			}
		});
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setModel(tableModel);
		table.setAutoResizeMode(5);

		JButton btnCrearClaves = new JButton("Crear Claves");
		btnCrearClaves.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String formatJSON = "";
				String cierre = "\"},";
				int column = table.getColumnCount();
				for (int i = 0; i < column; i++) {
					if (i == (column - 1)) {
						cierre = "\"}";
					}
					if (table.getValueAt(0, i).toString().length() >= 18) {
						if (table.getValueAt(0, i).toString().substring(1, 18).equals("Ljava.lang.String")) {
							// {"column_name": "PERIODEND" , "column_type":
							// "date"}
							formatJSON = formatJSON + "{\"column_name\": \"" + table.getColumnName(i)
									+ "\", \"column_type\": \"text" + cierre + System.lineSeparator();

						} else {
							formatJSON = formatJSON + "{\"column_name\": \"" + table.getColumnName(i)
									+ "\", \"column_type\": \"" + table.getValueAt(0, i) + cierre
									+ System.lineSeparator();
						}
					} else {
						formatJSON = formatJSON + "{\"column_name\": \"" + table.getColumnName(i)
								+ "\", \"column_type\": \"" + table.getValueAt(0, i) + cierre + System.lineSeparator();
					}
					// System.out.println(
					// "JSON String: " + "{\"tabla\": [" +
					// System.lineSeparator() + formatJSON + "]}::jsonb");
				}
				String[] nombreFormato = { "{\"tabla\": [" + System.lineSeparator() + formatJSON + "]}" };
				strJSON = "'{\"tabla\": [" + System.lineSeparator() + formatJSON + "]}'::jsonb";
				creaFormato rowFormato = new creaFormato(nombreFormato);
				// rowFormato.dataFormato[0] = "'{\"tabla\":
				// ["+System.lineSeparator()+ formatJSON+ "]}'::jsonb";
				rowFormato.setVisible(true);

			}
		});
		btnCrearClaves.setBounds(812, 570, 166, 23);
		contentPane.add(btnCrearClaves);

		textField = new JTextField();
		textField.setBounds(122, 280, 321, 23);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(122, 310, 321, 23);
		contentPane.add(textField_1);

		JLabel lblNombreTabla = new JLabel("Nombre Tabla");
		lblNombreTabla.setBounds(20, 284, 77, 14);
		contentPane.add(lblNombreTabla);

		JLabel lblNombreFormato = new JLabel("Nombre Formato");
		lblNombreFormato.setBounds(18, 314, 94, 14);
		contentPane.add(lblNombreFormato);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(889, 632, 89, 23);
		contentPane.add(btnCancelar);

		panel_2 = new JPanel();
		panel_2.setBorder(
				new TitledBorder(null, "Clave Principal", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(14, 555, 150, 43);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		textField_2.setEnabled(false);
		textField_2.setEditable(false);
		textField_2.setBounds(6, 16, 138, 20);
		panel_2.add(textField_2);
		textField_2.setColumns(10);

		panel_3 = new JPanel();
		panel_3.setBorder(
				new TitledBorder(null, "Clave Secundaria 1", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(162, 555, 150, 43);
		contentPane.add(panel_3);
		panel_3.setLayout(null);

		textField_3 = new JTextField();
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 10));
		textField_3.setEditable(false);
		textField_3.setBounds(6, 16, 138, 20);
		panel_3.add(textField_3);
		textField_3.setColumns(10);

		panel_4 = new JPanel();
		panel_4.setBorder(
				new TitledBorder(null, "Clave Secundaria 2", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBounds(311, 555, 150, 43);
		contentPane.add(panel_4);
		panel_4.setLayout(null);

		textField_4 = new JTextField();
		textField_4.setFont(new Font("Tahoma", Font.PLAIN, 10));
		textField_4.setEditable(false);
		textField_4.setEnabled(false);
		textField_4.setBounds(6, 16, 138, 20);
		panel_4.add(textField_4);
		textField_4.setColumns(10);

		panel_5 = new JPanel();
		panel_5.setBorder(
				new TitledBorder(null, "Clave Secundaria 3", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setBounds(459, 555, 150, 43);
		contentPane.add(panel_5);
		panel_5.setLayout(null);

		textField_5 = new JTextField();
		textField_5.setFont(new Font("Tahoma", Font.PLAIN, 10));
		textField_5.setEditable(false);
		textField_5.setEnabled(false);
		textField_5.setBounds(6, 16, 138, 20);
		panel_5.add(textField_5);
		textField_5.setColumns(10);

		panel_6 = new JPanel();
		panel_6.setBorder(
				new TitledBorder(null, "Clave Secundaria 4", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.setBounds(612, 555, 150, 43);
		contentPane.add(panel_6);
		panel_6.setLayout(null);

		textField_6 = new JTextField();
		textField_6.setFont(new Font("Tahoma", Font.PLAIN, 10));
		textField_6.setEditable(false);
		textField_6.setEnabled(false);
		textField_6.setBounds(6, 16, 138, 20);
		panel_6.add(textField_6);
		textField_6.setColumns(10);

		JPanel panel_7 = new JPanel();
		panel_7.setBorder(null);
		panel_7.setBounds(10, 546, 792, 57);
		contentPane.add(panel_7);

		btnCrearFrmTbl = new JButton("Crear Formato y Tabla");
		btnCrearFrmTbl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textField.getText().equals("") && !textField_1.getText().equals("")) {
					if (!textField_1.getText().trim().equals("")) {
						conn = ConectarDBPostgres.main(null);
						String cierre = ",";
						String columnasCrear = "";
						String columnasSolo = "";
						int column = table.getColumnCount();
						for (int i = 0; i < column; i++) {
							if (i == (column - 1)) {
								cierre = " ";
							}
							if (table.getValueAt(0, i).toString().length() >= 18) {
								if (table.getValueAt(0, i).toString().substring(1, 18).equals("Ljava.lang.String")) {
									// {"column_name": "PERIODEND" ,
									// "column_type":
									// "date"}
									columnasCrear = columnasCrear + table.getColumnName(i) + " text" + cierre
											+ System.lineSeparator();
									columnasSolo = columnasSolo + table.getColumnName(i) + cierre;

								} else {
									columnasCrear = columnasCrear + table.getColumnName(i) + " "
											+ table.getValueAt(0, i) + cierre + System.lineSeparator();
									columnasSolo = columnasSolo + table.getColumnName(i) + cierre;
								}
							} else {
								columnasCrear = columnasCrear + table.getColumnName(i) + "  " + table.getValueAt(0, i)
										+ cierre + System.lineSeparator();
								columnasSolo = columnasSolo + table.getColumnName(i) + cierre;
							}
						}
						// SQL EXISTEN TABLA y FORMATOS?
						String sql_queryTbl = "SELECT count(1) FROM pg_tables WHERE schemaname = 'public' AND tablename='"
								+ textField.getText().trim() + "';";
						String sql_queryFrm = "SELECT count(1) FROM app.app_tipo_archivos WHERE nombre='"
								+ textField_1.getText().trim() + "';";
						String sql_creaTbl = "CREATE TABLE public." + textField.getText().trim()
								+ " ( id Serial PRIMARY KEY," + columnasCrear + ");";
						String slq_countTbl = "SELECT COUNT(1) FROM public." + textField.getText().trim() + ";";
						String sql_idFile = "SELECT max(id) FROM app.app_archivos WHERE nombre_archivo='" + nameFile
								+ "';";
						String sql_idForm = "SELECT max(id) FROM app.app_tipo_archivos WHERE nombre='"
								+ textField_1.getText().trim() + "';";
						String sql_insert = "INSERT INTO app.app_tipo_archivos(nombre, columnas_tipo, fecha_creacion, clave_1, clave_2, clave_3, clave_4, clave_5, clave_6) VALUES ('"
								+ textField_1.getText().trim() + "', " + strJSON + ", now(), '" + textField_2.getText()
								+ "', '" + textField_3.getText() + "', '" + textField_4.getText() + "', '"
								+ textField_5.getText() + "', '" + textField_6.getText() + "','');";
						// System.out.println("SQL Query: " + sql_queryFrm);
						// System.out.println("SQL Insert: " + sql_insert);
						if (ejecutaQueryGuardar2(sql_queryFrm, "T") == "0") {
							if (ejecutaQueryGuardar2(sql_queryTbl, "T") == "0") {
								if (ejecutaQueryGuardar2(sql_creaTbl, "C") == "0") {
									try {
										conn = ConectarDBPostgres.main(null);
										CopyManager copyManager = new CopyManager((BaseConnection) conn);
										FileReader fileReader = new FileReader(selectedFile);
										String col = removeLastChar(columnasSolo);
										copyManager.copyIn(
												"COPY " + textField.getText().trim() + " (" + col
														+ ") FROM STDIN WITH DELIMITER '"
														+ comboBox.getSelectedItem().toString() + "' CSV HEADER",
												fileReader);
										conn.close();
									} catch (SQLException | IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									System.out.println("Insert Formato en app.app_tipo_archivos.");
									ejecutaQueryGuardar2(sql_insert, "I");
									String cantidad = ejecutaQueryGuardar2(slq_countTbl, "S");
									String sql_instblAr = "INSERT INTO app.app_archivos(nombre_archivo, formato_archivo, registros_cargados, fecha_carga, \"Estado\") VALUES ('"
											+ nameFile + "', '" + extension + "', " + Integer.valueOf(cantidad.trim())
											+ ", now(), 'Activo' );";
									System.out.println("Insert  Archivo en app.app_archivos: " + sql_instblAr);
									ejecutaQueryGuardar2(sql_instblAr, "I");
									String idFile = ejecutaQueryGuardar2(sql_idFile, "S");
									String sql_insertArTabla = "INSERT INTO app.app_archivo_tabla(id_archivo, tabla, fecha_creacion) VALUES ("
											+ idFile + ", '" + textField.getText().trim() + "', now());";
									System.out.println("Insert  Archivo y Tabla en app.app_archivo_tabla:" + sql_insertArTabla);
									ejecutaQueryGuardar2(sql_insertArTabla, "I");
									String idFormato = ejecutaQueryGuardar2(sql_idForm, "S");
									System.out.println("Insert  Formato Tabla en app.app_archivo_tabla.");
									String sql_idFileFormato = "INSERT INTO app.app_tipo_archivo_archivo(id_archivo_tipo, id_archivo) VALUES ("
											+ idFormato + "," + idFile + " );";
									ejecutaQueryGuardar2(sql_idFileFormato, "I");

								} else {
									System.out.println("Error Creado La tabla de datos");
								}
							} else {
								textField.setText("");
							}
						} else {
							textField_1.setText("");
						}

					} else {
						JOptionPane.showMessageDialog(null, " Ingrese un nombre de formato valid0",
								"No se puede crear formato", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, " Ser requiere nombre de tabla y de Formato",
							"No se puede crear tabla o formato", JOptionPane.ERROR_MESSAGE);

				}
			}

			private String removeLastChar(String s) {
				// TODO Auto-generated method stub
				if (s == null || s.length() == 0) {
					return s;
				}
				return s.substring(0, s.length() - 1);
			}
		});
		btnCrearFrmTbl.setBounds(321, 632, 166, 23);
		contentPane.add(btnCrearFrmTbl);
		btnCrearFrmTbl.setVisible(false);

		btnCrearSoloTabla = new JButton("Crear Solo Tabla");
		btnCrearSoloTabla.setBounds(122, 632, 166, 23);
		contentPane.add(btnCrearSoloTabla);
		btnCrearSoloTabla.setVisible(false);

		btnCrearFormato = new JButton("Crear Solo Formato");
		btnCrearFormato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textField_1.getText().trim().equals("")) {

					String cierre = ",";
					String columnasCrear = "";
					int column = table.getColumnCount();
					for (int i = 0; i < column; i++) {
						if (i == (column - 1)) {
							cierre = " ";
						}
						if (table.getValueAt(0, i).toString().length() >= 18) {
							if (table.getValueAt(0, i).toString().substring(1, 18).equals("Ljava.lang.String")) {
								// {"column_name": "PERIODEND" , "column_type":
								// "date"}
								columnasCrear = columnasCrear + table.getColumnName(i) + " text" + cierre
										+ System.lineSeparator();

							} else {
								columnasCrear = columnasCrear + table.getColumnName(i) + " " + table.getValueAt(0, i)
										+ cierre + System.lineSeparator();
							}
						} else {
							columnasCrear = columnasCrear + table.getColumnName(i) + "  " + table.getValueAt(0, i)
									+ cierre + System.lineSeparator();
						}
					}
					String sql_queryFrm = "SELECT count(1) FROM app.app_tipo_archivos WHERE nombre='"
							+ textField_1.getText().trim() + "';";
					String sql_insert = "INSERT INTO app.app_tipo_archivos(nombre, columnas_tipo, fecha_creacion, clave_1, clave_2, clave_3, clave_4, clave_5, clave_6) VALUES ('"
							+ textField_1.getText().trim() + "', " + strJSON + ", now(), '" + textField_2.getText()
							+ "', '" + textField_3.getText() + "', '" + textField_4.getText() + "', '"
							+ textField_5.getText() + "', '" + textField_6.getText() + "','');";
					// System.out.println("SQL Query: " + sql_queryFrm);
					// System.out.println("SQL Insert: " + sql_insert);

					if (ejecutaQueryGuardar2(sql_queryFrm, "T") == "0") {// SE
																			// PUEDE
																			// PROCEDER
																			// A
																			// LA
																			// CARGA
						ejecutaQueryGuardar2(sql_insert, "I");
					} else {
						textField_1.setText("");
					} // RESETEA NOMBRE
				} else {
					JOptionPane.showMessageDialog(null, " Ingrese un nombre de formato valid0",
							"No se puede crear formato", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnCrearFormato.setBounds(517, 632, 166, 23);
		contentPane.add(btnCrearFormato);
		btnCrearFormato.setVisible(false);

		panel_7.setVisible(false);
		panel_6.setVisible(false);
		panel_5.setVisible(false);
		panel_4.setVisible(false);
		panel_3.setVisible(false);
		panel_2.setVisible(false);
		btnCrearClaves.setVisible(false);

		rdbtnCrearFormato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!rdbtnCrearFormato.isSelected()) {
					panel_6.setVisible(false);
					panel_5.setVisible(false);
					panel_4.setVisible(false);
					panel_3.setVisible(false);
					panel_2.setVisible(false);
					btnCrearClaves.setVisible(false);
					btnCrearFrmTbl.setVisible(false);
					btnCrearFormato.setVisible(false);
				} else {
					panel_6.setVisible(true);
					panel_5.setVisible(true);
					panel_4.setVisible(true);
					panel_3.setVisible(true);
					panel_2.setVisible(true);
					btnCrearClaves.setVisible(true);
				}
				;
			}
		});

	}

	////////// INICIO FUNCIONES

	class TableListRenderer implements TableCellRenderer {
		@SuppressWarnings("rawtypes")
		JComboBox comboBox;
		JLabel label;
		Component c;

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public TableListRenderer(String[] items) {
			comboBox = new JComboBox(items);
			comboBox.setSelectedIndex(0);
			label = new JLabel();
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setOpaque(true);

		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (row == 0 && column < 99999) {
				c = comboBox;
				if (isSelected)
					comboBox.setBackground(table.getSelectionBackground());
				else
					comboBox.setBackground(table.getBackground());
				comboBox.setSelectedItem(value);
			} else {
				c = label;
				if (isSelected)
					label.setBackground(table.getSelectionBackground());
				else
					label.setBackground(table.getBackground());
				if (value == null) {
					label.setText("Nulo");
				} else {
					label.setText(value.toString());
				}
			}
			return c;
		}
	}

	class TableListEditor extends DefaultCellEditor {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public TableListEditor(String[] items) {
			super(new JComboBox(items));
		}
	}


	public static void rowFormato(String[] claves) {
		// TODO Auto-generated method stub
		System.out.println("Regreso" + claves[0].toString() + "  " + claves[1].toString());
		textField_2.setText(claves[0]);
		textField_3.setText(claves[1]);
		textField_4.setText(claves[2]);
		textField_5.setText(claves[3]);
		textField_6.setText(claves[4]);
		textField_2.setEnabled(true);
		textField_3.setEnabled(true);
		textField_4.setEnabled(true);
		textField_5.setEnabled(true);
		textField_6.setEnabled(true);
		btnCrearFrmTbl.setVisible(true);
		btnCrearFormato.setVisible(true);
	}

	protected String ejecutaQueryGuardar2(String sqlstr_Input, String Tipo) {
		conn = ConectarDBPostgres.main(null);
		if (Tipo.trim() == "S") {
			try {
				System.out.println("Entradas: " + sqlstr_Input + " " + Tipo);
				PreparedStatement pat_vali = conn.prepareStatement(sqlstr_Input);
				ResultSet rs_vali = pat_vali.executeQuery();
				rs_vali.next();
				System.out.println("Consulta: " + rs_vali.getString(1));
				conn.close();
				return rs_vali.getString(1);
			} catch (SQLException e1) {
				System.out.println("Error en la expresion de consulta: " + e1.getMessage());
				JOptionPane.showMessageDialog(null, "Error en la expresion: " + e1.getMessage(),
						"Validacion Incorrecta ", JOptionPane.ERROR_MESSAGE);
				return "1";
			}
		}
		if (Tipo.trim() == "I") {
			try {
				PreparedStatement pat_vali = conn.prepareStatement(sqlstr_Input);
				pat_vali.executeUpdate();
				System.out.println("Insert ejecutado: " + sqlstr_Input);
				conn.close();
				return "0";
			} catch (SQLException e1) {
				if (e1.getErrorCode() != 0) {
					System.out.println(
							"Error en la expresion de Insercion: " + e1.getMessage() + " Codigo: " + e1.getErrorCode());
					JOptionPane.showMessageDialog(null,
							"Error en la expresion, No se pudo insertar: " + e1.getMessage(), "Validacion Incorrecta ",
							JOptionPane.ERROR_MESSAGE);
					return "1";
				}
				return "1";
			}
		}
		if (Tipo.trim() == "T") {
			try {
				System.out.println("Entradas: " + sqlstr_Input + " " + Tipo);
				PreparedStatement pat_vali = conn.prepareStatement(sqlstr_Input);
				ResultSet rs_vali = pat_vali.executeQuery();
				while (rs_vali.next()) {
					if (rs_vali.getObject(1).toString().trim().equals("1")) {
						JOptionPane.showMessageDialog(null, " Ya existe ese registro.", "No se puede crear ",
								JOptionPane.ERROR_MESSAGE);
						return "1";
					}
					return "0";
				}
				conn.close();
			} catch (SQLException e1) {
				System.out.println("Error en la expresion de consulta: " + e1.getMessage());
				JOptionPane.showMessageDialog(null, "Error en la expresion: " + e1.getMessage(),
						"Validacion Incorrecta ", JOptionPane.ERROR_MESSAGE);
				return "1";
			}
		}
		if (Tipo.trim() == "C") {
			try {
				PreparedStatement pat_vali = conn.prepareStatement(sqlstr_Input);
				pat_vali.executeUpdate();
				System.out.println("Creacion ejecutado.");
				conn.close();
				return "0";
			} catch (SQLException e1) {
				if (e1.getErrorCode() != 0) {
					System.out.println(
							"Error en la expresion de creacion: " + e1.getMessage() + " Codigo: " + e1.getErrorCode());
					JOptionPane.showMessageDialog(null, "Error en la expresion, No se pudo crear: " + e1.getMessage(),
							"Validacion Incorrecta ", JOptionPane.ERROR_MESSAGE);
					return "1";
				}
				return "1";
			}
		}
		return null;
	}

}
