/**
 * 
 */
package biomass.simulator.gui.model;

/**
 * @author candysansores
 *
 */
public class PopulationQuery {
	private int id;
	private String name;
	private int fg;
    private int excludeId;
    private FunctionalGroupQuery functionalgroupquery;
    private boolean allfgrequested;

    
	/**
	 * 
	 */
	public PopulationQuery() {
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
	 * @return the excludeId
	 */
	public int getExcludeId() {
		return excludeId;
	}

	/**
	 * @param excludeId the excludeId to set
	 */
	public void setExcludeId(int excludeId) {
		this.excludeId = excludeId;
	}

	/**
	 * @return the functionalgroupquery
	 */
	public FunctionalGroupQuery getFunctionalGroupQuery() {
		return functionalgroupquery;
	}

	/**
	 * @param functionalgroupquery the functionalgroupquery to set
	 */
	public void setFunctionalGroupQuery(FunctionalGroupQuery functionalgroupquery) {
		this.functionalgroupquery = functionalgroupquery;
	}

	/**
	 * @return the allfgrequested
	 */
	public boolean isAllFgRequested() {
		return allfgrequested;
	}

	/**
	 * @param allfgrequested the allfgrequested to set
	 */
	public void setAllFgRequested(boolean allfgrequested) {
		this.allfgrequested = allfgrequested;
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
	
	

}
