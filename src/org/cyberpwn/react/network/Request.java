package org.cyberpwn.react.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.cyberpwn.react.util.GMap;
import org.cyberpwn.react.util.JSONObject;

public class Request extends Thread
{
	private RequestCallback callback;
	private NetworkedServer ns;
	private Socket s;
	
	public Request(NetworkedServer ns, RequestCallback callback)
	{
		this.callback = callback;
		this.ns = ns;
	}
	
	public void run()
	{
		try
		{
			s = new Socket(ns.getAddress(), ns.getPort());
			DataInputStream i = new DataInputStream(s.getInputStream());
			DataOutputStream o = new DataOutputStream(s.getOutputStream());
			PacketRequest pr = new PacketRequest(ns.getUsername(), ns.getPassword(), PacketRequestType.GET_SAMPLES.toString());
			o.writeUTF(pr.toString());
			PacketResponse ps = new PacketResponse(new JSONObject(i.readUTF()));
			GMap<String, Double> data = new GMap<String, Double>();
			
			if(ps.getString("type").equals("OK"))
			{
				for(String j : ps.getJSON().keySet())
				{
					if(j.equals("type"))
					{
						continue;
					}
					
					try
					{
						data.put(j, ps.getJSON().getDouble(j));
					}
					
					catch(Exception e)
					{
						
					}
				}
				
				callback.run(data, true);
			}
			
			callback.run(data, false);
		}
		
		catch(UnknownHostException e)
		{
			e.printStackTrace();
		} 
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
