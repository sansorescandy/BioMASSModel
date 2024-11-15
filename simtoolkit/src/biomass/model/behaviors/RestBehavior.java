/**
 * Bajo este comportamiento, el organismo se dirige a velocidad media hacia el refugio que tenga 
 * memorizado y se queda ah’.
 * La condici—n para su activaci—n es que se tenga un refugio memorizado.
 */
package biomass.model.behaviors;


import java.awt.Color;

import biomass.model.taxonomy.Heterotroph;
import biomass.model.utils.Velocity;
import biomass.simulator.gui.BioMASSGUIFrame;
import multiagent.model.agent.Behavior;

/**
 * @author flavioreyes
 */
public class RestBehavior extends Behavior {
	private Heterotroph agent;
	
	
	private double restSpeed; 
	private Color restingColor;

	/**
	 * @param agent
	 */
	public RestBehavior(Heterotroph agent) {
		this.agent=agent;
		restSpeed = (agent.fg.maxSpeed - agent.fg.minSpeed)/2;
		restingColor = new Color(0,0,0); // Negro
	}

	/* (non-Javadoc)
	 * @see multiagent.model.subsumption.Behavior#act()
	 */
	@Override
	public void act() {
		//Pprimero verificamos si ya est‡ oculto el agente
	    agent.drawablecolor=restingColor;  // Color de descanso
		if(!agent.isHidden()){
			// No est‡ oculto
			// Obtenemos el vector hacia el refugio
			agent.getVelocity().Assign(calculateHideVelocity());
			// Se mueve al organismo
			BioMASSGUIFrame.getInstance().seaSpace.move(agent.getID());
			// Calculamos la distancia que nos separa del refugio
			double dist_to_refuge = BioMASSGUIFrame.getInstance().seaSpace.getDistance(agent.getID(), agent.getRefuge().getID());
			dist_to_refuge += agent.radio - agent.getRefuge().radio;
			// Verificamos si ya llegamos al refugio
			if(dist_to_refuge <= 0){	
				// Ya llegamos!
				// Tratamos de entrar
				if(agent.getRefuge().enter(agent.getBiomass())){
					// Ya entramos 
					// Nos ocultamos
					agent.hide();
					if(agent.shout)
						System.out.println("Agent:"+agent.getID()+" resting"); 
				}
				else{
					// Si no pudimos entrar perdemos el refugio
					agent.setRefuge(null);
					if(agent.shout)
						System.out.println("Agent:"+agent.getID()+" couldn't rest"); 
				}
			}
			else{
				// No hemos llegado al refugio
				if(agent.shout)
					System.out.println("Agent:"+agent.getID()+" going to rest"); 
			}
		}
	}
	

	/* (non-Javadoc)
	 * @see multiagent.model.subsumption.Behavior#isActive()
	 */
	
	@Override
	public boolean isActive() {
		
		// Este comportamiento dirige al agente hacia el refugio para descansar ah’. 
		// El œnico requisito para su activaci—n es que se tenga identificado y memorizado un refugio
		
		// Si el organismo no tiene escondite asignado se desactiva este comportamiento
		if (agent.getRefuge()==null)
			return false;
		else
			return true;			
	}
	
	
	public Velocity calculateHideVelocity()
	{
		Velocity hideVector;
		
		// Vector normalizado que apunta hacia el refugio con origen en la posici—n actual de la presa
		hideVector=BioMASSGUIFrame.getInstance().seaSpace.getDistanceVector(agent.id, agent.getRefuge().id);
		hideVector.Normalize();
		// Se calcula la distancia de la presa al refugio
		double ref_rel_dist = BioMASSGUIFrame.getInstance().seaSpace.getDistance(agent.getID(), agent.getRefuge().getID())/agent.length;		
		if(ref_rel_dist < restSpeed)
				// Se ajusta la velocidad de escape para llegar justo al centro del refugio
				hideVector.setMagnitude(ref_rel_dist*agent.length*BioMASSGUIFrame.getInstance().getTimeStep());
			else
				// Se ajusta la velocidad estandard de escape
				// Se ajusta la velocidad de ocultamiento
				hideVector.setMagnitude(restSpeed*agent.length*BioMASSGUIFrame.getInstance().getTimeStep());				
		return hideVector;
	}
	
	
	


}
