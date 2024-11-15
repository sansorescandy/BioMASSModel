/**
 * 
 */
package biomass.model.taxonomy;

/**
 * @author candysansores
 *
 */
public final class FunctionalGroup {
	
    //Identificaci—n de los grupos funcionales y sus clases

	public String name;
    public String spec;
    public String commonName;
    public String groupClass;
    public int ageClassLapse=365;
	
	//Par‡metros para el metabolismo

    public double optimalFatMassFraction = 0.17;
    public double maxFattyMassFraction = 0.32;
    public double minFattyMassFraction = 0.02;
    public double optimalLeanMassFraction = 1-optimalFatMassFraction;
    public double maxLeanMassFraction = 1-minFattyMassFraction;
    public double minLeanMassFraction = 1-maxFattyMassFraction;
    public double stomachMassCapacityRatio=0.2; //Size of stomach relative to leanMass
    public double mouthMassCapacityRatio=0.04; //Size of mouth relative to leanMass
    public double appetiteWithoutFatDeficit=0.5; //Nivel de hambre del organismo con el est—mago vac’o sin deficit de grasa
    public double goodPlanktonConcentration; //Concentraci—n suficiente para que se nutra con media hora de alimentaci—n al d’a
    public double bmr=0.03; //Porcentaje de grasa que pierde cada d’a
    
    //Par‡metros generales de los organismos que pertenecen al grupo funcional

    public double density=1; //(en grs/cm3)
    public double maxSpeed; //Maxima velocidad en longitudes del pez por segundo (length/s)
    public double minSpeed;
    
    //Par‡metros para el modelo predador-presa con refugios
    
    public double perceptionRangeFactor; //Radio de percepci—n
    public String[] predators=new String[10];
	public String[] preys=new String[10];
	
    //Par‡metros para el c‡lculo del crecimiento potencial de von Bertalanffy
    public int lifeSpan=0; 	//Maximum lifespan in days
    public double linf=0; //longitud asint—tica del heterotrofo (de acuerdo al modelo de von Bertalanffy)
    public double t0=0;   //valor de tiempo en el que el heterotrofo tuvo una longitud de cero (de acuerdo al modelo de von Bertalanffy)
	public double k=0;
    public double a=0; 	//constante de proporcionalidad en la expresi—n que relaciona el peso con la longitud W = aL^b
    public double b=0; 	// exponente en la expresi—n que relaciona el peso con la longitud W = aL^b
	
    //Par‡metros para prop—sitos de visualizaci—n

    public double shapeWidth; //
    public double shapeLength;
    public double shapeHeight; 
   
    //Par‡metros para Reproduction


    public double firstMaturityLength;	// Longitud en la cual alcanzan la madurez sexual
	public int repStdDev=30; 			// El 68% de los nuevos organismos nacen en el lapso de 30 d’as posterior a la madurez
    public int recruitment;
    
    //Para el plankton

    public double maxBiomassConcentration; //Densidad m‡xima de plankton
    public double radio;

    



	public FunctionalGroup(String name) {
    	setName(name);
    }
	
	
	public void setSpec(String spec){
		this.spec = spec;
	}
	
	public void setCommonName(String commonName){
		this.commonName = commonName;
	}
	
    
    
    /**
	 * @param perceptionRangeFactor the perceptionRangeFactor to set
	 */
	public void setPerceptionRangeFactor(double perceptionRangeFactor) {
		this.perceptionRangeFactor = perceptionRangeFactor;
	}



	/**
	 * @param linf the linf to set
	 */
	public void setLinf(double linf) {
		this.linf = linf;
	}


	/**
	 * @param to the to to set
	 */
	public void setT0(double t0) {
		this.t0 = t0;
	}

	


	/**
	 * @param k the k to set
	 */
	public void setK(double k) {
		this.k = k;
	}

	/**
	 * @param a the a to set
	 */
	public void setA(double a) {
		this.a = a;
	}


	/**
	 * @param b the b to set
	 */
	public void setB(double b) {
		this.b = b;
	}

	/**
	 * @param birthLeanMass the birthLeanMass to set
	 */

	public void setPreys(String[] preys) {
		this.preys = preys;
	}

	public void setLifeSpan(int lifeSpan) {
		this.lifeSpan = lifeSpan;
	}


    public void setPredators(String[] p) {
    	for(int i=0; i<p.length;i++) {
    		predators[i]=new String(p[i]);
    	}
    }
    
	
	public void setGroupClass(String groupClass) {
		this.groupClass = groupClass;
	}
	




    /**
     * Sets the functionalgroup' name.
     */
    public void setName(String name) {
        this.name = name;
    }
  

	/**
	 * @param maxSpeed the maxSpeed to set
	 */
	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
		
	/**
	 * @param minSpeed the minSpeed to set
	 */
	public void setMinSpeed(double minSpeed) {
		this.minSpeed = minSpeed;
	}



	/**
	 * @param maxBiomass the maxBiomass to set
	 */
	public void setMaxBiomassPlanktonConcentration(double maxBiomass) {
		this.maxBiomassConcentration = maxBiomass;
	}




	/**
	 * @param goodPlanktonConcentration the goodPlanktonConcentration to set
	 */
	public void setGoodPlanktonConcentration(double goodPlanktonConcentration) {
		this.goodPlanktonConcentration = goodPlanktonConcentration;
	}
	
    



	public void setRadio(double radio) {
		this.radio = radio;
	}


    public void setAgeClassLapse(int lapse) {
		this.ageClassLapse = lapse;
	}


    public double getFirstMaturityLength() {
		return firstMaturityLength;
	}


	public void setFirstMaturityLength(double firstMaturityLength) {
		this.firstMaturityLength = firstMaturityLength;
	}


	public int getRecruitment() {
		return recruitment;
	}


	public void setRecruitment(int recruitment) {
		this.recruitment = recruitment;
	}


	

}
