package com.IA.T1.Main;

import com.IA.T1.Agente.Agente;
import com.IA.T1.Cavaleiros.Cavaleiros;
import com.IA.T1.Interface.InterfaceManager;
import com.IA.T1.Mapa.Casas;
import com.IA.T1.Mapa.Terreno;

public class Main 
{
	static private Terreno			pTerreno	= null;
	static private Agente			pAgente		= null;
	static private InterfaceManager pInterface = null;
	
	final static public int[] STARTING_POINT = new int[] {37,37};
	
	public static void main(String [ ] args)
	{
		pTerreno = Terreno.getTerreno();
		pAgente = Agente.getAgente();
		
		pInterface = InterfaceManager.getInterfaceManager("data\\Terreno.png", "data\\Cav.gif", 
				STARTING_POINT, pTerreno.getMatrizClone(), pAgente.getCaminhoAgente());
	}
}
