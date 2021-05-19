package CancerClassifier;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class window1 {

	private JFrame frame;
	private JTextField addPath;
	private final JFileChooser OpenFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window1 window = new window1();
					window.frame.setVisible(true);
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
		//OpenFile.setFileFilter(new FileNameExtensionFilter("cvs documents","csv"));
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 364, 154);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		addPath = new JTextField();
		addPath.setBounds(10, 45, 324, 20);
		frame.getContentPane().add(addPath);
		addPath.setColumns(10);
		
		JLabel FilePathText = new JLabel("File Path");
		FilePathText.setBounds(10, 29, 324, 14);
		frame.getContentPane().add(FilePathText);
		
		JButton GoBtn = new JButton("Go!");
		GoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				Dataset data = FileHandling.getDataset(addPath.getText());
				System.out.println(data);
				//ClassifierPackager(data);
				
				//file handler, exporta o pacote, que recebe "data" da funç anterior
			}
		});
		GoBtn.setBounds(245, 76, 89, 23);
		frame.getContentPane().add(GoBtn);
		
		JButton openBtn = new JButton("Open file...");
		openBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(OpenFile.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					String path = OpenFile.getSelectedFile().getAbsolutePath();
					Dataset data = FileHandling.getDataset(path);
					System.out.println(data);
			}
		}});
		openBtn.setBounds(10, 76, 89, 23);
		frame.getContentPane().add(openBtn);
	}
}
