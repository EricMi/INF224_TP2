package INF224;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {
	/**
	 * Set class version
	 */
	private static final long serialVersionUID = 1L;
	
	protected JButton btn1, btn2, btn3;
	protected JTextArea text;
	protected JScrollPane textScroll;
	protected static JPanel panel;
	
	public MainWindow() {
		super("Main Window");
		
		setLayout(new BorderLayout());
		
		panel = new JPanel(new BorderLayout());
		panel.setOpaque(true);
		
		btn1 = new JButton("Add Text1");
		btn2 = new JButton("Add Text2");
		btn3 = new JButton("Exit");
		
		btn1.addActionListener(new AddTextListener1());
		btn2.addActionListener(new AddTextListener2());
		btn3.addActionListener(new ExitListener());
		
		text = new JTextArea();
		text.setColumns(22);
		text.setLineWrap(true);
		text.setRows(10);
		text.setWrapStyleWord(true);
		text.setEditable(false);
		
		textScroll = new JScrollPane();
		textScroll.setViewportView(text);
		
		panel.add(btn1, BorderLayout.WEST);
		panel.add(btn2, BorderLayout.CENTER);
		panel.add(btn3, BorderLayout.EAST);
		panel.add(textScroll, BorderLayout.SOUTH);
	}
	
	private static void createAndShowGUI() {
		MainWindow frame = new MainWindow();		
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String argv[]) {
		// Schedule a job for the event-dispatching thread:
		// crating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
	
	class AddTextListener1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			text.append("Button \"Add Text1\" actived.\n");
		}
	}
	
	class AddTextListener2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			text.append("Button \"Add Text2\" actived.\n");
		}
	}
	
	class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
}
