package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import models.entity.Product;

public class DeatailsPanel extends JDialog {

	private static final long serialVersionUID = 1L;
	JLabel lblName, lblMark, lblCategory, lblPrice, lbStock;
	JTextArea txtDetails;

	public DeatailsPanel(Product product) {
		setTitle(Constants.DETAILS_TEXT);
		setSize(new Dimension(700, 500));
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		JPanel centralpanel = new JPanel();
		centralpanel.setLayout(new GridLayout(1, 2));
		PanelLogo limg = new PanelLogo(product.getImagePath().substring(1));
		centralpanel.add(limg);

		JPanel panelInf = new JPanel();
		panelInf.setBackground((Color.decode(Constants.BACKGROUND_COLOR_MAIN)));
		panelInf.setLayout(new GridLayout(6, 1));
		lblName = new JLabel(product.getName());
		lblName.setBorder(BorderFactory.createTitledBorder(Constants.NAME_TEXT));
		panelInf.add(lblName);

		lblCategory = new JLabel(product.getCategory().toString());
		lblCategory.setBorder(BorderFactory.createTitledBorder(Constants.CATEGORY_TEXT));
		panelInf.add(lblCategory);

		lblPrice = new JLabel("" + product.getPrice());
		lblPrice.setBorder(BorderFactory.createTitledBorder(Constants.PRICE_TEXT));
		panelInf.add(lblPrice);
		centralpanel.add(panelInf);
		add(centralpanel, BorderLayout.CENTER);

		lbStock = new JLabel("" + product.getQuantumAvailable());
		lbStock.setBorder(BorderFactory.createTitledBorder(Constants.STOCK_TEXT));
		panelInf.add(lbStock);
		centralpanel.add(panelInf);
		add(centralpanel, BorderLayout.CENTER);
	}
}