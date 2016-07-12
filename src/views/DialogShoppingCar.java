package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import controller.ActionEnum;
import controller.Controller;

public class DialogShoppingCar extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel panelBase = new JPanel();
	ArrayList<Integer> ids = new ArrayList<>();
	ArrayList<PanelObjectShoppingCar> panels = new ArrayList<>();

	public DialogShoppingCar(Controller controller, MainWindow main) {
		super(main);
		setLayout(new GridBagLayout());
		setSize(new Dimension(500, 600));
		setLocationRelativeTo(null);
		panelBase.setLayout(new GridLayout(panels.size(), 1));
		JScrollPane scrol = new JScrollPane(panelBase);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.97;
		add(scrol, c);
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.03;
		JPanel panelBtnBuy = new JPanel();
		panelBtnBuy.setLayout(new BorderLayout());
		JButton btnBuy = new JButton(Constants.BUY_TEXT);
		btnBuy.addActionListener(controller);
		btnBuy.setActionCommand(ActionEnum.BUY.name());
		panelBtnBuy.add(btnBuy, BorderLayout.CENTER);
		add(panelBtnBuy, c);
	}

	public void removeOfTheCar(int CarObjId) {
		for (PanelObjectShoppingCar panel : panels) {
			if (panel.getId() == CarObjId) {
				panels.remove(panel);
				revalidate();
			}
		}
	}

	public void add(PanelObjectShoppingCar panel) {
		panels.add(panel);
		panelBase.setLayout(new GridLayout(panels.size(), 1));
		for (PanelObjectShoppingCar panels : panels) {
			panelBase.add(panels);
		}
	}
}