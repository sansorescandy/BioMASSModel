package multiagent.model.agent;

import java.awt.Color;

import multiagent.model.environment.PhysicalObject;


public abstract class Agent extends PhysicalObject implements SteppableAgent {
	
	private boolean active;
	public Color drawablecolor;
	
	public Agent() {
		active=true;
	}
	
	public Agent(int x, int y){
		this.x = x;
		this.y = y;
		active=true;
	}
	
	    
	protected abstract void initBehavior();
	
	protected abstract void performBehavior();
	
	@Override
	public boolean step(long stepsfwd) {
		if(active && stepsfwd==1)
			performBehavior();
		return active;
	}
	
	public void setActive(boolean active) {
		this.active=active;
	}


	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
