package com.IA.T1.Interface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import com.IA.T1.Mapa.Terreno;
import com.IA.T1.SupportClasses.AudioManager;

public class InterfaceManager
{
	static private	InterfaceManager 	pInterfaceManager 	= null;
	static private	MapFrame			mapFrame 			= null;
	static private	InfoFrame			infoFrame			= null;	
	static private	AudioManager		audioManager		= null;

	static protected int MatrixColumns;
	static protected int MatrixRows;
	static protected int StringsSpacingX;
	static protected int StringsSpacingY;

	static protected String backgroundImgPath;				//Endereço da imagem do background
	static protected String agentImgPath;					//Endereço da imagem do agente

	static protected int[][] 			zonesCoordMatrix;	//Matriz de posições do agente
	static protected ArrayList<int[]>	agentCoordinates;	//Caminho completo do agente 
	static protected int[]				agentCoordAtuais;	//Coordenadas instantaneas do agente
	private			 int[]				agentCoordIniciais 	= Terreno.getTerreno().getCoordIniciais();
	static private	 int				custoAcumulado		= 0;

	private		static 	Timer 			timer 			= null;
	protected 	static	Iterator<int[]> iteratorAgente	= null;

	private		static	boolean			isIntro			= true;
	private 	static	boolean 		isRunning 		= false;


	static public InterfaceManager getInterfaceManager(String backgroundImgPath, String agentImgPath, 
			int[] agentCoordinates, int[][] matrizTerreno, ArrayList<int[]> caminhoAgente) 
	{
		if (pInterfaceManager == null){
			pInterfaceManager = new InterfaceManager(backgroundImgPath, agentImgPath, agentCoordinates, 
					matrizTerreno, caminhoAgente);
			return pInterfaceManager;
		}		
		else {
			return pInterfaceManager;
		}
	}

	static public void startStopButtonPressed()
	{
		if(isIntro == true)
		{
			MapFrame.startMap();
			isIntro = false;
			iniciarRelogio();
			isRunning = true;
		}
		else if(isRunning == true)
		{
			timer.cancel();
			timer.purge();
			isRunning = false;
		}
		else if(isRunning == false)
		{
			iniciarRelogio();
			isRunning = true;
		}
	}

	private InterfaceManager(String backgroundImgPath, String agentImgPath, int[] agentCoordinates, 
			int[][] zonesCoordMatrix, ArrayList<int[]> caminhoAgente)
	{
		InterfaceManager.zonesCoordMatrix = zonesCoordMatrix;
		InterfaceManager.agentCoordinates = caminhoAgente;
		InterfaceManager.backgroundImgPath = backgroundImgPath;
		InterfaceManager.agentImgPath = agentImgPath;

		this.fixAgentCoordinates();		
		agentCoordAtuais = new int[2];
		mapFrame = new MapFrame();
		infoFrame = new InfoFrame();

		if(InterfaceManager.zonesCoordMatrix!=null)
		{
			InterfaceManager.MatrixColumns = InterfaceManager.zonesCoordMatrix.length+1;
			InterfaceManager.MatrixRows = InterfaceManager.zonesCoordMatrix[0].length+1;
			InterfaceManager.StringsSpacingX = (int)(mapFrame.getMapSizeFrameX()/
					InterfaceManager.MatrixColumns);
			InterfaceManager.StringsSpacingY = (int)(mapFrame.getMapSizeFrameY()/
					InterfaceManager.MatrixRows);
		}

		audioManager = new AudioManager("data\\PegasusFantasy.WAV");
	}

	private static void iniciarRelogio()
	{
		timer = new Timer();

		timer.schedule(new TimerTask()
		{			
			@Override
			public void run() 
			{
				if(InterfaceManager.iteratorAgente == null)
				{
					InterfaceManager.iteratorAgente = InterfaceManager.agentCoordinates.iterator();
				}
				if(InterfaceManager.iteratorAgente.hasNext())
					InterfaceManager.setAgentCoord(InterfaceManager.iteratorAgente.next());
			}

		}, 0, 500);

	}

	private static void setAgentCoord(int[] agentCoordinates)
	{
		custoAcumulado += zonesCoordMatrix[agentCoordinates[0]][agentCoordinates[1]];
		InterfaceManager.agentCoordAtuais[0] = agentCoordinates[1]*InterfaceManager.StringsSpacingX;
		InterfaceManager.agentCoordAtuais[1] = agentCoordinates[0]*InterfaceManager.StringsSpacingY;
		notifyRepaint(agentCoordinates);
		
		infoFrame.changeClock(custoAcumulado);
	}

	private static void notifyRepaint(int[] agentCoordinates) 
	{
		mapFrame.repaint();	
		mapFrame.repaintAgente(agentCoordinates);
		infoFrame.repaintCustoAcumulado(custoAcumulado);
	}

	private void fixAgentCoordinates()
	{
		@SuppressWarnings("unchecked")
		ArrayList<int[]> agentCoordinatesClone = (ArrayList<int[]>)InterfaceManager.agentCoordinates.clone();
		Iterator<int[]> iterator = agentCoordinatesClone.iterator();		
		for(int i=InterfaceManager.agentCoordinates.size()-1; iterator.hasNext(); i--)
		{
			InterfaceManager.agentCoordinates.set(i, iterator.next());
		}
		InterfaceManager.agentCoordinates.add(0, 
				new int[]{this.agentCoordIniciais[0],this.agentCoordIniciais[1]});
	}
}
