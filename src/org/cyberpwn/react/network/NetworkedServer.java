package org.cyberpwn.react.network;

import org.cyberpwn.react.ui.ServerTab;
import org.cyberpwn.react.util.GMap;
import org.cyberpwn.react.util.JSONArray;
import org.cyberpwn.react.util.JSONObject;

public class NetworkedServer
{
	private String name;
	private String username;
	private String password;
	private String address;
	private Integer port;
	private GMap<String, Double> sample;
	private ServerTab tab;
	private Request rx;
	private String version;
	private String versionBukkit;
	
	public NetworkedServer(String name, String username, String password, String address, Integer port)
	{
		this.name = name;
		this.username = username;
		this.password = password;
		this.address = address;
		this.tab = null;
		this.port = port;
		this.sample = new GMap<String, Double>();
		this.rx = null;
		this.version = null;
		this.versionBukkit = null;
	}
	
	public NetworkedServer(JSONObject js)
	{
		this.name = js.getString("name");
		this.username = js.getString("username");
		this.password = js.getString("password");
		this.address = js.getString("address");
		this.port = js.getInt("port");
		this.tab = null;
		this.sample = new GMap<String, Double>();
	}
	
	public void toJson(JSONArray parent)
	{
		JSONObject js = new JSONObject();
		
		js.put("name", name);
		js.put("username", username);
		js.put("password", password);
		js.put("address", address);
		js.put("port", port);
		
		parent.put(js);
	}
	
	public void requestSample()
	{
		if(version == null || versionBukkit == null)
		{
			new RequestBasic(this, new RequestCallbackBasic()
			{
				public void run()
				{
					if(isOk())
					{
						version = getData().get("version");
						versionBukkit = getData().get("version-bukkit");
					}
				}
			}).start();
		}
		
		if(rx != null && rx.isAlive())
		{
			return;
		}
		
		rx = new Request(this, new RequestCallback()
		{
			public void run()
			{
				if(isOk())
				{
					sample = getData();
					tab.push(sample);
					
				}
			}
		});
		
		rx.start();
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	public Integer getPort()
	{
		return port;
	}
	
	public void setPort(Integer port)
	{
		this.port = port;
	}
	
	public GMap<String, Double> getSample()
	{
		return sample;
	}
	
	public void setSample(GMap<String, Double> sample)
	{
		this.sample = sample;
	}
	
	public ServerTab getTab()
	{
		return tab;
	}
	
	public void setTab(ServerTab tab)
	{
		this.tab = tab;
	}

	public Request getRx()
	{
		return rx;
	}

	public String getVersion()
	{
		return version;
	}

	public String getVersionBukkit()
	{
		return versionBukkit;
	}
}
