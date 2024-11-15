package biomass.simulator.core;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import biomass.ibmmodel.core.Organism;
import biomass.ibmmodel.core.Population;
import biomass.simulator.gui.BioMASSGUIFrame;

import multiagent.model.agent.Agent;

public class Scheduler {
	private Bag queue = new Bag();
	private Bag newscheduledagents= new Bag();
	private int seconds=0;
	private int hours=0;
	private int days=0;
	private int timestep;
	private IntBag garbage = new IntBag();


	public Scheduler(int timestep) {
		this.timestep=timestep;//%daysecs;
	}
	
	public void add(Organism agent) {
		queue.add(agent);
	}
	
	public void addNewScheduleAgents(Organism agent) {
		newscheduledagents.add(agent);
	}
	
	public boolean step(long step) {
		
		//step en cada segundo simulado
		for(int i=0;i<queue.numObjs;i++) {
			if(!((Organism)queue.objs[i]).step()) //if not active 
				garbage.add(i); //add the inactive agents to the garbage to be deleted
		}
		//Borra del scheduler los organismos muertos
		garbage.sort(); //Los ordena de menor a mayor y los borra de mayor a menor de la "queue" del scheduler para evitar errores de outofbounds
		while(!garbage.isEmpty()) //remove the dead organisms from the scheduler
			queue.remove(garbage.pop());
		
		seconds+=timestep;

		//step en cada hora simulada
		if(seconds>=3600) {
			seconds=seconds%3600;
			hours++;
		}

		//step en cada dia simulado
		if(hours>=24) {
			hours=hours%24;
			days++;
		}
		while(!newscheduledagents.isEmpty())
			queue.add(newscheduledagents.pop());
		
		return(queue.isEmpty());
	}
	

	public void reset() {
		queue.clear();
		seconds=0;
	}
	
	public int getTimeStep() {
		return timestep;
	}
	
	public int getTimeSecs() {
		return days*86400+hours*3600+seconds;
	}
}
