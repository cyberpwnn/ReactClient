package org.cyberpwn.react.network;

import org.cyberpwn.react.util.GMap;
import org.cyberpwn.react.util.JSONArray;
import org.cyberpwn.react.util.JSONObject;

public class Network
{
	private GMap<String, NetworkedServer> servers;
	
	public Network()
	{
		this.servers = new GMap<String, NetworkedServer>();
	}
	
	public Network(JSONObject network)
	{
		fromJson(network);
	}
	
	public void fromJson(JSONObject network)
	{
		this.servers = new GMap<String, NetworkedServer>();
		
		JSONArray js = network.getJSONArray("servers");
		
		for(int i = 0; i < js.length(); i++)
		{
			JSONObject jsx = js.getJSONObject(i);
			NetworkedServer server = new NetworkedServer(jsx);
			servers.put(server.getName(), server);
		}
	}
	
	public JSONObject toJson()
	{
		JSONObject js = new JSONObject();
		JSONArray jsa = new JSONArray();
		
		for(String i : servers.keySet())
		{
			servers.get(i).toJson(jsa);
		}
		
		js.put("servers", jsa);
		
		return js;
	}
}
