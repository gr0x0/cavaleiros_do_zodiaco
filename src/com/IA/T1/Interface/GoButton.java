package com.IA.T1.Interface;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GoButton extends JPanel
{	
	static protected JButton button = null;
	
	protected GoButton()
	{		
		button = new JButton("GO!");
		button.setMargin(new Insets(25, 30, 25, 30));
		this.add(button);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InterfaceManager.startStopButtonPressed();
				if(GoButton.button.getText().equals("GO!"))
					GoButton.button.setText("STOP!");
				else
					GoButton.button.setText("GO!");
			}
		});  
	}
	

}
