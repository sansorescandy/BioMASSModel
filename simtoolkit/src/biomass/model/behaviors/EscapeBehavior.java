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
 * Bajo este comportamiento el organismo huye a m�xima velocidad alej�ndose de las amenazas.
 * Para ello calcula un vector de escape tomando en cuenta la cantidad, la distancia y el tama�o de
 * los posibles depredadores.
 * Se activa si hay depredadores y no hay refugio en la memoria.
 */

public class EscapeBehavior extends Behavior {
	private Heterotroph agent;
	
	
	private Bag dangerous_predators;
	private double escapeSpeed; 
	private String predatorName;
	private Color escapingColor;

	/**
	 * @param agent
	 */
	public EscapeBehavior(Heterotroph agent) {
		this.agent=agent;
		escapeSpeed = agent.fg.maxSpeed;
		predatorName=agent.fg.predators[0];
		// Se crea una lista nueva de depredadores peligrosos
		dangerous_predators = new Bag();
	    escapingColor=new Color(255,0,255);  // Color magenta
	}

	/* (non-Javadoc)
	 * @see multiagent.model.subsumption.Behavior#act()
	 */
	@Override
	public void act() {
		if(agent.shout)
			System.out.println("Agent:"+agent.getID()+" escaping from "+dangerous_predators.numObjs+" dangerous predators"); 
	    agent.drawablecolor=escapingColor;
		agent.getVelocity().Assign(calculateEscapeVelocity());
		
		//La distancia que puede recorrer el pez en 1 timestep (lenghts/s*cm*s=cm) regulado por el foco 
		agent.getVelocity().setMagnitude(escapeSpeed*agent.length*BioMASSGUIFrame.getInstance().getTimeStep());
		
		BioMASSGUIFrame.getInstance().seaSpace.move(agent.getID());
		agent.shout=false;
	}
	

	/* (non-Javadoc)
	 * @see multiagent.model.subsumption.Behavior#isActive()
	 */

	@Override
	public boolean isActive() {		
		
		// Si el organismo SI tiene escondite asignado se desactiva este comportamiento
		if (agent.getRefuge()==null){
			// Se obtienen los objetos dentro del radio de percepci�n
			Bag objects = BioMASSGUIFrame.getInstance().seaSpace.getDimensionObjectsAtRadio(agent.getID(), agent.fg.perceptionRangeFactor*agent.length);
			// Si la lista de objetos no est� vac�a
			if (!objects.isEmpty()) {
				// Verifica si en la lista de objetos cercanos hay depredadores y los agrega a otra lista
				// Se obtiene la lista de depredadores para este grupo funcional
				Bag predators = new Bag();
				for(int i=0;i<objects.numObjs;i++) 
					if(objects.objs[i].getClass().getCanonicalName().equalsIgnoreCase(predatorName))
						predators.add(objects.objs[i]);
				// Si la lista de depredadores no est� vac�a
				if(!predators.isEmpty()){
					dangerous_predators = getDangerousPredators(predators);
					if (!dangerous_predators.isEmpty()) 
						return true;
				}
			}
		}
		return false;			
	}
	
	
	//Eval�a el miedo a cada uno de los depredadores detectados
	//identifica el m�ximo valor del miedo, el cual determinar� la velocidad de escape.
	Bag getDangerousPredators(Bag predators){
		double rel_weight; // Relaci�n de masas entre depredador y presa
		Heterotroph predator;

		// Se limpia la lista de depredadores peligrosos
		dangerous_predators.clear();
		// Se evalua uno a uno la peligrosidad de los depredadores
		for(int i=0;i<predators.numObjs;i++) {
			predator= (Heterotroph) predators.objs[i];
			// Se calcula la relaci�n de masas
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
		//CORREGIR ERROR: multilicar el escapeSpeed por su propia longitud y timestep
		double speed_delta = predator.getVelocity().getMagnitude()-escapeSpeed;
		if(speed_delta >0.0 )
			return rel_dist/(speed_delta);
		else
			return Double.MAX_VALUE;
	}

	
	//El vector de velocidad de escape depende del miedo y de la posici�n de cada depredador
	public Velocity calculateEscapeVelocity()
	{
		Velocity vector_from_pred;
		double time; // Tiempo de llegada de cada depredador
		double rel_dist; //Distancia a la que se encuentra cada depredador
		Heterotroph predator;

		// Determina la velocidad de escape considerando la posici�n de los depredadores que ha detectado
		// Se inicializa el vector de escape resultante
		double vector_from_pred_norm_x = 0, vector_from_pred_norm_y = 0;
		
		for(int i=0;i<dangerous_predators.numObjs;i++) {
			// Se obtiene a la referencia a cada uno de los depredadores peligrosos
			predator = (Heterotroph) dangerous_predators.objs[i];
			// Se calcula la distancia de la presa al depredador CORREGIR: A DISTANCIA ABSOLUTA
			rel_dist = BioMASSGUIFrame.getInstance().seaSpace.getTouchDistance(agent.getID(), predator.getID())/agent.length;
			// Se calcula el tiempo de llegada
			time = calculateEncounterTime(predator, rel_dist);

			// Se obtiene el vector que apunta en direcci�n al depredador
			vector_from_pred=BioMASSGUIFrame.getInstance().seaSpace.getDistanceVector(predator.getID(), agent.getID());
			// Se normaliza
			vector_from_pred.Normalize();
			// Se dividen los componentes XY por la magnitud del tiempo de llegada  y se acumulan a un vector resultante
			vector_from_pred_norm_x += vector_from_pred.getCx() / time;
			vector_from_pred_norm_y += vector_from_pred.getCy() / time;
		}
		
		//Normaliza el vector resultante con direcci�n contraria a la de los depredadores
		Velocity vector_from_pred_norm= new Velocity(vector_from_pred_norm_x, vector_from_pred_norm_y);
		vector_from_pred_norm.Normalize();
				
	    return vector_from_pred_norm;
	}
	
	
	
}
