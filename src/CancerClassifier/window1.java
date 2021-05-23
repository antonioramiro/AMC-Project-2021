package CancerClassifier;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;

public class window1 {

	private JFrame frmWindow;
	private final JFileChooser OpenFile;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the application.
	 */
	public window1() {
		initialize();
		
		OpenFile = new JFileChooser();
		OpenFile.setFileFilter(new FileNameExtensionFilter("csv documents","csv"));
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWindow = new JFrame();
		frmWindow.getContentPane().setBackground(Color.WHITE);
		frmWindow.setTitle("Classifier Creator");
		frmWindow.setBounds(100, 100, 664, 154);
		frmWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWindow.getContentPane().setLayout(null);
		
		JLabel instructionsLbl = new JLabel("To create a classifier, select a dataset to import");
		instructionsLbl.setBounds(10, 23, 376, 14);
		frmWindow.getContentPane().add(instructionsLbl);
		
		JLabel PathLbl = new JLabel("");
		PathLbl.setBackground(Color.BLACK);
		PathLbl.setBounds(133, 48, 493, 19);
		frmWindow.getContentPane().add(PathLbl);
			
		JButton openBtn = new JButton("Choose file...");
		openBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(OpenFile.showOpenDialog(frmWindow) == JFileChooser.APPROVE_OPTION) {
					String path = OpenFile.getSelectedFile().getAbsolutePath();
					PathLbl.setText(path);
			}
		}});
		openBtn.setBounds(10, 44, 113, 23);
		frmWindow.getContentPane().add(openBtn);
		
		JLabel updateLbl = new JLabel("");
		updateLbl.setForeground(Color.BLACK);
		updateLbl.setBounds(10, 70, 517, 22);
		frmWindow.getContentPane().add(updateLbl);
		
		JButton GoBtn = new JButton("Import");
		GoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				String path = PathLbl.getText();
			if(path.isBlank()){
				updateLbl.setForeground(Color.RED);
				updateLbl.setText("Please choose a dataset");
			}else{
				Dataset T = FileHandling.getDataset(path);
				String FinalPath = path.split("\\\\")[(path.split("\\\\").length)-1].split("\\.")[0];
				FileHandling.exportClassifier(T, FinalPath);
				updateLbl.setForeground(Color.GREEN);
				updateLbl.setText(FinalPath + "classifier has been created, you may close this window now");
				
				}
			}
		});
		GoBtn.setBounds(537, 70, 89, 23);
		frmWindow.getContentPane().add(GoBtn);
		
		
		
	}
}
