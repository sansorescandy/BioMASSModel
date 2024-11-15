package biomass.model.taxonomy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import multiagent.model.agent.Behavior;
import biomass.model.behaviors.DriftBehavior;
import biomass.simulator.core.BioMASSModel;


public final class Plant extends Autotroph {
	private double maxBiomass;
	Color planktonColor;
	int whiteComponent, whiteNew;

	
	public Plant(FunctionalGroup fg, Population population, int age, double x, double y)
	{
		super(fg, population, 0, x, y);
		length=fg.radio;
		radio=length/2;
		volume=4*Math.PI*Math.pow(radio, 3)/3;
		maxBiomass=volume*fg.maxBiomassConcentration;
		addLeanMass(maxBiomass*fg.minLeanMassFraction);
		addFatMass(maxBiomass*fg.maxFattyMassFraction);
		whiteComponent = (int) (255-255*getBiomass()/maxBiomass);
		planktonColor =new Color(whiteComponent,255,whiteComponent);			
		initAgentBehaviors();
	}

	//Inicializa los comportamientos del agente
	private void initAgentBehaviors() {

		Behavior[] behaviors = { new DriftBehavior(this) };
		boolean subsumes[][] = { { false} };    
		initBehaviors(behaviors, subsumes);
	}


	
	
	public void update(double dt)
	{
		double biomassincrement=getBiomass()*dt*fg.k*(1-getBiomass()/maxBiomass);
		double leanincrement=biomassincrement*getLeanMass()/getBiomass();
		double fatincrement=biomassincrement*getFatMass()/getBiomass();	
		addLeanMass(leanincrement);	
		addFatMass(fatincrement);
	}
	
	

	@Override
	public void hourlystep() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dailystep() {
		// TODO Auto-generated method stub
		update(1);
	}

		

	/* (non-Javadoc)
	 * @see multiagent.model.agent.ReactiveAgent#performBehavior()
	 */


	
	
	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		whiteNew = (int) (255-255*getBiomass()/maxBiomass);
		if(whiteNew != whiteComponent){
			whiteComponent = whiteNew;
			planktonColor =new Color(whiteComponent, 255, whiteComponent);			
		}
		g2d.setPaint(planktonColor); 
		double scale=length*BioMASSModel.scaleFactor;
		Ellipse2D plankton_shape = new Ellipse2D.Double(getX()-scale/2,getY()-scale/2,scale,scale);
		g2d.fill(plankton_shape);	
	}
}
