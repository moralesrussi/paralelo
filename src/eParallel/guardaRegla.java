package eParallel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JToggleButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class guardaRegla extends JFrame {
	private String input1 = null;
	private String input2 = null;
	private String input3 = null;
	private String input4 = null;
	private String input5 = null;
	private String input6 = null;
	Connection conn = null;
	String resultado = "";
	// DateFormat dateFormat = new DateFormat("yyyy/MM/dd HH:mm:ss");
	// Date date = new Date();
	// dateFormat.format(date)

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;

	public guardaRegla(String inputCall1, String inputCall2, String inputCall3, String inputCall4, String inputCall5,
			String inputCall6) {
		this.input1 = inputCall1;
		this.input2 = inputCall2;
		this.input3 = inputCall3;
		this.input4 = inputCall4;
		this.input5 = inputCall5;
		this.input6 = inputCall6;
		conn = ConectarDBPostgres.main(null);

		setTitle("Guardar Reglas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 775, 356);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		System.out.println("Datos llamados: " + input1 + "" + input2);

		JPanel panel = new JPanel();
		panel.setBorder(
				new TitledBorder(null, "Nombre de la Regla", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(105, 175, 566, 58);
		contentPane.add(panel);
		panel.setLayout(null);

		textField = new JTextField();
		textField.setBounds(6, 16, 554, 31);
		panel.add(textField);
		textField.setColumns(10);

		JToggleButton tglbtnActiva = new JToggleButton("Activa");
		tglbtnActiva.setBounds(105, 244, 121, 23);
		contentPane.add(tglbtnActiva);

		JLabel lblFormatoA = new JLabel("Formato A:");
		lblFormatoA.setBounds(39, 40, 65, 14);
		contentPane.add(lblFormatoA);

		JLabel lblFormatoB = new JLabel("Formato B:");
		lblFormatoB.setBounds(397, 40, 65, 14);
		contentPane.add(lblFormatoB);

		JLabel lblClaveA = new JLabel("Clave B:");
		lblClaveA.setBounds(397, 65, 65, 14);
		contentPane.add(lblClaveA);

		JLabel lblClaveA_1 = new JLabel("Clave A:");
		lblClaveA_1.setBounds(39, 65, 65, 14);
		contentPane.add(lblClaveA_1);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 145, 739, 7);
		contentPane.add(separator);

		JLabel lblRegla = new JLabel("Regla:");
		lblRegla.setBounds(39, 90, 46, 14);
		contentPane.add(lblRegla);

		// formatoA_final, claveA_final, formatoB_final, claveB_final,
		// condicion_final)
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(105, 37, 262, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText(input1);

		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(105, 62, 262, 20);
		contentPane.add(textField_2);
		textField_2.setText(input2);

		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBounds(105, 87, 616, 20);
		contentPane.add(textField_3);
		textField_3.setText(input5);

		textField_4 = new JTextField();
		textField_4.setEditable(false);
		textField_4.setColumns(10);
		textField_4.setBounds(459, 37, 262, 20);
		contentPane.add(textField_4);
		textField_4.setText(input3);

		textField_5 = new JTextField();
		textField_5.setEditable(false);
		textField_5.setColumns(10);
		textField_5.setBounds(459, 62, 262, 20);
		contentPane.add(textField_5);
		textField_5.setText(input4);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().trim() != "" && textField.getText().matches("[a-zA-Z0-9]*")) {
					String sqlstr_name = "SELECT trim(to_char(count(1), '99999'))	FROM app.app_reglas t1 where t1.nombre='"
							+ textField.getText().trim() + "' LIMIT 1;";
					resultado = ejecutaQueryGuardar(sqlstr_name, "S").toString().trim();
					if (Integer.valueOf(resultado) != 0) { // Grabar
						System.out.println("Regla ya existe Resultado:+" + resultado + "+");
						JOptionPane.showMessageDialog(null, "Nombre de regla y existe", "Mensaje ",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						String camposFomateado = formateaCampos(input6.trim());
						String condicionFormateado = formateaCondicion(textField_1.getText().trim(),
								textField_4.getText().trim(), textField_3.getText().trim());

						String sql_insert = "INSERT INTO app.app_reglas( nombre, formatoA, claveA, formatoB, claveB, condicion, estado, fecha_creacion, campos) VALUES ("
								+ "'" + textField.getText().trim() + "', '" + textField_1.getText().trim() + "', '"
								+ textField_2.getText().trim()
										.substring(textField_2.getText().trim().indexOf(":") + 1,
												textField_2.getText().trim().length())
										.trim()
								+ "', '" + textField_4.getText().trim() + "', '"
								+ textField_5.getText().trim()
										.substring(textField_5.getText().trim().indexOf(":") + 1,
												textField_5.getText().trim().length())
										.trim()
								+ "', '" + condicionFormateado.trim() + "', " + true + ", '" + java.time.LocalDate.now()
								+ "', '" + camposFomateado.trim() + "');";
						System.out.println("SQL de insercion: " + sql_insert + " Resultado: " + resultado);
						ejecutaQueryGuardar(sql_insert, "I");
						dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Nombre permitido de solo Caracteres alfanumericos", "Mensaje ",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});
		btnGuardar.setBounds(582, 244, 89, 23);
		contentPane.add(btnGuardar);

		JButton button = new JButton("Volver");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.setBounds(237, 244, 89, 23);
		contentPane.add(button);

	}

	protected String formateaCondicion(String formatoAcon1, String formatoBcon1, String condicionIn) {
		// TODO Auto-generated method stub
		String condicionPre1 = condicionIn.replace(formatoAcon1, "ta");
		String condicionPre2 = condicionPre1.replace(formatoBcon1, "tb");
		return condicionPre2;
	}

	protected String formateaCampos(String inputcampo) {
		// TODO Auto-generated method stub
		// formato2.BILLSTYLE,formato1.GENEVAVERSION,formato2.ACCCURRENCYCODE,formato1.BILLTYPE
		// formato1.GENEVAVERSION,formato1.BILLTYPE&formato2.ACCCURRENCYCODE,formato2.BILLSTYLE&ta.GENEVAVERSION
		// as ta_GENEVAVERSION,ta.BILLTYPE as ta_BILLTYPE,tb.ACCCURRENCYCODE as
		// tb_ACCCURRENCYCODE,tb.BILLSTYLE as tb_BILLSTYLE
		String f1 = inputcampo;
		String f2 = "";
		String f3 = "";
		String f4 = "";
		String f5 = "";
		String ftemp ="";
		String[] f1array = f1.split(",");
		for (int i = 0; i < f1array.length; i++) {
			System.out.println("Campo: " + f1array[i]);
			if (f1array[i].contains("formatoA.")) {
				// f2=f2+f1array[i].toString()+",";
				f4 = f1array[i].toString().substring(f1array[i].lastIndexOf('.') + 1);
				f2 = "ta." + f4 + "," + f2;
				ftemp= "ta." + f4 + " as ta_" + f4;
				if(!f5.contains(ftemp)){//SI CAMPO YA EXISTE EN LA CADENA
					f5 = "ta." + f4 + " as ta_" + f4 + ", " + f5;
				}
			} else {
				f4 = f1array[i].toString().substring(f1array[i].lastIndexOf('.') + 1);
				f3 = "tb." + f4 + "," + f3;
				ftemp="tb." + f4 + " as tb_" + f4;
				if(!f5.contains(ftemp)){ //SI CAMPO YA EXISTE EN LA CADENA
					f5 = "tb." + f4 + " as tb_" + f4 + ", " + f5;
				}
			}
			System.out.println("Campo: " + f1array[i] + " f2: " + f2 + " f3: " + f3);
		}
		String finaltext = removeLastChar(f2) + "&" + removeLastChar(f3) + "&" + removeLastChar(f5);
		return finaltext;
	}

	private static String removeLastChar(String str) {
		return str.substring(0, str.length() - 1);
	}

	protected String ejecutaQueryGuardar(String sqlstr_Input, String Tipo) {

		if (Tipo.trim() == "S") {
			try {
				System.out.println("Entradas: " + sqlstr_Input + " " + Tipo);
				PreparedStatement pat_vali = conn.prepareStatement(sqlstr_Input);
				ResultSet rs_vali = pat_vali.executeQuery();
				rs_vali.next();
				System.out.println("Consulta: " + rs_vali.getString(1));
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
				pat_vali.executeQuery();
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
		return null;
	}
}
