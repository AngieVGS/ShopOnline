package models.dao;

import java.util.ArrayList;
import java.util.List;
import models.entity.Administrator;
import models.entity.Category;
import models.entity.Product;
import models.entity.Purchase;
import models.entity.User;
import models.errors.ExhaustedProductExeption;
import models.errors.IdProductInexistExeption;
import models.errors.idRegistered;

public class Shop {
	private ArrayList<Product> listProducts;
	private ArrayList<Product> listProductsFilter;
	private ArrayList<User> listUsers;
	private ArrayList<Purchase> listPurchase;
	private Administrator administrator;

	public Shop() {
		administrator = new Administrator(01, "Admin", "123");
		listProducts = new ArrayList<>();
		listProductsFilter = new ArrayList<>();
		listPurchase = new ArrayList<>();
		listUsers = new ArrayList<>();

	}

	private boolean idCheck(Product product) {
		for (Product product1 : listProducts) {
			if (product1.getId() == product.getId()) {
				return true;
			}
		}
		return false;
	}

	public static Product createProduct(String name, double price, String imagePath, int quantumAvailable,
			Category category, double percentageOfProfit) {
		return new Product(name, price, imagePath, quantumAvailable, category, percentageOfProfit);
	}

	public void addProduct(Product product) throws idRegistered {
		if (!idCheck(product)) {
			listProducts.add(product);
		} else {
			throw new idRegistered();
		}
	}

	public Product searchProductById(int id) {
		for (int i = 0; i < listProducts.size(); i++) {
			if (listProducts.get(i).getId() == id) {
				return listProducts.get(i);
			}
		}
		return null;
	}

	public boolean isIdExist(int id) {
		for (Product product : listProducts) {
			if (product.getId() == id) {
				return true;
			}
		}
		return false;
	}

	public void editProduct(int id, Product product) throws IdProductInexistExeption {
		for (int i = 0; i < listProducts.size(); i++) {
			if (listProducts.get(i).getId() == id) {
				listProducts.remove(i);
				listProducts.add(i, product);
				return;
			}
		}
		throw new IdProductInexistExeption();
	}

	public void deleteProduct(int id) throws IdProductInexistExeption {
		for (Product product : listProducts) {
			if (product.getId() == id) {
				listProducts.remove(product);
				return;
			}
		}
		throw new IdProductInexistExeption();
	}

	public void saleProduct(int id) throws ExhaustedProductExeption {
		for (Product product : listProducts) {
			if (product.getId() == id && product.getQuantumAvailable() > 0) {
				product.setQuantumAvailable(product.getQuantumAvailable() - 1);
				return;
			}
		}
		throw new ExhaustedProductExeption();
	}

	public ArrayList<Product> searchProductForName(String name) {
		ArrayList<Product> listProductsResult = new ArrayList<>();
		for (Product product : listProducts) {
			if (product.getName().contains(name)) {
				listProductsResult.add(product);
			}
		}
		return listProductsResult;
	}

	public ArrayList<Product> searchProductForPrice(int priceMin, int priceMax) {
		ArrayList<Product> listProductsResult = new ArrayList<>();
		for (Product product : listProducts) {
			if (product.getPrice() > priceMin && product.getPrice() < priceMin) {
				listProductsResult.add(product);
			}
		}
		return listProductsResult;
	}

	public ArrayList<Product> searchProductForCategory(Category category) {
		ArrayList<Product> listProductsResult = new ArrayList<>();
		for (Product product : listProducts) {
			if (product.getCategory().equals(category)) {
				listProductsResult.add(product);
			}
		}
		return listProductsResult;
	}

	public ArrayList<Product> getListProducts() {
		return listProducts;
	}

	public void setListUsers(ArrayList<User> listUsers) {
		this.listUsers = listUsers;
	}

	public void setListProducts(ArrayList<Product> listProducts) {
		this.listProducts = listProducts;
	}

	public ArrayList<Product> getListProductsForFilter(int id, String name, double priceMin, double priceMax, Category category) {
		ArrayList<Product> listProductsResult = new ArrayList<>();
		for (Product product : listProducts) {
			if (id > 0) {
				if (product.getId() == id && product.getName().contains(name) && (product.getPrice() >= priceMin && product.getPrice() <= priceMax)) {
					if (category.equals(Category.OTHER) || category.equals(Category.ALL) || product.getCategory().equals(category)) {
						listProductsResult.add(product);
					}
				}
			}else {
				if (product.getName().contains(name) && (product.getPrice() >= priceMin && product.getPrice() <= priceMax)) {
					if (category.equals(Category.OTHER) || category.equals(Category.ALL) || product.getCategory().equals(category)) {
						listProductsResult.add(product);
					}
				}
			}
		}
		return listProductsResult;
	}

	public ArrayList<Product> getListProductsFilter() {
		return listProductsFilter;
	}

	public void setListProductsFilter(ArrayList<Product> listProductsFilter) {
		this.listProductsFilter = listProductsFilter;
	}

	public Product findProductForId(int id) {
		for (Product product : listProductsFilter) {
			if (product.getId() == id) {
				return product;
			}
		}
		return null;
	}

	public Administrator getAdministrator() {
		return administrator;
	}

	public ArrayList<User> getListUsers() {
		return listUsers;
	}

	public static User createUser(String name, String password) {
		return new User(name, password);
	}

	public void registerUser(User user) {
		listUsers.add(user);
	}

	public int searchUserToGetIndex(String name) {
		int index = -1;
		for (int i = 0; i < listUsers.size(); i++) {
			if (listUsers.get(i).getName().equals(name)) {
				index = i;
			}
		}
		return index;
	}

	public User searchUser(String name) {
		for (int i = 0; i < listUsers.size(); i++) {
			if (listUsers.get(i).getName().equals(name)) {
				return listUsers.get(i);
			}
		}
		return null;
	}

	public void enableUser(User user) {
		for (User user2 : listUsers) {
			if (user2.getId() == user.getId()) {
				user.enabledUser();
			}
		}
	}

	public void disableUser(User user) {
		for (User user2 : listUsers) {
			if (user2.getId() == user.getId()) {
				user.disableUser();
			}
		}
	}

	public static Purchase createBuy(Product product, int quanty) {
		return new Purchase(product, quanty);
	}

	public void addBuy(Purchase purchase) {
		listPurchase.add(purchase);
	}

	private int contarNumeroDeProductosVendidos(Product product) {
		int num = 0;
		for (Purchase purchase : listPurchase) {
			if (purchase.getProduct().getId() == product.getId()) {
				num += purchase.getQuanty();
			}
		}
		return num;
	}

	public ArrayList<Product> deleteRepeatedProducts(ArrayList<Purchase> purchases) {
		ArrayList<Product> selledProducts = new ArrayList<>();
		ArrayList<Product> productsCopy = new ArrayList<>();
		for (int i = 0; i < purchases.size(); i++) {
			productsCopy.add(purchases.get(i).getProduct());
		}
		for (int i = 0; i < productsCopy.size(); i++) {
			selledProducts.add(productsCopy.get(i));
			Product auxiliar = productsCopy.get(i);
			for (int j = 0; j < productsCopy.size(); j++) {
				if (productsCopy.get(j).equals(auxiliar)) {
					productsCopy.set(j, new Product(null, 0, null, 0, Category.ALL, 0));
				}
			}
		}
		return removeNull(selledProducts);
	}

	public ArrayList<Product> removeNull(ArrayList<Product> listProduct) {
		ArrayList<Product> aux = new ArrayList<>();
		for (int i = 0; i < listProduct.size(); i++) {
			if (listProduct.get(i).getId() != 0) {
				aux.add(listProduct.get(i));
			}
		}
		return aux;
	}

	public ArrayList<Integer> getRepetitionWord(ArrayList<Purchase> buys, ArrayList<Purchase> buysOriginal) {
		ArrayList<Integer> aux = new ArrayList<>();
		for (int i = 0; i < buys.size(); i++) {
			aux.add(contarNumeroDeProductosVendidos(buys.get(i).getProduct()));
		}
		return aux;
	}

	public ArrayList<Integer> getProductsSold(List<Product> products) {
		ArrayList<Integer> numberProductsSold = new ArrayList<>();
		for (int i = 0; i < products.size(); i++) {
			numberProductsSold.add(contarNumeroDeProductosVendidos(products.get(i)));
		}
		return numberProductsSold;
	}

	public void imprimir(List<Product> buys) {
		for (int i = 0; i < buys.size(); i++) {
			System.out.println(buys.get(i).getId() + "----->" + contarNumeroDeProductosVendidos(buys.get(i)));
		}
	}
}