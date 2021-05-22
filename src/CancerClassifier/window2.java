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
	private ClassifierPackager cp;

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
		frmClassifier.setBounds(100, 100, 603, 283);
		frmClassifier.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClassifier.getContentPane().setLayout(null);
		
		insertValues = new JTextField();
		insertValues.setBounds(10, 108, 569, 20);
		frmClassifier.getContentPane().add(insertValues);
		insertValues.setColumns(10);
		
		JLabel resultLabel = new JLabel("");
		resultLabel.setBackground(Color.BLACK);
		resultLabel.setForeground(Color.BLACK);
		resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resultLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		resultLabel.setBounds(219, 195, 149, 36);
		frmClassifier.getContentPane().add(resultLabel);
		
		JLabel MeasurementsLbl = new JLabel("");
		MeasurementsLbl.setBounds(10, 83, 358, 14);
		frmClassifier.getContentPane().add(MeasurementsLbl);
		
		JLabel ProblemLbl = new JLabel("");
		ProblemLbl.setBounds(10, 139, 569, 20);
		frmClassifier.getContentPane().add(ProblemLbl);
		
		JButton ChooseFileBtn = new JButton("Choose File...");
		ChooseFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (OpenFile.showOpenDialog(frmClassifier) == JFileChooser.APPROVE_OPTION) {
					
					JLabel FilePath = new JLabel("");
					FilePath.setBounds(128, 49, 451, 23);
					frmClassifier.getContentPane().add(FilePath);
					
					String path = OpenFile.getSelectedFile().getAbsolutePath();
					FilePath.setText(path);
					cp = FileHandling.importClassifier(FilePath.getText());
					
					MeasurementsLbl.setText(Integer.toString(cp.getMrft().get(0).getDim()));
					
					
				}
			}
		});
		
		
		
		ChooseFileBtn.setBounds(10, 49, 111, 23);
		frmClassifier.getContentPane().add(ChooseFileBtn);
		
		JButton ClassifyBtn = new JButton("Classify");
		ClassifyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String values = insertValues.getText();
				String[] valuesList = (values.split(","));
				int valueNumber = valuesList.length;
				
				int[] firstValues = new int[valueNumber];
				//ClassifierPackager cp = FileHandling.importClassifier(FilePath.getText());
				
			      for (int i = 0; i < valuesList.length; i++) {
			        int thisValue = Integer.parseInt(valuesList[i]);
			        int dimi = cp.getMrft().get(0).getMeasurementDim(i);
			        
			        if (thisValue > dimi) {
			        	ProblemLbl.setText("Measurement" + i + "out of range");
			        }else {
			        firstValues[i] = thisValue;
			        }
			      }
			      
			      Classifier c = new Classifier(cp.getMrft(), cp.getFreq());
			      int result = c.Classify(firstValues);
			      resultLabel.setText(String.valueOf(result));		
			}
		});
		
		ClassifyBtn.setBounds(488, 170, 89, 23);
		frmClassifier.getContentPane().add(ClassifyBtn);
		
		JLabel lblNewLabel_1 = new JLabel("Choose a Classifier");
		lblNewLabel_1.setBounds(10, 30, 155, 14);
		frmClassifier.getContentPane().add(lblNewLabel_1);
		
		
			

	}
}
//1,2,1,0,0,0,1,0
//Classifiers/maria.classifier