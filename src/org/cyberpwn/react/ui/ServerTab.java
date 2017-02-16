package org.cyberpwn.react.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.cyberpwn.react.ReactClient;
import org.cyberpwn.react.network.NetworkedServer;
import org.cyberpwn.react.network.RequestCommand;
import org.cyberpwn.react.network.RequestCommandCallback;
import org.cyberpwn.react.util.F;
import org.cyberpwn.react.util.GList;
import org.cyberpwn.react.util.GMap;
import net.miginfocom.swing.MigLayout;

public class ServerTab implements ListSelectionListener, ActionListener
{
	private NetworkedServer ns;
	
	private JLabel lblServerName;
	private JLabel lblTps;
	private JLabel lblMbUsed;
	private JLabel lblGcminute;
	private JLabel lblMahs;
	private JLabel lblUsingSpigot;
	private JLabel lblOnline;
	private JLabel lblmsPing;
	private JLabel label;
	private JLabel label2;
	private JLabel lblNewLabel;
	private JCheckBox chckbxNewCheckBox;
	
	private JScrollBar vertical;
	private JTextArea textArea;
	private JButton btnNewButton;
	private JList<String> actionSet;
	private DefaultListModel<String> actionList;
	
	private JXTabbedPane tabbedPane;
	private JPanel panel;
	
	private Grapher TPS;
	private GList<Double> DTPS;
	
	private Grapher MEM;
	private GList<Double> DMEM;
	
	private Grapher GC;
	private GList<Double> DGC;
	
	private Grapher MAH;
	private GList<Double> DMAH;
	private JTextField textField;
	
	public ServerTab(JFrame frame, NetworkedServer server, JXTabbedPane tp)
	{
		tabbedPane = tp;
		ns = server;
		DTPS = new GList<Double>().qadd(20.0);
		DMEM = new GList<Double>().qadd(128.0);
		DGC = new GList<Double>().qadd(1.0);
		DMAH = new GList<Double>().qadd(9.0);
		
		panel = new JPanel();
		tabbedPane.addTab(ns.getName(), new ImageIcon(ReactClient.class.getResource("/org/cyberpwn/react/ui/server-mini.png")), panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
		panel.add(tabbedPane_1, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		tabbedPane_1.addTab("General", null, panel_1, null);
		panel_1.setLayout(new MigLayout("", "[grow][][][grow][][][][][][][][][][grow]", "[grow][][][][grow][grow][][][]"));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_5.setBackground(Color.WHITE);
		panel_1.add(panel_5, "cell 0 0 13 4,grow");
		panel_5.setLayout(new MigLayout("", "[][][][]", "[][][][][][][]"));
		
		lblServerName = new JLabel("Connecting...");
		lblServerName.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		panel_5.add(lblServerName, "cell 0 0");
		
		lblUsingSpigot = new JLabel("Connecting...");
		lblUsingSpigot.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panel_5.add(lblUsingSpigot, "cell 0 1");
		
		lblOnline = new JLabel("");
		lblOnline.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panel_5.add(lblOnline, "cell 0 2");
		
		lblmsPing = new JLabel("");
		lblmsPing.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panel_5.add(lblmsPing, "cell 0 3");
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_8.setBackground(Color.WHITE);
		panel_1.add(panel_8, "cell 13 0 1 4,grow");
		panel_8.setLayout(new MigLayout("", "[]", "[][][][][][]"));
		
		JLabel lblConnection = new JLabel("Connection");
		lblConnection.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		panel_8.add(lblConnection, "cell 0 0");
		
		JButton btnNewButton_1 = new JButton("Edit");
		btnNewButton_1.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				ReactClient.getInstance().editConnection(ns);
			}
		});
		btnNewButton_1.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panel_8.add(btnNewButton_1, "cell 0 2");
		
		JButton btnNewButton_2 = new JButton("Delete");
		btnNewButton_2.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				ReactClient.getInstance().deleteConnection(ns);
			}
		});
		btnNewButton_2.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panel_8.add(btnNewButton_2, "cell 0 2");
		
		label = new JLabel(new ImageIcon(ReactClient.class.getResource("/org/cyberpwn/react/ui/server-mini-red.png")));
		panel_8.add(label, "cell 0 3");
		label2 = new JLabel("Connecting...");
		label2.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panel_8.add(label2, "cell 0 3");
		
		JPanel panel_2 = new JPanel();
		tabbedPane_1.addTab("Performance", null, panel_2, null);
		panel_2.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane_2.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
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
		
		lblTps = new JLabel("Connecting...");
		lblTps.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panel_7.add(lblTps, "cell 1 0");
		
		TPS = new Grapher(20, Color.CYAN, new GList<Double>().qadd(1.0));
		TPS.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		TPS.setBackground(Color.DARK_GRAY);
		panel_7.add(TPS, "cell 0 1 2 2,grow");
		
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
		
		lblMbUsed = new JLabel("Connecting...");
		lblMbUsed.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panel_14.add(lblMbUsed, "cell 1 0");
		
		MEM = new Grapher(400, Color.CYAN, new GList<Double>().qadd(1.0));
		MEM.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		MEM.setBackground(Color.DARK_GRAY);
		panel_14.add(MEM, "cell 0 1 2 1,grow");
		
		JPanel panel_16 = new JPanel();
		panel_16.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_16.setBackground(Color.WHITE);
		panel_13.add(panel_16, "cell 0 1,grow");
		panel_16.setLayout(new MigLayout("", "[grow][]", "[][grow]"));
		
		JLabel lblGarbage = new JLabel("Garbage");
		lblGarbage.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		panel_16.add(lblGarbage, "cell 0 0");
		
		lblGcminute = new JLabel("Connecting...");
		lblGcminute.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panel_16.add(lblGcminute, "cell 1 0");
		
		GC = new Grapher(1200, Color.CYAN, new GList<Double>().qadd(1.0));
		GC.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GC.setBackground(Color.DARK_GRAY);
		panel_16.add(GC, "cell 0 1 2 1,grow");
		
		JPanel panel_15 = new JPanel();
		panel_15.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_15.setBackground(Color.WHITE);
		panel_13.add(panel_15, "cell 1 1,grow");
		panel_15.setLayout(new MigLayout("", "[grow][]", "[][grow]"));
		
		JLabel lblState = new JLabel("Volatility");
		lblState.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		panel_15.add(lblState, "cell 0 0");
		
		lblMahs = new JLabel("Connecting...");
		lblMahs.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panel_15.add(lblMahs, "cell 1 0");
		
		MAH = new Grapher(1, Color.CYAN, new GList<Double>().qadd(1.0));
		MAH.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		MAH.setBackground(Color.DARK_GRAY);
		panel_15.add(MAH, "cell 0 1 2 1,grow");
		
		JPanel panel_4 = new JPanel();
		tabbedPane_1.addTab("Actions", null, panel_4, null);
		panel_4.setLayout(new MigLayout("", "[grow]", "[grow][grow]"));
		
		JPanel panel_12 = new JPanel();
		panel_12.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_12.setBackground(Color.WHITE);
		panel_4.add(panel_12, "cell 0 0 1 2,grow");
		panel_12.setLayout(new MigLayout("", "[grow][]", "[][grow][]"));
		
		JLabel lblSelectAnAction = new JLabel("Actions");
		lblSelectAnAction.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		panel_12.add(lblSelectAnAction, "cell 0 0");
		
		actionList = new DefaultListModel<String>();
		
		actionSet = new JList<String>(actionList);
		actionSet.setBackground(Color.WHITE);
		actionSet.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
		actionSet.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		actionSet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		actionSet.addListSelectionListener((ListSelectionListener) this);
		
		JScrollPane scrollActions = new JScrollPane(actionSet);
		panel_12.add(scrollActions, "cell 0 1 2 1,grow");
		
		lblNewLabel = new JLabel("Not Yet Implemented");
		lblNewLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panel_12.add(lblNewLabel, "cell 0 2");
		
		btnNewButton = new JButton("Perform Action");
		btnNewButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
		btnNewButton.setEnabled(false);
		btnNewButton.addActionListener(this);
		panel_12.add(btnNewButton, "cell 1 2");
		
		JPanel panel_3 = new JPanel();
		tabbedPane_1.addTab("Console", null, panel_3, null);
		panel_3.setLayout(new MigLayout("", "[grow][grow]", "[grow][grow]"));
		
		JPanel panel_18 = new JPanel();
		panel_18.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_18.setBackground(Color.WHITE);
		panel_3.add(panel_18, "cell 0 0 2 2,grow");
		panel_18.setLayout(new MigLayout("", "[grow]", "[grow][grow]"));
		
		textArea = new JTextArea();
		textArea.setBackground(Color.DARK_GRAY);
		textArea.setForeground(Color.WHITE);
		textArea.setFont(new Font("Lucida Console", Font.PLAIN, 16));
		textArea.setEditable(false);
		
		JScrollPane scrollConsole = new JScrollPane(textArea);
		panel_18.add(scrollConsole, "cell 0 0,grow");
		
		textField = new JTextField();
		textField.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		panel_18.add(textField, "flowx,cell 0 1,growx");
		textField.setColumns(10);
		textField.setDisabledTextColor(Color.RED);
		textField.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				
			}
			
			@Override
			public void keyPressed(KeyEvent e)
			{
				
			}
			
			@Override
			public void keyReleased(KeyEvent e)
			{
				if(e.getKeyCode() == 10)
				{
					String command = textField.getText();
					textField.setEnabled(false);
					
					new RequestCommand(ns, new RequestCommandCallback()
					{
						@Override
						public void run()
						{
							textField.setEnabled(true);
							textField.setText("");
							textField.requestFocusInWindow();
						}
					}, command).start();
				}
			}
		});
		
		chckbxNewCheckBox = new JCheckBox("Follow Log");
		chckbxNewCheckBox.setBackground(Color.WHITE);
		chckbxNewCheckBox.setSelected(true);
		chckbxNewCheckBox.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		panel_18.add(chckbxNewCheckBox, "cell 0 1");
		vertical = scrollConsole.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
	}
	
	public void pushStartedActions()
	{
		label2.setText("Getting Reactors...");
	}
	
	public void push(GList<String> actions)
	{
		actionList.clear();
		
		for(String i : actions)
		{
			actionList.addElement(i);
		}
	}
	
	public void push(GMap<String, Double> sample, String console)
	{
		if(!textArea.getText().equals(console))
		{
			textArea.setText(console);
			
			if(chckbxNewCheckBox.isSelected())
			{
				textArea.setCaretPosition(textArea.getDocument().getLength());
			}
		}
		
		lblServerName.setText(ns.getName());
		lblUsingSpigot.setText(ns.getVersion());
		lblTps.setText(F.f(sample.get("tps"), 2) + " TPS (" + F.pc(sample.get("stability"), 0) + " Stable)");
		lblGcminute.setText(F.f(sample.get("spms")) + " GC/Minute");
		lblMahs.setText(F.f(sample.get("mah/s")) + " MAH/s");
		lblMbUsed.setText(F.mem(sample.get("mem").longValue()) + " Used");
		lblOnline.setText(F.f(sample.get("plr")) + " Players Online");
		lblmsPing.setText(F.f(sample.get("plg")) + " Loaded Plugins");
		label.setIcon(new ImageIcon(ReactClient.class.getResource("/org/cyberpwn/react/ui/server-mini.png")));
		label2.setText("Connected");
		
		if(MAH.getMax() < sample.get("mah/s"))
		{
			MAH.setMax(sample.get("mah/s").intValue());
		}
		
		DTPS.add(sample.get("tps"));
		
		if(DTPS.size() > TPS.getWidth())
		{
			while(DTPS.size() > TPS.getWidth())
			{
				DTPS.remove(0);
			}
		}
		
		TPS.setData(DTPS);
		TPS.repaint();
		
		DMEM.add(sample.get("mem"));
		
		if(DMEM.size() > MEM.getWidth())
		{
			while(DMEM.size() > MEM.getWidth())
			{
				DMEM.remove(0);
			}
		}
		
		MEM.setMax(sample.get("memory-max").intValue());
		MEM.setData(DMEM);
		MEM.repaint();
		
		DGC.add(sample.get("spms"));
		
		if(DGC.size() > GC.getWidth())
		{
			while(DGC.size() > GC.getWidth())
			{
				DGC.remove(0);
			}
		}
		
		GC.setData(DGC);
		GC.repaint();
		
		DMAH.add(sample.get("mah/s"));
		
		if(DMAH.size() > MAH.getWidth())
		{
			while(DMAH.size() > MAH.getWidth())
			{
				DMAH.remove(0);
			}
		}
		
		MAH.setData(DMAH);
		MAH.repaint();
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		lblNewLabel.setText(actionSet.getSelectedValue());
		btnNewButton.setEnabled(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String action = actionSet.getSelectedValue();
		actionSet.clearSelection();
		btnNewButton.setEnabled(false);
		System.out.println("Perform Action: " + action);
		System.out.println("Sending Action Packet...");
		ns.act(action, lblNewLabel);
	}
}
