package com.IA.T1.Interface;

import java.awt.BorderLayout;
import java.util.Timer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;

class MapFrame extends JFrame
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static MapFrame		pMapFrame		= null;
	private static MapPanel 	mapPanel 		= null;
	private static GifPanelIntro 		gifPanelIntro 	= null;
	final protected int MapFrameSizeX = 602;
	final protected int MapFrameSizeY = 646;

	protected MapFrame()
	{					
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		this.setBounds(100, 40, this.MapFrameSizeX, this.MapFrameSizeY);
		this.setUndecorated(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		this.setLayout(new BorderLayout());
		this.setVisible(true);	
		
		pMapFrame = this;
		
		gifPanelIntro = new GifPanelIntro();
		this.add(gifPanelIntro);
		gifPanelIntro.setVisible(true);

		this.validate();		
	}
	
	protected static void startMap() 
	{
		gifPanelIntro.setVisible(false);
		mapPanel = new MapPanel();
		pMapFrame.add(mapPanel);
		mapPanel.setVisible(true);		
	}
	
	protected void repaintAgente(int[] agentCoordinates)
	{
		mapPanel.repaintAgente(agentCoordinates);
	}
	
	protected int getMapSizeFrameX()
	{
		return this.MapFrameSizeX;
	}
	
	protected int getMapSizeFrameY()
	{
		return this.MapFrameSizeY;
	}
}
