package org.cyberpwn.reactclient.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class CircularProgressBar extends JPanel
{
	private static final long serialVersionUID = 8657006248665406571L;
	private final static int MAX_PROGRESS_AMOUNT = 100;
	private double prgValue = 0;
	private double lvalue = 0;
	private double dist = 0;

	public CircularProgressBar()
	{
		setForeground(Color.DARK_GRAY);
		setBackground(Color.DARK_GRAY);
		new Thread()
		{
			@Override
			public void run()
			{
				while(!Thread.interrupted())
				{
					if(!isVisible())
					{
						try
						{
							Thread.sleep(6);
							continue;
						}

						catch(InterruptedException e)
						{
							e.printStackTrace();
						}
					}

					repaint();

					try
					{
						if(lvalue > prgValue)
						{
							lvalue -= (lvalue - prgValue) / 60;
							dist = Math.abs(lvalue - prgValue);
						}

						if(lvalue < prgValue)
						{
							lvalue += (prgValue - lvalue) / 40;
							dist = Math.abs(prgValue - lvalue);
						}

						Thread.sleep(6);
					}

					catch(InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;

		setForeground(Color.DARK_GRAY);
		setBackground(Color.DARK_GRAY);
		g2.setColor(Color.DARK_GRAY);
		g2.clearRect(0, 0, getWidth(), getHeight());
		g2.setColor(Color.DARK_GRAY);
		g2.drawRect(0, 0, getWidth(), getHeight());
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(Color.DARK_GRAY);

		try
		{
			g2.setStroke(new BasicStroke(1000f + (float) ((dist) * 0.8)));
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

		g.drawArc(10, 10, getWidth() - 20, getHeight() - 20, 90, 360);

		if(prgValue <= MAX_PROGRESS_AMOUNT)
		{

			int angle = -(int) (((float) (lvalue) / MAX_PROGRESS_AMOUNT) * 360);

			g.setColor(UX.c);

			try
			{
				g2.setStroke(new BasicStroke(1f + (float) ((dist * 1.4))));
			}

			catch(Exception e)
			{
				e.printStackTrace();
			}

			g.drawArc(10, 10, getWidth() - 20, getHeight() - 20, 90, angle);
		}
	}

	public void setProgress(int pct)
	{
		prgValue = pct;
	}
}