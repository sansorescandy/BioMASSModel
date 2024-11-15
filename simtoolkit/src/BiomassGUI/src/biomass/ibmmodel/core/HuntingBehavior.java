package biomass.ibmmodel.core;


import java.awt.Color;

import biomass.ibmmodel.utils.FuzzyFunctions;
import biomass.simulator.core.Bag;
import biomass.simulator.gui.BioMASSGUIFrame;
import multiagent.model.agent.Behavior;

public class HuntingBehavior extends Behavior {
	private Heterotroph agent;
	Organism mostPalatable=null;
	private Bag preys;
    double hungerThreshold = 0.10;
    double maxPreyOpValue=-1;

	public HuntingBehavior(Heterotroph agent) {
		// TODO Auto-generated constructor stub
		this.agent=agent;
		preys = new Bag();
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		
		//Apunta hacia la presa m‡s apetecible
		agent.getVelocity().Assign(BioMASSGUIFrame.getInstance().seaSpace.getDimensionDistanceVector(mostPalatable.getID(), agent.getID())); 
//		agent.getVelocity().Assign(mostPalatablePrey.getX()-agent.getX(),mostPalatablePrey.getY()-agent.getY());

		//si se encuentra lejos la persigue con la m‡xima velocidad
		//double timeStepReach=agent.fg.getMaxSpeed()*agent.length*BioMASSGUIFrame.getInstance().scheduler.getTimeStep()*foc_max; //La distancia que puede recorrer el pez en 1 timestep (lenghts/s*cm*s=cm) regulado por el foco
		double timeStepReach=agent.fg.getMaxSpeed()*agent.length*BioMASSGUIFrame.getInstance().scheduler.getTimeStep();
		if(agent.getVelocity().getMagnitude()>timeStepReach) 
			agent.getVelocity().setMagnitude(timeStepReach);
		else //si se encuentra cerca se la come
		{
			//Comer	
			agent.eat(mostPalatable);
		}
		BioMASSGUIFrame.getInstance().seaSpace.move(agent.getID());
	    
	    agent.drawablecolor=new Color(255,0,0);
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
					//Verifica si son presas y no est‡n ocultas
					if (o.objs[i].getClass().getCanonicalName().equalsIgnoreCase(p) && !((Organism)(o.objs[i])).isHidden()) { 
						preys.add(o.objs[i]);
						//System.out.println("Presa: "+o.objs[i].getClass().getCanonicalName());
					}
				}
				//if(preys.isEmpty())
					//System.out.println("No hay presas en su radio");
				if(!preys.isEmpty()) {//Hay organismos en su rango de percepci—n y son presas
					chooseMostPalatablePrey();
					if (maxPreyOpValue>0.02) 
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
		double preyOpValue;
		double relativedistance;
		Organism prey;
		
		if(mostPalatable!=null) {
			//Se calcula el foco con la distancia a la presa relativa al rango de percepci—n
			relativedistance=BioMASSGUIFrame.getInstance().seaSpace.getDimensionDistance(mostPalatable.getID(), agent.getID())/(agent.fg.getPerceptionRangeFactor()*agent.length);
			palatableInx=getPalatableIndex(mostPalatable.biomass.getBiomass()/(agent.biomass.getBiomass()*agent.fg.mouthMassCapacityRatio), agent.getHunger());
			//foc_max=focusindexes[(int)(relativedistance*100)][(int)(palatableInx*100)];
			maxPreyOpValue=getPreyOpValue(relativedistance, palatableInx);
			//if(agent.age>(agent.fg.getLongevity()*0.99))
			//System.out.println("Agent ID: "+ agent.getID() + " Presa ID: "+mostPalatable.getID()+" Mostpalatable Reldist: "+relativedistance + " PalIndex: "+palatableInx+" Focmax: "+foc_max+" relconcent: "+mostPalatable.biomass.getBiomass()/(mostPalatable.volume)+" hambre:"+agent.getHunger()+"good: "+mostPalatable.fg.goodPlanktonConcentration);
		}

		for(int i=0; i<preys.numObjs; i++) {
			prey=(Organism)preys.objs[i];
			palatableInx=getPalatableIndex(prey.biomass.getBiomass()/(agent.biomass.getBiomass()*agent.fg.mouthMassCapacityRatio), agent.getHunger());
			relativedistance=BioMASSGUIFrame.getInstance().seaSpace.getDimensionDistance(prey.getID(), agent.getID())/(agent.fg.getPerceptionRangeFactor()*agent.length);
			
			//Se calcula el foco con la distancia a la presa relativa al rango de percepci—n
			preyOpValue=getPreyOpValue(relativedistance, palatableInx);
			if(preyOpValue > maxPreyOpValue) {
				maxPreyOpValue = preyOpValue;
				mostPalatable=prey;
			}
		}

		return mostPalatable;
	}

	
	
	double getPalatableIndex(double relativesize, double hunger) { 
		return FuzzyFunctions.Pi(relativesize, 1, 1, 0.2)*hunger;	
	}

	double getPreyOpValue(double relativedistance, double palatableInx) {
		if(relativedistance>1)
			relativedistance=1;
		return (1-relativedistance)*palatableInx;
	}

}
