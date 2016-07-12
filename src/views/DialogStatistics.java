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
		JTabbedPane laps = new JTabbedPane();
		PanelBarGraphic lineGraphic = new PanelBarGraphic(purchases);
		laps.add("Lines graphic", lineGraphic);
		add(laps,BorderLayout.CENTER);
	}
}