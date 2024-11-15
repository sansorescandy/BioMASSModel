package biomass.ibmmodel.utils;


import java.awt.geom.Point2D;

public class Velocity {

	private double theta; //‡ngulo con respecto al eje x
	private double magnitude;
	
	
	
	public Velocity () {
		magnitude = 0;
	    theta = 0;
	}
	
	public Velocity(double cx, double cy)
	{
	    magnitude = Math.sqrt(cx*cx+cy*cy);
	    theta = Math.atan2(cy,cx);
	}

	
	public Velocity(Point2D.Double p0, Point2D.Double p1)
	{
	    double cx, cy;

	    cx = p1.getX()-p0.getX();
	    cy = p1.getY()-p0.getY();

	    magnitude = Math.sqrt(cx*cx+cy*cy);
	    theta = Math.atan2(cy,cx);
	}

	public void Assign(double cx, double cy)
	{
	    magnitude = Math.sqrt(cx*cx+cy*cy);
	    theta = Math.atan2(cy,cx);
	}
	
	public void setVelocity(double cx, double cy)
	{
	    magnitude = Math.sqrt(cx*cx+cy*cy);
	    theta = Math.atan2(cy,cx);
	}


	public void Assign(double x, double y, double magnitude)
	{
	    this.magnitude = magnitude;
	    theta = Math.atan2(y,x);
	}


	public void Assign(Velocity v)
	{
	    magnitude = v.magnitude;
	    theta = v.theta;
	}

	public double getCx()
	{
	    return magnitude*Math.cos(theta);
	}

	public double getCy()
	{
	    return magnitude*Math.sin(theta);
	}
	
    public double getMagnitude() 
    {
    	return magnitude;
    }
    
    public void Normalize() {
    	magnitude = 1.0;
    }

	public void setMagnitude(double magnitude)
	{
	    this.magnitude = magnitude;
	}
	
	public static Point2D.Double Normalize(double x, double y)
	{
	    double magnitude;
	    magnitude = Math.sqrt(x*x+y*y);
	    Point2D.Double p=new Point2D.Double();
	    if(magnitude > 0)
	    {
	        p.setLocation(x/magnitude, y/magnitude);
	    }
	    else
	    	p.setLocation(x, y);
	    return p;
	}
	
	public static Point2D.Double Normalize(Point2D.Double point)
	{
	    double magnitude;
	    double x=point.getX();
	    double y=point.getY();
	    magnitude = Math.sqrt(x*x+y*y);
	    if(magnitude > 0)
	    {
	       point.setLocation(x/magnitude, y/magnitude);
	    }
	    return point;
	}
	
	public Velocity(Velocity v)
	{
	    magnitude = v.magnitude;
	    theta = v.theta;
	}
	
	public void bounceX() {
		setVelocity(-getCx(), getCy());
	}
	
	public void bounceY() {
		setVelocity(getCx(), -getCy());
	}
}
