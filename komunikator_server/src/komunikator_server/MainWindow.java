package komunikator_server;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class MainWindow extends JFrame {

	public interface MainWindowListener {
		public void onGoButtonPressed();
	}
	public void setMainWindowListener(MainWindowListener l) {
		btnGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				l.onGoButtonPressed();
			}
		});
	}
	private JPanel contentPane;
	public JTextArea getTextAreaServerDebug() {
		return textAreaServerDebug;
	}
	public JTextField getTextFieldIpServer() {
		return textFieldIpServer;
	}
	private JTextField textFieldIpServer;
	private JButton btnGo;
	private JScrollPane scrollPane;
	private JTextArea textAreaServerDebug;
	public JButton getBtnGo() {
		return btnGo;
	}
	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		btnGo = new JButton("Go");
		btnGo.setBounds(212, 111, 89, 23);
		contentPane.add(btnGo);
		
		textFieldIpServer = new JTextField();
		textFieldIpServer.setText("127.0.0.1");
		textFieldIpServer.setBounds(82, 112, 115, 20);
		contentPane.add(textFieldIpServer);
		textFieldIpServer.setColumns(10);
		
		textAreaServerDebug = new JTextArea(5,30);
		
		scrollPane = new JScrollPane(textAreaServerDebug);
		scrollPane.setBounds(92, 145, 209, 94);
		contentPane.add(scrollPane);
		
		
	}
}
