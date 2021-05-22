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
		frmWindow.setTitle("Window 1");
		frmWindow.setBounds(100, 100, 664, 154);
		frmWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWindow.getContentPane().setLayout(null);
		
		JLabel PathLbl = new JLabel("");
		PathLbl.setBackground(Color.BLACK);
		PathLbl.setBounds(122, 48, 504, 19);
		frmWindow.getContentPane().add(PathLbl);
		
		JButton openBtn = new JButton("Open file...");
		openBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(OpenFile.showOpenDialog(frmWindow) == JFileChooser.APPROVE_OPTION) {
					String path = OpenFile.getSelectedFile().getAbsolutePath();
					PathLbl.setText(path);
			}
		}});
		openBtn.setBounds(10, 44, 102, 23);
		frmWindow.getContentPane().add(openBtn);
		
		JButton GoBtn = new JButton("Go!");
		GoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				String path = PathLbl.getText();
				if (! path.isBlank()) {
					Dataset data = FileHandling.getDataset(path);
					System.out.println(path);
					String[] SplitEndOfPath = path.split("\\\\");
					System.out.println(Arrays.toString(SplitEndOfPath));
					String EndOfPath = SplitEndOfPath[(SplitEndOfPath.length)-1];
					System.out.println(EndOfPath);
					String[] SplitCSV = EndOfPath.split("\\.");
					System.out.println(Arrays.toString(SplitCSV));
					String FinalPath = SplitCSV[0];
					System.out.println(FinalPath);
					FileHandling.exportClassifier(data, FinalPath);
					//FileHandling.exportClassifier(data,"aqui");
					//FileHandling.exportClassifier(data,parsedPath[parsedPath.length-1].split(".")[0]);
				}
			}
		});
		GoBtn.setBounds(537, 70, 89, 23);
		frmWindow.getContentPane().add(GoBtn);
		
		JLabel lblNewLabel = new JLabel("Select a dataset to import");
		lblNewLabel.setBounds(10, 23, 376, 14);
		frmWindow.getContentPane().add(lblNewLabel);
	}
}
