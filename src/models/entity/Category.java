package models.entity;

public enum Category {
	OTHER, TECHNOLOGY, HOME, FOOD, PETS, TEST, ALL;

	@Override
	public String toString() {
		String name = name().replaceAll("_", " ").toLowerCase();
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		return name;
	}
}