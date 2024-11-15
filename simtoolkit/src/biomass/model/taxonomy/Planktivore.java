/**
 * 
 */
package biomass.model.taxonomy;

import java.awt.*;
import java.awt.geom.*;

import biomass.model.behaviors.EscapeBehavior;
import biomass.model.behaviors.ExploreBehavior;
import biomass.model.behaviors.ForrageBehavior;
import biomass.model.behaviors.HideBehavior;
import biomass.model.behaviors.ScoutBehavior3;
import biomass.model.behaviors.RestBehavior;
import biomass.simulator.core.BioMASSModel;
import multiagent.model.agent.Behavior;
import biomass.simulator.gui.DrawableObject;
/**
 * @author candysansores
 *
 */
public final class Planktivore extends Heterotroph implements DrawableObject {
	
	public Planktivore(FunctionalGroup fg, Population p, int age, double x, double y) {
		super(fg, p, age, x, y);
		initAgentBehaviors();
		// TODO Auto-generated constructor stub
	}
	
	//Inicializa los comportamientos del agente
	private void initAgentBehaviors() {

		Behavior[] behaviors = { new HideBehavior(this), new EscapeBehavior(this), new ForrageBehavior(this), new ScoutBehavior3(this), new RestBehavior(this), new ExploreBehavior(this) };
		boolean subsumes[][] = { { false, true, true, true, true, true}, { false, false, true, true, true, true}, { false, false, false, true, true, true}, { false, false, false, false, true, true}, { false, false, false, false, false, true}, { false, false, false, false, false, false} };    
		initBehaviors(behaviors, subsumes);
	}

/*	public boolean step() {
		if(alive) {
			return super.step();
		}
		return false;
	}
*/
	
	
	public void eat(Organism prey){
		// Calculamos el volumen de la boca;
		double mouthVolume = fg.mouthMassCapacityRatio*getLeanMass();
		// Mordemos, es decir extraemos un % de la biomasa de la presa
		double leanmass = prey.extractLeanMassFraction(mouthVolume/prey.volume);
		double fatmass = prey.extractFatMassFraction(mouthVolume/prey.volume);
		// Verificamos cuanto de lo mordido cabe en el est—mago
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
	}
	
	
	
	
	public String toString() {
	    return "Planktivore";
	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub

		//El planktivoro
		g2d.setPaint(drawablecolor); 
		double scale=length*BioMASSModel.scaleFactor*2;
		//Ellipse2D fish_shape = new Ellipse2D.Double(x-scale/2,y-scale/2,scale,scale);
		Rectangle2D fish_shape = new Rectangle2D.Double(x-scale/2,y-scale/2,scale,scale);
		g2d.fill(fish_shape);
	}
	
    


}
