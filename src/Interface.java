import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

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
import javax.swing.JTextPane;

import java.awt.Font;

import javax.swing.JScrollPane;

import java.awt.Toolkit;

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
	private JTextPane txtpnCards;
	private String cardsString = "";
	private JScrollPane scrollPane;
	private Document doc;

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
		setIconImage(Toolkit.getDefaultToolkit().getImage(Interface.class.getResource("/com/test/icon/mtg_icon.png")));
		setTitle("Deckbox parser");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 541, 710);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][32.00][][][]",
				"[][][][grow][grow]"));

		JButton btnOpenFile = new JButton("Open file...");
		btnOpenFile.setAction(action);
		contentPane.add(btnOpenFile, "cell 0 0");

		spnHeader = new JSpinner();
		spnHeader.setModel(new SpinnerNumberModel(new Integer(1), null, null,
				new Integer(1)));
		contentPane.add(spnHeader, "cell 1 0");

		lblHeader = new JLabel("Header");
		contentPane.add(lblHeader, "cell 2 0");

		spnBottom = new JSpinner();
		spnBottom.setModel(new SpinnerNumberModel(new Integer(4), null, null,
				new Integer(1)));
		contentPane.add(spnBottom, "cell 3 0");

		lblBottom = new JLabel("Bottom");
		contentPane.add(lblBottom, "cell 4 0");

		lblPath = new JLabel("You can drag and drop file into the window");
		contentPane.add(lblPath, "cell 0 1 5 1");

		lblFileInfo = new JLabel("");
		contentPane.add(lblFileInfo, "flowx,cell 0 2 5 1");

		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "cell 0 3 5 2,grow");

		txtpnCards = new JTextPane();
		scrollPane.setViewportView(txtpnCards);
		txtpnCards.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtpnCards.setText("Copy from here");
		doc = txtpnCards.getDocument();
		
		new FileDrop(contentPane, new FileDrop.Listener() {
			public void filesDropped(java.io.File[] files) {
				for (File file : files) {
					try {
						psr = new Parser(file);
					} catch (InvalidFileFormatException e1) {
						lblPath.setText("Wrong file format!");
					}
					cards = psr.parse();
					fillTextPane(cards);
				}
			}
		});

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
			fillTextPane(cards);

		}
	}

	private void fillTextPane(List<String[]> cards) {
		txtpnCards.setText("");
		lblFileInfo.setText("Loaded " + cards.size() + " lines!");
		for (int i = (Integer) spnHeader.getValue(); i < cards.size()
				- (Integer) spnBottom.getValue(); i++) {
			if (!cards.get(2)[1].matches("^\\d+")) {
				for (int ii = 0; ii < cards.get(i).length; ii++)
					if (ii == 0) {
						cardsString = cardsString + cards.get(i)[ii] + "x ";
					} else if (ii == 1) {
						cardsString = cardsString + cards.get(i)[ii];
					}
				cardsString = cardsString + "\n";
				try {
					doc.insertString(doc.getLength(), cardsString, null);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				cardsString = "";
			} else {
				for (int ii = 0; ii < cards.get(i).length; ii++)
					if (ii == 0) {
						cardsString = cardsString + cards.get(i)[ii] + "x ";
					} else if (ii == 2) {
						cardsString = cardsString + cards.get(i)[ii];
					}
				cardsString = cardsString + "\n";
				try {
					doc.insertString(doc.getLength(), cardsString, null);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				cardsString = "";
			}
		}
	}
}
