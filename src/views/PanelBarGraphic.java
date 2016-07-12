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
	public PanelBarGraphic(ArrayList<Purchase> purchase) {// el diagrama se arma por dias
		purch = purchase;
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
		try{
		int partsOfEjeXInPixels = calculatePartsInPixels(1,24);//se hace la divicion para saber de a cuantos pixeles toca para usar entre objetos de x
		int partsOfEjeYInPixels = calculatePartsInPixels(0,NumberPurchasesToday());// numberpurchasesToday().. compras del dia
		int lastPositionx = 30;
		int lastPositiony = getHeight()-30;
		
		for (int i = 0; i < 24; i++) {// recorre las 24 horas del dia y va graficando linea por linea 
			g.setColor(Color.blue);
			g.drawLine(lastPositionx, lastPositiony,lastPositionx+partsOfEjeXInPixels,getHeight()-30-(calculateSalesPerHour((new GregorianCalendar().get(GregorianCalendar.DAY_OF_MONTH)),i)*partsOfEjeYInPixels));
			lastPositionx += partsOfEjeXInPixels;
			lastPositiony = (getHeight()-30)-(calculateSalesPerHour(new GregorianCalendar().get(GregorianCalendar.DAY_OF_MONTH), i)*partsOfEjeYInPixels);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int calculateSalesPerHour(int day,int hour){
		int sales = 0;
		for (Purchase purchase: purch){			
			if (day == LocalDateTime.now().getDayOfMonth() &&  hour == LocalDateTime.now().getHour()) {
				sales++;
			}
			System.out.println(""+sales);
		}
		return sales;
	}
	
	public int NumberPurchasesToday(){
		
		ArrayList<Purchase> aux = new ArrayList<>();		
		for (Purchase purchase: purch) {
			System.out.println("****"+purchase.getDate().DATE);
			System.out.println((new GregorianCalendar().get(GregorianCalendar.DAY_OF_MONTH)));
			if (12 == (new GregorianCalendar().get(GregorianCalendar.DAY_OF_MONTH)) ) {// el 12 quemado es el dia  CAMBIAR
				aux.add(purchase);
			}
		}
		return aux.size();
	}
	
	public int calculatePartsInPixels(int a,int parts){	//este metodo retorna el monto de pixeles que separan los puntos	
		if (a==1) {
			return (getWidth()-85)/parts;
		}else if (a==0) {
			return (getHeight()-55)/parts;
		}
		return 0;			
	}
		
//	public static void main(String[] args) {
//		JFrame frame = new JFrame("");
//		frame.setLayout(new BorderLayout());
//		PanelBarGraphic graphic = new PanelBarGraphic(null);		
//		frame.add(graphic,BorderLayout.CENTER);
//		frame.setSize(700, 400);
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(true);
//	}	
}
