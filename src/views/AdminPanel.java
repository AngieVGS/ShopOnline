package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import controller.ActionEnum;
import controller.Controller;
import models.entity.Product;

public class AdminPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String[] titles = { "id", "name", "stock", "category", "imagePath", " " };
	private JTable tableObj;
	private JButton btnadd;
	private JButton btnEdit;
	private JButton btnDelete;
	private JButton btnView;
	private DefaultTableModel model;
	private JLabel title;
	private JButton btnSatsistics;

	public AdminPanel(Controller controller) {
		setBackground(Color.decode(Constants.BACKGROUND_COLOR_MAIN));
		setLayout(new GridBagLayout());
		title = new JLabel(Constants.PRODUCTS_LABEL);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.15;
		add(title, c);

		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.05;
		JPanel panelx = new JPanel();
		FlowLayout lay = new FlowLayout(FlowLayout.LEFT);
		panelx.setLayout(lay);

		btnadd = new JButton(Constants.ADD_TEXT);
		btnadd.setForeground(Color.decode(Constants.COLOR_FONT_BUTTON));
		btnadd.setBackground(Color.decode(Constants.COLOR_BUTTON));
		btnadd.setBorder(null);
		btnadd.addActionListener(controller);
		btnadd.setActionCommand(ActionEnum.SHOW_CREATE_DIALOG.name());
		Dimension dim = new Dimension(70, 30);
		btnadd.setPreferredSize(dim);
		panelx.add(btnadd);
		panelx.setBackground(Color.white);

		btnEdit = new JButton(Constants.EDIT_TEXT);
		btnEdit.setForeground(Color.decode(Constants.COLOR_FONT_BUTTON));
		btnEdit.setBorder(null);
		btnEdit.setBackground(Color.decode(Constants.COLOR_BUTTON));
		btnEdit.addActionListener(controller);
		btnEdit.setActionCommand(ActionEnum.EDIT_PRODUCT.name());
		btnEdit.setPreferredSize(dim);
		panelx.add(btnEdit);

		btnDelete = new JButton(Constants.DELETE_TEXT);
		btnDelete.setForeground(Color.decode(Constants.COLOR_FONT_BUTTON));
		btnDelete.setBackground(Color.decode(Constants.COLOR_BUTTON));
		btnDelete.setBorder(null);
		btnDelete.addActionListener(controller);
		btnDelete.setActionCommand(ActionEnum.DELETE_PRODUCT.name());
		btnDelete.setPreferredSize(dim);
		panelx.add(btnDelete);

		btnView = new JButton(Constants.VIEW_TEXT);
		btnView.setForeground(Color.decode(Constants.COLOR_FONT_BUTTON));
		btnView.setBackground(Color.decode(Constants.COLOR_BUTTON));
		btnView.setBorder(null);
		btnView.addActionListener(controller);
		btnView.setActionCommand(ActionEnum.VIEW_PRODUCT.name());
		btnView.setPreferredSize(dim);
		panelx.add(btnView);

		btnSatsistics = new JButton(Constants.BTN_STATISTICS_NAME);
		btnSatsistics.setForeground(Color.decode(Constants.COLOR_FONT_BUTTON));
		btnSatsistics.setBackground(Color.decode(Constants.COLOR_BUTTON));
		btnSatsistics.setBorder(null);
		btnSatsistics.addActionListener(controller);
		btnSatsistics.setActionCommand(ActionEnum.SHOW_STATISTICS.name());
		btnSatsistics.setPreferredSize(dim);
		panelx.add(btnSatsistics);

		add(panelx, c);

		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.8;

		model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return column > 5;
			}
		};
		model.setColumnIdentifiers(titles);
		tableObj = new JTable(model);
		tableObj.getColumnModel().getColumn(0).setPreferredWidth(-12);
		tableObj.getColumnModel().getColumn(0).setWidth(5);
		tableObj.getColumnModel().getColumn(1).setPreferredWidth(180);
		tableObj.getColumnModel().getColumn(2).setPreferredWidth(80);
		tableObj.getColumnModel().getColumn(3).setPreferredWidth(180);
		tableObj.setOpaque(false);
		JScrollPane scrol = new JScrollPane(tableObj);
		scrol.setBackground(Color.decode(Constants.BACKGROUND_COLOR_MAIN));
		scrol.getViewport().setBackground(Color.decode(Constants.BACKGROUND_COLOR_MAIN));
		scrol.setBorder(BorderFactory.createLineBorder(Color.decode(Constants.BACKGROUND_COLOR_MAIN), 8));
		scrol.setOpaque(false);
		add(scrol, c);

	}

	public void deleteAllValuesofTheTable() {
		model.setRowCount(0);
	}

	public String getDate() {
		return "" + tableObj.getValueAt(tableObj.getSelectedRow(), tableObj.getSelectedColumn());
	}

	public int getSelectedRow() {
		return tableObj.getSelectedRow();
	}

	public int getidToEditedProduct() {
		return tableObj.getSelectedRow();
	}

	public void addProduct(Product product) {
		String[] ob = { "" + product.getId(), product.getName(), "" + product.getQuantumAvailable(),
				product.getCategory().toString(), product.getImagePath() };
		model.addRow(ob);
	}

	public void deleteProduct() {
		if (tableObj.getSelectedRow() != -1) {
			model.removeRow(tableObj.getSelectedRow());
		}
	}

	public int returnTheSelectedId() {
		return Integer.parseInt((String) tableObj.getValueAt(tableObj.getSelectedRow(), 0));
	}

	public void revalidateTableWithSpecificItems(ArrayList<Product> products) {
		deleteAllValuesofTheTable();
		for (int i = 0; i < products.size(); i++) {
			addProduct(products.get(i));
		}
	}
}