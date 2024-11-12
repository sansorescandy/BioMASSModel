package multiagent.simulator.gui;

import java.io.File;

public class HeadlessMain {  
    public static void main(String[] args) {  
        int steps=10;
        // Headless
        System.setProperty("java.awt.headless", "true");  
  
        Parameter parameters = new Parameter(); 
        setParameters(parameters);  

        HeadlessSimulationModel simulationModel = new HeadlessSimulationModel(parameters); 
        createOutputDirectory(); 
        simulationModel.startSimulation(steps);  
    }  
        
    private static void createOutputDirectory() {  
        File outputDir = new File("output");  
        if (!outputDir.exists()) {  
            outputDir.mkdir(); 
        }  
    }  

    private static void setParameters(Parameter parameters) {  
        // Configure parameters
        parameters.setWidth(800);
        parameters.setHeigh(600); 
        parameters.setNumPrey(3000);  
        parameters.setNumPred(4);  
        parameters.setPreyNeighborDistance(20);  
        parameters.setMaxPreyNeighbors(7);  
        parameters.setPreySpacing(8.0f);  
        parameters.setPreyAttractForce(60.0f);  
        parameters.setPreyRepelForce(-80.0f);  
        parameters.setPreyFearRadius(80.0f);  
        parameters.setPreyFearForce(-500000.0f);  
        parameters.setPreyAcceleration(1.0f);  
        parameters.setPreyMaxSpeed(70.0f);  
        parameters.setPredPerceptionDistance(100.0f);  
        parameters.setKillRadius(60.0f);  
        parameters.setPredAcceleration(3.0f);  
        parameters.setPredMaxSpeed(300.0f);  
        parameters.setTimeScale(0.095f);  
    }
    
}