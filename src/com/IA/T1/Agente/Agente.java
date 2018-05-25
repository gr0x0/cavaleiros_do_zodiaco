package com.IA.T1.Agente;

import java.util.ArrayList;
import java.util.Iterator;

import com.IA.T1.Mapa.Terreno;

public class Agente 
{
	//----------VARIÁVEIS----------//
	static private 	Agente 		pAgente 	= null;
	
	private BuscaHeuristicaAEstrela_Agente			arvoreBusca		= null;
	private ArrayList<int[]>						caminhoAgente	= null;	
	
	//----------CONSTRUTOR----------//
	private Agente()
	{								
		//Inicializa a arvore de busca heurística
		arvoreBusca = new BuscaHeuristicaAEstrela_Agente(Terreno.getTerreno());
		arvoreBusca.criaArvoreBusca();
		caminhoAgente = arvoreBusca.getCaminhoAgente();
//		/*TEST CODE*/this.printCaminhoAgente();
	}
	
	//----------MÉTODOS PÚBLICOS----------//
	static public Agente getAgente() 
	{
		if (pAgente == null){
			pAgente = new Agente();
			return pAgente;
		}
		
		else {
			return pAgente;
		}
	}
	
	public ArrayList<int[]> getCaminhoAgente()
	{
		if(this.caminhoAgente!=null)
			return this.caminhoAgente;
		else
			return null;
	}
	
	/*TEST CODE*/private void printCaminhoAgente()
	/*TEST CODE*/{
		/*TEST CODE*/Iterator<int[]> i = caminhoAgente.iterator();
		/*TEST CODE*/while(i.hasNext()!=false)
		/*TEST CODE*/{
			/*TEST CODE*/int[] coords = i.next();
			/*TEST CODE*/System.out.print("["+coords[0]+", "+coords[1]+"] ");			
		/*TEST CODE*/}
	/*TEST CODE*/}

}
