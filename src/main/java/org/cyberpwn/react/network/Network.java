package org.cyberpwn.react.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import org.cyberpwn.react.ConnectionFailure;
import org.cyberpwn.react.L;
import org.cyberpwn.react.util.GList;
import org.cyberpwn.react.util.GMap;
import org.cyberpwn.react.util.JSONArray;
import org.cyberpwn.react.util.JSONObject;

public class Network
{
	private GMap<String, NetworkedServer> servers;
	
	public Network()
	{
		servers = new GMap<String, NetworkedServer>();
		
		File f = new File(new File(".").getAbsolutePath(), "react-data");
		f.mkdirs();
		L.l("Loading Config at: " + f.getAbsolutePath());
		File c = new File(f, "react.json");
		
		if(c.exists())
		{
			try
			{
				JSONObject jso = new JSONObject(readFile(c.getPath()));
				
				if(jso != null)
				{
					fromJson(jso);
				}
			}
			
			catch(Exception e)
			{
				L.l("Appears to be a new Config.");
				
				try
				{
					save();
				}
				
				catch(IOException e1)
				{
					e1.printStackTrace();
				}
			}
		}
		
		else
		{
			try
			{
				save();
			}
			
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void save() throws IOException
	{
		File f = new File(new File(".").getAbsolutePath(), "react-data");
		f.mkdirs();
		L.l("Saving Config at: " + f.getAbsolutePath());
		File c = new File(f, "react.json");
		
		if(!c.exists())
		{
			c.createNewFile();
		}
		
		PrintWriter writer = new PrintWriter(c.getPath(), "UTF-8");
		writer.println(toJson().toString());
		writer.close();
	}
	
	public static String readFile(String filename)
	{
		String result = "";
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(filename));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			
			while(line != null)
			{
				sb.append(line);
				line = br.readLine();
			}
			
			result = sb.toString();
			br.close();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Network(JSONObject network)
	{
		fromJson(network);
	}
	
	public GList<NetworkedServer> getServers()
	{
		return servers.v();
	}
	
	public void fromJson(JSONObject network)
	{
		servers = new GMap<String, NetworkedServer>();
		
		JSONArray js = network.getJSONArray("servers");
		
		for(int i = 0; i < js.length(); i++)
		{
			JSONObject jsx = js.getJSONObject(i);
			NetworkedServer server = new NetworkedServer(jsx);
			servers.put(server.getName(), server);
		}
	}
	
	public void rename(String newName, NetworkedServer ns)
	{
		servers.remove(ns.getName());
		servers.put(newName, ns);
		ns.setName(newName);
		
		try
		{
			save();
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
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
	
	public void addConnection(String name, String address, int port, String username, String password)
	{
		NetworkedServer ns = new NetworkedServer(name, username, password, address, port);
		servers.put(name, ns);
		
		try
		{
			save();
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void deleteServer(NetworkedServer ns)
	{
		servers.remove(ns.getName());
	}
	
	public void fail(NetworkedServer networkedServer)
	{
		ConnectionFailure.failed(networkedServer);
	}
}
