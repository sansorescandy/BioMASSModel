package biomass.model.behaviors;

/**
 * @author flavioreyes
 * Bajo este comportamiento el organismo se dirige directamente hacia aquella presa que le resulte
 * mas atractiva tomando en cuenta el apetito, la suculencia y el esfuerzo para alcanzarla. 
 * El organismo es un carn’vor y la presa es heter—trofo, es decir, un organismo CON
 * autonom’a de decisi—n. Al alcanzarla come de ella tomando un bocado y resultando en la 
 * muerte de la presa.
 * Se activa cuando el valor de oportunidad de la presa iguala o supera un cierto umbral. 
 */


import java.awt.Color;

import biomass.model.taxonomy.Heterotroph;
import biomass.model.taxonomy.Organism;
import biomass.model.utils.FuzzyFunctions;
import biomass.simulator.core.Bag;
import biomass.simulator.gui.BioMASSGUIFrame;
import multiagent.model.agent.Behavior;

public class HuntBehavior extends Behavior {
	private Heterotroph agent;
    double palindexThreshold = 0.2;
    private Bag palatablePreys;
	String preyName;
	private double huntSpeed;
	private Color huntColor;

	public HuntBehavior(Heterotroph agent) {
		this.agent=agent;
		huntSpeed = agent.fg.maxSpeed;
		preyName=agent.fg.preys[0]; //Obtiene la lista de sus presas
		palatablePreys = new Bag();
	    huntColor=new Color(255,192,0);  // Amarillo
	}

	@Override
	public void act() {
		Organism mostAccesiblePrey;

		// Si se encuentra en el refugio sale de el
		if(agent.isHidden())
			agent.leaveRefuge();
	    agent.drawablecolor=huntColor;
		// Se selecciona a la presa mas accesible
	    mostAccesiblePrey=chooseMostAccesiblePrey();
		// Apunta hacia la presa m‡s apetecible
		agent.getVelocity().Assign(BioMASSGUIFrame.getInstance().seaSpace.getTouchDistanceVector(agent.getID(),mostAccesiblePrey.getID())); 
		// Ajusta la velocidad de cacer’a
		double timeStepReach=huntSpeed*agent.length*BioMASSGUIFrame.getInstance().getTimeStep();
		if(agent.shout)
			System.out.println("Agent :"+agent.getID()+" hunting prey "+mostAccesiblePrey.getID());

		if(agent.getVelocity().getMagnitude()>timeStepReach) 
			agent.getVelocity().setMagnitude(timeStepReach);
		else {//si se encuentra cerca se la come
			agent.eat(mostAccesiblePrey);
			if(agent.shout)
				System.out.println("Agent :"+agent.getID()+" killing prey "+mostAccesiblePrey.getID()+" hidden="+mostAccesiblePrey.isHidden());		
		}
		BioMASSGUIFrame.getInstance().seaSpace.move(agent.getID());
	}

	
	
	@Override
	public boolean isActive() {
	    Bag preys=new Bag();

	    // Se captan objetos de alrededor
	    Bag objects = BioMASSGUIFrame.getInstance().seaSpace.getDimensionObjectsAtRadio(agent.getID(), agent.fg.perceptionRangeFactor*agent.length);
	    // Se revisan uno a uno en busca de posibles presas
	    for(int i=0;i<objects.numObjs;i++) 
	    	// Si el objeto es una posible presa y no est‡ oculto
	    	if (objects.objs[i].getClass().getCanonicalName().equalsIgnoreCase(preyName) && !((Organism)(objects.objs[i])).isHidden())
	    		preys.add(objects.objs[i]);
	    if(!preys.isEmpty()) {//Hay organismos en su rango de percepci—n y son presas
	    	getPalatablePreys(preys);
	    	if(!palatablePreys.isEmpty()){
	    		return true;						
	    	}
	    }
		return false;
	}


	private void getPalatablePreys(Bag preys) {
		double pal_index;
		Organism prey;
		
		// Se inicializa la lista de presas apetecibles
		palatablePreys.clear();
		// Se revisan una a una las presas
		for(int i=0; i<preys.numObjs; i++) {
			prey=(Organism)preys.objs[i];
			// Se calcula el ’ndice de suculencia
			pal_index=getPalatableIndex(prey.getBiomass()/agent.getBiomass(), agent.getHunger());
			// Si la suculencia es suficiente se agrega a la lista
			if(pal_index >= palindexThreshold)
				palatablePreys.add(prey);
		}
	}

	Organism chooseMostAccesiblePrey() {
		Organism prey;
		Organism mostAccesiblePrey=null;
		double rel_dist;
		double rel_weight;
		double min_encounter_time=Double.MAX_VALUE;
		double encounter_time;


		// Se revisan una a una las presas apetecibles para seleccionar la mejor oportunidad
		for(int i=0; i<palatablePreys.numObjs; i++) {
			prey=(Organism)palatablePreys.objs[i];
			// Se calcula la distancia a las presas en tŽrminos del cuerpo del depredador
			rel_dist=BioMASSGUIFrame.getInstance().seaSpace.getTouchDistance(agent.getID(), prey.getID())/agent.length;
			// Se calcula la relaci—n de masas
			rel_weight=prey.getBiomass()/agent.getBiomass();
			// Se calcula el tiempo de encuentro
			encounter_time=calculateHuntEncounterTime(rel_dist, rel_weight);
			if(encounter_time<min_encounter_time){
				min_encounter_time=encounter_time;
				mostAccesiblePrey=prey;
			}
		}
		return mostAccesiblePrey;
	}
	
	
	
	double getPalatableIndex(double relativesize, double hunger) { 
		return FuzzyFunctions.Pi(relativesize, 0.1, 0.1, 0.45)*hunger;	
	}


	
	double calculateHuntEncounterTime(double rel_dist, double rel_weight)
	{
		return rel_dist/(huntSpeed*(1-rel_weight));
	}

	

	
	
	double getPreyOpValue(double relativedistance, double palatableInx) {
		if(relativedistance>1)
			relativedistance=1;
		return (1-relativedistance/2)*palatableInx;
	}

}
