/**
 * 
 */
package biomass.model.taxonomy;

import biomass.simulator.gui.BioMASSGUIFrame;
import multiagent.model.agent.ReactiveAgent;

/**
 * @author candysansores, flavioreyes
 *
 */
public abstract class Organism extends ReactiveAgent implements BiologicalCycles {
	
	public int age; // edad del organismo en dias
	public FunctionalGroup fg;
	public Population population;
	private boolean ishidden=false;
	private Biomass biomass;
	public boolean alive;
	public int agesecs=0;
	public double length;
	public double volume;
	public boolean shout=false;
	public int ageGroup;

    
	public Organism(FunctionalGroup fg, Population population, int age) {
		alive=true;
		biomass=new Biomass();
		this.age=age;
		ageGroup=age/fg.ageClassLapse;
		this.fg=fg;
		this.population=population;
		population.organisms.add(this);
		population.incOrgs(ageGroup);
	}

	
	public double getLeanMass(){
		return biomass.getLeanMass();
	}
	
	
	public double getFatMass(){
		return biomass.getFatMass();
	}
	
		
	
	public double addLeanMass(double grams) {
		// Esta funci—n corrige la solicitud de gramos de biomass con base en lo que queda disponible
		grams = biomass.addLeanMass(grams);
		population.addLeanMass(ageGroup, grams);
		// Regresa el valor en gramos del incremento efectivamente efectuado
		return grams;
	}
	
	
	public double addFatMass(double grams) {
		// Esta funci—n corrige la solicitud de gramos de biomass con base en lo que queda disponible
		grams = biomass.addFatMass(grams);
		population.addFatMass(ageGroup, grams);
		// Regresa el valor en gramos del incremento efectivamente efectuado
		return grams;
	}
	

	public double addRepLeanMass(double grams) {
		// Esta funci—n corrige la solicitud de gramos de biomass con baso en lo que queda disponible
		grams = biomass.addRepLeanMass(grams);
		population.addLeanMass(ageGroup, grams);
		// Regresa el valor en gramos del incremento efectivamente efectuado
		return grams;
	}
	
	
	public double addRepFatMass(double grams) {
		// Esta funci—n corrige la solicitud de gramos de biomass con baso en lo que queda disponible
		grams = biomass.addRepFatMass(grams);
		population.addFatMass(ageGroup, grams);
		// Regresa el valor en gramos del incremento efectivamente efectuado
		return grams;
	}


	public double extractLeanMassFraction(double fraction){
		double mass = biomass.extractLeanMassFraction(fraction);
		population.addKilledMass(ageGroup, mass);
		population.addLeanMass(ageGroup, -mass);
		return mass;
	}

	
	public double extractFatMassFraction(double fraction){
		double mass = biomass.extractFatMassFraction(fraction);
		population.addKilledMass(ageGroup, mass);
		population.addFatMass(ageGroup, -mass);
		return mass;
	}

	
	
	
	public void suddenDeath() {
		BioMASSGUIFrame.getInstance().seaSpace.remove(getID());
		setActive(false);
		if(shout)
			System.out.println("Agent:"+getID()+" killed"); 
		alive=false;	
		population.addLeanMass(ageGroup, -getLeanMass());
		population.addFatMass(ageGroup, -getFatMass());
		population.addKilledMass(ageGroup, getBiomass());
		population.incKilled(ageGroup);
		population.decOrgs(ageGroup);
		population.remove(this);
	}


	public void starve() {
		BioMASSGUIFrame.getInstance().seaSpace.remove(getID());
		setActive(false);
		if(shout)
			System.out.println("Agent:"+getID()+" starved"); 
		alive=false;	
		population.addLeanMass(ageGroup, -getLeanMass());
		population.addFatMass(ageGroup, -getFatMass());
		population.addStarvedMass(ageGroup, getBiomass());
		population.incStarved(ageGroup);
		population.decOrgs(ageGroup);
		population.remove(this);
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see multiagent.model.agent.Agent#step()
	 */
	@Override
	public boolean step(long stepsfwd) {
		int days;
		if(alive) {
			agesecs+=stepsfwd*BioMASSGUIFrame.getInstance().getTimeStep();
			days=(agesecs/86400)-age;
			for(int i=0;i<days;i++) {
				if(alive) {
					dailystep();
					int newAgeGroup=age/fg.ageClassLapse;
					// Verificamos que no se pase de la œltima clase
					if(newAgeGroup>=population.ageClasses)
						newAgeGroup=population.ageClasses-1;
					// Verificamos si cambia de grupo de edad
					if(newAgeGroup>ageGroup){
						population.decOrgs(ageGroup);
						population.incOrgs(newAgeGroup);
						population.addLeanMass(ageGroup, -getLeanMass());
						population.addLeanMass(newAgeGroup, getLeanMass());
						population.addFatMass(ageGroup, -getFatMass());
						population.addFatMass(newAgeGroup, getFatMass());
						ageGroup=newAgeGroup;
					}
				}
			}
			return super.step(stepsfwd);
		}
		return false;
	}


	public double getBiomass() {
		return biomass.getBiomass();
	}
	
	public double getRelativeAge() {
		return age/(fg.lifeSpan);
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
//	public String getName() {
//		return name;
//	}
	
	
	public boolean isHidden() {
		return ishidden;
	}

	public void hide(){
		this.ishidden = true;
		population.incHid(ageGroup);
	}
	

	public void unhide(){
		this.ishidden = false;
	}
	

	public double getReproductionMass() {
		return biomass.getReproductionMass();
	}

	
	
}
