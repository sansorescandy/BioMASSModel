/**
 * 
 */
package biomass.simulator.gui.model;

import java.util.ArrayList;



/**
 * @author candysansores
 *
 */
public class Population {
	private int id;
    private String name;
    private int cohorte;
    private double mortalityrate;
    private int fg;
    private double linf;
    private double k;
	private double t0;
	private double a;
	private double b;
	private double lifespan;
	private double stomachratio;
	private double mouthratio;
	private double maturitylength;
	private int recruitment;
	private double recruitmentlength;
	
	public double getLifespan() {
		return lifespan;
	}
	
	public void setLifespan(double lifespan) {
		this.lifespan = lifespan;
	}
	
	public int getRecruitment() {
		return recruitment;
	}
	
	public void setRecruitment(int recruitment) {
		this.recruitment = recruitment;
	}
	
	public double getRecruitmentlength() {
		return recruitmentlength;
	}
	public void setRecruitmentlength(double recruitmentlength) {
		this.recruitmentlength = recruitmentlength;
	}

    private ArrayList<FunctionalGroup> allfunctionalgroups;
    private boolean newrecord;
    
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the cohorte
	 */
	public int getCohorte() {
		return cohorte;
	}
	/**
	 * @param cohorte the cohorte to set
	 */
	public void setCohorte(int cohorte) {
		this.cohorte = cohorte;
	}
	/**
	 * @return the mortalityrate
	 */
	public double getMortalityrate() {
		return mortalityrate;
	}
	/**
	 * @param mortalityrate the mortalityrate to set
	 */
	public void setMortalityrate(double mortalityrate) {
		this.mortalityrate = mortalityrate;
	}
	/**
	 * @return the fg
	 */
	public int getFg() {
		return fg;
	}
	/**
	 * @param fg the fg to set
	 */
	public void setFg(int fg) {
		this.fg = fg;
	}
	
	/**
	 * @return the linf
	 */
	public double getLinf() {
		return linf;
	}

	/**
	 * @param linf the linf to set
	 */
	public void setLinf(double linf) {
		this.linf = linf;
	}
	

	/**
	 * @return the k
	 */
	public double getK() {
		return k;
	}

	/**
	 * @param k the k to set
	 */
	public void setK(double k) {
		this.k = k;
	}

	/**
	 * @return the t0
	 */
	public double getT0() {
		return t0;
	}

	/**
	 * @param t0 the t0 to set
	 */
	public void setT0(double t0) {
		this.t0 = t0;
	}

	/**
	 * @return the a
	 */
	public double getA() {
		return a;
	}

	/**
	 * @param a the a to set
	 */
	public void setA(double a) {
		this.a = a;
	}

	/**
	 * @return the b
	 */
	public double getB() {
		return b;
	}

	/**
	 * @param b the b to set
	 */
	public void setB(double b) {
		this.b = b;
	}
	
	/**
	 * @return the lifespan
	 */
	public double getLifeSpan() {
		return lifespan;
	}
	/**
	 * @param set lifespan
	 */
	public void setLifeSpan(double lifespan) {
		this.lifespan = lifespan;
	}
	
    /**
	 * @return the allfunctionalgroups
	 */
	public ArrayList<FunctionalGroup> getAllfunctionalgroups() {
		return allfunctionalgroups;
	}
	/**
	 * @param allfunctionalgroups the allfunctionalgroups to set
	 */
	public void setAllfunctionalgroups(ArrayList<FunctionalGroup> allfunctionalgroups) {
		this.allfunctionalgroups = allfunctionalgroups;
	}
	
	
	public boolean isNewrecord() {
        return newrecord;
    }

    @Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
	public void setNewrecord(boolean newrecord) {
        this.newrecord = newrecord;
    }
	
	/**
	 * @return the stomachratio
	 */
	public double getStomachratio() {
		return stomachratio;
	}
	/**
	 * @param stomachratio to set
	 */
	public void setStomachratio(double stomachratio) {
		this.stomachratio = stomachratio;
	}
	
	/**
	 * @return the mouthratio
	 */
	public double getMouthratio() {
		return mouthratio;
	}

	/**
	 * @param mouthratio the mouthratio to set
	 */
	public void setMouthratio(double mouthratio) {
		this.mouthratio = mouthratio;
	}
	/**
	 * @return the maturitylength
	 */
	public double getMaturitylength() {
		return maturitylength;
	}

	/**
	 * @param maturitylength the maturitylength to set
	 */
	public void setMaturitylength(double maturitylength) {
		this.maturitylength = maturitylength;
	}
}
