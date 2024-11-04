package biomass.simulator.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;

import java.util.ArrayList;
import java.util.Random;


import javax.swing.JFrame;

import biomass.continuospace.test.Predator;
import biomass.continuospace.test.Prey;
import multiagent.model.agent.Scheduler;
import multiagent.model.environment.Space;
import java.awt.event.WindowAdapter;

/**
 * @author candysansores
 *
 */
public class SimulationModel {
	private Thread simulationThread;
    private volatile boolean running = false;
    public Parameter parameters;
    public ArrayList<Prey> preys;
	public ArrayList<Predator> predators;
	public Scheduler scheduler;
	public Space environment;
	public Display display;
	public JFrame frame;
	public Random rand;
	public Main main;

    public SimulationModel(Main main) {
    	parameters=main.getParameters();
    	frame = new JFrame("BioMASS Space Model in Action");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                main.onSimulationClosed();
            }
        });
        environment = new Space(0, 0, parameters.getWidth(), parameters.getHeigh());
		display=new Display(environment);
        frame.setLayout(new BorderLayout());
        frame.add(display, BorderLayout.CENTER);
	    frame.pack();
	    frame.setVisible(true);
    }
    

    public void startSimulation() {
    	running = true;
    	
        scheduler = new Scheduler();
		rand=new Random(System.currentTimeMillis());
		
        preys = new ArrayList<Prey>();
     	for(int i=0; i<parameters.getNumPrey(); i++){    
     		Prey agent = new Prey(rand.nextDouble()*(parameters.getWidth()), rand.nextDouble()*(parameters.getHeigh()), this); 
            environment.insert(agent);
            frame.add(display, BorderLayout.CENTER);
            scheduler.add(agent);
            preys.add(agent);
        }
     	predators = new ArrayList<Predator>();
     	for(int i=0; i<parameters.getNumPred(); i++){
            Predator agent = new Predator(rand.nextDouble()*(parameters.getWidth()), rand.nextDouble()*(parameters.getHeigh()), this);
            environment.insert(agent);
            scheduler.add(agent);
            predators.add(agent);
        }
    	
        simulationThread = new Thread(() -> {
            while (running && !Thread.currentThread().isInterrupted()) {
                scheduler.step();
                display.step();
            }
        });

        simulationThread.start();
    }


	public void stopSimulation() {
		if (simulationThread != null && simulationThread.isAlive()) {
            running = false;
            simulationThread.interrupt(); // Interrumpir el hilo de simulación
            try {
                simulationThread.join(); // Esperar a que el hilo termine
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.frame.dispose();
        }
    }
}
