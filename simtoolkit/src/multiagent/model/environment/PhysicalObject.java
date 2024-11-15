package multiagent.model.environment;

import biomass.model.utils.Velocity;

public class PhysicalObject {
	public int id;
	public double radio;
	public double x;
	public double y;
	public int nextX;
	public int nextY;
	public int prevX;
	public int prevY;
	public Velocity velocity;
	//public Double2D lastd = new Double2D(0,0);
	
	public PhysicalObject() {
		velocity=new Velocity();
	}
	
	public PhysicalObject(double x, double y) {
		velocity=new Velocity();
		this.x=x;
		this.y=y;	
	}
	
	public void setID(int id) {
		this.id=id;
	}
	
	public int getID() {
		return id;
	}
	
	public void setX(double x) {
		this.x=x;
	}
	
	public void setY(double y) {
		this.y=y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public Velocity getVelocity() {
		return velocity;
	}
	
	/*public Double2D momentum()
	{
		return lastd;
	}*/

}
