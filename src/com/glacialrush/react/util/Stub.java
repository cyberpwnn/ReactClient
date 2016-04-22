package com.glacialrush.react.util;

public class Stub
{
	private String title;
	private String text;
	private String ago;
	
	public Stub(String title, String text, String ago)
	{
		this.title = title;
		this.text = text;
		this.ago = ago;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getAgo()
	{
		return ago;
	}

	public void setAgo(String ago)
	{
		this.ago = ago;
	}
}
