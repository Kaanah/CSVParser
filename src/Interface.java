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
import java.io.File;
import java.util.List;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class Interface extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final Action action = new SwingAction();
	private Parser psr;
	private static JFileChooser fc;
	private static File fileOpen;
	private JLabel lblPath;
	private JLabel lblFileInfo;
	private List<String[]> cards;
	private JSpinner spnHeader;
	private JLabel lblHeader;
	private JSpinner spnBottom;
	private JLabel lblBottom;

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
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][32.00][][][]", "[][][]"));
		
				JButton btnOpenFile = new JButton("Open file...");
				btnOpenFile.setAction(action);
				contentPane.add(btnOpenFile, "cell 0 0");
								
								spnHeader = new JSpinner();
								spnHeader.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));
								contentPane.add(spnHeader, "cell 1 0");
								
								lblHeader = new JLabel("Header");
								contentPane.add(lblHeader, "cell 2 0");
								
								spnBottom = new JSpinner();
								spnBottom.setModel(new SpinnerNumberModel(new Integer(4), null, null, new Integer(1)));
								contentPane.add(spnBottom, "cell 3 0");
								
								lblBottom = new JLabel("Bottom");
								contentPane.add(lblBottom, "cell 4 0");
						
								lblPath = new JLabel("");
								contentPane.add(lblPath, "cell 0 1");
				
						lblFileInfo = new JLabel("");
						contentPane.add(lblFileInfo, "flowx,cell 0 2");

		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
	}

	private class SwingAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		public SwingAction() {
			putValue(NAME, "Open file...");
			putValue(SHORT_DESCRIPTION, "Opens .csv file for parsing");
		}

		public void actionPerformed(ActionEvent e) {
			int returnVal = fc.showOpenDialog(Interface.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				fileOpen = fc.getSelectedFile();
				lblPath.setText("File: " + fileOpen.getPath());
				try {
					psr = new Parser(fileOpen);
				} catch (InvalidFileFormatException e1) {
					lblPath.setText("Wrong file format!");
				}
			}
			cards = psr.parse();
			lblFileInfo.setText("Loaded " + cards.size() + " lines!");
			for (int i = (Integer)spnHeader.getValue(); i<cards.size() - (Integer)spnBottom.getValue(); i++) {
				for (int ii = 0; ii < cards.get(i).length; ii++)
					if (ii == 0)
						System.out.print(cards.get(i)[ii] + "x ");
					else if (ii == 1)
						System.out.print(cards.get(i)[ii]);
				System.out.println();
			}
		}
	}
}
