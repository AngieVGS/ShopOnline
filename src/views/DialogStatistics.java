package views;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;

import models.entity.Purchase;

public class DialogStatistics extends JDialog {

	private static final long serialVersionUID = 1L;
	
	public DialogStatistics(ArrayList<Purchase> purchases) {
		setLayout(new BorderLayout());
		JTabbedPane pestañas = new JTabbedPane();
		PanelBarGraphic lineGraphic = new PanelBarGraphic(purchases);
		pestañas.add("Lines graphic", lineGraphic);
		add(pestañas,BorderLayout.CENTER);
	}
}