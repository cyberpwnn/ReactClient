package org.cyberpwn.react;

import org.cyberpwn.react.network.NetworkedServer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ConnectionFailure extends JDialog {
    private static final long serialVersionUID = 1L;

    /**
     * Create the dialog.
     */
    public ConnectionFailure(final NetworkedServer ns) {
        setTitle("Problem Connecting to " + ns.getName());
        setResizable(false);
        ReactClient.getInstance().lockConnection(ns);
        setType(Type.POPUP);
        setAlwaysOnTop(true);
        setIconImage(Toolkit.getDefaultToolkit().getImage(ConnectionFailure.class.getResource("/server-mini-red.png")));
        setBounds(100, 100, 394, 296);
        getContentPane().setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        SpringLayout sl_contentPanel = new SpringLayout();
        contentPanel.setLayout(sl_contentPanel);

        JLabel lblFailedToConnect = new JLabel("Failed to Connect");
        lblFailedToConnect.setFont(new Font("Segoe UI Light", Font.PLAIN, 24));
        sl_contentPanel.putConstraint(SpringLayout.NORTH, lblFailedToConnect, 10, SpringLayout.NORTH, contentPanel);
        sl_contentPanel.putConstraint(SpringLayout.WEST, lblFailedToConnect, 10, SpringLayout.WEST, contentPanel);
        contentPanel.add(lblFailedToConnect);

        JLabel lblLocalhost = new JLabel(ns.getUsername() + "@" + ns.getAddress() + ":" + ns.getPort());
        sl_contentPanel.putConstraint(SpringLayout.NORTH, lblLocalhost, 6, SpringLayout.SOUTH, lblFailedToConnect);
        sl_contentPanel.putConstraint(SpringLayout.WEST, lblLocalhost, 0, SpringLayout.WEST, lblFailedToConnect);
        lblLocalhost.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
        contentPanel.add(lblLocalhost);

        JTextPane txtpnSdfsdfsdf = new JTextPane();
        txtpnSdfsdfsdf.setForeground(Color.GRAY);
        sl_contentPanel.putConstraint(SpringLayout.NORTH, txtpnSdfsdfsdf, 6, SpringLayout.SOUTH, lblLocalhost);
        sl_contentPanel.putConstraint(SpringLayout.WEST, txtpnSdfsdfsdf, 10, SpringLayout.WEST, contentPanel);
        sl_contentPanel.putConstraint(SpringLayout.SOUTH, txtpnSdfsdfsdf, -5, SpringLayout.SOUTH, contentPanel);
        sl_contentPanel.putConstraint(SpringLayout.EAST, txtpnSdfsdfsdf, -15, SpringLayout.EAST, contentPanel);
        txtpnSdfsdfsdf.setEditable(false);
        txtpnSdfsdfsdf.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
        txtpnSdfsdfsdf.setText("Please ensure that your username, password, address and port are correct. Also make sure that the react remote server is running & you are connected to the internet. You also may want to check documentation for other debugging procedures.");
        contentPanel.add(txtpnSdfsdfsdf);
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setBackground(Color.WHITE);
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);

            JButton buttonEdit = new JButton("Edit");
            buttonEdit.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
            buttonEdit.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if(SwingUtilities.isLeftMouseButton(e)) {
                        setVisible(false);
                        dispose();
                        ReactClient.getInstance().editConnection(ns);
                    }
                }
            });
            buttonPane.add(buttonEdit);

            JButton btnRetry = new JButton("Retry");
            btnRetry.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
            btnRetry.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if(SwingUtilities.isLeftMouseButton(e)) {
                        setVisible(false);
                        dispose();
                        ReactClient.getInstance().releaseConnection(ns);
                    }
                }
            });
            buttonPane.add(btnRetry);

            JButton cancelButton = new JButton("Cancel");
            cancelButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
            cancelButton.setActionCommand("Cancel");
            buttonPane.add(cancelButton);
            cancelButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if(SwingUtilities.isLeftMouseButton(e)) {
                        setVisible(false);
                        dispose();
                        ns.getTab().die();
                    }
                }
            });

        }
    }

    public static void failed(NetworkedServer ns) {
        try {
            ConnectionFailure dialog = new ConnectionFailure(ns);
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
