/**
 * 
 */
package biomass.ibmmodel.core;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import multiagent.model.agent.Behavior;
import biomass.ibmmodel.utils.*;
import biomass.simulator.core.BioMASSModel;
import biomass.simulator.gui.BioMASSGUIFrame;
import biomass.simulator.gui.DrawableObject;

/**
 * @author candysansores
 *
 */
public class Carnivore extends Heterotroph implements DrawableObject {
	
	public Carnivore(FunctionalGroup fg, Population p, int age, double x, double y) {
		super(fg, p, age);
		this.x=x;
		this.y=y;
		initAgentBehaviors();
		// TODO Auto-generated constructor stub
		//drawablecolor=Color.RED;
	}
	
	
	//Inicializa los comportamientos del agente
	private void initAgentBehaviors() {

		Behavior[] behaviors = { new HuntingBehavior(this), new WanderingBehavior(this) };
		boolean subsumes[][] = { { false, true}, { false, false}  };    
		initBehaviors(behaviors, subsumes);
	}
	

	public void eat(Organism prey) {
		/*if (getID()==10){
		System.out.println("EAT ID= "+getID()+" StomachLean= "+stomachLeanMass+" StomachFat= "+stomachFatMass);
		System.out.println("FatDeficit= "+(1-biomass.getFatMass()*fg.optimalLeanMassFraction/(biomass.getLeanMass()*fg.optimalFatMassFraction)));
		System.out.println("optLean= "+fg.optimalLeanMassFraction+ " optFat= "+ fg.optimalFatMassFraction);
		System.out.println("Vacuity= "+getStomachRelativeVacuity());
		System.out.println("Hambre= "+getHunger());
		}*/
		double leanmassincrement;
		double fatmassincrement;
		//Calculamos los gramos de capacidad disponible en el est—mago
		double stomach_actualcapacity=(fg.stomachMassCapacityRatio*biomass.getLeanMass())-(stomachLeanMass+stomachFatMass);
		//Calculamos la biomasa aportada por la presa
		double food=prey.biomass.getBiomass();
		//Si esta biomasa es mayor que la que cabe en el est—mago
		if(stomach_actualcapacity<food) {
			//Ingresamos prote’na en el est—mago en la misma proporci—n disponible en la presa
			leanmassincrement=prey.biomass.getLeanMass()/food*stomach_actualcapacity;
			//Ingresamos grasa en el est—mago en la misma proporci—n disponible en la presa
			fatmassincrement=prey.biomass.getFatMass()/food*stomach_actualcapacity;
			//Suma a la biomasa desperdiciada de la poblaci—n lo que no se come en prote’na
			population.addWasteLeanMass(prey.biomass.getLeanMass()-leanmassincrement);
			//Suma a la biomasa desperdiciada de la poblaci—n lo que no se come en grasa
			population.addWasteFatMass(prey.biomass.getFatMass()-fatmassincrement);	
		}
		else {
			leanmassincrement=prey.biomass.getLeanMass();
			fatmassincrement=prey.biomass.getFatMass();
		}
		
		stomachLeanMass+=leanmassincrement;
		stomachFatMass+=fatmassincrement;
		prey.die(Eaten);
		
		
		/*if (getID()==10){
			System.out.println("ID: "+getID()+" StomachLean= "+stomachLeanMass+" StomachFat= "+stomachFatMass);
			System.out.println("FatDeficit= "+(1-biomass.getFatMass()*fg.optimalLeanMassFraction/(biomass.getLeanMass()*fg.optimalFatMassFraction)));
			System.out.println("optLean= "+fg.optimalLeanMassFraction+ " optFat= "+ fg.optimalFatMassFraction);
			System.out.println("Vacuity= "+getStomachRelativeVacuity());
			System.out.println("Hambre= "+getHunger());
			}*/
	}


	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		Color perceptionColor= new Color(100,0,50);

		//El carnivoro
		g2d.setPaint(drawablecolor); 
		double scale=length*2;
		Ellipse2D fish_shape = new Ellipse2D.Double(getX()-scale/2,getY()-scale/2,scale,scale);
		//Ellipse2D fish_shape = new Ellipse2D.Double(x-lenght/2,y-lenght/2,lenght,lenght);
		g2d.fill(fish_shape);


		//Su percepci—n

		//Transparencia
		//		    Composite originalComposite = g2d.getComposite(); 
		//		    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
		//Transparencia

		//g2d.setPaint(perceptionColor);
		//double p=fg.getPerceptionRangeFactor()*length;
		//Ellipse2D perception_shape = new Ellipse2D.Double(getX()-p,getY()-p,p*2,p*2);
		//g2d.draw(perception_shape);


		//Transparencia
		//		    g2d.setComposite(originalComposite);

	}	

}
