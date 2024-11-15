/**
 * En cada paso olvida el refugio actual antes de inspeccionar el entorno en busca de refugios
 * si encuentra alguno o algunos selecciona al m‡s cercano,
 * si no encuentra ninguno es porque el que ten’a quedo fuera del rango de percepci—n
 * Se activa si hay refugio en memoria y hay hambre.
 */
package biomass.model.behaviors;

import biomass.model.environment.Refuge;
import biomass.model.taxonomy.Heterotroph;
import biomass.model.utils.Velocity;
import biomass.simulator.core.Bag;
import biomass.simulator.gui.BioMASSGUIFrame;
import biomass.model.utils.FuzzyFunctions;
import multiagent.model.agent.Behavior;

import java.awt.Color;


/**
 * @author candysansores, flavioreyes
 *
 */
public class ScoutBehavior extends Behavior {
	private Heterotroph agent;
	private static double changeDirProbability=0.05;
	private double maxDistFromRefRatio;
	private double relDistFromRef;
	private double scoutSpeed;
    private double hungerThreshold = 0.2;
	private Color scoutColor;
	/**
	 * 
	 */
	public ScoutBehavior(Heterotroph agent) {
		this.agent=agent;
		scoutSpeed = agent.fg.minSpeed;
		maxDistFromRefRatio = agent.fg.perceptionRangeFactor*2.0;
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
				
	// Asigna el refugio mas cercano si encontr— alguno
		// Si el mas cercano es el actual lo reasigna
		// Si no encontr— ninguno asigna NULL y con eso saldr‡ del comportamiento
		agent.setRefuge(findNewRefuge());	
	}

	/* (non-Javadoc)
	 * @see multiagent.model.agent.Behavior#isActive(biomass.model.core.Heterotroph)
	 */
	@Override
	public boolean isActive() {
		if (agent.getRefuge()!=null && agent.getHunger()>=hungerThreshold) 
		  return true; // Se activa si hay refugio asignado
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
			// Se combinan los tres componentes actual, aleatorio y hacia el refugio
	        cx = agent.getVelocity().getCx() + 0.5*(refuge_attraction*refugeVelNorm.getCx()+(1-refuge_attraction)*randomComponent.getCx());
	        cy = agent.getVelocity().getCy() + 0.5*(refuge_attraction*refugeVelNorm.getCy()+(1-refuge_attraction)*randomComponent.getCy());
			agent.getVelocity().Assign(cx,cy);	        
	    }
		
	}

	
	Refuge findNewRefuge(){
		Refuge refuge, newRefuge=null;

		// Crea una bolsa con los objetos perceptibles
		Bag o = BioMASSGUIFrame.getInstance().seaSpace.getDimensionObjectsAtRadio(agent.getID(), agent.fg.perceptionRangeFactor*agent.length);
		// Inspecciona los objetos uno a uno
		double dist=0, mindist=Double.MAX_VALUE;
		for(int i=0;i<o.numObjs;i++) {
			// Verifica si alguno de los objetos es refugio
			if (o.objs[i].getClass().getCanonicalName().equalsIgnoreCase("biomass.model.environment.Refuge")) {
				refuge=(Refuge)o.objs[i];
				// Se verifica que el agente quepa en el refugio
				if(agent.getBiomass() <= refuge.getVolume()){
					// Calcula la distancia
					dist=BioMASSGUIFrame.getInstance().seaSpace.getTouchDistance(refuge.getID() , agent.getID());
					if(dist<mindist) { //Selecciona el m‡s cercano
						mindist=dist;
						newRefuge=refuge;
					}  // Se queda con el refugio mas cercano
				}
			}
		}
		return(newRefuge);
	}
	
	
	
	private double calcRefugeAttraction(double hunger, double relDistFromRef){
		return FuzzyFunctions.S(relDistFromRef,0,0.1+hunger*0.9);
	}

	


}
