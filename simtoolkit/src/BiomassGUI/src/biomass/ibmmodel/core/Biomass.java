/**
 * 
 */
package biomass.ibmmodel.core;

/**
 * @author candysansores
 *
 */
public class Biomass {
	private Population population;
	private double leanmass=0;
	private double fatmass=0;
	private double repleanmass=0;
	private double repfatmass=0;

	/**
	 * 
	 */
	public Biomass(Population population) {
		// TODO Auto-generated constructor stub
		this.population=population;
	}

	/**
	 * @return the leanMass
	 */
	public double getLeanMass() {
		return leanmass;
	}

	/**
	 * @return the fatMass
	 */
	public double getFatMass() {
		return fatmass;
	}

	
	public double getBiomass() {
		return leanmass+fatmass;
	}
	
	public void addLeanMass(double grams) {
		leanmass=leanmass+grams;
		if(leanmass<0) leanmass=0;
		else population.addLeanmass(grams);
	}
	
	public void addFatMass(double grams) {
		fatmass=fatmass+grams;
		if(fatmass<0) fatmass=0;
		else population.addFatmass(grams);
	}
	
	public void addRepLeanMass(double grams) {
		repleanmass=repleanmass+grams;
		if(repleanmass<0) repleanmass=0;
		else population.addLeanmass(grams);
	}
	
	public void addRepFatMass(double grams) {
		repfatmass=repfatmass+grams;
		if(repfatmass<0) repfatmass=0;
		else population.addFatmass(grams);
	}

	/**
	 * @return the repleanmass
	 */
	public double getRepLeanMass() {
		return repleanmass;
	}

	public double getReproductionMass() {
		return repleanmass+repfatmass;
	}

	/**
	 * @return the repfatmass
	 */
	public double getRepFatMass() {
		return repfatmass;
	}

	
	
	

}
