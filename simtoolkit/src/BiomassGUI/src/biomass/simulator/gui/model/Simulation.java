/**
 * 
 */
package biomass.simulator.gui.model;

import java.util.ArrayList;


/**
 * @author candysansores
 *
 */
public class Simulation {
	private int id;
    private String name;
    private double width;
    private double length;
    private double depth;
    private double refuge;
    private boolean zooplankton;
    private ArrayList<Integer> popids;
    private ArrayList<Population> popobjs;
    private ArrayList<Population> allpopulations;
    private boolean newrecord;
  

	public Simulation() {
		popids = new ArrayList<Integer>();
		popobjs = new ArrayList<Population>();
		allpopulations = new ArrayList<Population>();
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
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/**
	 * @return the length
	 */
	public double getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(double length) {
		this.length = length;
	}

	/**
	 * @return the depth
	 */
	public double getDepth() {
		return depth;
	}

	/**
	 * @param depth the depth to set
	 */
	public void setDepth(double depth) {
		this.depth = depth;
	}

	/**
	 * @return the refuge
	 */
	public double getRefuge() {
		return refuge;
	}

	/**
	 * @param refuge the refuge to set
	 */
	public void setRefuge(double refuge) {
		this.refuge = refuge;
	}
	
	/**
	 * @return the zooplankton
	 */
	public boolean isZooplankton() {
		return zooplankton;
	}

	/**
	 * @param zooplankton the zooplankton to set
	 */
	public void setZooplankton(boolean zooplankton) {
		this.zooplankton = zooplankton;
	}

	/**
	 * @return the popids
	 */
	public ArrayList<Integer> getPopids() {
		return popids;
	}

	/**
	 * @param popids the popids to set
	 */
	public void setPopids(ArrayList<Integer> popids) {
		this.popids = popids;
	}

	/**
	 * @return the popobjs
	 */
	public ArrayList<Population> getPopobjs() {
		return popobjs;
	}

	/**
	 * @param popobjs the popobjs to set
	 */
	public void setPopobjs(ArrayList<Population> popobjs) {
		this.popobjs = popobjs;
	}

	/**
	 * @return the allpopulations
	 */
	public ArrayList<Population> getAllpopulations() {
		return allpopulations;
	}

	/**
	 * @param allpopulations the allpopulations to set
	 */
	public void setAllPopulations(ArrayList<Population> allpopulations) {
		this.allpopulations = allpopulations;
	}

    public boolean isNewrecord() {
        return newrecord;
    }

    public void setNewrecord(boolean newrecord) {
        this.newrecord = newrecord;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
    
    
    
}
