package CancerClassifier;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.Font;
import java.awt.Color;

public class window {

	private JFrame Interface1;
	private final JFileChooser ChooseFile;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window window = new window();
					window.Interface1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public window() {
		initialize();
		ChooseFile = new JFileChooser();
		ChooseFile.setFileFilter(new FileNameExtensionFilter("cvs file", "cvs"));
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Interface1 = new JFrame();
		Interface1.setTitle("Chow Liu ");
		Interface1.setBounds(100, 100, 452, 142);
		Interface1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Interface1.getContentPane().setLayout(null);
		
		Label FileSelected = new Label("");
		FileSelected.setBackground(Color.WHITE);
		FileSelected.setForeground(Color.BLACK);
		FileSelected.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		FileSelected.setBounds(104, 38, 320, 22);
		Interface1.getContentPane().add(FileSelected);
		
		Button ChooseFileB = new Button("Choose File");
		ChooseFileB.setBackground(Color.DARK_GRAY);
		ChooseFileB.setForeground(Color.WHITE);
		ChooseFileB.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		ChooseFileB.addActionListener(new ActionListener());
			
		
		ChooseFileB.setBounds(10, 38, 88, 22);
		Interface1.getContentPane().add(ChooseFileB);
		
		
		Label Instructions = new Label("Choose a cvs file to begin the Chow Liu");
		Instructions.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		Instructions.setBounds(10, 10, 281, 22);
		Interface1.getContentPane().add(Instructions);
		
		Button BeginB = new Button("Begin!");
		BeginB.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		BeginB.setBackground(new Color(0, 255, 127));
		BeginB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		BeginB.setBounds(10, 71, 88, 22);
		Interface1.getContentPane().add(BeginB);
		
		Label Results = new Label("");
		Results.setBounds(104, 71, 320, 22);
		Interface1.getContentPane().add(Results);
	}

