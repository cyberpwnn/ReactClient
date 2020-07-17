package org.cyberpwn.react.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;
import org.cyberpwn.react.util.GList;

public class TimingsPackage
{
	private GList<TimingsElement> elements;
	
	public TimingsPackage()
	{
		elements = new GList<TimingsElement>();
	}
	
	public String getData() throws IOException
	{
		ByteArrayOutputStream boas = new ByteArrayOutputStream();
		GZIPOutputStream gzo = new GZIPOutputStream(boas);
		DataOutputStream dos = new DataOutputStream(gzo);
		dos.writeUTF(toString());
		return new String(boas.toByteArray(), "UTF-8");
	}
	
	public void fromData(String comp) throws IOException
	{
		fromString(comp);
	}
	
	@Override
	public String toString()
	{
		return elements.toString(":::");
	}
	
	public void fromString(String s)
	{
		elements.clear();
		
		for(String i : s.split(":::"))
		{
			elements.add(new TimingsElement(i));
		}
	}
	
	public GList<TimingsElement> getElements()
	{
		return elements;
	}
}
