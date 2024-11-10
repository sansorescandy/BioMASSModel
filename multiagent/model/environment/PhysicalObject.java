package multiagent.model.environment;

import multiagent.model.utils.Vector2d;

public class PhysicalObject {
	public int id;
	public double radio;
	public double x;
	public double y;
	public int nextX;
	public int nextY;
	public int prevX;
	public int prevY;
	public Vector2d velocity;
	
	public PhysicalObject() {
		velocity=new Vector2d();
	}
	
	public PhysicalObject(double x, double y) {
		this.x=x;
		this.y=y;
		velocity=new Vector2d(x,y);
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
}
