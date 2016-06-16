package org.cyberpwn.react.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

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
			s.setSoTimeout(500);
			DataInputStream i = new DataInputStream(s.getInputStream());
			DataOutputStream o = new DataOutputStream(s.getOutputStream());
			PacketRequest pr = new PacketRequest(ns.getUsername(), ns.getPassword(), PacketRequestType.GET_SAMPLES.toString());
			o.writeUTF(pr.toString());
			o.flush();
			String response = i.readUTF();
			PacketResponse ps = new PacketResponse(new JSONObject(response));
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
						e.printStackTrace();
					}
				}
				
				callback.run(data, true);
			}
			
			callback.run(data, false);
		}
		
		catch(Exception e)
		{
			
		}
	}
}
