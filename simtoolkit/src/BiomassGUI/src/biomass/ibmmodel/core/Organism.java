/**
 * 
 */
package biomass.ibmmodel.core;

import biomass.simulator.gui.BioMASSGUIFrame;
import multiagent.model.agent.ReactiveAgent;

/**
 * @author candysansores
 *
 */
public abstract class Organism extends ReactiveAgent implements BiologicalCycles {
	
	public int age; // edad del organismo en dias
	private String name;
	public FunctionalGroup fg;
	public Population population;
	private boolean ishidden;
	protected Biomass biomass;
	public boolean alive;
	public int agesecs=0;
	public double length;
	public double volume;
	public static int[] arrayplank=new int[3000]; 

    

	public Organism() {
		// TODO Auto-generated constructor stub
		alive=true;
		biomass=new Biomass(population);
	}
	
	public void die(int cause) {
		BioMASSGUIFrame.getInstance().seaSpace.remove(getID());
		setActive(false);
		alive=false;	
		population.remove(this, cause);
		//System.out.println(population.getName()+" Num= "+ population.getPopulation());
		//System.out.println(population.getName()+" Eaten= "+ population.getEaten());
		//System.out.println(population.getName()+" Starvation= "+ population.getStarvation());
		//System.out.println();
		/*if(population.getName().equalsIgnoreCase("Carnivore") && population.getPopulation()==0)
			System.exit(0);*/
	}
	
	
	
	/* (non-Javadoc)
	 * @see multiagent.model.agent.Agent#step()
	 */
	@Override
	public boolean step() {
		// TODO Auto-generated method stub
		agesecs++;
		if(agesecs%3600==0) {
			hourlystep();
			if(agesecs%86400==0)
				dailystep();
		}
		return super.step();
	}


	public Biomass getBiomass() {
		return biomass;
	}
	
	public double getRelativeAge() {
		return age/(fg.longevity);
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	
	
	public boolean isHidden() {
		return ishidden;
	}

	public void setHidden(boolean ishidden) {
		this.ishidden = ishidden;
	}

	public void setRefuge(Refuge refuge){};
	
	
	
}
