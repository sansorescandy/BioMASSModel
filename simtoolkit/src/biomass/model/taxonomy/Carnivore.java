/**
 * 
 */
package biomass.model.taxonomy;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import biomass.model.behaviors.HuntBehavior;
import biomass.model.behaviors.WanderBehavior;
import biomass.simulator.core.BioMASSModel;
import multiagent.model.agent.Behavior;
import biomass.simulator.gui.DrawableObject;

/**
 * @author candysansores
 *
 */
public final class Carnivore extends Heterotroph implements DrawableObject {
	
	public Carnivore(FunctionalGroup fg, Population p, int age, double x, double y) {
		super(fg, p, age, x, y);
		initAgentBehaviors();
		// TODO Auto-generated constructor stub
	}
	
	
	//Inicializa los comportamientos del agente
	private void initAgentBehaviors() {

		Behavior[] behaviors = { new HuntBehavior(this), new WanderBehavior(this)};
		boolean subsumes[][] = { { false, true}, { false, false}  };    
		initBehaviors(behaviors, subsumes);
	}
	

	public void eat(Organism prey) {
		// Calculamos el volumen de la boca;
		double mouthVolume = fg.mouthMassCapacityRatio*getLeanMass();
		// Mordemos, es decir extraemos un % de la biomasa de la presa
		double leanmass = prey.extractLeanMassFraction(mouthVolume/prey.getBiomass());
		double fatmass = prey.extractFatMassFraction(mouthVolume/prey.getBiomass());
		// Verificamos cuanto de lo mordido cabe en el est�mago
		double stomachCurrentCapacityRatio = (fg.stomachMassCapacityRatio*getLeanMass()-stomachLeanMass-stomachFatMass)/(leanmass+fatmass);
		if(stomachCurrentCapacityRatio>=1){
			// Cabe todo el bocado
			stomachLeanMass += leanmass;
			stomachFatMass += fatmass;
		}	
		else{
			// Solo cabe una parte del bocado
			stomachLeanMass += leanmass*stomachCurrentCapacityRatio;
			stomachFatMass += fatmass*stomachCurrentCapacityRatio;
		}						
		prey.suddenDeath();		
	}


	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub

		//El carnivoro
		g2d.setPaint(drawablecolor); 
		double scale=length*BioMASSModel.scaleFactor;
		Ellipse2D fish_shape = new Ellipse2D.Double(getX()-scale/2,getY()-scale/2,scale,scale);
		g2d.fill(fish_shape);
	}	

}
