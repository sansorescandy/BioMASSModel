/**
 * 
 */
package biomass.ibmmodel.core;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;


import multiagent.model.agent.Agent;
import multiagent.model.agent.Behavior;
import biomass.simulator.core.Bag;
import biomass.simulator.core.BioMASSModel;
import biomass.simulator.gui.BioMASSGUIFrame;
import biomass.simulator.gui.DrawableObject;
import biomass.ibmmodel.utils.*;
/**
 * @author candysansores
 *
 */
public class Planktivore extends Heterotroph implements DrawableObject {
	
	public Planktivore(FunctionalGroup fg, Population p, int age, double x, double y) {
		super(fg, p, age);
		this.x=x;
		this.y=y;
		//super(fg,age);
		initAgentBehaviors();
		// TODO Auto-generated constructor stub
		//drawablecolor=Color.BLUE;
	}
	
	public Planktivore(FunctionalGroup fg, Population p, Biomass b, double x, double y) {
		super(fg, p, b);
		this.x=x;
		this.y=y;
		//super(fg,age);
		initAgentBehaviors();
		// TODO Auto-generated constructor stub
		//drawablecolor=Color.BLUE;
	}
	
	//Inicializa los comportamientos del agente
	private void initAgentBehaviors() {

		Behavior[] behaviors = { new EscapeBehavior(this), new ForragingBehavior(this), new ScoutingBehavior(this) };
		//Behavior[] behaviors = { new EscapeBehavior(), new SwarmBehavior() };
		boolean subsumes[][] = { { false, true, true}, { false, false, true}, { false, false, false} };    
		//boolean subsumes[][] = { { false, false} };
		initBehaviors(behaviors, subsumes);
	}

	public boolean step() {
		if(alive) {
			double dist_to_refuge = BioMASSGUIFrame.getInstance().seaSpace.getDimensionDistance(getID(), getRefuge().getID());
			if(dist_to_refuge<fg.getHiddenThreshold())
				setHidden(true);
			else
				setHidden(false);
			//double h=getHunger();
			/*if(h < 1.0)
		        setHunger(h+0.0005);
			else
				setHunger(0);*/
			return super.step();
		}
		return false;
	}
	
	public void eat(Organism prey) {
		double leanmassincrement;
		double fatmassincrement;
		
		//arrayplank[getID()]++;
		/*if(getID()==or)
			System.out.println("Hambre1: "+this.getHunger()+" ID: "+getID());*/
		
		//Calculamos la biomasa aportada por la presa
		//Calculamos el volumen de agua que le cabe en la boca y lo multiplicamos por la concentraci—n de plankton (biomasa/volumen)
		//con esto obtenemos la biomasa filtrada
		double food=(fg.mouthMassCapacityRatio*biomass.getLeanMass())*prey.biomass.getBiomass()/prey.volume;
		//Calculamos los gramos de capacidad disponible en el est—mago
		double stomach_actualcapacity=(fg.stomachMassCapacityRatio*biomass.getLeanMass())-(stomachLeanMass+stomachFatMass);
		/*if(getID()==or)
			System.out.println("Capacity: "+stomach_actualcapacity);*/
		//Si la biomasa capturada es mayor que la que cabe en el est—mago
		if(stomach_actualcapacity<food) {
			//Ingresamos prote’na en el est—mago en la misma proporci—n disponible en la presa
			leanmassincrement=prey.biomass.getLeanMass()/prey.biomass.getBiomass()*stomach_actualcapacity;
			//Ingresamos grasa en el est—mago en la misma proporci—n disponible en la presa
			fatmassincrement=prey.biomass.getFatMass()/prey.biomass.getBiomass()*stomach_actualcapacity;
			//System.out.println("SLURP! Lean %biomass: "+biomass.getLeanMass()/biomass.getBiomass()+" Fat %biomass: "+biomass.getFatMass()/biomass.getBiomass()+" Length: "+this.length);
		}
		else {
			leanmassincrement=prey.biomass.getLeanMass()/prey.biomass.getBiomass()*food;
			fatmassincrement=prey.biomass.getFatMass()/prey.biomass.getBiomass()*food;
		}
		//Los plant’voros no desperdician al comer.
		////Suma a la biomasa desperdiciada de la poblaci—n lo que no se come en prote’na
		//population.addWasteLeanMass(organism.getBiomass().getLeanMass()-leanmassincrement);
		////Suma a la biomasa desperdiciada de la poblaci—n lo que no se come en grasa
		//population.addWasteFatMass(organism.getBiomass().getFatMass()-fatmassincrement);
		stomachLeanMass+=leanmassincrement;
		stomachFatMass+=fatmassincrement;
		
		//Plankton, se disminuye la biomasa del plankton
		prey.biomass.addLeanMass(-leanmassincrement);
		prey.biomass.addFatMass(-fatmassincrement);
		prey.population.addEatenMass(leanmassincrement+fatmassincrement);
		/*if(getID()==or) {
		System.out.println("Hambre2: "+this.getHunger());
		double actualcapacity=(fg.stomachMassCapacityRatio*biomass.getLeanMass())-(stomachLeanMass+stomachFatMass);
		System.out.println("Capacity: "+actualcapacity); }*/
		/*if(biomass.getFatMass()/biomass.getBiomass()<this.fg.minFattyMassFraction+.01)
			System.out.println("Eat! ID:"+this.id+"     Fat %biomass: "+biomass.getFatMass()/biomass.getBiomass()+ "     StomachCapacity: "+stomach_actualcapacity+"     Hambre: "+this.getHunger());*/
	}
	
	
	public String toString() {
	    return "Planktivore";
	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		Color perceptionColor= new Color(127,0,127);
		
		/*double scale=length*4;
		
		if(biomass.getFatMass()/biomass.getBiomass()<this.fg.minFattyMassFraction+.01) {
			drawablecolor=Color.BLACK;
			scale=scale*100;
		}*/

		if(pregnant)
			drawablecolor=new Color(255,200,0);
		//El planktivoro
		g2d.setPaint(drawablecolor); 
		double scale=length*4;
		Ellipse2D fish_shape = new Ellipse2D.Double(x-scale/2,y-scale/2,scale,scale);
		//Ellipse2D fish_shape = new Ellipse2D.Double(x-lenght/2,y-lenght/2,lenght,lenght);
		g2d.fill(fish_shape);


		//Su percepci—n

		//Transparencia
			   // Composite originalComposite = g2d.getComposite(); 
			    //g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
		//Transparencia
/*		
			g2d.setPaint(perceptionColor);
			double p=fg.getPerceptionRangeFactor()*length;
			Ellipse2D perception_shape = new Ellipse2D.Double(x-p,y-p,p*2,p*2);
			g2d.draw(perception_shape);*/
		


		//Transparencia
			  //  g2d.setComposite(originalComposite);
	}
	
    


}
