package biomass.model.utils;

import java.util.Random;

import biomass.model.environment.Refuge;
import biomass.simulator.core.Bag;

public class RandomRefuges {
    // Ubica de forma aleatoria refugios en el area comprendida por x1,y1,x2,y2 hasta cumplir
    // con la fracci—n de ‡rea areaFrac.La distribuci—n espacial es normal centrada en el 
    // ‡rea designada y con dispersi—n stdDev. El radio de los refugios es aleatorio con
    // distribuci—n uniforme entre minRefRad y maxRefRad.

	public Bag refugesBag;
    public double refMinRad = Double.MAX_VALUE;
    public double refMaxRad = 0;
    public double refArea = 0;
    public double simArea = 0;
    public double distAvg = 0;

	
    public RandomRefuges(double x1, double y1, double x2, double y2, double minRefRad, double maxRefRad, double areaFrac, double stdDev){
    	int i;
    	double x,y,area;
    	boolean cleared;
    	// Dimensiones del ‡rea de demarcaci—n
    	this.simArea=(x2-x1) * (y2-y1);
    	// La fracci—n del ‡rea por cubrir
    	double areaToFill = this.simArea * areaFrac;
    	// Areas m’nima y m‡xima de los refugios
    	double minRefArea = Math.PI * Math.pow(minRefRad,2);
    	double maxRefArea = Math.PI * Math.pow(maxRefRad,2);
    	double distance, azimuth, clearance;
    	// Inicializamos una bolsa vac’a para los refugios
    	refugesBag = new Bag();
    	// Se inicializa el generador de nœmeros aleatorios.
    	Random rg = new Random();
    	Refuge refuge;
    	while( areaToFill >= minRefArea ){
    		if(areaToFill > maxRefArea)
    			refuge = getRandomSizeRefuge(minRefRad, maxRefRad);
    		else
    			refuge = new Refuge(Math.sqrt(areaToFill/Math.PI));
    		do{
    			// Generamos coordenadas aleatorias segœn una distribuci—n normal centrada en 0 y dispersion=stdDev
    			// Cuidamos que el regufio quede dentro de los l’mites del ‡rea de demarcaci—n
    			do{
    				azimuth = 2*rg.nextDouble()*Math.PI;
    				distance = rg.nextGaussian()*stdDev;
    				x = distance*Math.cos(azimuth);
    				y = distance*Math.sin(azimuth);
    			} while(x-refuge.radio<x1 || x2<=x+refuge.radio || y-refuge.radio<y1 || y2<=y+refuge.radio );
    			// Las coordenadas propuestas colocan al refugio dentro del ‡rea de demarcaci—n
    			// Chequemos si no se traslapa con otro refugio;
    			for(i=0, cleared=true; cleared && i<refugesBag.size(); i++){
    				clearance = Math.sqrt( Math.pow(((Refuge)refugesBag.objs[i]).x-x,2)+Math.pow(((Refuge)refugesBag.objs[i]).y-y,2) );
    				// ÀEs la distancia mayor o igual a la suma de los radios?
    				cleared = (clearance >= ((Refuge)refugesBag.objs[i]).radio + refuge.radio);   					
    			}
    		}while(!cleared);
    		// No hay traslapes
    		// Ajustamos la posici—n del refugio
    		refuge.setPosition(x, y);
    		// Agregamos el refugio a la bolsa
    		refugesBag.add(refuge);
    		// Calculamos el ‡rea del refugio
    		area = Math.PI * refuge.radio * refuge.radio;
    		// Registramos estad’sticas
    		distAvg += distance;
    		this.refArea = this.refArea + area;
    		if(refuge.radio < this.refMinRad)
    			this.refMinRad = refuge.radio;
    		if(refuge.radio > this.refMaxRad)
    			this.refMaxRad = refuge.radio;
       		// Reducimos el ‡rea pendiente de cubrir
    		areaToFill -= area; 
    	}
    	if(refugesBag.size()>0)
    		distAvg = distAvg/refugesBag.size();
    }

    // Genera un refugio en la posici—n x,y con radio aleatorio entre minRefRad y maxRefRad
    private Refuge getRandomSizeRefuge(double minRefRad, double maxRefRad){
    	double radio = Math.random()*(maxRefRad - minRefRad) + minRefRad;
    	Refuge refuge = new Refuge(radio);
    	return refuge;
    }

}
