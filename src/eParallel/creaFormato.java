package eParallel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

@SuppressWarnings({ "unused", "serial" })
public class creaFormato extends JFrame {

	private JPanel contentPane;
	public String[] dataFormato;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JToggleButton toggleButton;
	private JToggleButton toggleButton_1;
	private JToggleButton toggleButton_2;
	private JToggleButton toggleButton_3;
	private JToggleButton toggleButton_4;
	private Integer selector;
	private final DefaultTableModel tableModel = new DefaultTableModel();
	@SuppressWarnings("rawtypes")
	private final DefaultListModel listModel = new DefaultListModel();
	private ObjectMapper mapper;
	private JButton btnCancelar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					creaFormato frame = new creaFormato(args);
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
	public creaFormato(String[] arg) {
		setTitle("Crear Claves De Formato");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 940, 479);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		selector = 1;
		String[] claves = { "", "", "", "", "" };

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Campos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(58, 11, 247, 381);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 16, 235, 358);
		panel.add(scrollPane);

		JList list = new JList();
		scrollPane.setViewportView(list);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(
				new TitledBorder(null, "Clave 1 - Principal", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(472, 28, 423, 43);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		textField = new JTextField();
		textField.setBounds(6, 16, 407, 20);
		panel_1.add(textField);
		textField.setColumns(10);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(
				new TitledBorder(null, "Clave 2 - Secundaria", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(472, 108, 423, 43);
		contentPane.add(panel_2);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(6, 16, 407, 20);
		panel_2.add(textField_1);

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(
				new TitledBorder(null, "Clave 3 - Secundaria", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(472, 188, 423, 43);
		contentPane.add(panel_3);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(6, 16, 407, 20);
		panel_3.add(textField_2);

		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBorder(
				new TitledBorder(null, "Clave 4 - Secundaria", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBounds(472, 268, 423, 43);
		contentPane.add(panel_4);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(6, 16, 407, 20);
		panel_4.add(textField_3);

		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBorder(
				new TitledBorder(null, "Clave 5 - Secundaria", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setBounds(472, 348, 423, 43);
		contentPane.add(panel_5);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(6, 16, 407, 20);
		panel_5.add(textField_4);

		toggleButton = new JToggleButton();
		toggleButton.setText("");
		toggleButton.setBounds(341, 38, 121, 23);
		contentPane.add(toggleButton);
		toggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.out.println("ActionEvent!");
				toggleButton.setSelected(true);
				toggleButton.setText("=>");
				toggleButton_1.setSelected(false);
				toggleButton_1.setText("");
				toggleButton_2.setSelected(false);
				toggleButton_2.setText("");
				toggleButton_3.setSelected(false);
				toggleButton_3.setText("");
				toggleButton_4.setSelected(false);
				toggleButton_4.setText("");
				selector = 1;

			}
		});

		toggleButton_1 = new JToggleButton();
		// JToggleButton toggleButton_1 = new JToggleButton("=>");
		toggleButton_1.setBounds(341, 118, 121, 23);
		contentPane.add(toggleButton_1);
		toggleButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.out.println("ActionEvent!");
				toggleButton_1.setSelected(true);
				toggleButton_1.setText("=>");
				toggleButton.setSelected(false);
				toggleButton.setText("");
				toggleButton_2.setSelected(false);
				toggleButton_2.setText("");
				toggleButton_3.setSelected(false);
				toggleButton_3.setText("");
				toggleButton_4.setSelected(false);
				toggleButton_4.setText("");
				selector = 2;
			}
		});

		toggleButton_2 = new JToggleButton();
		toggleButton_2.setText("");
		toggleButton_2.setBounds(341, 198, 121, 23);
		contentPane.add(toggleButton_2);
		toggleButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.out.println("ActionEvent!");
				toggleButton_2.setSelected(true);
				toggleButton_2.setText("=>");
				toggleButton_1.setSelected(false);
				toggleButton_1.setText("");
				toggleButton.setSelected(false);
				toggleButton.setText("");
				toggleButton_3.setSelected(false);
				toggleButton_3.setText("");
				toggleButton_4.setSelected(false);
				toggleButton_4.setText("");
				selector = 3;
			}
		});

		toggleButton_3 = new JToggleButton();
		toggleButton_3.setText("");
		toggleButton_3.setBounds(341, 278, 121, 23);
		contentPane.add(toggleButton_3);
		toggleButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.out.println("ActionEvent!");
				toggleButton_3.setSelected(true);
				toggleButton_3.setText("=>");
				toggleButton_1.setSelected(false);
				toggleButton_1.setText("");
				toggleButton_2.setSelected(false);
				toggleButton_2.setText("");
				toggleButton.setSelected(false);
				toggleButton.setText("");
				toggleButton_4.setSelected(false);
				toggleButton_4.setText("");
				selector = 4;
			}
		});

		toggleButton_4 = new JToggleButton();
		toggleButton_4.setText("");
		toggleButton_4.setBounds(341, 358, 121, 23);
		contentPane.add(toggleButton_4);
		toggleButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.out.println("ActionEvent!");
				toggleButton_4.setSelected(true);
				toggleButton_4.setText("=>");
				toggleButton_1.setSelected(false);
				toggleButton_1.setText("");
				toggleButton_2.setSelected(false);
				toggleButton_2.setText("");
				toggleButton_3.setSelected(false);
				toggleButton_3.setText("");
				toggleButton.setSelected(false);
				toggleButton.setText("");
				selector = 5;
			}
		});
		toggleButton.setSelected(true);
		toggleButton.setText("=>");
		toggleButton_1.setSelected(false);
		toggleButton_1.setText("");
		toggleButton_2.setSelected(false);
		toggleButton_2.setText("");
		toggleButton_3.setSelected(false);
		toggleButton_3.setText("");
		toggleButton_4.setSelected(false);
		toggleButton_4.setText("");

		// CREA LISTA

		mapper = new ObjectMapper();
		// For text string
		// JsonNode = mapper.readValue(mapper.writeValueAsString("Text-string"),
		// JsonNode.class)
		// For Array String

		try {
			// JSONObject json_str= new JSONObject(arg[0]);
			JsonNode json_str = mapper.readValue(arg[0], JsonNode.class);
			for (int j = 0; j < json_str.get("tabla").size(); j++) {
				JsonNode res = json_str.get("tabla").get(j);
				listModel.addElement(res.get("column_name").toString().replace("\"", ""));
				System.out.println("Valores: " + res.get("column_name") + res.get("column_type"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		list.setModel(listModel);

		list.addMouseListener(new MouseAdapter() { // listener sobre lista base
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() >= 1) {
					list.setEnabled(true);
					String value = list.getSelectedValue().toString(); // Tabla
																		// base
					if (selector == 1) {
						textField.setText(textField.getText() + value);
					}
					;
					if (selector == 2) {
						textField_1.setText(textField_1.getText() + value);
					}
					;
					if (selector == 3) {
						textField_2.setText(textField_2.getText() + value);
					}
					;
					if (selector == 4) {
						textField_3.setText(textField_3.getText() + value);
					}
					;
					if (selector == 5) {
						textField_4.setText(textField_4.getText() + value);
					}
					;
					claves[0] = textField.getText();
					claves[1] = textField_1.getText();
					claves[2] = textField_2.getText();
					claves[3] = textField_3.getText();
					claves[4] = textField_4.getText();
				}
			}
		});
		JButton btnVolver = new JButton("Crear");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textField.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Clave principal es obligatoria", "Error creando claves",
							JOptionPane.ERROR_MESSAGE);
				} else {
					cargaArchivo.rowFormato(claves);
					dispose();
				}
			}
		});
		btnVolver.setBounds(124, 407, 89, 23);
		contentPane.add(btnVolver);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancelar.setBounds(631, 402, 89, 23);
		contentPane.add(btnCancelar);

	}
}
