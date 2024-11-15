/**
 * @author flavioreyes
 * Bajo este comportamiento el organismo se dirige directamente hacia aquella presa que le resulte
 * mas atractiva tomando en cuenta el apetito, la suculencia y la proximidad. 
 * El organismo es un herb’voro y la presa es plancton o una planta, es decir, un organismo sin
 * autonom’a de decisi—n. Al llegar a la presa come de ella reduciendo la concentraci—n de alimento
 * en la mancha.
 * Se activa cuando el valor de oportunidad de la presa iguala o supera un cierto umbral.
 */


package biomass.model.behaviors;


import java.awt.Color;

import biomass.model.taxonomy.Heterotroph;
import biomass.model.taxonomy.Organism;
import biomass.simulator.core.Bag;
import biomass.simulator.gui.BioMASSGUIFrame;
import multiagent.model.agent.Behavior;

public class ForrageBehavior extends Behavior {
	private Heterotroph agent;
	Organism mostPalatablePrey;
    double opvalueThreshold = 0.1;
    private Bag preys;
	String preyName;
	private double forrageSpeed;
	private Color forrageColor;
    

	public ForrageBehavior(Heterotroph agent) {
		this.agent=agent;
		forrageSpeed = agent.fg.maxSpeed;
		preys = new Bag();
		preyName=agent.fg.preys[0]; //Obtiene la lista de sus presas
	    forrageColor=new Color(255,192,0); // Amarillo
	}

	@Override
	public void act() {
		// Si el agente est‡ oculto, sale de el
		if(agent.isHidden())
			agent.leaveRefuge();
	    agent.drawablecolor=forrageColor; // Color rojo
		// Apunta hacia la presa m‡s apetecible
		agent.getVelocity().Assign(BioMASSGUIFrame.getInstance().seaSpace.getTouchDistanceVector(agent.getID(),mostPalatablePrey.getID())); 
		// Ajusta la velocidad de forrajeo
		double timeStepReach=forrageSpeed*agent.length*BioMASSGUIFrame.getInstance().getTimeStep();
		if(agent.getVelocity().getMagnitude()>timeStepReach) 
			agent.getVelocity().setMagnitude(timeStepReach);
		else //si se encuentra cerca se la come
			agent.eat(mostPalatablePrey);
		BioMASSGUIFrame.getInstance().seaSpace.move(agent.getID());
	    
	}

	@Override
	public boolean isActive() {
		preys.clear(); // Se vac’a la lista de presas
		// Se captan objetos de la periferia
		Bag objects= BioMASSGUIFrame.getInstance().seaSpace.getDimensionObjectsAtRadio(agent.getID(), agent.fg.perceptionRangeFactor*agent.length);
		if(!objects.isEmpty()){
			// Si la bolsa no est‡ vac’a se revisan uno a uno en busca de posibles presas
			for(int i=0;i<objects.numObjs;i++) {
				// Si el objeto es posible presa
				if (objects.objs[i].getClass().getCanonicalName().equalsIgnoreCase(preyName)) 
					// Se agrega a la lista de posibles presas
					preys.add(objects.objs[i]);

			}
			// Si la lista de posibles presas no est‡ vac’a
			if(!preys.isEmpty()){
				mostPalatablePrey = chooseMostPalatablePrey(preys);
				if(mostPalatablePrey != null){					
					return true;
				}
			}
		}
		return false;
	}


	private Organism chooseMostPalatablePrey(Bag preys) {
		double pal_index;
		double op_value;
		double max_op_value=-1.0;
		double relativeconcentration;
		double relativedistance;
		Organism prey;
		
		for(int i=0; i<preys.numObjs; i++) {
			prey=(Organism)preys.objs[i];
			relativeconcentration=prey.getBiomass()/(prey.volume)/prey.fg.goodPlanktonConcentration;
			if(relativeconcentration>1)
				relativeconcentration=1;
			pal_index=getPlanktonPalatableIndex(relativeconcentration, agent.getHunger());
			relativedistance=BioMASSGUIFrame.getInstance().seaSpace.getTouchDistance(prey.getID(), agent.getID())/(agent.fg.perceptionRangeFactor*agent.length);
			
			//Se calcula el foco con la distancia a la presa relativa al rango de percepci—n
			op_value=getPreyOpValue(relativedistance, pal_index);
			if(op_value > max_op_value) {
				max_op_value = op_value;
				mostPalatablePrey=prey;
			}			
		}
		if(max_op_value >= opvalueThreshold)
			return mostPalatablePrey;
		else
			return null;
	}

	
	
	
	double getPlanktonPalatableIndex(double relativeconcentration, double hunger) { 
		if(relativeconcentration>1)
			relativeconcentration=1;
		return relativeconcentration*hunger;
	
	}
	
	double getPreyOpValue(double relativedistance, double palatableInx) {
		if(relativedistance>1)
			relativedistance=1;
		return (1-relativedistance/2)*palatableInx;
	}

}
