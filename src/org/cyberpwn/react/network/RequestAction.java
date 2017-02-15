package org.cyberpwn.react.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import org.cyberpwn.react.util.JSONObject;

public class RequestAction extends Thread
{
	private RequestActionCallback callback;
	private NetworkedServer ns;
	private Socket s;
	private String action;
	
	public RequestAction(NetworkedServer ns, RequestActionCallback callback, String action)
	{
		this.callback = callback;
		this.ns = ns;
		this.action = action;
	}
	
	@Override
	public void run()
	{
		try
		{
			System.out.println("Requesting Action: " + action);
			s = new Socket(ns.getAddress(), ns.getPort());
			s.setSoTimeout(500);
			DataInputStream i = new DataInputStream(s.getInputStream());
			DataOutputStream o = new DataOutputStream(s.getOutputStream());
			PacketRequest pr = new PacketRequest(ns.getUsername(), ns.getPassword(), "ACTION " + action);
			System.out.println("OUT: " + pr.toString());
			o.writeUTF(pr.toString());
			o.flush();
			String response = i.readUTF();
			PacketResponse ps = new PacketResponse(new JSONObject(response));
			System.out.println("IN: " + ps.toString());
			
			if(ps.getString("type").equals("OK"))
			{
				callback.run(true);
			}
			
			else
			{
				callback.run(false);
			}
		}
		
		catch(Exception e)
		{
			
		}
	}
}
