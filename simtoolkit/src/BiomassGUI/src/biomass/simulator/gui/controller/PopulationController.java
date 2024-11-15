
package biomass.simulator.gui.controller;

import java.util.ArrayList;

import biomass.simulator.dataservice.DataService;
import biomass.simulator.gui.model.FunctionalGroup;
import biomass.simulator.gui.model.FunctionalGroupQuery;
import biomass.simulator.gui.model.Population;
import biomass.simulator.gui.model.PopulationQuery;

/**
 * @author candysansores
 *
 */
public class PopulationController {
	private DataService dataservice;
	/**
	 * 
	 */
	public PopulationController(DataService dataservice) {
		// TODO Auto-generated constructor stub
		this.dataservice=dataservice;
	}
    public ArrayList<Population> list() throws Throwable {
        return dataservice.get(new PopulationQuery());
    }
    
    public ArrayList<FunctionalGroup> functionalGrouplist() throws Throwable {
        return dataservice.get(new FunctionalGroupQuery());
    }

    public Population create() throws Throwable {
    	Population population = new Population();
    	population.setNewrecord(true);
    	population.setAllfunctionalgroups(dataservice.get(new FunctionalGroupQuery()));
    	return population;
    }

    public ArrayList<Population> save(Population population) throws Throwable {
        boolean isNew = population.isNewrecord();
        dataservice.save(population);
        return isNew ? list() : null;
    }								 

    public ArrayList<Population> delete(Population population) throws Throwable {
        dataservice.delete(population);
        return list(); 
    }
}
