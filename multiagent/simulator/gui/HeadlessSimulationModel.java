package multiagent.simulator.gui;

import java.util.ArrayList;
import java.util.Random;
import biomass.continuospace.example.HeadlessPredator;
import biomass.continuospace.example.HeadlessPrey;
import multiagent.model.agent.Scheduler;
import multiagent.model.environment.Space;

/**
 * @author candysansores
 *
 */
public class HeadlessSimulationModel {
	private Thread simulationThread;
    private volatile boolean running = false;
    public Parameter parameters;
    public ArrayList<HeadlessPrey> preys;
	public ArrayList<HeadlessPredator> predators;
	public Scheduler scheduler;
	public Space environment;
	public HeadlessDisplay display;
	public Random rand;
    public int steps;

    public HeadlessSimulationModel(Parameter parameters) {
    	this.parameters=parameters;
        environment = new Space(0, 0, parameters.getWidth(), parameters.getHeigh());
		display=new HeadlessDisplay(environment);
    } 

    public void startSimulation(int steps) {
        this.steps=steps;
    	running = true;	
        scheduler = new Scheduler();
		rand=new Random(System.currentTimeMillis());
		
        preys = new ArrayList<HeadlessPrey>();
     	for(int i=0; i<parameters.getNumPrey(); i++){    
     		HeadlessPrey agent = new HeadlessPrey(rand.nextDouble()*(parameters.getWidth()), rand.nextDouble()*(parameters.getHeigh()), this); 
            environment.insert(agent);
            scheduler.add(agent);
            preys.add(agent);
        }
     	predators = new ArrayList<HeadlessPredator>();
     	for(int i=0; i<parameters.getNumPred(); i++){
            HeadlessPredator agent = new HeadlessPredator(rand.nextDouble()*(parameters.getWidth()), rand.nextDouble()*(parameters.getHeigh()), this);
            environment.insert(agent);
            scheduler.add(agent);
            predators.add(agent);
        }
    	
        simulationThread = new Thread(() -> {
            int simsteps=1;
            while (running && !Thread.currentThread().isInterrupted()) {
                scheduler.step();
                display.step(simsteps++);
                if(simsteps<=steps)
                    continue;
                else
                    running=false;
            }
        });

        simulationThread.start();
    }

	public void stopSimulation() {
		if (simulationThread != null && simulationThread.isAlive()) {
            running = false;
            simulationThread.interrupt();
            try {
                simulationThread.join(); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
