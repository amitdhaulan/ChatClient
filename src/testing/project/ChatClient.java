package testing.project;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;


public class ChatClient extends Frame implements ActionListener {
	private static final long serialVersionUID = 1L;
	String uname;
	PrintWriter pw;
	BufferedReader br;
	TextArea taMessages;
	TextField tfInput;
	Button btnSend, btnExit;
	Socket client;
	JScrollPane sp;

	public ChatClient(String uname, String servername) throws Exception {

		super(uname); // set title for frame
		this.uname = uname;
		client = new Socket("192.168.168.61", 8980);
		br = new BufferedReader(new InputStreamReader(client.getInputStream()));
		pw = new PrintWriter(client.getOutputStream(), true);

		pw.println(uname); // send name to server
		buildInterface();
		new MessagesThread().start(); // create thread for listening for
										// messages
		
	}
	
	public void buildInterface() {
		btnSend = new Button("Send");
		btnExit = new Button("Exit");
		taMessages = new TextArea();
		taMessages.setRows(5);
		taMessages.setColumns(5);
		taMessages.setEditable(false);
		taMessages.setFocusable(false);
		tfInput = new TextField(20);
		sp = new JScrollPane(taMessages);
		add(sp, "Center");
		Panel bp = new Panel(new FlowLayout());
		tfInput.setFocusable(true);
		bp.add(tfInput);
		bp.add(btnSend);
		bp.add(btnExit);
		add(bp, "South");
		btnSend.addActionListener(this);
		btnExit.addActionListener(this);
		setSize(500, 300);
		setVisible(true);
		pack();
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btnExit) {
			pw.println("end"); // send end to server so that server know about
								// the termination
			System.exit(0);
		} else if (evt.getSource() == btnSend) {
			// send message to server
			
			
//			ecifm amit kumar singh kashyap 534779 _(*&&^%# this is to be done like this 

			pw.println(tfInput.getText());
			tfInput.setText("");
		}
	}

	public static void main(String[] args) {

		// take username from user
		String name = JOptionPane.showInputDialog(null, "Enter your name :",
				"Username", JOptionPane.PLAIN_MESSAGE);
		String servername = "localhost";
		try {
			new ChatClient(name, servername);
		} catch (Exception ex) {
			System.out.println("Error --> " + ex.getMessage());
		}

	} // end of main

	// inner class for Messages Thread
	class MessagesThread extends Thread {
		public void run() {
			String line;
			try {
				while (true) {
					line = br.readLine();
					taMessages.append(line + "\n");
					
					
					
				} // end of while
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		
	}
}