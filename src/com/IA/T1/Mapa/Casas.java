package com.IA.T1.Mapa;

import java.io.IOException;
import java.util.ArrayList;
import com.IA.T1.Cavaleiros.Cavaleiros;
import com.IA.T1.SupportClasses.ArchiveManager;

public class Casas 
{
	//----------VARIÁVEIS----------//
	static private Casas 		pCasas 		= null;
	static private Cavaleiros	pCavaleiros	= null;
	
	private int[][] 		coordCasas			= null;	//Vetor com as coordenadas x,y das casas na matriz
	private float[] 		valoresCasas		= null;	//Vetor com valores das casas
	
	final int 	NUMERO_CASAS	= 12;
	
	//----------CONSTRUTOR----------//
	private Casas()
	{
		pCavaleiros = Cavaleiros.getCavaleiros();
		
		if(valoresCasas == null)
		{
			valoresCasas	= new float[12];
			coordCasas 		= new int[12][2];
			ArrayList<String> data = null;
			try
			{
				data = leArquivoCasas();
				this.inicializaCasas(data);
				valoresCasas = pCavaleiros.getValoresCasas(this.valoresCasas);
			} 
			catch (IOException e)
			{
				System.out.printf("Erro na inicialização das Casas\n");
			}
		}
	}
	
	//----------MÉTODOS PÚBLICOS----------//
	static public Casas getCasas() 
	{
		if (pCasas == null){
			pCasas = new Casas();
			return pCasas;
		}
		
		else {
			return pCasas;
		}		
	}
	
	public float[] getValoresCasas()
	{
		return this.valoresCasas.clone();
	}
	
	public int[] getCoordCasa(int idx)
	{
		return this.coordCasas[idx];
	}
	
	public int[][] getCoordCasas()
	{
		return this.coordCasas.clone();
	}
	
	public int getNumeroCasas()
	{
		return this.NUMERO_CASAS;
	}
	
	//----------MÉTODOS PRIVADOS----------//
	private void inicializaCasas(ArrayList<String> data)
	{		
		//Inicializando variáveis auxiliares
		int idxCoordenadas=0;
		int idxDificuldade=0;
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
				//...pega e armazena o valor de dificuldade da casa no vetor dificuldadeCasas[]...
				this.valoresCasas[idxDificuldade] = Integer.parseInt(stringData.substring(1));
				idxDificuldade++;
			}
			//Se ela for de coordenadas...
			else if(stringData.startsWith("(")==true)
			{
				//...separando as coordenadas i e j em índices diferentes do vetor 'coordenadas[]'...
				String coordenadas[] = stringData.split(";");
				//...retirando os '(' e ')' de cada um...
				coordenadas[0]=coordenadas[0].substring(1);
				coordenadas[1]=coordenadas[1].substring(0,coordenadas[1].length()-1);
				
				//...armazenando os valores das coordenadas...			
				int coordX = Integer.parseInt(coordenadas[0]);
				int coordY = Integer.parseInt(coordenadas[1]);
				
				//Armazenando as coordenadas das casas no vetor de coordenadas
				this.coordCasas[idxCoordenadas][0] = coordX;
				this.coordCasas[idxCoordenadas][1] = coordY;
				idxCoordenadas++;
			}
			//Se ela for de fechamento de bloco...
			else if(stringData.startsWith("}"))
			{}
			
			//Atualizando variável de iteração
			k++;
		}
	}
	
	static private ArrayList<String> leArquivoCasas() throws IOException
	{		
		//Importando as leituras do arquivo texto de valores das casas
		ArchiveManager archiveManager = new ArchiveManager("data\\ValoresCasas.txt");
		final ArrayList<String> data = archiveManager.openArchive();
		return data;
	}

}
