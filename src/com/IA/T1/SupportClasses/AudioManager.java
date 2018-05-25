package com.IA.T1.SupportClasses;

import javax.sound.sampled.*;
import java.io.*;

public class AudioManager 
{
	int total, totalToRead, numBytesRead, numBytesToRead;
	byte[] buffer;
	boolean         stopped;
	AudioFormat     wav;
	TargetDataLine  line;
	SourceDataLine  lineIn;
	DataLine.Info   info;
	File            file;
	FileInputStream fis;
	String			filename;
	int				numTimesLoop = 5;

	public AudioManager(String filename){

		this.filename = filename;

		//AudioFormat(float sampleRate, int sampleSizeInBits, 
		//int channels, boolean signed, boolean bigEndian) 
		wav = new AudioFormat(44100, 16, 2, true, false);
		info = new DataLine.Info(SourceDataLine.class, wav);


		buffer = new byte[1024*333];
		numBytesToRead = 1024*333;
		total=0;
		stopped = false;

		if (!AudioSystem.isLineSupported(info)) {
			System.out.print("no support for " + wav.toString() );
		}
		try {
			// Obtain and open the line.
			openLine();	

		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
		} catch (FileNotFoundException nofile) {
			nofile.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	private void openLine() throws LineUnavailableException, IOException
	{
		lineIn = (SourceDataLine) AudioSystem.getLine(info);
		lineIn.open(wav);
		lineIn.start();
		fis = new FileInputStream(file = new File(filename));
		totalToRead = fis.available();

		while (total < totalToRead && !stopped){
			numBytesRead = fis.read(buffer, 0, numBytesToRead);
			if (numBytesRead == -1) break;
			total += numBytesRead;
			lineIn.write(buffer, 0, numBytesRead);
		}		
		stopped = false;
	}
}
