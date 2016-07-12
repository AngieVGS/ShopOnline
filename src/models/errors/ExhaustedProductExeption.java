package models.errors;

public class ExhaustedProductExeption extends Exception {

	private static final long serialVersionUID = 1L;

	public ExhaustedProductExeption() {
		super("The product is Exhausted");
	}
}