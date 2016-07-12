package models.errors;

public class idRegistered extends Exception {

	private static final long serialVersionUID = 1L;
	
	public idRegistered() {
		super("There is a product registered with that id");
	}
}