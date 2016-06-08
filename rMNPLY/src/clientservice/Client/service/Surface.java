package clientservice.Client.service;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import clientservice.Client.controller.ClientController;

/**
 * created by Christian Zen
 * christian.zen@outlook.de
 * Date of creation: 05.06.2016
 */
public class Surface extends JFrame {

	private JTextField willkommensMessage;
	private JTextField spielerName;
	public ClientController clientController;
	private JButton userIcon;

	public Surface(ClientController clientController) {
		super("Restopoly");
		this.clientController = clientController;

		//setTitle("Restopoly");
		setVisible(true);
		setLayout(new GridLayout(3,3));

		//setSize(500, 500);

		

		
		
		Icon icon = new ImageIcon(getClass().getResource(
				"user-icon-6s.png"));
		userIcon = new JButton("test", icon);
		this.userIcon.setSize(25, 25);
		this.userIcon.setAlignmentX(LEFT_ALIGNMENT);
		this.userIcon.setAlignmentY(TOP_ALIGNMENT);
		add(userIcon);
		userIcon.setVisible(false);
		
		spielerName = new JTextField(10);
		spielerName.setEditable(true);
		add(spielerName);
		Handler handler = new Handler();
		spielerName.addActionListener(handler);
	}

	public void setPlayerIcon() {
		//Icon icon = new ImageIcon(getClass().getResource(
		//		"user-icon-6.png"));
		//userIcon = new JButton(clientController.getPlayerName(), icon);
		//userIcon = new JButton("test", icon);
		//userIcon.setLocation(1, 1);
		this.userIcon.setVisible(true);
		this.userIcon.setText(clientController.getPlayerName());
		//userIcon.setBounds(1, 1, 50, 50);
		this.userIcon.setAlignmentX(LEFT_ALIGNMENT);
		this.userIcon.setAlignmentY(TOP_ALIGNMENT);
		this.userIcon.setSize(25, 25);
		//add(userIcon);
	}

	public void setTitleCus(String string) {
		this.setTitle("Restopoly! Spieler: " + string);
	}

	public void hideNameField() {
		//if (!(string == null) || !(string.equals(""))) {
		this.spielerName.setVisible(false);
		//}
	}
	
	public void startLogScreen(){
		ReadFile readFile = new ReadFile();
	}

	private class Handler implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String string = "";
			if (e.getSource() == spielerName) {
				setPlayerIcon();
				string = e.getActionCommand();
				//JOptionPane.showConfirmDialog(null, string);
				JOptionPane.showMessageDialog(null, string);
				clientController.setPlayerName(string);
				setTitleCus(string);
				hideNameField();
				startLogScreen();
				
			}
		}
	}
}
