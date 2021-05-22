package CancerClassifier;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class window2 {

	private JFrame frmClassifier;
	private JTextField insertValues;
	private final JFileChooser OpenFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window2 window = new window2();
					window.frmClassifier.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public window2() {
		initialize();
		
		OpenFile = new JFileChooser();
		OpenFile.setFileFilter(new FileNameExtensionFilter("classifiers","classifier"));
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClassifier = new JFrame();
		frmClassifier.setTitle("Classifier");
		frmClassifier.setBounds(100, 100, 605, 190);
		frmClassifier.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClassifier.getContentPane().setLayout(null);
		
		insertValues = new JTextField();
		insertValues.setBounds(10, 37, 569, 20);
		frmClassifier.getContentPane().add(insertValues);
		insertValues.setColumns(10);
		
		JLabel resultLabel = new JLabel("");
		resultLabel.setBackground(Color.BLACK);
		resultLabel.setForeground(Color.BLACK);
		resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resultLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		resultLabel.setBounds(119, 108, 149, 36);
		frmClassifier.getContentPane().add(resultLabel);
		
		JLabel lblNewLabel = new JLabel("Insert Values");
		lblNewLabel.setBounds(10, 22, 358, 14);
		frmClassifier.getContentPane().add(lblNewLabel);
		
		JLabel FilePath = new JLabel("");
		FilePath.setBounds(128, 82, 451, 14);
		frmClassifier.getContentPane().add(FilePath);
		
		JButton ChooseFileBtn = new JButton("Choose File...");
		ChooseFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (OpenFile.showOpenDialog(frmClassifier) == JFileChooser.APPROVE_OPTION) {
					String path = OpenFile.getSelectedFile().getAbsolutePath();
					FilePath.setText(path);
				}
			}
		});
		ChooseFileBtn.setBounds(10, 78, 111, 23);
		frmClassifier.getContentPane().add(ChooseFileBtn);
		
		JButton ClassifyBtn = new JButton("Classify");
		ClassifyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String values = insertValues.getText();
				String[] valuesList = (values.split(","));
				int valueNumber = valuesList.length;
				
				int[] firstValues = new int[valueNumber];
			      for (int i = 0; i < valuesList.length; i++) {
			        int thisValue = Integer.parseInt(valuesList[i]);
			        firstValues[i] = thisValue;
			      }
			      ClassifierPackager cp = FileHandling.importClassifier(FilePath.getText());
			      Classifier c = new Classifier(cp.getMrft(), cp.getFreq());
			      int result = c.Classify(firstValues);
			      resultLabel.setText(String.valueOf(result));		
			}
		});
		
		ClassifyBtn.setBounds(490, 117, 89, 23);
		frmClassifier.getContentPane().add(ClassifyBtn);
			

	}
}
//1,2,1,0,0,0,1,0
//Classifiers/maria.classifier