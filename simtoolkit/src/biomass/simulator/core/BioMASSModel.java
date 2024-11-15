package biomass.simulator.core;

import java.util.Random;

import multiagent.model.agent.Scheduler;
import multiagent.model.environment.Space;

import biomass.model.environment.Refuge;
import biomass.model.taxonomy.FunctionalGroup;
import biomass.model.taxonomy.Population;
import biomass.model.utils.RandomRefuges;

public class BioMASSModel {
	private double version = 0.22;
	public Scheduler scheduler;
	private String[] organismFunctionalGroups = {"biomass.model.taxonomy.Carnivore", "biomass.model.taxonomy.Planktivore", "biomass.model.taxonomy.Plant"};
	//Lista de depredadores del planktivore
	private String[] predatorPlanktivore = {"biomass.model.taxonomy.Carnivore"};
	private String[] preyCarnivore = {new String("biomass.model.taxonomy.Planktivore")};
	private String[] preyPlanktivore = {new String("biomass.model.taxonomy.Plant")};
	private Bag fg = new Bag();
	public Bag populations = new Bag();
	public Bag refuges = new Bag();
	private Reporter recorder;
	private int recordSteps=3600;
	public int chartSteps=450;
	private int records=-1;
	private long lastChartStep=-chartSteps;
	private double width=57600;
	private double height=36000;
	private double refSpatialStdDev = 15000;
    public double refMinRad = 50;
    public double refMaxRad = 250;
    private double refAreaFrac = 0.04;
    public int refSizeClasses = 10;
    private int refCounter[];
    private int refUseCounter[];
	public Space seaSpace;
	private Display display;
	public Random rand;
	private int timestep=1;
	public static double scaleFactor=1.0;
	
	
	//TEMPORAL PARA PARAR CUANDO ENGORDAN
	public boolean parar=false;
	
	public BioMASSModel() {
		System.out.println("Constructor BioMASSModel");
		scheduler = new Scheduler();
		seaSpace = new Space(-width/2, -height/2, width/2, height/2);
		display=new Display(seaSpace);
		rand=new Random(System.currentTimeMillis());
		refCounter = new int[refSizeClasses];
		refUseCounter = new int[refSizeClasses];
		for(int i=0;i<refSizeClasses;i++){
			refCounter[i]=0;
			refUseCounter[i]=0;
		}
	}
	
	private void initBioMASSModel() {
        // Inicializa el reporteador
    	recorder = new Reporter();        
		//Crea los refugios
		createRefuges();
		//Crea los grupos funcionales tom�ndolos ya sea de la BD o de objetos serializados, por ahora c�digo ejemplo
		for (int i = 0; i < organismFunctionalGroups.length; i++) {
			fg.add(new FunctionalGroup(organismFunctionalGroups[i]));
		}
		double deathRate = 1.5/365;
		//Crea las poblaciones iniciales de cada grupo funcional y programa a los agentes en el scheduler
		for (int i = 0; i < fg.numObjs; i++) {
			//ESTO ES PROVISIONAL
			int N0;
			if(((FunctionalGroup)fg.objs[i]).name=="biomass.model.taxonomy.Carnivore") {
				((FunctionalGroup)fg.objs[i]).setGroupClass("biomass.model.taxonomy.Carnivore");
				((FunctionalGroup)fg.objs[i]).setPreys(preyCarnivore);
				
				//Rango de percepci�n
				((FunctionalGroup)fg.objs[i]).setPerceptionRangeFactor(25.0);
				
				((FunctionalGroup)fg.objs[i]).setMaxSpeed(5.0);
				((FunctionalGroup)fg.objs[i]).setMinSpeed(1.0);
								
				((FunctionalGroup)fg.objs[i]).setSpec("Sphyraena-barracuda");
				((FunctionalGroup)fg.objs[i]).setCommonName("Barracuda");
				((FunctionalGroup)fg.objs[i]).setLifeSpan((int) (32.1*365-1));
				((FunctionalGroup)fg.objs[i]).setAgeClassLapse(365);
				((FunctionalGroup)fg.objs[i]).setK(0.09);
				((FunctionalGroup)fg.objs[i]).setLinf(178.0);
				((FunctionalGroup)fg.objs[i]).setFirstMaturityLength(87.7);
				((FunctionalGroup)fg.objs[i]).setRecruitment(1);
				((FunctionalGroup)fg.objs[i]).setT0(-1.19);
				((FunctionalGroup)fg.objs[i]).setA(0.0108);
				((FunctionalGroup)fg.objs[i]).setB(2.84); 
				N0=10;
				deathRate = 0.1/365;
				populations.add(new Population(i, ((FunctionalGroup)fg.objs[i]), N0, deathRate));
				
			}
			if(((FunctionalGroup)fg.objs[i]).name=="biomass.model.taxonomy.Planktivore") {
				((FunctionalGroup)fg.objs[i]).setGroupClass("biomass.model.taxonomy.Planktivore");
				((FunctionalGroup)fg.objs[i]).setPredators(predatorPlanktivore);
				((FunctionalGroup)fg.objs[i]).setPreys(preyPlanktivore);
				
				//Rango de percepci�n
				((FunctionalGroup)fg.objs[i]).setPerceptionRangeFactor(25.0);
				
				//Refugiado
				((FunctionalGroup)fg.objs[i]).setMaxSpeed(5.0);
				((FunctionalGroup)fg.objs[i]).setMinSpeed(1.0);
								
				((FunctionalGroup)fg.objs[i]).setSpec("Girella-Nigricans");
				((FunctionalGroup)fg.objs[i]).setCommonName("Chopa-verde");
				((FunctionalGroup)fg.objs[i]).setLifeSpan((int)(10.7*365-1));
				((FunctionalGroup)fg.objs[i]).setAgeClassLapse(365);
				((FunctionalGroup)fg.objs[i]).setK(0.27);
				((FunctionalGroup)fg.objs[i]).setLinf(68.3);
				((FunctionalGroup)fg.objs[i]).setFirstMaturityLength(37.1);
				((FunctionalGroup)fg.objs[i]).setRecruitment(8);
				((FunctionalGroup)fg.objs[i]).setT0(-0.42);
				((FunctionalGroup)fg.objs[i]).setA(0.0199);
				((FunctionalGroup)fg.objs[i]).setB(3.0063); 
				N0=800;
				deathRate = 0.25/365;
				populations.add(new Population(i, ((FunctionalGroup)fg.objs[i]), N0, deathRate));
				
			}
			if(((FunctionalGroup)fg.objs[i]).name=="biomass.model.taxonomy.Plant") {
				((FunctionalGroup)fg.objs[i]).setGroupClass("biomass.model.taxonomy.Plant");
				((FunctionalGroup)fg.objs[i]).setGoodPlanktonConcentration(0.005);
				((FunctionalGroup)fg.objs[i]).setMaxBiomassPlanktonConcentration(((FunctionalGroup)fg.objs[i]).goodPlanktonConcentration*2);
				((FunctionalGroup)fg.objs[i]).setSpec("Generic");
				((FunctionalGroup)fg.objs[i]).setCommonName("Plant");
				((FunctionalGroup)fg.objs[i]).setK(0.5);
				((FunctionalGroup)fg.objs[i]).setLifeSpan(100*365-1);
				((FunctionalGroup)fg.objs[i]).setAgeClassLapse(50*365);
				((FunctionalGroup)fg.objs[i]).setMaxSpeed(0.02);
				((FunctionalGroup)fg.objs[i]).setMinSpeed(0.02);				
				((FunctionalGroup)fg.objs[i]).setRadio(50.0);				
				N0=400;	
				populations.add(new Population(i, ((FunctionalGroup)fg.objs[i]), N0, deathRate));
			}
			//populations.add(new Population(((FunctionalGroup)fg.objs[i]),p));
		}	
	}
	
	
	
	
	private void createRefuges(){
    	RandomRefuges randomRefuges;
        Refuge refuge;
        double sizeSpan=(this.refMaxRad-this.refMinRad)/refSizeClasses;
        int sizeClass;

        // Solicitamos una bolsa con refugios de tama�o y distribuci�n espacial aleatorios
        randomRefuges = new RandomRefuges(-width/2, -height/2, width/2, height/2, refMinRad, refMaxRad, refAreaFrac, refSpatialStdDev);
        // Obtenemos las estad�sticas
        this.refAreaFrac = randomRefuges.refArea / randomRefuges.simArea;
        this.refMinRad = randomRefuges.refMinRad;
        this.refMaxRad = randomRefuges.refMaxRad;
        // Calculamos la desviaci�n est�ndard de la dispersi�n espacial de los refugios con respecto 
        // al centro de la simulaci�n conforme vamos insertando los refugios en el espaci�n 2d
        // Aprovechamos para asiganrles un grupo de tama�o para compilar estad�sticas de utilizaci�n
        this.refSpatialStdDev = 0;
        for(int i=0;i<randomRefuges.refugesBag.size();i++) {
        	refuge = (Refuge) randomRefuges.refugesBag.objs[i];
        	sizeClass=(int)((refuge.radio-this.refMinRad)/sizeSpan);
        	if(sizeClass>=refSizeClasses)
        		sizeClass=refSizeClasses-1;
        	refuge.setSizeClass(sizeClass);
        	refCounter[sizeClass]++;
			seaSpace.insert(refuge);
			refuges.add(refuge);
			// Asumimos que el promedio est� en el centro
			this.refSpatialStdDev += refuge.x*refuge.x + refuge.y*refuge.y;
		}
        this.refSpatialStdDev = Math.sqrt(this.refSpatialStdDev/refuges.size());
		System.out.println("Refuges created: " + refuges.size()); 
		System.out.println("Refuges min rad: " + this.refMinRad); 
		System.out.println("Refuges max rad: " + this.refMaxRad); 
		System.out.println("Refuges area fr: " + this.refAreaFrac); 
		System.out.println("Refuges dist sd: " + this.refSpatialStdDev); 
		for(int i=0;i<refSizeClasses;i++)
			System.out.println("Refuges created with rad up to " + (this.refMinRad+(i+1)*sizeSpan) + ": " + refCounter[i]); 
	}
	
	
	
	
	private void stopBioMASSModel() {
		scheduler.reset();
		fg.clear();
		populations.clear();
		//display.step();
	}
	
	public void start() {
		initBioMASSModel();
		recorder.start();
		for(Object p:populations) {
			((Population)p).start();
		}
		
			
	}
	
	public void stop() {
		for(Object p:populations) {
			((Population)p).stop();
		}
		recorder.stop();
		stopBioMASSModel();
	}
	
	public boolean step(long steps, long stepsfwd) {
		boolean stop=false;
		
		if((steps/recordSteps)>records){
			records=(int)(steps/recordSteps);
			recorder.step(steps);
		}
		if((steps-chartSteps)>=lastChartStep){
			lastChartStep=steps;
			for(Object p:populations) {
				((Population)p).step(steps);
				stop=stop||((Population)p).organisms.numObjs==0;
			}
		}
		stop=stop||scheduler.step(stepsfwd);
		if(stop) 
			recorder.step(steps);
		display.step();
		return stop;
	}
	
	
	public Display getBioMASSDisplay() {
		return display;
	}
	
	public double getRefSpatialStdDev() {
		return refSpatialStdDev;
	}

	public double getRefMinRad() {
		return refMinRad;
	}

	public double getRefMaxRad() {
		return refMaxRad;
	}

	public double getRefAreaFrac() {
		return refAreaFrac;
	}

	public int getTimeStep() {
		return timestep;
	}

	public void setTimeStep(int timestep) {
		this.timestep = timestep;
	}

	public double getVersion() {
		return version;
	}

	public int getRefCounter(int sizeClass){
		return this.refCounter[sizeClass];
	}
	
	public void incRefUseCounter(int sizeClass){
		this.refUseCounter[sizeClass]++;
	}
	
	public int getRefUseCounter(int sizeClass){
		return this.refUseCounter[sizeClass];
	}

}
