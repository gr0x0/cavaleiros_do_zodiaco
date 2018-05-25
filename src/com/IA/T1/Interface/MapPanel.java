package com.IA.T1.Interface;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

class MapPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Image imgBackground;
	
	private Icon icon = new ImageIcon(InterfaceManager.agentImgPath);
	private Icon iconMirror = new ImageIcon("data\\CavMirror.gif");
    private JLabel label = null;

	protected MapPanel()
	{}

	@Override
	public void paintComponent(Graphics g)
	{
		try
		{
			imgBackground = ImageIO.read(new File(InterfaceManager.backgroundImgPath));
			g.drawImage(imgBackground, 0, 0, null);
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
	
	protected void repaintAgente(int[] agentCoordinates)
	{
		int agentCoordX = agentCoordinates[0]; 
		int agentCoordY = agentCoordinates[1];
		
		if(this.label==null)
		{
			label = new JLabel(icon);
			label.setSize(30, 40);
			this.add(label);
		}
		if(agentCoordX<=34 && agentCoordX>27)
		{
			label.setIcon(this.iconMirror);
			label.repaint();
		}
		else if(agentCoordX<=27 && agentCoordX>21)
		{
			label.setIcon(this.icon);
			label.repaint();
		}
		else if(agentCoordX<=21 && agentCoordX>13)
		{
			label.setIcon(this.iconMirror);
			label.repaint();
		}
		else if(agentCoordX<=13 && agentCoordX>6)
		{
			label.setIcon(this.icon);
			label.repaint();
		}
		else if(agentCoordX<=6)
		{
			label.setIcon(this.iconMirror);
			label.repaint();
		}
		
        label.setLocation(InterfaceManager.agentCoordAtuais[0],
        		InterfaceManager.agentCoordAtuais[1]-20);
	}
}