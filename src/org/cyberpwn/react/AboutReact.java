package org.cyberpwn.react;

import java.awt.Font;
import java.awt.Window.Type;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

public class AboutReact
{
	private JFrame frame;
	private String version = "2.3";
	
	public AboutReact()
	{
		initialize();
		frame.setVisible(true);
	}
	
	private void initialize()
	{
		frame = new JFrame();
		frame.setType(Type.UTILITY);
		frame.setResizable(false);
		frame.setBounds(100, 100, 548, 533);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JLabel lblReactClient = new JLabel("React Client");
		springLayout.putConstraint(SpringLayout.NORTH, lblReactClient, 263, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblReactClient, 172, SpringLayout.WEST, frame.getContentPane());
		lblReactClient.setFont(new Font("Segoe UI Light", Font.PLAIN, 40));
		frame.getContentPane().add(lblReactClient);
		
		JLabel lblDevelopedByCyberpwn = new JLabel("Developed by Cyberpwn");
		springLayout.putConstraint(SpringLayout.NORTH, lblDevelopedByCyberpwn, 6, SpringLayout.SOUTH, lblReactClient);
		springLayout.putConstraint(SpringLayout.WEST, lblDevelopedByCyberpwn, 178, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblDevelopedByCyberpwn, -156, SpringLayout.SOUTH, frame.getContentPane());
		lblDevelopedByCyberpwn.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		frame.getContentPane().add(lblDevelopedByCyberpwn);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon(ReactClient.class.getResource("/org/cyberpwn/react/ui/server-low.png")));
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 143, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel, -6, SpringLayout.NORTH, lblReactClient);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel labelVersion = new JLabel("Version " + version);
		springLayout.putConstraint(SpringLayout.NORTH, labelVersion, 24, SpringLayout.NORTH, lblReactClient);
		springLayout.putConstraint(SpringLayout.WEST, labelVersion, 6, SpringLayout.EAST, lblReactClient);
		labelVersion.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		frame.getContentPane().add(labelVersion);
	}
}
