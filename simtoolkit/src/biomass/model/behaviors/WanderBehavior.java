/**
 * 
 */
package biomass.model.behaviors;

import java.awt.Color;

import biomass.model.taxonomy.Heterotroph;
import biomass.model.utils.Velocity;
import biomass.simulator.gui.BioMASSGUIFrame;
import multiagent.model.agent.Behavior;

/**
 * @author flavioreyes
 * Bajo este comportamiento el individuo vaga de forma aleatoria.
 * Para ello con una cierta probabilidad se calcula un vector aleatorio o se utiliza el mismo ya
 * calculado.
 * No hay condiciones para su activaci—n.
 */
public class WanderBehavior extends Behavior {
	private Heterotroph agent;
	private static double changeDirProbability=0.05;
	private double wanderSpeed;


	/**
	 * @param sensors
	 */
	public WanderBehavior(Heterotroph agent) {
		this.agent=agent;
		wanderSpeed=agent.fg.minSpeed;
		setWanderVelocity(1.0);
	}
	

	/* (non-Javadoc)
	 * @see multiagent.model.subsumption.Behavior#act()
	 */
	@Override
	public void act() {

		// Si se encuentra en el refugio sale de el
		if(agent.isHidden())
			agent.leaveRefuge();
	    agent.drawablecolor=new Color(255,0,0);
	    // El agente calcula el vector de movimiento
		setWanderVelocity(changeDirProbability);
		// Se ajusta la velocidad vagabundeo
		agent.getVelocity().setMagnitude(wanderSpeed*agent.length*BioMASSGUIFrame.getInstance().getTimeStep());
		BioMASSGUIFrame.getInstance().seaSpace.move(agent.getID());
//		if(agent.getID()==50)
//			System.out.println("Agent:"+agent.getID()+" wandering ");
	}
	

	
	/* (non-Javadoc)
	 * @see multiagent.model.subsumption.Behavior#isActive()
	 */
	@Override
	public boolean isActive() {
		return true;  // Siempre esta activo
	}
	
	
	
	
	public void setWanderVelocity(double changeDirProbability)
	{
	    Velocity randomComponent = new Velocity();
	    
	    if(BioMASSGUIFrame.getInstance().rand.nextDouble() < changeDirProbability)
	    {
	    	agent.getVelocity().Normalize();
	        randomComponent.Assign(2*BioMASSGUIFrame.getInstance().rand.nextDouble()-1.0,2*BioMASSGUIFrame.getInstance().rand.nextDouble()-1.0);
	        randomComponent.Normalize();
	        agent.getVelocity().Assign(agent.getVelocity().getCx()+randomComponent.getCx(), agent.getVelocity().getCy()+randomComponent.getCy());
	    }
	}

}
