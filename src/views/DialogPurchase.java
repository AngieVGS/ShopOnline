package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.ActionEnum;
import controller.Controller;
import models.entity.Purchase;

public class DialogPurchase extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel pnlMain;
	private JButton btnNexPage;
	private JButton btnPrevPage;
	private JPanel panel;

	public DialogPurchase(Controller controller, MainWindow mainWindow) {
		super(mainWindow);
		setTitle(Constants.PURCHASE_TEXT);
		setSize(Constants.WIDTH, Constants.HEIGHT);
		setLayout(new BorderLayout());
		pnlMain = new JPanel(
				new FlowLayout(FlowLayout.CENTER, Constants.SEPARATION_PURCHASE, Constants.SEPARATION_PURCHASE));

		JPanel pnlPage = new JPanel(
				new FlowLayout(FlowLayout.CENTER, Constants.SEPARATION_PURCHASE, Constants.SEPARATION_PURCHASE));
		btnNexPage = new JButton(">");
		btnNexPage.addActionListener(controller);
		btnNexPage.setActionCommand(ActionEnum.NEXT_PURCHASE.name());

		btnPrevPage = new JButton("<");
		btnNexPage.addActionListener(controller);
		btnNexPage.setActionCommand(ActionEnum.PREV_PURCHASE.name());

		pnlPage.add(btnPrevPage);
		pnlPage.add(btnNexPage);

		add(pnlMain, BorderLayout.CENTER);
		add(pnlPage, BorderLayout.PAGE_END);
	}

	public void removeAllPurchase() {
		try {
			panel.removeAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void revalidBtnPage() {
		if (Controller.PAGE_PURCHASE == 0) {
			btnPrevPage.setEnabled(true);
		} else {
			btnPrevPage.setEnabled(false);
		}
		if ((Controller.PAGE_PURCHASE + 1) == Controller.PAGE_MAX_PURCHASE) {
			btnNexPage.setEnabled(true);
		} else {
			btnNexPage.setEnabled(true);
		}
	}

	public void addPurchase(Purchase purchase) {
		panel = new JPanel(
				new FlowLayout(FlowLayout.LEFT, Constants.SEPARATION_PURCHASE, Constants.SEPARATION_PURCHASE));
		JLabel lbImage = new JLabel(new ImageIcon(purchase.getProduct().getImagePath()));
		JLabel lbName = new JLabel(purchase.getProduct().getName());
		JLabel lbDate = new JLabel(purchase.returnDate());
		JLabel lbPrice = new JLabel("" + purchase.getProduct().getPrice());
		JLabel lbQuanty = new JLabel("" + purchase.getQuanty());

		panel.add(lbImage);
		panel.add(lbName);
		panel.add(lbDate);
		panel.add(lbPrice);
		panel.add(lbQuanty);

		pnlMain.add(panel);
	}
}