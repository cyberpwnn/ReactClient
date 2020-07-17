package org.cyberpwn.react.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import org.cyberpwn.react.util.GMap;
import org.cyberpwn.react.util.JSONObject;

public class RequestBasic extends Thread
{
	private RequestCallbackBasic callback;
	private NetworkedServer ns;
	private Socket s;

	public RequestBasic(NetworkedServer ns, RequestCallbackBasic callback)
	{
		this.callback = callback;
		this.ns = ns;
	}

	@Override
	public void run()
	{
		try
		{
			s = new Socket(ns.getAddress(), ns.getPort());
			s.setSoTimeout(500);
			DataInputStream i = new DataInputStream(s.getInputStream());
			DataOutputStream o = new DataOutputStream(s.getOutputStream());
			PacketRequest pr = new PacketRequest(ns.getUsername(), ns.getPassword(), PacketRequestType.GET_BASIC.toString());
			o.writeUTF(pr.toString());
			o.flush();
			String response = i.readUTF();
			PacketResponse ps = new PacketResponse(new JSONObject(response));
			GMap<String, String> data = new GMap<String, String>();

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
						data.put(j, ps.getJSON().getString(j));
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
