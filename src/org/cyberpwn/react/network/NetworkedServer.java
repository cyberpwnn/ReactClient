package org.cyberpwn.react.network;

import javax.swing.JLabel;
import org.cyberpwn.react.L;
import org.cyberpwn.react.ReactClient;
import org.cyberpwn.react.ui.ServerTab;
import org.cyberpwn.react.util.GList;
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
	private RequestActions ra;
	private String version;
	private String versionBukkit;
	private GList<String> actions;
	private int req;
	private int s;
	private boolean gettingReactors;
	
	public NetworkedServer(String name, String username, String password, String address, Integer port)
	{
		this.name = name;
		req = 0;
		this.username = username;
		this.password = password;
		this.address = address;
		tab = null;
		gettingReactors = true;
		actions = null;
		this.port = port;
		sample = new GMap<String, Double>();
		rx = null;
		s = 0;
		ra = null;
		version = null;
		versionBukkit = null;
	}
	
	public NetworkedServer(JSONObject js)
	{
		name = js.getString("name");
		username = js.getString("username");
		password = js.getString("password");
		address = js.getString("address");
		port = js.getInt("port");
		tab = null;
		sample = new GMap<String, Double>();
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
	
	public void reset()
	{
		req = 0;
	}
	
	public void requestActions()
	{
		if(ReactClient.getInstance().isLocked(this))
		{
			return;
		}
		
		if(actions != null)
		{
			return;
		}
		
		gettingReactors = true;
		
		tab.pushStartedActions();
		
		if(ra != null && ra.isAlive())
		{
			return;
		}
		
		ra = new RequestActions(this, new RequestActionsCallback()
		{
			@Override
			public void run()
			{
				if(isOk())
				{
					actions = getActions();
					tab.push(actions);
					gettingReactors = false;
				}
			}
		});
		
		ra.start();
		req++;
		tab.status(true, "Reconnecting (try " + req + " of 4" + ")");
		
		if(req > 4)
		{
			L.n(NetworkedServer.this.getName() + " Failed to connect.");
			tab.status(true, "No Connection");
			ReactClient.getInstance().getNetwork().fail(NetworkedServer.this);
		}
	}
	
	public void requestSample()
	{
		if(gettingReactors)
		{
			return;
		}
		
		s++;
		
		if(version == null || versionBukkit == null)
		{
			new RequestBasic(this, new RequestCallbackBasic()
			{
				@Override
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
			@Override
			public void run()
			{
				if(isOk())
				{
					sample = getData();
					tab.push(sample, getConsole());
					
					tab.status(false, "Connected");
					
					s = 0;
				}
			}
		});
		
		rx.start();
		
		if(getDrops() > 2)
		{
			L.w(getName() + " dropped " + getDrops() + " packets");
			tab.status(true, "Not Connected (" + getDrops() + " Dropouts)");
			
			if(getDrops() > 100)
			{
				tab.status(true, "Reconnecting (try " + req + " of 4" + ")");
				L.w(getName() + " has dropped over " + getDrops() + " packets.");
				L.w(getName() + " lost connection. Reconnecting to reactors...");
				ReactClient.getInstance().releaseConnection(this);
				actions = null;
				requestActions();
			}
		}
	}
	
	public void push()
	{
		tab.push(sample);
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public int getDrops()
	{
		return s;
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
	
	public void act(String action, final JLabel label)
	{
		label.setText("Preparing...");
		RequestAction ra = new RequestAction(this, new RequestActionCallback()
		{
			@Override
			public void run()
			{
				if(isOk())
				{
					label.setText("Action Completed");
				}
				
				else
				{
					label.setText("Failed... Check Console.");
				}
			}
		}, action);
		label.setText("Executing...");
		ra.start();
	}
}
