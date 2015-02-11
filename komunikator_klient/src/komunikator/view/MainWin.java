package komunikator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;

public class MainWin extends JFrame {

	public interface MainWindowListener {
		public void onUserSendMessage(String msg);
	}
	private JPanel contentPane;
	public JTextArea getMessagesbox() {
		return textAreaComunicator;
	}
	private JTextField txtToSend;
	private JButton btnSendMsg;
	private JTextArea textAreaComunicator;

	public void setMainWindowListener(MainWindowListener l) {
		btnSendMsg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				if(txtToSend.getText() != ""){
					l.onUserSendMessage(txtToSend.getText());
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public MainWin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		textAreaComunicator = new JTextArea();
		textAreaComunicator.setEditable(false);
		panel.add(textAreaComunicator);
		
		JScrollPane scrollPane = new JScrollPane(textAreaComunicator);
		panel.add(scrollPane);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		txtToSend = new JTextField();
		panel_1.add(txtToSend);
		txtToSend.setColumns(10);
		txtToSend.setMaximumSize(new Dimension(Integer.MAX_VALUE,25));
		
		Component horizontalStrut = Box.createHorizontalStrut(10);
		panel_1.add(horizontalStrut);
		horizontalStrut.setMaximumSize(new Dimension(horizontalStrut.getMaximumSize().width,25));
		
		btnSendMsg = new JButton("Send");
		panel_1.add(btnSendMsg);
	}
}
