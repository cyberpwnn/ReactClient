package org.cyberpwn.react;

import net.miginfocom.swing.MigLayout;
import org.cyberpwn.react.ui.PortJTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddConnection extends JDialog {
    private static final long serialVersionUID = 801014377635942783L;
    private final JPanel contentPanel = new JPanel();
    private final JTextField txtLocalhost;
    private final JTextField textField_1;
    private final JTextField txtCyberpwn;
    private final JTextField txtReactisawesome;
    private final JTextField txtFancyServer;

    public AddConnection() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(AddConnection.class.getResource("/server-low.png")));
        setTitle("Add a Connection");
        setBounds(100, 100, 450, 397);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new MigLayout("", "[grow][grow]", "[][][][][][][][]"));
        {
            JLabel lblName = new JLabel("Name");
            lblName.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
            contentPanel.add(lblName, "cell 0 0");
        }
        {
            txtFancyServer = new JTextField();
            txtFancyServer.setText("Fancy Server Name");
            txtFancyServer.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
            txtFancyServer.setColumns(10);
            contentPanel.add(txtFancyServer, "cell 0 1,growx");
        }
        {
            JLabel lblAddress = new JLabel("Address");
            lblAddress.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
            contentPanel.add(lblAddress, "cell 0 2");
        }
        {
            JLabel lblPort = new JLabel("Port");
            lblPort.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
            contentPanel.add(lblPort, "cell 1 2");
        }
        {
            txtLocalhost = new JTextField();
            txtLocalhost.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
            txtLocalhost.setText("localhost");
            contentPanel.add(txtLocalhost, "cell 0 3,growx");
            txtLocalhost.setColumns(10);
        }
        {
            textField_1 = new PortJTextField();
            textField_1.setText("8128");
            textField_1.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
            textField_1.setColumns(10);
            contentPanel.add(textField_1, "cell 1 3,alignx left");
        }
        {
            JLabel lblUsername = new JLabel("Username");
            lblUsername.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
            contentPanel.add(lblUsername, "cell 0 4");
        }
        {
            txtCyberpwn = new JTextField();
            txtCyberpwn.setText("cyberpwn");
            txtCyberpwn.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
            txtCyberpwn.setColumns(10);
            contentPanel.add(txtCyberpwn, "cell 0 5,growx");
        }
        {
            JLabel lblPassword = new JLabel("Password");
            lblPassword.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
            contentPanel.add(lblPassword, "cell 0 6");
        }
        {
            txtReactisawesome = new JPasswordField();
            txtReactisawesome.setText("reactisawesome");
            txtReactisawesome.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
            txtReactisawesome.setColumns(10);

            contentPanel.add(txtReactisawesome, "cell 0 7,growx");
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("Add Connection");
                okButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        addServer(txtFancyServer.getText(), txtLocalhost.getText(), Integer.parseInt(textField_1.getText()), txtCyberpwn.getText(), txtReactisawesome.getText());
                        setVisible(false);
                        dispose();
                    }
                });
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }

            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        setVisible(false);
                        dispose();
                    }
                });
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
    }

    public static void addConnection() {
        try {
            AddConnection dialog = new AddConnection();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addServer(String name, String address, int port, String username, String password) {
        ReactClient.getInstance().validateConnectionAdd(name, address, port, username, password);
    }
}
