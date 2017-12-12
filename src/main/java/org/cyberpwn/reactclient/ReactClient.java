package org.cyberpwn.reactclient;

import org.cyberpwn.reactlink.RemoteClient;

public class ReactClient
{
	public static void main(String[] a)
	{
		startClient();
	}

	public static void startClient()
	{
		RemoteClient client = new RemoteClient("localhost", 8192);

	}

	public static void l(String log)
	{
		System.out.println(log);
	}
}
