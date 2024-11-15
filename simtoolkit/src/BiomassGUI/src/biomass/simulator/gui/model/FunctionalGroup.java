/**
 * 
 */
package biomass.simulator.gui.model;


/**
 * @author candysansores
 *
 */
public class FunctionalGroup {
	private int id;
	private String name;
	private double linf;
	private double k;
	private double t0;
	private double a;
	private double b;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}

	private double longevity;
	private int	   classid;  //No se si verificar si hubo error a la hora de asignar los valores en la clase DataService
	private double stomachratio;
	private double mouthratio;
	private double maturitylength;
	private int    repcycle;
	private double perceprange;
	private double maxspeed;
	private double escapingspeed;
	private double huntingspeed;
	private double wanderingspeed;
	private double foragingspeed;
	private double scoutingspeed;
	private double maxdistancerefuge;
	private double alertlevel;
	private int behaviortype;
	private boolean newrecord;

	/**
	 * 
	 */
	public FunctionalGroup() {
		// TODO Auto-generated constructor stub
	}

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
	 * @return the longevity
	 */
	public double getLongevity() {
		return longevity;
	}

	/**
	 * @param longevity the longevity to set
	 */
	public void setLongevity(double longevity) {
		this.longevity = longevity;
	}

	/**
	 * @return the classid
	 */
	public int getClassid() {
		return classid;
	}

	/**
	 * @param classid the classid to set
	 */
	public void setClassid(int classid) {
		this.classid = classid;
	}

	/**
	 * @return the stomachratio
	 */
	public double getStomachratio() {
		return stomachratio;
	}

	/**
	 * @param stomachratio the stomachratio to set
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

	/**
	 * @return the repcycle
	 */
	public int getRepcycle() {
		return repcycle;
	}

	/**
	 * @param repcycle the repcycle to set
	 */
	public void setRepcycle(int repcycle) {
		this.repcycle = repcycle;
	}

	/**
	 * @return the perceprange
	 */
	public double getPerceprange() {
		return perceprange;
	}

	/**
	 * @param perceprange the perceprange to set
	 */
	public void setPerceprange(double perceprange) {
		this.perceprange = perceprange;
	}

	/**
	 * @return the maxspeed
	 */
	public double getMaxspeed() {
		return maxspeed;
	}

	/**
	 * @param maxspeed the maxspeed to set
	 */
	public void setMaxspeed(double maxspeed) {
		this.maxspeed = maxspeed;
	}

	/**
	 * @return the escapingspeed
	 */
	public double getEscapingspeed() {
		return escapingspeed;
	}

	/**
	 * @param escapingspeed the escapingspeed to set
	 */
	public void setEscapingspeed(double escapingspeed) {
		this.escapingspeed = escapingspeed;
	}

	/**
	 * @return the huntingspeed
	 */
	public double getHuntingspeed() {
		return huntingspeed;
	}

	/**
	 * @param huntingspeed the huntingspeed to set
	 */
	public void setHuntingspeed(double huntingspeed) {
		this.huntingspeed = huntingspeed;
	}

	/**
	 * @return the wanderingspeed
	 */
	public double getWanderingspeed() {
		return wanderingspeed;
	}

	/**
	 * @param wanderingspeed the wanderingspeed to set
	 */
	public void setWanderingspeed(double wanderingspeed) {
		this.wanderingspeed = wanderingspeed;
	}

	/**
	 * @return the foragingspeed
	 */
	public double getForagingspeed() {
		return foragingspeed;
	}

	/**
	 * @param foragingspeed the foragingspeed to set
	 */
	public void setForagingspeed(double foragingspeed) {
		this.foragingspeed = foragingspeed;
	}

	/**
	 * @return the scoutingspeed
	 */
	public double getScoutingspeed() {
		return scoutingspeed;
	}

	/**
	 * @param scoutingspeed the scoutingspeed to set
	 */
	public void setScoutingspeed(double scoutingspeed) {
		this.scoutingspeed = scoutingspeed;
	}

	/**
	 * @return the maxdistancerefuge
	 */
	public double getMaxdistancerefuge() {
		return maxdistancerefuge;
	}

	/**
	 * @param maxdistancerefuge the maxdistancerefuge to set
	 */
	public void setMaxdistancerefuge(double maxdistancerefuge) {
		this.maxdistancerefuge = maxdistancerefuge;
	}

	/**
	 * @return the alertlevel
	 */
	public double getAlertlevel() {
		return alertlevel;
	}

	/**
	 * @param alertlevel the alertlevel to set
	 */
	public void setAlertlevel(double alertlevel) {
		this.alertlevel = alertlevel;
	}

	/**
	 * @return the behaviortype
	 */
	public int getBehaviorType() {
		return behaviortype;
	}

	/**
	 * @param behaviortype the behaviortype to set
	 */
	public void setBehaviorType(int behaviortype) {
		this.behaviortype = behaviortype;
	}

	/**
	 * @return the newrecord
	 */
	public boolean isNewrecord() {
		return newrecord;
	}

	/**
	 * @param newrecord the newrecord to set
	 */
	public void setNewrecord(boolean newrecord) {
		this.newrecord = newrecord;
	}
	
	

}
