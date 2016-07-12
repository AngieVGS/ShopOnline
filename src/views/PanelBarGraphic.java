package views;

import java.awt.Color;
import java.awt.Graphics;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.JPanel;
import models.entity.Purchase;

public class PanelBarGraphic extends JPanel {

	private static final long serialVersionUID = 1L;
	private ArrayList<Purchase> purch;

	public PanelBarGraphic(ArrayList<Purchase> purchase) {
		purch = purchase;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// -----------------------------dibujar
		// ejes----------------------------------------------------------
		g.drawLine(30, 25, 30, getHeight() - 30);
		g.drawLine(30, getHeight() - 30, getWidth() - 55, getHeight() - 30);
		// -----------------------------dibujar limites y
		// textos----------------------------------------------
		g.drawString("0 Am", 30, getHeight() - 10);
		g.drawString("12 pm", getWidth() - 80, getHeight() - 10);
		g.drawString("Hours", getWidth() - 38, getHeight() - 10);
		g.drawString("Purchases", 30, 10);
		// -----------------------------dibujar
		// puntos--------------------------------------------------------
		try {
			int partsOfEjeXInPixels = calculatePartsInPixels(1, 24);
			int partsOfEjeYInPixels = calculatePartsInPixels(0, NumberPurchasesToday());
			int lastPositionx = 30;
			int lastPositiony = getHeight() - 30;

			for (int i = 0; i < 24; i++) {
				g.setColor(Color.blue);
				g.drawLine(lastPositionx, lastPositiony,
						lastPositionx
								+ partsOfEjeXInPixels,
						getHeight() - 30
								- (calculateSalesPerHour((new GregorianCalendar().get(GregorianCalendar.DAY_OF_MONTH)),
										i) * partsOfEjeYInPixels));
				lastPositionx += partsOfEjeXInPixels;
				lastPositiony = (getHeight() - 30)
						- (calculateSalesPerHour(new GregorianCalendar().get(GregorianCalendar.DAY_OF_MONTH), i)
								* partsOfEjeYInPixels);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int calculateSalesPerHour(int day, int hour) {
		int sales = 0;
		for (@SuppressWarnings("unused")
		Purchase purchase : purch) {
			if (day == LocalDateTime.now().getDayOfMonth() && hour == LocalDateTime.now().getHour()) {
				sales++;
			}
		}
		return sales;
	}

	public int NumberPurchasesToday() {

		ArrayList<Purchase> aux = new ArrayList<>();
		for (Purchase purchase : purch) {
			if (12 == (LocalDateTime.now().getDayOfMonth())) {
				aux.add(purchase);
			}
		}
		return aux.size();
	}

	public int calculatePartsInPixels(int a, int parts) {
		if (a == 1) {
			return (getWidth() - 85) / parts;
		} else if (a == 0) {
			return (getHeight() - 55) / parts;
		}
		return 0;
	}
}