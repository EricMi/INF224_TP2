package INF224;

import javax.swing.*;
import java.awt.event.*;

public class MainWindow extends JFrame {
	JButton btn1, btn2, btn3;
	JTextArea t1;
	
	public static void main(String argv[]) {
		new MainWindow();
	}
	
	public MainWindow() {
		b1 = new JButton("Add Text1");
		add(b1);
		add(b2 = new JButton("Add Text2"));
		add(b3 = new JButton("Exit"));
		add(t1 = new JTextArea());
		
		b1.addActionListener(new AddTextListener1());
		b2.addActionListener(new AddTextListener2());
		b3.addActionListener(new ExitListener());
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	class AddTextListener1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			t1.append("Button \"Add Text1\" actived.\n");
		}
	}
	
	class AddTextListener2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			t1.append("Button \"Add Text2\" actived.\n");
		}
	}
	
	class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
}
