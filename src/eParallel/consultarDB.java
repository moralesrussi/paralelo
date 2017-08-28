package eParallel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JSplitPane;
import javax.swing.JList;

@SuppressWarnings({ "serial", "unused" })
public class consultarDB extends JFrame {

	private JPanel contentPane;
	Connection conn = null;
	private JTable table;
	static String tablaConsulta;
	private final DefaultTableModel tableModel = new DefaultTableModel();
	private final DefaultTableModel tableModel_2 = new DefaultTableModel();
	private JTable table_2;

	/**
	 * Launch the application.
	 */
	public static void main(String... aArgs) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					consultarDB frame = new consultarDB(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param tablaConsulta
	 */
	public consultarDB(String aArgs) {
		if (aArgs == null) {
			tablaConsulta = "TEMPORAL2";
		} else {
			tablaConsulta = aArgs;
		}
		URL iconURL = getClass().getResource("/eparallel.png");
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());

		setTitle("Consulta DB");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 840, 475);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tabla Consultada",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(309, 21, 505, 389);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 16, 493, 366);
		panel.add(scrollPane_1);

		table = new JTable();
		scrollPane_1.setViewportView(table);
		table.getTableHeader();
		table.setFillsViewportHeight(true);
		table.setAutoscrolls(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		panel.add(table.getTableHeader(), BorderLayout.NORTH);
		panel.setLayout(new BorderLayout());

		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				conn = ConectarDBPostgres.main(null);

				// Lista de tablas
				int column = 0;
				int row = table_2.getSelectedRow();
				// if (row <= -1) {row = 0;}
				if (row <= -1)
					row = 0;
				tablaConsulta = table_2.getModel().getValueAt(row, column).toString();

				String sqlstr = "SELECT * FROM " + tablaConsulta;

				try {
					PreparedStatement pat = conn.prepareStatement(sqlstr);
					ResultSet rs = pat.executeQuery();
					ResultSetMetaData metaData = rs.getMetaData();

					// Names of columns
					Vector<String> columnNames = new Vector<String>();
					int columnCount = metaData.getColumnCount();
					for (int i = 1; i <= columnCount; i++) {
						columnNames.add(metaData.getColumnName(i));
						System.out.println("Columna: " + metaData.getColumnName(i));
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
					// JTable table = new JTable(tableModel);
					table.setModel(tableModel);
					panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
							"Tabla Consultada " + tablaConsulta, TitledBorder.LEADING, TitledBorder.TOP, null,
							new Color(0, 0, 0)));

					// table.setTableHeader(null);

				} catch (SQLException e) {
					System.out.println("Exception in Load Data: " + e);
					e.printStackTrace();
				}
			}

		});
		btnConsultar.setBounds(49, 387, 89, 23);
		contentPane.add(btnConsultar);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCerrar.setBounds(172, 387, 89, 23);
		contentPane.add(btnCerrar);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(
				new TitledBorder(null, "Lista de Tablas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 21, 273, 355);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 16, 257, 328);
		panel_1.add(scrollPane);

		table_2 = new JTable();
		scrollPane.setViewportView(table_2);
		conn = ConectarDBPostgres.main(null);
		String sqlstr = "SELECT tablename FROM pg_tables WHERE schemaname = 'public'";
		try {
			PreparedStatement pat = conn.prepareStatement(sqlstr);
			ResultSet rs = pat.executeQuery();
			ResultSetMetaData metaData_2 = rs.getMetaData();
			// Names of columns
			Vector<String> columnNames_2 = new Vector<String>();
			int columnCount = metaData_2.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				columnNames_2.add(metaData_2.getColumnName(i));
				System.out.println("Columna Tablas: " + metaData_2.getColumnName(i));
			}

			// Data of the table
			Vector<Vector<Object>> data_2 = new Vector<Vector<Object>>();
			while (rs.next()) {
				Vector<Object> vector_2 = new Vector<Object>();
				for (int i = 1; i <= columnCount; i++) {
					vector_2.add(rs.getObject(i));
				}
				data_2.add(vector_2);
			}
			tableModel_2.setDataVector(data_2, columnNames_2);
			table_2.setModel(tableModel_2);

		} catch (SQLException e) {
			System.out.println("Exception in Load Data: " + e);
			e.printStackTrace();
		}

	}
}
