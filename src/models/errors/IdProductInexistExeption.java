package models.errors;

public class IdProductInexistExeption extends Exception {

	private static final long serialVersionUID = 1L;

	public IdProductInexistExeption() {
		super("The id in not exist");
	}
}