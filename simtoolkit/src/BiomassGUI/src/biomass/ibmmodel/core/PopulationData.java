package biomass.ibmmodel.core;

import java.io.Serializable;

public class PopulationData implements Cloneable, Serializable{
	public String name;
	public long step=0;
	public int population=0;
	public int starved=0;
	public int eaten=0;
	public double leanmass=0;
	public double fatmass=0;
	public double eatenmass=0;
	public double starvedmass=0;
	public double wastefatmass=0;
	public double wasteleanmass=0;
	
	protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
