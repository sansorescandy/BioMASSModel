package biomass.simulator.core;

import java.awt.EventQueue;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.jfree.ui.RefineryUtilities;

import multiagent.model.agent.Agent;
import multiagent.model.agent.ReactiveAgent;

import biomass.ibmmodel.core.FunctionalGroup;
import biomass.ibmmodel.core.Organism;
import biomass.ibmmodel.core.OrganismType;
import biomass.ibmmodel.core.Plankton;
import biomass.ibmmodel.core.Population;
import biomass.ibmmodel.core.Refuge;
import biomass.ibmmodel.utils.test.ChartClient;

public class BioMASSModel {
	public Scheduler scheduler;
	private String[] organismFunctionalGroups = {"Carnivore", "Planktivore", "Plankton"};
	//Lista de depredadores del planktivore
	private String[] predatorPlanktivore = {"biomass.ibmmodel.core.Carnivore"};
	private String[] preyCarnivore = {new String("biomass.ibmmodel.core.Planktivore")};
	private String[] preyPlanktivore = {new String("biomass.ibmmodel.core.Plankton")};
	private Bag fg = new Bag();
	public Bag populations = new Bag();
	public Bag refuges = new Bag();
	private double width=19200;
	private double height=12000;
	private double depth=2000;
	private int planktonwidth=1920;
	private int planktonheight=1200;
	private int planktondepth=1;
	private double wastemass=0;
	public Space seaSpace;
	public Plankton plankton;
	private Display display;
	public MersenneTwisterFast random;
	public Random r;
	private int timestep=1;
	
	//TEMPORAL PARA PARAR CUANDO ENGORDAN
	public boolean parar=false;
	
	public BioMASSModel() {
		System.out.println("Constructor BioMASSModel");
		scheduler = new Scheduler(timestep);
		seaSpace = new Space(-width/2, -height/2, width/2, height/2);
		display=new Display(seaSpace);
		//plankton = new Plankton(planktonwidth, planktonheight, planktondepth, planktonwidth/2, planktonheight/2, planktondepth/2, );
		r=new Random(System.currentTimeMillis());
	}
	
	private void initBioMASSModel() {
		//Crea los refugios
		int id;
		for(int i=0;i<40;i++) {
			double x = r.nextDouble()*seaSpace.getWidth()-seaSpace.getWidth()/2;
			double y = r.nextDouble()*seaSpace.getHeight()-seaSpace.getHeight()/2;
			Refuge refuge=new Refuge(x,y);
			seaSpace.insert(refuge);
			refuges.add(refuge);
		}
		
		//seaSpace.setObjectLocation(new Refuge(0,0), new Double2D(0,0));
		
		//Crea los grupos funcionales tom‡ndolos ya sea de la BD o de objetos serializados, por ahora c—digo ejemplo
		for (int i = 0; i < organismFunctionalGroups.length; i++) {
			fg.add(new FunctionalGroup(organismFunctionalGroups[i]));
		}
		
		//Crea las poblaciones iniciales de cada grupo funcional y programa a los agentes en el scheduler
		for (int i = 0; i < fg.numObjs; i++) {
			//ESTO ES PROVISIONAL
			int p=0;
			if(((FunctionalGroup)fg.objs[i]).getName()=="Carnivore") {
				((FunctionalGroup)fg.objs[i]).setGroupClass("biomass.ibmmodel.core.Carnivore");
				((FunctionalGroup)fg.objs[i]).setOrganismType(OrganismType.HETEROTROPH);
				((FunctionalGroup)fg.objs[i]).setPreys(preyCarnivore);
				((FunctionalGroup)fg.objs[i]).setHideBehavior(false);
				
				//Rango de percepci—n
				((FunctionalGroup)fg.objs[i]).setPerceptionRangeFactor(100.0);
				
				((FunctionalGroup)fg.objs[i]).setMaxSpeed(5.0);
				((FunctionalGroup)fg.objs[i]).setWanderSpeed(1.0);
			/*	
				((FunctionalGroup)fg.objs[i]).setBirthLeanMass(10);
				((FunctionalGroup)fg.objs[i]).setAdultAge(1);
				((FunctionalGroup)fg.objs[i]).setAdultAgeFinalLeanMassPercentage(0.9);
				((FunctionalGroup)fg.objs[i]).setFinalLeanMass(100);*/
				
				((FunctionalGroup)fg.objs[i]).setLongevity(16*365);
				((FunctionalGroup)fg.objs[i]).reproductioncycle=365/2;
				((FunctionalGroup)fg.objs[i]).setK(0.1);
				((FunctionalGroup)fg.objs[i]).setLinf(148.0);
				((FunctionalGroup)fg.objs[i]).setLo(7.0);
				((FunctionalGroup)fg.objs[i]).setA(0.014);
				((FunctionalGroup)fg.objs[i]).setB(2.81); //Pickhandle Barracuda Sphyraena jello
				p=5;
				//populations.add(new Population(((FunctionalGroup)fg.objs[i]),p));
				
			}
			if(((FunctionalGroup)fg.objs[i]).getName()=="Planktivore") {
				((FunctionalGroup)fg.objs[i]).setGroupClass("biomass.ibmmodel.core.Planktivore");
				((FunctionalGroup)fg.objs[i]).setOrganismType(OrganismType.HETEROTROPH);
				((FunctionalGroup)fg.objs[i]).setPredators(predatorPlanktivore);
				((FunctionalGroup)fg.objs[i]).setPreys(preyPlanktivore);
				((FunctionalGroup)fg.objs[i]).setHideBehavior(true);
				
				//Rango de percepci—n
				((FunctionalGroup)fg.objs[i]).setPerceptionRangeFactor(100.0);
				
				//Refugiado
				((FunctionalGroup)fg.objs[i]).setHiddenThreshold(100.0);
				
				((FunctionalGroup)fg.objs[i]).setMaxSpeed(5.0);
				((FunctionalGroup)fg.objs[i]).setForagingSpeed(1.0);
				
				/*((FunctionalGroup)fg.objs[i]).setBirthLeanMass(1);
				((FunctionalGroup)fg.objs[i]).setAdultAge(1);*/
				/*((FunctionalGroup)fg.objs[i]).setAdultAgeFinalLeanMassPercentage(0.9);
				((FunctionalGroup)fg.objs[i]).setFinalLeanMass(10);
				*/
				((FunctionalGroup)fg.objs[i]).setLongevity(4*365);
				((FunctionalGroup)fg.objs[i]).reproductioncycle=90;
				((FunctionalGroup)fg.objs[i]).setK(1.05);
				((FunctionalGroup)fg.objs[i]).setLinf(15.67);
				((FunctionalGroup)fg.objs[i]).setLo(1.6);
				((FunctionalGroup)fg.objs[i]).setA(0.0024);
				((FunctionalGroup)fg.objs[i]).setB(3.32); //Cetengraulis edentulus, Atlantic anchoveta
														  //Length at first maturity
														  //Lm 14.7  range ? - ? cm	
				p=150;
				populations.add(new Population(((FunctionalGroup)fg.objs[i]),p));
				
			}
			if(((FunctionalGroup)fg.objs[i]).getName()=="Plankton") {
				((FunctionalGroup)fg.objs[i]).setGroupClass("biomass.ibmmodel.core.Plankton");
				((FunctionalGroup)fg.objs[i]).setOrganismType(OrganismType.AUTOTROPH);
				((FunctionalGroup)fg.objs[i]).setK(0.5);
				((FunctionalGroup)fg.objs[i]).setGoodPlanktonConcentration(0.001);
				((FunctionalGroup)fg.objs[i]).setMaxBiomassPlanktonConcentration(((FunctionalGroup)fg.objs[i]).goodPlanktonConcentration*2);
				((FunctionalGroup)fg.objs[i]).reproductioncycle=1; //PARA QUE NO CAUSE ERROR A LA HORA DE CREAR LOS GRUPOS DE EDADES
				p=100;	
				populations.add(new Population(((FunctionalGroup)fg.objs[i]),p));
			}
			//populations.add(new Population(((FunctionalGroup)fg.objs[i]),p));
		}	
	}
	
	private void stopBioMASSModel() {
		scheduler.reset();
		fg.clear();
		populations.clear();
		//display.step();
	}
	
	public void start() {
		initBioMASSModel();
		for(Object p:populations) {
			((Population)p).start();
		}
		
			
	}
	
	public void stop() {
		for(Object p:populations) {
			((Population)p).stop();
		}
		stopBioMASSModel();
	}
	
	public boolean step(long step) {
		if(step%1800==0)
			for(Object p:populations) {
				((Population)p).step(step);
			}
		boolean stop=scheduler.step(step);
		display.step();
		
		
		
		//Temporal, la poblaci—n 0 son los planktivoros 
		if(((Population)populations.get(0)).organisms.numObjs>0) {
			//TEMPORAL PARA PARAR CUANDO ENGORDEN
			if(parar) return true;
			else return false;
		}
		else {
			for(Object p:populations) {
				((Population)p).step(step);
			}
			return true;
		}
		//return stop;
	}
	
	
	public JPanel getBioMASSDisplay() {
		return display;
	}
	

}
