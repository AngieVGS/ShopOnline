//package views;
//
//import java.awt.Color;
//import java.awt.GridLayout;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//import controller.ActionEnum;
//import controller.Controller;
//import models.entity.Brand;
//import models.entity.Category;
//
//public class PanelFilter extends JPanel {
//
//	private static final long serialVersionUID = 1L;
//	private JTextField fieldPriceDown;
//	private JTextField fieldPriceUp;
//	private JComboBox<Category> boxCategory;
//	private JComboBox<Brand> boxBrand;
//
//	public PanelFilter(Controller controller) {
//		setLayout(new GridLayout(5, 2, 5, 10));
//		setBackground(Color.white);
//		JLabel lbPriceDown = new JLabel(Constants.PRICE_DOWN_TEXT);
//		JLabel lbPriceUp = new JLabel(Constants.PRICE_UP_TEXT);
//		JLabel lbCategory = new JLabel(Constants.CATEGORY_TEXT);
//		JLabel lbBrand = new JLabel(Constants.BRAND_TEXT);
//		fieldPriceDown = new JTextField();
//		fieldPriceUp = new JTextField();
//		boxCategory = new JComboBox<>(Category.values());
//		boxCategory.setEditable(true);
//
//		boxBrand = new JComboBox<>(Brand.values());
//		boxBrand.setEditable(true);
//		
//		add(fieldPriceDown);
//		add(lbPriceDown);
//		add(fieldPriceUp);
//		add(lbPriceUp);
//		add(boxCategory);
//		add(lbCategory);
//		add(boxBrand);
//		add(lbBrand);
//
//		JButton btFilter = new JButton(Constants.FILTER_TEXT);
//		btFilter.addActionListener(controller);
//		btFilter.setBackground(Color.decode(Constants.COLOR_BUTTON));
//		btFilter.setForeground(Color.decode(Constants.BACKGROUND_COLOR_MAIN));
//		btFilter.setActionCommand(ActionEnum.FILTER.name());
//
//		add(btFilter);
//
//		JButton btCancelFilter = new JButton(Constants.CANCEL_TEXT);
//		btCancelFilter.setBackground(Color.decode(Constants.COLOR_BUTTON));
//		btCancelFilter.setForeground(Color.decode(Constants.BACKGROUND_COLOR_MAIN));
//		btCancelFilter.addActionListener(controller);
//
//		add(btCancelFilter);
//		add(btCancelFilter);
//		add(btFilter);
//	}
//
//	public double getPriceDown() {
//		return Double.parseDouble(fieldPriceDown.getText());
//	}
//
//	public double getPriceUp() {
//		return Double.parseDouble(fieldPriceUp.getText());
//	}
//
//	public Category getCategory() {
//		return (Category) boxCategory.getSelectedItem();
//	}
//
//	public Brand getBrand() {
//		return (Brand) boxBrand.getSelectedItem();
//	}
//}