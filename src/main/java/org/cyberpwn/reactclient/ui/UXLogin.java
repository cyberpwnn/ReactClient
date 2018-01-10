package org.cyberpwn.reactclient.ui;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class UXLogin
{

	private JFrame frame;


	/**
	 * Create the application.
	 */
	public UXLogin()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		CircularLoaderBar bar = new CircularLoaderBar();
		frame.getContentPane().add(bar);
		FrameDragListener frameDragListener = new FrameDragListener(frame);
		frame.addMouseListener(frameDragListener);
		frame.addMouseMotionListener(frameDragListener);
		frame.requestFocusInWindow();
		frame.setVisible(true);
	}

	public static class FrameDragListener extends MouseAdapter
	{
		private final JFrame frame;
		private Point mouseDownCompCoords = null;

		public FrameDragListener(JFrame frame)
		{
			this.frame = frame;
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			mouseDownCompCoords = null;
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			mouseDownCompCoords = e.getPoint();
		}

		@Override
		public void mouseDragged(MouseEvent e)
		{
			Point currCoords = e.getLocationOnScreen();
			frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
		}
	}
}
