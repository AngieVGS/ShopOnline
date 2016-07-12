//package persistence;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import com.google.gson.Gson;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//
//import models.dao.Catalog;
//import models.entity.Brand;
//import models.entity.Category;
//import models.errors.PermistRequired;
//
//public class ManagerJson {
//
//	public static void write(Catalog catalog) {
//		Gson gson = new Gson();
//		String json = gson.toJson(catalog);
//		try {
//			FileWriter writer = new FileWriter("src/data/catalog.json");
//			writer.write(json);
//			writer.close();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static Catalog read() throws IOException, PermistRequired {
//		Catalog catalog = new Catalog();
//		BufferedReader br = new BufferedReader(new FileReader("src/data/catalog.json"));
//		Gson gson = new Gson();
//		catalog.setUserIsAdmin(true);
//		JsonArray products = gson.fromJson(br, JsonObject.class).get("products").getAsJsonArray();
//		for (JsonElement jsonElement : products) {
//			catalog.add(Catalog.create(jsonElement.getAsJsonObject().get("name").getAsString(),
//					jsonElement.getAsJsonObject().get("stockBase").getAsInt(),
//					jsonElement.getAsJsonObject().get("description").getAsString(),
//					jsonElement.getAsJsonObject().get("imagePath").getAsString(),
//					jsonElement.getAsJsonObject().get("price").getAsDouble(),
//					Brand.valueOf(jsonElement.getAsJsonObject().get("brand").getAsString().toUpperCase()),
//					Category.valueOf(jsonElement.getAsJsonObject().get("category").getAsString().toUpperCase())));
//		}
//		return catalog;
//	}
//}