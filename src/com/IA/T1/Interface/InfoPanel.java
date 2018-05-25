package com.IA.T1.Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class InfoPanel extends JPanel
{
	JLabel label;
	int custoAcumulado;
	
	protected InfoPanel()
	{
		this.setLayout(new BorderLayout());
		Border border = BorderFactory.createLineBorder(Color.BLACK, 3) ;
		JLabel labelText = new JLabel("-CUSTO ACUMULADO-",JLabel.CENTER);
		label = new JLabel("-",JLabel.CENTER);
		label.setBackground(Color.WHITE);
		label.setBorder(border);
		this.add(labelText, BorderLayout.NORTH);
		this.add(label, BorderLayout.CENTER);		
	}
	
	protected void repaintCustoAcumulado(int custoAcumulado)
	{
		this.custoAcumulado = custoAcumulado;
		this.label.setText(Integer.toString(this.custoAcumulado));
	}

}
