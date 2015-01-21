import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;


public class Interface extends JFrame {

	private JPanel contentPane;
	private final Action action = new SwingAction();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface frame = new Interface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Interface() {
		setTitle("Deckbox parser");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][]", "[][]"));
		
		JButton btnOpenFile = new JButton("Open file...");
		btnOpenFile.setAction(action);
		contentPane.add(btnOpenFile, "cell 0 0");
		
		JLabel lblPath = new JLabel("");
		contentPane.add(lblPath, "cell 1 0");
		
		JLabel lblFileInfo = new JLabel("File info:");
		contentPane.add(lblFileInfo, "cell 0 1");
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Open file...");
			putValue(SHORT_DESCRIPTION, "Opens .csv file for parsing");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
