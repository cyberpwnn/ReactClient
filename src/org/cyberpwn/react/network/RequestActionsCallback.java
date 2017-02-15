package org.cyberpwn.react.network;

import org.cyberpwn.react.util.GList;

public class RequestActionsCallback implements Runnable
{
	private GList<String> actions;
	private boolean ok;
	
	public void run(GList<String> actions, boolean ok)
	{
		this.actions = actions;
		this.ok = ok;
		run();
	}
	
	@Override
	public void run()
	{
		
	}
	
	public GList<String> getActions()
	{
		return actions;
	}
	
	public boolean isOk()
	{
		return ok;
	}
}
