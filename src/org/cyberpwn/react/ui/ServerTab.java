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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.commons.lang3.StringUtils;
import org.cyberpwn.react.L;
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
	private JComboBox<String> comboBox;
	
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
	private GList<Double> DCUSTOM;
	private JTextField textField;
	private String lastConsole;
	private String lastLog;
	private int index;
	private boolean dead;
	private JButton btnNewButton_1;
	private JLabel lblGraph;
	private JLabel lblConnecting;
	private Grapher CUSTOM;
	private JComboBox<String> comboBox_1;
	
	public ServerTab(JFrame frame, NetworkedServer server, JXTabbedPane tp)
	{
		dead = false;
		tabbedPane = tp;
		ns = server;
		DTPS = new GList<Double>().qadd(20.0);
		DMEM = new GList<Double>().qadd(128.0);
		DGC = new GList<Double>().qadd(1.0);
		DMAH = new GList<Double>().qadd(200.0);
		DCUSTOM = new GList<Double>().qadd(1.0);
		
		panel = new JPanel();
		
		tabbedPane.addTab(ns.getName(), new ImageIcon(ReactClient.class.getResource("/org/cyberpwn/react/ui/server-mini.png")), panel, null);
		index = tabbedPane.getTabCount() - 1;
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
		
		btnNewButton_1 = new JButton("Edit");
		btnNewButton_1.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				if(dead)
				{
					ReactClient.restart();
				}
				
				else
				{
					ReactClient.getInstance().editConnection(ns);
				}
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
		label2 = new JLabel("Starting");
		label2.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		panel_8.add(label2, "cell 0 3");
		
		JPanel panel_2 = new JPanel();
		tabbedPane_1.addTab("Performance", null, panel_2, null);
		
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane_2.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
		
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
		
		TPS = new Grapher(20, Color.GREEN, new GList<Double>().qadd(1.0));
		TPS.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		TPS.setBackground(Color.DARK_GRAY);
		panel_7.add(TPS, "cell 0 1 2 2,grow");
		
		JPanel panel_13 = new JPanel();
		tabbedPane_2.addTab("Memory", null, panel_13, null);
		
		JPanel panel_14 = new JPanel();
		panel_14.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_14.setBackground(Color.WHITE);
		
		JLabel lblMemory = new JLabel("Memory");
		lblMemory.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		
		lblMbUsed = new JLabel("Connecting...");
		lblMbUsed.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		
		MEM = new Grapher(400, Color.CYAN, new GList<Double>().qadd(1.0), true);
		MEM.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		MEM.setBackground(Color.DARK_GRAY);
		
		JPanel panel_16 = new JPanel();
		panel_16.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_16.setBackground(Color.WHITE);
		
		JLabel lblGarbage = new JLabel("Garbage");
		lblGarbage.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		
		lblGcminute = new JLabel("Connecting...");
		lblGcminute.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		
		GC = new Grapher(1, Color.RED, new GList<Double>().qadd(1.0), true);
		GC.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GC.setBackground(Color.DARK_GRAY);
		
		JPanel panel_15 = new JPanel();
		panel_15.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_15.setBackground(Color.WHITE);
		
		JLabel lblState = new JLabel("Volatility");
		lblState.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		
		lblMahs = new JLabel("Connecting...");
		lblMahs.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		
		MAH = new Grapher(1, Color.ORANGE, new GList<Double>().qadd(1.0), true);
		MAH.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		MAH.setBackground(Color.DARK_GRAY);
		GroupLayout gl_panel_14 = new GroupLayout(panel_14);
		gl_panel_14.setHorizontalGroup(gl_panel_14.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_14.createSequentialGroup().addGap(7).addGroup(gl_panel_14.createParallelGroup(Alignment.LEADING).addComponent(MEM, GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE).addGroup(gl_panel_14.createSequentialGroup().addComponent(lblMemory).addPreferredGap(ComponentPlacement.RELATED, 322, Short.MAX_VALUE).addComponent(lblMbUsed))).addContainerGap()));
		gl_panel_14.setVerticalGroup(gl_panel_14.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_14.createSequentialGroup().addGap(7).addGroup(gl_panel_14.createParallelGroup(Alignment.BASELINE).addComponent(lblMemory).addComponent(lblMbUsed)).addGap(4).addComponent(MEM, GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE).addGap(7)));
		panel_14.setLayout(gl_panel_14);
		GroupLayout gl_panel_15 = new GroupLayout(panel_15);
		gl_panel_15.setHorizontalGroup(gl_panel_15.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_15.createSequentialGroup().addGap(7).addGroup(gl_panel_15.createParallelGroup(Alignment.LEADING).addComponent(MAH, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(gl_panel_15.createSequentialGroup().addComponent(lblState).addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE).addComponent(lblMahs))).addContainerGap()));
		gl_panel_15.setVerticalGroup(gl_panel_15.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_15.createSequentialGroup().addGap(7).addGroup(gl_panel_15.createParallelGroup(Alignment.BASELINE).addComponent(lblState).addComponent(lblMahs)).addGap(4).addComponent(MAH, GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE).addGap(7)));
		panel_15.setLayout(gl_panel_15);
		GroupLayout gl_panel_16 = new GroupLayout(panel_16);
		gl_panel_16.setHorizontalGroup(gl_panel_16.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_16.createSequentialGroup().addGap(7).addGroup(gl_panel_16.createParallelGroup(Alignment.LEADING).addComponent(GC, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(gl_panel_16.createSequentialGroup().addComponent(lblGarbage).addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE).addComponent(lblGcminute))).addContainerGap()));
		gl_panel_16.setVerticalGroup(gl_panel_16.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_16.createSequentialGroup().addGap(7).addGroup(gl_panel_16.createParallelGroup(Alignment.BASELINE).addComponent(lblGarbage).addComponent(lblGcminute)).addGap(4).addComponent(GC, GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE).addGap(7)));
		panel_16.setLayout(gl_panel_16);
		GroupLayout gl_panel_13 = new GroupLayout(panel_13);
		gl_panel_13.setHorizontalGroup(gl_panel_13.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_13.createSequentialGroup().addGap(7).addGroup(gl_panel_13.createParallelGroup(Alignment.TRAILING).addComponent(panel_14, GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE).addGroup(gl_panel_13.createSequentialGroup().addComponent(panel_16, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(panel_15, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))).addContainerGap()));
		gl_panel_13.setVerticalGroup(gl_panel_13.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_13.createSequentialGroup().addGap(7).addComponent(panel_14, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE).addGap(4).addGroup(gl_panel_13.createParallelGroup(Alignment.LEADING).addComponent(panel_15, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE).addComponent(panel_16, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)).addGap(13)));
		panel_13.setLayout(gl_panel_13);
		
		JPanel panel_9 = new JPanel();
		tabbedPane_2.addTab("Other", null, panel_9, null);
		
		JPanel panel_10 = new JPanel();
		panel_10.setBackground(Color.WHITE);
		panel_10.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		GroupLayout gl_panel_9 = new GroupLayout(panel_9);
		gl_panel_9.setHorizontalGroup(gl_panel_9.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_9.createSequentialGroup().addContainerGap().addComponent(panel_10, GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE).addContainerGap()));
		gl_panel_9.setVerticalGroup(gl_panel_9.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_9.createSequentialGroup().addContainerGap().addComponent(panel_10, GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE).addContainerGap()));
		
		lblGraph = new JLabel("Graph");
		lblGraph.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		
		CUSTOM = new Grapher(1, Color.MAGENTA, new GList<Double>().qadd(1.0), true);
		CUSTOM.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		CUSTOM.setBackground(Color.DARK_GRAY);
		
		lblConnecting = new JLabel("Connecting...");
		lblConnecting.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		
		comboBox_1 = new JComboBox<String>();
		comboBox_1.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		GroupLayout gl_panel_10 = new GroupLayout(panel_10);
		gl_panel_10.setHorizontalGroup(gl_panel_10.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, gl_panel_10.createSequentialGroup().addContainerGap().addGroup(gl_panel_10.createParallelGroup(Alignment.TRAILING).addComponent(CUSTOM, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE).addGroup(gl_panel_10.createSequentialGroup().addComponent(lblGraph).addPreferredGap(ComponentPlacement.RELATED, 324, Short.MAX_VALUE).addComponent(lblConnecting)).addComponent(comboBox_1, Alignment.LEADING, 0, 514, Short.MAX_VALUE)).addContainerGap()));
		gl_panel_10.setVerticalGroup(gl_panel_10.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_10.createSequentialGroup().addContainerGap().addGroup(gl_panel_10.createParallelGroup(Alignment.BASELINE).addComponent(lblConnecting).addComponent(lblGraph)).addPreferredGap(ComponentPlacement.RELATED).addComponent(CUSTOM, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addContainerGap()));
		panel_10.setLayout(gl_panel_10);
		panel_9.setLayout(gl_panel_9);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_2.createSequentialGroup().addGap(7).addComponent(tabbedPane_2, GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE).addContainerGap()));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_2.createSequentialGroup().addGap(6).addComponent(tabbedPane_2, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE).addContainerGap()));
		panel_2.setLayout(gl_panel_2);
		
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
		actionSet.setBackground(Color.LIGHT_GRAY);
		actionSet.setFont(new Font("Segoe UI Light", Font.PLAIN, 24));
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
		textArea.setText("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
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
		
		comboBox = new JComboBox<String>();
		comboBox.insertItemAt("Remote", 0);
		comboBox.insertItemAt("Local", 1);
		comboBox.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		comboBox.setSelectedIndex(0);
		comboBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(comboBox.getSelectedIndex() == 0)
				{
					if(!textArea.getText().equals(lastConsole))
					{
						textArea.setText(lastConsole);
						
						if(chckbxNewCheckBox.isSelected())
						{
							textArea.setCaretPosition(textArea.getDocument().getLength());
						}
					}
				}
				
				else
				{
					if(!textArea.getText().equals(lastLog))
					{
						textArea.setText(lastLog);
						
						if(chckbxNewCheckBox.isSelected())
						{
							textArea.setCaretPosition(textArea.getDocument().getLength());
						}
					}
				}
			}
		});
		
		panel_18.add(comboBox, "cell 0 1");
		
		chckbxNewCheckBox = new JCheckBox("Follow Log");
		chckbxNewCheckBox.setBackground(Color.WHITE);
		chckbxNewCheckBox.setSelected(true);
		chckbxNewCheckBox.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		panel_18.add(chckbxNewCheckBox, "cell 0 1");
		vertical = scrollConsole.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
	}
	
	public void die()
	{
		dead = true;
		btnNewButton_1.setText("Reconnect");
	}
	
	public void pushStartedActions()
	{
		
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
		sample.put("rct", sample.get("rct") / 1000000.0);
		lastConsole = StringUtils.repeat("\n", 40) + console;
		lastLog = StringUtils.repeat("\n", 40) + L.log;
		
		if(comboBox.getSelectedIndex() == 0)
		{
			if(!textArea.getText().equals(lastConsole))
			{
				textArea.setText(lastConsole);
				
				if(chckbxNewCheckBox.isSelected())
				{
					textArea.setCaretPosition(textArea.getDocument().getLength());
				}
			}
		}
		
		else
		{
			if(!textArea.getText().equals(lastLog))
			{
				textArea.setText(lastLog);
				
				if(chckbxNewCheckBox.isSelected())
				{
					textArea.setCaretPosition(textArea.getDocument().getLength());
				}
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
		DGC.add(sample.get("spms"));
		DGC.add(sample.get("spms"));
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
		DMAH.add(sample.get("mah/s"));
		DMAH.add(sample.get("mah/s"));
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
		
		if(comboBox_1.getItemCount() == 0)
		{
			for(String i : sample.k())
			{
				comboBox_1.addItem(i);
			}
		}
		
		comboBox_1.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				DCUSTOM.clear();
			}
		});
		
		String d = (String) comboBox_1.getSelectedItem();
		DCUSTOM.add(sample.get(d));
		if(DCUSTOM.size() > CUSTOM.getWidth())
		{
			while(DCUSTOM.size() > CUSTOM.getWidth())
			{
				DCUSTOM.remove(0);
			}
		}
		
		CUSTOM.setData(DCUSTOM);
		CUSTOM.repaint();
		lblGraph.setText(d);
		lblConnecting.setText(F.f(sample.get(d)) + " " + d);
	}
	
	public void status(boolean red, String msg)
	{
		String resource = (red ? "/org/cyberpwn/react/ui/server-mini-red.png" : "/org/cyberpwn/react/ui/server-mini.png");
		tabbedPane.setIconAt(index, new ImageIcon(ReactClient.class.getResource(resource)));
		label.setIcon(new ImageIcon(ReactClient.class.getResource(resource)));
		label2.setText(msg);
		tabbedPane.revalidate();
		tabbedPane.repaint();
		panel.revalidate();
		panel.repaint();
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
		L.l("Perform Action: " + action);
		L.l("Sending Action Packet...");
		ns.act(action, lblNewLabel);
	}
	
	public void push(GMap<String, Double> sample)
	{
		push(sample, lastConsole);
	}
}
