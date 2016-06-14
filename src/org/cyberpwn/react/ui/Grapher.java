package org.cyberpwn.react.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;

import javax.swing.JComponent;

import org.cyberpwn.react.util.F;
import org.cyberpwn.react.util.GList;

public class Grapher extends JComponent
{
	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	private int min;
	private boolean rcColor;
	private int max;
	private Color color;
	private GList<Double> data;
	
	public Grapher(int width, int height, int max, Color color, GList<Double> data)
	{
		this.width = width;
		this.color = color;
		this.max = max + 1;
		this.height = height;
		this.data = data;
		this.rcColor = false;
	}
	
	public double pos(double v)
	{
		double p = (double) height - ((double) height * (double) (v / (double) max));
		
		if(p == 0)
		{
			p = 10;
		}
		
		if(p == height)
		{
			p = height - 1;
		}
		
		return p;
	}
	
	public void paintComponent(Graphics gg)
	{
		super.paintComponent(gg);
		Graphics2D g = (Graphics2D) gg;
		g.setStroke(new BasicStroke(2.2f));
		
		if(data.isEmpty())
		{
			return;
		}
		
		if(color == null)
		{
			double pc = (double) (data.get(data.size() - 1) / (double) max);
			
			if(rcColor)
			{
				pc = 1.0 - pc;
			}
			
			if(pc > 1.0)
			{
				pc = 1.0;
			}
			
			if(pc < 0.0)
			{
				pc = 0.0;
			}
			
			g.setColor(new Color(255 - (int)(255.0 * pc), (int)(160.0 * pc), (int)(40.0 * pc)));
		}
		
		else
		{
			g.setColor(color);
		}
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Path2D.Double p = new Path2D.Double();
		p.setWindingRule(Path2D.WIND_NON_ZERO);
		double step = (double) width / (double) data.size();
		double x = 0;
		GList<Double> d = data.copy();
		p.moveTo(x, pos(d.pop()));
		double mv = 0;
		double fmv = 0;
		
		while(!d.isEmpty())
		{
			if(d.size() >= 3)
			{
				double a = pos(d.pop());
				double b = pos(d.pop());
				double dv = d.pop();
				double c = pos(dv);
				mv = c;
				fmv = dv;
				
				p.curveTo(x + step, a, x + step + step, b, x + step + step + step, c);
				x = x + (step * 3);
			}
			
			else
			{
				break;
			}
		}
		
		if(mv > height - 100)
		{
			mv -= 30;
		}
				
		else
		{
			mv += 30;
		}
		
		if(fmv > 100000)
		{
			fmv /= 1000000.0;
		}
		
		g.drawString(F.f(fmv, 2), (int)width - 50, (int)mv);
		g.draw(p);
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setHeight(int height)
	{
		this.height = height;
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