package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import models.dao.Shop;
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

public class Controller implements ActionListener {

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
		default:
			break;

		}

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
			// TODO Auto-generated catch block
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
		addOrCreate = false;
		dialogAdd.setVisible(true);
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

}