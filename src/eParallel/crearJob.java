package eParallel;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;

import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextField;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JSeparator;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import de.javasoft.plaf.synthetica.*;
import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;

@SuppressWarnings({ "serial", "unused" })
public class crearJob extends JFrame {

	private JPanel contentPane;
	Connection conn = null;
	private javax.swing.JTree tree;
	private JTextField textField;
	private JTextField textField_1;
	private JList<?> list;
	private JList<?> list_1;
	private JPanel panel;
	private JPanel panel_2;
	private String formatoA, formatoB, archivoA, archivoB, tablaA, tablaB, valToInserta;
	@SuppressWarnings("rawtypes")
	private final DefaultListModel listModel = new DefaultListModel();
	@SuppressWarnings("rawtypes")
	private final DefaultListModel listModel_1 = new DefaultListModel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try { UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel");} 
					catch (Exception e){  e.printStackTrace();}
					crearJob frame = new crearJob();
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
	@SuppressWarnings("unchecked")
	public crearJob() {
		conn = ConectarDBPostgres.main(null);
		setTitle("Crear Job");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1032, 469);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(
				new TitledBorder(null, "Formatos Disponibles", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(84, 46, 327, 114);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 16, 315, 91);
		panel_1.add(scrollPane_1);
		tree = new JTree();
		scrollPane_1.setViewportView(tree);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(520, 70, 307, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.getDocument().addDocumentListener(new MyDocumentListener2());

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(520, 118, 307, 20);
		contentPane.add(textField_1);
		textField_1.getDocument().addDocumentListener(new MyDocumentListener2());

		JLabel lblArchivoA = DefaultComponentFactory.getInstance().createLabel("Archivo A:");
		lblArchivoA.setBounds(443, 73, 60, 14);
		contentPane.add(lblArchivoA);

		JLabel lblArchivoB = DefaultComponentFactory.getInstance().createLabel("Archivo B:");
		lblArchivoB.setBounds(443, 121, 49, 14);
		contentPane.add(lblArchivoB);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 171, 996, 2);
		contentPane.add(separator);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Reglas asociadas a los archivos seleccionados", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(78, 189, 330, 172);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 16, 318, 149);
		panel.add(scrollPane);

		list = new JList<Object>();
		scrollPane.setViewportView(list);
		list.setEnabled(false);
		panel.setEnabled(false);

		list.addMouseListener(new MouseAdapter() { // listener sobre tabla base
													// LADO A
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 1) {
					if (!list.isSelectionEmpty()) {
						String valorSeleccionado = list.getSelectedValue().toString();
						// Add
						listModel_1.addElement(valorSeleccionado);

						// remove
						for (int i = 0; i < listModel.getSize(); i++) {
							// System.out.println("Tamaño lista " +
							// listModel.size());
							String valor = listModel.getElementAt(i).toString();
							if (valor == valorSeleccionado) {
								listModel.removeElement(listModel.getElementAt(i));
							}
						}
					}
				}
			}
		});

		panel_2 = new JPanel();
		panel_2.setBorder(
				new TitledBorder(null, "Reglas Seleccionadas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(509, 189, 330, 172);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(6, 16, 318, 149); // 6, 16, 318, 149 -10, 11,
													// 306, 141
		panel_2.add(scrollPane_2);

		list_1 = new JList<Object>();
		scrollPane_2.setViewportView(list_1);
		list_1.setEnabled(false);
		if (!listModel_1.isEmpty())
			list_1.setModel(listModel_1);
		list_1.setModel(listModel_1);
		list_1.setEnabled(false);
		panel_2.setEnabled(false);

		list_1.addMouseListener(new MouseAdapter() { // listener sobre tabla
														// base LADO A
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 1) {
					if (!list_1.isSelectionEmpty()) {
						String valorSeleccionado = list_1.getSelectedValue().toString();
						if (!valorSeleccionado.toString().equals("")) {
							// Add
							listModel.addElement(valorSeleccionado);

							// remove
							for (int i = 0; i < listModel_1.getSize(); i++) {
								System.out.println("Tamaño lista2 " + listModel_1.size());
								String valor = listModel_1.getElementAt(i).toString();
								if (valor == valorSeleccionado) {
									listModel_1.removeElement(listModel_1.getElementAt(i));
								}
							}
						}
					}
				}
			}
		});

		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("");
				list.setEnabled(false);
				panel.setEnabled(false);
				listModel.removeAllElements();
				listModel_1.removeAllElements();
			}
		});
		btnLimpiar.setBounds(849, 69, 89, 23);
		contentPane.add(btnLimpiar);

		JButton button = new JButton("Limpiar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_1.setText("");
				list.setEnabled(false);
				panel.setEnabled(false);
				listModel.removeAllElements();
				listModel_1.removeAllElements();
			}
		});
		button.setBounds(849, 117, 89, 23);
		contentPane.add(button);

		JButton btnTodas = new JButton("Todas =>");
		btnTodas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnTodas.setBounds(410, 244, 89, 23);
		contentPane.add(btnTodas);

		JButton btnGrabar = new JButton("Grabar");
		btnGrabar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Grabando.......");
				String sql = "SELECT NEXTVAL('app.app_jobs_id_seq')";
				try {
					String sequencia = "1";
					PreparedStatement pat = conn.prepareStatement(sql);
					ResultSet rs = pat.executeQuery();
					while (rs.next()) {
						sequencia = rs.getString(1);
						System.out.println("Secuencia: " + sequencia);
					}
					sql = "SELECT SETVAL('app.app_jobs_id_seq'," + sequencia + ",false)"; // Para
																							// conservar
																							// ultima
																							// secuencia
					pat = conn.prepareStatement(sql);
					rs = pat.executeQuery();
					sql = "SELECT t2.tabla FROM  app.app_archivo_tabla t2, app.app_archivos t1, app.app_tipo_archivo_archivo t3,app.app_tipo_archivos t4 WHERE t1.id=t2.id_archivo AND t3.id_archivo=t1.id AND t4.id=t3.id_archivo_tipo AND t1.nombre_archivo='"
							+ archivoA + "';";
					pat = conn.prepareStatement(sql);
					rs = pat.executeQuery();
					while (rs.next()) {
						tablaA = rs.getString(1);
					}
					sql = "SELECT t2.tabla FROM  app.app_archivo_tabla t2, app.app_archivos t1, app.app_tipo_archivo_archivo t3,app.app_tipo_archivos t4 WHERE t1.id=t2.id_archivo AND t3.id_archivo=t1.id AND t4.id=t3.id_archivo_tipo AND t1.nombre_archivo='"
							+ archivoB + "';";
					pat = conn.prepareStatement(sql);
					rs = pat.executeQuery();
					while (rs.next()) {
						tablaB = rs.getString(1);
					}
					// GRABAA JOB
					sql = "INSERT INTO app.app_jobs( tabla_1, tabla_2, fecha_hora, estado, descripcion) VALUES ('"
							+ tablaA + "', '" + tablaB + "', now(), 'inicial', 'Job a ejecutar posteriormente');";
					// pat = conn.prepareStatement(sql);
					Statement sat = conn.createStatement();
					sat.executeUpdate(sql);
					// sql = "SELECT
					// CURRVAL('app.app_jobs_id_seq',"+sequencia+",false)";
					// // Secuencia
					// pat = conn.prepareStatement(sql);
					// pat.executeQuery(sql);
					// while (rs.next()) {sequencia =rs.getString(1);}
					Integer listmodelsize = listModel_1.getSize();
					String idregla = "";
					valToInserta = "";
					for (int i = 0; i < listmodelsize; i++) {

						sql = "SELECT id FROM app.app_reglas WHERE nombre='" + listModel_1.getElementAt(i).toString()
								+ "';";
						System.out.println("Lista 1......." + listModel_1.getElementAt(i).toString() + " , tamaño"
								+ listmodelsize + " SQL: " + sql);
						pat = conn.prepareStatement(sql);
						rs = pat.executeQuery();
						while (rs.next()) {
							idregla = rs.getString(1);
							System.out.println("Id Regla: " + idregla);
						}
						// sql = "SELECT
						// CURRVAL('app.app_jobs_id_seq',"+sequencia+",false)";
						// // Secuencia
						sql = "SELECT max(id) FROM app.app_jobs;";
						pat = conn.prepareStatement(sql);
						rs = pat.executeQuery();
						while (rs.next()) {
							sequencia = rs.getString(1);
						}
						valToInserta = valToInserta + "('" + sequencia + "', '" + idregla + "','inicial'),";
					}
					sql = removeLastChar(
							"INSERT INTO app.app_jobs_reglas(id_job, id_rule, estado) VALUES " + valToInserta) + ";";
					System.out.println("SQL Insertar Job- Reglas: " + removeLastChar(sql) + ";");
					sat = conn.createStatement();
					sat.executeUpdate(sql);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					if (e1.getErrorCode() != 0) {
						e1.printStackTrace();
					} else {
						System.out.println("Error codigo 0.");
					}

				}
				textField.setText("");
				textField_1.setText("");
				archivoA = "";
				archivoB = "";
				listModel.removeAllElements();
				listModel_1.removeAllElements();

			}
		});
		btnGrabar.setBounds(849, 244, 117, 23);
		contentPane.add(btnGrabar);

		JButton btnGrabarYEjecutar = new JButton("Grabar y Ejecutar");
		btnGrabarYEjecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Grabando  y ejecutando.......");
				String sql = "SELECT NEXTVAL('app.app_jobs_id_seq')";
				try {
					String sequencia = "1";
					PreparedStatement pat = conn.prepareStatement(sql);
					ResultSet rs = pat.executeQuery();
					while (rs.next()) {
						sequencia = rs.getString(1);
						System.out.println("Secuencia: " + sequencia);
					}
					sql = "SELECT SETVAL('app.app_jobs_id_seq'," + sequencia + ",false);"; // Para
																							// conservar
																							// ultima
																							// secuencia
					System.out.println("Secuencia setead con SQL: "+sql);
					pat = conn.prepareStatement(sql);
					rs = pat.executeQuery();
					
					//trae la tabla donde estan los datos archivo A
					sql = "SELECT t2.tabla FROM  app.app_archivo_tabla t2, app.app_archivos t1, app.app_tipo_archivo_archivo t3,app.app_tipo_archivos t4 WHERE t1.id=t2.id_archivo AND t3.id_archivo=t1.id AND t4.id=t3.id_archivo_tipo AND t1.nombre_archivo='"
							+ archivoA + "';";
					pat = conn.prepareStatement(sql);
					rs = pat.executeQuery();
					while (rs.next()) {
						tablaA = rs.getString(1);
					}
					//trae la tabla donde estan los datos archivo B
					sql = "SELECT t2.tabla FROM  app.app_archivo_tabla t2, app.app_archivos t1, app.app_tipo_archivo_archivo t3,app.app_tipo_archivos t4 WHERE t1.id=t2.id_archivo AND t3.id_archivo=t1.id AND t4.id=t3.id_archivo_tipo AND t1.nombre_archivo='"
							+ archivoB + "';";
					pat = conn.prepareStatement(sql);
					rs = pat.executeQuery();
					while (rs.next()) {
						tablaB = rs.getString(1);
					}
					// GRABA JOB
					// GRABA PRIMERO JOB-REGLAS
					Integer listmodelsize = listModel_1.getSize();
					String idregla = "";
					valToInserta = "";
					for (int i = 0; i < listmodelsize; i++) {
						sql = "SELECT id FROM app.app_reglas WHERE nombre='" + listModel_1.getElementAt(i).toString()
								+ "';";
						System.out.println("Lista 1......." + listModel_1.getElementAt(i).toString() + " , tamaño"
								+ listmodelsize + " SQL: " + sql);
						pat = conn.prepareStatement(sql);
						rs = pat.executeQuery();
						while (rs.next()) {
							idregla = rs.getString(1);
							System.out.println("Id Regla: " + idregla);
						}
						// sql = "SELECT
						// CURRVAL('app.app_jobs_id_seq',"+sequencia+",false)";
						// // Secuencia
//						sql = "SELECT max(id) FROM app.app_jobs;";
//						pat = conn.prepareStatement(sql);
//						rs = pat.executeQuery();
//						
//							while (rs.next()) {
//								if(rs.getString(1) !=  null){
//									sequencia = String.valueOf((Integer.parseInt(rs.getString(1)) + 1)); // Suma una para la proxima secuencia a asignar
//								}else {
//									sequencia = "0";
//								}
//							}
//						
						valToInserta = valToInserta + "('" + sequencia + "', '" + idregla + "','inicial'),";
					}
					sql = removeLastChar(
							"INSERT INTO app.app_jobs_reglas(id_job, id_rule, estado) VALUES " + valToInserta) + ";";
					System.out.println("SQL Insertar Job- Reglas: " + removeLastChar(sql) + ";");
					pat = conn.prepareStatement(sql);
					pat.executeUpdate();
					// GRABA EFECTIVAMENTE EL JOB
					//PRIMERO GRABA JOB SIN FLAG DE EJECUTAR
					try {
						sql = "INSERT INTO app.app_jobs( tabla_1, tabla_2, fecha_hora, estado, descripcion) VALUES ('"
								+ tablaA + "', '" + tablaB + "', now(), 'Inicial', 'Job a ejecutar inmediatamente');";
						pat = conn.prepareStatement(sql);
						System.out.println("Insert to inicial  SQL: " + sql);
						pat.executeUpdate();
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
					//GRABA CON FLAG
					try {
						sql = "UPDATE app.app_jobs SET estado='ejecutar' WHERE id="+sequencia+";";
						pat = conn.prepareStatement(sql);
						System.out.println("Update to trigger  SQL: " + sql);
						pat.executeUpdate();
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
					;

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					if (e1.getErrorCode() != 0) {
						e1.printStackTrace();
					} else {
						System.out.println("Error codigo 0.");
					}

				}
				textField.setText("");
				textField_1.setText("");
				archivoA = "";
				archivoB = "";
				listModel.removeAllElements();
				listModel_1.removeAllElements();

			}
		});
		btnGrabarYEjecutar.setBounds(849, 325, 117, 23);
		contentPane.add(btnGrabarYEjecutar);

		pop_tree();
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				// System.out.println("Nivel de Seleccion: "
				if (selectedNode != null) {
					if (selectedNode.isLeaf()) {
						Object nodeInfo = selectedNode.getUserObject();
						System.out.println("Informacion de Seleccion: " + nodeInfo.toString());
						System.out.println("Nivel de Seleccion: " + selectedNode.getLevel());
						if (textField.getText().equals("")) {
							textField.setText(nodeInfo.toString());
							archivoA = textField.getText();
							formatoA = selectedNode.getParent().toString();
							System.out.println("Padre A: " + selectedNode.getParent().toString());
						} else if (textField_1.getText().equals("")) {
							textField_1.setText(nodeInfo.toString());
							archivoB = textField_1.getText();
							list.setEnabled(true);
							panel.setEnabled(true);
							list_1.setEnabled(true);
							panel_2.setEnabled(true);
							formatoB = selectedNode.getParent().toString();
							System.out.println("Padre B: " + selectedNode.getParent().toString());
							String sql = "SELECT id, nombre FROM app.app_reglas	WHERE formatoa in ('" + formatoA + "','"
									+ formatoB + "') AND formatob in ('" + formatoA + "','" + formatoB + "');";
							System.out.println("insertUpdate, sql: " + sql);
							try {
								PreparedStatement pat = conn.prepareStatement(sql);
								ResultSet rs = pat.executeQuery();
								while (rs.next()) {
									listModel.addElement(rs.getString("nombre")); // Adiciono
																					// formatos
																					// coincidentes
									list.setModel(listModel);
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						} else {
							System.out.println("No seleccion..." + textField.getText() + "." + textField_1.getText());
							listModel.removeAllElements();
						}

					}
				}
			}
		});

	}

	public final void pop_tree() {

		try {
			ArrayList<Object> list = new ArrayList<Object>();
			list.add("Lista Formatos");
			String sql = "SELECT id, nombre from app.app_tipo_archivos";
			PreparedStatement pat = conn.prepareStatement(sql);
			ResultSet rs = pat.executeQuery();
			while (rs.next()) {
				Object value[] = { rs.getString(1), rs.getString(2) };
				list.add(value);
			}
			Object hierarchy[] = list.toArray();
			DefaultMutableTreeNode root = processHierarchy(hierarchy);
			DefaultTreeModel treeModel = new DefaultTreeModel(root);
			tree.setModel(treeModel);
		} catch (Exception e) {
			System.out.println("Errorrrr: ");
			e.printStackTrace();
		}
	}

	public DefaultMutableTreeNode processHierarchy(Object[] hierarchy) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(hierarchy[0]);
		try {
			int ctrow = 0;
			int i = 0;
			try {
				// String sql = "SELECT catid, catname from category";
				String sql = "SELECT id, nombre from app.app_tipo_archivos";
				PreparedStatement pat = conn.prepareStatement(sql);
				ResultSet rs = pat.executeQuery();
				while (rs.next()) {
					ctrow = rs.getRow();
				}
				String L1Nam[] = new String[ctrow];
				String L1Id[] = new String[ctrow];
				System.out.println("Creando Jerarquia: " + ctrow);
				ResultSet rs1 = pat.executeQuery();
				while (rs1.next()) {
					L1Nam[i] = rs1.getString("nombre");
					L1Id[i] = rs1.getString("id");
					i++;
				}
				DefaultMutableTreeNode child, grandchild;
				for (int childIndex = 0; childIndex < L1Nam.length; childIndex++) {
					child = new DefaultMutableTreeNode(L1Nam[childIndex]);
					node.add(child);// add each created child to root
					String sql2 = "SELECT  t2.nombre_archivo FROM app.app_tipo_archivo_archivo  tu, app.app_tipo_archivos t1, app.app_archivos t2 WHERE tu.id_archivo_tipo= t1.id AND t2.id= tu.id_archivo AND id_archivo_tipo="
							+ L1Id[childIndex] + "ORDER BY 1;";
					PreparedStatement pat_2 = conn.prepareStatement(sql2);
					ResultSet rs3 = pat_2.executeQuery();
					while (rs3.next()) {
						grandchild = new DefaultMutableTreeNode(rs3.getString("nombre_archivo"));
						child.add(grandchild);// add each grandchild to each
												// child
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (Exception e) {
		}
		return (node);
	}

	private static String removeLastChar(String str) {
		return str.substring(0, str.length() - 1);
	}

	class MyDocumentListener2 implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent arg0) {
			// TODO Auto-generated method stub
			if (!textField.getText().equals("") && !textField_1.getText().equals("")) {
				list.setEnabled(true);
				panel.setEnabled(true);
				System.out.println("changedUpdate");
			}

		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			// TODO Auto-generated method stub
			if (!textField.getText().equals("") && !textField_1.getText().equals("")) {
				list.setEnabled(true);
				panel.setEnabled(true);

			}
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			// TODO Auto-generated method stub
			if (!textField.getText().equals("") && !textField_1.getText().equals("")) {
				list.setEnabled(false);
				panel.setEnabled(false);
				System.out.println("removeUpdate");
			}
		}

	}

}
