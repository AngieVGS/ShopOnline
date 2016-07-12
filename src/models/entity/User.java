package models.entity;

import java.util.ArrayList;

import controller.Controller;
import views.Constants;

public class User {

	private static int BASIC_ID;
	private int id;
	private String name;
	private Boolean isEnabled;
	private String password;
	private ArrayList<Purchase> listProductsPurchased;
	private ArrayList<Product> listProductsInTheCar;
	private int numMaxPagePurchase = 0;

	public User(String name, String password) {
		this.id = BASIC_ID++;
		this.name = name;
		isEnabled = true;
		this.password = Encryption.base64encode(password);
		listProductsInTheCar = new ArrayList<>();
		listProductsPurchased = new ArrayList<>();
	}

	public void addPurchase(Purchase purchase) {
		listProductsPurchased.add(purchase);
	}

	public void enabledUser() {
		isEnabled = true;
	}

	public void disableUser() {
		isEnabled = false;
	}

	public int getId() {
		return id;
	}

	public boolean verifyPassword(String password) {
		return Encryption.base64decode(this.password).equals(password);
	}

	public String getName() {
		return name;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public String getPassword() {
		return password;
	}

	public ArrayList<Purchase> getListProductsPurchased() {
		return listProductsPurchased;
	}

	public ArrayList<Product> getListProductsInTheCar() {
		return listProductsInTheCar;
	}

	public ArrayList<Purchase> getPurchase() {
		if (!listProductsPurchased.isEmpty()) {
			if (listProductsPurchased.size() < (Controller.PAGE_PURCHASE + 1)
					* Constants.INIT_PURCHASE_IN_DIALO_PURCHASE_PAGE) {
				return (ArrayList<Purchase>) listProductsPurchased.subList(
						Controller.PAGE_PURCHASE * Constants.INIT_PURCHASE_IN_DIALO_PURCHASE_PAGE,
						(listProductsPurchased.size() - 1));
			}
			return (ArrayList<Purchase>) listProductsPurchased.subList(
					Controller.PAGE_PURCHASE * Constants.INIT_PURCHASE_IN_DIALO_PURCHASE_PAGE,
					(Controller.PAGE_PURCHASE + 1) * Constants.INIT_PURCHASE_IN_DIALO_PURCHASE_PAGE);
		}
		return listProductsPurchased;
	}

	public void reloadPagePurchase() {
		numMaxPagePurchase = (int) Math
				.ceil(listProductsPurchased.size() / Constants.INIT_PURCHASE_IN_DIALO_PURCHASE_PAGE);
	}

	public int getNumMaxPagePurchase() {
		return numMaxPagePurchase;
	}
}