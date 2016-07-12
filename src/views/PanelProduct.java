package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import controller.ActionEnum;
import controller.Controller;
import models.entity.Product;

public class PanelProduct extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel pnl;
	private Controller controller;

	public PanelProduct(Controller controller) {
		this.controller = controller;
		pnl = new JPanel();
		pnl.setLayout(new GridLayout(5, 3, 20, 20));
		pnl.setBackground(Color.WHITE);
		pnl.setPreferredSize(new Dimension(1000, 1000));

		setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(pnl, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		add(scrollPane, BorderLayout.CENTER);

		JPanel pager = new JPanel();
		JButton prev = new JButton(Constants.PREV_TEXT);
		prev.addActionListener(controller);
		prev.setActionCommand(ActionEnum.PREV_PAGE.name());
		pager.add(prev);

		JButton next = new JButton(Constants.NEXT_TEXT);
		next.addActionListener(controller);
		next.setActionCommand(ActionEnum.NEXT_PAGE.name());

		pager.add(next);

		add(pager, BorderLayout.SOUTH);
	}

	public JButton createBtnPage(String text, ActionEnum actionEnum) {
		JButton btn = new JButton(text);

		btn.addActionListener(controller);
		btn.setActionCommand(actionEnum.name());
		return btn;
	}

	public void addProduct(Product product) throws IOException {
		Image imgScale = null;
		if (product.getImagePath().charAt(0) == 'U') {
			imgScale = new ImageIcon(getClass().getResource(product.getImagePath().substring(1))).getImage()
					.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		} else {
			URL linkImage = new URL(product.getImagePath().substring(1));
			BufferedImage gg = ImageIO.read(linkImage);
			imgScale = new ImageIcon(gg).getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		}
		ImageIcon iconFinally = new ImageIcon(imgScale);
		final JButton btnProduct = new JButton(iconFinally);
		btnProduct.setPreferredSize(new Dimension(100, 100));
		btnProduct.setText(product.getName());
		btnProduct.setName(product.getId() + "");
		btnProduct.setBackground(Color.decode(Constants.BACKGROUND_COLOR_MAIN));
		btnProduct.setHorizontalTextPosition(SwingConstants.CENTER);
		btnProduct.setVerticalTextPosition(SwingConstants.BOTTOM);
		PanelProduct panelProduct = new PanelProduct(controller);
		panelProduct.setLayout(new BorderLayout());
		panelProduct.add(btnProduct, BorderLayout.CENTER);
		panelProduct.setAlignmentY(JPanel.CENTER_ALIGNMENT);
		panelProduct.setPreferredSize(new Dimension(500, 500));
		JLabel lbPrice = new JLabel("$" + product.getPrice());
		JButton btnAddCar = new JButton(Constants.ADD_CAR_ID_TEXT + product.getId());
		btnAddCar.addActionListener(controller);
		btnAddCar.setActionCommand(ActionEnum.ADD_TO_CAR.name());
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 50, 5));
		panelProduct.add(panel, BorderLayout.PAGE_END);
		panel.add(lbPrice);
		panel.add(btnAddCar);
		panel.setBackground(Color.WHITE);
		pnl.add(panelProduct);
	}

	public void addRemainingPanels() {
		pnl.add(new JPanel());
	}

	public void replaceAllProductsInGridWithSpecificOnes(ArrayList<Product> products) throws IOException {
		pnl.removeAll();
		for (int i = 0; i < products.size(); i++) {
			addProduct(products.get(i));
		}
		pnl.revalidate();
		pnl.repaint();
	}

	public void changePage(int page, ArrayList<Product> products) throws IOException {
		ArrayList<Product> auxArray = new ArrayList<>();
		for (int j = (page * 15); j < (page * 15) + 15; j++) {
			auxArray.add(products.get(j));
			if (j >= products.size() - 1) {
				break;
			}
		}
		replaceAllProductsInGridWithSpecificOnes(auxArray);
	}
}