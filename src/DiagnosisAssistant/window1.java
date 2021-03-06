package DiagnosisAssistant;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class window1 {

	private JFrame frmWindow;
	private final JFileChooser OpenFile;

	///Launch the application.	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window1 window = new window1();
					window.frmWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Create the application.
	public window1() {
		initialize();
		
		OpenFile = new JFileChooser();
		OpenFile.setFileFilter(new FileNameExtensionFilter("csv documents","csv"));
	}

	//Initialize the contents of the frame.	
	private void initialize() {
		frmWindow = new JFrame();
		
		frmWindow.setTitle("Classifier Creator");
		frmWindow.setBounds(100, 100, 664, 154);
		frmWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWindow.getContentPane().setLayout(null);
		
		//Instructions label
		JLabel instructionsLbl = new JLabel("To create a classifier, select a dataset to import");
		instructionsLbl.setBounds(10, 23, 376, 14);
		frmWindow.getContentPane().add(instructionsLbl);
		
		//Open file button 
		JButton OpenBtn = new JButton("Choose file...");
		OpenBtn.setBounds(10, 44, 113, 23);
		frmWindow.getContentPane().add(OpenBtn);

		//Dataset path label
		JLabel PathLbl = new JLabel("");
		PathLbl.setBackground(Color.BLACK);
		PathLbl.setBounds(133, 48, 493, 19);
		frmWindow.getContentPane().add(PathLbl);
		
		//Update label
		JLabel updateLbl = new JLabel("");
		updateLbl.setForeground(Color.BLACK);
		updateLbl.setBounds(10, 70, 517, 22);
		frmWindow.getContentPane().add(updateLbl);

		//Import button 
		JButton ImportBtn = new JButton("Import");
		ImportBtn.setBounds(537, 70, 89, 23);
		frmWindow.getContentPane().add(ImportBtn);
		
		//Let's user select a dataset when Open button is activated
		OpenBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(OpenFile.showOpenDialog(frmWindow) == JFileChooser.APPROVE_OPTION) { //opens file dialog
					String path = OpenFile.getSelectedFile().getAbsolutePath(); //gets file path
					PathLbl.setText(path); //sets path label with dataset path
			}
		}});
		
		//Imports a dataset and creates a classifier when ImportButton is activated
		ImportBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				String path = PathLbl.getText();
			if(path.isBlank()){ //makes sure a path was chosen
				updateLbl.setForeground(Color.RED);
				updateLbl.setText("Please choose a dataset"); 
			}else{
				Dataset T = FileHandling.getDataset(path); //gets selected dataset
				String FinalPath = path.split("\\\\")[(path.split("\\\\").length)-1].split("\\.")[0]; //prepares classifier name
				FileHandling.exportClassifier(T, FinalPath); //exports classifier with right name
				updateLbl.setForeground(Color.GREEN); 
				updateLbl.setText(FinalPath + "classifier has been created, you may close this window now");
				}
			}
		});
		
	}
}
