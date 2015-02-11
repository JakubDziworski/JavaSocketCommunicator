package komunikator.view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;

import javax.swing.SwingConstants;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Console;

import javax.swing.JLabel;

public class LogInWindow extends JFrame{

	private JTextField textFieldnick;

	public JTextField getTextFieldnick() {
		return textFieldnick;
	}

	public JTextField getTextFieldIP() {
		return textFieldIP;
	}

	public void setBtnLoginClickListener(MouseAdapter l){
		btnLogin.addMouseListener(l);
	};

	private JTextField textFieldIP;
	private JButton btnLogin;
	private JLabel loadingLabel;

	/**
	 * Initialize the contents of the frame.
	 */
	public LogInWindow() {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(10, 10, 0, 0));
		
		panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		lblNewLabel = new JLabel("server ip:");
		panel_1.add(lblNewLabel);
		
		textFieldIP = new JTextField();
		textFieldIP.setText("127.0.0.1");
		panel_1.add(textFieldIP);
		textFieldIP.setColumns(10);
		
		panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		lblNick = new JLabel("nick");
		panel_2.add(lblNick);
		
		textFieldnick = new JTextField();
		textFieldnick.setText("kuba");
		panel_2.add(textFieldnick);
		textFieldnick.setColumns(15);
		
		btnLogin = new JButton("Log in");
		panel.add(btnLogin);
		
		loadingLabel = new JLabel("New label");
		loadingLabel.setHorizontalAlignment(SwingConstants.LEFT);
		loadingLabel.setVisible(false);
		panel.add(loadingLabel);
	}
	
	boolean runThread;
	private JPanel panel_1;
	private JLabel lblNewLabel;
	private JPanel panel_2;
	private JLabel lblNick;
	public void setLoading(boolean s) {
			loadingLabel.setVisible(s);
			runThread = s;
			if(!runThread) return;
			new Thread(new Runnable() {
				@Override
				public void run() {
					loadingLabel.setVisible(true);
					int i=1;
					while(runThread) {
						System.out.println("wykonywanie");
						StringBuilder strBuilder = new StringBuilder("Loading");
						for(int j=1;j<(i%4+1);j++) {
							strBuilder.append(".");
						}
						loadingLabel.setText(strBuilder.toString());
						i++;
						try {
							Thread.sleep(600);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
}
