package biomass.model.utils;

public class vonBertalanffy {

	// Funci—n que regresa la longitud en cent’metros a partir de la edad dado el tiempo t0 (edad en a–os) 
	// que tendr’a el organismo cuando su longitud fuera 0 (to = (1/k)ln[(Loo-L0)/Loo])
	public static double lengthFromAgeT0(double age, double linf, double k, double t0) {
		return linf*(1-Math.exp(-k*(age-t0)));
	}
	
	
	// Funci—n que regresa la edad en a–os a partir de la longitud tomando la constante T0
	public static double ageFromLengthT0(double length, double linf, double k, double t0){
		return - Math.log(1-length/linf)/k + t0;
	}
	
	
	// Funci—n que regresa la longitud en cent’metros a partir de la edad en a–os dada la longitud al nacer (l0)
	// en cent’metros (L0 = Loo[1 - exp(kto)]) 
	public static double lengthFromAgeL0(double age, double linf, double k, double l0) {
		return linf-(linf-l0)*Math.exp(-k*age);
	}
	
	// Funci—n que regresa el peso en gramos (gr) a partir de la longitud en cent’metros (cm)
	public static double weightFromLength(double length, double a, double b) {
		return a*(Math.pow(length, b));
	}
	
	// Funci—n que regresa la longitud en cent’metros (cm) a partir del peso en gramos (gr) 	
	public static double lengthFromWeight(double weight, double a, double b) {
		return Math.pow(weight/a, 1/b);
	}


	// Funci—n de velocidad de crecimiento de longitud a partir de edad y t0
	public static double lengthGrowRate(double age, double linf, double k, double t0){
		return linf*k*Math.exp(-k*(age-t0));
	}
	
	
	// Funci—n que regresa la masa en gramos dado el tiempo t0 (edad en a–os) que tendr’a el organismo 
	// cuando su longitud fuera 0 (to = (1/k)ln[(Loo-L0)/Loo])
	public static double weightFromAgeT0(double age, double linf, double k, double t0, double a, double b){
		return weightFromLength(lengthFromAgeT0(age, linf, k, t0), a, b);
	}
	
	// Funci—n que regresa la masa en gramos a partir de la edad en a–os dada la longitud al nacer (l0)
	// en cent’metros (L0 = Loo[1 - exp(kto)]) 
	public static double weightFromAgeL0(double age, double linf, double k, double l0, double a, double b){
		return weightFromLength(lengthFromAgeL0(age, linf, k, l0), a, b);
	}
	
	
	// Funci—n de velocidad de crecimiento de masa a partir de edad y l0
	public static double growRateFunctionL0(double age, double linf, double l0, double k, double a, double b) {
		return a*b*k*(linf-l0)*Math.exp(-k*age)*Math.pow(linf-(linf-l0)*Math.exp(-k*age), b-1);
	}
	
	// Funci—n que retorna la longitud al tiempo 0 (l0) a partir de T0
	public static double l0FromT0(double t0, double linf, double k){
		return lengthFromAgeT0(0, linf, k, t0);
	}
	
	// Funci—n que retorna la constante t0 a partir de l0
	public static double t0FromL0(double l0, double linf, double k){
		return Math.log(1-l0/linf)/k;
	}
}

