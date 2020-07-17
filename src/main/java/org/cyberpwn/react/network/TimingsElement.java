package org.cyberpwn.react.network;

public class TimingsElement
{
	private String name;
	private double pct;
	private long hits;
	private long violations;
	
	public TimingsElement(String name, double pct, long hits, long violations)
	{
		this.name = name;
		this.pct = pct;
		this.hits = hits;
		this.violations = violations;
	}
	
	public TimingsElement(String i)
	{
		fromData(i);
	}
	
	@Override
	public String toString()
	{
		return name + ",,,," + pct + ",,,," + hits + ",,,," + violations;
	}
	
	public void fromData(String s)
	{
		name = s.split(",,,,")[0];
		pct = Double.valueOf(s.split(",,,,")[1]);
		hits = Long.valueOf(s.split(",,,,")[2]);
		violations = Long.valueOf(s.split(",,,,")[3]);
	}
	
	public String getName()
	{
		return name;
	}
	
	public double getPct()
	{
		return pct;
	}
	
	public long getHits()
	{
		return hits;
	}
	
	public long getViolations()
	{
		return violations;
	}
}
