package biomass.continuospace.example;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import multiagent.model.agent.Agent;
import multiagent.model.utils.Vector2d;
import multiagent.simulator.gui.*;

public class HeadlessPredator extends Agent implements DrawableObject {
	private HeadlessPrey target; 
	private Vector2d attackVector = new Vector2d();
	protected Color predatorColor = new Color(255,0,0);
	HeadlessSimulationModel state;

	public HeadlessPredator(double x, double y, HeadlessSimulationModel state) 
	{
		this.x=x;
		this.y=y;
		this.state = state;
	}

	@Override
	public void step() {
		Vector2d velocityUpdate = new Vector2d();  
		if( target == null || attackVector.lengthSquared() < state.parameters.getKillRadius() * state.parameters.getKillRadius()){
			target=state.preys.get(state.rand.nextInt(state.preys.size()));
		}		
		if(target != null){ 
			attackVector=state.environment.getVector2dTorus(target.id, id);
			velocityUpdate.add(attackVector);
		}
		velocityUpdate.scale(state.parameters.getPredAcceleration() * state.parameters.getPreyAcceleration() * state.parameters.getTimeScale());
		velocity.add(velocityUpdate);
		if (velocity.magnitude() > state.parameters.getPredMaxSpeed()){
			velocity.normalize();
			velocity.scale(state.parameters.getPredMaxSpeed());
		}
		velocity.scale(state.parameters.getTimeScale());	
		state.environment.moveByDisplacement(id, velocity.x, velocity.y);
	}


	@Override
	public void draw(Graphics2D g2d) {
		g2d.setPaint(predatorColor); 
		Ellipse2D shape = new Ellipse2D.Double(x-2.0,y-2.0,4,4);
		g2d.fill(shape);
	}
}

