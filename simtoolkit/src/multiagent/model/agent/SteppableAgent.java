package multiagent.model.agent;

public interface SteppableAgent {
	public boolean step(long step);
	public void start();
	public void stop();

}
