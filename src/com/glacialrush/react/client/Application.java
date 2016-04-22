package com.glacialrush.react.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.glacialrush.react.Version;
import com.glacialrush.react.json.JSONObject;
import com.glacialrush.react.util.Format;
import com.glacialrush.react.util.GList;
import com.glacialrush.react.util.Stub;

import net.miginfocom.swing.MigLayout;

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
	private JLabel lblEntities;
	private JLabel lblDrops;
	private JLabel lblChunks;
	private JLabel lblmsInterval;
	private JLabel lblkSize;
	private JLabel lblTotal;
	private JLabel lblProblemstub;
	private JLabel lblAgo;
	private JLabel lblUsername;
	private JLabel lblConnectedWith;
	
	private JTextArea txtrProblem;
	
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
	
	private static long ns = 1000000;
	private static long lns = System.nanoTime();
	private static long sz = 12345;
	private static long tot = 0;
	private static int size;
	private static String username;
	private static GList<Stub> stubs;
	
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
					stubs = new GList<Stub>();
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
		mntmAbout.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				popup("About", "React Client v" + Version.V + "\nAlpha testing, If something isnt working, or is having problems, please understand it is not finished yet.");
			}
		});
		mnReact.add(mntmAbout);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addMouseListener(new MouseAdapter()
		{
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
		mntmSupport.addMouseListener(new MouseAdapter()
		{
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
		mntmWiki.addMouseListener(new MouseAdapter()
		{
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
		mntmSubmitIssue.addMouseListener(new MouseAdapter()
		{
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
		sl_panelCards.putConstraint(SpringLayout.SOUTH, cardGetStarted, 278, SpringLayout.SOUTH, cartReact);
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
		
		JPanel panel_5 = new JPanel();
		sl_panelCards.putConstraint(SpringLayout.SOUTH, panel_5, -48, SpringLayout.SOUTH, cartReact);
		panel_5.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_5.setBackground(Color.WHITE);
		sl_panelCards.putConstraint(SpringLayout.NORTH, panel_5, 0, SpringLayout.NORTH, cartReact);
		sl_panelCards.putConstraint(SpringLayout.WEST, panel_5, 6, SpringLayout.EAST, cartReact);
		sl_panelCards.putConstraint(SpringLayout.EAST, panel_5, 330, SpringLayout.EAST, cartReact);
		panelCards.add(panel_5);
		SpringLayout sl_panel_5 = new SpringLayout();
		panel_5.setLayout(sl_panel_5);
		
		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setFont(new Font("Yu Gothic Light", Font.PLAIN, 64));
		sl_panel_5.putConstraint(SpringLayout.NORTH, lblWelcome, 10, SpringLayout.NORTH, panel_5);
		sl_panel_5.putConstraint(SpringLayout.WEST, lblWelcome, 10, SpringLayout.WEST, panel_5);
		panel_5.add(lblWelcome);
		
		lblUsername = new JLabel("Username");
		sl_panel_5.putConstraint(SpringLayout.NORTH, lblUsername, 3, SpringLayout.SOUTH, lblWelcome);
		sl_panel_5.putConstraint(SpringLayout.WEST, lblUsername, 0, SpringLayout.WEST, lblWelcome);
		lblUsername.setFont(new Font("Yu Gothic Light", Font.PLAIN, 24));
		panel_5.add(lblUsername);
		
		lblConnectedWith = new JLabel("Connected with 0 others");
		sl_panel_5.putConstraint(SpringLayout.WEST, lblConnectedWith, 0, SpringLayout.WEST, lblWelcome);
		sl_panel_5.putConstraint(SpringLayout.SOUTH, lblConnectedWith, -10, SpringLayout.SOUTH, panel_5);
		lblConnectedWith.setFont(new Font("Yu Gothic Light", Font.PLAIN, 14));
		panel_5.add(lblConnectedWith);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_6.setBackground(Color.WHITE);
		sl_panelCards.putConstraint(SpringLayout.NORTH, panel_6, 9, SpringLayout.SOUTH, panel_5);
		sl_panelCards.putConstraint(SpringLayout.WEST, panel_6, 9, SpringLayout.EAST, cartReact);
		sl_panelCards.putConstraint(SpringLayout.SOUTH, panel_6, 0, SpringLayout.SOUTH, cardGetStarted);
		sl_panelCards.putConstraint(SpringLayout.EAST, panel_6, 0, SpringLayout.EAST, panel_5);
		panelCards.add(panel_6);
		SpringLayout sl_panel_6 = new SpringLayout();
		panel_6.setLayout(sl_panel_6);
		
		JLabel lblShit = new JLabel("Status");
		sl_panel_6.putConstraint(SpringLayout.NORTH, lblShit, 10, SpringLayout.NORTH, panel_6);
		sl_panel_6.putConstraint(SpringLayout.WEST, lblShit, 10, SpringLayout.WEST, panel_6);
		lblShit.setFont(new Font("Yu Gothic Light", Font.PLAIN, 64));
		panel_6.add(lblShit);
		
		lblmsInterval = new JLabel("50ms Interval");
		sl_panel_6.putConstraint(SpringLayout.NORTH, lblmsInterval, 6, SpringLayout.SOUTH, lblShit);
		sl_panel_6.putConstraint(SpringLayout.WEST, lblmsInterval, 0, SpringLayout.WEST, lblShit);
		lblmsInterval.setFont(new Font("Yu Gothic Light", Font.PLAIN, 24));
		panel_6.add(lblmsInterval);
		
		lblkSize = new JLabel("3.2k Size");
		sl_panel_6.putConstraint(SpringLayout.NORTH, lblkSize, 6, SpringLayout.SOUTH, lblmsInterval);
		sl_panel_6.putConstraint(SpringLayout.WEST, lblkSize, 0, SpringLayout.WEST, lblShit);
		lblkSize.setFont(new Font("Yu Gothic Light", Font.PLAIN, 24));
		panel_6.add(lblkSize);
		
		lblTotal = new JLabel("Total");
		sl_panel_6.putConstraint(SpringLayout.NORTH, lblTotal, 6, SpringLayout.SOUTH, lblkSize);
		sl_panel_6.putConstraint(SpringLayout.WEST, lblTotal, 0, SpringLayout.WEST, lblShit);
		lblTotal.setFont(new Font("Yu Gothic Light", Font.PLAIN, 24));
		panel_6.add(lblTotal);
		
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
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_3.setBackground(Color.WHITE);
		sl_panel_1.putConstraint(SpringLayout.NORTH, panel_3, 6, SpringLayout.SOUTH, panel_2);
		sl_panel_1.putConstraint(SpringLayout.WEST, panel_3, 0, SpringLayout.WEST, panel_2);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, panel_3, 241, SpringLayout.SOUTH, panel_2);
		sl_panel_1.putConstraint(SpringLayout.EAST, panel_3, 0, SpringLayout.EAST, panel_2);
		panel_1.add(panel_3);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_4.setBackground(Color.WHITE);
		sl_panel_1.putConstraint(SpringLayout.NORTH, panel_4, 10, SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.WEST, panel_4, 6, SpringLayout.EAST, panel_2);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, panel_4, 0, SpringLayout.SOUTH, panel_3);
		SpringLayout sl_panel_3 = new SpringLayout();
		panel_3.setLayout(sl_panel_3);
		
		JLabel lblWorld = new JLabel("World");
		sl_panel_3.putConstraint(SpringLayout.NORTH, lblWorld, 10, SpringLayout.NORTH, panel_3);
		sl_panel_3.putConstraint(SpringLayout.WEST, lblWorld, 10, SpringLayout.WEST, panel_3);
		lblWorld.setFont(new Font("Yu Gothic Light", Font.PLAIN, 64));
		panel_3.add(lblWorld);
		
		lblEntities = new JLabel("Entities");
		sl_panel_3.putConstraint(SpringLayout.WEST, lblEntities, 0, SpringLayout.WEST, lblWorld);
		sl_panel_3.putConstraint(SpringLayout.EAST, lblEntities, 130, SpringLayout.EAST, lblWorld);
		lblEntities.setFont(new Font("Yu Gothic Light", Font.PLAIN, 24));
		panel_3.add(lblEntities);
		
		lblDrops = new JLabel("Drops");
		sl_panel_3.putConstraint(SpringLayout.NORTH, lblDrops, 175, SpringLayout.NORTH, panel_3);
		sl_panel_3.putConstraint(SpringLayout.WEST, lblDrops, 10, SpringLayout.WEST, panel_3);
		sl_panel_3.putConstraint(SpringLayout.EAST, lblDrops, -110, SpringLayout.EAST, panel_3);
		sl_panel_3.putConstraint(SpringLayout.SOUTH, lblEntities, -6, SpringLayout.NORTH, lblDrops);
		lblDrops.setFont(new Font("Yu Gothic Light", Font.PLAIN, 24));
		panel_3.add(lblDrops);
		
		lblChunks = new JLabel("Chunks");
		sl_panel_3.putConstraint(SpringLayout.NORTH, lblChunks, 45, SpringLayout.NORTH, lblWorld);
		sl_panel_3.putConstraint(SpringLayout.WEST, lblChunks, 6, SpringLayout.EAST, lblWorld);
		lblChunks.setFont(new Font("Yu Gothic Light", Font.PLAIN, 24));
		panel_3.add(lblChunks);
		sl_panel_1.putConstraint(SpringLayout.EAST, panel_4, 296, SpringLayout.EAST, panel_2);
		panel_1.add(panel_4);
		SpringLayout sl_panel_4 = new SpringLayout();
		panel_4.setLayout(sl_panel_4);
		
		JLabel lblProblems = new JLabel("Problems");
		sl_panel_4.putConstraint(SpringLayout.NORTH, lblProblems, 10, SpringLayout.NORTH, panel_4);
		sl_panel_4.putConstraint(SpringLayout.WEST, lblProblems, 10, SpringLayout.WEST, panel_4);
		lblProblems.setFont(new Font("Yu Gothic Light", Font.PLAIN, 64));
		panel_4.add(lblProblems);
		
		lblProblemstub = new JLabel("ProblemStub");
		sl_panel_4.putConstraint(SpringLayout.NORTH, lblProblemstub, 6, SpringLayout.SOUTH, lblProblems);
		sl_panel_4.putConstraint(SpringLayout.WEST, lblProblemstub, 10, SpringLayout.WEST, panel_4);
		lblProblemstub.setFont(new Font("Yu Gothic Light", Font.PLAIN, 24));
		panel_4.add(lblProblemstub);
		
		lblAgo = new JLabel("Ago");
		sl_panel_4.putConstraint(SpringLayout.NORTH, lblAgo, 4, SpringLayout.SOUTH, lblProblemstub);
		sl_panel_4.putConstraint(SpringLayout.WEST, lblAgo, 0, SpringLayout.WEST, lblProblems);
		lblAgo.setFont(new Font("Yu Gothic Light", Font.BOLD, 14));
		panel_4.add(lblAgo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sl_panel_4.putConstraint(SpringLayout.NORTH, scrollPane, 6, SpringLayout.SOUTH, lblAgo);
		sl_panel_4.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, lblProblems);
		sl_panel_4.putConstraint(SpringLayout.SOUTH, scrollPane, 336, SpringLayout.SOUTH, lblAgo);
		sl_panel_4.putConstraint(SpringLayout.EAST, scrollPane, -3, SpringLayout.EAST, lblProblems);
		panel_4.add(scrollPane);
		
		txtrProblem = new JTextArea();
		txtrProblem.setFont(new Font("Yu Gothic Light", Font.PLAIN, 18));
		txtrProblem.setEditable(false);
		txtrProblem.setLineWrap(true);
		txtrProblem.setWrapStyleWord(true);
		txtrProblem.setText("problem");
		scrollPane.setViewportView(txtrProblem);
		
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
		ns = System.nanoTime() - lns;
		lns = System.nanoTime();
		tot++;
		
		if(data == null)
		{
			return;
		}
		
		sz = data.toString().getBytes(Charset.defaultCharset()).length;
		
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
			
			stubs = new GList<Stub>();
			
			for(Object i : data.getJSONArray("issues"))
			{
				JSONObject o = (JSONObject) i;
				stubs.add(new Stub(o.getString("title"), o.getString("text"), o.getString("ago")));
			}
			
			size = data.getInt("size");
			username = data.getString("username");
			
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
		lblMem.setText(Format.mem(cache.latest("sample-memory-used").longValue()) + " Used (" + Format.pc(cache.latest("sample-memory-used") / (double) maxMem) + ")");
		lblMahs.setText(Format.mem(cache.latest("sample-memory-allocations-per-second").longValue()) + "/s");
		lblSpms_1.setText(Format.f(cache.latest("sample-memory-sweep-frequency")) + " SPMS");
		lblChunks.setText(Format.f(cache.latest("sample-chunks-loaded")) + " Chunks");
		lblEntities.setText(Format.f(cache.latest("sample-entities")) + " Entities");
		lblDrops.setText(Format.f(cache.latest("sample-drops")) + " Drops");
		lblmsInterval.setText(Format.ms(ns, 1) + " Interval");
		lblkSize.setText(Format.f(sz) + " Bytes" + " (" + Format.dps((sz / ((ns/1000000))) * 1000) + ")");
		lblTotal.setText(Format.f(tot) + " Total");
		lblUsername.setText(username);
		lblConnectedWith.setText("Connected with " + (size - 1) + " Others.");
		
		if(!stubs.isEmpty())
		{
			Stub s = stubs.get(0);
			lblProblemstub.setText(s.getTitle());
			txtrProblem.setText(s.getText());
			lblAgo.setText(s.getAgo());
			lblAgo.setVisible(true);
		}
		
		else
		{
			lblAgo.setVisible(false);
			lblProblemstub.setText("No Issues");
			txtrProblem.setText("There is no issue on the server at the moment. Once an issue happens, the latest issue will show up here.");
		}
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
