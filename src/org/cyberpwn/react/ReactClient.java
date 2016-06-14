package org.cyberpwn.react;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import org.apache.commons.lang3.StringUtils;
import org.cyberpwn.react.network.Network;
import org.cyberpwn.react.network.NetworkScheduler;
import org.cyberpwn.react.network.NetworkedServer;
import org.cyberpwn.react.ui.AbstractTabRenderer;
import org.cyberpwn.react.ui.JXTabbedPane;

import net.miginfocom.swing.MigLayout;

public class ReactClient
{
	private final Network network;
	private NetworkScheduler ns;
	private JFrame frmReactClient;
	private static ReactClient instance;
	public JXTabbedPane tabbedPane;
	
	public static void main(String[] args)
	{
		instance = new ReactClient();
	}
	
	public ReactClient()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		
		catch(Exception e)
		{
			try
			{
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			}
			
			catch(Exception ex)
			{
				
			}
		}
		
		this.network = new Network();
		initialize();
		ns = new NetworkScheduler(network.getServers(), 500);
		ns.start();
	}
	
	private void initialize()
	{
		JMenuBar menuBar = new JMenuBar();
		JMenu mnReact = new JMenu("React");
		JMenuItem mntmAbout = new JMenuItem("About");
		JMenuItem mntmExit = new JMenuItem("Exit");
		JMenu mnConnections = new JMenu("Connections");
		JMenuItem mntmAddConnection = new JMenuItem("Add Connection");
		JMenuItem mntmConnectionSettings = new JMenuItem("Connection Settings");
		
		menuBar.add(mnReact);
		mnReact.add(mntmAbout);
		menuBar.add(mnConnections);
		mnConnections.add(mntmAddConnection);
		mnConnections.add(mntmConnectionSettings);
		mnReact.add(mntmExit);
		
		mntmAddConnection.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				addConnection();
			}
		});
		
		mntmExit.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				exit();
			}
		});
		
		mntmAbout.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				new AboutReact();
			}
		});
		
		frmReactClient = new JFrame();
		frmReactClient.setTitle("React Client");
		frmReactClient.setIconImage(Toolkit.getDefaultToolkit().getImage(ReactClient.class.getResource("/org/cyberpwn/react/ui/icon.png")));
		frmReactClient.setBounds(100, 100, 813, 610);
		frmReactClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmReactClient.setJMenuBar(menuBar);
		frmReactClient.getContentPane().setLayout(new BorderLayout(0, 0));
		
		/*
		 * TABS
		 */
		
		if(network.getServers().isEmpty())
		{
			showTutorial();
		}
		
		else
		{
			tabbedPane = new JXTabbedPane(JTabbedPane.LEFT);
			AbstractTabRenderer renderer = (AbstractTabRenderer) tabbedPane.getTabRenderer();
			renderer.setPrototypeText("This text is a prototype");
			renderer.setPrototypeIcon(new ImageIcon(ReactClient.class.getResource("/org/cyberpwn/react/ui/server-mini.png")));
			renderer.setHorizontalTextAlignment(SwingConstants.LEADING);
			tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			tabbedPane.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
			frmReactClient.getContentPane().add(tabbedPane, BorderLayout.CENTER);
			
			System.out.println("Compiling Server List...");
			
			for(NetworkedServer i : network.getServers())
			{
				System.out.println(" > Building (" + i.getName() + ") " + i.getAddress() + ":" + i.getPort() + " @ " + i.getUsername() + "/" + StringUtils.repeat('*', i.getPassword().length()));
				i.setTab(new ServerTab(frmReactClient, i, tabbedPane));
			}
		}
		
		frmReactClient.setVisible(true);
	}
	
	public void addConnection()
	{
		AddConnection.addConnection();
	}
	
	public void showTutorial()
	{
		JPanel panelTutorial = new JPanel();
		panelTutorial.setBackground(Color.WHITE);
		panelTutorial.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		frmReactClient.getContentPane().add(panelTutorial, BorderLayout.CENTER);
		panelTutorial.setLayout(new MigLayout("", "[grow]", "[][][grow]"));
		
		JLabel lblAddYourFirst = new JLabel("Add Your First Server");
		lblAddYourFirst.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		panelTutorial.add(lblAddYourFirst, "cell 0 0");
		
		JLabel lblNewLabel = new JLabel("Add Your first server by clicking Connections > Add Connection in the menu bar.");
		lblNewLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panelTutorial.add(lblNewLabel, "cell 0 1");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panelTutorial.add(panel_1, "cell 0 2,grow");
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblLabel = new JLabel(new ImageIcon(ReactClient.class.getResource("/org/cyberpwn/react/ui/server-low.png")));
		panel_1.add(lblLabel, BorderLayout.CENTER);
		
		JLabel lblLabel2 = new JLabel(new ImageIcon(ReactClient.class.getResource("/org/cyberpwn/react/ui/icon.png")));
		panel_1.add(lblLabel2, BorderLayout.WEST);
		
		JLabel lblNewLabel_1 = new JLabel(new ImageIcon(ReactClient.class.getResource("/org/cyberpwn/react/ui/icon.png")));
		panel_1.add(lblNewLabel_1, BorderLayout.EAST);
	}
	
	public static void restart()
	{
		instance.ns.interrupt();
		Dimension d = instance.frmReactClient.getSize();
		Point p = instance.frmReactClient.getLocationOnScreen();
		instance.frmReactClient.setVisible(false);
		instance.frmReactClient.dispose();
		System.out.println("Rebuilding & Restarting Client...");
		instance = new ReactClient();
		instance.frmReactClient.setSize((int) d.getWidth(), (int) d.getHeight());
		instance.frmReactClient.setLocation(p);
	}
	
	public void addTab(String name)
	{
		
	}
	
	public void exit()
	{
		ns.interrupt();
		System.exit(0);
	}
	
	public Network getNetwork()
	{
		return network;
	}
	
	public static ReactClient getInstance()
	{
		return instance;
	}
	
	public void validateConnectionAdd(String name, String address, int port, String username, String password)
	{
		getNetwork().addConnection(name, address, port, username, password);
		System.out.println("Connection Added: (" + name + ") " + address + ":" + port + " @ " + username + "/" + StringUtils.repeat('*', password.length()));
		restart();
	}
	
	public void deleteConnection(NetworkedServer ns)
	{
		network.deleteServer(ns);
		try
		{
			network.save();
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		restart();
	}
}
