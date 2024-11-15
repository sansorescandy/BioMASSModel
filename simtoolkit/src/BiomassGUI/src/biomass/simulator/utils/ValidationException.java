/**
 * 
 */
package biomass.simulator.utils;

/**
 * @author candysansores
 *
 */
public class ValidationException extends Throwable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ValidationErrors errors;

    public ValidationErrors getErrors() {
        return errors;
    }

    public ValidationException(ValidationErrors errors) {
        this.errors = errors;
    }
}
