package biomass.ibmmodel.core;

import java.awt.EventQueue;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import biomass.simulator.core.BiomassAgeGroupChartGraph;
import biomass.simulator.core.ChartGraph;
import biomass.simulator.core.PopulationBiomassChartGraph;
import biomass.simulator.core.PopulationChartGraph;
import biomass.simulator.core.PopulationWasteChartGraph;
import biomass.simulator.core.SteppableObject;
import biomass.simulator.core.Bag;
import biomass.simulator.core.BioMASSModel;
import biomass.simulator.gui.BioMASSGUIFrame;
import biomass.ibmmodel.utils.test.ChartClient;

public class Population implements SteppableObject {
	 //Par‡metros para la generaci—n de la poblaci—n inicial
	private int initialPopulationAgeClass;
    private double deathRate=0.4/365;
	private String name;
	public FunctionalGroup fg;
	//Temporalmente public
	public Bag organisms = new Bag();
	private BioMASSModel m;
	private int starved=0;
	private int eaten=0;
	private double leanmass=0;
	private double fatmass=0;
	private double eatenmass=0;
	private double starvedmass=0;
	private double wastefatmass=0;
	private double wasteleanmass=0;
	private int classgroups;
	private int[] classcount;
	private double[] classmass;
	private PopulationChartGraph popchartgraph;
	private PopulationBiomassChartGraph popbiomasschartgraph;
	private BiomassAgeGroupChartGraph bioagegroupchartgraph;
	private PopulationWasteChartGraph popwastechartgraph;
	private ChartClient chartclient;
	private PopulationData populationdata;
	
	//private int populationSize=10; //Se calcular‡ con los par‡metros de la clase funtionalgroup
	
	/**
	 * La clase poblaci—n pareciera tener una funci—n puramente organizacional, ya que simplemente estructura a los organismos
	 * en grupos funcionales, sin embargo, los mecanismos de creaci—n de poblaciones iniciales se encontrar‡n aqui e inclusive
	 * alguna otra funcionalidad propia de las poblaciones 
	 */
	public Population(FunctionalGroup fg, int initialPopulationAgeClass) {		
		// TODO Auto-generated constructor stub
		this.fg=fg;
		this.initialPopulationAgeClass=initialPopulationAgeClass;
		m=BioMASSGUIFrame.getInstance();
		name=fg.getName();
		populationdata=new PopulationData();
		populationdata.name=name;
		classgroups=fg.longevity/fg.reproductioncycle+1;
		classcount = new int[classgroups];
		classmass = new double[classgroups];
		if(name!="Plankton")
			createInitialExpDistributionPopulation();
		else
			createPlanktonDistribution();
		System.out.println("Poblaci—n= "+name+" Num= "+organisms.numObjs);
	}

	
	private void createPlanktonDistribution() {
		Organism organism;
		double b=0;
		for(int i=1;i<=40;i++)
			for(int j=1; j<=32;j++) {
				organism=new Plankton(fg, this, fg.getMaxBiomassPlanktonConcentration()/*BioMASSGUIFrame.getInstance().r.nextDouble()*/, 13.75, i*(m.seaSpace.getWidth()/41)-m.seaSpace.getWidth()/2, j*(m.seaSpace.getHeight()/33)-m.seaSpace.getHeight()/2);
				m.seaSpace.insert(organism);
				organisms.add(organism);
				b+=organism.biomass.getBiomass();
				m.scheduler.add(organism);
			}
		System.out.println("Biomasa: "+b);
	}

	private void createInitialExpDistributionPopulation() {
		int groupSize;
		for (int age = 0; age < fg.getLongevity(); age+=fg.reproductioncycle) {
			groupSize = (int) (initialPopulationAgeClass * Math.exp(-deathRate * age));
			addOrganisms(groupSize, age);
			System.out.println("Grupo: "+age+" Cantidad: "+groupSize);
			//System.out.println("Edad: "+age+" "+"Individuos: "+groupSize+" Long:"+lengthFromBirthLengthBertalanffyGrowthFunction((double) age/365.0)+" Peso:"+weightFromLengthBertalanffyGrowthFunction(lengthFromBirthLengthBertalanffyGrowthFunction((double) age/365)));
			/*for(int j=0; j<16;j++)
			    addOrganisms(groupSize, age);*/
		}
	}
	
	private void addOrganisms(int groupSize, int age) {
		Constructor constructor = null;
		Organism organism;
		double x,y;
		int id;

		try {
			//Obtiene la clase a la que pertenecen los organismos de esta poblaci—n
			Class groupClass=Class.forName(fg.getGroupClass());
			//Obtiene el constructor de la clase a la que pertenecen los organismos de esta poblaci—n
			constructor=groupClass.getConstructor(new Class[] {FunctionalGroup.class, Population.class, int.class, double.class, double.class});
		}
		catch ( ClassNotFoundException e){
			e.printStackTrace();
		} catch ( NoSuchMethodException e){
			e.printStackTrace();
		}
		for (int i=0; i < groupSize; ++i) {
			try {
				if(fg.isHideBehaviorActive()) {
					//Selecciona un refugio al azar
					Refuge refuge=(Refuge)m.refuges.objs[m.r.nextInt(m.refuges.numObjs)];
					//Posiciona a los agentes en el refugio
					//x=refuge.getX();
					//y=refuge.getY();
					organism = (Organism) constructor.newInstance(new Object[] {fg, this, age, refuge.getX(), refuge.getY()});
					organism.setRefuge(refuge);
					m.seaSpace.insert(refuge.getID(),organism);
				}
				else {
					//Ubica aleatoriamente organismo y le asigna una posici—n aleatoria
					x = m.r.nextDouble()*m.seaSpace.getWidth()-m.seaSpace.getWidth()/2;
					y = m.r.nextDouble()*m.seaSpace.getHeight()-m.seaSpace.getHeight()/2;
					organism = (Organism) constructor.newInstance(new Object[] {fg, this, age, x, y});
					m.seaSpace.insert(organism);
				}

				//Inicia su velocidad, LA MAGNITUD DE LA VELOCIDAD SERç PROPORCIONAL A SU TAMA„O, POR AHORA ES 10
				//Y SîLO NOS INTERESA LA DIRECCIîN EN ESTE MOMENTO INICIAL
				organism.getVelocity().Assign(m.r.nextDouble()*2-1, m.r.nextDouble()*2-1, 10);
				organisms.add(organism);

				//A–ade al scheduler de la simulaci—n a los organismos que se ejecutar‡n en cada paso de la simulaci—n
				//en un mismo intervalo de tiempo
				m.scheduler.add(organism);		
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
	
	public void remove(Organism o, int cause) {		
		switch(cause) {
		case 0: populationdata.eaten++;
				populationdata.eatenmass+=o.biomass.getLeanMass()+o.biomass.getFatMass();
				break;
		case 1: populationdata.starved++;
				populationdata.starvedmass+=o.biomass.getLeanMass()+o.biomass.getFatMass();
				break;
	   /*default: leanmass-=o.biomass.getLeanMass();	
				fatmass-=o.biomass.getFatMass();	
				deadmass+=o.biomass.getLeanMass()+o.biomass.getFatMass();*/
		}	
		populationdata.leanmass-=o.biomass.getLeanMass();	
		populationdata.fatmass-=o.biomass.getFatMass();	
		organisms.remove(o);
	}
	
	
	public String getName() {
		return name;
	}
	
	public int getPopulation() {
		return organisms.numObjs;
	}
	
	public int getEaten() {
		return populationdata.eaten;
	}
	
	public int getStarved() {
		return populationdata.starved;
	}

	public void clear() {
		organisms.clear();
	}
	
	public void addLeanmass(double leanmass) {
		//this.leanmass+=leanmass;
		populationdata.leanmass+=leanmass;
	}
	
	public void addFatmass(double fatmass) {
		//this.fatmass+=fatmass;
		populationdata.fatmass+=fatmass;
	}
	
	public double getBiomass() {
		return populationdata.leanmass+populationdata.fatmass;
	}
	
	public double getLeanMass() {
		return populationdata.leanmass;
	}
	
	public double getFatMass() {
		return populationdata.fatmass;
	}

	public double getEatenMass() {
		return populationdata.eatenmass;
	}

	public double getStarvedMass() {
		return populationdata.starvedmass;
	}
	
	public void addWasteFatMass(double wastefatmass) {
		populationdata.wastefatmass+=wastefatmass;
	}
	
	public void addWasteLeanMass(double wasteleanmass) {
		populationdata.wasteleanmass+=wasteleanmass;
	}
	
	public double getWasteFatMass() {
		return populationdata.wastefatmass;
	}
	
	public double getWasteLeanMass() {
		return populationdata.wasteleanmass;
	}
	
	public void addEatenMass(double grams) {
		populationdata.eatenmass+=grams;
	}


	@Override
	public void step(long step) {
		// TODO Auto-generated method stub
		switch(fg.getOrganismType()) {
		case HETEROTROPH:	
			for(int i=0;i<classgroups;i++) {
				classcount[i]=0;
				classmass[i]=0;
			}
			int classgroup;
			for(Object o:organisms) {
				classgroup=((Organism)o).age/fg.reproductioncycle;
				if(classgroup>=classgroups) //Este caso es para los que alcanzan la m‡xima edad
					classgroup=classgroups-1;
				classcount[classgroup]++;
				classmass[classgroup]+=((Organism)o).biomass.getBiomass()+((Organism)o).biomass.getReproductionMass();
			}
			//Totales: organismos y grupos de edades
			//popchartgraph.updatePopulation(step, organisms.numObjs);
			popchartgraph.setNotify(false);
			//popchartgraph.updateStarved(step, populationdata.starved);
			//popchartgraph.updateEaten(step, populationdata.eaten);
			popchartgraph.updateAgeGroups(step, classcount);
			popchartgraph.setNotify(true);
			/*popchartgraph.updateAgeGroup1(step, classcount[1]);
			popchartgraph.updateAgeGroup2(step, classcount[2]);
			popchartgraph.updateAgeGroup3(step, classcount[3]);
			popchartgraph.updateAgeGroup4(step, classcount[4]);*/
			//Totales: biomasa poblaci—n
			popbiomasschartgraph.setNotify(false);
			popbiomasschartgraph.updateLeanmass(step, populationdata.leanmass);
			popbiomasschartgraph.updateFatmass(step, populationdata.fatmass);
			popbiomasschartgraph.updateStarvedmass(step, populationdata.starvedmass);
			popbiomasschartgraph.setNotify(true);
			//popbiomasschartgraph.updateEatenmass(step, populationdata.eatenmass);
			//popbiomasschartgraph.updateWasteLeanmass(step, populationdata.wasteleanmass);
			//popbiomasschartgraph.updateWasteFatmass(step, populationdata.wastefatmass);
			//Totales: biomasa por grupos de edades
			bioagegroupchartgraph.setNotify(false);
			bioagegroupchartgraph.updateAgeGroups(step, classmass);
			bioagegroupchartgraph.setNotify(true);
			
			popwastechartgraph.setNotify(false);
			popwastechartgraph.updateWasteLeanmass(step, populationdata.wasteleanmass);
			popwastechartgraph.updateWasteFatmass(step,populationdata.wastefatmass);
			popwastechartgraph.setNotify(true);
			/*bioagegroupchartgraph.updateBioAgeGroup0(step, classmass[0]);
			bioagegroupchartgraph.updateBioAgeGroup1(step, classmass[1]);
			bioagegroupchartgraph.updateBioAgeGroup2(step, classmass[2]);
			bioagegroupchartgraph.updateBioAgeGroup3(step, classmass[3]);
			bioagegroupchartgraph.updateBioAgeGroup4(step, classmass[4]);*/
			break;
		case AUTOTROPH:	
			//Totales: biomasa poblaci—n
			popbiomasschartgraph.setNotify(false);
			popbiomasschartgraph.updateLeanmass(step, populationdata.leanmass);
			popbiomasschartgraph.updateFatmass(step, populationdata.fatmass);
			popbiomasschartgraph.updateStarvedmass(step, populationdata.starvedmass);
			popbiomasschartgraph.setNotify(true);
			//popbiomasschartgraph.updateEatenmass(step, populationdata.eatenmass);
			//popbiomasschartgraph.updateWasteLeanmass(step, populationdata.wasteleanmass);
			//popbiomasschartgraph.updateWasteFatmass(step, populationdata.wastefatmass);
			break;
		}
		
		//Esto es para el objeto de datos cuando se realiza la graficaci—n con un servidor
		populationdata.step=step;
		populationdata.population=organisms.numObjs;
	
			
		/*try {
			PopulationData clone = (PopulationData)populationdata.clone();
			//System.out.println("Name: "+clone.name);
			//System.out.println("Num: "+clone.population);
			chartclient.sendPopulatioData(clone);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}


	@Override
	public void start() {
		// TODO Auto-generated method stub
		switch(fg.getOrganismType()) {
		case HETEROTROPH:	popchartgraph=new PopulationChartGraph(name, classgroups);
							popbiomasschartgraph=new PopulationBiomassChartGraph(name);
							bioagegroupchartgraph=new BiomassAgeGroupChartGraph(name, classgroups);
							popwastechartgraph= new PopulationWasteChartGraph(name);
							
							break;
		case AUTOTROPH:		popbiomasschartgraph=new PopulationBiomassChartGraph(name);
							break;
		}	
		//chartclient=new ChartClient();
	}


	@Override
	public void stop() {
		// TODO Auto-generated method stub
		//chartclient.close();
	}
}
