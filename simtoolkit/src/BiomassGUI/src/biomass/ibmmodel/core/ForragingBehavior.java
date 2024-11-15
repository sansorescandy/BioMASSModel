package biomass.ibmmodel.core;


import java.awt.Color;

import biomass.simulator.core.Bag;
import biomass.simulator.gui.BioMASSGUIFrame;
import multiagent.model.agent.Behavior;

public class ForragingBehavior extends Behavior {
	private Heterotroph agent;
	private Bag preys;
	Organism mostPalatable=null;
    double hungerThreshold = 0.10;
    double foc_max=-1;
    

	public ForragingBehavior(Heterotroph agent) {
		// TODO Auto-generated constructor stub
		this.agent=agent;
		preys = new Bag();
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		
		//Apunta hacia la presa m‡s apetecible
		agent.getVelocity().Assign(BioMASSGUIFrame.getInstance().seaSpace.getDimensionDistanceVector(mostPalatable.getID(), agent.getID())); 
		
		//si se encuentra lejos la persigue con la m‡xima velocidad
		//double timeStepReach=agent.fg.getMaxSpeed()*agent.length*BioMASSGUIFrame.getInstance().scheduler.getTimeStep()*foc_max; //La distancia que puede recorrer el pez en 1 timestep (lenghts/s*cm*s=cm) regulado por el foco
		double timeStepReach=agent.fg.getMaxSpeed()*agent.length*BioMASSGUIFrame.getInstance().scheduler.getTimeStep();
		if(agent.getVelocity().getMagnitude()>timeStepReach) {
			agent.getVelocity().setMagnitude(timeStepReach);
			//System.out.println("Move!");
		}
		else //si se encuentra cerca se la come
		{
			agent.eat(mostPalatable);
			//System.out.println("Eat!");
		}
		BioMASSGUIFrame.getInstance().seaSpace.move(agent.getID());
	    
	    agent.drawablecolor=new Color(128,128,255);
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		//System.out.println("Hambre: "+agent.getHunger());
		if(agent.getHunger()>hungerThreshold) { //Tiene hambre. 
			//System.out.println("Tiene hambre");
			String p=agent.fg.getPreys()[0]; //Obtiene la lista de sus presas
			preys.clear();
			Bag o = BioMASSGUIFrame.getInstance().seaSpace.getDimensionObjectsAtRadio(agent.getID(), agent.fg.getPerceptionRangeFactor()*agent.length);
			
			if(!o.isEmpty()) { //Si hay organismos en su rango de precepci—n
				//System.out.println("Hay "+ o.size()+" organismos en agente: "+agent.getID());
				
				for(int i=0;i<o.numObjs;i++) {
					//System.out.println("Organismo: "+o.objs[i].getClass().getCanonicalName());

					if (o.objs[i].getClass().getCanonicalName().equalsIgnoreCase(p)) {
						preys.add(o.objs[i]);
						//System.out.println("Presa: "+o.objs[i].getClass().getCanonicalName());
					}
				}
				//if(preys.isEmpty())
					//System.out.println("No hay presas en su radio");
				if(!preys.isEmpty()) {//Hay organismos en su rango de percepci—n y son presas
					chooseMostPalatablePrey();
					if (foc_max>0.02) 
						return true; 
					else
						return false;
				}
				else			     //Hay organismos en su rango de precepci—n pero no son presas
					return false;
			}
			else //La pila est‡ vac’a, no hay organismos en su rango de precepci—n
				return false;
		}
		else //No tiene hambre el agente por lo tanto no se activa su comportamiento de caza
			return false;
	}


	private Organism chooseMostPalatablePrey() {
		double palatableInx;
		double foc;
		double relativeconcentration;
		double relativedistance;
		Organism prey;
		
		if(mostPalatable!=null) {
			//Se calcula el foco con la distancia a la presa relativa al rango de percepci—n
			relativedistance=BioMASSGUIFrame.getInstance().seaSpace.getDimensionDistance(mostPalatable.getID(), agent.getID())/(agent.fg.getPerceptionRangeFactor()*agent.length);
			relativeconcentration=mostPalatable.biomass.getBiomass()/(mostPalatable.volume)/mostPalatable.fg.goodPlanktonConcentration;				
			//palatableInx=palatableindexes[(int)(relativeconcentration*100)][(int)(agent.getHunger()*100)];
			palatableInx=getPlanktonPalatableIndex(relativeconcentration, agent.getHunger());
			//foc_max=focusindexes[(int)(relativedistance*100)][(int)(palatableInx*100)];
			foc_max=getPreyOpValue(relativedistance, palatableInx);
			//if(agent.age>(agent.fg.getLongevity()*0.99))
			//System.out.println("Agent ID: "+ agent.getID() + " Presa ID: "+mostPalatable.getID()+" Mostpalatable Reldist: "+relativedistance + " PalIndex: "+palatableInx+" Focmax: "+foc_max+" relconcent: "+mostPalatable.biomass.getBiomass()/(mostPalatable.volume)+" hambre:"+agent.getHunger()+"good: "+mostPalatable.fg.goodPlanktonConcentration);
		}
		
		for(int i=0; i<preys.numObjs; i++) {
			prey=(Organism)preys.objs[i];
			relativeconcentration=prey.biomass.getBiomass()/(prey.volume)/prey.fg.goodPlanktonConcentration;
			if(relativeconcentration>1)
				relativeconcentration=1;
			palatableInx=getPlanktonPalatableIndex(relativeconcentration, agent.getHunger());
			relativedistance=BioMASSGUIFrame.getInstance().seaSpace.getDimensionDistance(prey.getID(), agent.getID())/(agent.fg.getPerceptionRangeFactor()*agent.length);
			
			//Se calcula el foco con la distancia a la presa relativa al rango de percepci—n
			foc=getPreyOpValue(relativedistance, palatableInx);
			if(foc > foc_max) {
				foc_max = foc;
				mostPalatable=prey;
			}
			
		}
		
		return mostPalatable;
	}

	
	
	
	double getPlanktonPalatableIndex(double relativeconcentration, double hunger) { 
		if(relativeconcentration>1)
			relativeconcentration=1;
		return relativeconcentration*hunger;
	
	}
	
	double getPreyOpValue(double relativedistance, double palatableInx) {
		if(relativedistance>1)
			relativedistance=1;
		return (1-relativedistance)*palatableInx;
	}

}
