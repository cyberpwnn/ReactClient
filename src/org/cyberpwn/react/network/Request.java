package org.cyberpwn.react.network;

public class Request extends Thread
{
	private RequestCallback callback;
	
	public Request(RequestCallback callback)
	{
		this.callback = callback;
	}
}
