package komunikator_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.rmi.CORBA.Tie;
import javax.swing.JTextArea;

import komunikator_server.MainWindow.MainWindowListener;

public class MainWindowController {
	
	//obczaja kazdego uzytwkonika czy czegos nie wyslal
	private class ClientHandler implements Runnable {
		
		BufferedReader reader;
		Socket socket;
		String userName;
		
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public ClientHandler(Socket socket) {
			try{
				this.socket = socket;
				InputStreamReader strReader = new InputStreamReader(socket.getInputStream());
				reader = new BufferedReader(strReader);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		@Override
		public void run() {
			String message;
			try {
				while((message = reader.readLine()) != null) {
					if(userName == null) {
						userName = message;
					}
					else tellEveryone(userName,message);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private MainWindow window;
	private JTextArea serverConsole;
	private BufferedReader reader;
	private ArrayList<PrintWriter> clientsOutputStreams;
	private MainWindowListener mainWinListener = new MainWindowListener() {
		@Override
		public void onGoButtonPressed() {
			initServer();
		}
	};
	//dodawanie uzytrkwonikow
	private void initServer() {
		try {
			ServerSocket serverSocket = new ServerSocket(Program.PORT);
			printToConsole("server initialized");
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						try {
							Socket socket = serverSocket.accept();
							PrintWriter writer = new PrintWriter(socket.getOutputStream());
							clientsOutputStreams.add(writer);
							new Thread(new ClientHandler(socket)).start();
							printToConsole("new user connected");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void printToConsole(String s) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		serverConsole.setText(serverConsole.getText() + "\n" + dateFormat.format(date) + ": "+ s);
	}
	public MainWindowController(){
		clientsOutputStreams = new ArrayList<PrintWriter>();
		window = new MainWindow();
		window.setVisible(true);
		window.setMainWindowListener(mainWinListener);
		serverConsole = window.getTextAreaServerDebug();
	}
	private void tellEveryone(String userName,String msg) {
		for(PrintWriter userOutputStream : clientsOutputStreams) {
			userOutputStream.println(" / " + userName + ": " + msg );
			userOutputStream.flush();
		}
		printToConsole(userName + " says '" + msg + "'");
	}
}
