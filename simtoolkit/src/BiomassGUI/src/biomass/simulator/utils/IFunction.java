/**
 * 
 */
package biomass.simulator.utils;

/**
 * @author candysansores
 *
 */
public interface IFunction<T, U> {
	U execute(T param) throws Throwable;
}
