package persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import models.dao.Shop;
import models.entity.Administrator;
import models.entity.Category;
import models.entity.Product;
import models.entity.Purchase;
import models.entity.User;

public class ManagerPersistence {

	public static final String PATH_NAME_PRODUCTS = "src/data/products.json";
	public static final String PATH_NAME_USERS = "src/data/users.json";
	public static final String PATH_NAME_ADMIN = "src/data/admin.json";
	private static final String PATH_NAME_PROPERTIES = "src/data/config.properties";

	public static void writeProperty(String key, String value) {
		Properties properties = new Properties();
		try {
			properties.setProperty("numberDataForPage", readProperty("numberDataForPage"));
			FileWriter fileWriter = new FileWriter(new File(PATH_NAME_PROPERTIES));
			properties.setProperty(key, value);
			properties.store(fileWriter, null);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readProperty(String key) {
		Properties properties = new Properties();
		try {
			FileReader fileReader = new FileReader(new File(PATH_NAME_PROPERTIES));
			properties.load(fileReader);
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties.getProperty(key);
	}

	public static void writeProducts(ArrayList<Product> listProducts) throws IOException {
		Gson gson = new Gson();
		String json = gson.toJson(listProducts);
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(PATH_NAME_PRODUCTS));
		bufferedWriter.write(json);
		bufferedWriter.close();
	}

	public static ArrayList<Product> readProducts() throws FileNotFoundException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_NAME_PRODUCTS));
		Gson gson = new Gson();
		JsonArray jsonArray = (JsonArray) gson.fromJson(bufferedReader, JsonArray.class);
		ArrayList<Product> listProducts = new ArrayList<>();
		for (JsonElement jsonElement : jsonArray) {
			listProducts.add(Shop.createProduct(jsonElement.getAsJsonObject().get("name").getAsString(),
					jsonElement.getAsJsonObject().get("price").getAsDouble(),
					jsonElement.getAsJsonObject().get("imagePath").getAsString(),
					jsonElement.getAsJsonObject().get("quantumAvailable").getAsInt(),
					Category.valueOf(jsonElement.getAsJsonObject().get("category").getAsString()),
					jsonElement.getAsJsonObject().get("percentageOfProfit").getAsDouble()));
		}
		return listProducts;
	}

	public static ArrayList<Purchase> purchases() throws FileNotFoundException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_NAME_USERS));
		Gson gson = new Gson();
		JsonArray jsonArray = (JsonArray) gson.fromJson(bufferedReader, JsonArray.class);
		ArrayList<Purchase> listUsers = new ArrayList<>();
		if (!(jsonArray.size() == 0)) {
			for (JsonElement jsonElement : jsonArray) {
				for (JsonElement purchase : jsonElement.getAsJsonObject().get("listProductsPurchased")
						.getAsJsonArray()) {
					if (jsonElement.getAsJsonObject().get("listProductsPurchased").getAsJsonArray().size() > 0) {
						Product produ = Shop.createProduct(
								purchase.getAsJsonObject().get("product").getAsJsonObject().get("name").getAsString(),
								purchase.getAsJsonObject().get("product").getAsJsonObject().get("price").getAsDouble(),
								purchase.getAsJsonObject().get("product").getAsJsonObject().get("imagePath")
										.getAsString(),
								purchase.getAsJsonObject().get("product").getAsJsonObject().get("quantumAvailable")
										.getAsInt(),
								Category.valueOf(purchase.getAsJsonObject().get("product").getAsJsonObject()
										.get("category").getAsString()),
								purchase.getAsJsonObject().get("product").getAsJsonObject().get("percentageOfProfit")
										.getAsDouble());
						int quanty = purchase.getAsJsonObject().get("quanty").getAsInt();
						Purchase pr = new Purchase(produ, quanty);
						listUsers.add(pr);
					}
				}

			}
		}
		return listUsers;
	}

	public static void writeUsers(ArrayList<User> listUsers) throws IOException {
		Gson gson = new Gson();
		String json = gson.toJson(listUsers);
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(PATH_NAME_USERS));
		bufferedWriter.write(json);
		bufferedWriter.close();
	}

	public static ArrayList<User> readUsers() throws FileNotFoundException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_NAME_USERS));
		Gson gson = new Gson();
		JsonArray jsonArray = (JsonArray) gson.fromJson(bufferedReader, JsonArray.class);
		ArrayList<User> listUsers = new ArrayList<>();
		for (JsonElement jsonElement : jsonArray) {
			listUsers.add(Shop.createUser(jsonElement.getAsJsonObject().get("name").getAsString(),
					jsonElement.getAsJsonObject().get("password").getAsString()));
		}
		return listUsers;
	}

	public static void writeAdministrator(Administrator administrator) throws IOException {
		Gson gson = new Gson();
		String json = gson.toJson(administrator);
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(PATH_NAME_ADMIN));
		bufferedWriter.write(json);
		bufferedWriter.close();
	}

	public static Administrator readAdministrator() throws FileNotFoundException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_NAME_ADMIN));
		Gson gson = new Gson();
		JsonObject jsonObject = (JsonObject) gson.fromJson(bufferedReader, JsonObject.class);
		return new Administrator(jsonObject.getAsJsonObject().get("id").getAsInt(),
				jsonObject.getAsJsonObject().get("name").getAsString(),
				jsonObject.getAsJsonObject().get("password").getAsString());
	}

}
