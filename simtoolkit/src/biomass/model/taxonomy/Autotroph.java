/**
 * 
 */
package biomass.model.taxonomy;

import biomass.simulator.gui.BioMASSGUIFrame;
import biomass.simulator.gui.DrawableObject;

/**
 * @author candysansores
 *
 */
public abstract class Autotroph extends Organism implements DrawableObject {

	/**
	 * @param functionalgroup
	 */
	public Autotroph(FunctionalGroup fg, Population population, int age, double x, double y) {
		// TODO Auto-generated constructor stub
		super(fg, population, age);
		this.x=x;
		this.y=y;
		agesecs=age*86400+(int)(86400*BioMASSGUIFrame.getInstance().rand.nextDouble());
	}
	

}
