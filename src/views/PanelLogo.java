package views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelLogo extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image image;

	public PanelLogo(String src) {
		setBackground(Color.decode(Constants.BACKGROUND_COLOR_MAIN));
		image = new ImageIcon(getClass().getResource(src)).getImage();
	}

	protected void paintComponent(Graphics g) {
		int x = getWidth() - 20;
		int y = getHeight() - 20;
		int dimension = (x > y) ? y : x;
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, (getWidth() / 2) - (dimension / 2), (getHeight() / 2) - (dimension / 2), dimension,
				dimension, this);

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