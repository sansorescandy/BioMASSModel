/**
 * 
 */
package biomass.ibmmodel.core;

import biomass.simulator.core.Bag;
import biomass.simulator.gui.BioMASSGUIFrame;
import multiagent.model.agent.Behavior;

/**
 * @author candysansores
 *
 */
public class SwarmBehavior /*extends Behavior*/ {
	private Heterotroph agent;
	public double cohesion = 0.5;
    public double avoidance = 1.0;
    public double randomness = 0;
    public double consistency = 1.0;
    public double momentum = 1.0;


	public SwarmBehavior(Heterotroph agent) {
		// TODO Auto-generated constructor stub
	}
/*
	 (non-Javadoc)
	 * @see multiagent.model.agent.Behavior#act(biomass.ibmmodel.core.Heterotroph)
	 
	@Override
	public void act(Heterotroph agent) {
		// TODO Auto-generated method stub
		this.agent=agent;
		Bag b = getNeighbors();
        
	    Double2D avoid = avoidance(b);
	    Double2D cohe = cohesion(b);
	    Double2D rand = randomness();
	    Double2D cons = consistency(b);
	    Double2D mome = agent.momentum();
	    
	    double dx = cohesion * cohe.x + avoidance * avoid.x + consistency* cons.x + randomness * rand.x + momentum * mome.x;
	    double dy = cohesion * cohe.y + avoidance * avoid.y + consistency* cons.y + randomness * rand.y + momentum * mome.y;
	            
	    // renormalize to the given step size
	    double dis = Math.sqrt(dx*dx+dy*dy);
	    if (dis>0)
	        {
	        dx = dx / dis;
	        dy = dy / dis;
	        }
	    agent.velocity.Assign(dx, dy);
	    agent.velocity.setMagnitude(agent.fg.getWanderSpeed()*agent.length*BioMASSGUIFrame.getInstance().scheduler.getTimeStep());
	    agent.updatePosition();
	    
	    agent.lastd = new Double2D(dx,dy);
	    Double2D loc = new Double2D(agent.x,agent.y);
	    BioMASSGUIFrame.getInstance().seaSpace.setObjectLocation(agent, loc);

	    agent.organismColor=agent.escapingColor;
	}

	 (non-Javadoc)
	 * @see multiagent.model.agent.Behavior#isActive(biomass.ibmmodel.core.Heterotroph)
	 
	@Override
	public boolean isActive(Heterotroph agent) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public Bag getNeighbors()
    {
		Bag b=new Bag();
		Bag o=BioMASSGUIFrame.getInstance().seaSpace.getObjectsExactlyWithinDistance(new Double2D(agent.x, agent.y), agent.fg.getPerceptionRangeFactor()*agent.length);
		for(int i =0; i < o.numObjs; i++) {
			 Object obj= o.objs[i];
			 if (obj.getClass().getCanonicalName().equalsIgnoreCase(agent.getClass().getCanonicalName())) {
				 b.add(obj);
			 }
		}
		return b;
    }

	public double getOrientation() { return orientation2D(); }
	


	public void setOrientation2D(double val)
	{
		agent.lastd = new Double2D(Math.cos(val),Math.sin(val));
	}

	public double orientation2D()
	{
		if (agent.lastd.x == 0 && agent.lastd.y == 0) return 0;
		return Math.atan2(agent.lastd.y, agent.lastd.x);
	}


	public Double2D consistency(Bag b)
	{
		if (b==null || b.numObjs == 0) return new Double2D(0,0);

		double x = 0; 
		double y= 0;
		int i =0;
		int count = 0;
		for(i=0;i<b.numObjs;i++)
		{
			Organism other = (Organism)(b.objs[i]);
			if (other.alive)
			{
				double dx = agent.x-other.x;
				double dy = agent.y-other.y;
				Double2D m = ((Organism)b.objs[i]).momentum();
				count++;
				x += m.x;
				y += m.y;
			}
		}
		if (count > 0) { x /= count; y /= count; }
		return new Double2D(x,y);
	}

	public Double2D cohesion(Bag b)
	{
		if (b==null || b.numObjs == 0) return new Double2D(0,0);

		double x = 0; 
		double y= 0;        

		int count = 0;
		int i =0;
		for(i=0;i<b.numObjs;i++)
		{
			Organism other = (Organism)(b.objs[i]);
			if (other.alive)
			{
				double dx = agent.x-other.x;
				double dy = agent.y-other.y;
				count++;
				x += dx;
				y += dy;
			}
		}
		if (count > 0) { x /= count; y /= count; }
		return new Double2D(-x/10,-y/10);
	}

	public Double2D avoidance(Bag b)
	{
		if (b==null || b.numObjs == 0) return new Double2D(0,0);
		double x = 0;
		double y = 0;

		int i=0;
		int count = 0;

		for(i=0;i<b.numObjs;i++)
		{
			Organism other = (Organism)(b.objs[i]);
			if (other != agent )
			{
				double dx = agent.x-other.x;
				double dy = agent.y-other.y;
				double lensquared = dx*dx+dy*dy;
				count++;
				x += dx/(lensquared*lensquared + 1);
				y += dy/(lensquared*lensquared + 1);
			}
		}
		if (count > 0) { x /= count; y /= count; }
		return new Double2D(BioMASSGUIFrame.getInstance().seaSpace.width*x,BioMASSGUIFrame.getInstance().seaSpace.height*y);      
	}

	public Double2D randomness()
	{
		double x = BioMASSGUIFrame.getInstance().r.nextDouble() * 2 - 1.0;
		double y = BioMASSGUIFrame.getInstance().r.nextDouble() * 2 - 1.0;
		double l = Math.sqrt(x * x + y * y);
		return new Double2D(0.05*x/l,0.05*y/l);
	}
*/
}
