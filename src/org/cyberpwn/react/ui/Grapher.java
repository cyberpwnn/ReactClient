package org.cyberpwn.react.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import org.cyberpwn.react.util.GList;

public class Grapher extends JPanel
{
	private static final long serialVersionUID = 1L;
	private int min;
	private boolean rcColor;
	private int max;
	private Color color;
	private GList<Double> data;
	
	public Grapher(int max, Color color, GList<Double> data)
	{
		this.color = color;
		this.max = max;
		this.data = data;
		this.rcColor = false;
	}
	
	public int pos(Double double1)
	{
		int y = getHeight() - (int) (((double)double1 / (double) max) * (double)getHeight());
		
		if(y <= 0)
		{
			y = 20;
		}
		
		if(y >= getHeight())
		{
			y = getHeight() - 20;
		}
		
		return y;
	}
	
	public void paintComponent(Graphics gg)
	{
		super.paintComponent(gg);
		Graphics2D g = (Graphics2D) gg;
		g.setStroke(new BasicStroke(1.2f));
				
		if(data.isEmpty())
		{
			return;
		}
		
		g.setColor(color);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	
		int x = 0;
		int ly = 0;
		
		for(int i = x; i < getWidth(); i++)
		{
			if(data.size() > i)
			{
				g.drawRect(i, pos(data.get(i)), 1, 1);
				
				if(i > 0)
				{
					if(pos(data.get(i)) > ly)
					{
						for(int j = ly; j < pos(data.get(i)); j++)
						{
							g.drawRect(i, j, 1, 1);
						}
					}
					
					else if(pos(data.get(i)) < ly)
					{
						for(int j = ly; j > pos(data.get(i)); j--)
						{
							g.drawRect(i, j, 1, 1);
						}
					}
				}
				
				ly = pos(data.get(i));
			}
		}
	}
	
	public int getMin()
	{
		return min;
	}
	
	public void setMin(int min)
	{
		this.min = min;
	}
	
	public int getMax()
	{
		return max;
	}
	
	public void setMax(int max)
	{
		this.max = max;
	}
	
	public GList<Double> getData()
	{
		return data;
	}
	
	public void setData(GList<Double> data)
	{
		this.data = data;
	}
	
	public boolean isRcColor()
	{
		return rcColor;
	}
	
	public void setRcColor(boolean rcColor)
	{
		this.rcColor = rcColor;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
}