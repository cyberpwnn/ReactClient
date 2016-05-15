package com.glacialrush.react.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;

public class Error extends JDialog
{
	static final long serialVersionUID = 2831793718968667876L;
	private final JPanel contentPanel = new JPanel();
	
	public static void error(String title, String message, boolean eoc)
	{
		try
		{
			Error dialog = new Error(title, message, eoc);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Error(String title, String message, boolean eoc)
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(Error.class.getResource("/com/glacialrush/react/icon.png")));
		setAlwaysOnTop(true);
		setTitle(title);
		setBounds(100, 100, 485, 226);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JTextArea textArea = new JTextArea();
			textArea.setColumns(22);
			textArea.setText(message);
			textArea.setEditable(false);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			textArea.setFont(new Font("Yu Gothic Light", Font.PLAIN, 24));
			textArea.setForeground(Color.WHITE);
			textArea.setBackground(Color.DARK_GRAY);
			contentPanel.add(textArea);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.DARK_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter()
				{
					@Override
					public void mouseClicked(MouseEvent arg0)
					{
						dispose();
						
						if(eoc)
						{
							System.exit(0);
						}
						
						else
						{
							Login.unerror();
						}
					}
				});
				
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
}
