package biomass.model.environment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import biomass.simulator.core.BioMASSModel;
import biomass.simulator.gui.BioMASSGUIFrame;
import biomass.simulator.gui.DrawableObject;
import multiagent.model.environment.PhysicalObject;

public class Refuge extends PhysicalObject implements DrawableObject {
	private double volume;
	private double availableVolume;
	private int timesUsed;
	private int sizeClass;
	BioMASSModel model=BioMASSGUIFrame.getInstance();




	Ellipse2D refuge_shape;
    Color lineColor;
    Color fillColor;

	public Refuge(double x, double y, double radio) {
		this.x=x;
		this.y=y;
		this.radio=radio;
		volume = 4*Math.PI*Math.pow(radio,3)/3;
		availableVolume = volume;
        refuge_shape = new Ellipse2D.Double(x-radio, y-radio, radio*2, radio*2);
        lineColor = new Color(0,0,0);
        fillColor = new Color(255,255,192);
	}
	
	public Refuge(double radio) {
		x = 0;
        y = 0;
        timesUsed=0;
		this.radio=radio;
		volume = 4*Math.PI*Math.pow(radio,3)/3;
		availableVolume = volume;
        refuge_shape = new Ellipse2D.Double(-radio, -radio, radio*2, radio*2);
        lineColor = new Color(0,0,0);
        fillColor = new Color(255,255,192);
	}

	
	public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
        refuge_shape = new Ellipse2D.Double(x-radio, y-radio, radio*2, radio*2);
	}
	
	
	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}
	

	public int getTimesUsed() {
		return timesUsed;
	}
	
	
	public boolean enter(double volume) {
		if(availableVolume>=volume){
			availableVolume -= volume;
			timesUsed++;
			model.incRefUseCounter(sizeClass);
			return true;
		}
		return false;
	}
	
	public void leave(double volume ){
		availableVolume+=volume;
		if(availableVolume>this.volume)
			availableVolume = this.volume;
	}

	public int getSizeClass() {
		return sizeClass;
	}

	public void setSizeClass(int sizeClass) {
		this.sizeClass = sizeClass;
	}



	
	@Override
	public void draw(Graphics2D g2d) {
        // Redibujamos el refugio
        g2d.setColor(fillColor);
        g2d.fill(refuge_shape);
        g2d.setColor(lineColor);
        g2d.draw(refuge_shape);
	}

    
     

	
	
}

