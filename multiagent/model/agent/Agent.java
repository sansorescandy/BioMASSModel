package multiagent.model.agent;

import multiagent.model.environment.PhysicalObject;

/**
 * @author candysansores
 *
 */
public abstract class Agent extends PhysicalObject {
	abstract public void step();
}
