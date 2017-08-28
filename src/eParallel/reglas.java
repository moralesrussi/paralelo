package eParallel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.EtchedBorder;
import org.json.*;
import de.javasoft.plaf.synthetica.*;
import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;



@SuppressWarnings({ "unused", "serial" })
public class reglas extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	private JTable table_2;
	private JTable table_3;
	// private JTable table_4;
	Connection conn = null;
	static Boolean vali_A, vali_B;
	static String tablaConsulta, tablaConsulta2, tablaA, tablaB, sqlstr_tabla, camposTodos;
	private final DefaultTableModel tableModel = new DefaultTableModel();
	private final DefaultTableModel tableModel_1 = new DefaultTableModel();
	private final DefaultTableModel tableModel_2 = new DefaultTableModel();
	private final DefaultTableModel tableModel_3 = new DefaultTableModel();
	@SuppressWarnings("rawtypes")
	private final DefaultListModel listModel_1 = new DefaultListModel();
	@SuppressWarnings("rawtypes")
	private final DefaultListModel listModel_2 = new DefaultListModel();
	// private final DefaultTableModel tableModel_4 = new DefaultTableModel();
	private JTextField textField_Rule;
	@SuppressWarnings("rawtypes")
	private JList list_2;
	@SuppressWarnings("rawtypes")
	private JList list_3;
	
	
	// JList list_2 = new JList();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try { UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel");} 
					catch (Exception e){  e.printStackTrace();}
					reglas frame = new reglas();
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public reglas() {
		setTitle("Reglas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 998, 619);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		vali_B = false;
		vali_A = false;
		tableModel_2.setRowCount(0);
		camposTodos = null;

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Lado A", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(34, 11, 382, 108);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 16, 356, 81);
		panel.add(scrollPane);

		// LADO A
		table = new JTable();
		scrollPane.setViewportView(table);
		conn = ConectarDBPostgres.main(null);
		// String sqlstr = "SELECT tablename as Nombre FROM pg_tables WHERE
		// schemaname = 'public'";
		String sqlstr = "SELECT nombre FROM app.app_tipo_archivos";
		try {
			PreparedStatement pat = conn.prepareStatement(sqlstr);
			ResultSet rs = pat.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			// Names of columns
			Vector<String> columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				columnNames.add(metaData.getColumnName(i));
				System.out.println("Columna Formatos: " + metaData.getColumnName(i));
			}

			// Data of the table
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			while (rs.next()) {
				Vector<Object> vector = new Vector<Object>();
				for (int i = 1; i <= columnCount; i++) {
					vector.add(rs.getObject(i));
				}
				data.add(vector);
			}
			tableModel.setDataVector(data, columnNames);
			table.setModel(tableModel);

		} catch (SQLException e) {
			System.out.println("Exception in Load Data: " + e);
			e.printStackTrace();
		}

		// LABO B _1
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Lado B", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(560, 11, 393, 108);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 16, 373, 81);
		panel_1.add(scrollPane_1);

		table_1 = new JTable(); // Resuso query lado A
		scrollPane_1.setViewportView(table_1);
		table_1.setModel(tableModel);

		// Lista Columnas lado A _2
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(
				new TitledBorder(null, "Columnas Lado A", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(34, 130, 382, 117);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 16, 362, 94);
		panel_2.add(scrollPane_2);

		table_2 = new JTable();
		scrollPane_2.setViewportView(table_2);
		table.addMouseListener(new MouseAdapter() { // listener sobre tabla base
													// LADO A
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() >= 1) {
					int column = 0;
					int row = table.getSelectedRow(); // Tabla base
					// if (row <= -1) {row = 0;}
					if (row <= -1)
						row = 0;

					tablaConsulta = table.getModel().getValueAt(row, column).toString();
					tableModel_2.setRowCount(0); // resetea modelo
					System.out.println("Nombre tabla Columnas: " + tablaConsulta);
					String sqlstr_2 = "SELECT columnas_tipo FROM app.app_tipo_archivos WHERE nombre='" + tablaConsulta
							+ "' LIMIT 1";
					String header[] = new String[] { "Nombre", "Tipo" }; // Asigna
																			// header
					tableModel_2.setColumnIdentifiers(header);
					try {
						PreparedStatement pat_2 = conn.prepareStatement(sqlstr_2);
						ResultSet rs_2 = pat_2.executeQuery();
						ResultSetMetaData metaData_2 = rs_2.getMetaData();
						int columnCount_2 = metaData_2.getColumnCount();
						rs_2.next();
						try {
							JSONObject json_columnas = new JSONObject(rs_2.getString(1)); // Solo
																							// una
																							// linea
							for (int j = 0; j < json_columnas.getJSONArray("tabla").length(); j++) {
								JSONObject res = json_columnas.getJSONArray("tabla").getJSONObject(j);
								tableModel_2.addRow(new Object[] { res.get("column_name"), res.get("column_type") });
								System.out.println(
										"Valores: " + res.getString("column_name") + res.getString("column_type"));
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						table_2.setModel(tableModel_2);
					} catch (SQLException e) {
						System.out.println("Exception in Load Data: " + e);
						e.printStackTrace();
					}

					// Trae claves de formato 1
					String sqlstr_claveA = "SELECT * FROM app.app_tipo_archivos WHERE nombre='" + tablaConsulta
							+ "' LIMIT 1";
					listModel_1.removeAllElements();
					try {
						PreparedStatement pat_claveA = conn.prepareStatement(sqlstr_claveA);
						ResultSet rs_claveA = pat_claveA.executeQuery();
						rs_claveA.next();
						String clave_1A = rs_claveA.getString(5);// rs_claveA.getString("clave_1");
						String clave_2A = rs_claveA.getString("clave_2");
						String clave_3A = rs_claveA.getString("clave_3");
						String clave_4A = rs_claveA.getString("clave_4");
						String clave_5A = rs_claveA.getString("clave_5");
						String clave_6A = rs_claveA.getString("clave_6");
						listModel_1.addElement("Clave 1: " + clave_1A);
						listModel_1.addElement("Clave 2: " + clave_2A);
						listModel_1.addElement("Clave 3: " + clave_3A);
						listModel_1.addElement("Clave 4: " + clave_4A);
						listModel_1.addElement("Clave 5: " + clave_5A);
						listModel_1.addElement("Clave 6: " + clave_6A);
					} catch (SQLException e) {
						System.out.println("Exception in Load Data: " + e);
						e.printStackTrace();
					}

				}
			}
		});

		// Lista Columnas lado B
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(
				new TitledBorder(null, "Columnas Lado B", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(560, 130, 393, 117);
		contentPane.add(panel_3);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 16, 373, 94);
		panel_3.add(scrollPane_3);
		table_3 = new JTable();
		scrollPane_3.setViewportView(table_3);

		table_1.addMouseListener(new MouseAdapter() { // listener sobre tabla
														// base LADO B
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() >= 1) {
					int column = 0;
					int row = table_1.getSelectedRow(); // Tabla base
					// if (row <= -1) {row = 0;}
					if (row <= -1)
						row = 0;
					tablaConsulta2 = table_1.getModel().getValueAt(row, column).toString();
					System.out.println("Nombre tabla Columnas: " + tablaConsulta2);
					tableModel_3.setRowCount(0); // resetea modelo
					System.out.println("Nombre tabla Columnas: " + tablaConsulta);
					String sqlstr_3 = "SELECT columnas_tipo FROM app.app_tipo_archivos WHERE nombre='" + tablaConsulta2
							+ "' LIMIT 1";
					String header_3[] = new String[] { "Nombre", "Tipo" }; // Asigna
																			// header
					tableModel_3.setColumnIdentifiers(header_3);
					try {
						PreparedStatement pat_3 = conn.prepareStatement(sqlstr_3);
						ResultSet rs_3 = pat_3.executeQuery();
						ResultSetMetaData metaData_3 = rs_3.getMetaData();
						int columnCount_3 = metaData_3.getColumnCount();
						rs_3.next();
						try {
							JSONObject json_columnas_3 = new JSONObject(rs_3.getString(1)); // Solo
																							// una
																							// linea
							for (int j = 0; j < json_columnas_3.getJSONArray("tabla").length(); j++) {
								JSONObject res_3 = json_columnas_3.getJSONArray("tabla").getJSONObject(j);
								tableModel_3
										.addRow(new Object[] { res_3.get("column_name"), res_3.get("column_type") });
								System.out.println(
										"Valores: " + res_3.getString("column_name") + res_3.getString("column_type"));
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						table_3.setModel(tableModel_3);
					} catch (SQLException e) {
						System.out.println("Exception in Load Data: " + e);
						e.printStackTrace();
					}

					// Trae claves de formato 1
					String sqlstr_claveB = "SELECT * FROM app.app_tipo_archivos WHERE nombre='" + tablaConsulta2
							+ "' LIMIT 1";
					listModel_2.removeAllElements();
					try {
						PreparedStatement pat_claveB = conn.prepareStatement(sqlstr_claveB);
						ResultSet rs_claveB = pat_claveB.executeQuery();
						rs_claveB.next();
						String clave_1B = rs_claveB.getString(5);// rs_claveA.getString("clave_1");
						String clave_2B = rs_claveB.getString("clave_2");
						String clave_3B = rs_claveB.getString("clave_3");
						String clave_4B = rs_claveB.getString("clave_4");
						String clave_5B = rs_claveB.getString("clave_5");
						String clave_6B = rs_claveB.getString("clave_6");
						listModel_2.addElement("Clave 1: " + clave_1B);
						listModel_2.addElement("Clave 2: " + clave_2B);
						listModel_2.addElement("Clave 3: " + clave_3B);
						listModel_2.addElement("Clave 4: " + clave_4B);
						listModel_2.addElement("Clave 5: " + clave_5B);
						listModel_2.addElement("Clave 6: " + clave_6B);
					} catch (SQLException e) {
						System.out.println("Exception in Load Data: " + e);
						e.printStackTrace();
					}

				}
			}
		});

		// AREA DE CONSTRUCCION DE REGLA
		// Lado A
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(null, "Regla Lado A", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.setBounds(33, 324, 418, 47);
		contentPane.add(panel_6);
		panel_6.setLayout(null);

		JList list_1 = new JList();
		list_1.setBounds(29, 33, 367, -12);
		panel_6.add(list_1);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 16, 398, 20);
		panel_6.add(textArea);

		table_2.addMouseListener(new MouseAdapter() { // listener sobre tabla
														// base
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() >= 1) {
					panel.setEnabled(false);
					table.setEnabled(false);
					panel_6.setBorder(new TitledBorder(null, "Regla Lado A: " + tablaConsulta, TitledBorder.LEADING,
							TitledBorder.TOP, null, null));
					int column = 0;
					int row = table_2.getSelectedRow(); // Tabla base
					if (row <= -1)
						row = 0;
					String campo_1 = table_2.getModel().getValueAt(row, column).toString();
					if (camposTodos == null) {
						camposTodos = "formatoA." + campo_1;
					} else if (!campo_1.contains("formatoA." + campo_1)) {
						camposTodos = "formatoA." + campo_1 + "," + camposTodos;
					}
					System.out.println("Campo Seleccionado: " + campo_1);
					String previousText = textArea.getText();
					textArea.setText(previousText + " " + tablaConsulta + "." + campo_1 + " ");
				}
			}
		});

		JButton btnLimpiar_1 = new JButton("Limpiar");
		btnLimpiar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setEnabled(true);
				table.setEnabled(true);
				textArea.setText("");
				panel_6.setBorder(
						new TitledBorder(null, "Regla Lado A ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			}
		});
		btnLimpiar_1.setBounds(34, 382, 89, 23);
		contentPane.add(btnLimpiar_1);

		// Lado B
		JPanel panel_7 = new JPanel();
		panel_7.setLayout(null);
		panel_7.setBorder(new TitledBorder(null, "Regla Lado B", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_7.setBounds(529, 324, 424, 47);
		contentPane.add(panel_7);

		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(10, 16, 404, 20);
		panel_7.add(textArea_1);
		table_3.addMouseListener(new MouseAdapter() { // listener sobre tabla
														// base
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() >= 1) {
					panel_1.setEnabled(false);
					table_1.setEnabled(false);
					panel_7.setBorder(new TitledBorder(null, "Regla Lado B: " + tablaConsulta2, TitledBorder.LEADING,
							TitledBorder.TOP, null, null));
					int column = 0;
					int row = table_3.getSelectedRow(); // Tabla base
					if (row <= -1)
						row = 0;
					String campo_2 = table_3.getModel().getValueAt(row, column).toString();
					System.out.println("Campo Seleccionado2: " + campo_2);
					if (camposTodos == null) {
						camposTodos = "formatoB." + campo_2;
					} else if (!campo_2.contains("formatoB." + campo_2)) {
						camposTodos = "formatoB." + campo_2 + "," + camposTodos;
					}
					String previousText_2 = textArea_1.getText();
					textArea_1.setText(previousText_2 + " " + tablaConsulta2 + "." + campo_2 + " ");
				}
			}
		});

		// boton limpiar lado B
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel_1.setEnabled(true);
				table_1.setEnabled(true);
				textArea_1.setText("");
				panel_7.setBorder(
						new TitledBorder(null, "Regla Lado B", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			}
		});
		btnLimpiar.setBounds(864, 382, 89, 23);
		contentPane.add(btnLimpiar);

		// Boton Validar lado A
		JButton btnValidarSentencia = new JButton("Validar Sentencia");
		btnValidarSentencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String preliminarSQL = textArea.getText();
				String sqlstr_vali = "SELECT " + preliminarSQL + "FROM " + tablaConsulta + " LIMIT 1";
				vali_A = false;
				if (validateSQL(sqlstr_vali)) {
					JOptionPane.showMessageDialog(null, "Validacion OK!!!", "Mensaje ",
							JOptionPane.INFORMATION_MESSAGE);
					vali_A = true;
					// panel_6.setBackground(Color.red);
				} else {
					vali_A = false;
				}
			}
		});
		btnValidarSentencia.setBounds(133, 382, 139, 23);
		contentPane.add(btnValidarSentencia);

		// Boton Validar lado B
		JButton button = new JButton("Validar Sentencia");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String preliminarSQL_2 = textArea_1.getText();
				String sqlstr_vali = "SELECT " + preliminarSQL_2 + "FROM " + tablaConsulta2 + " LIMIT 1";
				vali_B = false;
				if (validateSQL(sqlstr_vali)) {
					JOptionPane.showMessageDialog(null, "Validacion OK!!!", "Mensaje ",
							JOptionPane.INFORMATION_MESSAGE);
					vali_B = true;
				} else {
					vali_B = false;
				}
			}
		});
		button.setBounds(717, 382, 139, 23);
		contentPane.add(button);

		// Lista de operadores
		final DefaultListModel listModel = new DefaultListModel();
		listModel.addElement("=");
		listModel.addElement(">");
		listModel.addElement("<");
		listModel.addElement(">=");
		listModel.addElement("<=");
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(473, 324, 36, 59);
		contentPane.add(scrollPane_4);
		JList list = new JList(listModel);
		scrollPane_4.setViewportView(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.setVisibleRowCount(3);

		// Regla Final
		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(19, 483, 934, 50);
		contentPane.add(scrollPane_5);
		textField_Rule = new JTextField();
		scrollPane_5.setViewportView(textField_Rule);
		textField_Rule.setColumns(10);

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (vali_B && vali_A) {
					JOptionPane.showMessageDialog(null, "Perfect", "Mensaje ", JOptionPane.INFORMATION_MESSAGE);
					if (textField_Rule.getText() != "") { // Si ya existe una
															// regla
						textField_Rule.setText(textField_Rule.getText() + " (" + textArea.getText() + " "
								+ list.getSelectedValue() + " " + textArea_1.getText() + ")");

					} else { // Si es primera regla
						textField_Rule.setText(
								textArea.getText() + " " + list.getSelectedValue() + " " + textArea_1.getText());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Por favor validar Reglas", "Mensaje ",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnAdicionar.setBounds(170, 454, 89, 23);
		contentPane.add(btnAdicionar);

		// Guardar
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String formatoA_final = tablaConsulta;
				String formatoB_final = tablaConsulta2;
				if (!list_2.isSelectionEmpty() && !list_3.isSelectionEmpty()) {
					String claveA_final = list_2.getSelectedValue().toString();
					String claveB_final = list_3.getSelectedValue().toString();
					String condicion_final = textField_Rule.getText();//
					System.out.println("Datos a guardar: " + formatoA_final + " -  " + formatoB_final + " -  "
							+ claveA_final + " -  " + claveB_final + " - " + camposTodos);
					guardaRegla guardaRegla = new guardaRegla(formatoA_final, claveA_final, formatoB_final,
							claveB_final, condicion_final, camposTodos);
					guardaRegla.setVisible(true);
					// guardaRegla(formatoA_final, formatoB_final);
					// , claveA_final, claveB_final
				} else {
					JOptionPane.showMessageDialog(null, "Por favor seleccione una clave en los dos formatos",
							"Mensaje ", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnGuardar.setBounds(446, 544, 89, 23);
		contentPane.add(btnGuardar);

		// Boton Cerrar
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCerrar.setBounds(864, 544, 89, 23);
		contentPane.add(btnCerrar);

		JButton btnAdicionarO = new JButton("Adicionar O");
		btnAdicionarO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_Rule.getText().trim() != null) {
					String textField_Rule_Final = (textField_Rule.getText() + " OR ");
					textField_Rule.setText(textField_Rule_Final);
					// Limpia reglas lado A y lado B
					panel_1.setEnabled(true);
					table_1.setEnabled(true);
					textArea_1.setText("");
					panel_7.setBorder(
							new TitledBorder(null, "Regla Lado B", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					panel.setEnabled(true);
					table.setEnabled(true);
					textArea.setText("");
					panel_6.setBorder(new TitledBorder(null, "Regla Lado A ", TitledBorder.LEADING, TitledBorder.TOP,
							null, null));
					vali_B = false;
					vali_B = false;
					// btnAdicionarY.setEnabled(false);
				}
			}
		});
		btnAdicionarO.setBounds(388, 454, 109, 23);
		contentPane.add(btnAdicionarO);

		// Valida dos Sentencias
		JButton btnValidarSentencias = new JButton("Validar Sentencias");
		btnValidarSentencias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((textArea.getText().trim() != "") && (textArea_1.getText().trim() != "")) {
					sqlstr_tabla = creaQuery(tablaConsulta);
					tablaA = ejecutaQuery(sqlstr_tabla);
					// String preliminarSQL
					// =preliminarSQL_0.replace(tablaConsulta, tablaA);
					String preliminarSQL = textArea.getText().replace(tablaConsulta, tablaA);
					// System.out.println("Tabla A: " + tablaA +" Original: "+
					// textArea.getText() +" Preliminary Query: " +
					// preliminarSQL);
					sqlstr_tabla = creaQuery(tablaConsulta2);
					tablaB = ejecutaQuery(sqlstr_tabla);
					String preliminarSQL_2 = textArea_1.getText().replace(tablaConsulta2, tablaB);
					// System.out.println("Tabla B: " + tablaB +" Original: "+
					// textArea_1.getText() +" Preliminary Query: " +
					// preliminarSQL_2);

					if (tablaA != null && tablaB != null) { // Existen al menos
															// un archivo para
															// ese formato
						String sqlstr_vali = "SELECT " + preliminarSQL + "FROM " + tablaA + " LIMIT 1";
						String sqlstr_vali_2 = "SELECT " + preliminarSQL_2 + "FROM " + tablaB + " LIMIT 1";
						vali_B = false;
						vali_A = true;
						if (validateSQL(sqlstr_vali) && validateSQL(sqlstr_vali_2)) {
							JOptionPane.showMessageDialog(null, "Validacion OK!!!", "Mensaje ",
									JOptionPane.INFORMATION_MESSAGE);
							vali_B = true;
							vali_A = true;
						} else {
							vali_B = false;
							vali_B = false;
						}
					} else {
						JOptionPane.showMessageDialog(null, "Validacion Fallida!!!", "Mensaje ",
								JOptionPane.INFORMATION_MESSAGE);
					}

				} else {
					JOptionPane.showMessageDialog(null, "Falta una Expresion!!!", "Mensaje ",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnValidarSentencias.setBounds(16, 454, 144, 23);
		contentPane.add(btnValidarSentencias);

		// Adicionar Y
		final JButton btnAdicionarY = new JButton("Adicionar Y");
		btnAdicionarY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_Rule.getText().trim() != null) {
					String textField_Rule_Final = (textField_Rule.getText() + " AND ");
					textField_Rule.setText(textField_Rule_Final);
					// Limpia reglas lado A y lado B
					panel_1.setEnabled(true);
					table_1.setEnabled(true);
					textArea_1.setText("");
					panel_7.setBorder(
							new TitledBorder(null, "Regla Lado B", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					panel.setEnabled(true);
					table.setEnabled(true);
					textArea.setText("");
					panel_6.setBorder(new TitledBorder(null, "Regla Lado A ", TitledBorder.LEADING, TitledBorder.TOP,
							null, null));
					vali_B = false;
					vali_B = false;
					// btnAdicionarY.setEnabled(false);
				}
			}
		});
		btnAdicionarY.setBounds(269, 454, 109, 23);
		contentPane.add(btnAdicionarY);

		// Limpia y reinicia lados A y B
		JButton btnLimpiar_2 = new JButton("Limpiar Todo");
		btnLimpiar_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_Rule.setText("");
				panel_1.setEnabled(true);
				table_1.setEnabled(true);
				textArea_1.setText("");
				panel_7.setBorder(
						new TitledBorder(null, "Regla Lado B", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel.setEnabled(true);
				table.setEnabled(true);
				textArea.setText("");
				panel_6.setBorder(
						new TitledBorder(null, "Regla Lado A ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				vali_B = false;
				vali_B = false;
			}
		});
		btnLimpiar_2.setBounds(784, 449, 120, 23);
		contentPane.add(btnLimpiar_2);

		JLabel label = new JLabel(" =");
		label.setFont(new Font("Tahoma", Font.BOLD, 20));
		label.setBounds(473, 275, 36, 14);
		contentPane.add(label);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_4.setBounds(19, 0, 939, 249);
		contentPane.add(panel_4);

		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_8.setBounds(19, 319, 939, 91);
		contentPane.add(panel_8);

		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_11.setBounds(10, 433, 949, 148);
		contentPane.add(panel_11);

		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "Claves Lado A", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setBounds(19, 250, 394, 62);
		contentPane.add(panel_5);
		panel_5.setLayout(null);

		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setBounds(6, 15, 382, 35);
		panel_5.add(scrollPane_6);

		list_2 = new JList();
		list_2.setVisibleRowCount(6);
		list_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_6.setViewportView(list_2);
		list_2.setModel(listModel_1);

		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new TitledBorder(null, "Claves Lado B", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_9.setBounds(560, 250, 394, 62);
		contentPane.add(panel_9);
		panel_9.setLayout(null);

		JScrollPane scrollPane_7 = new JScrollPane();
		scrollPane_7.setBounds(6, 15, 382, 35);
		panel_9.add(scrollPane_7);

		list_3 = new JList();
		list_3.setVisibleRowCount(6);
		list_3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_7.setViewportView(list_3);
		list_3.setModel(listModel_2);

		// setIconImage(new
		// ImageIcon(getClass().getResource("resources\\eparallel.ico"),
		// "eParallel Bill 2 Bill").getImage());

	}

	// ZONA DE METODOS LOCALES

	protected String creaQuery(String formatoBusqueda) {
		String sqlstr_tabla = ("SELECT t2.tabla FROM " + " app.app_archivo_tabla t2," + " app.app_archivos t1, "
				+ " app.app_tipo_archivo_archivo t3," + " app.app_tipo_archivos t4" + " WHERE t1.id=t2.id_archivo"
				+ " AND t3.id_archivo=t1.id" + " AND t4.id=t3.id_archivo_tipo" + " AND t4.nombre='" + formatoBusqueda
				+ "'");
		return sqlstr_tabla;
	}

	protected String ejecutaQuery(String sqlstr_Input) {
		try {
			PreparedStatement pat_vali = conn.prepareStatement(sqlstr_Input);
			ResultSet rs_vali = pat_vali.executeQuery();
			rs_vali.next();
			return rs_vali.getString(1);
		} catch (SQLException e1) {
			System.out.println("Error en la expresion: " + e1.getMessage());
			JOptionPane.showMessageDialog(null, "Error en la expresion: " + e1.getMessage(), "Validacion Incorrecta ",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	protected boolean validateSQL(String sqlstr_vali) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement pat_vali = conn.prepareStatement(sqlstr_vali);
			// ResultSet rs_vali =
			pat_vali.executeQuery();
			return true;
		} catch (SQLException e1) {
			System.out.println("Exception in Load Data: " + e1);
			JOptionPane.showMessageDialog(null, "Error en la expresion: \n" + e1.getMessage(), "Validacion...",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	public class Entity {
		// field/property names must match to your json keys (with some
		// exceptions)
		// other wise we are required to use further annotations
		private String column_name;
		private String column_type;

		public String getColumnName() {
			return column_name;
		}

		public String getColumnType() {
			return column_type;
		}

		public void setColumnName(String column_name) {
			this.column_name = column_name;
		}

		public void setColumnType(String column_type) {
			this.column_type = column_type;
		}
	}

}
