package views;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.JPanel;

import models.entity.Product;
import models.entity.Purchase;

public class PanelBestSellersProducts extends JPanel {

	private static final long serialVersionUID = 1L;
	/**
	 * diseñado para mostrar el top de productos mas vendidos
	 */
	private ArrayList<Purchase> purch;
	
	public PanelBestSellersProducts(ArrayList<Purchase> purchase) {
		purch = purchase;
	}	
	
	public Product[] searchTop() {
		Product[] pr = new Product[10];
		
		return pr;
	}
	
	public void searchRepeats(){
		
	}

	@Override
	protected void paintComponent(Graphics g) {		
		super.paintComponent(g);
		//-----------------------------dibujar ejes----------------------------------------------------------
		g.drawLine(30,25,30,getHeight()-30);
		g.drawLine(30,getHeight()-30,getWidth()-55,getHeight()-30);	
		//-----------------------------dibujar limites y textos----------------------------------------------
		g.drawString("0 Am",30,getHeight()-10);
		g.drawString("12 pm",getWidth()-80,getHeight()-10);
		g.drawString("Hours",getWidth()-38,getHeight()-10);
		g.drawString("Purchases",30,10);
		//-----------------------------dibujar puntos--------------------------------------------------------
		
	}
}
