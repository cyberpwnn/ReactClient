package org.cyberpwn.react;

import java.awt.Window.Type;

import javax.swing.JFrame;

public class AboutReact
{
	private JFrame frame;
	
	public AboutReact()
	{
		initialize();
		frame.setVisible(true);
	}
	
	private void initialize()
	{
		frame = new JFrame();
		frame.setType(Type.POPUP);
		frame.setResizable(false);
		frame.setBounds(100, 100, 432, 465);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
