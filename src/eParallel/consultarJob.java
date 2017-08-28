package eParallel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.JScrollPane;
import de.javasoft.plaf.synthetica.*;
import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;
import eParallel.cargaArchivo.TableListEditor;
import eParallel.cargaArchivo.TableListRenderer;

import java.awt.Font;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

@SuppressWarnings({ "serial", "unused" })
public class consultarJob extends JFrame {

	private JPanel contentPane;
	private JTable table;
	Connection conn = null;
	static String tablaConsulta, tablaConsulta2, tablaA, tablaB, sqlstr_tabla, camposTodos, estadoEjecucion;
	private String[] reglas;
	private final DefaultTableModel tableModel = new DefaultTableModel(){
		@Override
		public boolean isCellEditable(int row, int column) {
			// segunda fila en adelante
			return row < 1;
		}
	};
	ImageIcon backIcon = getImage("/blue-circle.png");
	public ResultSet rs = null;
	public String jobConsulta, headerReport, tailorReport, reporteSummary, reporteDetail, numeroReglas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try { UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel");} 
					catch (Exception e){  e.printStackTrace();}
					consultarJob frame = new consultarJob();
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
	public consultarJob() {
		setTitle("Jobs");
		conn = ConectarDBPostgres.main(null); //Conector DB
		URL iconURL = getClass().getResource("/eparallel.png");
		ImageIcon icon = new ImageIcon(iconURL);
		this.setIconImage(icon.getImage());
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1055, 564);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//ResultSet rs;
		String sql_query="SELECT * FROM app.app_jobs;";
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Jobs", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(37, 38, 477, 340);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 16, 464, 317);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		table.setColumnSelectionAllowed(true);
		scrollPane.setViewportView(table);
		
		table.setAutoCreateRowSorter(true);
		
		JButton btnRefrescar = new JButton("Refrescar");
		btnRefrescar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ResultSet rs_1 = ejecutaQuery(sql_query,"S");
				if (rs_1 != null){
					try {
						
						ResultSetMetaData metaData = rs_1.getMetaData();
						// Names of columns
						Vector<String> columnNames = new Vector<String>();
						int columnCount = metaData.getColumnCount();
						for (int i = 1; i <= columnCount; i++) {
							columnNames.add(metaData.getColumnName(i));
							System.out.println("Columna Formatos: " + metaData.getColumnName(i));
						}

						// Data of the table
						Vector<Vector<Object>> data = new Vector<Vector<Object>>();
						while (rs_1.next()) {
							Vector<Object> vector = new Vector<Object>();
							for (int i = 1; i <= columnCount; i++) {
								vector.add(rs_1.getObject(i));
								if(i == 5) {estadoEjecucion = rs_1.getObject(i).toString();};
							}
							data.add(vector);
						}
						tableModel.setDataVector(data, columnNames);
						//table.setDefaultRenderer(JLabel.class,  new Renderer());
						table.setModel(tableModel);
						table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
						TableColumnModel colModel=table.getColumnModel();
						colModel.getColumn(0).setPreferredWidth(30);    
						colModel.getColumn(4).setPreferredWidth(100);
						colModel.getColumn(5).setPreferredWidth(220);
//						TableColumnModel colModel = table.getColumnModel();
//						Integer numColumns = table.getColumnCount();
//						for (int j = 0; j < numColumns; j++) {
//							TableColumn col = colModel.getColumn(j);
//							col.setCellRenderer(new TableListRenderer("ok") );
//							//col.setCellEditor(new TableListEditor("ok"));
//						}

					} catch (SQLException e) {
						System.out.println("Exception in Load Data: " + e);
						e.printStackTrace();
					}
					
					
				}
			}
		});
		btnRefrescar.setBounds(239, 384, 89, 23);
		contentPane.add(btnRefrescar);
		
		
		rs =ejecutaQuery(sql_query,"S");
		if (rs != null){
			try {
				
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
						
						if(columnCount == 2){
							vector.add(new JLabel("ICONO"));
						} else{
							vector.add(rs.getObject(i));
						}
						if(i == 5) {estadoEjecucion = rs.getObject(i).toString();};
					}
					data.add(vector);
				}
				tableModel.setDataVector(data, columnNames);
				table.setModel(tableModel);
				table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
				TableColumnModel colModel=table.getColumnModel();
				colModel.getColumn(0).setPreferredWidth(30);    
				colModel.getColumn(4).setPreferredWidth(100);
				colModel.getColumn(5).setPreferredWidth(220);
//				TableColumnModel colModel = table.getColumnModel();
//				Integer numColumns = table.getColumnCount();
//				for (int j = 0; j < numColumns; j++) {
//					TableColumn col = colModel.getColumn(j);
//					col.setCellRenderer(new TableListRenderer("ok") );
//					//col.setCellEditor(new TableListEditor("ok"));
//				}
				
				//table.setDefaultRenderer(JLabel.class,  new Renderer());

			} catch (SQLException e) {
				System.out.println("Exception in Load Data: " + e);
				e.printStackTrace();
			}
			
		}
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		JButton btnNewButton = new JButton("Cerrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnNewButton.setBounds(929, 384, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnEjecutar = new JButton("Ejecutar");
		btnEjecutar.setBounds(765, 384, 89, 23);
		contentPane.add(btnEjecutar);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Detalle", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(546, 38, 483, 340);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(6, 16, 470, 320);
		panel_2.add(scrollPane_2);
		
		
		StyleContext sc = new StyleContext();
	    final DefaultStyledDocument doc = new DefaultStyledDocument(sc);

	    JTextPane textPane = new JTextPane(doc);
		scrollPane_2.setViewportView(textPane);
		textPane.setContentType("text/html");
		
		JButton btnVerDetalle = new JButton("Ver Detalle");
		btnVerDetalle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Consul2 consultadetall = new Consul2(jobConsulta, reporteDetail, estadoEjecucion, numeroReglas);
				consultadetall.setVisible(true);
				
			}
		});
		btnVerDetalle.setBounds(591, 384, 89, 23);
		contentPane.add(btnVerDetalle);
	    //Option HTML
	    StringBuilder reporteSummaryHTML = new StringBuilder();
	    headerReport=""; 
	    tailorReport="";
	    reporteSummary="";
	

		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 0) {
		            jobConsulta = table.getModel().getValueAt(row, 0).toString(); //captura job tabla
		            System.out.println("Job Id: " +jobConsulta);
		            
		            String sql_countRules="SELECT to_char(count(1),'9999999') FROM app.app_jobs_reglas WHERE id_job="+jobConsulta+";";
		            rs =ejecutaQuery(sql_countRules,"S");
		            numeroReglas="";
		        	if (rs != null){
		    			try {
		    				ResultSetMetaData metaData = rs.getMetaData();
		    				// Numero of columns
		    	 			int columnCount = metaData.getColumnCount();

		    				while (rs.next()) {
		    					//Vector<Object> vector = new Vector<Object>();
		    					for (int i = 1; i <= columnCount; i++) {
		    						numeroReglas=(String) (rs.getObject(i));
		    					}
		    				}

		    			} catch (SQLException e) {
		    				System.out.println("Exception in Load Data: " + e);
		    				e.printStackTrace();
		    			}
		    		}
		            
		            
		            //textArea.setText("Job: "+jobConsulta);
		            String sql_queryJobDetails="SELECT t1.id,t1.tabla_1, t1.tabla_2 ,t3.nombre, t3.formatoa, t3.formatob,t3.clavea, t3.claveb, t3.condicion, to_char(t1.fecha_hora, 'YYYY-MM-DD HH12:MI:SS') as fechahora, t1.estado "+ 
		            " FROM app.app_jobs t1, app.app_jobs_reglas t2, app.app_reglas t3 WHERE t1.id=t2.id_job AND t3.id=t2.id_rule AND t1.id="+jobConsulta+";";
		            rs =ejecutaQuery(sql_queryJobDetails,"S");
		        	if (rs != null){
		    			try {
		    				//REMUEVE CONTENIDO TEXTPANEL
		    				textPane.setText("");
		    			    headerReport=""; 
		    			    tailorReport="";
		    			    reporteSummary="";
		    			    reporteDetail="";
		    			    reporteSummaryHTML.delete(0, reporteSummaryHTML.length());
		    				//Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    				ResultSetMetaData metaData = rs.getMetaData();
		    				// Numero of columns
		    	 			int columnCount = metaData.getColumnCount();
		    			
		    				// Data of the table
		    				//Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		    	 			if(numeroReglas == null)numeroReglas="0";
		    	 			
		    				String[][] datos = new String[Integer.valueOf(numeroReglas.trim())][11];//11 NUMERO COLUMNAS
		    				Integer k=0;
		    				while (rs.next()) {
		    					//Vector<Object> vector = new Vector<Object>();
		    					for (int i = 1; i <= columnCount; i++) {
		    						//rs.getObject("id").toString()
		    						if(i == 1){
		    							datos[k][i-1]=String.valueOf(rs.getObject(i));
		    						} else {
		    							datos[k][i-1]=(String) (rs.getObject(i));
		    						}
		    					}
		    					k++;
		    				}
		    				
		    				//HEADER
		    				headerReport="<html><body><h1>Job: "+jobConsulta+"</h1><table>"+System.lineSeparator();
		    				tailorReport="</table></body></html>";
		    				reporteSummary="<tr><td></td><td>"+"Archivo 1: "+datos[0][1]+"</td><td>"+"Archivo 2: "+datos[0][2]+"</td></tr>"+System.lineSeparator();
		    				reporteDetail=headerReport+"<tr><td><b>Estado: "+estadoEjecucion+"</b></td></tr>"+reporteSummary;
		    				
		    				//DATOS DE ARCHIVIOS
		    				String[] datosTb1= new String[2];
		    				String[] datosTb2= new String[2];
		    				
		    				String sql_tabla1="SELECT t4.nombre_archivo,to_char(t4.registros_cargados,'99999999999999999999999') FROM app.app_archivos t4, app.app_archivo_tabla t5, app.app_jobs t1"+
		    				" WHERE t5.id_archivo=t4.id AND t5.tabla=t1.tabla_1 AND t1.id="+jobConsulta+";";
		    				rs =ejecutaQuery(sql_tabla1,"S");
		    				if (rs != null){
				    			try {
				    				metaData = rs.getMetaData();
				    				// Numero of columns
				    	 			columnCount = metaData.getColumnCount();
				    				while (rs.next()) {
				    					//Vector<Object> vector = new Vector<Object>();
				    					for (int i = 1; i <= columnCount; i++) {
				    						datosTb1[i-1]=(String) (rs.getObject(i));
				    					}
				    				}

				    			} catch (SQLException e) {
				    				System.out.println("Exception in Load Data: " + e);
				    				e.printStackTrace();
				    			}
				    		}
		    				
		    				
		    				String sql_tabla2="SELECT t4.nombre_archivo,to_char(t4.registros_cargados,'99999999999999999999999') FROM app.app_archivos t4, app.app_archivo_tabla t5, app.app_jobs t1"+
				    				" WHERE t5.id_archivo=t4.id AND t5.tabla=t1.tabla_2 AND t1.id="+jobConsulta+";";
		    				rs =ejecutaQuery(sql_tabla2,"S");
		    				if (rs != null){
				    			try {
				    				metaData = rs.getMetaData();
				    				// Numero of columns
				    	 			columnCount = metaData.getColumnCount();
				    				while (rs.next()) {
				    					//Vector<Object> vector = new Vector<Object>();
				    					for (int i = 1; i <= columnCount; i++) {
				    						datosTb2[i-1]=(String) (rs.getObject(i));
				    					}
				    				}

				    			} catch (SQLException e) {
				    				System.out.println("Exception in Load Data: " + e);
				    				e.printStackTrace();
				    			}
				    		}
		    				
		    				reporteSummary=reporteSummary+"<tr><td></td><td>"+"Registros("+datosTb1[1].trim()+")"+"</td><td>"+"Registros("+datosTb2[1].trim()+")</td></tr>"+System.lineSeparator()
		    				+"<tr><td><h2>Reglas:</h2></td></tr>"+System.lineSeparator();
		    				reporteDetail=reporteDetail+"<tr><td></td><td>"+"Registros("+datosTb1[1].trim()+")"+"</td><td>"+"Registros("+datosTb2[1].trim()+")</td></tr>"+System.lineSeparator()
		    				+System.lineSeparator();
		    				
		    				for (int i = 0; i < Integer.valueOf(numeroReglas.trim()); i++){
		    					reporteSummary=reporteSummary+"<tr><td><b>"+datos[i][3]+"</b></td></tr>"+System.lineSeparator();
		    					reporteSummary=reporteSummary+"<tr><td></td><td>"+"Formato 1: "+datos[i][4]+"</td><td>"+"Formato 2: "+datos[i][5]+"</td></tr>"+System.lineSeparator();
		    					reporteSummary=reporteSummary+"<tr><td></td><td>"+"Clave 1: "+datos[i][6]+"</td><td>"+"Clave 2: "+datos[i][7]+"</td></tr>"+System.lineSeparator();
		    					reporteSummary=reporteSummary+"<tr><td></td><td>"+"Condicion: "+datos[i][8]+"</td></tr>"+System.lineSeparator();
		    					reporteSummary=reporteSummary+"<tr><td></td><td>"+"Excepciones: "+"No definidas"+"</td></tr>"+System.lineSeparator();
		    					if (datos[i][10].trim() =="ejecutado"){
		    					reporteSummary=reporteSummary+"<tr><td></td><td>Ultima Ejecucion: "+datos[i][9]+"</td></tr>"+System.lineSeparator();
		    					}else {
		    						reporteSummary=reporteSummary+"<tr><td></td><td>Ultima actualizacion: "+datos[i][9]+"</td></tr>"+System.lineSeparator();
		    					}
		    				}
		    						    				
		    				//PARA PANEL
		    	            reporteSummaryHTML.append( headerReport+ reporteSummary + tailorReport);
		    	            textPane.setText(reporteSummaryHTML.toString());
		    	            System.out.println("Texto HTML: " + reporteSummaryHTML.toString());
		    	            reporteDetail=reporteDetail+tailorReport;


		    			} catch (SQLException  e) {
		    				System.out.println("Exception in Load Data: " + e);
		    				e.printStackTrace();
		    			}
		    			
		    		}
		        }
		    }
		});
		
		
	}
	
	public ResultSet ejecutaQuery(String sqlstr_Input, String Tipo) {

		if (Tipo.trim() == "S") {
			try {
				System.out.println("Entradas: " + sqlstr_Input + " " + Tipo);
				PreparedStatement pat_vali = conn.prepareStatement(sqlstr_Input);
				ResultSet rs_vali = pat_vali.executeQuery();
				//rs_vali.next();
				//System.out.println("Consulta: " + rs_vali.getString(1));
				//rs_vali.first();
				return rs_vali;
			} catch (SQLException e1) {
				System.out.println("Error en la expresion de consulta: " + e1.getMessage());
				JOptionPane.showMessageDialog(null, "Error en la expresion: " + e1.getMessage(),
						"Validacion Incorrecta ", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}

		return null;
	}
	
//	class TableListRenderer implements TableCellRenderer {
//		@SuppressWarnings("rawtypes")
//		//JComboBox comboBox;
//		JLabel label;
//		Component c;
//
//		@SuppressWarnings({ "rawtypes", "unchecked" })
//		public TableListRenderer(String items) {
//			//comboBox = new JComboBox(items);
//			//comboBox.setSelectedIndex(0);
//			label = new JLabel();
//			label.setHorizontalAlignment(JLabel.CENTER);
//			label.setOpaque(true);
//
//		}
//
//		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
//				int row, int column) {
//			if (row >= 0 && column == 4) {
//				c = label;
//				if (isSelected)
//					label.setBackground(table.getSelectionBackground());
//				else
//					label.setBackground(table.getBackground());
//				//label.setText("OK2");
//				label.setIcon(backIcon);
//				//label.setIcon(new ImageIcon(getClass().getResource("blue-circle.png")));
//			} else {
//				c = label;
//				if (isSelected)
//					label.setBackground(table.getSelectionBackground());
//				else
//					label.setBackground(table.getBackground());
//				if (value == null) {
//					label.setText("Nulo");
//					label.setIcon(backIcon);
//				} else {
//					label.setText(value.toString());
//					label.setIcon(backIcon);
//				}
//			}
//			return c;
//		}
//	}
//
//	class TableListEditor extends DefaultCellEditor {
//		@SuppressWarnings({ "rawtypes", "unchecked" })
//		public TableListEditor(String items) {
//			super(new JList());
//		}
//	}
//	
    private ImageIcon getImage(String path)
    {
        java.net.URL url = getClass().getResource(path);
        if (url != null)
            return (new ImageIcon(url));
        else
        {
            System.out.println(url);
            return null;
        }
    }
}
