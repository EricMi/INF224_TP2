package INF224;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class MainWindow extends JFrame {
	/**
	 * Set class version.
	 */
	private static final long serialVersionUID = 1L;
	static final String DEFAULT_HOST = "localhost";
	static final int DEFAULT_PORT = 3331;
	private Socket sock;
	private BufferedReader input;
	private BufferedWriter output;
	
	protected JButton btn1, btn2, btn3;
	protected JTextArea text;
	protected JScrollPane textScroll;
	protected JTextField entry;
	protected static JPanel panel;
	
	protected static JMenuBar menuBar;
	protected JMenu menu;
	
	protected JToolBar toolBar;
	
	public MainWindow(String host, int port) throws UnknownHostException, IOException {
		// Graphic part:
		super("Main Window");
		
		setLayout(new BorderLayout());
		
		panel = new JPanel(new BorderLayout());
		panel.setOpaque(true);
		
		btn1 = new JButton("Print");
		btn2 = new JButton("Play");
		btn3 = new JButton("Exit");
		
		//btn1.addActionListener(new AddTextListener1());
		//btn2.addActionListener(new AddTextListener2());
		btn3.addActionListener(new ExitListener());
		
		text = new JTextArea();
		text.setColumns(70);
		text.setLineWrap(true);
		text.setRows(10);
		text.setWrapStyleWord(true);
		text.setEditable(false);
		
		textScroll = new JScrollPane();
		textScroll.setViewportView(text);
		
		//panel.add(btn1, BorderLayout.WEST);
		//panel.add(btn2, BorderLayout.CENTER);
		//panel.add(btn3, BorderLayout.EAST);
		panel.add(textScroll, BorderLayout.SOUTH);
		
		menuBar = new JMenuBar();
		menu = new JMenu("Command");
		JMenuItem mItem1, mItem2;
		
		Action addText1 = new AddTextAction("Print", "PRINT");
		Action addText2 = new AddTextAction("Play", "PLAY");
		mItem1 = new JMenuItem(addText1);
		mItem2 = new JMenuItem(addText2);
		mItem1.setIcon(null);
		mItem2.setIcon(null);
		mItem1.getAccessibleContext().setAccessibleDescription("Print information by name.");
		mItem2.getAccessibleContext().setAccessibleDescription("Play multimedia by name.");
		menu.add(mItem1);
		menu.add(mItem2);
		menuBar.add(menu);
		setJMenuBar(menuBar);
		
		toolBar = new JToolBar("Add Text");
		btn1.setAction(addText1);
		btn2.setAction(addText2);
		entry = new JTextField("name");
		entry.setColumns(20);
		toolBar.add(btn1);
		toolBar.add(btn2);
		toolBar.add(btn3);
		toolBar.add(entry);
		panel.add(toolBar, BorderLayout.NORTH);
		
		// Network part:
		try {
			sock = new java.net.Socket(host, port);
		}
		catch(java.net.UnknownHostException e) {
			System.err.println("Client: Couldn't find host " + host + ":" + port);
			throw e;
		}
		catch(java.io.IOException e) {
			System.err.println("Client: Couldn't reach host " + host + ":" + port);
			throw e;
		}
		
		try {
			input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			output = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
		}
		catch(java.io.IOException e) {
			System.err.println("Client: Couldn't open input or output streams");
			throw e;
		}
	}
	
	public String send(String request) {
		try {
			request += "\n";
			output.write(request, 0, request.length());
			output.flush();
		}
		catch(java.io.IOException e) {
			System.err.println("Client: Couldn't send message: " + e);
			return null;
		}
		
		try {
			return input.readLine();
		}
		catch(java.io.IOException e) {
			System.err.println("Client: Couldn't receive message: " + e);
			return null;
		}
	}

	public static void main(String argv[]) {
		// Schedule a job for the event-dispatching thread:
		// crating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				String host = DEFAULT_HOST;
				int port = DEFAULT_PORT;
				if(argv.length >= 1) host = argv[0];
				if(argv.length >= 2) port = Integer.parseInt(argv[1]);
				
				MainWindow frame = null;
				
				try {
					// Create and set up the window.
					frame = new MainWindow(host, port);		
					frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
					frame.setContentPane(panel);

					// Display the window.
					frame.pack();
					frame.setVisible(true);
				}
				catch(Exception e) {
					System.err.println("Client: Couldn't connect to " + host + ":" + port);
					System.exit(1);
				}
				System.out.println("Client connected to " + host + ":" + port);
			}
		});
	}
	
	/*
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
	*/
	
	class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
	class AddTextAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		
		private String command;
		public AddTextAction(String text, String s) {
			super(text);
			command = s;
		}
		
		public void actionPerformed(ActionEvent e) {
			String request, response;
			String name = entry.getText();
			if(name.length() == 0) {
				JFrame diag = new JFrame("Error: empty name");
				JOptionPane.showMessageDialog(diag, "You must input the name!", "Error: empty name", JOptionPane.ERROR_MESSAGE);
				
			} else {
				request = command + " " + name;
				response = send(request);
				text.append(response);
			}
		}
	}
}
