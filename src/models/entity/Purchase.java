package models.entity;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Purchase {
	
	private Product product;
	private GregorianCalendar date;
	private int quanty;

	public Purchase(Product product,int quanty) {
	
		this.product = product;
		this.date = new GregorianCalendar(new GregorianCalendar().get(Calendar.YEAR), new GregorianCalendar().get(Calendar.MONTH),new GregorianCalendar().get(Calendar.DATE), new GregorianCalendar().get(Calendar.HOUR), new GregorianCalendar().get(Calendar.MINUTE));//edit to get the current date
		this.quanty = quanty;
	}
	
	public int getQuanty() {
		return quanty;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public GregorianCalendar getDate() {
		return date;
	}

	@SuppressWarnings("deprecation")
	public String returnDate(){
		return date.YEAR + "/" + date.MONTH+ "/" + date.DATE+"//" + date.HOUR + ":" + date.MINUTE;
	}
}