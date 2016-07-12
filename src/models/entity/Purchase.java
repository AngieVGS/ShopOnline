package models.entity;

import java.time.LocalDateTime;
import java.util.GregorianCalendar;

public class Purchase {

	private Product product;
	private GregorianCalendar date;
	private int quanty;

	public Purchase(Product product, int quanty) {

		this.product = product;
		this.date = new GregorianCalendar(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(),
				LocalDateTime.now().getDayOfMonth(), LocalDateTime.now().getHour(), LocalDateTime.now().getMinute());
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

	@SuppressWarnings("static-access")
	public String returnDate() {
		return date.YEAR + "/" + date.MONTH + "/" + date.DATE + "//" + date.HOUR + ":" + date.MINUTE;
	}
}