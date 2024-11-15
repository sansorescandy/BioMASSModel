/**
 * 
 */
package biomass.simulator.dataservice;

import java.util.*;

//import biomass.ibmmodel.utils.*;
import biomass.simulator.gui.model.*;
import biomass.simulator.utils.*;


/**
 * @author candysansores
 *
 */
public class DataService {
	SqlLiteDAO dao;
	/**
	 * 
	 */
	public DataService(SqlLiteDAO dao) {
		// TODO Auto-generated constructor stub
		this.dao = dao;
	}
	
    public ArrayList<Simulation> get(SimulationQuery query) throws Throwable {
        ArrayList<Simulation> list = dao.get(query);

        if (query.isAllPopRequested()) { //Selecciona todas las poblaciones que existen en la base de datos								  
            ArrayList<Population> allpops = get(new PopulationQuery()); //Nota: Quiz‡ no es necesario hacerlo para cada simulaci—n sino sacarlas todas
            for (Simulation model : list)								//una sola vez para el GUI
                model.setAllPopulations(allpops);
        }

        if (query.getSimPopQuery() != null) { //Selecciona las poblaciones que le corresponden a la simulaci—n
            for (Simulation model : list) {
                query.getSimPopQuery().setSimid(model.getId());
                //Todo este c—digo es para seleccionar los IDs (integers) de las poblaciones que le corresponden a la simulaci—n
                //de una manera elegante, aunque tengo mis dudas de que sea eficaz y por otro lado resulta confuso
                model.setPopids(new ArrayList<Integer>(CollectionUtils.select(get(query.getSimPopQuery()), new IFunction<SimulationPopulation, Integer>() {
                    public Integer execute(SimulationPopulation model) throws Throwable { 
                        return model.getPopid();
                    }
                })));
            }
        }

        return list;
    }

    public boolean isValid(Simulation model, ValidationErrors errors) throws Throwable {
        if (!model.isNewrecord()) {
            SimulationQuery query = new SimulationQuery();
            query.setId(model.getId());
            Simulation oldModel = CollectionUtils.firstOrDefault(get(query));
            if (oldModel == null)
                errors.addError("name", "This simulation doesn't exist anymore");
        }
        
        if (model.getName() == null ? "" == null : model.getName().equals("")) //No entiendo por quŽ la verificaci—n de: ""==null
            errors.addError("name", "The simulation name can't be empty");
        else if(model.getName().length() > 255)
            errors.addError("name", "The simulation name must be 255 characters long or less");
        else {
            SimulationQuery request = new SimulationQuery();
            request.setExcludeId(model.getId());
            request.setName(model.getName());
            if (get(request).size() > 0)
                errors.addError("name", "There is already a simulation with this name");
        }
        if (model.getWidth() <= 0)
            errors.addError("width", "The simulation width must be greater than 0");
        if (model.getDepth() <= 0)
            errors.addError("depth", "The simulation depth must be greater than 0");
        if (model.getLength() <= 0)
            errors.addError("length", "The simulation length must be greater than 0");
        return errors.size() == 0;
    }

    public void save(final Simulation model) throws Throwable {
        ValidationErrors errors = new ValidationErrors();
        if (isValid(model, errors)) {
            dao.save(model);

            if (model.isNewrecord())
                model.setNewrecord(false);
            else {
                SimulationQuery query = new SimulationQuery();
                query.setId(model.getId());
                query.setSimPopQuery(new SimulationPopulationQuery());
                Simulation oldmodel = CollectionUtils.firstOrDefault(get(query));
                
                for (int id : oldmodel.getPopids()) { //Obtiene todas las pobaciones que participan en esta simulaci—n
                    SimulationPopulation simpop = new SimulationPopulation(); //Crea un objeto que sirve para acceder a la tabla "Simpop"
                    simpop.setSimid(model.getId());
                    simpop.setPopid(id);
                    delete(simpop); //Borra de la tabla la relaci—n de poblaciones de esta simulaci—n en particular
                }
            }

            for (int id : model.getPopids()) { //Obtiene todas las nuevas poblaciones de esta simulaci—n
                SimulationPopulation simpop = new SimulationPopulation();
                simpop.setSimid(model.getId());
                simpop.setPopid(id);
                save(simpop); //Las guarda de nuevo en la tabla "Simpop"
            }
        } else
            throw new ValidationException(errors);
    }

    public void delete(Simulation model) throws Throwable {
        dao.delete(model);
        SimulationPopulationQuery query = new SimulationPopulationQuery();
        query.setSimid(model.getId());
        ArrayList<SimulationPopulation> simpops = get(query);
        for (SimulationPopulation simpop : simpops)
            delete(simpop);
    }

    public ArrayList<Population> get(PopulationQuery query) throws Throwable {
        ArrayList<Population> list = dao.get(query);
        if (query.isAllFgRequested()) { //Selecciona todos los grupos funcionales que existen en la base de datos	
            ArrayList<FunctionalGroup> allfg = get(new FunctionalGroupQuery()); //Nota: Quiz‡ no es necesario hacerlo para cada poblaci—n
            for (Population model : list)										//sino sacarlos todos una sola vez para el GUI
                model.setAllfunctionalgroups(allfg);
        }
        return list;
    }

    public ArrayList<FunctionalGroup> get(FunctionalGroupQuery query) throws Throwable {
        return dao.get(query);
    }
    
    public boolean isValid(FunctionalGroup model, ValidationErrors errors) throws Throwable {
        if (model.getName() == null ? "" == null : model.getName().equals(""))  
            errors.addError("name", "The functionalgroup name can't be empty");
        else if(model.getName().length() > 255)
            errors.addError("name", "The functionalgroup name must be 255 characters long or less");
        else {
            FunctionalGroupQuery query = new FunctionalGroupQuery();
            query.setExcludeId(model.getId());
            query.setName(model.getName());
            if (get(query).size() > 0)
                errors.addError("name", "There is already a functionalgroup with this name");
        }
        if (model.getPerceprange() <= 0)
            errors.addError("perceprange", "The perceprange must be greater than 0");
        if (model.getMaxspeed() <= 0)
            errors.addError("maxspeed", "The maxspeed must be greater than 0");
        if (model.getEscapingspeed() <= 0)
            errors.addError("escapingspeed", "The escapingspeed must be greater than 0");
        if (model.getHuntingspeed() <= 0)
            errors.addError("huntingspeed", "The huntingspeed must be greater than 0");
        if (model.getWanderingspeed() <= 0)
            errors.addError("wanderingspeed", "The wanderingspeed must be greater than 0");
        if (model.getForagingspeed() <= 0)
            errors.addError("foragingspeed", "The foragingspeed must be greater than 0");
        if (model.getScoutingspeed() <= 0)
            errors.addError("scoutingspeed", "The scoutingspeed must be greater than 0");
        if (model.getMaxdistancerefuge() <= 0)
            errors.addError("maxdistancerefuge", "The maxdistancerefuge must be greater than 0");
        if (model.getAlertlevel() <= 0)
            errors.addError("alertlevel", "The alertlevel must be greater than 0");
        return errors.size() == 0;
    }
    
    public boolean isValid(Population model, ValidationErrors errors) throws Throwable {
        if (model.getName() == null ? "" == null : model.getName().equals(""))  
            errors.addError("name", "The population name can't be empty");
        else if(model.getName().length() > 255)
            errors.addError("name", "The population name must be 255 characters long or less");
        else {
            PopulationQuery query = new PopulationQuery();
            query.setExcludeId(model.getId());
            query.setName(model.getName());
            if (get(query).size() > 0)
                errors.addError("name", "There is already a population with this name");
        }
        if (model.getCohorte() <= 0)
            errors.addError("cohorte", "The cohorte must be greater than 0");
        if (model.getMortalityrate() <= 0)
            errors.addError("mortalityrate", "The mortality rate must be greater than 0 ");
        else if (model.getMortalityrate() > 1)
            errors.addError("mortalityrate", "The mortality rate must be less than or equal to 1");   
        return errors.size() == 0;
    }

    public void save(FunctionalGroup model) throws Throwable {
        ValidationErrors errors = new ValidationErrors();
        if (isValid(model, errors)) {
            dao.save(model);	//Falta el c—digo para dar de alta a las presas de este grupo funcional
            if (model.isNewrecord())
                model.setNewrecord(false);
        } else
            throw new ValidationException(errors);
    }
    
    public void save(Population model) throws Throwable {
        ValidationErrors errors = new ValidationErrors();
        if (isValid(model, errors)) {
            dao.save(model);
            if (model.isNewrecord())
                model.setNewrecord(false);
        } else
            throw new ValidationException(errors);
    }

    public boolean isValidDelete(FunctionalGroup model, ValidationErrors errors) throws Throwable {
    	PopulationQuery query = new PopulationQuery();
        query.setFg(model.getId());
        if (get(query).size() > 0)
            errors.addError("fg", "Populations are still associated with this functional group");
        return errors.size() == 0;
    }
    
    public boolean isValidDelete(Population model, ValidationErrors errors) throws Throwable {
        SimulationPopulationQuery query = new SimulationPopulationQuery();
        query.setPopid(model.getId());
        if (get(query).size() > 0)
            errors.addError("fg", "Simulations are still associated with this population");
        return errors.size() == 0;
    }
    
    public void delete(FunctionalGroup model) throws Throwable {
    	ValidationErrors errors = new ValidationErrors();
        if (isValidDelete(model, errors)) {
        	dao.delete(model);
        } else
            throw new ValidationException(errors);
    }

    public void delete(Population model) throws Throwable {
    	ValidationErrors errors = new ValidationErrors();
        if (isValidDelete(model, errors)) {
        	dao.delete(model);
        } else
            throw new ValidationException(errors);
    }
    
    public ArrayList<SimulationPopulation> get(SimulationPopulationQuery query) throws Throwable {
        return dao.get(query);
    }
    
    public void save(SimulationPopulation model) throws Throwable {
        dao.save(model);
    }

    public void delete(SimulationPopulation model) throws Throwable {
        dao.delete(model);
    }
    
    public ArrayList<BehaviorType> get(BehaviorTypeQuery query) throws Throwable {
        return dao.get(query);
    }
    
    public ArrayList<EcologicalTrait> get(EcologicalTraitQuery query) throws Throwable {
        return dao.get(query);
    }
}
