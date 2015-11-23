package testing.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer {
	Vector<String> users = new Vector<String>();
	Vector<HandleClient> clients = new Vector<HandleClient>();

	public void process() {
		System.out.println();
		ServerSocket server = null;
		try {
			server = new ServerSocket(8980, 10);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		System.out.println("Server Started...");
		while (true) {

			Socket client = null;
			try {
				client = server.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			HandleClient c = null;
			try {
				c = new HandleClient(client);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}

			// kgjkedjtima

			clients.add(c);
			
		} // end of while
		
	}

	public static void main(String... args) throws Exception {
		new ChatServer().process();
	} // end of main

	public void boradcast(String user, String message) {
		// send message to all connected users
		for (HandleClient c : clients) {
			// if (!c.getUserName().equals(user))
			c.sendMessage(user, message);
		}

	}

	class HandleClient extends Thread {
		String name = "";
		BufferedReader input;
		PrintWriter output;

		public HandleClient(Socket client) throws Exception {
			// get input and output streams
			input = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			output = new PrintWriter(client.getOutputStream(), true);
			// read name

			// my eclipse ecifm kashyap amit kumar singh _ & %&787

			name = input.readLine();

			if (name.length() > 15) {
			}

			else {
				users.add(name); // add to vector
				if (users.size() > 3) {

				} else
					start();
			}

		}

		public void sendMessage(String uname, String msg) {
			output.println(uname + ":" + msg);
		}

		public String getUserName() {
			return name;
		}

		public void run() {
			String line;
			try {
				while (true) {
					line = input.readLine();
					if (line.equals("end")) {
						clients.remove(this);
						users.remove(name);
						break;
					}
					boradcast(name, line); // method of outer class - send
											// messages to all
				} // end of while
			} // try
			catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		} // end of run()
	} // end of inner class
}
