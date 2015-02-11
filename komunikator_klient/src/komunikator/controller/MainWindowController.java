package komunikator.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.SwingUtilities;

import komunikator.view.MainWin;
import komunikator.view.MainWin.MainWindowListener;

public class MainWindowController {
	
	private BufferedReader reader;
	private PrintWriter writer;
	private Socket socket;
	private MainWin mainWin;
	private String userName;
	
	private MainWindowListener mWinListener = new MainWindowListener() {
		@Override
		public void onUserSendMessage(String msg) {
			writer.println(msg);
			writer.flush();
		}
	};
	
	public MainWindowController(BufferedReader reader,PrintWriter writer,Socket socket,String userName) {
		this.reader = reader;
		this.writer = writer;
		this.socket = socket;
		this.userName = userName;
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				listenForMessages();
			}
		}).start();;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mainWin = new MainWin();
				mainWin.setVisible(true);
				mainWin.setMainWindowListener(mWinListener);
			}
		});
	}
	
	private void listenForMessages() {
		String message;
		try {
			while((message = reader.readLine()) != null) {
				printToConsole(message);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void printToConsole(String s) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		mainWin.getMessagesbox().setText(mainWin.getMessagesbox().getText() + "\n" + dateFormat.format(date) + ": "+ s);
	}
}
