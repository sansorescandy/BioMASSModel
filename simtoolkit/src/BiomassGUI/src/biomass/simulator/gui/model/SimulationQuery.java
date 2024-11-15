/**
 * 
 */
package biomass.simulator.gui.model;

/**
 * @author candysansores
 *
 */
public class SimulationQuery {
	private int id;
    private int excludeId;
    private String name;
    private SimulationPopulationQuery simpopquery;
    private boolean allpoprequested;

	/**
	 * 
	 */
	public SimulationQuery() {
		// TODO Auto-generated constructor stub
	}
	
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExcludeId() {
        return excludeId;
    }

    public void setExcludeId(int excludeId) {
        this.excludeId = excludeId;
    }

    public SimulationPopulationQuery getSimPopQuery() {
        return simpopquery;
    }

    public void setSimPopQuery(SimulationPopulationQuery simpopquery) { //Query para obtener las poblaciones involucradas en esta simulaci—n
        this.simpopquery = simpopquery;
    }

    public boolean isAllPopRequested() {
        return allpoprequested;
    }

    public void setAllPopRequested(boolean allpoprequested) {
        this.allpoprequested = allpoprequested;
    }

}
