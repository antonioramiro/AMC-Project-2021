package CancerClassifier;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;

public class Interface1 {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface1 window = new Interface1();
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
	public Interface1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 453, 153);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel Instructions = new JLabel("Choose a cvs file to begin");
		Instructions.setForeground(Color.DARK_GRAY);
		Instructions.setFont(new Font("Arial", Font.PLAIN, 12));
		Instructions.setBounds(10, 11, 163, 14);
		frame.getContentPane().add(Instructions);
		
		JButton ChooseFileB = new JButton("Choose File...");
		ChooseFileB.setForeground(Color.DARK_GRAY);
		ChooseFileB.setBackground(Color.WHITE);
		ChooseFileB.setFont(new Font("Arial", Font.PLAIN, 12));
		ChooseFileB.setBounds(10, 36, 128, 23);
		frame.getContentPane().add(ChooseFileB);
		
		JLabel FileLabel = new JLabel("");
		FileLabel.setBackground(Color.WHITE);
		FileLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		FileLabel.setBounds(127, 36, 297, 23);
		frame.getContentPane().add(FileLabel);
		
		JButton BeginB = new JButton("Begin");
		BeginB.setBackground(Color.WHITE);
		BeginB.setBounds(30, 73, 89, 23);
		frame.getContentPane().add(BeginB);
		
		JLabel UpdateLabel = new JLabel("");
		UpdateLabel.setBounds(127, 77, 297, 19);
		frame.getContentPane().add(UpdateLabel);
	}
}
