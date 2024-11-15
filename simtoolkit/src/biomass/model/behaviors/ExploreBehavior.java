/**
 * Este comportamiento consiste en la bœsqueda de un refugio adecuado para el individuo el cual vaga
 * aleatoriamente inspeccionando los objetos que entran en su rango de percepci—n.
 * No hay condici—n para su activaci—n.
 */
package biomass.model.behaviors;

import java.awt.Color;

import biomass.model.environment.Refuge;
import biomass.model.taxonomy.Heterotroph;
import biomass.model.utils.Velocity;
import biomass.simulator.core.Bag;
import biomass.simulator.gui.BioMASSGUIFrame;
import multiagent.model.agent.Behavior;

/**
 * @author flavioreyes
 *
 */

public class ExploreBehavior extends Behavior {
	private Heterotroph agent;
	public static double changeDirProbability=0.05;
	private double exploreSpeed;
	private Color exploreColor;


	/**
	 * @param sensors
	 */
	public ExploreBehavior(Heterotroph agent) {
		// TODO Auto-generated constructor stub
		this.agent=agent;
		exploreSpeed = agent.fg.minSpeed;
		setExploreVelocity(1.0);
	    exploreColor=new Color(192,192,255);	// Celeste
	}

	
	/* (non-Javadoc)
	 * @see multiagent.model.subsumption.Behavior#act()
	 */
	@Override
	public void act() {
		// TODO Auto-generated method stub
	    agent.drawablecolor=exploreColor;
		// El agente calcula el vector de movimiento
		setExploreVelocity(changeDirProbability);
		// Se ajusta la velocidad de exploraci—n
		agent.getVelocity().setMagnitude(exploreSpeed*agent.length*BioMASSGUIFrame.getInstance().getTimeStep());
		// El agente se mueve
		BioMASSGUIFrame.getInstance().seaSpace.move(agent.getID());
		// Verifica si encuentra un nuevo refugio
		
		agent.setRefuge(findNewRefuge());	
	}
	

	
	/* (non-Javadoc)
	 * @see multiagent.model.subsumption.Behavior#isActive()
	 */
	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return true;  // Siempre esta activo
	}
	
	
	
	
	public void setExploreVelocity(double changeDirProbability)
	{
	    Velocity randomComponent = new Velocity();
	    double changeDir=BioMASSGUIFrame.getInstance().rand.nextDouble();
	    if(changeDir < changeDirProbability)
	    {
	    	agent.getVelocity().Normalize();
	        randomComponent.Assign(2*BioMASSGUIFrame.getInstance().rand.nextDouble()-1.0,2*BioMASSGUIFrame.getInstance().rand.nextDouble()-1.0);
	        randomComponent.Normalize();
	        agent.getVelocity().Assign(agent.getVelocity().getCx()+randomComponent.getCx(), agent.getVelocity().getCy()+randomComponent.getCy());
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

	
	

}
