package multiagent.simulator.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.awt.Graphics2D;
import multiagent.model.environment.PhysicalObject;
import multiagent.model.environment.Space;
import java.awt.image.BufferedImage;  
import javax.imageio.ImageIO;  
import java.io.File;  
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @author candysansores
 *
 */
public class HeadlessDisplay {
	private Space environment;

	public HeadlessDisplay(Space environment) {
		this.environment=environment;
	}

	public void step(int steps) {
		captureSimulationState(steps);;
	}
    private void captureSimulationState(int step) {   
        BufferedImage image = new BufferedImage((int)environment.getWidth(), (int)environment.getHeight(), BufferedImage.TYPE_INT_RGB);  
        Graphics2D g2d = image.createGraphics();  
        
        drawSimulation(g2d, step);   
        g2d.dispose();  
        
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());  
        String fileName = "output/simulation_step_" + step + "_" + timestamp + ".png";  
        try {  
            ImageIO.write(image, "png", new File(fileName));  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  

    private void drawSimulation(Graphics2D g2d, int step) {   
        g2d.setColor(Color.WHITE);  
        g2d.fillRect(0, 0, (int)environment.getWidth(), (int)environment.getHeight());  

		ArrayList<PhysicalObject> drawableobjects = environment.getAllObjects();
		for(int i=0;i<drawableobjects.size();i++)  {
			((DrawableObject)(drawableobjects.get(i))).draw(g2d);
		}
	} 	
}


