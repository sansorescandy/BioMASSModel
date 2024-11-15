/**
 * 
 */
package biomass.simulator.gui.controller;

import java.util.ArrayList;

import biomass.simulator.dataservice.DataService;
import biomass.simulator.gui.model.Population;
import biomass.simulator.gui.model.PopulationQuery;
import biomass.simulator.gui.model.Simulation;
import biomass.simulator.gui.model.SimulationPopulationQuery;
import biomass.simulator.gui.model.SimulationQuery;
import biomass.simulator.utils.CollectionUtils;

/**
 * @author candysansores
 *
 */
public class SimulationController {
	private DataService dataservice;
	/**
	 * 
	 */
	public SimulationController(DataService dataservice) {
		// TODO Auto-generated constructor stub
		this.dataservice=dataservice;
	}
    public ArrayList<Simulation> list() throws Throwable {
        return dataservice.get(new SimulationQuery());
    }
    
    public ArrayList<Population> populationlist() throws Throwable {
        return dataservice.get(new PopulationQuery());
    }

    public Simulation create() throws Throwable {
        Simulation simulation = new Simulation();
        simulation.setNewrecord(true);
        simulation.setAllPopulations(dataservice.get(new PopulationQuery()));
        return simulation;
    }

    public ArrayList<Simulation> save(Simulation simulation) throws Throwable {
        boolean isNew = simulation.isNewrecord();
        dataservice.save(simulation);
        return isNew ? list() : null; //Si es nuevo el registro regresa la lista de todas las simulaciones para actualizar la vista
    }								  //sino es nuevo regresa nulo para indicarle a la vista que no hubo cambios en la lista

    public ArrayList<Simulation> delete(Simulation simulation) throws Throwable {
        dataservice.delete(simulation);
        return list(); //Regresa la lista modificada para actualizar la vista
    }
	
}
