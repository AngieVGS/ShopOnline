package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import controller.ActionEnum;
import controller.Controller;
import models.entity.Category;
import models.entity.Product;

public class DialogAdd extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField name;
	private JTextField price;
	private JSpinner stock;
	private PanelPhoto photo;
	private String path;
	private JComboBox<Category> category;
	private JTextField newCategory;
	private JTextField percentageOfOProfit;
	private JPanel panel;

	public DialogAdd(Controller controller, MainWindow main) {
		super(main);
		setLocation(500, 100);
		setTitle(Constants.ADD_TEXT);
		setSize(300, 500);
		setLayout(new BorderLayout());
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBorder(BorderFactory.createTitledBorder(Constants.CREATION_PANEL_TEXT));

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.8;
		photo = new PanelPhoto();
		photo.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		panel.add(photo, c);

		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.05;
		JPanel panelbtnChance = new JPanel();
		panelbtnChance.setLayout(new GridLayout(0, 2));

		JButton btnChanceWeb = new JButton(Constants.CHANCE_IMAGE_TO_WEB_TEXT);

		btnChanceWeb.setBorder(null);

		btnChanceWeb.addActionListener(controller);
		btnChanceWeb.setActionCommand(ActionEnum.CHANGE_IMAGE_WEB.name());
		panelbtnChance.add(btnChanceWeb);

		JButton btnChancesrc = new JButton(Constants.CHANCE_IMAGE_TO_SRC_TEXT);

		btnChancesrc.setBorder(null);
		btnChancesrc.setForeground(Color.BLUE);

		btnChancesrc.addActionListener(controller);
		btnChancesrc.setActionCommand(ActionEnum.CHANGE_IMAGE_LOCAL_SERVER.name());
		panelbtnChance.add(btnChancesrc);
		panel.add(panelbtnChance, c);
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.05;
		name = new JTextField();
		name.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), Constants.NAME_TEXT));
		panel.add(name, c);

		c.gridx = 0;
		c.gridy = 3;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.05;
		stock = new JSpinner();
		stock.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), Constants.STOCK_TEXT));
		
		percentageOfOProfit = new JTextField();
		percentageOfOProfit.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Percentage of profit"));
		
		price = new JTextField();
		price.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Price"));
		panel.add(price, c);
		JPanel example = new JPanel();
		example.setLayout(new GridLayout(0, 2));
		JPanel panelButons = new JPanel();
		panelButons.setLayout(new GridLayout(0, 2));

		JPanel categoryPanel = new JPanel();
		categoryPanel.setLayout(new GridLayout(1, 2));
		newCategory = new JTextField(Constants.TYPE_A_NEW_CATEGORY_TEXT);

		c.gridx = 0;
		c.gridy = 4;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.05;
		category = new JComboBox<>(Category.values());
		categoryPanel
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), Constants.CATEGORY_TEXT));
		categoryPanel.add(category);
		categoryPanel.add(newCategory);
		panel.add(categoryPanel, c);

		JButton btnAccept = new JButton(Constants.ACEPT_TEXT);
		btnAccept.setBorder(null);
		btnAccept.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		btnAccept.setActionCommand(ActionEnum.CREATE.name());
		btnAccept.addActionListener(controller);
		panelButons.add(btnAccept);

		JButton btnCancel = new JButton(Constants.CANCEL_TEXT);
		btnCancel.setBorder(null);
		btnCancel.addActionListener(controller);
		btnCancel.setActionCommand(ActionEnum.CANCEL_CREATION.name());
		panelButons.add(btnCancel);

		example.add(stock);
		example.add(percentageOfOProfit);
		example.add(panelButons);

		c.gridx = 0;
		c.gridy = 6;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.05;

		panel.add(example, c);
		add(panel, BorderLayout.CENTER);
	}

	public File selectFile() {
		JFileChooser file = new JFileChooser(Constants.IMG_PATH);
		file.showOpenDialog(this);
		return file.getSelectedFile();
	}

	public void changeImageSrc() throws IOException {
		String src = "/img/" + selectFile().getName();
		photo.chancePhotoByPath(src);
		path = "U" + src;
	}

	public void changeImageWeb() throws IOException {
		new JOptionPane();
		String url = JOptionPane.showInputDialog(null, Constants.PASTE_THE_LINK_OF_THE_IMAGE_YOU_WANT);
		photo.changePhoto(url);
		path = "S" + url;
	}

	public void clean() {
		this.name.setText("");
		this.price.setText("");
		this.stock.setValue(0);
		name.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), Constants.NAME_TEXT));
		price
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), Constants.DESCRIPTION_TEXT));
		stock.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), Constants.STOCK_TEXT));
		try {
			this.photo.chancePhotoByPath(Constants.NO_PRODUCT_SELECTED_IMG);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e);
			e.printStackTrace();
		}
	}

	public Product returnCreatedProduct() {
		return new Product(name.getText(), Double.parseDouble(price.getText()), path, (int)stock.getValue(), (Category)category.getSelectedItem(), Double.parseDouble(percentageOfOProfit.getText()));
	}

	public boolean verifyIfThereIsNoErrors() {
		boolean therisNoErrors = false;
		if (name.getText().equals("")) {
			name.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED), Constants.NAME_TEXT));
		} else if (price.getText().equals("")) {
			price.setBorder(
					BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED), "Price"));
		} else if ((int) stock.getValue() == 0) {
			stock.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED),  Constants.STOCK_TEXT));
		} else if (path.equals(null)) {
			photo.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		} else {
			therisNoErrors = true;
		}
		return therisNoErrors;
	}

	public void setErrorMesage(Color color, String message) {
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(color), message));
	}
}