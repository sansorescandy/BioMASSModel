package biomass.simulator.gui;

public class Parameter {
    private float width;
    private float heigh;
    private int numPrey;
    private int numPred;
    //All prey within a certain distance
    private int preyNeighborDistance;
    // Select only the closest neighbors
    private int maxPreyNeighbors;
    // Smaller cloth give a more organized, cloth like look
    private float preySpacing;
    // Attract force for prey
    private float preyAttractForce;
    // Higher repulsion force gives more randomness
    private float preyRepelForce;
    // The distance a prey can see a predator
    private float preyFearRadius; //80.0
    // Repulsion force for predator
    private float preyFearForce;
 	//Prey acceleration
    private float preyAcceleration;
 	//Prey max speed
    private float preyMaxSpeed;
 	 //All prey within a certain distance
    private float predPerceptionDistance;
    // How close the predator has to be to the prey to kill it
    private float killRadius; //10
    private float predAcceleration;
    private float predMaxSpeed;
	//Reduce to speed up simulation speed.
    private float timeScale;
	
	
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	
	public float getHeigh() {
		return heigh;
	}
	public void setHeigh(float heigh) {
		this.heigh = heigh;
	}
	public int getNumPrey() {
		return numPrey;
	}
	public void setNumPrey(int numPrey) {
		this.numPrey = numPrey;
	}
	public int getNumPred() {
		return numPred;
	}
	public void setNumPred(int numPred) {
		this.numPred = numPred;
	}
	public int getPreyNeighborDistance() {
		return preyNeighborDistance;
	}
	public void setPreyNeighborDistance(int preyNeighborDistance) {
		this.preyNeighborDistance = preyNeighborDistance;
	}
	public int getMaxPreyNeighbors() {
		return maxPreyNeighbors;
	}
	public void setMaxPreyNeighbors(int maxPreyNeighbors) {
		this.maxPreyNeighbors = maxPreyNeighbors;
	}
	public float getPreySpacing() {
		return preySpacing;
	}
	public void setPreySpacing(float preySpacing) {
		this.preySpacing = preySpacing;
	}
	public float getPreyAttractForce() {
		return preyAttractForce;
	}
	public void setPreyAttractForce(float preyAttractForce) {
		this.preyAttractForce = preyAttractForce;
	}
	public float getPreyRepelForce() {
		return preyRepelForce;
	}
	public void setPreyRepelForce(float preyRepelForce) {
		this.preyRepelForce = preyRepelForce;
	}
	public float getPreyFearRadius() {
		return preyFearRadius;
	}
	public void setPreyFearRadius(float preyFearRadius) {
		this.preyFearRadius = preyFearRadius;
	}
	public float getPreyFearForce() {
		return preyFearForce;
	}
	public void setPreyFearForce(float preyFearForce) {
		this.preyFearForce = preyFearForce;
	}
	public float getPreyAcceleration() {
		return preyAcceleration;
	}
	public void setPreyAcceleration(float preyAcceleration) {
		this.preyAcceleration = preyAcceleration;
	}
	public float getPreyMaxSpeed() {
		return preyMaxSpeed;
	}
	public void setPreyMaxSpeed(float preyMaxSpeed) {
		this.preyMaxSpeed = preyMaxSpeed;
	}
	public float getPredPerceptionDistance() {
		return predPerceptionDistance;
	}
	public void setPredPerceptionDistance(float predPerceptionDistance) {
		this.predPerceptionDistance = predPerceptionDistance;
	}
	public float getKillRadius() {
		return killRadius;
	}
	public void setKillRadius(float killRadius) {
		this.killRadius = killRadius;
	}
	public float getPredAcceleration() {
		return predAcceleration;
	}
	public void setPredAcceleration(float predAcceleration) {
		this.predAcceleration = predAcceleration;
	}
	public float getPredMaxSpeed() {
		return predMaxSpeed;
	}
	public void setPredMaxSpeed(float predMaxSpeed) {
		this.predMaxSpeed = predMaxSpeed;
	}
	public float getTimeScale() {
		return timeScale;
	}
	public void setTimeScale(float timeScale) {
		this.timeScale = timeScale;
	}
	
}
