package views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelCar extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Image image;
	private static int stocks;

	public PanelCar() {
		setOpaque(false);
		image = new ImageIcon(getClass().getResource(Constants.SHOP_CAR_LOGO)).getImage();
		stocks = 0;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.darkGray);
		g.drawImage(image, (getWidth()/2)-25, 5, 50,50, null);
		int x = 20;
		if (stocks > 9) {
			x += 5;
		}
		if (stocks > 0) {
			g.fillOval(getWidth() - 30, 10, 25, 25);
			g.setColor(Color.decode(Constants.BACKGROUND_COLOR_MAIN));
			g.drawString("" + stocks, getWidth() - x, 27);
		}
	}

	@SuppressWarnings("static-access")
	public void add() {
		this.stocks++;
		repaint();
	}

	@SuppressWarnings("static-access")
	public void deleting() {
		this.stocks--;
		repaint();
	}

	@SuppressWarnings("static-access")
	public void cleanCar() {
		this.stocks = 0;
		repaint();
	}
}