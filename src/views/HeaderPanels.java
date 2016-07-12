package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import controller.ActionEnum;
import controller.Controller;

public class HeaderPanels extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JButton btnChance;
	private JTextField text;
	private JButton btnShoppingCar;
	private PanelCar panelBtnShoppingCar;

	public HeaderPanels(Controller controller) {
		setBackground(Color.decode(Constants.BACKGROUND_COLOR_MAIN));
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(100, 70));
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.2;
		c.weighty = 1;

		JPanel panelIcon = new PanelLogo(Constants.LOGO_PATH);

		add(panelIcon, c);

		JPanel panelSearch = new JPanel();
		panelSearch.setBorder(BorderFactory.createLineBorder(Color.decode(Constants.BACKGROUND_COLOR_MAIN), 7));
		panelSearch.setLayout(new GridBagLayout());
		JButton btnSearch = new JButton(Constants.SEARCH_TEXT);
		btnSearch.addActionListener(controller);
		btnSearch.setActionCommand(ActionEnum.SEARCH.name());

		text = new JTextField();

		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.9;
		c.weighty = 1;
		panelSearch.add(text, c);
		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.1;
		c.weighty = 1;
		panelSearch.add(btnSearch, c);

		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.6;
		c.weighty = 1;
		add(panelSearch, c);

		c.gridx = 2;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.1;
		c.weighty = 1;
		btnShoppingCar = new JButton();
		btnShoppingCar.setOpaque(false);

		btnShoppingCar.setLayout(new BorderLayout());
		panelBtnShoppingCar = new PanelCar();
		btnShoppingCar.addActionListener(controller);
		btnShoppingCar.setActionCommand(ActionEnum.OPEN_CAR.name());
		btnShoppingCar.setToolTipText(Constants.OPEN_SHOPING_CAR__TEXT);
		panelBtnShoppingCar.setPreferredSize(new Dimension(60, 60));

		btnShoppingCar.add(panelBtnShoppingCar, BorderLayout.CENTER);
		btnShoppingCar.setBorder(null);
		add(btnShoppingCar, c);

		c.gridx = 3;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.1;
		c.weighty = 1;
		
		btnChance = new JButton();
		btnChance.setLayout(new BorderLayout());
		PanelLogo logo = new  PanelLogo(Constants.CHANGE_USER_ADMINISTRATOR_LOGO);
		btnChance.add(logo,BorderLayout.CENTER);
		btnChance.setBackground(Color.decode(Constants.BACKGROUND_COLOR_MAIN));
		btnChance.addActionListener(controller);
		btnChance.setActionCommand(ActionEnum.CHANGE_MODE.name());
		btnChance.setToolTipText(Constants.CHANGE_BETWEEN_USER_AN_ADMIN_TEXT);
		btnChance.setBorder(null);
		add(btnChance, c);

	}

	public void add() {
		panelBtnShoppingCar.add();
	}

	public void reset() {
		panelBtnShoppingCar.cleanCar();
	}

	public String getEnteredText() {
		return text.getText();
	}
}