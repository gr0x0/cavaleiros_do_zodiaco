package com.IA.T1.Interface;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClockPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Icon icon01 = new ImageIcon("data\\clock01.gif");
	private Icon icon02 = new ImageIcon("data\\clock02.gif");
	private Icon icon03 = new ImageIcon("data\\clock03.gif");
	private Icon icon04 = new ImageIcon("data\\clock04.gif");
	private Icon icon05 = new ImageIcon("data\\clock05.gif");
	private Icon icon06 = new ImageIcon("data\\clock06.gif");
	private Icon icon07 = new ImageIcon("data\\clock07.gif");
	private Icon icon08 = new ImageIcon("data\\clock08.gif");
	private Icon icon09 = new ImageIcon("data\\clock09.gif");
	private Icon icon10 = new ImageIcon("data\\clock10.gif");
	private Icon icon11 = new ImageIcon("data\\clock11.gif");
	private Icon icon12 = new ImageIcon("data\\clock12.gif");
	private Icon icon13 = new ImageIcon("data\\clock13.gif");
    private JLabel label = null;
    int hora = 1;

	protected ClockPanel()
	{
		label = new JLabel(icon01);
		this.add(label);
	}
	
	protected void changeClock(int custoAcumulado)
	{
		if(custoAcumulado/(60*hora)>=1)
		{
			atualizaClock();
		}
	}
	
	protected void atualizaClock()
	{		
		switch(hora)
		{
		case(1):
		{
			label.setIcon(icon02);
			this.repaint();
			hora++;
			break;
		}
		case(2):
		{
			label.setIcon(icon03);
			this.repaint();
			hora++;
			break;
		}
		case(3):
		{
			label.setIcon(icon04);
			this.repaint();
			hora++;
			break;
		}
		case(4):
		{
			label.setIcon(icon05);
			this.repaint();
			hora++;
			break;
		}
		case(5):
		{
			label.setIcon(icon06);
			this.repaint();
			hora++;
			break;
		}
		case(6):
		{
			label.setIcon(icon07);
			this.repaint();
			hora++;
			break;
		}
		case(7):
		{
			label.setIcon(icon08);
			this.repaint();
			hora++;
			break;
		}
		case(8):
		{
			label.setIcon(icon09);
			this.repaint();
			hora++;
			break;
		}
		case(9):
		{
			label.setIcon(icon10);
			this.repaint();
			hora++;
			break;
		}
		case(10):
		{
			label.setIcon(icon11);
			this.repaint();
			hora++;
			break;
		}
		case(11):
		{
			label.setIcon(icon12);
			this.repaint();
			hora++;
			break;
		}
		case(12):
		{
			label.setIcon(icon13);
			this.repaint();
			hora++;
			break;
		}
		case(13):
		{
			break;
		}
		}
	}

}
