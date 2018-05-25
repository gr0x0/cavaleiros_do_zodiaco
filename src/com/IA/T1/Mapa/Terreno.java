package com.IA.T1.Mapa;

import java.io.IOException;
import java.util.ArrayList;

import com.IA.T1.Agente.Agente;
import com.IA.T1.Cavaleiros.Cavaleiros;
import com.IA.T1.SupportClasses.ArchiveManager;

public class Terreno 
{
	//----------VARIÁVEIS----------//
	static private Terreno				pTerreno		= null;
	static private Casas				pCasas			= null;
	static private int[][] 				matrizTerreno	= null;

	final static int	COORD_I_MAX		= 42;
	final static int	COORD_J_MAX		= 42;
	final static int[]	COORD_FINAIS 	= new int[] {4,37};
	final static int[]	COORD_INICIAIS	= new int[] {37,37};
	
	//----------CONSTRUTOR----------//
	private Terreno()
	{		
		pCasas = Casas.getCasas();
		
		if(matrizTerreno == null)
		{
			matrizTerreno = new int[Terreno.COORD_I_MAX][Terreno.COORD_J_MAX];
			ArrayList<String> data = null;
			try
			{
				data = leArquivoTerreno();
				Terreno.inicializaMatriz(data);
				/*TEST CODE*/Terreno.printMatriz();
			} 
			catch (IOException e)
			{
				System.out.printf("Erro na inicialização da Matriz\n");
			}
		}
	}
	
	//----------MÉTODOS PÚBLICOS----------//
	static public Terreno getTerreno() 
	{
		if (pTerreno == null){
			pTerreno = new Terreno();
			return pTerreno;
		}
		
		else {
			return pTerreno;
		}		
	}
	
	static public int[][] getMatrizClone()
	{
		return Terreno.matrizTerreno.clone();
	}
	
	public int getCusto(int i, int j)
	{
		return Terreno.matrizTerreno[i][j];
	}
	
	public int getCoordIMax()
	{
		return Terreno.COORD_I_MAX;
	}
	
	public int getCoordJMax()
	{
		return Terreno.COORD_J_MAX;
	}
	
	public int[] getCoordIniciais()
	{
		return Terreno.COORD_INICIAIS;
	}
	
	public int[] getCoordFinais()
	{
		return Terreno.COORD_FINAIS;
	}
	
	//----------MÉTODOS PRIVADOS----------//
	static private void inicializaMatriz(ArrayList<String> data)
	{		
		//Inicializando variáveis auxiliares
		int valor=0;
		int k=0;
		
		//Loop de preenchimento da matriz
		while(k < data.size())
		{
			//Pegando uma linha do arquivo texto
			String stringData = data.get(k).toString();
//			/*TEST PRINT*/System.out.printf(stringData+"\n");
			
			//Se ela for abertura de bloco...
			if(stringData.startsWith("{")==true)
			{
				//...pega e armazena o valor a ser inserido nas coordenadas seguintes
				valor = Integer.parseInt(stringData.substring(1));
//				/*TEST PRINT*/System.out.printf(valor+"\n");
			}
			//Se ela for de coordenadas...
			else if(stringData.startsWith("(")==true)
			{
				//...separando as coordenadas i e j em índices diferentes do vetor 'coordenadas[]'...
				String coordenadas[] = stringData.split(";");
//				/*TEST PRINT*/System.out.printf(coordenadas[0]+"\n");
//				/*TEST PRINT*/System.out.printf(coordenadas[1]+"\n");
				//...retirando os '(' e ')' de cada um...
				coordenadas[0]=coordenadas[0].substring(1);
				coordenadas[1]=coordenadas[1].substring(0,coordenadas[1].length()-1);
				
				//...inicializando vetores que conterão todas as coordenadas i e j a serem populadas...
				int[] coordX, coordY;
				
				//...pegando as coordenadas i caso sejam uma sequência...
				if(coordenadas[0].contains("~")==true)
				{
					String limites[] = coordenadas[0].split("~");
					coordX = new int[Integer.parseInt(limites[1]) - Integer.parseInt(limites[0])+1];
					for(int i=0; i<coordX.length; i++)
					{
						coordX[i]=Integer.parseInt(limites[0])+i;
					}					
				}
				//...ou se as coordenadas i forem um conjunto não sequencial...
				else if(coordenadas[0].contains(",")==true)
				{
					String conjunto[] = coordenadas[0].split(",");
					coordX = new int[conjunto.length];
					for(int i=0; i<coordX.length; i++)
					{
						coordX[i]=Integer.parseInt(conjunto[i]);
					}
				}
				//...ou se a coordenada i for apenas um ponto...
				else
				{
					coordX = new int[]{Integer.parseInt(coordenadas[0])};
				}
				//...agora pegando as coordenadas j caso sejam uma sequência...
				if(coordenadas[1].contains("~")==true)
				{
					String limites[] = coordenadas[1].split("~");
					coordY = new int[Integer.parseInt(limites[1]) - Integer.parseInt(limites[0])+1];
					for(int i=0; i<coordY.length; i++)
					{
						coordY[i]=Integer.parseInt(limites[0])+i;
					}					
				}
				//...ou se as coordenadas j forem um conjunto não sequencial...
				else if(coordenadas[1].contains(",")==true)
				{
					String conjunto[] = coordenadas[1].split(",");
					coordY = new int[conjunto.length];
					for(int i=0; i<coordY.length; i++)
					{
						coordY[i]=Integer.parseInt(conjunto[i]);
					}
				}
				//...ou se a a coordenada j for apenas um ponto...
				else
				{
					coordY = new int[]{Integer.parseInt(coordenadas[1])};
				}
				
				//Populando a matriz com os valores
				for(int i=0; i<coordX.length; i++)
				{
					for(int j=0; j<coordY.length; j++)
					{
						Terreno.matrizTerreno[coordX[i]][coordY[j]] = valor;
					}
				}
			}
			//Se ela for de fechamento de bloco...
			else if(stringData.startsWith("}"))
			{}	
			k++;
		}
		
		//Inserindo os valores das casas na matriz
		Terreno.inicializaCasas();
	}
	
	static private void inicializaCasas()
	{
		int[] casa = new int[2];
		float[] valoresCasas = pCasas.getValoresCasas();
		
		for(int idx=0; idx<12; idx++)
		{
			casa = Terreno.pCasas.getCoordCasa(idx);
			Terreno.matrizTerreno[casa[0]][casa[1]] = (int) valoresCasas[idx];
		}
	}
	
	static private ArrayList<String> leArquivoTerreno() throws IOException
	{		
		//Importando as leituras do arquivo texto de valores da matriz
		ArchiveManager archiveManager = new ArchiveManager("data\\ValoresTerreno.txt");
		final ArrayList<String> data = archiveManager.openArchive();
		return data;
	}
	
	/*FUNÇÃO DE TESTE*/
	static void printMatriz()
	{
		for(int i=0; i<Terreno.COORD_I_MAX; i++)
		{
			for(int j=0; j<Terreno.COORD_J_MAX; j++)
			{
				if(matrizTerreno[i][j]!=200)
					System.out.printf("%03d ",matrizTerreno[i][j]);
				else System.out.print("=== ");
			}
			System.out.print("\n");
		}
	}	
}
