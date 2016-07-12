package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import models.dao.Shop;
import models.entity.Category;
import models.entity.Product;
import models.entity.Purchase;
import models.entity.User;
import models.errors.IdProductInexistExeption;
import models.errors.idRegistered;
import persistence.ManagerPersistence;
import views.DeatailsPanel;
import views.DialogAdd;
import views.DialogLogin;
import views.DialogPurchase;
import views.DialogShoppingCar;
import views.DialogStatistics;
import views.MainWindow;
import views.PanelObjectShoppingCar;
import views.PanelPointsGraphic;

public class Controller implements ActionListener, MouseListener {

	Shop shop;
	MainWindow mainWindow;
	DialogAdd dialogAdd;
	DialogShoppingCar shoppingCar;
	DialogLogin dialogLogin;
	DialogStatistics statistics;
	boolean addOrCreate;
	private int page;
	public static int PAGE_PURCHASE = 0;
	public static int PAGE_MAX_PURCHASE = 0;
	DialogPurchase dialogPurchase;
	PanelPointsGraphic panelPointsGraphic;

	public Controller() {
		page = 0;
		loadPersistenceIfExists();
		mainWindow = new MainWindow(this);
		mainWindow.setVisible(false);
		dialogAdd = new DialogAdd(this, mainWindow);
		shoppingCar = new DialogShoppingCar(this, mainWindow);
		dialogLogin = new DialogLogin(this, mainWindow);
		dialogPurchase = new DialogPurchase(this, mainWindow);
		saveAdmin();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (ActionEnum.valueOf(e.getActionCommand())) {
		case BUTTON_CHANGE_BETWEEN_USER_AND_ADMIN:
			dialogLogin.changeMode();
			break;
		case SHOW_CREATE_DIALOG:
			addProduct();
			break;
		case CHANGE_IMAGE_LOCAL_SERVER:
			changeImagenLocalOnDialogAdd();
			break;
		case CHANGE_IMAGE_WEB:
			changeImagenWebOnDialogAdd();
			break;
		case CREATE:
			createProductOrEditProduct();
			break;
		case CANCEL_CREATION:
			hideAndCleanAddDialog();
			break;
		case CHANGE_MODE:
			dialogLogin.clean();
			dialogLogin.setVisible(true);
			break;
		case ADD_TO_CAR:
			addToCAr(Integer.parseInt(((JButton) e.getSource()).getText().substring(13)), "David");
			break;
		case BUY:
			buy();
			break;
		case REMOVE_OF_THE_CAR:
			removeOFTheCar(0);
			break;
		case OPEN_CAR:
			shoppingCar.setVisible(true);
			break;
		case DELETE_PRODUCT:
			deleteProductFromAdminPerspective();
			break;
		case EDIT_PRODUCT:
			editProduct();
			break;
		case SEARCH:
			searchProduct();
			break;
		case VIEW_PRODUCT:
			viewProduct();
			break;
		case NEXT_PAGE:
			nextPage();
			break;
		case PREV_PAGE:
			prevPage();
			break;
		// case FILTER:
		// filter();
		// break;
		case FILTER:
			filter();
			break;
		case LOGIN:
			login();
			break;
		case REGISTER:
			register();
			break;
		case VIEW_PURCHASE:
			reloadListOfPurchase("j1");
			break;
		case NEXT_PURCHASE:
			nextPurchase("j1");
			break;
		case PREV_PURCHASE:
			prevPurchase("j1");
			break;
		case SHOW_STATISTICS:
			showStatistics();
			break;
		case BUTTON_PRINT:
			printTable();
			break;
		case BUTTON_BIO_PRODUCT:
			break;
		case BUTTON_EDIT_PRODUCT:
			break;
		case BUTTON_PAPERS:
			mainWindow.disableTable();
			break;
		case BUTTON_PREVIEW_PRODUCT:
			break;
		case BUTTON_REMOVE_PRODUCT:
			break;
		case SHOW_CLIENTS:
			mainWindow.disableTable();
			break;
		case SHOW_TABLE:
			mainWindow.avalibleTable();
			break;

		}
	}

	private void printTable() {
		mainWindow.printTable();
	}

	private void filter() {
		try {
			Object[] values = mainWindow.getListValuesForFilter();
			if (String.valueOf(values[0]).equals("")) {
				values[0] = "-1";
			}
			shop.setListProductsFilter(shop.getListProductsForFilter(Integer.parseInt((String) values[0]),
					(String) values[1], (double) values[2], (double) values[3], (Category) values[4]));
		} catch (Exception e) {
			mainWindow.getMessageErrorPrice();
			return;
		}
		mainWindow.revalidateTableWithSpecificItems(shop.getListProductsFilter());
	}

	private void showStatistics() {
		try {
			ArrayList<Purchase> purchases = ManagerPersistence.purchases();
			statistics = new DialogStatistics(purchases);
			statistics.setSize(700, 400);
			statistics.setTitle("Statistics");
			statistics.setVisible(true);
			panelPointsGraphic = new PanelPointsGraphic(purchases);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} // colocal el parameetro
	}

	private void saveAdmin() {
		try {
			ManagerPersistence.writeAdministrator(shop.getAdministrator());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void register() {
		User user = Shop.createUser(JOptionPane.showInputDialog(null, "User's name"),
				JOptionPane.showInputDialog(null, "User's password"));
		shop.registerUser(user);
		try {
			ManagerPersistence.writeUsers(shop.getListUsers());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void login() {
		dialogLogin.cleanToErrors();
		String user = dialogLogin.getUser();
		String enteredPassword = dialogLogin.getEnteredPassword();
		if (user.equals("")) {
			dialogLogin.aminateInvalidUser();
			return;
		}
		if (enteredPassword.equals("")) {
			dialogLogin.aminateInvalidPasword();
			return;
		}
		if (dialogLogin.adminOrUser()) {
			if (shop.getAdministrator().verifyPassword(enteredPassword)) {
				dialogLogin.setVisible(false);
				mainWindow.setVisible(true);
				mainWindow.switchBetweenAdminAndUserView(true);
				mainWindow.revalidateTableWithSpecificItems(shop.getListProducts());
				mainWindow.revalidate();
			} else {
				dialogLogin.animateIncorrectUserOrPassword();
			}
		} else {
			int index = shop.searchUserToGetIndex(user);
			if (index != -1) {
				if (shop.getListUsers().get(index).verifyPassword(enteredPassword)) {
					dialogLogin.setVisible(false);
					mainWindow.setVisible(true);
					mainWindow.switchBetweenAdminAndUserView(false);
					try {
						mainWindow.revalidateGridWithSpecificItems(shop.getListProducts());
					} catch (IOException e) {
						e.printStackTrace();
					}
					mainWindow.revalidate();
				}
			} else {
				dialogLogin.animateIncorrectUserOrPassword();
			}
		}
	}

	private void buy() {
		JOptionPane.showMessageDialog(mainWindow, "Purchase made");
		shoppingCar.setVisible(false);
		mainWindow.resetItemsOnCar();
		// reloadPurchaseMaxPage("j1");
	}

	private void addToCAr(int id, String user) {
		if (shop.searchProductById(id).getQuantumAvailable() > 0) {
			mainWindow.increaseItemsOnCar();
			shoppingCar.add(new PanelObjectShoppingCar(shop.searchProductById(id), this));
			shop.searchProductById(id).decreaseStock();
			Purchase purchase = new Purchase(shop.searchProductById(id), 1);
			shop.searchUser(user).addPurchase(purchase);
			shop.addBuy(purchase);
			try {
				ManagerPersistence.writeUsers(shop.getListUsers());
				ManagerPersistence.writeProducts(shop.getListProducts());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void nextPage() {
		page++;
		changePage(page);
	}

	private void prevPage() {
		if (page > 0) {
			page--;
		}
		changePage(page);
	}

	private void changePage(int page) {
		// try {
		// mainWindow.changePage(page, productDao.getProducts());
		// } catch (IOException e) {
		// JOptionPane.showMessageDialog(mainWindow, e);
		// }
	}

	private void viewProduct() {
		DeatailsPanel details = new DeatailsPanel(shop.getListProducts().get(mainWindow.returnTheSelectedId()));
		details.setVisible(true);
	}

	private void searchProduct() {
		// if (shop.getIsUserIsAdmin() == true) {
		// mainWindow.revalidateTableWithSpecificItems(shop.searchSimilars(mainWindow.getEnteredText()));
		// } else {
		// try {
		// mainWindow.revalidateGridWithSpecificItems(shop.searchSimilars(mainWindow.getEnteredText()));
		// } catch (IOException e) {
		// JOptionPane.showMessageDialog(mainWindow, e);
		// }
		// }
	}

	private void addProduct() {
		addOrCreate = true;
		dialogAdd.setVisible(true);
	}

	private void editProduct() {
		if (JOptionPane.showConfirmDialog(mainWindow, "Really want to EDIT this product?", "Remove Product",
				JOptionPane.WARNING_MESSAGE) == 0) {
			addOrCreate = false;
			dialogAdd.setVisible(true);
		}
	}

	private void deleteProductFromAdminPerspective() {
		try {
			shop.deleteProduct(mainWindow.returnTheSelectedId());
		} catch (IdProductInexistExeption e) {
			e.printStackTrace();
		}
		mainWindow.removeProductOfTheTable();
		try {
			ManagerPersistence.writeProducts(shop.getListProducts());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void removeOFTheCar(int id) {
		shoppingCar.removeOfTheCar(id);
	}

	private void hideAndCleanAddDialog() {
		this.dialogAdd.setVisible(false);
		this.dialogAdd.clean();
	}

	private void loadPersistenceIfExists() {
		shop = new Shop();
		try {
			shop.setListProducts(ManagerPersistence.readProducts());
			shop.setListUsers(ManagerPersistence.readUsers());
		} catch (FileNotFoundException e) {
			System.out.println("La excepcion se origino en loadPersistenceIfExists");
		}
	}

	private void reloadListOfProducts() {
		mainWindow.deleteAllValuesofTheTable();
		ArrayList<Product> productsListAuxiliar = shop.getListProducts();
		if (productsListAuxiliar.size() > 0) {
			for (int i = 0; i < productsListAuxiliar.size(); i++) {
				mainWindow.addProductToTableOfAdmin(productsListAuxiliar.get(i));
			}
		}
	}

	private void createProductOrEditProduct() {
		if (dialogAdd.verifyIfThereIsNoErrors()) {
			Product product = dialogAdd.returnCreatedProduct();
			if (addOrCreate) {
				mainWindow.addProductToTableOfAdmin(product);
				try {
					shop.addProduct(product);
				} catch (idRegistered e) {
					e.printStackTrace();
				}
				hideAndCleanAddDialog();
				dialogAdd.setErrorMesage(Color.BLACK, "Creation panel");
				try {
					ManagerPersistence.writeProducts(shop.getListProducts());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					shop.editProduct(mainWindow.returnTheSelectedId(), product);
				} catch (IdProductInexistExeption e) {
					e.printStackTrace();
				}
				hideAndCleanAddDialog();
				dialogAdd.setErrorMesage(Color.BLACK, "Creation panel");
				try {
					ManagerPersistence.writeProducts(shop.getListProducts());
				} catch (IOException e) {
					e.printStackTrace();
				}
				reloadListOfProducts();
			}
		} else {
			dialogAdd.setErrorMesage(Color.RED, "Error");
		}
	}

	private void changeImagenWebOnDialogAdd() {
		try {
			dialogAdd.changeImageWeb();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void changeImagenLocalOnDialogAdd() {
		try {
			dialogAdd.changeImageSrc();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void reloadListOfPurchase(String user) {
		dialogPurchase.removeAllPurchase();
		ArrayList<Purchase> purchaseList = shop.searchUser(user).getPurchase();
		System.out.println(user);
		if (!purchaseList.isEmpty()) {
			System.out.println("entre");
			for (Purchase purchase : purchaseList) {
				dialogPurchase.addPurchase(purchase);
			}
		}
		dialogPurchase.setVisible(true);
	}

	private void nextPurchase(String userName) {
		PAGE_PURCHASE++;
		reloadListOfPurchase(userName);
		dialogPurchase.revalidate();
	}

	private void prevPurchase(String user) {
		PAGE_PURCHASE--;
		reloadListOfPurchase(user);
		dialogPurchase.revalidate();
	}

	private void viewProduct(int id) {
		if (JOptionPane.showConfirmDialog(mainWindow, "Really want to PREVIEW this product?", "Remove Product",
				JOptionPane.WARNING_MESSAGE) == 0) {
			DeatailsPanel details = new DeatailsPanel(shop.getListProducts().get(id));
			details.setVisible(true);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	private void actionButtonRemoveProduct(int id) {
		if (JOptionPane.showConfirmDialog(mainWindow, "Really want to REMOVE this product?", "Remove Product",
				JOptionPane.WARNING_MESSAGE) == 0) {
			try {
				shop.deleteProduct(id);
			} catch (IdProductInexistExeption e) {
				JOptionPane.showMessageDialog(mainWindow, "The Product Not Exist");
			}
			shop.setListProductsFilter(shop.getListProducts());
			// ManagerPersistence.writeProductsInJson(shop.getListProducts());
			mainWindow.revalidateTableWithSpecificItems(shop.getListProductsFilter());
			try {
				ManagerPersistence.writeProducts(shop.getListProducts());
			} catch (IOException e) {
				e.printStackTrace();
			}
			// numberMaxPages(shop.getListProductsFilter());
			// windowAdministrator.setFormatPages(MAX_NUMBER_PAGES);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Object objectSelectedInTable = ((JTable) e.getComponent()).getModel().getValueAt(0,
				((JTable) e.getComponent()).getSelectedColumn());

		if (objectSelectedInTable.getClass().equals(JButton.class)) {
			int columnCount = ((JTable) e.getComponent()).columnAtPoint(e.getPoint());
			int id = 0;
			for (int i = 0; i < columnCount; i++) {
				if (((JTable) e.getComponent()).getModel().getColumnName(i).equals("ID")) {
					id = (Integer) ((JTable) e.getComponent()).getModel().getValueAt(mainWindow.getNumberRowSelect(),
							i);
				}
			}
			switch (ActionEnum.valueOf(((JButton) objectSelectedInTable).getActionCommand())) {
			case BUTTON_REMOVE_PRODUCT:
				actionButtonRemoveProduct(id);
				break;
			case BUTTON_EDIT_PRODUCT:
				editProduct();
				break;
			case BUTTON_PREVIEW_PRODUCT:
				viewProduct(id);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}