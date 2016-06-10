package com.glacialrush.react.util;

public class Timer
{
	private long tns;
	private long cns;
	
	public Timer()
	{
		tns = 0;
		cns = 0;
	}
	
	public void start()
	{
		cns = M.ns();
	}
	
	public void stop()
	{
		tns = M.ns() - cns;
		cns = M.ns();
	}
	
	public long getTime()
	{
		return tns;
	}
	
	public long getLastRun()
	{
		return cns;
	}
}
