/**
 * 
 */
package biomass.ibmmodel.core;

/**
 * @author candysansores
 *
 */
public final class FunctionalGroup {
	
    //Identificaci—n de los grupos funcionales y sus clases
    private String name;
    private String groupClass;
    private OrganismType organismtype; //0=Heterotroph, 1=Autotroph, 2=Decomposer
	
	//Par‡metros para el metabolismo
    public double optimalFatMassFraction = 0.12;
    public double maxFattyMassFraction = 0.32;
    public double minFattyMassFraction = 0.05;
    
    public double optimalLeanMassFraction = 1-optimalFatMassFraction;
    public double maxLeanMassFraction = 1-minFattyMassFraction;
    public double minLeanMassFraction = 1-maxFattyMassFraction;
    
    public double stomachMassCapacityRatio=0.1; //Size of stomach relative to leanMass
    public double mouthMassCapacityRatio=0.04; //Size of mouth relative to leanMass
    public double appetiteWithoutFatDeficit=0.5; //Nivel de hambre del organismo con el est—mago vac’o sin deficit de grasa
    
    public double goodPlanktonConcentration; //Concentraci—n suficiente para que se nutra con media hora de alimentaci—n al d’a
    
    
    public double hbmr=0.01; //Porcentaje de grasa que pierde cada hora

	
    
    //Par‡metros generales de los organismos que pertenecen al grupo fncional
    public double density=1; //(en grs/cm3)
    public int longevity; //Maximum lifespan in days
    private double maxSpeed; //Maxima velocidad en longitudes del pez por segundo (length/s)
    private double wanderSpeed;
    private double foragingSpeed;
   
    
    //Par‡metros para el modelo predador-presa con refugios
    private boolean hideBehavior; //Es un pez que le gusta esconderse o no?
    
    private double perceptionRangeFactor; //Radio de percepci—n
    private double hiddenThreshold; //Se considera escondido mientras la distancia al refugio sea de los centimetros determinados
    private String[] predators=new String[10];
	private String[] preys=new String[10];
	
    
    //Par‡metros para el c‡lculo del crecimiento potencial de Bertalanffy
    public double linf; //longitud asint—tica del heterotrofo (de acuerdo al modelo de Bertalanffy)
    public double lo;
    public double k;
    public double t0; //valor de tiempo en el que el heterotrofo tuvo una longitud de cero (de acuerdo al modelo de Bertalanffy)
    public double a; //constante de proporcionalidad en la expresi—n que relaciona el peso con la longitud W = aL^b
    public double b; // exponente en la expresi—n que relaciona el peso con la longitud W = aL^b
   
   
    //Par‡metros para Reproduction
    public double maturitylength=11.5; //Longitud en la cual alcanzan la madurez sexual
    public int reproductioncycle; // Reproduction cycle in days
    public int reproductionlapse=15;
    public double reproductionbiomassfraction=0.05;
    //public int 
    
    //Para el plankton
    public double maxBiomassConcentration; //Densidad m‡xima de plankton

    
	public FunctionalGroup(String name) {
    	setName(name);
    }
	
	
	
    
    /**
	 * @param perceptionRangeFactor the perceptionRangeFactor to set
	 */
	public void setPerceptionRangeFactor(double perceptionRangeFactor) {
		this.perceptionRangeFactor = perceptionRangeFactor;
	}

	/**
	 * @return the perceptionRangeFactor
	 */   
    public double getPerceptionRangeFactor() {
		return perceptionRangeFactor;
	}



	/**
	 * @return the linf
	 */
	public double getLinf() {
		return linf;
	}




	/**
	 * @param linf the linf to set
	 */
	public void setLinf(double linf) {
		this.linf = linf;
	}




	/**
	 * @return the lo
	 */
	public double getLo() {
		return lo;
	}




	/**
	 * @param lo the lo to set
	 */
	public void setLo(double lo) {
		this.lo = lo;
	}




	/**
	 * @return the k
	 */
	public double getK() {
		return k;
	}




	/**
	 * @param k the k to set
	 */
	public void setK(double k) {
		this.k = k;
	}




	/**
	 * @return the a
	 */
	public double getA() {
		return a;
	}




	/**
	 * @param a the a to set
	 */
	public void setA(double a) {
		this.a = a;
	}




	/**
	 * @return the b
	 */
	public double getB() {
		return b;
	}




	/**
	 * @param b the b to set
	 */
	public void setB(double b) {
		this.b = b;
	}


	/**
	 * @return the hideBehavior
	 */
	public boolean isHideBehaviorActive() {
		return hideBehavior;
	}

	/**
	 * @param hideBehavior the hideBehavior to set
	 */
	public void setHideBehavior(boolean hideBehaviorActive) {
		this.hideBehavior = hideBehaviorActive;
	}

	public double getHiddenThreshold() {
		return hiddenThreshold;
	}

	public void setHiddenThreshold(double hiddenThreshold) {
		this.hiddenThreshold = hiddenThreshold;
	}

	public String[] getPreys() {
		return preys;
	}

	public void setPreys(String[] preys) {
		this.preys = preys;
	}


	public int getLongevity() {
		return longevity;
	}

	public void setLongevity(int longevity) {
		this.longevity = longevity;
	}


    public void setPredators(String[] p) {
    	for(int i=0; i<p.length;i++) {
    		predators[i]=new String(p[i]);
    	}
    }
    
    public String[] getPredators() {
    	return predators;
    }
    
	
	public String getGroupClass() {
		return groupClass;
	}

	public void setGroupClass(String groupClass) {
		this.groupClass = groupClass;
	}
	

    /**
	 * @return the type
	 */
	public OrganismType getOrganismType() {
		return organismtype;
	}


	/**
	 * @param type the type to set
	 */
	public void setOrganismType(OrganismType organismtype) {
		this.organismtype = organismtype;
	}


	/**
     * Gets the functionalgroup' name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the functionalgroup' name.
     */
    public void setName(String name) {
        this.name = name;
    }
  

    /**
	 * @return the maxSpeed
	 */
	public double getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * @param maxSpeed the maxSpeed to set
	 */
	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
		

	/**
	 * @return the wanderSpeed
	 */
	public double getWanderSpeed() {
		return wanderSpeed;
	}

	/**
	 * @param wanderSpeed the wanderSpeed to set
	 */
	public void setWanderSpeed(double wanderSpeed) {
		this.wanderSpeed = wanderSpeed;
	}
		

	/**
	 * @return the foragingSpeed
	 */
	public double getForagingSpeed() {
		return foragingSpeed;
	}

	/**
	 * @param foragingSpeed the foragingSpeed to set
	 */
	public void setForagingSpeed(double foragingSpeed) {
		this.foragingSpeed = foragingSpeed;
	}




	/**
	 * @return the maxBiomass
	 */
	public double getMaxBiomassPlanktonConcentration() {
		return maxBiomassConcentration;
	}




	/**
	 * @param maxBiomass the maxBiomass to set
	 */
	public void setMaxBiomassPlanktonConcentration(double maxBiomass) {
		this.maxBiomassConcentration = maxBiomass;
	}




	/**
	 * @return the goodPlanktonConcentration
	 */
	public double getGoodPlanktonConcentration() {
		return goodPlanktonConcentration;
	}




	/**
	 * @param goodPlanktonConcentration the goodPlanktonConcentration to set
	 */
	public void setGoodPlanktonConcentration(double goodPlanktonConcentration) {
		this.goodPlanktonConcentration = goodPlanktonConcentration;
	}
	
	
	

}
