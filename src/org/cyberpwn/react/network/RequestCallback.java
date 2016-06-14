package org.cyberpwn.react.network;

import org.cyberpwn.react.util.GMap;

public class RequestCallback implements Runnable
{
	private GMap<String, Double> data;
	private boolean ok;
	
	public void run(GMap<String, Double> data, boolean ok)
	{
		this.data = data;
		this.ok = ok;
		run();
	}
	
	@Override
	public void run()
	{
		
	}

	public GMap<String, Double> getData()
	{
		return data;
	}

	public boolean isOk()
	{
		return ok;
	}
}
