package com.IA.T1.Interface;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GifPanel extends JPanel
{
	private Icon icon = null;
    private JLabel label = null;
    
    protected GifPanel(String gifPath)
	{
    	icon = new ImageIcon(gifPath);
    	label = new JLabel(icon);
//		label.setSize(30, 40);
    	this.setBackground(Color.BLACK);
		this.add(label);
	}   

}
