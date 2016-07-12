package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import controller.ActionEnum;
import controller.Controller;
import models.entity.Product;

public class PanelObjectShoppingCar extends JPanel {

	private static final long serialVersionUID = 1L;
	private static int DeafaultId = 0;
	private int id;
	private PanelLogo img;
	private JLabel title;
	private JButton removeOfTheCar;
	private JLabel price;

	public PanelObjectShoppingCar(Product product, Controller controller){
		setBackground(Color.decode(Constants.BACKGROUND_COLOR_MAIN));
		Product prod = product;
		this.img = new PanelLogo(prod.getImagePath().substring(1));
		img.setBorder(BorderFactory.createLineBorder(Color.decode(Constants.BACKGROUND_COLOR_MAIN), 5));
		Font font = new Font("Arial", 2, 24);
		this.title = new JLabel("   " + prod.getName());
		this.removeOfTheCar = new JButton("x");
		this.removeOfTheCar.addActionListener(controller);
		this.removeOfTheCar.setActionCommand(ActionEnum.REMOVE_OF_THE_CAR.name());
		removeOfTheCar.setBorder(null);
		removeOfTheCar.setBackground(Color.decode(Constants.BACKGROUND_COLOR_MAIN));
		removeOfTheCar.setOpaque(false);
		this.price = new JLabel("    $" + prod.getPrice());
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(500, 160));
		id = DeafaultId;
		DeafaultId++;

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.4;
		c.weighty = 1;
		add(img, c);

		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.4;
		c.weighty = 1;

		JPanel panelTitleAndPrice = new JPanel();
		panelTitleAndPrice.setLayout(new GridLayout(2, 1));
		title.setForeground(Color.decode(Constants.COLOR_FONT_MAIN));
		title.setFont(font);
		price.setForeground(Color.decode(Constants.COLOR_FONT_SECUNDARY));
		price.setFont(font);
		panelTitleAndPrice.add(title);
		panelTitleAndPrice.add(price);
		add(panelTitleAndPrice, c);

		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.1;
		c.weighty = 1;
		add(removeOfTheCar, c);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}