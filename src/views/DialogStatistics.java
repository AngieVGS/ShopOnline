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
		JTabbedPane pesta�as = new JTabbedPane();
		PanelBarGraphic lineGraphic = new PanelBarGraphic(purchases);
		pesta�as.add("Lines graphic", lineGraphic);
		add(pesta�as,BorderLayout.CENTER);
	}
}