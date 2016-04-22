package com.glacialrush.react.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.UIManager;

import com.glacialrush.react.json.JSONObject;

public class Login
{
	private JFrame frmReactClient;
	private JTextField address;
	private JTextField port;
	private JTextField username;
	private JTextField password;
	private JProgressBar progressBar;
	private JSONObject config;
	public static Login instance;
	private static boolean app = false;
	
	/**
	 * Launch the application.
	 */
	public static void login()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Login window = new Login();
					instance = window;
					window.frmReactClient.setVisible(true);
				}
				
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public Login()
	{
		File f = new File(new File(".").getAbsolutePath(), "React");
		f.mkdirs();
		System.out.println("Loading Config at: " + f.getAbsolutePath());
		config = new JSONObject();
		config.put("address", "localhost");
		config.put("port", "8118");
		config.put("username", "cyberpwn");
		config.put("password", "react123");
		File c = new File(f, "login.dat");
		
		if(c.exists())
		{
			try
			{
				FileInputStream fin = new FileInputStream(c);
				DataInputStream dis = new DataInputStream(fin);
				String json = dis.readUTF();
				dis.close();
				config = new JSONObject(json);
			}
			
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		else
		{
			try
			{
				c.createNewFile();
				FileOutputStream fos = new FileOutputStream(c);
				DataOutputStream dos = new DataOutputStream(fos);
				dos.writeUTF(config.toString());
				dos.flush();
				dos.close();
			}
			
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmReactClient = new JFrame();
		frmReactClient.setBackground(Color.DARK_GRAY);
		frmReactClient.setAlwaysOnTop(true);
		frmReactClient.setTitle("React Client");
		frmReactClient.setResizable(false);
		frmReactClient.setBounds(100, 100, 331, 352);
		frmReactClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmReactClient.setIconImage(Toolkit.getDefaultToolkit().getImage(Error.class.getResource("/com/glacialrush/react/icon.png")));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		frmReactClient.getContentPane().add(panel, BorderLayout.CENTER);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		JLabel lblReact = new JLabel("React Remote");
		sl_panel.putConstraint(SpringLayout.NORTH, lblReact, 10, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, lblReact, 49, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, lblReact, -50, SpringLayout.EAST, panel);
		lblReact.setFont(new Font("Yu Gothic Light", Font.PLAIN, 36));
		lblReact.setForeground(Color.CYAN);
		panel.add(lblReact);
		
		JLabel lblNewLabel = new JLabel("Address");
		sl_panel.putConstraint(SpringLayout.NORTH, lblNewLabel, 7, SpringLayout.SOUTH, lblReact);
		sl_panel.putConstraint(SpringLayout.WEST, lblNewLabel, 49, SpringLayout.WEST, panel);
		lblNewLabel.setFont(new Font("Yu Gothic Light", Font.PLAIN, 24));
		lblNewLabel.setForeground(Color.CYAN);
		panel.add(lblNewLabel);
		
		address = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, address, 6, SpringLayout.SOUTH, lblNewLabel);
		sl_panel.putConstraint(SpringLayout.WEST, address, 49, SpringLayout.WEST, panel);
		address.setFont(new Font("Yu Gothic", Font.PLAIN, 13));
		address.setText(config.getString("address"));
		address.setBackground(Color.GRAY);
		address.setForeground(Color.WHITE);
		panel.add(address);
		address.setColumns(10);
		
		port = new JTextField();
		sl_panel.putConstraint(SpringLayout.WEST, port, 6, SpringLayout.EAST, address);
		port.setText(config.getString("port"));
		port.setBackground(Color.GRAY);
		port.setForeground(Color.WHITE);
		port.setFont(new Font("Yu Gothic", Font.PLAIN, 13));
		panel.add(port);
		port.setColumns(10);
		
		JLabel lblPort = new JLabel("Port");
		sl_panel.putConstraint(SpringLayout.EAST, port, 0, SpringLayout.EAST, lblPort);
		sl_panel.putConstraint(SpringLayout.WEST, lblPort, 39, SpringLayout.EAST, lblNewLabel);
		sl_panel.putConstraint(SpringLayout.NORTH, port, 6, SpringLayout.SOUTH, lblPort);
		sl_panel.putConstraint(SpringLayout.NORTH, lblPort, 0, SpringLayout.NORTH, lblNewLabel);
		lblPort.setFont(new Font("Yu Gothic Light", Font.PLAIN, 24));
		lblPort.setForeground(Color.CYAN);
		panel.add(lblPort);
		
		JLabel lblLogin = new JLabel("Login");
		sl_panel.putConstraint(SpringLayout.NORTH, lblLogin, 6, SpringLayout.SOUTH, address);
		sl_panel.putConstraint(SpringLayout.WEST, lblLogin, 49, SpringLayout.WEST, panel);
		lblLogin.setForeground(Color.CYAN);
		lblLogin.setFont(new Font("Yu Gothic Light", Font.PLAIN, 24));
		panel.add(lblLogin);
		
		username = new JTextField();
		sl_panel.putConstraint(SpringLayout.WEST, username, 49, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, username, 0, SpringLayout.EAST, address);
		username.setBackground(Color.GRAY);
		username.setForeground(Color.WHITE);
		username.setFont(new Font("Yu Gothic", Font.PLAIN, 13));
		sl_panel.putConstraint(SpringLayout.NORTH, username, 6, SpringLayout.SOUTH, lblLogin);
		username.setText(config.getString("username"));
		panel.add(username);
		username.setColumns(10);
		
		password = new JTextField();
		sl_panel.putConstraint(SpringLayout.EAST, password, 0, SpringLayout.EAST, lblReact);
		password.setFont(new Font("Yu Gothic", Font.PLAIN, 13));
		password.setBackground(Color.GRAY);
		password.setForeground(Color.WHITE);
		password.setText(config.getString("password"));
		sl_panel.putConstraint(SpringLayout.NORTH, password, 52, SpringLayout.SOUTH, port);
		sl_panel.putConstraint(SpringLayout.WEST, password, 6, SpringLayout.EAST, username);
		panel.add(password);
		password.setColumns(10);
		
		JCheckBox remember = new JCheckBox("Remember Connection");
		remember.setSelected(true);
		sl_panel.putConstraint(SpringLayout.WEST, remember, 49, SpringLayout.WEST, panel);
		remember.setBackground(Color.DARK_GRAY);
		remember.setForeground(Color.WHITE);
		sl_panel.putConstraint(SpringLayout.NORTH, remember, 6, SpringLayout.SOUTH, username);
		panel.add(remember);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				doConnect(remember.isSelected());
			}
		});
		
		sl_panel.putConstraint(SpringLayout.NORTH, btnConnect, 10, SpringLayout.SOUTH, remember);
		sl_panel.putConstraint(SpringLayout.WEST, btnConnect, 51, SpringLayout.WEST, panel);
		btnConnect.setFont(new Font("Yu Gothic", Font.PLAIN, 13));
		btnConnect.setBackground(Color.CYAN);
		btnConnect.setForeground(new Color(0, 0, 0));
		panel.add(btnConnect);
		
		progressBar = new JProgressBar();
		sl_panel.putConstraint(SpringLayout.NORTH, progressBar, 12, SpringLayout.SOUTH, remember);
		sl_panel.putConstraint(SpringLayout.WEST, progressBar, 6, SpringLayout.EAST, btnConnect);
		sl_panel.putConstraint(SpringLayout.SOUTH, progressBar, 0, SpringLayout.SOUTH, btnConnect);
		sl_panel.putConstraint(SpringLayout.EAST, progressBar, 11, SpringLayout.EAST, lblReact);
		progressBar.setValue(0);
		progressBar.setForeground(UIManager.getColor("Table.selectionBackground"));
		progressBar.setBackground(Color.WHITE);
		progressBar.setVisible(false);
		progressBar.setFont(new Font("Yu Gothic", Font.PLAIN, 13));
		panel.add(progressBar);
	}
	
	public void doConnect(boolean save)
	{
		String address = this.address.getText();
		Integer port = Integer.valueOf(this.port.getText());
		String username = this.username.getText();
		String password = this.password.getText();
		
		if(save)
		{
			config.put("address", address);
			config.put("port", port.toString());
			config.put("username", username);
			config.put("password", password);
			File f = new File(new File(".").getAbsolutePath(), "React");
			System.out.println("Saving Config at: " + f.getAbsolutePath());
			File c = new File(f, "login.dat");
			
			try
			{
				c.delete();
				c.createNewFile();
				FileOutputStream fos = new FileOutputStream(c);
				DataOutputStream dos = new DataOutputStream(fos);
				dos.writeUTF(config.toString());
				dos.flush();
				dos.close();
			}
			
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
		}
		
		progressBar.setVisible(true);
		progressBar.setIndeterminate(true);
		ClientThread t = new ClientThread(address, port, username, password);
		t.start();
	}
	
	public static void begin()
	{
		instance.frmReactClient.dispose();
		Application.login();
		app = true;
	}
	
	public static void error(String title, String text, boolean ext)
	{
		if(app)
		{
			Application.error(title, text, ext);
			return;
		}
		
		instance.frmReactClient.dispose();
		Error.error(title, text, ext);
	}
	
	public static void unerror()
	{
		if(app)
		{
			Application.unerror();
			return;
		}
		
		login();
	}
}
