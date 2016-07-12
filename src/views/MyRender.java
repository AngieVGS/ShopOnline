package views;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MyRender extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	Color background;
	Color foreground;

	public MyRender(Color background, Color foreground) {
		this.background = background;
		this.foreground = foreground;
	}

	public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column) {
		java.awt.Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		comp.setBackground(background);
		comp.setForeground(foreground);
		this.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		this.setBorder(javax.swing.BorderFactory.createLineBorder(Color.decode(Constants.COLOR_BUTTON)));
		return comp;
	}
}