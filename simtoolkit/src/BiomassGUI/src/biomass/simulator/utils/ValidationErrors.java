/**
 * 
 */
package biomass.simulator.utils;

import java.util.*;

/**
 * @author candysansores
 *
 */
public class ValidationErrors extends HashMap<String, ArrayList<String>> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void addError(String key, String value) {
        if (!containsKey(key))
            put(key, new ArrayList<String>());
        get(key).add(value);
    }
}
