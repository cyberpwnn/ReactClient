package com.glacialrush.react.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

import com.glacialrush.react.Version;
import com.glacialrush.react.json.JSONObject;

public class ClientThread extends Thread
{
	private Integer port;
	private String address;
	private String username;
	private String password;
	private Socket socket;
	private Boolean running;
	private DataInputStream i;
	private DataOutputStream o;
	public static Boolean disconnect = false;
	
	public ClientThread(String address, Integer port, String username, String password)
	{
		this.address = address;
		this.port = port;
		this.username = username;
		this.password = password;
	}
	
	public void run()
	{
		running = true;
		
		try
		{
			socket = new Socket(address, port);
			socket.setSoTimeout(8000);
			
			i = new DataInputStream(socket.getInputStream());
			o = new DataOutputStream(socket.getOutputStream());
			
			o.writeInt(Version.C);
			o.writeUTF(username);
			o.writeUTF(password);
			o.writeBoolean(false);
			o.flush();
			
			String response = i.readUTF();
			
			if(!response.equals("ok"))
			{
				running = false;
				Login.error("Error while Connecting", response, false);
			}
		}
		
		catch(ConnectException e)
		{
			Login.error("Failed to Connect", "Could not connect to the server.", false);
			return;
		}
		
		catch(IOException e)
		{
			Login.error("Failed to Connect", "Could not connect to the server.", false);
			e.printStackTrace();
			return;
		}
		
		catch(Exception e)
		{
			Login.error("Failed to Connect", "Could not connect to the server.", false);
			e.printStackTrace();
			return;
		}
		
		if(running)
		{
			Login.begin();
		}
		
		while(running)
		{
			if(disconnect)
			{
				try
				{
					socket.close();
				} 
				
				catch(IOException e)
				{
					e.printStackTrace();
				}
								
				return;
			}
			
			try
			{
				String data = i.readUTF();
				Application.update(new JSONObject(data));
			} 
			
			catch(IOException e)
			{
				Login.error("Disconnected", "Lost Connection to server.", false);
				break;
			}
		}
	}
}
