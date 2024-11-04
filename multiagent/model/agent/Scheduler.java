package multiagent.model.agent;

import java.util.ArrayList;

/**
 * @author candysansores
 *
 */
public class Scheduler {
	private ArrayList<Agent> queue = new ArrayList<Agent>();

	public Scheduler() {
	}
	
	public void add(Agent agent) {
		queue.add(agent);
	}
	
	public void step() {
		for(int i=0;i<queue.size();i++)
			((Agent)(queue.get(i))).step();
	}	

	public void reset() {
		queue.clear();
	}
}
