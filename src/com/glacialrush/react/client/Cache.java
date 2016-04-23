package com.glacialrush.react.client;

import com.glacialrush.react.util.GList;
import com.glacialrush.react.util.GMap;

public class Cache
{
	private int size;
	private GMap<String, GList<Double>> data;
	
	public Cache()
	{
		size = 256;
		data = new GMap<String, GList<Double>>();
	}
	
	public void put(String s, Double v)
	{
		if(!data.containsKey(s))
		{
			data.put(s, new GList<Double>());
		}
		
		data.get(s).add(v);
		
		while(data.get(s).size() > size)
		{
			data.get(s).remove(0);
		}
	}
	
	public GList<Double> get(String s)
	{
		return data.get(s);
	}
	
	public Double latest(String s)
	{
		if(!data.containsKey(s) || data.get(s).isEmpty())
		{
			return null;
		}
		
		return data.get(s).get(data.get(s).size() - 1);
	}
}
