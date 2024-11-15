package biomass.simulator.core;

import biomass.ibmmodel.utils.Velocity;

public final class MovingParticle {
	public Object o;
	public double radio;
	public double x;
	public double y;
	public int nextX;
	public int nextY;
	public int prevX;
	public int prevY;
	public Velocity velocity;
	
	public MovingParticle(double x, double y, Object o) {
		this.x=x;
		this.y=y;
		this.o=o;
		
		velocity=new Velocity();
	}
	
	public void reset(double x, double y, Object o) {
		this.x=x;
		this.y=y;
		this.o=o;
		velocity.setVelocity(0, 0);
	}
	
	

}
