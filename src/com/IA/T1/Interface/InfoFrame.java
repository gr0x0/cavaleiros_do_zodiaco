package com.IA.T1.Interface;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JRootPane;

class InfoFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	final protected int InfoFrameSizeX = 400;
	final protected int InfoFrameSizeY = 646;
	
	private GoButton	goButton	= null;
	private ClockPanel	clockPanel	= null;
	private InfoPanel	infoPanel	= null;
	
	protected InfoFrame()
	{					
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		this.setBounds(750, 40, 500, InfoFrameSizeY);
		this.setUndecorated(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		this.setLayout(new BorderLayout());
		this.setVisible(true);	
		
		goButton = new GoButton();
		this.getContentPane().add(goButton, BorderLayout.NORTH);
		clockPanel = new ClockPanel();
		this.getContentPane().add(clockPanel, BorderLayout.CENTER);
		infoPanel = new InfoPanel();
		this.getContentPane().add(infoPanel, BorderLayout.SOUTH);

		this.validate();		
	}
	
	protected void repaintCustoAcumulado(int custoAcumulado)
	{
		this.infoPanel.repaintCustoAcumulado(custoAcumulado);
	}
	
	protected void changeClock(int custoAcumulado)
	{
		this.clockPanel.changeClock(custoAcumulado);
		this.repaint();
	}
}
