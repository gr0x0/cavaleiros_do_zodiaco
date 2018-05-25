package com.IA.T1.Cavaleiros;

import java.util.ArrayList;
import java.util.Iterator;

public class BuscaHeuristicaAEstrela_Cavaleiros
{
	private static No							noRaiz					= null;
	private static No			 				noAtual					= null;
	private static ArrayList<No>				folhas					= null;
	private static float[]						valoresCasasOriginais 	= null;
	private static float[]						valoresCasasFim			= null;
	
	private ArrayList<CavaleiroDeBronze>	listaCavaleiros		= null;
	private int 							nivelArvoreAtual 	= 0;
	private int								nivelMaxArvore 		= 0;
	
	protected BuscaHeuristicaAEstrela_Cavaleiros(ArrayList<CavaleiroDeBronze> listaCavaleiros, float[] valoresCasasOriginais)
	{				
		this.listaCavaleiros = listaCavaleiros;
		BuscaHeuristicaAEstrela_Cavaleiros.valoresCasasOriginais = valoresCasasOriginais;
		BuscaHeuristicaAEstrela_Cavaleiros.valoresCasasFim = valoresCasasOriginais.clone();
		
		float[] divisores = new float[12];
		for(int i=0; i<12; i++)
		{
			divisores[i] = 1;
		}
		noRaiz = new No(divisores, 
				null, 
				listaCavaleiros.get(0).getNome(),
				listaCavaleiros.get(0).getPoderCosmico(),
				listaCavaleiros.get(0).getPontosEnergia());
		
		noAtual = noRaiz;
		
		folhas = new ArrayList<No>();
		folhas.add(noRaiz);
		
		int maiorPE = this.listaCavaleiros.get(0).getPontosEnergia();
		for(int i=0; i<this.listaCavaleiros.size(); i++)
		{
			if(maiorPE<this.listaCavaleiros.get(i).getPontosEnergia())
				maiorPE = this.listaCavaleiros.get(i).getPontosEnergia();
		}
		this.nivelMaxArvore = this.listaCavaleiros.size()*maiorPE;
	}
	
	protected void criaArvoreBusca()
	{
		while(this.nivelArvoreAtual < this.nivelMaxArvore)
		{
			noAtual = this.encontraProxFolha();
			this.expandeNoAtual();
			/*TEST PRINT*/System.out.print("noAtual = "+noAtual.getNomeCavaleiro()+", PEs = "+noAtual.getPontosEnergia()+", Nivel na Arvore = "+noAtual.getNivelNo()+", Custo Acumulado = "+noAtual.getCustoAcumulado()+"\n");
			/*TEST CODE*/float[] div = noAtual.getDivisores();
			/*TEST CODE*/System.out.print("divs = ");
			/*TEST CODE*/for(int i=0; i<12; i++)
			/*TEST CODE*/{
				/*TEST PRINT*/System.out.print(+div[i]+", ");
			/*TEST CODE*/}System.out.print("\n");

		}
		
		noAtual = this.encontraProxFolha();
		float[] divisores = noAtual.getDivisores();
		/*TEST PRINT*/System.out.print("valores finais:\n");
		for(int i=0; i<12; i++)
		{
			valoresCasasFim[i] = valoresCasasOriginais[i]/divisores[i];
			/*TEST PRINT*/System.out.print(+valoresCasasOriginais[i]+"/"+divisores[i]+"="+valoresCasasFim[i]+"\n");
		}
	}
	
	protected static float[] getValoresCasasFim()
	{
		if(valoresCasasFim != null)
			return valoresCasasFim;
		else
			return null;
	}
	
	protected static float[] getValoresCasasOriginais()
	{
		return valoresCasasOriginais;
	}
	
	private No encontraProxFolha()
	{
		Iterator<No> i = folhas.iterator();
		No no = folhas.get(0);
		No noAux;
		
		while(i.hasNext())
		{
			noAux = i.next();
			if(no.getCustoHeuristico() > noAux.getCustoHeuristico())
				no = noAux;
		}
		return no;
	}
	
	private void expandeNoAtual()
	{		
		//Se o cavaleiro do nó atual ainda possui mais de 1 PE restante
		if(noAtual.getPontosEnergia()>1)
		{
			for(int i=0; i<12; i++)
			{
				float[] divisores = noAtual.getDivisores();				
				
				if(divisores[i]==1)
				{
					divisores[i] = noAtual.getPoderCosmico();
					No novoNo = new No(divisores, noAtual, noAtual.getNomeCavaleiro(), 
							noAtual.getPoderCosmico(), noAtual.getPontosEnergia()-1);
					noAtual.addFilhos(novoNo);
					folhas.add(novoNo);
				}
				else
				{
					float maxDivisor = 0;
					No noAux = noAtual;
					while(noAux.getNoPai()!=null)
					{
						if(noAux.getNomeCavaleiro().contentEquals(noAux.getNoPai().getNomeCavaleiro())==false)
							maxDivisor += noAux.getPoderCosmico();
						noAux = noAux.getNoPai();
					}
					
					if(divisores[i]<maxDivisor)
					{
						divisores[i] = divisores[i] + noAtual.getPoderCosmico();
						No novoNo = new No(divisores, noAtual, noAtual.getNomeCavaleiro(), 
								noAtual.getPoderCosmico(), noAtual.getPontosEnergia()-1);
						noAtual.addFilhos(novoNo);
						folhas.add(novoNo);
					}
				}
			}	
		}
		//Se o cavaleiro do nó atual possui apenas 1 PE restante
		if(noAtual.getPontosEnergia()==1)
		{
			for(int i=0; i<12; i++)
			{
				float[] divisores = noAtual.getDivisores();

				if(divisores[i]==1)
				{
					divisores[i] = noAtual.getPoderCosmico();
					No novoNo = new No(divisores, noAtual, noAtual.getNomeCavaleiro(), 
							noAtual.getPoderCosmico(), noAtual.getPontosEnergia()-1);
					noAtual.addFilhos(novoNo);
					folhas.add(novoNo);
					this.nivelArvoreAtual = novoNo.getNivelNo();
				}
				else
				{
					float maxDivisor = 0;
					No noAux = noAtual;
					while(noAux.getNoPai()!=null)
					{
						if(noAux.getNomeCavaleiro().contentEquals(noAux.getNoPai().getNomeCavaleiro())==false)
							maxDivisor += noAux.getPoderCosmico();
						noAux = noAux.getNoPai();
					}
					
					if(divisores[i]<maxDivisor)
					{
						divisores[i] = divisores[i] + noAtual.getPoderCosmico();
						No novoNo = new No(divisores, noAtual, noAtual.getNomeCavaleiro(), 
								noAtual.getPoderCosmico(), noAtual.getPontosEnergia()-1);
						noAtual.addFilhos(novoNo);
						folhas.add(novoNo);
					}
				}				
			}
		}
		//Se o cavaleiro do nó atual não possui mais PEs
		if(noAtual.getPontosEnergia()==0)
		{
			CavaleiroDeBronze novoCav;
			if((int)(noAtual.getNivelNo()/5)<this.listaCavaleiros.size())
			{
				novoCav = this.listaCavaleiros.get((int)(noAtual.getNivelNo()/5));
				/*TEST PRINT*/System.out.print("novoCav = "+novoCav.getNome()+", idx = "+(int)(noAtual.getNivelNo()/5)+"\n");
			
				for(int i=0; i<12; i++)
				{
					float[] divisores = noAtual.getDivisores();
					
					if(divisores[i]==1)
					{
						divisores[i] = novoCav.getPoderCosmico();
						No novoNo = new No(divisores, noAtual, novoCav.getNome(), novoCav.getPoderCosmico(),
								novoCav.getPontosEnergia()-1);
						noAtual.addFilhos(novoNo);
						folhas.add(novoNo);
					}
					else
					{
						float maxDivisor = 0;
						No noAux = noAtual;
						while(noAux.getNoPai()!=null)
						{
							if(noAux.getNomeCavaleiro().contentEquals(noAux.getNoPai().getNomeCavaleiro())==false)
								maxDivisor += noAux.getPoderCosmico();
							noAux = noAux.getNoPai();
						}
						
						if(divisores[i]<maxDivisor)
						{
							divisores[i] = divisores[i] + novoCav.getPoderCosmico();
							No novoNo = new No(divisores, noAtual, novoCav.getNome(), 
									novoCav.getPoderCosmico(), novoCav.getPontosEnergia()-1);
							noAtual.addFilhos(novoNo);
							folhas.add(novoNo);
						}
					}	
				}
			}
		}
		folhas.remove(noAtual);
	}
}

class No
{
	private float[]			divisores;
	private String			nomeCavaleiro;
	private float			poderCosmico;
	private int				pontosEnergia;
	private float			custoAcumulado;
	private double			custoHeuristico;
	private No				noPai = null;
	private ArrayList<No>	noFilhos = new ArrayList<No>(4);
	private int				nivelNaArvore;
	
	protected No(float[] divisores, No noPai, String nomeCavaleiro, float poderCosmico,	int pontosEnergia)
	{
		this.divisores = divisores;
		this.noPai = noPai;
		this.nomeCavaleiro = nomeCavaleiro;
		this.poderCosmico = poderCosmico;
		this.pontosEnergia = pontosEnergia;
		if(noPai==null)
		{
			this.nivelNaArvore = 0;
			custoAcumulado = 0;
		}
		else
		{
			this.nivelNaArvore = noPai.getNivelNo()+1;
			this.noPai.addFilhos(this);
			this.custoAcumulado = this.getSomatorioValores();
		}
		
		this.custoHeuristico = this.funcaoHeuristica();
//		/*TEST PRINT*/System.out.printf("NOVO NO: coords = "+this.coordenadas[0]+","+this.coordenadas[1]+
//				"; custo heuristico = "+this.custoHeuristico+"\n");
	}
	
	@Override
	public boolean equals(Object obj)
	{
		No no = (No) obj;
		boolean isEqual = false;
		
		for(int i=0; i<12; i++)
		{
			if(this.divisores[i] == no.divisores[i] && 
					this.nomeCavaleiro.equalsIgnoreCase(no.getNomeCavaleiro()) == true)
				isEqual = true;
		}
		return isEqual;
	}
	
	protected double funcaoHeuristica()
	{
		return this.getSomatorioValores()/12;
	}
	
	protected void addFilhos(No noFilho)
	{
		this.noFilhos.add(noFilho);
	}
	
	protected float getSomatorioValores()
	{
		float somaValores = 0;
		float[] valCasas = BuscaHeuristicaAEstrela_Cavaleiros.getValoresCasasOriginais();
		
		for(int i=0; i<12; i++)
		{
			somaValores += valCasas[i]/this.divisores[i];
		}
		
		return somaValores;
	}
	
	protected float[] getDivisores()
	{
		return this.divisores.clone();
	}
	
	protected String getNomeCavaleiro()
	{
		return this.nomeCavaleiro;
	}
	
	protected float getPoderCosmico()
	{
		return this.poderCosmico;
	}
	
	protected int getPontosEnergia()
	{
		return this.pontosEnergia;
	}
	
	protected float getCustoAcumulado()
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
	
	protected int getNivelNo()
	{
		return this.nivelNaArvore;
	}
}
