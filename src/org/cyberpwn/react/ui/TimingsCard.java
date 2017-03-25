package org.cyberpwn.react.ui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import org.cyberpwn.react.network.TimingsElement;
import org.cyberpwn.react.util.GList;

public class TimingsCard extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JLabel named;
	private JLabel datasec;
	private Grapher graph;
	private GList<Double> data;
	
	public TimingsCard()
	{
		setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		setBackground(Color.WHITE);
		data = new GList<Double>();
		named = new JLabel("<TIMINGS>");
		named.setFont(new Font("Segoe UI Light", Font.PLAIN, 23));
		
		graph = new Grapher(1, Color.RED, data);
		graph.setBackground(Color.DARK_GRAY);
		
		datasec = new JLabel("MS");
		datasec.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(graph, GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE).addGroup(groupLayout.createSequentialGroup().addComponent(named).addPreferredGap(ComponentPlacement.RELATED, 152, Short.MAX_VALUE).addComponent(datasec))).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(named).addComponent(datasec)).addPreferredGap(ComponentPlacement.RELATED).addComponent(graph, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE).addContainerGap()));
		setLayout(groupLayout);
	}
	
	public void addData(TimingsElement e)
	{
		named.setText(e.getName());
		datasec.setText(e.getPct() + " MS");
		data.add(e.getPct() * 100);
		graph.setData(data);
		
		if(data.size() > graph.getWidth())
		{
			while(data.size() > graph.getWidth())
			{
				data.remove(0);
			}
		}
		
		graph.repaint();
	}
	
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
	
	public JLabel getNamed()
	{
		return named;
	}
	
	public JLabel getDatasec()
	{
		return datasec;
	}
	
	public Grapher getGraph()
	{
		return graph;
	}
	
	public GList<Double> getData()
	{
		return data;
	}
}
