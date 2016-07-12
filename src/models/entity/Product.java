package models.entity;

public class Product {
	private static int ID_STATIC = 0;
	private int id;
	private String name;
	private double price;
	private String imagePath;
	private int quantumAvailable;
	private Category category;
	private double percentageOfProfit;

	public Product(String name, double price, String imagePath, int quantumAvailable, Category category,
			double percentageOfProfit) {
		this.id = ID_STATIC++;
		this.name = name;
		this.price = price;
		this.imagePath = imagePath;
		this.quantumAvailable = quantumAvailable;
		this.category = category;
		this.percentageOfProfit = percentageOfProfit;
	}

	public int getId() {
		return id;
	}

	public int getQuantumAvailable() {
		return quantumAvailable;
	}

	public void setQuantumAvailable(int quantumAvailable) {
		this.quantumAvailable = quantumAvailable;
	}

	public Category getCategory() {
		return category;
	}

	public String getName() {
		return name;
	}

	public double getPercentageOfProfit() {
		return percentageOfProfit;
	}

	public double getPrice() {
		return price;
	}

	public void increaseStock() {
		quantumAvailable++;
	}

	public void decreaseStock() {
		quantumAvailable--;
	}

	public String getImagePath() {
		return imagePath;
	}

	public Object[] getArrayContent() {
		return new Object[] { getId(), getName(), getPrice() + "$", getQuantumAvailable(), getCategory(),
				getPercentageOfProfit() + "%" };
	}
}