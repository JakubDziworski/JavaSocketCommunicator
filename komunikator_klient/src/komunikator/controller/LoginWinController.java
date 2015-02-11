package komunikator.controller;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.SwingUtilities;

import komunikator.view.ErrorDialog;
import komunikator.view.LogInWindow;

public class LoginWinController {

	private LogInWindow win;
	private MouseAdapter loginButtonReleased = new MouseAdapter() {
		@Override
		public void mouseReleased(MouseEvent e) {
			if(win.getTextFieldIP().getText().compareTo("") == 0) {
				new ErrorDialog("Nie wpisa³eœ IP servera").setVisible(true);
				return;
			}
			if(win.getTextFieldnick().getText().compareTo("") == 0) {
				new ErrorDialog("Nie wpisa³eœ nicku").setVisible(true);
				return;
			}
			win.setLoading(true);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						initConnection(win.getTextFieldIP().getText());
						win.setLoading(false);
					} catch (UnknownHostException e1) {
						ErrorDialog err = new ErrorDialog("Nieznany Host.");
						err.setVisible(true);
						win.setLoading(false);
						e1.printStackTrace();
					} catch (IOException e1) {
						ErrorDialog err = new ErrorDialog("Serwer nieosi¹galny");
						err.setVisible(true);
						win.setLoading(false);
						e1.printStackTrace();
					} catch (Exception e2) {
						ErrorDialog err = new ErrorDialog("Wyst¹pi³ nieznany b³¹d.");
						err.setVisible(true);
						win.setLoading(false);
						e2.printStackTrace();
					}
				}
			}).start();
		}
	};
	private void serverLoadedSucessfully(Socket socket,BufferedReader reader,PrintWriter writer) {
		MainWindowController controller = new MainWindowController(reader,writer,socket,win.getTextFieldnick().getText());
		win.dispose();
	}
	private void initConnection(String ip) throws UnknownHostException, IOException {
		Socket socket = new Socket(ip,Programm.PORT);
		InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
		BufferedReader reader = new BufferedReader(streamReader);
		PrintWriter writer = new PrintWriter(socket.getOutputStream());
		writer.println(win.getTextFieldnick().getText());
		serverLoadedSucessfully(socket,reader,writer);
	}
	public LoginWinController(){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				win = new LogInWindow();
				win.setVisible(true);
				win.setBtnLoginClickListener(loginButtonReleased);
			}
		});
	}
}
