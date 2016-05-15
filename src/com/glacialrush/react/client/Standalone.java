package com.glacialrush.react.client;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Standalone
{
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		
		catch(UnsupportedLookAndFeelException e)
		{
			
		} 
		
		catch(ClassNotFoundException e)
		{
			
		} 
		
		catch(InstantiationException e)
		{
			
		} 
		
		catch(IllegalAccessException e)
		{
			
		}
		
		Login.login();
	}
}
