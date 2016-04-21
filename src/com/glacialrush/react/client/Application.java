package com.glacialrush.react.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.glacialrush.react.Version;
import com.glacialrush.react.json.JSONObject;
import com.glacialrush.react.util.Format;
import com.glacialrush.react.util.GList;

import net.miginfocom.swing.MigLayout;
import java.awt.SystemColor;

public class Application
{
	private JFrame frmReactClient;
	private static Application instance;
	private JPanel performance;
	private Cache cache;
	private JPanel world;
	
	private JLabel labelVersion;
	private JLabel lblRunningVersion;
	private JLabel lblTps;
	private JLabel lblMem;
	private JLabel lblMahs;
	private JLabel lblSpms_1;

	private int maxMem;
	private int maxThr;
	private String serverVersion;
	
	private JMenuItem mntmWiki;
	
	private JPanel TPS;
	private Grapher GTPS;
	
	private JPanel MEM;
	private Grapher GMEM;
	
	private JPanel THR;
	private Grapher GTHR;
	
	private JPanel RCT;
	private Grapher GRCT;
	
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
					Application window = new Application();
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
	public Application()
	{
		initialize();
		maxThr = 1;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		cache = new Cache();
		frmReactClient = new JFrame();
		frmReactClient.setBackground(Color.DARK_GRAY);
		frmReactClient.setTitle("React Client");
		frmReactClient.setBounds(100, 100, 902, 655);
		frmReactClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmReactClient.setIconImage(Toolkit.getDefaultToolkit().getImage(Error.class.getResource("/com/glacialrush/react/icon.png")));
		
		JMenuBar menuBar = new JMenuBar();
		frmReactClient.setJMenuBar(menuBar);
		
		JMenu mnReact = new JMenu("React");
		menuBar.add(mnReact);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				popup("About", "React Client v" + Version.V + "\nAlpha testing, If something isnt working, or is having problems, please understand it is not finished yet.");
			}
		});
		mnReact.add(mntmAbout);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e)
			{
				ClientThread.disconnect = true;
				instance.frmReactClient.dispose();
				System.exit(0);
			}
		});
		mnReact.add(mntmExit);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmSupport = new JMenuItem("Support");
		mntmSupport.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e)
			{
				try
				{
					Desktop.getDesktop().browse(new URI("https://www.spigotmc.org/threads/react-smart-server-performance-paid.136824/"));
				} 
				
				catch(IOException | URISyntaxException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		
		mntmWiki = new JMenuItem("Wiki");
		mntmWiki.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				try
				{
					Desktop.getDesktop().browse(new URI("https://github.com/cyberpwnn/React/wiki/Remote"));
				} 
				
				catch(IOException | URISyntaxException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		mnHelp.add(mntmWiki);
		mnHelp.add(mntmSupport);
		
		JMenuItem mntmSubmitIssue = new JMenuItem("Submit Bug");
		mntmSubmitIssue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e)
			{
				try
				{
					Desktop.getDesktop().browse(new URI("https://github.com/cyberpwnn/React/issues/new"));
				} 
				
				catch(IOException | URISyntaxException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		mnHelp.add(mntmSubmitIssue);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setBorder(null);
		tabbedPane.setForeground(Color.BLACK);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setFont(new Font("Courier New", Font.PLAIN, 24));
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		frmReactClient.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panelCards = new JPanel();
		panelCards.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tabbedPane.addTab("", new ImageIcon(Application.class.getResource("/com/glacialrush/react/icon.png")), panelCards, null);
		SpringLayout sl_panelCards = new SpringLayout();
		panelCards.setLayout(sl_panelCards);
		
		JPanel cartReact = new JPanel();
		sl_panelCards.putConstraint(SpringLayout.NORTH, cartReact, 16, SpringLayout.NORTH, panelCards);
		sl_panelCards.putConstraint(SpringLayout.WEST, cartReact, 16, SpringLayout.WEST, panelCards);
		sl_panelCards.putConstraint(SpringLayout.SOUTH, cartReact, 293, SpringLayout.NORTH, panelCards);
		sl_panelCards.putConstraint(SpringLayout.EAST, cartReact, 366, SpringLayout.WEST, panelCards);
		cartReact.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		cartReact.setBackground(Color.WHITE);
		panelCards.add(cartReact);
		SpringLayout sl_cartReact = new SpringLayout();
		cartReact.setLayout(sl_cartReact);
		
		JLabel lblReact = new JLabel("React");
		lblReact.setFont(new Font("Yu Gothic Light", Font.PLAIN, 64));
		sl_cartReact.putConstraint(SpringLayout.NORTH, lblReact, 10, SpringLayout.NORTH, cartReact);
		sl_cartReact.putConstraint(SpringLayout.WEST, lblReact, 10, SpringLayout.WEST, cartReact);
		cartReact.add(lblReact);
		
		labelVersion = new JLabel(Version.V);
		sl_cartReact.putConstraint(SpringLayout.NORTH, labelVersion, 45, SpringLayout.NORTH, lblReact);
		sl_cartReact.putConstraint(SpringLayout.WEST, labelVersion, 6, SpringLayout.EAST, lblReact);
		labelVersion.setFont(new Font("Yu Gothic Light", Font.PLAIN, 24));
		cartReact.add(labelVersion);
		
		JTextArea txtrHelloThere = new JTextArea();
		sl_cartReact.putConstraint(SpringLayout.NORTH, txtrHelloThere, 35, SpringLayout.SOUTH, lblReact);
		sl_cartReact.putConstraint(SpringLayout.WEST, txtrHelloThere, 10, SpringLayout.WEST, cartReact);
		sl_cartReact.putConstraint(SpringLayout.SOUTH, txtrHelloThere, -10, SpringLayout.SOUTH, cartReact);
		sl_cartReact.putConstraint(SpringLayout.EAST, txtrHelloThere, -10, SpringLayout.EAST, cartReact);
		txtrHelloThere.setWrapStyleWord(true);
		txtrHelloThere.setLineWrap(true);
		txtrHelloThere.setFont(new Font("Yu Gothic Light", Font.PLAIN, 16));
		txtrHelloThere.setText("Hello there. Welcome to the React client! Here you can monitor all of the data you need without the need of being online, and without any limitations of minecraft!");
		cartReact.add(txtrHelloThere);
		
		lblRunningVersion = new JLabel("Running Version");
		sl_cartReact.putConstraint(SpringLayout.NORTH, lblRunningVersion, 6, SpringLayout.SOUTH, lblReact);
		sl_cartReact.putConstraint(SpringLayout.WEST, lblRunningVersion, 10, SpringLayout.WEST, lblReact);
		cartReact.add(lblRunningVersion);
		
		JPanel cardGetStarted = new JPanel();
		sl_panelCards.putConstraint(SpringLayout.NORTH, cardGetStarted, 9, SpringLayout.SOUTH, cartReact);
		sl_panelCards.putConstraint(SpringLayout.WEST, cardGetStarted, 3, SpringLayout.WEST, cartReact);
		sl_panelCards.putConstraint(SpringLayout.SOUTH, cardGetStarted, 212, SpringLayout.SOUTH, cartReact);
		sl_panelCards.putConstraint(SpringLayout.EAST, cardGetStarted, 0, SpringLayout.EAST, cartReact);
		cardGetStarted.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		cardGetStarted.setBackground(Color.WHITE);
		panelCards.add(cardGetStarted);
		SpringLayout sl_cardGetStarted = new SpringLayout();
		cardGetStarted.setLayout(sl_cardGetStarted);
		
		JLabel lblGetStarted = new JLabel("Get Started");
		sl_cardGetStarted.putConstraint(SpringLayout.NORTH, lblGetStarted, 10, SpringLayout.NORTH, cardGetStarted);
		sl_cardGetStarted.putConstraint(SpringLayout.WEST, lblGetStarted, 10, SpringLayout.WEST, cardGetStarted);
		lblGetStarted.setFont(new Font("Yu Gothic Light", Font.PLAIN, 64));
		cardGetStarted.add(lblGetStarted);
		
		JLabel lblStartInThe = new JLabel("Start in the Monitoring Tab");
		sl_cardGetStarted.putConstraint(SpringLayout.NORTH, lblStartInThe, 6, SpringLayout.SOUTH, lblGetStarted);
		sl_cardGetStarted.putConstraint(SpringLayout.WEST, lblStartInThe, 10, SpringLayout.WEST, cardGetStarted);
		lblStartInThe.setFont(new Font("Yu Gothic Light", Font.PLAIN, 24));
		cardGetStarted.add(lblStartInThe);
		
		performance = new JPanel();
		tabbedPane.addTab("Monitor", new ImageIcon(Application.class.getResource("/com/glacialrush/react/tab-performance.png")), performance, null);
		performance.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		performance.add(tabbedPane_1, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		tabbedPane_1.addTab("General", null, panel_1, null);
		SpringLayout sl_panel_1 = new SpringLayout();
		panel_1.setLayout(sl_panel_1);
		
		JPanel panel_2 = new JPanel();
		sl_panel_1.putConstraint(SpringLayout.NORTH, panel_2, 10, SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.WEST, panel_2, 10, SpringLayout.WEST, panel_1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, panel_2, 311, SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.EAST, panel_2, 404, SpringLayout.WEST, panel_1);
		panel_2.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_2.setBackground(Color.WHITE);
		panel_1.add(panel_2);
		SpringLayout sl_panel_2 = new SpringLayout();
		panel_2.setLayout(sl_panel_2);
		
		JLabel lblPerformance = new JLabel("Performance");
		sl_panel_2.putConstraint(SpringLayout.NORTH, lblPerformance, 10, SpringLayout.NORTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.WEST, lblPerformance, 10, SpringLayout.WEST, panel_2);
		lblPerformance.setFont(new Font("Yu Gothic Light", Font.PLAIN, 64));
		panel_2.add(lblPerformance);
		
		lblTps = new JLabel("TPS");
		lblTps.setFont(new Font("Yu Gothic Light", Font.PLAIN, 24));
		sl_panel_2.putConstraint(SpringLayout.NORTH, lblTps, 9, SpringLayout.SOUTH, lblPerformance);
		sl_panel_2.putConstraint(SpringLayout.WEST, lblTps, 23, SpringLayout.WEST, panel_2);
		panel_2.add(lblTps);
		
		lblMem = new JLabel("MEM");
		sl_panel_2.putConstraint(SpringLayout.NORTH, lblMem, 3, SpringLayout.SOUTH, lblTps);
		sl_panel_2.putConstraint(SpringLayout.WEST, lblMem, 23, SpringLayout.WEST, panel_2);
		sl_panel_2.putConstraint(SpringLayout.EAST, lblMem, -131, SpringLayout.EAST, panel_2);
		sl_panel_2.putConstraint(SpringLayout.EAST, lblTps, 0, SpringLayout.EAST, lblMem);
		lblMem.setFont(new Font("Yu Gothic Light", Font.PLAIN, 24));
		panel_2.add(lblMem);
		
		lblMahs = new JLabel("MAHS");
		sl_panel_2.putConstraint(SpringLayout.NORTH, lblMahs, 6, SpringLayout.SOUTH, lblMem);
		sl_panel_2.putConstraint(SpringLayout.WEST, lblMahs, 57, SpringLayout.WEST, panel_2);
		sl_panel_2.putConstraint(SpringLayout.EAST, lblMahs, 34, SpringLayout.EAST, lblTps);
		lblMahs.setFont(new Font("Yu Gothic Light", Font.PLAIN, 14));
		panel_2.add(lblMahs);
		
		lblSpms_1 = new JLabel("SPMS");
		sl_panel_2.putConstraint(SpringLayout.NORTH, lblSpms_1, 6, SpringLayout.SOUTH, lblMahs);
		sl_panel_2.putConstraint(SpringLayout.WEST, lblSpms_1, 0, SpringLayout.WEST, lblMahs);
		sl_panel_2.putConstraint(SpringLayout.EAST, lblSpms_1, 34, SpringLayout.EAST, lblTps);
		lblSpms_1.setFont(new Font("Yu Gothic Light", Font.PLAIN, 14));
		panel_2.add(lblSpms_1);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.menu);
		tabbedPane_1.addTab("Graphs", null, panel, null);
		panel.setLayout(new MigLayout("", "[grow][grow]", "[][grow][][grow]"));
		
		
		JLabel lblNewLabel = new JLabel("Ticks Per Second");
		lblNewLabel.setFont(new Font("Yu Gothic Light", Font.PLAIN, 23));
		panel.add(lblNewLabel, "cell 0 0,alignx center");
		
		JLabel lblNewLabel_1 = new JLabel("Memory Usage");
		lblNewLabel_1.setFont(new Font("Yu Gothic Light", Font.PLAIN, 24));
		panel.add(lblNewLabel_1, "cell 1 0,alignx center");
		
		TPS = new JPanel();
		TPS.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		TPS.setBackground(Color.WHITE);
		panel.add(TPS, "cell 0 1,grow");
		TPS.setLayout(new BoxLayout(TPS, BoxLayout.X_AXIS));
		GTPS = new Grapher(100, 100, 20, null, new GList<Double>().qadd(1.0));
		TPS.add(GTPS);
		
		MEM = new JPanel();
		MEM.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		MEM.setBackground(Color.WHITE);
		panel.add(MEM, "cell 1 1,grow");
		MEM.setLayout(new BoxLayout(MEM, BoxLayout.X_AXIS));
		GMEM = new Grapher(100, 100, 1024, null, new GList<Double>().qadd(1.0));
		GMEM.setRcColor(true);
		MEM.add(GMEM);
		
		JLabel lblNewLabel_2 = new JLabel("Threads");
		lblNewLabel_2.setFont(new Font("Yu Gothic Light", Font.PLAIN, 24));
		panel.add(lblNewLabel_2, "cell 0 2,alignx center");
		
		JLabel lblNewLabel_3 = new JLabel("Reaction Time");
		lblNewLabel_3.setFont(new Font("Yu Gothic Light", Font.PLAIN, 24));
		panel.add(lblNewLabel_3, "cell 1 2,alignx center");
		
		THR = new JPanel();
		THR.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		THR.setBackground(Color.WHITE);
		panel.add(THR, "cell 0 3,grow");
		THR.setLayout(new BoxLayout(THR, BoxLayout.X_AXIS));
		GTHR = new Grapher(100, 100, 100, null, new GList<Double>().qadd(1.0));
		GTHR.setRcColor(true);
		THR.add(GTHR);
		
		RCT = new JPanel();
		RCT.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		RCT.setBackground(Color.WHITE);
		panel.add(RCT, "cell 1 3,grow");
		RCT.setLayout(new BoxLayout(RCT, BoxLayout.X_AXIS));
		GRCT = new Grapher(100, 100, 50000000, null, new GList<Double>().qadd(1.0));
		GRCT.setRcColor(true);
		RCT.add(GRCT);
		
		world = new JPanel();
		tabbedPane.addTab("Reports", new ImageIcon(Application.class.getResource("/com/glacialrush/react/tab-memory.png")), world, null);
		world.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][][]", "[][][][][][][][][]"));
		
		JLabel lblNotYetImplemented = new JLabel("Not Yet Implemented");
		lblNotYetImplemented.setFont(new Font("Yu Gothic Light", Font.PLAIN, 64));
		world.add(lblNotYetImplemented, "cell 7 8");
	}
	
	public static void update(JSONObject data)
	{
		if(data == null)
		{
			return;
		}
		
		try
		{
			instance.maxMem = data.getInt("mem");
			instance.serverVersion = data.getString("server");
			
			for(String i : data.getJSONObject("data").keySet())
			{
				instance.cache.put(i, data.getJSONObject("data").getDouble(i));
				
				if(i.equals("threads"))
				{
					if((int) data.getJSONObject("data").getDouble(i) > instance.maxThr)
					{
						instance.maxThr = 32 + (int) data.getJSONObject("data").getDouble(i);
					}
				}
			}
			
			instance.process();
		}
		
		catch(Exception e)
		{
			
		}
	}
	
	public void process()
	{
		GTPS.setWidth(TPS.getWidth());
		GTPS.setHeight(TPS.getHeight());
		GTPS.setData(cache.get("sample-ticks-per-second"));
		GTPS.repaint();
		
		GMEM.setWidth(MEM.getWidth());
		GMEM.setHeight(MEM.getHeight());
		GMEM.setMax(maxMem);
		GMEM.setData(cache.get("sample-memory-used"));
		GMEM.repaint();
		
		GTHR.setWidth(THR.getWidth());
		GTHR.setHeight(THR.getHeight());
		GTHR.setMax(maxThr);
		GTHR.setData(cache.get("threads"));
		GTHR.repaint();
		
		GRCT.setWidth(RCT.getWidth());
		GRCT.setHeight(RCT.getHeight());
		GRCT.setData(cache.get("sample-reaction-time"));
		GRCT.repaint();
		
		lblRunningVersion.setText(serverVersion);
		lblTps.setText(Format.f(cache.latest("sample-ticks-per-second"), 2) + " TPS (" + Format.pc(cache.latest("sample-stability")) + ")");
		lblMem.setText(Format.mem(cache.latest("sample-memory-used").longValue()) + " Used (" + Format.pc(cache.latest("sample-memory-used") / (double)maxMem) + ")");
		lblMahs.setText(Format.mem(cache.latest("sample-memory-allocations-per-second").longValue()) + "/s");
		lblSpms_1.setText(Format.f(cache.latest("sample-memory-sweep-frequency")) + " SPMS");
	}
	
	public static void popup(String title, String text)
	{
		Message.error(title, text, false);
	}
	
	public static void error(String title, String text, boolean ext)
	{
		instance.frmReactClient.dispose();
		Error.error(title, text, ext);
	}
	
	public static void unerror()
	{
		Login.login();
	}
}
