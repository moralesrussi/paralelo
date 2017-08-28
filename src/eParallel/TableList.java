package eParallel;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

@SuppressWarnings("unused")
public class TableList {
	public TableList() {
		JTable table = createTable();
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(table);
		f.setSize(400, 400);
		f.setLocation(200, 200);
		f.setVisible(true);
	}

	@SuppressWarnings("serial")
	private JTable createTable() {
		String[] col1 = { "text", "integer", "data", "boolean", "timestamp" };
		String[][] listData = { col1, col1 };
		final int rows = 4;
		final int cols = 5;
		DefaultTableModel dataModel = new DefaultTableModel() {
			public int getRowCount() {
				return rows;
			} // set size

			public int getColumnCount() {
				return cols;
			} // of table

			public boolean isCellEditable(int row, int column) {
				if (row > 0 && column < 4) // disable editing in cells
					return false; // below combo boxes
				return true;
			}
		};
		// load data into dataModel
		for (int row = 0; row < rows; row++)
			for (int col = 0; col < cols; col++) {
				Object o = null;
				if (row == 0 && col < 2) // combo boxes
					o = listData[col];
				else // other cells
					o = "item " + (row * cols + col + 1);
				dataModel.setValueAt(o, row, col);
			}
		JTable table = new JTable(dataModel);
		// install renderers and editors on first two columns
		TableColumnModel colModel = table.getColumnModel();
		Integer numColumns = table.getColumnCount();
		for (int j = 0; j < numColumns; j++) {
			TableColumn col = colModel.getColumn(j);
			col.setCellRenderer(new TableListRenderer(listData[1]));
			col.setCellEditor(new TableListEditor(listData[1]));
		}
		// set row height
		TableListRenderer renderer = (TableListRenderer) table.getCellRenderer(0, 0);
		Dimension d = renderer.comboBox.getPreferredSize();
		int rowHeight = d.height;
		table.setRowHeight(rowHeight);
		return table;
	}

	public static void main(String[] args) {
		new TableList();
	}
}

class TableListRenderer implements TableCellRenderer {
	@SuppressWarnings("rawtypes")
	JComboBox comboBox;
	JLabel label;
	Component c;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TableListRenderer(String[] items) {
		comboBox = new JComboBox(items);
		label = new JLabel();
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (row == 0 && column < 2) {
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
			label.setText(value.toString());
		}
		return c;
	}
}

@SuppressWarnings("serial")
class TableListEditor extends DefaultCellEditor {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TableListEditor(String[] items) {
		super(new JComboBox(items));
	}
}