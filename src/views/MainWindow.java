package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import controller.Controller;
import models.entity.Product;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JPanel mainPanel;
	private JPanel panelPage;
	HeaderPanels headerPanel;

	public MainWindow(Controller controller) {
		UI();
		setIconImage(new ImageIcon(getClass().getResource(Constants.LOGO_PATH)).getImage());
		this.controller = controller;
		setTitle("Shop");
		setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		// *****************headerPanel*****************************************************
		headerPanel = new HeaderPanels(controller);
		add(headerPanel, BorderLayout.PAGE_START);

		mainPanel = new AdminPanel(controller);
		add(mainPanel, BorderLayout.CENTER);

		// *****************panel page****************************************************
		panelPage = new JPanel();
		add(panelPage, BorderLayout.PAGE_END);
		setVisible(true);
	}

	public void switchBetweenAdminAndUserView(boolean admin) {
		if (admin) {
			mainPanel.setOpaque(false);
			mainPanel.setEnabled(false);
			mainPanel.setVisible(false);
			mainPanel.removeAll();
			mainPanel = new AdminPanel(controller);
			;
			add(mainPanel, BorderLayout.CENTER);
		} else {
			mainPanel.setOpaque(false);
			mainPanel.setEnabled(false);
			mainPanel.setVisible(false);
			mainPanel.removeAll();
			mainPanel = new PanelProduct(controller);
			add(mainPanel, BorderLayout.CENTER);
		}
	}

	public void addProduct(Product product) throws IOException {
		((PanelProduct) mainPanel).addProduct(product);
		mainPanel.revalidate();
	}

	public void addRemainingPanels() {
		((PanelProduct) mainPanel).addRemainingPanels();
	}

	public void deleteAllValuesofTheTable() {
		((AdminPanel) mainPanel).deleteAllValuesofTheTable();
	}

	public void addProductToTableOfAdmin(Product product) {
		((AdminPanel) mainPanel).addProduct(product);
	}

	public int returnTheSelectedId() {
		return ((AdminPanel) mainPanel).returnTheSelectedId();
	}

	public void removeProductOfTheTable(){
		((AdminPanel) mainPanel).deleteProduct();
	}

	private void UI() {
		UIManager.put("Button.background", Color.decode(Constants.COLOR_BUTTON));
		UIManager.put("Button.foreground", Color.decode(Constants.BACKGROUND_COLOR_MAIN));
		UIManager.put("Button.borderPainted", false);
	}

	public String getEnteredText() {
		return headerPanel.getEnteredText();
	}

	public void revalidateTableWithSpecificItems(ArrayList<Product> products) {
		((AdminPanel) mainPanel).revalidateTableWithSpecificItems(products);
	}

	public void revalidateGridWithSpecificItems(ArrayList<Product> products) throws IOException {
		((PanelProduct) mainPanel).replaceAllProductsInGridWithSpecificOnes(products);
	}

	public void changePage(int page, ArrayList<Product> products) throws IOException {
		((PanelProduct) mainPanel).changePage(page, products);
	}

	public void increaseItemsOnCar() {
		headerPanel.add();
	}

	public void resetItemsOnCar() {
		headerPanel.reset();
	}
}