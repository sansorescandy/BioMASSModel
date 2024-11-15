/**
 * 
 */
package biomass.ibmmodel.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import biomass.simulator.gui.BioMASSGUIFrame;

/**
 * @author candysansores
 *
 */
public abstract class Heterotroph extends Organism {
	protected static final int Eaten = 0;
	protected static final int Starved = 1;
	private Refuge refuge;
	protected double stomachLeanMass=0;
	protected double stomachFatMass=0;
	protected int gestationtime=0;
	protected boolean pregnant=false;
	

	public Heterotroph(FunctionalGroup fg, Population population, int age) {
		// TODO Auto-generated constructor stub
		this.fg=fg;
		this.population=population;
		this.age=age+(int)(BioMASSGUIFrame.getInstance().r.nextDouble()*fg.reproductioncycle);
		agesecs=age*86400+(int)(86400*BioMASSGUIFrame.getInstance().r.nextDouble());
		biomass=new Biomass(population);
		length=lengthFromAgeLoBertalanffyFunction();
		radio=length/2;
		biomass.addLeanMass(weightFromLengthBertalanffyFunction(length));
		// Les ajustamos un nivel de grasa aleatorio entre los l’mites inferior y superior
		biomass.addFatMass(biomass.getLeanMass()*(fg.minFattyMassFraction+(fg.maxFattyMassFraction-fg.minFattyMassFraction)*BioMASSGUIFrame.getInstance().r.nextDouble())/fg.optimalLeanMassFraction);
		//biomass.addFatMass(biomass.getLeanMass()*(fg.minFattyMassFraction+0.1)/(fg.maxLeanMassFraction-0.1));
		//biomass.addFatMass((biomass.getLeanMass()*fg.maxFattyMassFraction/fg.minLeanMassFraction)*BioMASSGUIFrame.getInstance().r.nextDouble());
		//biomass.addFatMass((biomass.getLeanMass()*fg.maxFattyMassFraction/fg.minLeanMassFraction));
		//System.out.println("Edad: "+age+" Biomass: "+biomass.getBiomass());
	}
	
	public Heterotroph(FunctionalGroup fg, Population population, Biomass b) {
		// TODO Auto-generated constructor stub
		this.fg=fg;
		this.population=population;
		age=0;
		agesecs=(int)(86400*BioMASSGUIFrame.getInstance().r.nextDouble());
		biomass=b;
		length=fg.lo;
		radio=length/2;
	}
	
	//Modelo de crecimiento de acuerdo a Bertalanffy
	public double bertalanffyGrowRateFunction() {
		return fg.a*fg.b*fg.k*(fg.linf-fg.lo)*Math.exp(-fg.k*(age/365))*Math.pow(fg.linf-(fg.linf-fg.lo)*Math.exp(-fg.k*(age/365)), fg.b-1);
	}
	
/*	//Modelo de crecimiento de Parrot
	public double leanMassGeneralGrowthFunction() {
		return fg.birthLeanMass * Math.exp((fg.k2 / fg.k1) * (1 - Math.exp(-fg.k1 * age)));
	}*/
	
	//Funci—n que regresa la longitud en cent’metros dado el tiempo t0 (edad) que tendr’a el organismo cuando su longitud fuera 0 (to = (1/k)ln[(Loo-Lo)/Loo])
	public double lengthFromAgeT0BertalanffyFunction() {
		return fg.linf*(1-Math.exp(-fg.k*(age/365-fg.t0)));
	}
	
	//Funci—n que regresa la longitud en cent’metros a partir de la edad en a–os dada la longitud al nacer (lo) en cent’metros (Lo = Loo[1 - exp(kto)]) 
	public double lengthFromAgeLoBertalanffyFunction() {
		return fg.linf-(fg.linf-fg.lo)*Math.exp(-fg.k*(age/365));
	}
	
	//Funci—n que regresa el peso en gramos (gr) a partir de la longitud en cent’metros (cm)
	public double weightFromLengthBertalanffyFunction(double length) {
		return fg.a*(Math.pow(length, fg.b));
	}
	
	public double lenghtFromWeightBertalanffyFunction(double weight) {
		return Math.pow(weight/fg.a, 1/fg.b);
	}

	/* (non-Javadoc)
	 * @see biomass.simulator.core.BiologicalCycles#dailystep()
	 */
	@Override
	public void dailystep() {
		// TODO Auto-generated method stub
		if(alive) {
			age++;
		}
	}
	

	/* (non-Javadoc)
	 * @see biomass.ibmmodel.core.BiologicalCycles#hourlystep()
	 */
	@Override
	public void hourlystep() {
		// TODO Auto-generated method stub
		if(alive) {
			grow(1); //un d’a
			metabolize();
//		    if(length>=fg.maturitylength)
//		    	reproduction();
		    dispose();
		}
	}

	private void reproduction() {
		if(gestationtime==0) {
			if(pregnant) {//Nacen nuevos individuos
				int offsprings = (int)(biomass.getRepLeanMass()/weightFromLengthBertalanffyFunction(fg.lo));
				System.out.println("Pariendo: "+offsprings);
				for(int i=0;i<offsprings;i++) {
					Biomass b=new Biomass(population);
					b.addLeanMass(biomass.getRepLeanMass()/offsprings);
					b.addFatMass(biomass.getRepFatMass()/offsprings);
					System.out.println("Lean: "+biomass.getRepLeanMass()/offsprings+" Fat: "+biomass.getRepFatMass()/offsprings);
					spawn(b);
				}
				pregnant=false;
				biomass.addRepLeanMass(-biomass.getRepLeanMass());
				biomass.addRepFatMass(-biomass.getRepFatMass());
			}
			gestationtime=fg.reproductioncycle+(int)(BioMASSGUIFrame.getInstance().r.nextGaussian()*fg.reproductionlapse);
			if(fg.reproductioncycle-fg.reproductionlapse<=gestationtime && gestationtime<=fg.reproductioncycle+fg.reproductionlapse){
				pregnant=true;
				System.out.println("Pregnant");
			}
			else
				gestationtime=fg.reproductioncycle;
		}
		else {
			--gestationtime;
			if(pregnant) {
				double repfatmassinc, repleanmassinc;
				repfatmassinc=biomass.getBiomass()*fg.reproductionbiomassfraction*fg.optimalFatMassFraction/fg.reproductioncycle; //Cuanto en gramos de grasa tendr‡ la panza dividido entre los d’as de reproducci—n
				repleanmassinc=biomass.getBiomass()*fg.reproductionbiomassfraction*fg.optimalLeanMassFraction/fg.reproductioncycle; //Cuanto en gramos de prote’na tendr‡ la panza dividido entre los d’as de reproducci—n
				if(stomachFatMass<repfatmassinc)
					repfatmassinc=stomachFatMass;
				if(stomachLeanMass<repleanmassinc)
					repleanmassinc=stomachLeanMass;
				stomachFatMass-=repfatmassinc;
				stomachLeanMass-=repleanmassinc;
				biomass.addRepFatMass(repfatmassinc);
				biomass.addRepLeanMass(repleanmassinc);
			}
		}
		//System.out.println("Gestation: "+gestationtime+" ID: "+getID());
	}
	
	private void spawn(Biomass b) {
		Constructor constructor = null;
		Organism organism;
		int id;

		try {
			//Obtiene la clase a la que pertenecen los organismos de esta poblaci—n
			Class groupClass=Class.forName(fg.getGroupClass());
			//Obtiene el constructor de la clase a la que pertenecen los organismos de esta poblaci—n
			constructor=groupClass.getConstructor(new Class[] {FunctionalGroup.class, Population.class, Biomass.class, double.class, double.class});
		}
		catch ( ClassNotFoundException e){
			e.printStackTrace();
		} catch ( NoSuchMethodException e){
			e.printStackTrace();
		}


		try {
			organism = (Organism) constructor.newInstance(new Object[] {fg, population, b, getX(), getY()});
			BioMASSGUIFrame.getInstance().seaSpace.insert(getID(),organism);
			organism.getVelocity().Assign(BioMASSGUIFrame.getInstance().r.nextDouble()*2-1, BioMASSGUIFrame.getInstance().r.nextDouble()*2-1, 10);
			BioMASSGUIFrame.getInstance().scheduler.addNewScheduleAgents(organism);	
			population.organisms.add(organism);
			if(fg.isHideBehaviorActive()) {
				organism.setRefuge(refuge);		
			}
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

	public double getHunger() {
		return calculateHunger();
	}
	
	public double getStomachRelativeVacuity() {
		//la capacidad del est—mago menos lo que tiene actualmente en el est—mago dividido entre la capacidad
		double p=(fg.stomachMassCapacityRatio*biomass.getLeanMass()-(stomachLeanMass+stomachFatMass))/(fg.stomachMassCapacityRatio*biomass.getLeanMass());
		//System.out.println("Vacuity: "+p);
		return p;
	}
	
	private double calculateHunger() {	
		double fatDeficit=1-(biomass.getFatMass()-fg.minFattyMassFraction*biomass.getBiomass())*fg.optimalLeanMassFraction/(biomass.getLeanMass()*fg.optimalFatMassFraction);
		if(fatDeficit<0) fatDeficit=0;
		return getStomachRelativeVacuity()*(fg.appetiteWithoutFatDeficit + fatDeficit*(1-fg.appetiteWithoutFatDeficit));
	}
	
	public abstract void eat(Organism organism);
	
	
	private void metabolize() {
		//Se calcula la cantidad de grasa consumida como una porci—n de la masa de grasa optima con respecto a la masa prote’nica actual
		double deltafatmass=-biomass.getLeanMass()*fg.optimalFatMassFraction/fg.optimalLeanMassFraction*fg.hbmr;
		biomass.addFatMass(deltafatmass);
		
		//Si la grasa consumida lleva por debajo del m’nimo a la grasa actual el individuo muere de inanici—n
		/*if(biomass.getFatMass()/biomass.getBiomass()<this.fg.minFattyMassFraction+.01)
			System.out.println("Metabolize! ID:"+this.id+"     Fat %biomass: "+biomass.getFatMass()/biomass.getBiomass()+ "     Biomass: "+this.biomass.getBiomass()+"     Hambre: "+this.getHunger());*/
		//if(biomass.getFatMass()<biomass.getLeanMass()*fg.minFattyMassFraction/fg.maxLeanMassFraction)
		if(biomass.getFatMass()==0)
			die(Starved);
		//	System.out.println("Starved! ID:"+this.id+"     Fat %biomass: "+biomass.getFatMass()/biomass.getBiomass()+ "     Biomass: "+this.biomass.getBiomass()+"     Hambre: "+this.getHunger());
	}
	
	private void grow(double delta) {
		//Para calcular la masa que va a crecer se multiplica el ritmo de crecimiento en gramos por a–o por el incremento de tiempo expresado en a–os
		double growthmass=bertalanffyGrowRateFunction()*delta/365;
		if(stomachLeanMass<=growthmass) {
			biomass.addLeanMass(stomachLeanMass);
			stomachLeanMass=0;
		}
		else {
			biomass.addLeanMass(growthmass);
			stomachLeanMass-=growthmass;
		}		
		//Incrementa su masa grasosa con lo que hay en el est—mago y regresa lo que le sobra
		double fatremanentmass=increaseFatMass(stomachFatMass);
		//Dejamos en el est—mago lo que sobra
		stomachFatMass=fatremanentmass;
		//Actualizamos su longitud
		length=lenghtFromWeightBertalanffyFunction(biomass.getLeanMass());
		/*if(biomass.getFatMass()/biomass.getBiomass()<this.fg.minFattyMassFraction+.01)
			System.out.println("Grow! ID:"+this.id+"     Fat %biomass: "+biomass.getFatMass()/biomass.getBiomass()+ "     Biomass: "+this.biomass.getBiomass()+"     Hambre: "+this.getHunger());*/
		radio=length/2;
	}
	
	private void dispose() {
		//Suma a la biomasa desperdiciada de la poblaci—n los remanentes del est—mago
		population.addWasteFatMass(stomachFatMass);
		population.addWasteLeanMass(stomachLeanMass);
		//System.out.println("Lean: "+stomachLeanMass+" Fat: "+stomachFatMass);
		//Vac’a el est—mago
		stomachLeanMass=0;
		stomachFatMass=0;
	}
	
	public double increaseFatMass(double grams) {
		double maxfatincrement=fg.maxFattyMassFraction*biomass.getLeanMass()/(1-fg.maxFattyMassFraction)-biomass.getFatMass();
		if(grams>maxfatincrement) {
			biomass.addFatMass(maxfatincrement);
			/*System.out.println("Estoy gordo");
			System.out.println("Paso: " + agesecs);
			
			//TEMPORAL PARA PARAR EL MODELO
			BioMASSGUIFrame.getInstance().parar=true;*/
			return grams-maxfatincrement;
		}
		else {
			biomass.addFatMass(grams);
			return 0;
		}
	}
	
	
	public Refuge getRefuge() {
		return refuge;
	}
	
	public void setRefuge(Refuge refuge) {
		this.refuge=refuge;
	}	

}
