package clientservice.Client.service;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import clientservice.Client.controller.ClientController;

public class InputDialog extends JFrame {

	public InputDialog(ClientController clientController) {
		super("Input Dialog");
		JOptionPane jOptionPane = new JOptionPane();
		String name = jOptionPane.showInputDialog("spielernamen eingeben: ");
		jOptionPane.setVisible(false);
	}

}
