/**
 * 
 */
package biomass.model.taxonomy;

/**
 * @author candysansores
 *
 */
public class Biomass {
	private double leanmass=0;
	private double fatmass=0;
	private double repleanmass=0;
	private double repfatmass=0;

	/**
	 * 
	 */
	public Biomass() {
		// TODO Auto-generated constructor stub
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
	
	public double addLeanMass(double grams) {
		// Esta funci—n corrige la solicitud de gramos de biomass con baso en lo que queda disponible
		if(grams < -leanmass)
			grams = -leanmass;
		leanmass=leanmass+grams;
		// Regresa el valor en gramos del incremento efectivamente efectuado
		return grams;
	}
	
	public double addFatMass(double grams) {
		// Esta funci—n corrige la solicitud de gramos de biomass con baso en lo que queda disponible
		if(grams < -fatmass)
			grams = -fatmass;
		fatmass=fatmass+grams;
		// Regresa el valor en gramos del incremento efectivamente efectuado
		return grams;
	}
	
	public double addRepLeanMass(double grams) {
		// Esta funci—n corrige la solicitud de gramos de biomass con baso en lo que queda disponible
		if(grams < -repleanmass)
			grams = -repleanmass;
		repleanmass=repleanmass+grams;
		// Regresa el valor en gramos del incremento efectivamente efectuado
		return grams;
	}
	
	public double addRepFatMass(double grams) {
		// Esta funci—n corrige la solicitud de gramos de biomass con baso en lo que queda disponible
		if(grams < - repfatmass)
			grams = -repfatmass;
		repfatmass=repfatmass+grams;
		// Regresa el valor en gramos del incremento efectivamente efectuado
		return grams;
	}

	public double extractLeanMassFraction(double fraction){
		double mass;
		// Si est‡ fuera del rango regresa 0
		if(fraction<0 )
			return 0;
		// Si se pide mas de lo que hay se regresa lo que hay
		if(fraction>=1.0 )
			mass = leanmass;
		else
			mass = fraction*leanmass;
		return -addLeanMass(-mass);
	}

	
	public double extractFatMassFraction(double fraction){
		double mass;
		// Si est‡ fuera del rango regresa 0
		if(fraction<0 )
			return 0;
		if(fraction>=1.0 )
			mass = fatmass;
		else
			mass = fraction*fatmass;
		return -addFatMass(-mass);
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
