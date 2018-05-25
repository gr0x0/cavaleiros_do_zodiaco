package com.IA.T1.Interface;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GifPanelIntro extends JPanel
{
	private Icon icon = null;
	private Icon intro = null;
    private JLabel label1 = null;
    private JLabel label2 = null;
    
    protected GifPanelIntro()
	{
    	icon = new ImageIcon("data\\intro.gif");
    	intro = new ImageIcon("data\\logo.gif");
    	label1 = new JLabel(icon);
    	label2 = new JLabel(intro);
    	this.setBackground(Color.BLACK);
		this.add(label1);
		this.add(label2);
	}   

}
