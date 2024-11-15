/**
 * 
 */
package biomass.model.behaviors;


import java.awt.Color;

import biomass.model.taxonomy.Heterotroph;
import biomass.model.taxonomy.Organism;
import biomass.model.utils.Velocity;
import biomass.simulator.core.Bag;
import biomass.simulator.gui.BioMASSGUIFrame;
import multiagent.model.agent.Behavior;

/**
 * @author flavioreyes
 * Bajo este comportamiento el organismo huye a toda velocidad a esconderse en el refugio que tiene
 * memorizado y trata de entrar. Si no puede entrar, olvida el refugio.
 * Se activa si hay refugio en memoria, si no est‡ oculto y hay amenazas.
 */
public class HideBehavior extends Behavior {
	private Heterotroph agent;
	
	
	private Bag dangerous_predators;
	private double hideSpeed; 
	private String predatorName;
	private Color hidingColor;

	/**
	 * @param agent
	 */
	public HideBehavior(Heterotroph agent) {
		this.agent=agent;
		hideSpeed = agent.fg.maxSpeed;
		predatorName=agent.fg.predators[0];
		// Se crea una lista nueva de depredadores peligrosos
		dangerous_predators = new Bag();
		hidingColor = new Color(255,255,0); // Magenta
	}

	/* (non-Javadoc)
	 * @see multiagent.model.subsumption.Behavior#act()
	 */
	@Override
	public void act() {
		// Nuestro color es
		agent.drawablecolor = hidingColor;
		// Obtenemos el vector hacia el refugio
		agent.getVelocity().Assign(calculateHideVelocity());
		// Se mueve al organismo
		BioMASSGUIFrame.getInstance().seaSpace.move(agent.getID());
		// Calculamos la distancia que nos separa del refugio
		double dist_to_refuge = BioMASSGUIFrame.getInstance().seaSpace.getDistance(agent.getID(), agent.getRefuge().getID());
		dist_to_refuge += agent.radio - agent.getRefuge().radio;
		// Verificamos si ya llegamos al refugio
		if(dist_to_refuge <= 0){	
			// Ya llegamos!
			// Tratamos de entrar
			if(agent.enterRefuge()){
				// Ya entramos 
			}
			else{
				// Si no pudimos entrar perdemos el refugio
				agent.setRefuge(null);
			}
		}
		else{
			// No hemos llegado al refugio
			if(agent.shout)
				System.out.println("Agent:"+agent.getID()+" hiding from "+dangerous_predators.numObjs+" dangerous predators"); 
		}
	}
	

	/* (non-Javadoc)
	 * @see multiagent.model.subsumption.Behavior#isActive()
	 */
	
	@Override
	public boolean isActive() {
		
		// Ya no es necesario evaluar si se est‡ oculto. De hecho este comportamiento debe garantizar que 
		// mientras haya depredadores peligrosos cercanos el organismo se mantenga dentro del refugio.
		//	if (agent.isHidden()) return false;
		
		// Si el organismo no tiene escondite asignado o si ya est‡ oculto se desactiva este comportamiento
		if (agent.getRefuge()!=null && !agent.isHidden()){
			// Se obtienen los objetos dentro del radio de percepci—n considerando su tama–o
			Bag objects = BioMASSGUIFrame.getInstance().seaSpace.getDimensionObjectsAtRadio(agent.getID(), agent.fg.perceptionRangeFactor*agent.length);
			// Si la lista de objetos no est‡ vac’a
			if (!objects.isEmpty()) {
				// Verifica si en la lista de objetos cercanos hay depredadores y los agrega a otra lista
				// Se obtiene la lista de depredadores para este grupo funcional
				Bag predators = new Bag();
				for(int i=0;i<objects.numObjs;i++) 
					if(objects.objs[i].getClass().getCanonicalName().equalsIgnoreCase(predatorName))
						predators.add(objects.objs[i]);
				// Si la lista de depredadores no est‡ vac’a
				if(!predators.isEmpty()){
					dangerous_predators = getDangerousPredators(predators);
					if (!dangerous_predators.isEmpty()) 
						return true;
				}
			}
		}
		return false;			
	}
	
	
	
	
	
	//Evalœa el miedo a cada uno de los depredadores detectados
	//identifica el m‡ximo valor del miedo, el cual determinar‡ la velocidad de escape.
	Bag getDangerousPredators(Bag predators){
		double rel_weight; // Relaci—n de masas entre depredador y presa
		Heterotroph predator;

		// Se limpia la lista de depredadores peligrosos
		dangerous_predators.clear();
		// Se evalua uno a uno la peligrosidad de los depredadores
		for(int i=0;i<predators.numObjs;i++) {
			predator= (Heterotroph) predators.objs[i];
			// Se calcula la relaci—n de masas
			rel_weight = predator.getBiomass()/agent.getBiomass();
			// Solo nos preocupan aquellos depredadores 2 o mas veces mas grandes
			if(rel_weight>=2.0) {
				// Se agrega el depredador a la lista de depredadores peligrosos
				dangerous_predators.add(predator);
			}
		}
		return dangerous_predators;
	}


	
	double calculateEncounterTime(Organism predator, double rel_dist)
	{
		double speed_delta = predator.getVelocity().getMagnitude()-hideSpeed;
		if(speed_delta >0.0 )
			return rel_dist/(speed_delta);
		else
			return Double.MAX_VALUE;
//		return rel_dist/(hideSpeed*(rel_weight-1));
	}

	
	
	public Velocity calculateHideVelocity()
	{
		Organism predator;
		Velocity vector_from_pred;
		Velocity hideVector;
		double time; // Tiempo de llegada de cada depredador
		double rel_dist; //Distancia a la que se encuentra cada depredador
		double min_time_from_preds=Double.MAX_VALUE;
	
		// Se calcula la distancia de la presa al refugio
		double ref_rel_dist = BioMASSGUIFrame.getInstance().seaSpace.getDistance(agent.getID(), agent.getRefuge().getID())/agent.length;

		// Se inicializa el vector de escape resultante
		double vector_from_pred_norm_x = 0, vector_from_pred_norm_y = 0;
		
		for(int i=0;i<dangerous_predators.numObjs;i++) {
			// Se obtiene a la referencia a cada uno de los depredadores peligrosos
			predator = (Heterotroph) dangerous_predators.objs[i];
			// Se calcula la distancia de la presa al depredador
			rel_dist = BioMASSGUIFrame.getInstance().seaSpace.getTouchDistance(agent.getID(), predator.getID())/agent.length;
			// Se calcula el tiempo de llegada
			time = calculateEncounterTime(predator, rel_dist);
			// Se decide si es o no el m’nimo tiempo
			if(time<min_time_from_preds)
				min_time_from_preds=time;

			// Se obtiene el vector que apunta en direcci—n al depredador
			vector_from_pred=BioMASSGUIFrame.getInstance().seaSpace.getDistanceVector(predator.getID(), agent.getID());
			// Se normaliza
			vector_from_pred.Normalize();
			// Se dividen los componentes XY por la magnitud del tiempo de llegada  y se acumulan a un vector resultante
			vector_from_pred_norm_x += vector_from_pred.getCx() / time;
			vector_from_pred_norm_y += vector_from_pred.getCy() / time;
		}
		
		
		// Se comparan el tiempo de llegada al refugio con el m’nimo tiempo de llegada de uno de los depredadores
		if( ref_rel_dist/hideSpeed <= min_time_from_preds) {
			// Conviene dirigirse al refugio
			// Vector normalizado que apunta hacia el refugio con origen en la posici—n actual de la presa
			hideVector=BioMASSGUIFrame.getInstance().seaSpace.getDistanceVector(agent.id, agent.getRefuge().id);
			hideVector.Normalize();
			if(ref_rel_dist < hideSpeed)
				// Se ajusta la velocidad de escape para llegar justo al centro del refugio
				hideVector.setMagnitude(ref_rel_dist*agent.length*BioMASSGUIFrame.getInstance().getTimeStep());
			else
				// Se ajusta la velocidad estandard de escape
				// Se ajusta la velocidad de escape
				hideVector.setMagnitude(hideSpeed*agent.length*BioMASSGUIFrame.getInstance().getTimeStep());				
		}
		else {
			// Conviene escapar en direcci—n opuesta a los depredadores
			// Se inicializa el vector de escape resultante
		
			// Se normaliza el vector resultante con direcci—n contraria a la de los depredadores
			hideVector= new Velocity(vector_from_pred_norm_x, vector_from_pred_norm_y);
			hideVector.Normalize();
			// Se ajusta la velocidad de escape
			hideVector.setMagnitude(hideSpeed*agent.length*BioMASSGUIFrame.getInstance().getTimeStep());
				
		}
		return hideVector;
	}

}
