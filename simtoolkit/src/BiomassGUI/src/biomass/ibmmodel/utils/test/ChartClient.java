package biomass.ibmmodel.utils.test;

import java.io.*;
import java.net.*;

import biomass.ibmmodel.core.PopulationData;

public class ChartClient
{
	Socket client;
	ObjectOutputStream oos;
	
	
	public ChartClient() {
		try{
			client = new Socket("10.3.0.208", 6788);
			oos = new ObjectOutputStream(client.getOutputStream());		
		}
		catch(IOException ioException) {
			ioException.printStackTrace();
		}	
	}
	
	public void sendPopulatioData(PopulationData p) {
		try{
			//System.out.println("Pasos="+p.step);
			oos.writeObject(p);
			oos.flush();	
		}
		catch(IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
	public void close() {
		try {
			oos.close();
			client.close();
		} 
		catch(IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
 
}