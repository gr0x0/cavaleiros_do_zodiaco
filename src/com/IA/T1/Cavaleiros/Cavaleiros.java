package com.IA.T1.Cavaleiros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.IA.T1.Agente.BuscaHeuristicaAEstrela_Agente;
import com.IA.T1.Mapa.Casas;
import com.IA.T1.SupportClasses.ArchiveManager;

public class Cavaleiros 
{
	//----------VARIÁVEIS----------//
	static private Cavaleiros				pCavaleiros		= null;	
	private ArrayList<CavaleiroDeBronze>	listaCavaleiros	= null;
	
	private BuscaHeuristicaAEstrela_Cavaleiros	arvoreBusca		= null;
	private float[]								valoresCasasFim	= null;	
	
	final int 	NUMERO_DE_CAVALEIROS = 5;
	
	//----------CONSTRUTOR----------//
	private Cavaleiros()
	{		
		listaCavaleiros = new ArrayList<CavaleiroDeBronze>(this.NUMERO_DE_CAVALEIROS);
			
		ArrayList<String> data = null;
		try
		{
			data = leArquivoCavaleiros();
			this.inicializaCavaleiros(data);
			/*TEST CODE*/this.printCavaleiros();
		} 
		catch (IOException e)
		{
				System.out.printf("Erro na inicialização dos Cavaleiros\n");
		}
	}
	
	//----------MÉTODOS PÚBLICOS----------//
	static public Cavaleiros getCavaleiros() 
	{
		if (pCavaleiros == null){
			pCavaleiros = new Cavaleiros();
			return pCavaleiros;
		}
		
		else {
			return pCavaleiros;
		}		
	}
	
	public float[] getValoresCasas(float[] valoresCasasOriginais)
	{
		//Inicializa a arvore de busca heurística
		arvoreBusca = new BuscaHeuristicaAEstrela_Cavaleiros(this.cloneListaCavaleiros(), valoresCasasOriginais);
		arvoreBusca.criaArvoreBusca();
		valoresCasasFim = BuscaHeuristicaAEstrela_Cavaleiros.getValoresCasasFim();
		return this.valoresCasasFim;
	}

	//----------MÉTODOS PRIVADOS----------//
	private void inicializaCavaleiros(ArrayList<String> data)
	{		
		//Inicializando variáveis auxiliares
		String nome = new String();
		int pontosEnergia = 0;
		float poderCosmico = 0;
		int k=0;
		
		//Loop de preenchimento dos vetores dificuldadeCasas[] e coordCasas[]
		while(k < data.size())
		{
			//Pegando uma linha do arquivo texto
			String stringData = data.get(k).toString();
//			/*TEST PRINT*/System.out.printf(stringData+"\n");
			
			//Se ela for abertura de bloco...
			if(stringData.startsWith("{")==true)
			{
				//...pega e guarda temporariamente o valor de Pontos de Energia...
				pontosEnergia = Integer.parseInt(stringData.substring(1));
			}
			//Se ela for de Nome...
			if(stringData.startsWith("<")==true)
			{
				//...retirando os '<' e '>'...
				nome = stringData.substring(1, stringData.length()-1);		
			}
			//Se ela for de Poder Cosmico...
			else if(stringData.startsWith("(")==true)
			{
				//...retirando os '(' e ')'...
				String valor = stringData.substring(1, stringData.length()-1);				
				//...gaurdando o valor de Poder Cósmico temporariamente...			
				poderCosmico = Float.parseFloat(valor);				
			}
			//Se ela for de fechamento de bloco...
			else if(stringData.startsWith("}"))
			{
				this.listaCavaleiros.add(new CavaleiroDeBronze(nome, poderCosmico, pontosEnergia));
			}
			
			//Atualizando variável de iteração
			k++;
		}
	}
	
	static private ArrayList<String> leArquivoCavaleiros() throws IOException
	{		
		//Importando as leituras do arquivo texto de valores dos cavaleiros
		ArchiveManager archiveManager = new ArchiveManager("data\\Cavaleiros.txt");
		final ArrayList<String> data = archiveManager.openArchive();
		return data;
	}
	
	private ArrayList<CavaleiroDeBronze> cloneListaCavaleiros()
	{
		ArrayList<CavaleiroDeBronze> listaClone = new ArrayList<CavaleiroDeBronze>();
		Iterator<CavaleiroDeBronze> i = this.listaCavaleiros.iterator();
		while(i.hasNext())
		{
			listaClone.add(i.next().clone());
		}
		return listaClone;
	}
	
	/*TEST CODE*/private void printCavaleiros()
	/*TEST CODE*/{
		/*TEST CODE*/for(int i = 0; i<5; i++)
			/*TEST CODE*/{
			/*TEST CODE*/System.out.printf("Cavaleiro #"+i+": PC="+this.listaCavaleiros.get(i).getPoderCosmico()+"; PE="+this.listaCavaleiros.get(i).getPontosEnergia()+"\n");
			/*TEST CODE*/}
	/*TEST CODE*/}
}

class CavaleiroDeBronze
{
	private String					nome;
	private float					poderCosmico;
	private int						pontosEnergia;
	private ArrayList<Integer>		casasBatalhadas;
	
	protected CavaleiroDeBronze(String nome, float poderCosmico, int pontosEnergia)
	{
		this.nome = nome;
		this.poderCosmico = poderCosmico;
		this.pontosEnergia = pontosEnergia;
	}
	
	protected String getNome()
	{
		return this.nome;
	}
	
	protected float getPoderCosmico()
	{
		return this.poderCosmico;
	}
	
	protected int getPontosEnergia()
	{
		return this.pontosEnergia;
	}
	
	protected void setCasasBatalhadas(int idxCasaBatalhada)
	{
		this.casasBatalhadas.add(idxCasaBatalhada);
	}
	
	protected CavaleiroDeBronze clone()
	{
		return new CavaleiroDeBronze(this.nome, this.poderCosmico, this.pontosEnergia);
	}
}
