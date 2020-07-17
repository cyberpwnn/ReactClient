package org.cyberpwn.react.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class PortJTextField extends JTextField
{
	private static final long serialVersionUID = 1L;
	
	public PortJTextField()
	{
		addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				char ch = e.getKeyChar();
				
				if(!isNumber(ch) && !isValidSignal(ch) && !validatePoint(ch) && ch != '\b')
				{
					e.consume();
				}
			}
		});
		
	}
	
	private boolean isNumber(char ch)
	{
		return ch >= '0' && ch <= '9';
	}
	
	private boolean isValidSignal(char ch)
	{
		if((getText() == null || "".equals(getText().trim())) && ch == '-')
		{
			return false;
		}
		
		return false;
	}
	
	private boolean validatePoint(char ch)
	{
		return false;
	}
}