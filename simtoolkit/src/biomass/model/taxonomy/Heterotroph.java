/**
 * 
 */
package biomass.model.taxonomy;

import biomass.simulator.gui.BioMASSGUIFrame;
import biomass.model.environment.Refuge;
import biomass.model.utils.vonBertalanffy;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author candysansores, flavioreyes
 *
 */
public abstract class Heterotroph extends Organism {
	private Refuge refuge;
	protected double stomachLeanMass=0;
	protected double stomachFatMass=0;
	protected int spawnAge=-1;
	

	public Heterotroph(FunctionalGroup fg, Population population, int age, double x, double y) {
		super(fg, population, age);
		this.x=x;
		this.y=y;
		agesecs=age*86400+(int)(86399*BioMASSGUIFrame.getInstance().rand.nextDouble());
		// Ajustamos la longitud segœn la funci—n de Von Bertalanffy 1a–o=31536000secs
		length=vonBertalanffy.lengthFromAgeT0(((double) agesecs)/31536000.0, fg.linf, fg.k, fg.t0);
		// Ajustamos el radio
		radio=length/2;
		// Ajustamos la masa a partir de la longitud utilizando otra funci—n de Von Bertalanffy
		// Les ajustamos un nivel de grasa aleatorio entre los l’mites inferior y superior
		addLeanMass(vonBertalanffy.weightFromLength(length, fg.a, fg.b));
		// Les ajustamos un nivel de grasa aleatorio entre los l’mites inferior y superior
		addFatMass(getLeanMass()*(fg.minFattyMassFraction+(fg.maxFattyMassFraction-fg.minFattyMassFraction)*BioMASSGUIFrame.getInstance().rand.nextDouble())/fg.optimalLeanMassFraction);
		//addFatMass(getLeanMass()*fg.maxFattyMassFraction/fg.optimalLeanMassFraction);
	}

	
	

	/* (non-Javadoc)
	 * @see biomass.simulator.core.BiologicalCycles#dailystep()
	 */
	@Override
	public void dailystep() {
		if(alive) {
			age++;
			// Se crece lo que corresponde a la hora transcurrida
			grow();
			// Se repone grasa con la que haya en el est—mago
			fatten();
			// Se consume grasa
			metabolize();
			// Llegado el momento habr‡ reproducci—n
		    if(length>=fg.firstMaturityLength)
		    	reproduce();
		}
	}
	

	/* (non-Javadoc)
	 * @see biomass.model.core.BiologicalCycles#hourlystep()
	 */
	@Override
	public void hourlystep() {
	}


	public double getHunger() {
		return calculateHunger();
	}
	
	public double getStomachRelativeVacuity() {
		//la capacidad del est—mago menos lo que tiene actualmente en el est—mago dividido entre la capacidad
		double p=(fg.stomachMassCapacityRatio*getLeanMass()-(stomachLeanMass+stomachFatMass))/(fg.stomachMassCapacityRatio*getLeanMass());
		//System.out.println("Vacuity: "+p);
		return p;
	}
	
	private double calculateHunger() {	
		// Con el est—mago vac’o y sin deficit de grasa el hambre m‡ximo es appetiteWithoutFatDeficit
		// Con deficit m‡ximo de grasa el hambre es igual a la vacuidad del est—mago
		double fatDeficit=1-(getFatMass()-fg.minFattyMassFraction*getBiomass())*fg.optimalLeanMassFraction/(getLeanMass()*fg.optimalFatMassFraction);
		if(fatDeficit<0) fatDeficit=0;
		return getStomachRelativeVacuity()*(fg.appetiteWithoutFatDeficit + fatDeficit*(1-fg.appetiteWithoutFatDeficit));
	}
	
	public abstract void eat(Organism organism);
	
	
	private void metabolize() {
		//Se calcula la cantidad de grasa consumida como una porci—n de la masa de grasa optima con respecto a la masa prote’nica actual
		double deltaFatMass=fg.optimalFatMassFraction*getLeanMass()/(1-fg.optimalFatMassFraction)*fg.bmr;
		addFatMass(-deltaFatMass);
		//Si la grasa cae por debajo del m’nimo tolerable el organismo muere de inanici—n
		if(getFatMass()/getBiomass() < fg.minFattyMassFraction )
			starve();
	}
	
	

	
	private void fatten(){
		// Solo si hay grasa en el est—mago
		if(stomachFatMass>0){
			// Calculamos los gramos de grasa que faltan para llegar al m‡ximo
			double deltaFatMass = fg.maxFattyMassFraction*getLeanMass()/(1-fg.maxFattyMassFraction)-getFatMass();
			// Comparamos con los que tenemos en el est—mago
			if( deltaFatMass>stomachFatMass ){
				// Tenemos menos vaciaremos el est—mago totalmente
				deltaFatMass=stomachFatMass;
				// Eliminamos la prote’na del est—mago
				stomachLeanMass=0;
			}
			// Reducimos lo que tenemos en el est—mago
			stomachFatMass-=deltaFatMass;				
			// Engordamos
			addFatMass(deltaFatMass);
		}
	}
	
	
	
	// delta = #d’as de crecimiento a partir de la edad del individuo
	private void grow() {
		// Solo si hay proteina en el est—mago
		if(stomachLeanMass>0){
			// Para determinar la masa que va a crecer se calcula la masa que se debe tener en este momento 
			// segœn la ecuaci—n de von Bertalanffy con precisi—n al segundo actual 1 a–o=31,536,000segs
			double deltaLeanMass = vonBertalanffy.weightFromAgeT0(((double) age)/365.0, fg.linf, fg.k, fg.t0, fg.a, fg.b)-getLeanMass();
			//Checamos que no haya un crecimiento negativo
			if(deltaLeanMass>0) {
				// Comparamos la masa necesaria con la que existe en el est—mago
				if(deltaLeanMass>stomachLeanMass)
					// Tenemos menos
					deltaLeanMass=stomachLeanMass;
				// Crecemos
				addLeanMass(deltaLeanMass);
				// Reducimos la proteina del est—mago
				stomachLeanMass-=deltaLeanMass;
				// Actualizamos su longitud
				length=vonBertalanffy.lengthFromWeight(getLeanMass(), fg.a, fg.b);
				// y su radio
				radio=length/2;
			}
		}
	}
	
	
	public Refuge getRefuge() {
		return refuge;
	}
	
	public void setRefuge(Refuge refuge) {
		this.refuge=refuge;
	}	

	
	
	private void reproduce() {
		Organism organism;
		Constructor constructor=null;
		
		if(spawnAge < 0){
			//
			spawnAge = age+Math.abs((int)(BioMASSGUIFrame.getInstance().rand.nextGaussian()*fg.repStdDev));
			//spawnAge = age+1;
			//if(follow==getID()) System.out.println("Spawn age: "+spawnAge+ " Follow: "+follow+" Length: "+length+ "ID: "+getID()+" Age: "+age);
			//System.out.println("My age   : "+age);
		}
		else {
			if(age==spawnAge){
				//if(follow==getID()) System.out.println("Pariendo: "+fg.recruitment+ " Follow: "+follow+" Length: "+length+ "ID: "+getID()+" Age: "+age);
				System.out.println("Pariendo: "+fg.recruitment);
				try {
					//Obtiene la clase a la que pertenecen los organismos de esta poblaci—n
					Class groupClass=this.getClass();
					//System.out.println("Clase: "+groupClass);
					//Obtiene el constructor de la clase a la que pertenecen los organismos de esta poblaci—n
					constructor=groupClass.getConstructor(new Class[] {FunctionalGroup.class, Population.class, int.class, double.class, double.class});
				} catch ( NoSuchMethodException e){
					e.printStackTrace();
				}
				for(int i=0;i<fg.recruitment;i++){
					try {
						// Crea un nuevo organismo utilizando los mismos grupo funcional, poblaci—n
						// y coordenadas x y del padre. La edad utilizada es la edad de reclutamiento
						// de juveniles.
						//double xm = population.model.rand.nextDouble()*population.model.seaSpace.getWidth()-population.model.seaSpace.getWidth()/2;
						//double ym = population.model.rand.nextDouble()*population.model.seaSpace.getHeight()-population.model.seaSpace.getHeight()/2;
						organism = (Organism) constructor.newInstance(new Object[] {fg, population, (int) vonBertalanffy.ageFromLengthT0(fg.firstMaturityLength, fg.linf, fg.k, fg.t0)*365, x, y});
                        //organism.child=true;
						// Se agrega el nuevo individuo al espacio 2d
						BioMASSGUIFrame.getInstance().seaSpace.insert(organism);
                        //System.out.println("ID Padre: "+getID()+" ID Hijo: "+m);
                        //System.out.println("X: "+organism.x+" Y: "+organism.y);
						//A–ade al scheduler de la simulaci—n a los organismos que se ejecutar‡n en cada paso de la simulaci—n
						//en un mismo intervalo de tiempo
						BioMASSGUIFrame.getInstance().scheduler.addNewScheduleAgents(organism);
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
		}
		
	}
	

	public boolean enterRefuge(){
		if(refuge.enter(getBiomass())){
			hide();
			if(shout)
				System.out.println("Agent:"+getID()+" hidden"); 
			return(true);
		}
		if(shout)
			System.out.println("Agent:"+getID()+" couldn't hide"); 
		return false;
	}
	
	
	public void leaveRefuge(){
		refuge.leave(getBiomass());
		unhide();
		if(shout){
			System.out.println("Agent:"+getID()+" exiting from refuge"); 	
		}
	}

	
}
