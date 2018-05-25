package com.IA.T1.Agente;

import java.util.ArrayList;
import java.util.Iterator;

import com.IA.T1.Mapa.Terreno;

public class BuscaHeuristicaAEstrela_Agente
{
	private static Terreno			pTerreno		= null;
	private static No				noRaiz			= null;
	private static No			 	noAtual			= null;
	private static ArrayList<No>	folhas			= null;
	private static boolean[][]		coordExpandidas	= new boolean[42][42];
	private static boolean[][]		coordVisitadas	= new boolean[42][42];
	private static ArrayList<int[]>	caminho			= null;
	
	final int 	TEMPO_MAXIMO	= 12*60;
	
	protected BuscaHeuristicaAEstrela_Agente(Terreno pTerreno)
	{			
		BuscaHeuristicaAEstrela_Agente.pTerreno = pTerreno;
		
		noRaiz = new No(pTerreno.getCoordIniciais(), null);
		
		noAtual = noRaiz;
		coordVisitadas[noAtual.getCoordI()][noAtual.getCoordJ()] = true;
		this.inicializaCoordVisitadas();
		
		folhas = new ArrayList<No>();
		folhas.add(noRaiz);
		coordExpandidas[noRaiz.getCoordI()][noRaiz.getCoordJ()] = true;
	}
	
	protected void criaArvoreBusca()
	{
//		/*TEST PRINT*/System.out.print("criaArvoreBusca iniciada\n");
		No noFim = new No(pTerreno.getCoordFinais(), null);
		while(noAtual.equals(noFim)!=true && noAtual.getCustoAcumulado()<this.TEMPO_MAXIMO)
		{
			noAtual = this.encontraProxFolha();
			this.expandeNoAtual();
//			/*TEST PRINT*/System.out.print("noAtual = "+noAtual.getCoordI()+", "+noAtual.getCoordJ()+", "+noAtual.getCustoAcumulado()+"\n");
		}
		/*TEST PRINT*/System.out.print("noFinal = "+noAtual.getCoordI()+", "+noAtual.getCoordJ()+", "+noAtual.getCustoAcumulado()+"\n");
		
		caminho = new ArrayList<int[]>();
		while(noAtual.getNoPai()!=null)
		{
			caminho.add(noAtual.getCoords());
			noAtual = noAtual.getNoPai();
		}
	}
	
	protected ArrayList<int[]> getCaminhoAgente()
	{
		if(caminho != null)
			return caminho;
		else
			return null;
	}
	
	private void expandeNoAtual()
	{				
		No noSul, noNorte, noLeste, noOeste;
		
		if(noAtual.getCoordI()!=(pTerreno.getCoordIMax()-1) &&
				coordVisitadas[noAtual.getCoordI()+1][noAtual.getCoordJ()]==false)
		{
			int[] coords = new int[]{noAtual.getCoordI()+1,noAtual.getCoordJ()};
			noSul = new No(coords, noAtual);
			
			if(coordExpandidas[noSul.getCoordI()][noSul.getCoordJ()] == false)
			{
				folhas.add(noSul);
				coordExpandidas[noSul.getCoordI()][noSul.getCoordJ()] = true;
			}
			else
			{
				int idx = folhas.indexOf(noSul);
				No noAux = folhas.get(idx);
				if(noSul.getCustoAcumulado() < noAux.getCustoAcumulado())
					folhas.set(idx,noAux);
			}
//			/*TEST PRINT*/System.out.print("   noSul = "+noSul.getCoordI()+","+noSul.getCoordJ()+"\n");
		}
		if(noAtual.getCoordI()!=0 && 
				coordVisitadas[noAtual.getCoordI()-1][noAtual.getCoordJ()]==false)
		{
			int[] coords = new int[]{noAtual.getCoordI()-1,noAtual.getCoordJ()};
			noNorte = new No(coords, noAtual);
			
			if(coordExpandidas[noNorte.getCoordI()][noNorte.getCoordJ()] == false)
			{
				folhas.add(noNorte);
				coordExpandidas[noNorte.getCoordI()][noNorte.getCoordJ()] = true;
			}
			else
			{
				int idx = folhas.indexOf(noNorte);
				No noAux = folhas.get(idx);
				if(noNorte.getCustoAcumulado() < noAux.getCustoAcumulado())
					folhas.set(idx,noAux);
			}
//			/*TEST PRINT*/System.out.print("   noNorte = "+noNorte.getCoordI()+","+noNorte.getCoordJ()+"\n");
		}
		if(noAtual.getCoordJ()!=(pTerreno.getCoordJMax()-1) && 
				coordVisitadas[noAtual.getCoordI()][noAtual.getCoordJ()+1]==false)
		{
			int[] coords = new int[]{noAtual.getCoordI(),noAtual.getCoordJ()+1};
			noLeste = new No(coords, noAtual);

			if(coordExpandidas[noLeste.getCoordI()][noLeste.getCoordJ()] == false)
			{
				folhas.add(noLeste);
				coordExpandidas[noLeste.getCoordI()][noLeste.getCoordJ()] = true;
			}
			else
			{
				int idx = folhas.indexOf(noLeste);
				No noAux = folhas.get(idx);
				if(noLeste.getCustoAcumulado() < noAux.getCustoAcumulado())
					folhas.set(idx,noAux);
			}
//			/*TEST PRINT*/System.out.print("   noLeste = "+noLeste.getCoordI()+","+noLeste.getCoordJ()+"\n");
		}
		if(noAtual.getCoordJ()!=0 && 
				coordVisitadas[noAtual.getCoordI()][noAtual.getCoordJ()-1]==false)
		{
			int[] coords = new int[]{noAtual.getCoordI(),noAtual.getCoordJ()-1};
			noOeste = new No(coords, noAtual);

			if(coordExpandidas[noOeste.getCoordI()][noOeste.getCoordJ()] == false)
			{
				folhas.add(noOeste);
				coordExpandidas[noOeste.getCoordI()][noOeste.getCoordJ()] = true;
			}
			else
			{
				int idx = folhas.indexOf(noOeste);
				No noAux = folhas.get(idx);
				if(noOeste.getCustoAcumulado() < noAux.getCustoAcumulado())
					folhas.set(idx,noAux);
			}
//			/*TEST PRINT*/System.out.print("   noOeste = "+noOeste.getCoordI()+","+noOeste.getCoordJ()+"\n");
		}		
	}
	
	private No encontraProxFolha()
	{
		No no = folhas.get(0);
		No noAux;
		Iterator<No> i = folhas.iterator();
		
		while(i.hasNext())
		{
			noAux = i.next();
			if(no.getCustoHeuristico() > noAux.getCustoHeuristico())
				no = noAux;
		}
		
		coordVisitadas[no.getCoordI()][no.getCoordJ()]=true;
		folhas.remove(noAtual);
		return no;
	}
	
	private void inicializaCoordVisitadas()
	{
		for(int i=0; i<42; i++)
		{
			for(int j=0; j<42; j++)
			{
				if(pTerreno.getCusto(i, j)==200)
					coordVisitadas[i][j]=true;
			}
		}
	}
}

class No
{
	private int[]			coordenadas = new int[2];
	private int				custoAcumulado;
	private double			custoHeuristico;
	private No				noPai = null;
	private ArrayList<No>	noFilhos = new ArrayList<No>(4);
	
	protected No(int[] coords, No noPai)
	{
		this.coordenadas = coords;
		this.noPai = noPai;
		if(this.noPai!=null)
		{
				this.noPai.addFilhos(this);
				this.custoAcumulado = this.getNoPai().custoAcumulado + 
				Terreno.getTerreno().getCusto(this.coordenadas[0], this.coordenadas[1]);
		}
		else
			custoAcumulado = 0;
		
		this.custoHeuristico = this.funcaoHeuristica();
//		/*TEST PRINT*/System.out.printf("NOVO NO: coords = "+this.coordenadas[0]+","+this.coordenadas[1]+
//				"; custo heuristico = "+this.custoHeuristico+"\n");
	}
	
	protected double funcaoHeuristica()
	{
		int[] coordFinais = Terreno.getTerreno().getCoordFinais();
		
		return (Math.sqrt(
					Math.pow((coordFinais[0]-this.coordenadas[0]), 2)+
					Math.pow((coordFinais[1]-this.coordenadas[1]), 2)
				) +
				this.custoAcumulado);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		No no = (No) obj;
		if(this.coordenadas[0] == no.coordenadas[0] && this.coordenadas[1] == no.coordenadas[1])
			return true;
		else return false;
	}
	
	protected void addFilhos(No noFilho)
	{
		this.noFilhos.add(noFilho);
	}
	
	protected int[] getCoords()
	{
		return this.coordenadas;
	}
	
	protected int getCoordI()
	{
		return this.coordenadas[0];
	}
	
	protected int getCoordJ()
	{
		return this.coordenadas[1];
	}
	
	protected int getCustoAcumulado()
	{
		return this.custoAcumulado;
	}
	
	protected double getCustoHeuristico()
	{
		return this.custoHeuristico;
	}
	
	protected No getNoPai()
	{
		return this.noPai;
	}
}
