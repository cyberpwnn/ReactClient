package org.cyberpwn.react;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Toolkit;

public class Main
{
	
	private JFrame frame;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Main window = new Main();
					window.frame.setVisible(true);
				} catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public Main()
	{
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/org/cyberpwn/react/icon.png")));
		frame.setBounds(100, 100, 813, 610);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnReact = new JMenu("React");
		menuBar.add(mnReact);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnReact.add(mntmAbout);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnReact.add(mntmExit);
		
		JMenu mnConnections = new JMenu("Connections");
		menuBar.add(mnConnections);
		
		JMenuItem mntmAddConnection = new JMenuItem("Add Connection");
		mnConnections.add(mntmAddConnection);
		
		JMenuItem mntmConnectionSettings = new JMenuItem("Connection Settings");
		mnConnections.add(mntmConnectionSettings);
	}
	
}
