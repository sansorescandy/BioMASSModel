/**
 * Esta clase es similar a ScoutBehavior con la diferencia de que el agente olvida su actual refugio si
 * la distancia a este rebasa el factor maxDistFromRefRatio.
 * No se buscan otros refugios dejando esta tarea al comportamiento explore. 
 * Se activa si hay refugio y hay hambre.
 */
package biomass.model.behaviors;

import biomass.model.taxonomy.Heterotroph;
import biomass.model.utils.Velocity;
import biomass.simulator.gui.BioMASSGUIFrame;
import biomass.model.utils.FuzzyFunctions;
import multiagent.model.agent.Behavior;

import java.awt.Color;


/**
 * @author flavioreyes
 *
 */
public class ScoutBehavior3 extends Behavior {
	private Heterotroph agent;
	private static double changeDirProbability=0.1;
	private double maxDistFromRefRatio;
	private double relDistFromRef;
	private double scoutSpeed;
    private double hungerThreshold = 0.2;
	private Color scoutColor;
	/**
	 * 
	 */
	public ScoutBehavior3(Heterotroph agent) {
		this.agent=agent;
		scoutSpeed = agent.fg.minSpeed;
		maxDistFromRefRatio = agent.fg.perceptionRangeFactor;
		scoutColor=new Color(0,0,255);
	}

	/* (non-Javadoc)
	 * @see multiagent.model.agent.Behavior#act(biomass.model.core.Heterotroph)
	 */
	@Override
	public void act() {
		// Si est‡ oculto sale del refugio
		if(agent.isHidden())
			agent.leaveRefuge();
		agent.drawablecolor=scoutColor; 
		// El agente calcula el vector de movimiento 
		setScoutVelocityVector();
		// Se calcula la velocidad de reconocimiento
		agent.getVelocity().setMagnitude(scoutSpeed*agent.length*BioMASSGUIFrame.getInstance().getTimeStep());
		// El agente se mueve
		BioMASSGUIFrame.getInstance().seaSpace.move(agent.getID());
				
		// Si el agente se alej— demasiado del refugio lo olvida y tendr‡ que abandonar el comportamiento
		if(relDistFromRef>1.0)
			agent.setRefuge(null);	
	}

	/* (non-Javadoc)
	 * @see multiagent.model.agent.Behavior#isActive(biomass.model.core.Heterotroph)
	 */
	@Override
	public boolean isActive() {
		if (agent.getRefuge()!=null && agent.getHunger()>=hungerThreshold) 
			return true; // Se activa si hay refugio asignado y hay hambre
		else return false;
	}
	
	//Mientras m‡s hambre, menor es la liga del individuo con el refugio
	//por lo cual pueden salir a explorar m‡s lejos
	private void setScoutVelocityVector() {
		double cx, cy;
		double refuge_attraction;
		
		
	    // Si hay cambio de direccion
	    if(BioMASSGUIFrame.getInstance().rand.nextDouble() < changeDirProbability)
	    {
	    	// Se normaliza el vector actual
	    	agent.getVelocity().Normalize();
	    	// Se calcula la porci—n aleatoria
		    Velocity randomComponent = new Velocity();
	        randomComponent.Assign(2*BioMASSGUIFrame.getInstance().rand.nextDouble()-1.0,2*BioMASSGUIFrame.getInstance().rand.nextDouble()-1.0);
	        randomComponent.Normalize(); // Se normaliza
	        // Se calcula el componente hacia el refugio
			// vector que apunta al refugio normalizado	
	        // la distancia se calcula entre los centros geomŽtricos del agente y el refugio
			Velocity refugeVelNorm=BioMASSGUIFrame.getInstance().seaSpace.getDistanceVector(agent.id, agent.getRefuge().id);
			// Se calcula la distancia relativa al refugio
			relDistFromRef=refugeVelNorm.getMagnitude()/agent.length/maxDistFromRefRatio;
			refugeVelNorm.Normalize(); // Se normaliza
			// peso del vector hacia el refugio
			refuge_attraction = calcRefugeAttraction(agent.getHunger(),relDistFromRef);
			// Se combinan los tres componentes actal, aleatorio y hacia el refugio
	        cx = 0.5*agent.getVelocity().getCx() + 0.5*(refuge_attraction*refugeVelNorm.getCx()+(1-refuge_attraction)*randomComponent.getCx());
	        cy = 0.5*agent.getVelocity().getCy() + 0.5*(refuge_attraction*refugeVelNorm.getCy()+(1-refuge_attraction)*randomComponent.getCy());
			agent.getVelocity().Assign(cx,cy);	        
	    }
		
	}

	
	
	private double calcRefugeAttraction(double hunger, double relDistFromRef){
		return FuzzyFunctions.S(relDistFromRef,0,0.1+hunger*0.9);
	}

	


}
