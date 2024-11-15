package biomass.model.taxonomy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import biomass.model.utils.vonBertalanffy;
import biomass.simulator.core.PopulationBiomassChartGraph;
import biomass.simulator.core.Bag;
import biomass.simulator.core.BioMASSModel;
import biomass.simulator.gui.BioMASSGUIFrame;

public class Population {
	 //Par‡metros para la generaci—n de la poblaci—n inicial
	private int id;
	private int N0;
	private double deathRate=1.0/365;
	private String name;
	public FunctionalGroup fg;
	//Temporalmente public
	public Bag organisms = new Bag();
	public int ageClasses;
	private int MaxChartPoints=100;
	private int chartedPoints=0;
	private int orgs[];
	private int hid[];
	private int starved[];
	private int killed[];
	private double leanMass[];
	private double fatMass[];
	private double killedMass[];
	private double starvedMass[];
	private double leanMassPoints[];
	private double fatMassPoints[];
	private double killedMassPoints[];
	private double starvedMassPoints[];
	private long stepPoints[];
	private PopulationBiomassChartGraph popbiomasschartgraph;
	BioMASSModel model=BioMASSGUIFrame.getInstance();
	
	//private int populationSize=10; //Se calcular‡ con los par‡metros de la clase funtionalgroup
	
	/**
	 * La clase poblaci—n pareciera tener una funci—n puramente organizacional, ya que simplemente estructura a los organismos
	 * en grupos funcionales, sin embargo, los mecanismos de creaci—n de poblaciones iniciales se encontrar‡n aqui e inclusive
	 * alguna otra funcionalidad propia de las poblaciones 
	 */

	
	public Population(int id, FunctionalGroup fg, int N0, double deathRate) {		
		this.id = id;
		this.fg=fg;
		this.N0=N0;
		this.name=fg.name+String.valueOf(this.id);
		this.deathRate = deathRate;
		// Determinamos cuantos grupos de edad hay en esta poblaci—n
		ageClasses = fg.lifeSpan/fg.ageClassLapse+1;
		// Creamos los contadores y acumuladores
		orgs = new int[ageClasses];
		hid = new int[ageClasses];
		killed = new int[ageClasses];
		starved = new int[ageClasses];
		leanMass = new double[ageClasses];
		fatMass = new double[ageClasses];
		killedMass = new double[ageClasses];
		starvedMass = new double[ageClasses];
		// Inicializamos contadores y acumuladores
		for(int i=0; i<ageClasses;i++){
			orgs[i]=0;
			hid[i]=0;
			killed[i]=0;
			starved[i]=0;
			leanMass[i]=0;
			fatMass[i]=0;
			killedMass[i]=0;
			starvedMass[i]=0;			
		}
		// Creamos los arreglos para los puntos de la gr‡fica
		leanMassPoints = new double[MaxChartPoints];
		fatMassPoints = new double[MaxChartPoints];
		killedMassPoints = new double[MaxChartPoints];
		starvedMassPoints = new double[MaxChartPoints];
		stepPoints = new long[MaxChartPoints];
		// Los inicializamos
		// Creamos la poblaci—n
		createInitialExpDistributionPopulation();
	}

	

	private void createInitialExpDistributionPopulation() {
		int orgsSum=0, classOrd, n;
		double leanmassSum=0, fatmassSum=0;
		int firstAgeClass = ((int) (vonBertalanffy.ageFromLengthT0(fg.firstMaturityLength, fg.linf, fg.k, fg.t0)*365.0/fg.ageClassLapse))*fg.ageClassLapse;
		System.out.println("Population= "+name+" lifespan: "+fg.lifeSpan+" ageclasslapse     = "+fg.ageClassLapse);
		for (int ageClass = firstAgeClass; ageClass<fg.lifeSpan-fg.ageClassLapse; ageClass+=fg.ageClassLapse) {
			// Determinamos la clase correspondiente
			classOrd=ageClass/fg.ageClassLapse;
			// Por cada grupo de edad determinamos cuantos organismos generamos
			n = (int) (N0 * Math.exp(-deathRate * (ageClass-firstAgeClass)));
			// inicializamos contadores y acumuladores para este grupo de edad
			if(n>0){
				addOrganisms(n, ageClass, fg.ageClassLapse);
				orgsSum+=orgs[classOrd];
				leanmassSum+=leanMass[classOrd];
				fatmassSum+=fatMass[classOrd];
				System.out.println("Population= "+name+" class: "+classOrd+" ageclass     = "+ageClass);
				System.out.println("Population= "+name+" class: "+classOrd+" organisms    = "+orgs[classOrd]);
				System.out.println("Population= "+name+" class: "+classOrd+" leanmass     = "+leanMass[classOrd]);
				System.out.println("Population= "+name+" class: "+classOrd+" fatmass      = "+fatMass[classOrd]);
			}
		}
		System.out.println("Population= "+name+" total organisms = "+orgsSum);
		System.out.println("Population= "+name+" total leanmass  = "+leanmassSum);
		System.out.println("Population= "+name+" total fatmass   = "+fatmassSum);
	}
	
	private void addOrganisms(int classSize, int ageClass, int ageClassLapse) {
		Constructor constructor = null;
		Organism organism;
		double x,y;
		int age;

		try {
			//Obtiene la clase a la que pertenecen los organismos de esta poblaci—n
			Class groupClass=Class.forName(fg.groupClass);
			//Obtiene el constructor de la clase a la que pertenecen los organismos de esta poblaci—n
			constructor=groupClass.getConstructor(new Class[] {FunctionalGroup.class, Population.class, int.class, double.class, double.class});
		}
		catch ( ClassNotFoundException e){
			e.printStackTrace();
		} catch ( NoSuchMethodException e){
			e.printStackTrace();
		}
		System.out.println("Population= "+name+" ageclass      = "+ageClass);
		System.out.println("Population= "+name+" ageclasslapse = "+ageClassLapse);
		for (int i=0; i < classSize; ++i) {
			try {
				//Ubica aleatoriamente organismo y le asigna una posici—n aleatoria
				x = model.rand.nextDouble()*model.seaSpace.getWidth()-model.seaSpace.getWidth()/2;
				y = model.rand.nextDouble()*model.seaSpace.getHeight()-model.seaSpace.getHeight()/2;
				age=ageClass+(int)(model.rand.nextDouble()*(ageClassLapse-1));
				organism = (Organism) constructor.newInstance(new Object[] {fg, this, age, x, y});
				model.seaSpace.insert(organism);
				//A–ade al scheduler de la simulaci—n a los organismos que se ejecutar‡n en cada paso de la simulaci—n
				//en un mismo intervalo de tiempo
				model.scheduler.add(organism);		
			}
			catch( InstantiationException e){
				//e.printStackTrace();
			} catch( IllegalAccessException e){
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}	

		}
	}
	
	
	
	public void clear() {
		organisms.clear();
	}
	
	
	public void remove(Organism o){
		organisms.remove(o);
		
	}
		
	
	public boolean step(long steps) {
		//Acumulamos los totales para la poblaci—n
		double leanSum=0, fatSum=0, killedSum=0, starvedSum=0; 
		for(int i=0; i<ageClasses;i++){
			leanSum+=leanMass[i];
			fatSum+=fatMass[i];
			killedSum+=killedMass[i];
			starvedSum+=starvedMass[i];
		}
		popbiomasschartgraph.setNotify(false);
		if(chartedPoints==MaxChartPoints){
			chartedPoints/=2;
			popbiomasschartgraph.clear();
			popbiomasschartgraph.updateLeanmass(0, leanMassPoints[0]);
			popbiomasschartgraph.updateFatmass(0, fatMassPoints[0]);
			//popbiomasschartgraph.updateKilledmass(0, killedMassPoints[0]);
			popbiomasschartgraph.updateStarvedmass(0, starvedMassPoints[0]);
			for(int i=1; i<chartedPoints; i++){
				stepPoints[i]=(stepPoints[i*2-1]+stepPoints[i*2])/2;
				leanMassPoints[i]=(leanMassPoints[i*2-1]+leanMassPoints[i*2])/2;
				fatMassPoints[i]=(fatMassPoints[i*2-1]+fatMassPoints[i*2])/2;
				killedMassPoints[i]=(killedMassPoints[i*2-1]+killedMassPoints[i*2])/2;
				starvedMassPoints[i]=(starvedMassPoints[i*2-1]+starvedMassPoints[i*2])/2;
				popbiomasschartgraph.updateLeanmass((double)(stepPoints[i]*model.getTimeStep())/86400, leanMassPoints[i]);
				popbiomasschartgraph.updateFatmass((double)(stepPoints[i]*model.getTimeStep())/86400, fatMassPoints[i]);
				//popbiomasschartgraph.updateKilledmass((double)(stepPoints[i]*model.getTimeStep())/86400, killedMassPoints[i]);
				popbiomasschartgraph.updateStarvedmass((double)(stepPoints[i]*model.getTimeStep())/86400, starvedMassPoints[i]);
			}
			model.chartSteps*=2;
		}
		leanMassPoints[chartedPoints]=leanSum;
		fatMassPoints[chartedPoints]=fatSum;
		killedMassPoints[chartedPoints]=killedSum;
		starvedMassPoints[chartedPoints]=starvedSum;
		stepPoints[chartedPoints]=steps;
		popbiomasschartgraph.updateLeanmass((double)(steps*model.getTimeStep())/86400, leanSum);
		popbiomasschartgraph.updateFatmass((double)(steps*model.getTimeStep())/86400, fatSum);
		//popbiomasschartgraph.updateKilledmass((double)(steps*model.getTimeStep())/86400, killedSum);
		popbiomasschartgraph.updateStarvedmass((double)(steps*model.getTimeStep())/86400, starvedSum);
		popbiomasschartgraph.setNotify(true);
		chartedPoints++;
		return true;		
	}


	public void start() {
		popbiomasschartgraph=new PopulationBiomassChartGraph(name);
	}


	public void stop() {
		//chartclient.close();
	}
	
	
	public String getName() {
		return name;
	}
	
	public int getPopulation() {
		return organisms.numObjs;
	}
	
	public void incOrgs(int ageOrd){
		orgs[ageOrd]++;
	}
	
		
	public void decOrgs(int ageOrd){
		orgs[ageOrd]--;
	}
	
		
	public void incHid(int ageOrd){
		hid[ageOrd]++;
	}
	
		
	public void decHid(int ageOrd){
		hid[ageOrd]--;
	}
	
		
	public void incKilled(int ageOrd){
		killed[ageOrd]++;
	}
	
	
	public void incStarved(int ageOrd){
		starved[ageOrd]++;
	}
	
	
	public double getLeanMass(int ageOrd) {
		return leanMass[ageOrd];
	}
	
	public void addLeanMass(int ageOrd, double leanmass) {
		this.leanMass[ageOrd]+=leanmass;
	}
	
	public double getFatMass(int ageOrd) {
		return fatMass[ageOrd];
	}

	public void addFatMass(int ageOrd, double fatmass) {
		this.fatMass[ageOrd]+=fatmass;
	}
	
	public double getBiomass(int ageOrd) {
		return leanMass[ageOrd]+fatMass[ageOrd];
	}
	
	public double getStarvedMass(int ageOrd) {
		return starvedMass[ageOrd];
	}
	
	public void addStarvedMass(int ageOrd, double grams) {
		this.starvedMass[ageOrd]+=grams;
	}

	public double getKilledMass(int ageOrd) {
		return killedMass[ageOrd];
	}

	public void addKilledMass(int ageOrd, double grams) {
		this.killedMass[ageOrd]+=grams;
	}


	public int getHid(int ageOrd) {
		return hid[ageOrd];
	}

	
	public void resetHid(int ageOrd){
		hid[ageOrd]=0;
	}

	public int getKilled(int ageOrd) {
		return killed[ageOrd];
	}


	public int getStarved(int ageOrd) {
		return starved[ageOrd];
	}



	public int getOrgs(int ageOrd) {
		return orgs[ageOrd];
	}


	public double getDeathRate() {
		return deathRate;
	}



	public void setDeathRate(double deathRate) {
		this.deathRate = deathRate;
	}

    public int getNewbornOrgs() {
		return N0;
	}



	public void setNewbornOrgs(int newbornOrgs) {
		this.N0 = newbornOrgs;
	}




	
	
}
