package views;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelPhoto extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image image;

	public PanelPhoto() {
		setOpaque(false);
		image = new ImageIcon(getClass().getResource(Constants.BASIC_SRC)).getImage();
	}

	protected void paintComponent(Graphics g) {
		int x = getWidth() - 20;
		int y = getHeight() - 20;
		int dimension = (x > y) ? y : x;
		g.drawImage(image, (getWidth() / 2) - (dimension / 2), (getHeight() / 2) - (dimension / 2), dimension,dimension, this);
	}
	
	public static String getBasicSrc(){
		return Constants.BASIC_SRC;
	}

	public void changePhoto(String url) throws IOException {
		URL linkImage = new URL(url);
		BufferedImage gg = ImageIO.read(linkImage);
		image = new ImageIcon(gg).getImage();
		revalidate();
		repaint();
	}

	public void chancePhotoByPath(String src) throws IOException {
		image = new ImageIcon(getClass().getResource(src)).getImage();
		revalidate();
		repaint();
	}
}