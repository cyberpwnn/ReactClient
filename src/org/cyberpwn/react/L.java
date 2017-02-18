package org.cyberpwn.react;

import org.cyberpwn.react.network.NetworkedServer;

public class L
{
	public static String log;
	
	private static void write(String s)
	{
		log = log + s + "\n";
		System.out.println("> " + s);
		
		try
		{
			for(NetworkedServer i : ReactClient.getInstance().getNetwork().getServers())
			{
				i.push();
			}
		}
		
		catch(Exception e)
		{
			
		}
	}
	
	public static void n(String log)
	{
		write("[NETWORK]: " + log);
	}
	
	public static void l(String log)
	{
		write("[INFO]: " + log);
	}
	
	public static void e(String log)
	{
		write("[FATAL]: " + log);
	}
	
	public static void w(String log)
	{
		write("[WARN]: " + log);
	}
}
