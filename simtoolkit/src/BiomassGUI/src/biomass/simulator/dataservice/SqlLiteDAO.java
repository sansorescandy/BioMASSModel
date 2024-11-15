/**
 * 
 */
package biomass.simulator.dataservice;

import biomass.simulator.gui.model.*;
import biomass.simulator.utils.*;
import java.sql.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

/**
 * @author candysansores
 *
 */
public class SqlLiteDAO {
	SqlUtils sqlUtils;
	/**
	 * 
	 */
	public SqlLiteDAO(String location) {
		// TODO Auto-generated constructor stub
		sqlUtils = new SqlUtils("org.sqlite.JDBC", "jdbc:sqlite:" + "lib/biomass.sqlite");
	}

    Simulation map(ResultSet rs, Simulation model) throws Throwable {
        model.setId(rs.getInt("id"));
        model.setName(rs.getString("name"));
        model.setWidth(rs.getDouble("width"));
        model.setLength(rs.getDouble("length"));
        model.setDepth(rs.getDouble("depth"));
        model.setRefuge(rs.getDouble("refuge"));
        model.setZooplankton(rs.getBoolean("zooplankton"));
        return model;
    }

    public ArrayList<Simulation> get(SimulationQuery query) throws Throwable {
        String sql = "select * from Simulation";
        ArrayList<String> restrictions = new ArrayList<String>();
        ArrayList<Object> parameters = new ArrayList<Object>();

        if (query.getId() > 0) {
            restrictions.add("id = ?");
            parameters.add(query.getId());
        }

        if (query.getExcludeId() > 0) {
            restrictions.add("id <> ?");
            parameters.add(query.getExcludeId());
        }

        if (!StringUtils.isEmpty(query.getName())) {
            restrictions.add("name = ?");
            parameters.add(query.getName());
        }

        if (restrictions.size() > 0) sql += " where " + StringUtils.join(restrictions, " and ");
        sql += " order by name collate nocase";
        
        return sqlUtils.executeList(sql, parameters, new IFunction<ResultSet, Simulation>() {
            public Simulation execute(ResultSet param) throws Throwable {
                return map(param, new Simulation());
            }
        });
    }

    public void save(Simulation model) throws Throwable {
        HashMap<String, Object> values = new HashMap<String, Object>();
        if (!model.isNewrecord()) values.put("id", model.getId());
        values.put("name", model.getName());
        values.put("width", model.getWidth());
        values.put("length", model.getLength());
        values.put("depth", model.getDepth());
        values.put("refuge", model.getRefuge());
        values.put("zooplankton", model.isZooplankton());
        if (model.isNewrecord())
            model.setId((int)(Integer)sqlUtils.insert("Simulation", values));
        else
            sqlUtils.update("Simulation", values, "id");
    }

    public void delete(Simulation model) throws Throwable {
        sqlUtils.delete("Simulation", model.getId(), "id");
    }

    FunctionalGroup map(ResultSet rs, FunctionalGroup model) throws Throwable {
        model.setId(rs.getInt("id"));
        model.setName(rs.getString("name"));
        //model.setClassid(rs.getInt("classid"));
        model.setPerceprange(rs.getDouble("perceprange"));
        model.setMaxspeed(rs.getDouble("maxspeed"));
        model.setEscapingspeed(rs.getDouble("escapingspeed"));
        model.setHuntingspeed(rs.getDouble("huntingspeed"));
        model.setWanderingspeed(rs.getDouble("wanderingspeed"));
        model.setForagingspeed(rs.getDouble("foregingspeed"));
        model.setScoutingspeed(rs.getDouble("scoutingspeed"));
        model.setMaxdistancerefuge(rs.getDouble("maxdistrefuge"));
        model.setAlertlevel(rs.getDouble("alertlevel"));
        model.setBehaviorType(rs.getInt("behaviortype"));
        return model;
    }

    public ArrayList<FunctionalGroup> get(FunctionalGroupQuery query) throws Throwable {
    	String sql = "select * from FunctionalGroup";
        ArrayList<String> restrictions = new ArrayList<String>();
        ArrayList<Object> parameters = new ArrayList<Object>();

        if (query.getId() > 0) {
            restrictions.add("id = ?");
            parameters.add(query.getId());
        }

        if (query.getExcludeId() > 0) {
            restrictions.add("id <> ?");
            parameters.add(query.getExcludeId());
        }

        if (!StringUtils.isEmpty(query.getName())) {
            restrictions.add("name = ?");
            parameters.add(query.getName());
        }

        if (restrictions.size() > 0) sql += " where " + StringUtils.join(restrictions, " and ");
        sql += " order by name collate nocase";

        return sqlUtils.executeList(sql, parameters, new IFunction<ResultSet, FunctionalGroup>() {
            public FunctionalGroup execute(ResultSet param) throws Throwable {
                return map(param, new FunctionalGroup());
            }
        });
    }

    public void save(FunctionalGroup model) throws Throwable {
        HashMap<String, Object> values = new HashMap<String, Object>();
        if (!model.isNewrecord()) values.put("id", model.getId());
        values.put("name", model.getName());
        //values.put("classid", model.getClassid());
        values.put("perceprange", model.getPerceprange());
        values.put("maxspeed", model.getMaxspeed());
        values.put("escapingspeed", model.getEscapingspeed());
        values.put("huntingspeed", model.getHuntingspeed());
        values.put("wanderingspeed", model.getWanderingspeed());
        values.put("foregingspeed", model.getForagingspeed());
        values.put("scoutingspeed", model.getScoutingspeed());
        values.put("maxdistrefuge", model.getMaxdistancerefuge());
        values.put("alertlevel", model.getAlertlevel());
        values.put("behaviortype", model.getBehaviorType());
        if (model.isNewrecord())
            model.setId((int)(Integer)sqlUtils.insert("FunctionalGroup", values));
        else
            sqlUtils.update("FunctionalGroup", values, "id");
    }
    
    public void delete(FunctionalGroup model) throws Throwable {
        sqlUtils.delete("FunctionalGroup", model.getId(), "id");
    }
    
    Population map(ResultSet rs, Population model) throws Throwable {
        model.setId(rs.getInt("id"));
        model.setName(rs.getString("name"));
        model.setCohorte(rs.getInt("cohorte"));
        model.setMortalityrate(rs.getDouble("mortalityrate"));
        model.setFg(rs.getInt("fg"));
        model.setLinf(rs.getDouble("linf"));
        model.setK(rs.getDouble("k"));
        model.setT0(rs.getDouble("t0"));
        model.setA(rs.getDouble("a"));
        model.setB(rs.getDouble("b"));
        model.setLifespan(rs.getDouble("lifespan"));
        model.setStomachratio(rs.getDouble("stomachratio"));
        model.setMouthratio(rs.getDouble("mouthratio"));
        model.setMaturitylength(rs.getDouble("maturitylength"));
        model.setRecruitment(rs.getInt("recruitment"));
        model.setRecruitmentlength(rs.getDouble("recruitmentlength"));
        
        return model;
    }
    
    public ArrayList<Population> get(PopulationQuery query) throws Throwable {
        String sql = "select * from Population";
        ArrayList<String> restrictions = new ArrayList<String>();
        ArrayList<Object> parameters = new ArrayList<Object>();

        if (query.getId() > 0) {
            restrictions.add("id = ?");
            parameters.add(query.getId());
        }

        if (query.getExcludeId() > 0) {
            restrictions.add("id <> ?");
            parameters.add(query.getExcludeId());
        }

        if (!StringUtils.isEmpty(query.getName())) {
            restrictions.add("name = ?");
            parameters.add(query.getName());
        }
        
        if (query.getFg() > 0) {
            restrictions.add("fg = ?");
            parameters.add(query.getFg());
        }

        if (restrictions.size() > 0) sql += " where " + StringUtils.join(restrictions, " and ");
        sql += " order by name collate nocase";
        
        return sqlUtils.executeList(sql, parameters, new IFunction<ResultSet, Population>() {
            public Population execute(ResultSet param) throws Throwable {
                return map(param, new Population());
            }
        });
    }
    
    public void save(Population model) throws Throwable {
        HashMap<String, Object> values = new HashMap<String, Object>();
        if (!model.isNewrecord()) values.put("id", model.getId());
        values.put("name", model.getName());
        values.put("cohorte", model.getCohorte());
        values.put("mortalityrate", model.getMortalityrate());
        values.put("fg", model.getFg());  
        values.put("linf", model.getLinf());
        values.put("k", model.getK());
        values.put("t0", model.getT0());
        values.put("a", model.getA());
        values.put("b", model.getB());
        values.put("lifespan", model.getLifeSpan());
        values.put("stomachratio", model.getStomachratio());
        values.put("mouthratio", model.getMouthratio());
        values.put("maturitylength", model.getMaturitylength());
        values.put("recruitment", model.getRecruitment());
        values.put("recruitmentlength", model.getRecruitmentlength());
        
        if (model.isNewrecord())
            model.setId((int)(Integer)sqlUtils.insert("Population", values));
        else
            sqlUtils.update("Population", values, "id");
    }
    
    public void delete(Population model) throws Throwable {
        sqlUtils.delete("Population", model.getId(), "id");
    }


    SimulationPopulation map(ResultSet rs, SimulationPopulation model) throws Throwable {
        model.setSimid(rs.getInt("simid"));
        model.setPopid(rs.getInt("popid"));
        return model;
    }

    public ArrayList<SimulationPopulation> get(SimulationPopulationQuery query) throws Throwable {
        String sql = "select * from SimPop";
        ArrayList<String> restrictions = new ArrayList<String>();
        ArrayList<Object> parameters = new ArrayList<Object>();

        if (query.getSimid() > 0) {
            restrictions.add("simid = ?");
            parameters.add(query.getSimid());
        }

        if (query.getPopid() > 0) {
            restrictions.add("popid = ?");
            parameters.add(query.getPopid());
        }

        if (restrictions.size() > 0) sql += " where " + StringUtils.join(restrictions, " and ");

        return sqlUtils.executeList(sql, parameters, new IFunction<ResultSet, SimulationPopulation>() {
            public SimulationPopulation execute(ResultSet param) throws Throwable {
                return map(param, new SimulationPopulation());
            }
        });
    }

    public void save(SimulationPopulation model) throws Throwable {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("simid", model.getSimid());
        values.put("popid", model.getPopid());
        sqlUtils.insert("SimPop", values);
    }

    public void delete(SimulationPopulation model) throws Throwable {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("simid", model.getSimid());
        values.put("popid", model.getPopid());
        sqlUtils.delete("SimPop", values);
    }

    PreyPredator map(ResultSet rs, PreyPredator model) throws Throwable {
        model.setPredatorid(rs.getInt("predatorid"));
        model.setPreyid(rs.getInt("preyid"));
        return model;
    }
    
    public ArrayList<PreyPredator> get(PreyPredatorQuery query) throws Throwable {
        String sql = "select * from PreyPredator";
        ArrayList<String> restrictions = new ArrayList<String>();
        ArrayList<Object> parameters = new ArrayList<Object>();

        if (query.getPredatorid() > 0) {
            restrictions.add("predatorid = ?");
            parameters.add(query.getPredatorid());
        }

        if (query.getPreyid() > 0) {
            restrictions.add("preyid = ?");
            parameters.add(query.getPreyid());
        }

        if (restrictions.size() > 0) sql += " where " + StringUtils.join(restrictions, " and ");

        return sqlUtils.executeList(sql, parameters, new IFunction<ResultSet, PreyPredator>() {
            public PreyPredator execute(ResultSet param) throws Throwable {
                return map(param, new PreyPredator());
            }
        });
    }
    
    public void save(PreyPredator model) throws Throwable {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("predatorid", model.getPredatorid());
        values.put("preyid", model.getPreyid());
        sqlUtils.insert("PreyPredator", values);
    }    
    
    public void delete(PreyPredator model) throws Throwable {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("predatorid", model.getPredatorid());
        values.put("preyid", model.getPreyid());
        sqlUtils.delete("PreyPredator", values);
    }
  
    //BehaviorType solo es para consulta en esta versi—n, habr’a que crear una interfaz para crear nuevos tipos y que se
    //pudieran combinar comportamientos y sus prioridades de ejecuci—n, es decir, la jerarqu’a se subsunci—n, aunque quiz‡
    //no sea recomendable ya que el usuario podr’a definir tipos incongruentes.
    
    BehaviorType map(ResultSet rs, BehaviorType model) throws Throwable {
        model.setId(rs.getInt("id"));
        model.setName(rs.getString("name"));
        return model;
    }
    
    public ArrayList<BehaviorType> get(BehaviorTypeQuery query) throws Throwable {
        String sql = "select * from BehaviorType";
        ArrayList<String> restrictions = new ArrayList<String>();
        ArrayList<Object> parameters = new ArrayList<Object>();

        if (query.getId() > 0) {
            restrictions.add("id = ?");
            parameters.add(query.getId());
        }

        if (!StringUtils.isEmpty(query.getName())) {
            restrictions.add("name = ?");
            parameters.add(query.getName());
        }

        if (restrictions.size() > 0) sql += " where " + StringUtils.join(restrictions, " and ");

        return sqlUtils.executeList(sql, parameters, new IFunction<ResultSet, BehaviorType>() {
            public BehaviorType execute(ResultSet param) throws Throwable {
                return map(param, new BehaviorType());
            }
        });
    }   
    
    EcologicalTrait map(ResultSet rs, EcologicalTrait model) throws Throwable {
        model.setId(rs.getInt("id"));
        model.setName(rs.getString("name"));
        return model;
    }
    
    public ArrayList<EcologicalTrait> get(EcologicalTraitQuery query) throws Throwable {
        String sql = "select * from EcologicalTrait";
        ArrayList<String> restrictions = new ArrayList<String>();
        ArrayList<Object> parameters = new ArrayList<Object>();

        if (query.getId() > 0) {
            restrictions.add("id = ?");
            parameters.add(query.getId());
        }

        if (!StringUtils.isEmpty(query.getName())) {
            restrictions.add("name = ?");
            parameters.add(query.getName());
        }

        if (restrictions.size() > 0) sql += " where " + StringUtils.join(restrictions, " and ");

        return sqlUtils.executeList(sql, parameters, new IFunction<ResultSet, EcologicalTrait>() {
            public EcologicalTrait execute(ResultSet param) throws Throwable {
                return map(param, new EcologicalTrait());
            }
        });
    }   
}
