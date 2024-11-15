package biomass.ibmmodel.utils.test;

import java.io.*;
import java.net.*;

import biomass.ibmmodel.core.PopulationData;
import biomass.simulator.core.ChartGraph;

class ChartServer {
	ObjectInputStream in;
	ChartGraph cg;
	ServerSocket s;

	public ChartServer() {
		try{
			s = new ServerSocket(6789);
		}
		catch(IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public void init() {
		try{    
			Socket connection = s.accept();
			in=new ObjectInputStream(connection.getInputStream());
			cg=new ChartGraph((String)in.readObject());

			while(true)
			{
				PopulationData p = (PopulationData)in.readObject();
				cg.updatePopulation(p.step, p.population);
				cg.updateStarved(p.step, p.starved);
				cg.updateEaten(p.step, p.eaten);
				cg.updateLeanmass(p.step, p.leanmass);
				cg.updateFatmass(p.step, p.fatmass);
				cg.updateStarvedmass(p.step, p.starvedmass);
				cg.updateEatenmass(p.step, p.eatenmass);
				cg.updateWasteLeanmass(p.step, p.wasteleanmass);
				cg.updateWasteFatmass(p.step, p.wastefatmass);
			}
		}
		catch(IOException ioException) {
			ioException.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			try {
				in.close();
				s.close();
			} 
			catch(IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

	public static void main(String argv[]) {
		ChartServer cs=new ChartServer();
		cs.init();
	}
}

