package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import controller.ActionEnum;
import controller.Controller;

public class DialogLogin extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField userName;
	private JPasswordField pasword;
	private JButton btnLogin;
	private JButton btnRegister;
	private JButton adminOrUser;
	private JLabel labelError;

	public DialogLogin(Controller controller, MainWindow mainWindow) {
		super(mainWindow);
		setSize(750, 700);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(1, 3));
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.WHITE);
		add(panel1);
		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.WHITE);
		panel2.setLayout(new GridLayout(4, 1));
		JPanel panelXample1 = new JPanel();
		panelXample1.setBackground(Color.WHITE);
		panel2.add(panelXample1);
		PanelImage imgUser = new PanelImage(Constants.LOGO_OF_THE_SHOP_PATH);
		panel2.add(imgUser);
		JPanel panelTexts = new JPanel();
		panelTexts.setLayout(new GridLayout(5, 1));
		JLabel lbluser = new JLabel("User name");
		lbluser.setBackground(Color.WHITE);
		lbluser.setOpaque(true);
		panelTexts.add(lbluser);
		userName = new JTextField();
		userName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panelTexts.add(userName);
		JLabel lblPasword = new JLabel("pasword");
		lblPasword.setBackground(Color.WHITE);
		lblPasword.setOpaque(true);
		panelTexts.add(lblPasword);
		pasword = new JPasswordField();
		pasword.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pasword.setBackground(Color.WHITE);
		panelTexts.add(pasword);
		JPanel panelbuttons = new JPanel();
		panelbuttons.setLayout(new GridLayout(1, 2));
		panelbuttons.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(controller);
		btnLogin.setActionCommand(ActionEnum.LOGIN.name());
		btnLogin.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		panelbuttons.add(btnLogin);
		
		btnRegister = new JButton("Register");
		btnRegister.addActionListener(controller);
		btnRegister.setActionCommand(ActionEnum.REGISTER.name());
		btnRegister.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		panelbuttons.add(btnRegister);
		
		panelTexts.add(panelbuttons);
		panel2.add(panelTexts);
		JPanel panelXample2 = new JPanel();
		labelError = new JLabel();
		panelXample2.setLayout(new BorderLayout());
		panelXample2.add(labelError, BorderLayout.CENTER);
		
		adminOrUser = new JButton("Administrator, wanna change to user?");
		adminOrUser.addActionListener(controller);
		adminOrUser.setActionCommand(ActionEnum.BUTTON_CHANGE_BETWEEN_USER_AND_ADMIN.name());
//		adminOrUser.setBackground(Color.WHITE);
		adminOrUser.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
		
		panelXample2.add(adminOrUser, BorderLayout.PAGE_START);
		panelXample2.setBackground(Color.WHITE);
		panel2.add(panelXample2);
		add(panel2);
		JPanel panel3 = new JPanel();
		panel3.setBackground(Color.WHITE);
		add(panel3);
		setVisible(true);
	}

	public void changeMode(){
		if (adminOrUser.getText().equals("Administrator, wanna change to user?")) {
			adminOrUser.setText("User, wanna change to administrator?");
		}else{
			adminOrUser.setText("Administrator, wanna change to user?");
		}
	}
	
	public boolean adminOrUser(){
		if (adminOrUser.getText().equals("Administrator, wanna change to user?")) {
			return true;
		}else{
			return false;
		}
	}
	
	public void aminateInvalidUser() {
		userName.setBorder(BorderFactory.createLineBorder(Color.RED));
		labelError.setText("              The user field is empty");
	}

	public void aminateInvalidPasword() {
		pasword.setBorder(BorderFactory.createLineBorder(Color.RED));
		labelError.setText("           The Password field is empty");
	}

	public void animateIncorrectUserOrPassword(){
		cleanToErrors();
		labelError.setText("         User or password are incorrect");
	}
	
	public void cleanToErrors() {
		userName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pasword.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		labelError.setText("");
	}
	
	public void clean(){
		userName.setText("");
		pasword.setText("");
	}

	public String getUser() {
		return userName.getText();
	}
		
	@SuppressWarnings("deprecation")
	public String getEnteredPassword() {
		return pasword.getText();
	}
}