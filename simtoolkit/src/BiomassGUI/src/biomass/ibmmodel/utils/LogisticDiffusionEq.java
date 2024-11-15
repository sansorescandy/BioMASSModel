/**
 * 
 */
package biomass.ibmmodel.utils;

/**
 * @author candysansores
 *
 */
public class LogisticDiffusionEq {
	private double[][][] dens;
    private double[][][] n_dens;    
    private int cx;
    private int cy;
    private int cz;
    private double M = 10; //densidad m‡xima en cada celda
    private double k = 0.1; //velocidad de crecimeinto del plancton (unidades de densidad por unidad de tiempo)
    private double dt;
    private double lambda;
    

	/**
	 * 
	 */
	public LogisticDiffusionEq(int cx, int cy, int cz, double dt, double lambda) {
		// TODO Auto-generated constructor stub
		this.cx = cx;
	    this.cy = cy;
	    this.cz = cz;
	    this.dt=dt;
	    this.lambda=lambda;
	    dens = new double[cx][cy][cz]; 
	    n_dens = new double[cx][cy][cz]; 
	}
	
	public void Init() {
	    int x, y, z;
	        
	    for(x = 0; x < cx; x++) {
	       for(y = 0; y < cy; y++) {
	           for(z = 0; z < cz; z++) {
	               dens[x][y][z] = Math.random()*M; //inicia la densidad de la celda en un valor entre 0 y M  
	               //System.out.println("("+x+","+y+","+z+",)="+dens[x][y][z]);
	           }
	       }
	    }    
	 }
	
	//Dt=0.001 bajar si crece mucho, Lambda=1

    public void CellActualize() {
        int x, y, z;
        int no, su, es, oe, ar, ab; //coordenadas de los vecinos m‡s cercanos
        double prom; //promedio de los vecinos m‡s cercanos
        
        //actualiza mediante la ecuaci—n log’stica 
        /*for(x = 0; x < cx; x++) {
            es = (x < cx-1) ? x+1:x;
            oe = (x > 0) ? x-1:x;
            for(y = 0; y < cy; y++) {
                no = (y < cy-1) ? y+1:y;
                su = (y > 0) ? y-1:y;                                
                for(z = 0; z < cz; z++) {
                    ar = (z < this.cz-1) ? z+1:z;
                    ab = (z > 0) ? z-1:z;                  
                    //promedio de densidad de los 6 vecinos m‡s cercanos
                    prom = dens[es][y][z]+dens[oe][y][z]+
                            dens[x][no][z]+dens[x][su][z]+
                            dens[x][y][ar]+dens[x][y][ab];
                    prom /= 6;
                    n_dens[x][y][z] = dens[x][y][z]+
                            dt*k*dens[x][y][z]*(1-dens[x][y][z]/M)-
                            dt*lambda*(prom-dens[x][y][z]);                                        
                }
            }
        }*/
        //copia los nuevos valores en el arreglo de densidades 
        //System.arraycopy(n_dens, 0, dens, 0, dens.length);
    }  
    
    public double getConcentration(int x, int y, int z) {
    	return dens[x][y][z];
    }
    
    public void decreaseConcentration(int x, int y, int z, double amount) {
    	dens[x][y][z]-=amount;
    }
}
