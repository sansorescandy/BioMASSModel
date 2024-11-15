/**
 * 
 */
package biomass.model.taxonomy;

import biomass.simulator.gui.BioMASSGUIFrame;


/**
 * @author candysansores
 *
 */
public abstract class Decomposer extends Organism {


	/**
	 * @param functionalgroup
	 */
	public Decomposer(FunctionalGroup fg, Population population, int age, double x, double y) {
		// TODO Auto-generated constructor stub
		super(fg, population, age);
		this.x=x;
		this.y=y;
		agesecs=age*86400+(int)(86400*BioMASSGUIFrame.getInstance().rand.nextDouble());
	}

}
