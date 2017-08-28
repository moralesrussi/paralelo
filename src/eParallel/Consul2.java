package eParallel;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.Canvas;
import java.awt.Color;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

@SuppressWarnings({ "serial", "unused" })
public class Consul2 extends JFrame {

	private JPanel contentPane;
	Connection conn = null;
	DefaultCategoryDataset result = new DefaultCategoryDataset();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			private String in1 = "";
			private String in2 = "";
			private String in3 = "";
			private String in4 = "";

			public void run() {
				try {
					try {
						UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel");
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (args.length != 0) {
						if (args[0] == null) {
							in1 = "2";
						} else {
							in1 = args[0];
						}
						if (args[1] == null) {
							in2 = "2";
						} else {
							in2 = args[1];
						}
						if (args[2] == null) {
							in3 = "No informado";
						} else {
							in3 = args[2];
						}
						if (args[3] == null) {
							in4 = "1";
						} else {
							in4 = args[3];
						}
					} else {
						in1 = "2";
						in2 = "2";
						in3 = "No informado";
						in4 = "1";
					}
					Consul2 frame = new Consul2(in1, in2, in3, in4);
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
	 * @param reporteDetail
	 * @param jobConsulta
	 */
	public Consul2(String jobConsulta, String reporteDetail, String estadoEjecucion, String numeroReglas) {
		setTitle("Detalle de Job");
		conn = ConectarDBPostgres.main(null); // Conector DB
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1149, 594);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// Option HTML
		StringBuilder reporteSummaryHTML = new StringBuilder();
		String sql_query_regla = "SELECT * FROM app.app_jobs_reglas WHERE id_job=" + Integer.valueOf(jobConsulta.trim())
				+ ";";
		String[] part = new String[Integer.valueOf(numeroReglas.trim())];
		Statement statement;
		try {
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql_query_regla);
			Integer k = 0;
			while (resultSet.next()) {
				String values = resultSet.getString("id_rule");
				part[k] = values; // ID DE REGLA
				k++;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String sql_coincidencias_gnral = "SELECT app.matching_files_result('temporal.job_" + jobConsulta.trim() + "_"
				+ part[0] + "') AS OUTPUT;";

		try {
			statement = conn.createStatement();

			ResultSet resultSet = statement
					.executeQuery(sql_coincidencias_gnral);
			DefaultPieDataset dataset = new DefaultPieDataset();

			while (resultSet.next()) {
				String values = resultSet.getString("OUTPUT");
				String[] part1 = values.split(",");
				for (int i = 0; i <= 2; i++) {
					// result.addValue(20.3, "Product 1 (US)", "Jan 04");
					if (i == 0)
						dataset.setValue("Coinciden", Double.parseDouble(part1[0]));
					if (i == 1)
						dataset.setValue("Solo en Archivo 1.", Double.parseDouble(part1[1]));
					if (i == 2)
						dataset.setValue("Solo en Archivo 2.", Double.parseDouble(part1[2]));
				}
			}

			JFreeChart chart = ChartFactory.createPieChart(null, // chart title
					dataset, // data
					false, // include legend
					false, false);

			PiePlot plot = (PiePlot) chart.getPlot();
			plot.setSectionPaint("Coinciden", Color.green);
			plot.setSectionPaint("Solo en Archivo 1.", Color.red);
			plot.setSectionPaint("Solo en Archivo 2.", Color.blue);
			plot.setExplodePercent("Coinciden", 0.10);
			// plot.setSimpleLabels(true);
			contentPane.setLayout(null);
			PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{0}: {1} ({2})",
					new DecimalFormat("0"), new DecimalFormat("0%"));
			plot.setLabelGenerator(gen);

			// int width = 100; /* Width of the image */
			// int height = 100; /* Height of the image */
			// File pieChart = new File("Pie_Chart.jpeg");
			// ChartUtilities.saveChartAsJPEG(pieChart, chart, width, height);
			ChartPanel chartPanel = new ChartPanel(chart);
			chartPanel.setBackground(UIManager.getColor("Button.background"));
			// getContentPane().add(chartPanel);
			// setVisible(true);
			// setContentPane(chartPanel);
			JPanel panel = new JPanel();
			panel.setBounds(563, 11, 541, 251);
			panel.setBorder(
					new TitledBorder(null, "Resultado del cruce", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPane.add(panel);

			// Adiciono chart al panel
			chartPanel.setMouseWheelEnabled(true);
			panel.setLayout(new java.awt.BorderLayout());
			panel.add(chartPanel, BorderLayout.CENTER);

			JPanel panel_1 = new JPanel();
			panel_1.setBorder(
					new TitledBorder(null, "Resumen de Job", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_1.setBounds(25, 11, 532, 251);
			contentPane.add(panel_1);
			panel_1.setLayout(null);

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 18, 510, 222);
			panel_1.add(scrollPane);

			JEditorPane editorPane = new JEditorPane();
			scrollPane.setViewportView(editorPane);
			editorPane.setEditable(false);
			editorPane.setContentType("text/html");
			reporteSummaryHTML.append(reporteDetail);
			editorPane.setText(reporteSummaryHTML.toString());

			JSeparator separator_1 = new JSeparator();
			separator_1.setBounds(10, 273, 1113, 16);
			contentPane.add(separator_1);
			panel.validate();

			// SELECT app.matching_files_result_detail('temporal.job_3_3_result');

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
