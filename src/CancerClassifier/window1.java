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
		OpenFile.setFileFilter(new FileNameExtensionFilter("cvs documents","csv"));
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWindow = new JFrame();
		frmWindow.setTitle("Window 1");
		frmWindow.setBounds(100, 100, 364, 154);
		frmWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWindow.getContentPane().setLayout(null);
		
		JLabel PathLbl = new JLabel("");
		PathLbl.setBounds(122, 27, 216, 19);
		frmWindow.getContentPane().add(PathLbl);
		
		JButton openBtn = new JButton("Open file...");
		openBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(OpenFile.showOpenDialog(frmWindow) == JFileChooser.APPROVE_OPTION) {
					String path = OpenFile.getSelectedFile().getAbsolutePath();
					PathLbl.setText(path);
			}
		}});
		openBtn.setBounds(10, 25, 111, 23);
		frmWindow.getContentPane().add(openBtn);
		
		JButton GoBtn = new JButton("Go!");
		GoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				String path = PathLbl.getText();
				if (! path.isBlank()) {
					Dataset data = FileHandling.getDataset(path);
					System.out.println(data);
				}
			}
		});
		GoBtn.setBounds(245, 76, 89, 23);
		frmWindow.getContentPane().add(GoBtn);
	}
}
