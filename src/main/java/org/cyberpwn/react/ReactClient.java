package org.cyberpwn.react;

import net.miginfocom.swing.MigLayout;
import org.apache.commons.lang3.StringUtils;
import org.cyberpwn.react.network.Network;
import org.cyberpwn.react.network.NetworkScheduler;
import org.cyberpwn.react.network.NetworkedServer;
import org.cyberpwn.react.ui.AbstractTabRenderer;
import org.cyberpwn.react.ui.JXTabbedPane;
import org.cyberpwn.react.ui.ServerTab;
import org.cyberpwn.react.util.GList;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class ReactClient {
    private static ReactClient instance;
    private final Network network;
    public JXTabbedPane tabbedPane;
    private final NetworkScheduler ns;
    private JFrame frmReactClient;
    private GList<NetworkedServer> locks;

    public ReactClient() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) {

            }
        }

        L.l("Starting React Client");
        L.l("Building Network Mesh");
        network = new Network();
        L.l("Building Frame");
        initialize();
        ns = new NetworkScheduler(network.getServers(), 50);
        ns.start();
    }

    public static void main(String[] args) {
        instance = new ReactClient();
    }

    public static void restart() {
        L.l("RESTARTING CLIENT");
        instance.ns.interrupt();
        Dimension d = instance.frmReactClient.getSize();
        Point p = instance.frmReactClient.getLocationOnScreen();
        instance.frmReactClient.setVisible(false);
        instance.frmReactClient.dispose();
        instance = new ReactClient();
        instance.frmReactClient.setSize((int) d.getWidth(), (int) d.getHeight());
        instance.frmReactClient.setLocation(p);
    }

    public static ReactClient getInstance() {
        return instance;
    }

    private void initialize() {
        locks = new GList<>();
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

        mntmAddConnection.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                addConnection();
            }
        });

        mntmExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                exit();
            }
        });

        mntmAbout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                new AboutReact();
            }
        });

        L.l("Packing Frame");

        frmReactClient = new JFrame();
        frmReactClient.setTitle("React Client");
        frmReactClient.setIconImage(Toolkit.getDefaultToolkit().getImage(ReactClient.class.getResource("/icon.png")));
        frmReactClient.setBounds(100, 100, 813, 610);
        frmReactClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmReactClient.setJMenuBar(menuBar);
        frmReactClient.getContentPane().setLayout(new BorderLayout(0, 0));

        if (network.getServers().isEmpty()) {
            L.w("No networks found");
            L.l("Launching tutorial page");
            showTutorial();
        } else {
            L.l("Building all servers");
            tabbedPane = new JXTabbedPane(JTabbedPane.LEFT);
            AbstractTabRenderer renderer = (AbstractTabRenderer) tabbedPane.getTabRenderer();
            renderer.setPrototypeText("This text is a prototype");
            renderer.setPrototypeIcon(new ImageIcon(ReactClient.class.getResource("/server-mini.png")));
            renderer.setHorizontalTextAlignment(SwingConstants.LEADING);
            tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
            tabbedPane.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
            frmReactClient.getContentPane().add(tabbedPane, BorderLayout.CENTER);

            L.l("Compiling servers");

            for (NetworkedServer i : network.getServers()) {
                L.l("Building (" + i.getName() + ") " + i.getAddress() + ":" + i.getPort() + " @ " + i.getUsername() + "/" + StringUtils.repeat('*', i.getPassword().length()));
                i.setTab(new ServerTab(frmReactClient, i, tabbedPane));
            }
        }

        frmReactClient.setVisible(true);
        L.l("CLIENT RUNNING");
    }

    public void addConnection() {
        AddConnection.addConnection();
    }

    public void showTutorial() {
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

        JLabel lblLabel = new JLabel(new ImageIcon(ReactClient.class.getResource("/server-low.png")));
        panel_1.add(lblLabel, BorderLayout.CENTER);

        JLabel lblLabel2 = new JLabel(new ImageIcon(ReactClient.class.getResource("/icon.png")));
        panel_1.add(lblLabel2, BorderLayout.WEST);

        JLabel lblNewLabel_1 = new JLabel(new ImageIcon(ReactClient.class.getResource("/icon.png")));
        panel_1.add(lblNewLabel_1, BorderLayout.EAST);
        L.l("TUTORIAL LAUNCHED");
    }

    public void addTab(String name) {

    }

    public void exit() {
        L.l("INTERRUPTING NETWORK MAPPING");
        ns.interrupt();
        L.l("SHUTTING DOWN");
        System.exit(0);
    }

    public Network getNetwork() {
        return network;
    }

    public void validateConnectionAdd(String name, String address, int port, String username, String password) {
        getNetwork().addConnection(name, address, port, username, password);
        L.l("Connection Added: (" + name + ") " + address + ":" + port + " @ " + username + "/" + StringUtils.repeat('*', password.length()));
        restart();
    }

    public void deleteConnection(NetworkedServer ns) {
        L.l("DELETING CONNECTION " + "(" + ns.getName() + ") " + ns.getAddress() + ":" + ns.getPort() + " @ " + ns.getUsername() + "/" + StringUtils.repeat('*', ns.getPassword().length()));
        releaseConnection(ns);
        network.deleteServer(ns);

        try {
            network.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

        restart();
    }

    public void editConnection(NetworkedServer ns) {
        EditConnection.editConnection(ns);
    }

    public void validateConnectionEdit(String name, String address, int port, String username, String password, NetworkedServer ns) {
        network.rename(name, ns);
        ns.setPort(port);
        ns.setAddress(address);
        ns.setPassword(password);
        ns.setUsername(username);

        try {
            network.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        restart();
    }

    public boolean isLocked(NetworkedServer ns) {
        return locks.contains(ns);
    }

    public void lockConnection(NetworkedServer ns2) {
        if (!locks.contains(ns2)) {
            L.l("Connection locked: " + ns2.getName());

            locks.add(ns2);
        }
    }

    public void releaseConnection(NetworkedServer ns2) {
        L.l("Connection unlocked: " + ns2.getName());
        locks.remove(ns2);
        ns2.reset();
    }
}
