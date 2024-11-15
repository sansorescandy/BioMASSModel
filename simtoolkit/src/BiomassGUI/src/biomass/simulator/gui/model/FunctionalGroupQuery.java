/**
 * 
 */
package biomass.simulator.gui.model;

/**
 * @author candysansores
 *
 */
public class FunctionalGroupQuery {
	private int id;
    private String name;
    private int excludeId;
    
	/**
	 * 
	 */
	public FunctionalGroupQuery() {
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

}
