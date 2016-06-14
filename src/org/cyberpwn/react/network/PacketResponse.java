package org.cyberpwn.react.network;

import org.cyberpwn.react.util.GList;
import org.cyberpwn.react.util.JSONArray;
import org.cyberpwn.react.util.JSONObject;

public class PacketResponse
{
	private JSONObject js;
	
	public PacketResponse()
	{
		this.js = new JSONObject();
	}
	
	public PacketResponse(JSONObject js)
	{
		this.js = js;
	}
	
	public JSONObject getJSON()
	{
		return js;
	}
	
	public void put(String s, PacketResponseType o)
	{
		js.put(s, o.toString());
	}
	
	public void put(String s, Double o)
	{
		js.put(s, o);
	}
	
	public void put(String s, Integer o)
	{
		js.put(s, o);
	}
	
	public void put(String s, Long o)
	{
		js.put(s, o);
	}
	
	public void put(String s, String o)
	{
		js.put(s, o);
	}
	
	public void put(String s, GList<String> o)
	{
		JSONArray jsa = new JSONArray();
		
		for(String i : o)
		{
			jsa.put(i);
		}
		
		js.put(s, jsa);
	}
	
	public String getString(String s)
	{
		return js.getString(s);
	}
	
	public Double getDouble(String s)
	{
		return js.getDouble(s);
	}
	
	public Integer getInt(String s)
	{
		return js.getInt(s);
	}
	
	public Long getLong(String s)
	{
		return js.getLong(s);
	}
	
	public Boolean getBoolean(String s)
	{
		return js.getBoolean(s);
	}
	
	public GList<String> getStringList(String s)
	{
		GList<String> list = new GList<String>();
		
		for(Object i : js.getJSONArray(s))
		{
			list.add(i.toString());
		}
		
		return list;
	}
}
