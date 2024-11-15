package biomass.simulator.gui.controller;

import java.util.ArrayList;

import biomass.simulator.dataservice.DataService;
import biomass.simulator.gui.model.EcologicalTrait;
import biomass.simulator.gui.model.EcologicalTraitQuery;
import biomass.simulator.gui.model.FunctionalGroupQuery;
import biomass.simulator.gui.model.FunctionalGroup;

public class FunctionalGroupController {
	private DataService dataservice;
	public FunctionalGroupController(DataService dataservice) {
		// TODO Auto-generated constructor stub
		this.dataservice=dataservice;
	}
    public ArrayList<FunctionalGroup> list() throws Throwable {
        return dataservice.get(new FunctionalGroupQuery());
    }
    
    public ArrayList<EcologicalTrait> ecologicalTraitslist() throws Throwable {
        return dataservice.get(new EcologicalTraitQuery());
    }

    public FunctionalGroup create() throws Throwable {
    	FunctionalGroup functionalgroup = new FunctionalGroup();
    	functionalgroup.setNewrecord(true);
    	return functionalgroup;
    }
    
    public ArrayList<FunctionalGroup> save(FunctionalGroup functionalgroup) throws Throwable {
        boolean isNew = functionalgroup.isNewrecord();
        dataservice.save(functionalgroup);
        return isNew ? list() : null;
    }								 

    public ArrayList<FunctionalGroup> delete(FunctionalGroup functionalgroup) throws Throwable {
        dataservice.delete(functionalgroup);
        return list(); 
    }
}
