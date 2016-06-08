package clientservice.Client.service;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * created by Christian Zen
 * christian.zen@outlook.de
 * Date of creation: 08.06.2016
 */
public class Log {

	private JFrame frame;
	private JTextArea textArea;

	public Log() {
		textArea = new JTextArea(25, 60);
		frame = new JFrame("Restopolie : Events");
		textArea.append("blabla" + "\n");
		frame.setVisible(true);
		frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
		frame.setSize(250, 250);
	}
	
	public void addLine(String line) {
		//if (!(string == null) || !(string.equals(""))) {
		this.textArea.append(line + "\n");
		//}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Log();
			}
		});
	}
}
