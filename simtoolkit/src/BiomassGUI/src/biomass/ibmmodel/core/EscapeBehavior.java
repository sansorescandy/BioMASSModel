/**
 * 
 */
package biomass.ibmmodel.core;


import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

import biomass.ibmmodel.utils.FuzzyFunctions;
import biomass.ibmmodel.utils.Velocity;
import biomass.simulator.core.Bag;
import biomass.simulator.gui.BioMASSGUIFrame;
import multiagent.model.agent.Behavior;

/**
 * @author candysansores
 *
 */
public class EscapeBehavior extends Behavior {
	private Heterotroph agent;
	private Bag predators;
	
	
	private Vector<Double> fears_to_predators = new Vector<Double>(); //vector con la magnitud del miedo que la presa siente por cada depredador
	private double fear_threshold=0.1; //Umbral de miedo del planktivoro
	private double max_fear_to_predator;

	/**
	 * @param agent
	 */
	public EscapeBehavior(Heterotroph agent) {
		// TODO Auto-generated constructor stub
		this.agent=agent;
	}

	/* (non-Javadoc)
	 * @see multiagent.model.subsumption.Behavior#act()
	 */
	@Override
	public void act() {
		// TODO Auto-generated method stub
		agent.getVelocity().Assign(calculateEscapeVelocity());
		
		//La distancia que puede recorrer el pez en 1 timestep (lenghts/s*cm*s=cm) regulado por el foco 
		//agent.velocity.setMagnitude(agent.fg.getMaxSpeed()*agent.length*BioMASSGUIFrame.getInstance().scheduler.getTimeStep()*agent.velocity.getMagnitude());
		agent.getVelocity().setMagnitude(agent.fg.getMaxSpeed()*agent.length*BioMASSGUIFrame.getInstance().scheduler.getTimeStep());
		
		BioMASSGUIFrame.getInstance().seaSpace.move(agent.getID());
	
	    agent.drawablecolor=new Color(0,0,255);
	}
	

	/* (non-Javadoc)
	 * @see multiagent.model.subsumption.Behavior#isActive()
	 */
	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		
		if (agent.isHidden()) return false;
		
		String p=agent.fg.getPredators()[0];
		predators = new Bag();
		
		Bag o = BioMASSGUIFrame.getInstance().seaSpace.getObjectsAtRadio(agent.getID(), agent.fg.getPerceptionRangeFactor()*agent.length);

		if (o.isEmpty()) 
			return false;
		else 
			for(int i=0;i<o.numObjs;i++) {
				//Verifica si son depredadores
				 if(o.objs[i].getClass().getCanonicalName().equalsIgnoreCase(p))
					predators.add(o.objs[i]);
			}
		if(predators.isEmpty())
			return false;
		else 			
		    return isEscapeBehaviorActive();	
	}
	
	
	public boolean isEscapeBehaviorActive() {
		max_fear_to_predator = getMaxFearToPredator();
		if (max_fear_to_predator>fear_threshold)
			return true;
		else 
			return false;
	}
	
	
	
	//La velocidad de escape depende del miedo y de la edad, adem‡s de la posici—n de cada depredador
	public Velocity calculateEscapeVelocity()
	{
		Velocity newVelocity=new Velocity();
		double memb_dist_ref[]=new double[2]; 
		double memb_age[] = new double[2];
		double memb_fear[] = new double[3];
		double act[] = new double[6]; //6 reglas 4 categor’as
		double out[] = new double[4]; 
		double speed;
		double cent[] = {0.5,0.5+1.0/6.0,0.5+1.0/3.0,1.0};
	
		//Evalœa las funciones de membres’a
		
		//distancia al refugio		
	    double dist_ref = BioMASSGUIFrame.getInstance().seaSpace.getDimensionDistance(agent.getID(), agent.getRefuge().getID());
	    
	    memb_dist_ref[0] = FuzzyFunctions.SInv(dist_ref,0,30); //muy cerca
	    memb_dist_ref[1] = 1-memb_dist_ref[0];					//lejos
		
		//edad
		memb_age[0] = FuzzyFunctions.S(agent.getRelativeAge(),0.0,1.0); //el pez es viejo
		memb_age[1] = 1-memb_age[0]; //el pez no es viejo
		
		//miedo
		memb_fear[0] = FuzzyFunctions.Pi(max_fear_to_predator,0.25,0.75); //miedo moderado
		memb_fear[1] = FuzzyFunctions.Pi(max_fear_to_predator,0.5,1.0); //mucho miedo
		memb_fear[2] = FuzzyFunctions.S(max_fear_to_predator,0.75,1.0); //p‡nico
		
		//Si el miedo es moderado y el pez no es viejo la velocidad es ... regular
		act[0] = FuzzyFunctions.Min(memb_age[1],memb_fear[0]);
		//Si el miedo es mucho y el pez no es viejo la velocidad es ... r‡pida
		act[1] = FuzzyFunctions.Min(memb_age[1],memb_fear[1]);
		//Si el miedo=p‡nico y el pez no es viejo la velocidad es ... muy r‡pida
		act[2] = FuzzyFunctions.Min(memb_age[1],memb_fear[2]);
		
		//Si el miedo es moderado y el pez es viejo la velocidad es ... lenta
		act[3] = FuzzyFunctions.Min(memb_age[0],memb_fear[0]);
		//Si el miedo es mucho y el pez es viejo la velocidad es ... regular
		act[4] = FuzzyFunctions.Min(memb_age[0],memb_fear[1]);
		//Si el miedo=p‡nico y el pez es viejo la velocidad es ... r‡pida
		act[5] = FuzzyFunctions.Min(memb_age[0],memb_fear[2]);
		
		out[0] = act[3]; //lenta
		out[1] = Math.sqrt(act[0]*act[0]+act[4]*act[4]); //regular
		out[2] = Math.sqrt(act[1]*act[1]+act[5]*act[5]); //r‡pida
		out[3] = act[2]; //muy r‡pida
		
		//speed indica la magnitud de la velocidad
		speed = (cent[0]*out[0]+cent[1]*out[1]+cent[2]*out[2]+cent[3]*out[3])/(out[0]+out[1]+out[2]+out[3]);
		
		//determina la velocidad de escape considerando la posici—n de los depredadores que ha detectado
		double vector_x_dir_oppos_pred = 0, vector_y_dir_oppos_pred = 0;
		
		

		for(int i=0;i<predators.numObjs;i++) {
			if (fears_to_predators.get(i) > fear_threshold) {
				Organism predator = (Organism) predators.objs[i];
				
//REVISAR CON FLAVIO
				Point2D.Double vector_pred_norm = Velocity.Normalize(predator.getX()-agent.getX(), predator.getY()-agent.getY());
				vector_x_dir_oppos_pred -= fears_to_predators.get(i) * vector_pred_norm.getX();
				vector_y_dir_oppos_pred -= fears_to_predators.get(i) * vector_pred_norm.getY();
			}
		}
		
		//Normaliza el vector resultante con direcci—n contraria a la de los depredadores
		Point2D.Double vector_oppos_pred_norm = Velocity.Normalize(vector_x_dir_oppos_pred, vector_y_dir_oppos_pred);
		
		//vector que apunta hacia el refugio con origen en la posici—n actual de la presa
		double vector_x_refuge = agent.getRefuge().getX()-agent.getX();
		double vector_y_refuge = agent.getRefuge().getY()-agent.getY();
		
		//Normaliza el vector que apunta hacia el refugio con origen en la posici—n actual de la presa
		Point2D.Double vector_refuge_norm = Velocity.Normalize(vector_x_refuge, vector_y_refuge);
		
		//Evalœa el producto punto entre el vector que apunta hacia el refugio
		//y el que sigue la direcci—n contraria de los depredadores
		//(si ambos tienen la misma direcci—n, el refugio se ignora)
		
		double pp = vector_oppos_pred_norm.getX()*vector_refuge_norm.getX()+vector_oppos_pred_norm.getY()*vector_refuge_norm.getY();
		
		//para la direcci—n de escape se considera la posici—n del refugio y la de los depredadores
        //mientras m‡s cercano estŽ al refugio y el depredador m‡s alejado m‡s pesa el refugio
		if(memb_dist_ref[0] > 0.5) //Muy cerca del refugio
		{
			
			newVelocity.Assign(memb_dist_ref[0]*vector_refuge_norm.getX()+memb_dist_ref[1]*vector_oppos_pred_norm.getX(),memb_dist_ref[0]*vector_refuge_norm.getY()+memb_dist_ref[1]*vector_oppos_pred_norm.getY(),speed);					
		}
		else
		{
			//si el refugio y el depredador est‡n en la misma direcci—n se ignora el refugio
	        if(pp < -0.5)
	        {
	        	newVelocity.Assign(vector_oppos_pred_norm.getX(),vector_oppos_pred_norm.getY(),speed);
	        }
	        else
	        {
	        	newVelocity.Assign(vector_refuge_norm.getX()+vector_oppos_pred_norm.getX(),vector_refuge_norm.getY()+vector_oppos_pred_norm.getY(),speed);
	        }
		}
		return newVelocity;
	}
	
	double calculateFearToPredators(double refuge_dist_ratio, double predator_dist_ratio, double prey_pred_weight_ratio)
	{
	    double act[] = new double[8];
	    double memb_dist_pred[] = new double[2];
	    double memb_dist_ref[] = new double[2];
	    double memb_w_rat[] = new double[3]; 
	    double out[] = new double[5];
	    double sum, fear;
	    
	    //evalœa las funciones de membres’a
	    //la variable lingŸ’stica distancia se etiqueta en dos categor’as: lejos y cerca.
	    memb_dist_pred[0] = FuzzyFunctions.SInv(predator_dist_ratio,0.0,1.0); //cerca
	    memb_dist_pred[1] = FuzzyFunctions.S(predator_dist_ratio,0.0,1.0); //lejos
	    
	    memb_dist_ref[0] = FuzzyFunctions.SInv(refuge_dist_ratio,0.0,1.0); //cerca
	    memb_dist_ref[1] = FuzzyFunctions.S(refuge_dist_ratio,0.0,1.0); //lejos
	    
	    memb_w_rat[0] = FuzzyFunctions.Pi(prey_pred_weight_ratio,0.2,0.3,0.1);
	    memb_w_rat[1] = 1.0-memb_w_rat[0]; //La relaci—n entre el tama–o de la presa y el depredador no es la ideal (la presa puede ser muy grande o muy peque–a)
	    memb_w_rat[2] = FuzzyFunctions.Min(memb_w_rat[1],1-FuzzyFunctions.S(prey_pred_weight_ratio,0.2,0.6)); //relaci—n no adecuada y no es peque–o
	   
	    //si la relaci—n de peso no es adecuada y el depredador no es peque–o, el depredador se encuentra lejos y el refugio cercano el miedo es nulo
	    act[0] = FuzzyFunctions.Min(memb_w_rat[2],FuzzyFunctions.Min(memb_dist_pred[1],memb_dist_ref[0]));
	    //si la relaci—n de peso no es adecuada y el depredador no es peque–o, el depredador se encuentra lejos y el refugio lejos el miedo es poco
	    act[1] = FuzzyFunctions.Min(memb_w_rat[2],FuzzyFunctions.Min(memb_dist_pred[1],memb_dist_ref[1]));
	    //si la relaci—n de peso no es adecuada y el depredador no es peque–o, el depredador se encuentra cercano y el refugio cercano el miedo es mucho
	    act[2] = FuzzyFunctions.Min(memb_w_rat[2],FuzzyFunctions.Min(memb_dist_pred[0],memb_dist_ref[0]));
	    //si la relaci—n de peso no es adecuada y el depredador no es peque–o, el depredador se encuentra cercano y el refugio lejos el miedo es mucho
	    act[3] = FuzzyFunctions.Min(memb_w_rat[2],FuzzyFunctions.Min(memb_dist_pred[0],memb_dist_ref[1]));
	    //si la relaci—n de peso es adecuada, el depredador se encuentra lejos y el refugio cercano el miedo es poco
	    act[4] = FuzzyFunctions.Min(memb_w_rat[0],FuzzyFunctions.Min(memb_dist_pred[1],memb_dist_ref[0]));
	    //si la relaci—n de peso es adecuada, el depredador se encuentra lejos y el refugio lejos el miedo es moderado
	    act[5] = FuzzyFunctions.Min(memb_w_rat[0],FuzzyFunctions.Min(memb_dist_pred[1],memb_dist_ref[1]));
	    //si la relaci—n de peso es adecuada, el depredador se encuentra cercano y el refugio cercano el miedo es mucho
	    act[6] = FuzzyFunctions.Min(memb_w_rat[0],FuzzyFunctions.Min(memb_dist_pred[0],memb_dist_ref[0]));
	    //si la relaci—n de peso es adecuada, el depredador se encuentra cercano y el refugio lejos el miedo es "p‡nico"
	    act[7] = FuzzyFunctions.Min(memb_w_rat[0],FuzzyFunctions.Min(memb_dist_pred[0],memb_dist_ref[1]));
	    
	    sum = act[0]+ act[1]+act[2]+act[3]+act[4]+act[5]+act[6]+act[7];
	    
	    if(sum > 0)
	    {
	        out[0] = act[0]; //Nulo
	        out[1] = Math.sqrt(act[1]*act[1]+act[4]*act[4]); //poco
	        out[2] = act[5];
	        out[3] = Math.sqrt(act[3]*act[3]+act[6]*act[6]+act[2]*act[2]); //mucho
	        out[4] = act[7]; //p‡nico
	 
	        fear = (0.25*out[1]+0.5*out[2]+0.75*out[3]+out[4])/(out[0]+out[1]+out[2]+out[3]+out[4]);
	    }
	    else
	        fear = 0;
	    return fear;
	}

	
	
	//Evalœa el miedo a cada uno de los depredadores detectados
	//identifica el m‡ximo valor del miedo, el cual determinar‡ la velocidad de escape.
	double getMaxFearToPredator(){
		double max_fear_to_predator=0;//Maximo de los miedos que siente la presa por los depredadores a su alrededor
		double fear; //Miedo a cada depredador
		double dist_to_predator; //Distancia a la que se encuentra cada depredador
		double dist_to_refuge; //Distancia desde la presa al refugio

//LIMPIA EL VECTOR
		fears_to_predators.clear();
		dist_to_refuge = BioMASSGUIFrame.getInstance().seaSpace.getDistance(agent.getID(), agent.getRefuge().getID());
		
		for(int i=0;i<predators.numObjs;i++) {
			Heterotroph predator= (Heterotroph) predators.objs[i];
			//distancia de la presa al depredador
			dist_to_predator = BioMASSGUIFrame.getInstance().seaSpace.getDistance(predator.getID(), agent.getID());
			//miedo al depredador
			fear=calculateFearToPredators(dist_to_refuge/agent.fg.getPerceptionRangeFactor()*agent.length, dist_to_predator/agent.fg.getPerceptionRangeFactor()*agent.length, (agent.getBiomass().getBiomass()/predator.getBiomass().getBiomass()));

//EL VALOR DEL MIEDO SE ASOCIA AL DEPREDADOR POR EL êNDICE			
			fears_to_predators.add(fear);
			if(fear > max_fear_to_predator){
				max_fear_to_predator=fear;
			}
		}
		return max_fear_to_predator;
	}


}
