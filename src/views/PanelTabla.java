package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTable.PrintMode;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import controller.ActionEnum;
import controller.Controller;
import models.entity.Category;
import models.entity.Product;
import persistence.ManagerPersistence;

public class PanelTabla extends JPanel {

	private static final long serialVersionUID = 1L;

	private TableToolbar tableToolBar;
	private ToolBarMain toolBarMain;

	private JTable table;
	public static final String[] titlesTable = { "ID", "NAME", "PRICE", "QUANTUM AVAILABLE", "CATEGORY", "DISCONT" };
	private DefaultTableModel defaultTableModel;
	private JButton buttonRemoveProduct;
	private JButton buttonEditProduct;
	private JButton buttonBioProduct;

	private JButton buttonLastBack;
	private JButton buttonBack;
	private JButton buttonNext;
	private JButton buttonLastNext;
	private JLabel labelCountPage;
	private int numberPageCurrent = 1;

	public PanelTabla(Controller controller) {
		setBackground(Constants.COLOR_DARK_BLUE_GENERAL);
		setLayout(new BorderLayout());

		tableToolBar = new TableToolbar(controller);
		tableToolBar.setBackground(Constants.COLOR_DARK_BLUE_GENERAL);
		add(tableToolBar, BorderLayout.PAGE_START);

		JPanel panelTable = new JPanel(new BorderLayout());
		panelTable.setBorder(BorderFactory.createLineBorder(Color.white));

		table = new JTable();
		table.setShowGrid(!true);
		table.setOpaque(!true);
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionBackground(Constants.COLOR_BACKGROUND_SELECTION_TABLE);
		table.setGridColor(Color.GREEN);
		table.setGridColor(Color.BLACK);
		// table.setFont(Constants.FONT_TABLE);
		panelTable.add(new JScrollPane(table));

		addPanelControl(controller);

		assignColorDissableBack();

		modificateTabla(controller);

		defaultTableModel.setColumnIdentifiers(titlesTable);
		defaultTableModel.addColumn("#");
		defaultTableModel.addColumn("Remove");
		defaultTableModel.addColumn("Edit");
		defaultTableModel.addColumn("Review");

		table.moveColumn(6, 0);

		buttonRemoveProduct = new JButton("Remove");
		buttonRemoveProduct.setToolTipText("Delete this product");
		buttonRemoveProduct.addActionListener(controller);
		buttonRemoveProduct.setBackground(Color.RED);
		buttonRemoveProduct.setBorderPainted(false);
		// buttonRemoveProduct.setFont(Constants.FONT_TABLE);
		buttonRemoveProduct.setForeground(Color.WHITE);

		buttonEditProduct = new JButton("Edit");
		// buttonEditProduct.setFont(Constants.FONT_TABLE);
		buttonEditProduct.setToolTipText("Edit this product");
		buttonEditProduct.addActionListener(controller);
		buttonEditProduct.setBackground(Color.decode("#00A33D"));
		buttonEditProduct.setForeground(Color.WHITE);

		buttonBioProduct = new JButton("Preview");
		// buttonBioProduct.setFont(Constants.FONT_TABLE);
		buttonBioProduct.setToolTipText("Show Bio of this product");
		buttonBioProduct.addActionListener(controller);
		buttonBioProduct.setBackground(Color.BLUE);
		buttonBioProduct.setForeground(Color.WHITE);

		toolBarMain = new ToolBarMain(controller);
		add(toolBarMain, BorderLayout.LINE_START);

		add(panelTable, BorderLayout.CENTER);
	}

	private void modificateTabla(Controller controller) {
		String[] columnas = new String[] {};

		@SuppressWarnings("rawtypes")
		final Class[] tiposColumnas = new Class[] { Object.class, String.class, Object.class, Object.class,
				Category.class, Object.class, String.class, JButton.class, JButton.class, JButton.class };

		Object[][] datos = new Object[][] {};

		table.setModel(defaultTableModel = new javax.swing.table.DefaultTableModel(datos, columnas) {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] tipos = tiposColumnas;

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int columnIndex) {
				return tipos[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return !true;
			}
		});

		table.setDefaultRenderer(JButton.class, new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean isSelected,
					boolean hasFocus, int row, int column) {
				return (Component) objeto;
			}
		});

		table.addMouseListener(controller);
	}

	private void addPanelControl(Controller controller) {
		JPanel panelControl = new JPanel(new FlowLayout());

		panelControl.add(buttonLastBack = new JButton("<<"));
		buttonLastBack.setBackground(Constants.COLOR_BLUE_GENERAL);
		buttonLastBack.setForeground(Color.WHITE);
		buttonLastBack.setToolTipText("go to ferst page");
		buttonLastBack.addActionListener(controller);
		buttonLastBack.setBorderPainted(false);
		buttonLastBack.setFocusPainted(false);
		// buttonLastBack.setActionCommand(Action.BUTTON_LAST_PAGE_BACK.name());
		buttonLastBack.setEnabled(false);

		panelControl.add(buttonBack = new JButton("<"));
		buttonBack.setBackground(Constants.COLOR_GREEN_GENERAL);
		buttonBack.setForeground(Color.WHITE);
		buttonBack.setToolTipText("advance to the previous page");
		buttonBack.addActionListener(controller);
		buttonBack.setBorderPainted(false);
		buttonBack.setFocusPainted(false);
		// buttonBack.setActionCommand(Action.BUTTON_PAGE_BACK.name());
		buttonBack.setEnabled(false);

		panelControl.add(labelCountPage = new JLabel("0/0"));

		panelControl.add(buttonNext = new JButton(">"));
		buttonNext.setBackground(Constants.COLOR_GREEN_GENERAL);
		buttonNext.setForeground(Color.WHITE);
		buttonNext.setToolTipText("advance to the next page");
		buttonNext.addActionListener(controller);
		buttonNext.setBorderPainted(false);
		buttonNext.setFocusPainted(false);
		// buttonNext.setActionCommand(Action.BUTTON_PAGE_NEXT.name());

		panelControl.add(buttonLastNext = new JButton(">>"));
		buttonLastNext.setBackground(Constants.COLOR_BLUE_GENERAL);
		buttonLastNext.setForeground(Color.WHITE);
		buttonLastNext.setToolTipText("go to last page");
		buttonLastNext.addActionListener(controller);
		buttonLastNext.setBorderPainted(false);
		buttonLastNext.setFocusPainted(false);
		// buttonLastNext.setActionCommand(Action.BUTTON_LAST_PAGE_NEXT.name());

		panelControl.setOpaque(false);
		add(panelControl, BorderLayout.PAGE_END);
	}

	public void advanceBackPage(int maxNumberPage) {
		labelCountPage.setText(String.valueOf(getNumberPageCurrent() + "/" + maxNumberPage));
		if (getNumberPageCurrent() == 1) {
			buttonBack.setEnabled(false);
			buttonLastBack.setEnabled(false);
			assignColorDissableBack();
		}
		if (!buttonLastNext.isEnabled()) {
			buttonNext.setEnabled(true);
			buttonLastNext.setEnabled(true);
			assignColorEnableNext();
		}
	}

	public void advanceLastBackPage(int maxNumberPage) {
		labelCountPage.setText(String.valueOf(getNumberPageCurrent() + "/" + maxNumberPage));
		if (buttonLastBack.isEnabled() && numberPageCurrent == 1) {
			buttonBack.setEnabled(false);
			buttonLastBack.setEnabled(false);
			assignColorDissableBack();
		}
		if (!buttonLastNext.isEnabled()) {
			buttonNext.setEnabled(true);
			buttonLastNext.setEnabled(true);
			assignColorEnableNext();
		}
	}

	public void advanceNextPage(int maxNumberPage) {
		labelCountPage.setText(String.valueOf(getNumberPageCurrent() + "/" + maxNumberPage));
		if (getNumberPageCurrent() == maxNumberPage) {
			buttonNext.setEnabled(false);
			buttonLastNext.setEnabled(false);
			assignColorDissableNext();
		}
		if (!buttonLastBack.isEnabled()) {
			buttonBack.setEnabled(true);
			buttonLastBack.setEnabled(true);
			assignColorEnableBack();
		}
	}

	public void advanceLastNextPage(int maxNumberPage) {
		labelCountPage.setText(String.valueOf(getNumberPageCurrent() + "/" + maxNumberPage));
		buttonNext.setEnabled(false);
		buttonLastNext.setEnabled(false);
		assignColorDissableNext();
		if (!buttonLastBack.isEnabled()) {
			buttonBack.setEnabled(true);
			buttonLastBack.setEnabled(true);
			assignColorEnableBack();
		}
	}

	private void assignColorEnableBack() {
		buttonLastBack.setBackground(Constants.COLOR_BLUE_GENERAL);
		buttonLastBack.setForeground(Color.WHITE);
		buttonBack.setBackground(Constants.COLOR_GREEN_GENERAL);
		buttonBack.setForeground(Color.WHITE);
	}

	private void assignColorDissableBack() {
		buttonLastBack.setBackground(getBackground());
		buttonLastBack.setForeground(Color.BLACK);
		buttonBack.setBackground(getBackground());
		buttonBack.setForeground(Color.BLACK);
	}

	private void assignColorEnableNext() {
		buttonLastNext.setBackground(Constants.COLOR_BLUE_GENERAL);
		buttonLastNext.setForeground(Color.WHITE);
		buttonNext.setBackground(Constants.COLOR_GREEN_GENERAL);
		buttonNext.setForeground(Color.WHITE);
	}

	private void assignColorDissableNext() {
		buttonLastNext.setBackground(getBackground());
		buttonLastNext.setForeground(Color.BLACK);
		buttonNext.setBackground(getBackground());
		buttonNext.setForeground(Color.BLACK);
	}

	public int getNumberPageCurrent() {
		return numberPageCurrent;
	}

	public void setNumberPageCurrent(int numberPageCurrent, int maxNumberPage) {
		this.numberPageCurrent = numberPageCurrent;
		advanceLastBackPage(maxNumberPage);
	}

	public void addProductToTable(Product product) {
		defaultTableModel.addRow(product.getArrayContent());
	}

	public void updateTable(ArrayList<Product> listProducts, Point point) {
		defaultTableModel.setRowCount(0);
		for (int i = (int) point.getX(), j = 0; i < (int) point.getY()
				&& j < ((int) point.getY() - (int) point.getX()); i++, j++) {
			addProductToTable(listProducts.get(i));
			int numberForPage = Integer.parseInt(ManagerPersistence.readProperty("numberDataForPage"));
			defaultTableModel.setValueAt(((getNumberPageCurrent() * numberForPage) + j) - (numberForPage - 1), j, 6);
			// buttonRemoveProduct.setActionCommand(Action.BUTTON_REMOVE_PRODUCT.name());
			defaultTableModel.setValueAt(buttonRemoveProduct, j, 7);
			// buttonEditProduct.setActionCommand(Action.BUTTON_EDIT_PRODUCT.name());
			defaultTableModel.setValueAt(buttonEditProduct, j, 8);
			// buttonBioProduct.setActionCommand(Action.BUTTON_BIO_PRODUCT.name());
			defaultTableModel.setValueAt(buttonBioProduct, j, 9);
		}
	}

	public void updateTable(ArrayList<Product> listProducts) {
		defaultTableModel.setRowCount(0);
		for (int i = 0; i < listProducts.size(); i++) {
			addProductToTable(listProducts.get(i));
			int numberForPage = Integer.parseInt(ManagerPersistence.readProperty("numberDataForPage"));
			defaultTableModel.setValueAt(((getNumberPageCurrent() * numberForPage) + i) - (numberForPage - 1), i, 6);
			buttonRemoveProduct.setActionCommand(ActionEnum.BUTTON_REMOVE_PRODUCT.name());
			defaultTableModel.setValueAt(buttonRemoveProduct, i, 7);
			buttonEditProduct.setActionCommand(ActionEnum.BUTTON_EDIT_PRODUCT.name());
			defaultTableModel.setValueAt(buttonEditProduct, i, 8);
			buttonBioProduct.setActionCommand(ActionEnum.BUTTON_PREVIEW_PRODUCT.name());
			defaultTableModel.setValueAt(buttonBioProduct, i, 9);
		}
	}

	public Object[] getListValuesForFilter() {
		return tableToolBar.getListValuesForFilter();
	}

	public void disableButtonsControl() {
		buttonNext.setEnabled(false);
		buttonLastNext.setEnabled(false);
		assignColorDissableNext();
	}

	public void disableButtonsControlBack() {
		buttonBack.setEnabled(false);
		buttonLastBack.setEnabled(false);
		assignColorDissableBack();
	}

	public void enableButtonsControl() {
		buttonNext.setEnabled(true);
		buttonLastNext.setEnabled(true);
		assignColorEnableNext();
	}

	public int getNumberRowsCurrent() {
		return table.getRowCount();
	}

	public int getNumberRowSelect() {
		return table.getSelectedRow();
	}

	public void setNumberPages(int maxNumberPages) {
		labelCountPage.setText(numberPageCurrent + "/" + maxNumberPages);
	}

	public void print() {
		try {
			MessageFormat headerFormat = new MessageFormat("");
			MessageFormat footerFormat = new MessageFormat("- P�gina {0} -");
			table.print(PrintMode.FIT_WIDTH, headerFormat, footerFormat);
		} catch (PrinterException e) {
			e.printStackTrace();
		}
	}

	public void printTable() {
		try {
			MessageFormat headerFormat = new MessageFormat("");
			MessageFormat footerFormat = new MessageFormat("- P�gina {0} -");
			table.print(PrintMode.FIT_WIDTH, headerFormat, footerFormat);
		} catch (PrinterException e) {
			e.printStackTrace();
		}
	}

	public void disableTable() {
		table.setVisible(false);
	}

	public void avalibleTable() {
		table.setVisible(true);
	}

}