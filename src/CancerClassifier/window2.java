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

	//Launch the application.
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

	//Create the application.	
	public window2() {
		OpenFile = new JFileChooser();
		OpenFile.setFileFilter(new FileNameExtensionFilter("classifiers","classifier"));
		initialize();
		
		
	}

	//Initialize the contents of the frame.
	private void initialize() {
		
		//frame
		frmClassifier = new JFrame();
		frmClassifier.setTitle("Classifier");
		frmClassifier.setBounds(100, 100, 605, 304);
		frmClassifier.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClassifier.getContentPane().setLayout(null);
		
		//Instructions Label
		JLabel InstructionsLbl = new JLabel("Choose a classifier");
		InstructionsLbl.setBounds(10, 23, 396, 20);
		frmClassifier.getContentPane().add(InstructionsLbl);
		
		//Choose file button
		JButton ChooseFileBtn = new JButton("Choose File...");
		ChooseFileBtn.setBounds(10, 49, 111, 23);
		frmClassifier.getContentPane().add(ChooseFileBtn);
		
		//Classifier path label
		JLabel FilePath = new JLabel("");
		FilePath.setBounds(128, 49, 451, 23);
		frmClassifier.getContentPane().add(FilePath);
		
		//Insert measurements Label
		JLabel MeasurementsLbl = new JLabel("Insert Measurements");
		MeasurementsLbl.setEnabled(false);
		MeasurementsLbl.setBounds(10, 83, 358, 14);
		frmClassifier.getContentPane().add(MeasurementsLbl);
		
		//Text field to insert measurement values
		insertValues = new JTextField();
		insertValues.setEditable(false);
		insertValues.setEnabled(false);
		insertValues.setBounds(10, 108, 569, 20);
		frmClassifier.getContentPane().add(insertValues);
		insertValues.setColumns(10);
		
		//classify button
		JButton ClassifyBtn = new JButton("Classify");
		ClassifyBtn.setBounds(490, 170, 89, 23);
		frmClassifier.getContentPane().add(ClassifyBtn);
		
		//Problems label 
		JLabel ProblemLbl = new JLabel("");
		ProblemLbl.setForeground(Color.RED);
		ProblemLbl.setBounds(10, 139, 569, 20);
		frmClassifier.getContentPane().add(ProblemLbl);

		
		//Class label is created
		JLabel ClassLbl = new JLabel("");
		ClassLbl.setBounds(163, 208, 46, 14);
		frmClassifier.getContentPane().add(ClassLbl);
		
		//result label is created
		JLabel ResultHereLbl = new JLabel("");
		ResultHereLbl.setBackground(Color.WHITE);
		ResultHereLbl.setHorizontalAlignment(SwingConstants.CENTER);
		ResultHereLbl.setForeground(Color.BLUE);
		ResultHereLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		ResultHereLbl.setBounds(219, 195, 149, 36);
		frmClassifier.getContentPane().add(ResultHereLbl);
		
		//imports a classifier when activated
		ChooseFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (OpenFile.showOpenDialog(frmClassifier) == JFileChooser.APPROVE_OPTION) { //Opens file dialog
					String path = OpenFile.getSelectedFile().getAbsolutePath(); //gets classifier path
					FilePath.setText(path); //shows path on Path label
					
					cp = FileHandling.importClassifier(path); //imports chosen classifier 
					int dim = cp.numberOfMeasurements();  //number of measurements
					
					MeasurementsLbl.setEnabled(true);
					MeasurementsLbl.setText("Insert " + dim + " measurements separated by commas" ); //shows measurements instructions
					
					insertValues.setEditable(true);
					insertValues.setEnabled(true);
				}
			}
		});
		
		//classifies values when classify button is activated 
		ClassifyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = FilePath.getText(); //gets classifier path
				boolean allset = true;
				if (path.isBlank()) { //checks if a classifier was chosen
					allset = false;
					ProblemLbl.setText("Please choose a classifier"); 
				}else if (insertValues.getText().isBlank()){ //checks if measurements were inserted
					allset = false;
					ProblemLbl.setText("Please insert values");
				}else {
					String[] values = insertValues.getText().replaceAll("\\s","").split(","); //removes spaces on path and splits between values
					int dim = cp.getMrft().get(0).getDim(); //supposed number of measurements
					int measurementsNumber = values.length;
					if (measurementsNumber == dim) { //checks if the number of measurements inserted matches classifier measurements
						int[] valuesList = new int[measurementsNumber]; 
						boolean foundwrong = false;
						for (int i = 0; i < measurementsNumber && !foundwrong; i++) { 
					        int thisValue = Integer.parseInt(values[i]);
					        int dimi = (cp.getMrft().get(0).getMeasurementDim(i));
					        if (thisValue >= dimi) { //checks if inserted values match classifier measurements dimensions
					        	allset = false;
					        	int number= i+1; //user gets measurements index beginning on 1 
					        	ProblemLbl.setText("Measurement " + number + " out of range");
					        	foundwrong = true;
					        }else {
					        	ProblemLbl.setText("");
					        	valuesList[i] = thisValue; //creates [] of values to classify
					        }
						} 
						if (allset) { //if values are compatible
							
							//Classifying:
							Classifier c = new Classifier(cp.getMrft(), cp.getFreq()); //creates a classifier from the ClassifierPackager
							int result = c.Classify(valuesList); //classifies vector
							ClassLbl.setText("Class:");
							ResultHereLbl.setText(String.valueOf(result));  //shows class result
						}
					}else {
						allset = false;
						ProblemLbl.setText("Wrong number of measurements");
					}
				} 
			}
		});
	}
}
