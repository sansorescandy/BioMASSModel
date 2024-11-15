/**
 * 
 */
package biomass.model.behaviors;


import biomass.model.taxonomy.Organism;
import biomass.model.utils.Velocity;
import biomass.simulator.gui.BioMASSGUIFrame;
import multiagent.model.agent.Behavior;

/**
 * @author candysansores, flavioreyes
 *
 */
public class DriftBehavior extends Behavior {
	private Organism agent;
	private static double changeDirProbability=0.0;
	private double driftSpeed;


	/**
	 * @param sensors
	 */
	public DriftBehavior(Organism agent) {
		// TODO Auto-generated constructor stub
		this.agent=agent;
		driftSpeed = agent.fg.minSpeed;
		setDriftVelocity(1.0);
	}
	

	/* (non-Javadoc)
	 * @see multiagent.model.subsumption.Behavior#act()
	 */
	@Override
	public void act() {
		// TODO Auto-generated method stub

		setDriftVelocity(changeDirProbability);
		// Se ajusta la velocidad vagabundeo
		agent.getVelocity().setMagnitude(driftSpeed*agent.length*BioMASSGUIFrame.getInstance().getTimeStep());
		BioMASSGUIFrame.getInstance().seaSpace.move(agent.getID());
	}
	

	
	/* (non-Javadoc)
	 * @see multiagent.model.subsumption.Behavior#isActive()
	 */
	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return true;  // Siempre esta activo
	}
	
	
	
	
	public void setDriftVelocity(double changeDirProbability)
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
