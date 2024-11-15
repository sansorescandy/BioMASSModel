package biomass.simulator.core;

public interface SteppableObject {
	public void step(long step);
	public void start();
	public void stop();

}
