/**
 * 
 */
package biomass.ibmmodel.core;

import biomass.ibmmodel.utils.FuzzyFunctions;
import biomass.ibmmodel.utils.Velocity;
import biomass.simulator.core.Bag;
import biomass.simulator.gui.BioMASSGUIFrame;
import multiagent.model.agent.Behavior;

import java.awt.Color;
import java.util.Random;


/**
 * @author candysansores
 *
 */
public class ScoutingBehavior extends Behavior {
	private Heterotroph agent;
	private static double changeDirProbability=0.05;
	/**
	 * 
	 */
	public ScoutingBehavior(Heterotroph agent) {
		// TODO Auto-generated constructor stub
		this.agent=agent;
	}

	/* (non-Javadoc)
	 * @see multiagent.model.agent.Behavior#act(biomass.ibmmodel.core.Heterotroph)
	 */
	@Override
	public void act() {
		// TODO Auto-generated method stub
		agent.drawablecolor=new Color(0,255,255);
		
		setScoutingVelocity();
		agent.getVelocity().setMagnitude(agent.fg.getForagingSpeed()*agent.length*BioMASSGUIFrame.getInstance().scheduler.getTimeStep());
		
		BioMASSGUIFrame.getInstance().seaSpace.move(agent.getID());
			   
		
		//Verifica si encuentra un nuevo refugio
		
		
		Bag o = BioMASSGUIFrame.getInstance().seaSpace.getDimensionObjectsAtRadio(agent.getID(), agent.fg.getPerceptionRangeFactor()*agent.length);
	
		
		double dist=0, mindist=Double.MAX_VALUE;
		Refuge refuge=null;
		for(int i=0;i<o.numObjs;i++) {
			if (o.objs[i].getClass().getCanonicalName().equalsIgnoreCase("biomass.ibmmodel.core.Refuge")) { //Verifica si son refugios
				dist=BioMASSGUIFrame.getInstance().seaSpace.getDimensionDistance(((Refuge)(o.objs[i])).getID(), agent.getID());
				if(dist<mindist) { //Selecciona el m‡s cercano
					mindist=dist;
					refuge=(Refuge)o.objs[i];
				}
			}
		}
		if(refuge!=null)
			agent.setRefuge(refuge);	
	}

	/* (non-Javadoc)
	 * @see multiagent.model.agent.Behavior#isActive(biomass.ibmmodel.core.Heterotroph)
	 */
	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return true;
	}
	
	//Mientras m‡s hambre, menor es la liga del individuo con el refugio
	//por lo cual pueden salir a explorar m‡s lejos
	private void setScoutingVelocity() {
		double maxDistFromRefRatio=100;
		double cx, cy;
		double refDist, refuge_attraction;
		
		//double dist_ref_max=BioMASSGUIFrame.getInstance().seaSpace.getWidth()/2;
		double maxDistFromRef=agent.length*maxDistFromRefRatio; // Valor ajustado por Dr. Luis Calder—n
		
		setWanderingVelocity();
		agent.getVelocity().Normalize();
		
		//vector que apunta al refugio normalizado	
		Velocity refugeVelNorm=BioMASSGUIFrame.getInstance().seaSpace.getDimensionDistanceVector(agent.getRefuge().id, agent.id);
		refDist=refugeVelNorm.getMagnitude();
		refugeVelNorm.Normalize();
		
		//peso del vector hacia el refugio
		refuge_attraction = calcRefugeAttraction(agent.getHunger(),refDist,maxDistFromRef);
		cx = refuge_attraction*refugeVelNorm.getCx()+(1-refuge_attraction)*agent.getVelocity().getCx();
		cy = refuge_attraction*refugeVelNorm.getCy()+(1-refuge_attraction)*agent.getVelocity().getCy();
		agent.getVelocity().Assign(cx,cy);
	}

	public void setWanderingVelocity()
	{
	    Velocity randomComponent = new Velocity();
	    
	    if(BioMASSGUIFrame.getInstance().r.nextDouble() < changeDirProbability)
	    {
	    	agent.getVelocity().Normalize();
	        randomComponent.Assign(2*BioMASSGUIFrame.getInstance().r.nextDouble()-1.0,2*BioMASSGUIFrame.getInstance().r.nextDouble()-1.0);
	        randomComponent.Normalize();
	        agent.getVelocity().Assign(agent.getVelocity().getCx()+randomComponent.getCx(), agent.getVelocity().getCy()+randomComponent.getCy());
	    }
	}
	
	private double calcRefugeAttraction(double hunger, double distFromRef, double maxDistFromRef){
//		return FuzzyFunctions.SInv(hunger,0,0.5)*Math.pow(FuzzyFunctions.S(distFromRef/maxDistFromRef,0,hunger),5);
		return FuzzyFunctions.S(distFromRef/maxDistFromRef,0,0.1+hunger*0.9);
	}

	
	//Mientras m‡s hambre, menor es la liga del individuo con el refugio
	//por lo cual pueden salir a explorar m‡s lejos
/*	private Velocity getScoutingVelocity2() {
		double maxDistFromRefRatio=100;
		double directionchangeprobability=0.1, cx, cy;
		double refDist, refuge_attraction;
		Velocity newVelocity=new Velocity();
		
		//double dist_ref_max=BioMASSGUIFrame.getInstance().seaSpace.getWidth()/2;
		double maxDistFromRef=agent.length*maxDistFromRefRatio; // Valor ajustado por Dr. Luis Calder—n
		
		Random r=BioMASSGUIFrame.getInstance().r;
		
		//vector con direcci—n actual del agente normalizada
		Velocity agentVelNorm=new Velocity();
		agentVelNorm.Assign(agent.getVelocity());
		agentVelNorm.Normalize();
		
		//vector que apunta al refugio normalizado	
		Velocity refugeVelNorm=new Velocity(agent.getRefuge().getX()-agent.getX(), agent.getRefuge().getY()-agent.getY());
		refDist=refugeVelNorm.getMagnitude();
		refugeVelNorm.Normalize();
		
		//peso del vector hacia el refugio
		
         //PRUEBA
		refuge_attraction = calcRefugeAttraction(agent.getHunger(),refDist,maxDistFromRef);
		
		if(r.nextDouble()<directionchangeprobability) {
			
			//vector con direcci—n aleatoria normalizada
			Velocity randVelNorm=new Velocity(r.nextDouble()*2-1, r.nextDouble()*2-1);
			randVelNorm.Normalize();

	        cx = agentVelNorm.getCx()+0.5*(refuge_attraction*refugeVelNorm.getCx()+(1-refuge_attraction)*randVelNorm.getCx());
	        cy = agentVelNorm.getCy()+0.5*(refuge_attraction*refugeVelNorm.getCy()+(1-refuge_attraction)*randVelNorm.getCy());

		}
		else {
			//cx = agentVelNorm.getCx()+0.5*weight_ref*refugeVelNorm.getCx();
	        //cy = agentVelNorm.getCy()+0.5*weight_ref*refugeVelNorm.getCy();
	        cx = agentVelNorm.getCx();
	        cy = agentVelNorm.getCy();
		}
			
		newVelocity.Assign(cx,cy);
        newVelocity.Normalize();	    
		return newVelocity;
	}
*/


}
