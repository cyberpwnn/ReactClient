package org.cyberpwn.react;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

import org.cyberpwn.react.network.Network;
import org.cyberpwn.react.ui.AbstractTabRenderer;
import org.cyberpwn.react.ui.JXTabbedPane;

import net.miginfocom.swing.MigLayout;

public class ReactClient
{
	private final Network network;
	
	private JFrame frame;
	private static ReactClient instance;
	
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
	}
	
	private void initialize()
	{
		JMenuBar menuBar = new JMenuBar();
		JMenu mnReact = new JMenu("React");
		JMenuItem mntmAbout = new JMenuItem("About");
		
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(ReactClient.class.getResource("/org/cyberpwn/react/ui/icon.png")));
		frame.setBounds(100, 100, 813, 610);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(menuBar);
		menuBar.add(mnReact);
		mnReact.add(mntmAbout);
		JMenuItem mntmExit = new JMenuItem("Exit");
		
		mnReact.add(mntmExit);
		
		mntmExit.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				exit();
			}
		});
		
		JMenu mnConnections = new JMenu("Connections");
		JMenuItem mntmAddConnection = new JMenuItem("Add Connection");
		JMenuItem mntmConnectionSettings = new JMenuItem("Connection Settings");
		menuBar.add(mnConnections);
		mnConnections.add(mntmAddConnection);
		mnConnections.add(mntmConnectionSettings);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		/*
		TAB
		*/
		
		JXTabbedPane tabbedPane = new JXTabbedPane(JTabbedPane.LEFT);
		AbstractTabRenderer renderer = (AbstractTabRenderer) tabbedPane.getTabRenderer();
		renderer.setPrototypeText("This text is a prototype");
		renderer.setPrototypeIcon(new ImageIcon(ReactClient.class.getResource("/org/cyberpwn/react/ui/server-mini.png")));
		renderer.setHorizontalTextAlignment(SwingConstants.LEADING);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Example Server", new ImageIcon(ReactClient.class.getResource("/org/cyberpwn/react/ui/server-mini.png")), panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane_1, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		tabbedPane_1.addTab("General", null, panel_1, null);
		panel_1.setLayout(new MigLayout("", "[grow][][][grow][][][][][][][][][][grow]", "[grow][][][][grow][grow][][][]"));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_5.setBackground(Color.WHITE);
		panel_1.add(panel_5, "cell 0 0 13 4,grow");
		panel_5.setLayout(new MigLayout("", "[][][][]", "[][][][][][][]"));
		
		JLabel lblServerName = new JLabel("Server Name");
		lblServerName.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		panel_5.add(lblServerName, "cell 0 0");
		
		JLabel lblUsingSpigot = new JLabel("Using Spigot 1.8.8");
		lblUsingSpigot.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panel_5.add(lblUsingSpigot, "cell 0 1");
		
		JLabel lblOnline = new JLabel("146 Players Online");
		lblOnline.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panel_5.add(lblOnline, "cell 0 2");
		
		JLabel lblHours = new JLabel("7 Hours Uptime");
		lblHours.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panel_5.add(lblHours, "cell 0 3");
		
		JLabel lblmsPing = new JLabel("43 Plugins");
		lblmsPing.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panel_5.add(lblmsPing, "cell 0 4");
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_8.setBackground(Color.WHITE);
		panel_1.add(panel_8, "cell 13 0 1 4,grow");
		panel_8.setLayout(new MigLayout("", "[]", "[][][]"));
		
		JLabel lblConnection = new JLabel("Connection");
		lblConnection.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		panel_8.add(lblConnection, "cell 0 0");
		
		JLabel lblPacketLoss = new JLabel("Packet Loss 0% (0 Failures)");
		lblPacketLoss.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panel_8.add(lblPacketLoss, "cell 0 1");
		
		JLabel lblmsPing_1 = new JLabel("21ms Ping");
		lblmsPing_1.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panel_8.add(lblmsPing_1, "cell 0 2");
		
		JPanel panel_2 = new JPanel();
		tabbedPane_1.addTab("Performance", null, panel_2, null);
		panel_2.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.BOTTOM);
		panel_2.add(tabbedPane_2, "cell 0 0,grow");
		
		JPanel panel_6 = new JPanel();
		tabbedPane_2.addTab("CPU", null, panel_6, null);
		panel_6.setLayout(new MigLayout("", "[grow][grow]", "[grow][grow][grow]"));
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_7.setBackground(Color.WHITE);
		panel_6.add(panel_7, "cell 0 0 2 3,grow");
		panel_7.setLayout(new MigLayout("", "[grow][]", "[][][grow]"));
		
		JLabel lblCpuLoad = new JLabel("Server Load");
		lblCpuLoad.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		panel_7.add(lblCpuLoad, "flowx,cell 0 0");
		
		JLabel lblTps = new JLabel("20 TPS (99% Stable)");
		lblTps.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panel_7.add(lblTps, "cell 1 0");
		
		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_10.setBackground(Color.DARK_GRAY);
		panel_7.add(panel_10, "cell 0 1 2 2,grow");
		
		JPanel panel_13 = new JPanel();
		tabbedPane_2.addTab("Memory", null, panel_13, null);
		panel_13.setLayout(new MigLayout("", "[grow][grow]", "[grow][grow]"));
		
		JPanel panel_14 = new JPanel();
		panel_14.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_14.setBackground(Color.WHITE);
		panel_13.add(panel_14, "cell 0 0 2 1,grow");
		panel_14.setLayout(new MigLayout("", "[grow][]", "[][grow]"));
		
		JLabel lblMemory = new JLabel("Memory");
		lblMemory.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		panel_14.add(lblMemory, "cell 0 0");
		
		JLabel lblMbUsed = new JLabel("683 MB Used");
		lblMbUsed.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panel_14.add(lblMbUsed, "cell 1 0");
		
		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_9.setBackground(Color.DARK_GRAY);
		panel_14.add(panel_9, "cell 0 1 2 1,grow");
		
		JPanel panel_16 = new JPanel();
		panel_16.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_16.setBackground(Color.WHITE);
		panel_13.add(panel_16, "cell 0 1,grow");
		panel_16.setLayout(new MigLayout("", "[grow][]", "[][grow]"));
		
		JLabel lblGarbage = new JLabel("Garbage");
		lblGarbage.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		panel_16.add(lblGarbage, "cell 0 0");
		
		JLabel lblGcminute = new JLabel("9 GC/Minute");
		lblGcminute.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panel_16.add(lblGcminute, "cell 1 0");
		
		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_11.setBackground(Color.DARK_GRAY);
		panel_16.add(panel_11, "cell 0 1 2 1,grow");
		
		JPanel panel_15 = new JPanel();
		panel_15.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_15.setBackground(Color.WHITE);
		panel_13.add(panel_15, "cell 1 1,grow");
		panel_15.setLayout(new MigLayout("", "[grow][]", "[][grow]"));
		
		JLabel lblState = new JLabel("Volatility");
		lblState.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		panel_15.add(lblState, "cell 0 0");
		
		JLabel lblMahs = new JLabel("264 MAH/s");
		lblMahs.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panel_15.add(lblMahs, "cell 1 0");
		
		JPanel panel_17 = new JPanel();
		panel_17.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_17.setBackground(Color.DARK_GRAY);
		panel_15.add(panel_17, "cell 0 1 2 1,grow");
		
		JPanel panel_4 = new JPanel();
		tabbedPane_1.addTab("Actions", null, panel_4, null);
		panel_4.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel_12 = new JPanel();
		panel_12.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_12.setBackground(Color.WHITE);
		panel_4.add(panel_12, "cell 0 0,grow");
		
		JLabel lblNotYetImplemented = new JLabel("Not Yet Implemented :(");
		lblNotYetImplemented.setFont(new Font("Segoe UI Light", Font.PLAIN, 58));
		panel_12.add(lblNotYetImplemented);
		
		JPanel panel_3 = new JPanel();
		tabbedPane_1.addTab("Problems", null, panel_3, null);
		panel_3.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel_18 = new JPanel();
		panel_18.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_18.setBackground(Color.WHITE);
		panel_3.add(panel_18, "cell 0 0,grow");
		
		JLabel lblNotYetImplemented_1 = new JLabel("Not Yet Implemented :L");
		lblNotYetImplemented_1.setFont(new Font("Segoe UI Light", Font.PLAIN, 58));
		panel_18.add(lblNotYetImplemented_1);
		
		mntmAbout.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				new AboutReact();
			}
		});
		
		frame.setVisible(true);
	}
	
	public void addTab(String name)
	{
		
	}
	
	public void exit()
	{
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
}
