package models.entity;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Purchase {
	
	private Product product;
	private GregorianCalendar date;
	private int quanty;

	public Purchase(Product product,int quanty) {
	
		this.product = product;
		this.date = new GregorianCalendar(new GregorianCalendar().YEAR, new GregorianCalendar().MONTH,new GregorianCalendar().DAY_OF_MONTH, new GregorianCalendar().HOUR, new GregorianCalendar().MINUTE);//edit to get the current date
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