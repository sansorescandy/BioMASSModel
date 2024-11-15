/**
 * 
 */
package biomass.ibmmodel.core;

import java.awt.Color;

import biomass.ibmmodel.utils.Velocity;
import biomass.simulator.gui.BioMASSGUIFrame;
import multiagent.model.agent.Behavior;

/**
 * @author candysansores
 *
 */
public class WanderingBehavior extends Behavior {
	private Heterotroph agent;
	public static double changeDirProbability=0.05;


	/**
	 * @param sensors
	 */
	public WanderingBehavior(Heterotroph agent) {
		// TODO Auto-generated constructor stub
		this.agent=agent;
	}
	

	/* (non-Javadoc)
	 * @see multiagent.model.subsumption.Behavior#act()
	 */
	@Override
	public void act() {
		// TODO Auto-generated method stub

		setWanderingVelocity();
		agent.getVelocity().setMagnitude(agent.fg.getWanderSpeed()*agent.length*BioMASSGUIFrame.getInstance().scheduler.getTimeStep());
		BioMASSGUIFrame.getInstance().seaSpace.move(agent.getID());
	    agent.drawablecolor=new Color(255,128,128);	   
	}
	

	
	/* (non-Javadoc)
	 * @see multiagent.model.subsumption.Behavior#isActive()
	 */
	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	
	public void setWanderingVelocity()
	{
	    Velocity randomComponent = new Velocity();
	    
	    if(BioMASSGUIFrame.getInstance().r.nextDouble() < changeDirProbability)
	    {
	    	agent.getVelocity().Normalize();
	        randomComponent.Assign(2*BioMASSGUIFrame.getInstance().r.nextDouble()-1.0,2*BioMASSGUIFrame.getInstance().r.nextDouble()-1.0);
	        randomComponent.Normalize();
	        agent.getVelocity().Assign(agent.getVelocity().getCx()+randomComponent.getCx(), agent.getVelocity().getCy()+randomComponent.getCy());
	    }
	}

}
