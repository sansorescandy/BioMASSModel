package multiagent.model.agent;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import biomass.simulator.core.Bag;
import biomass.simulator.core.IntBag;
import biomass.simulator.gui.BioMASSGUIFrame;
import biomass.model.taxonomy.Organism;
import biomass.model.taxonomy.Population;


public class Scheduler {
	private Bag queue = new Bag();
	private Bag newscheduledagents= new Bag();
	private IntBag garbage = new IntBag();
	
	
	
	public void add(Organism agent) {
		queue.add(agent);
	}
	
	public void addNewScheduleAgents(Organism agent) {
		newscheduledagents.add(agent);
	}
	
	public boolean step(long stepsfwd) {
		for(int i=0;i<queue.numObjs;i++) {
			if(!((Organism)queue.objs[i]).step(stepsfwd)) //if not active 
				garbage.add(i); //add the inactive agents to the garbage to be deleted
		}
		//Borra del scheduler los organismos muertos
		garbage.sort(); //Los ordena de menor a mayor y los borra de mayor a menor de la "queue" del scheduler para evitar errores de outofbounds
		while(!garbage.isEmpty()) //remove the dead organisms from the scheduler
			queue.remove(garbage.pop());	
		while(!newscheduledagents.isEmpty())
			queue.add(newscheduledagents.pop());
		return(queue.isEmpty());
	}
	

	public void reset() {
		queue.clear();
	}
	
}
