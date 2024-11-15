package biomass.simulator.core;


import java.util.Stack;

import multiagent.model.agent.PhysicalObject;

import biomass.ibmmodel.utils.Velocity;

public class Space2 {
	
	MovingParticle space[]=new MovingParticle[10000];
	int index;
	double centX=0;
	double centY=0;
	int numObjs=0;
	double x1, y1, x2, y2;
	Stack<Integer> pool=new Stack<Integer>();
	
	public Space2(double x1, double y1, double x2, double y2) {
		this.x1=x1;
		this.y1=y1;
		this.x2=x2;
		this.y2=y2;
		space[0]=new MovingParticle(x1, y1, null);
		space[0].nextX=1;
		space[0].nextY=1;
		space[0].prevX=0;
		space[0].prevY=0;
		space[1]=new MovingParticle(x2, y2, null);
		space[1].nextX=1;
		space[1].nextY=1;
		space[1].prevX=0;
		space[1].prevY=0;
		index=2;
	}
	
	private int findXIdxUp(int i, double x) {
		while(space[space[i].nextX].x<x) {
			i=space[i].nextX;
		}
		return i;
	}
	
	private int findXIdxDown(int i, double x) {
		while(space[i].x>x) {
			i=space[i].prevX;
		}
		return i;
	}
	
	private void insertX(int id) {
		if(space[id].x<centX) {//Por abajo
			moveToI(id, findXIdxUp(0,space[id].x));
			//System.out.println("Por abajo. ID="+id+" CentX="+centX+" X="+space[id].x);
		}
		else {//Por arriba
			moveToI(id, findXIdxDown(space[1].prevX,space[id].x));
			//System.out.println("Por arriba. ID="+id+" CentX="+centX+" X="+space[id].x);
		}
	}
	
	private int findYIdxUp(int i, double y) {
		while(space[space[i].nextY].y<y) {
			i=space[i].nextY;
		}
		return i;
	}
	
	private int findYIdxDown(int i, double y) {
		while(space[i].y>y) {
			i=space[i].prevY;
		}
		return i;
	}
	
	private void insertY(int id) {
		if(space[id].y<centY) {//Por abajo
			moveToJ(id, findYIdxUp(0,space[id].y));
			//System.out.println("Por abajo. ID="+id+" CentY="+centY+" Y="+space[id].y);
		}
		else {//Por arriba
			moveToJ(id, findYIdxDown(space[1].prevY,space[id].y));
			//System.out.println("Por arriba. ID="+id+" CentY="+centY+" Y="+space[id].y);
		}
	}
	
	public int insert(double x, double y, Object o) {
		int id;
		
		if(pool.empty()) {
			id=index++;
			space[id]=new MovingParticle(borderX(x), borderY(y), o);
		}
		else {
			id=((Integer)pool.pop()).intValue();
			space[id].reset(borderX(x), borderY(y), o);
		}
		
		insertX(id);
		insertY(id);
		centX=(centX*numObjs+space[id].x)/(numObjs+1);
		centY=(centY*numObjs+space[id].y)/(numObjs+1);
		numObjs++;
		return id;
	}
	
	public void setRadio(int id, double radio) {
		space[id].radio=radio;		
	}
	
	//Para insertar objetos en la misma posici—n de otro objeto existente
	public int insert(int refid, Object o) {
		int id;
		
		if(pool.empty()) {
			id=index++;
			space[id]=new MovingParticle(borderX(space[refid].x), borderY(space[refid].y), o);
		}
		else {
			id=((Integer)pool.pop()).intValue();
			space[id].reset(borderX(space[refid].x), borderY(space[refid].y), o);
		}
		
		moveToI(id,refid);
		moveToJ(id,refid);
		centX=(centX*numObjs+space[id].x)/(numObjs+1);
		centY=(centY*numObjs+space[id].y)/(numObjs+1);
		numObjs++;
		return id;
	}
	
	
	
	private void moveToI(int id, int refid) {	
		space[id].nextX=space[refid].nextX; //Mi siguiente
		space[id].prevX=refid; //Mi previo es i
		space[refid].nextX=id; //Yo soy el siguiente de i
		space[space[id].nextX].prevX=id; //Yo soy el previo del siguiente		
		//System.out.println("Index: "+ indx +" i="+ i + " X= " + space[indx].x + " PrevX: " + space[indx].prevX + " " +  space[space[indx].prevX].x + " NextX: " +space[indx].nextX + " " + space[space[indx].nextX].x);
	}
	
	private void moveToJ(int id, int refid) {
		space[id].nextY=space[refid].nextY; //Mi siguiente
		space[id].prevY=refid; //Mi previo es i
		space[refid].nextY=id; //Yo soy el siguiente de i
		space[space[id].nextY].prevY=id; //Yo soy el previo del siguiente	
	}
	
	private double moveToX(int id, double x){
		int i;
		x=borderX(x);
		
		if(x-space[id].x>0) {
			//Se mueve hacia arriba
			i=findXIdxUp(id, x);
			centX=centX+(x-space[id].x)/numObjs;
			space[id].x=x;
			if(i!=id) {
				leaveX(id);
				moveToI(id,i);
			}
		}
		else {
			//Se mueve hacia abajo
			i=findXIdxDown(space[id].prevX,x);
			centX=centX+(x-space[id].x)/numObjs;
			space[id].x=x;
			if(i!=space[id].prevX) {
				leaveX(id);
				moveToI(id,i);
			}
		}
		return x;
	}
	
	private double moveToY(int id, double y){
		int j;
		y=borderY(y);
		
		if(y-space[id].y>0) {
			//Se mueve hacia arriba
			j=findYIdxUp(id, y);
			centY=centY+(y-space[id].y)/numObjs;
			space[id].y=y;
			if(j!=id) { //La nueva X es siempre mayor a la actual pero puede ser menor a la siguiente y regresar el actual id
				leaveY(id);
				moveToJ(id,j);
			}
		}
		else {
			//Se mueve hacia abajo
			j=findYIdxDown(space[id].prevY,y);
			centY=centY+(y-space[id].y)/numObjs;
			space[id].y=y;
			if(j!=space[id].prevY) { //La nueva X es siempre menor a la actual pero puede ser mayor a la previa y regresar el id previo
				leaveY(id);
				moveToJ(id,j);
			}
		}
		return y;
	}
	
	
	private void leaveX(int indx) {
		space[space[indx].prevX].nextX=space[indx].nextX;
		space[space[indx].nextX].prevX=space[indx].prevX;
	}
	
	private void leaveY(int indx) {
		space[space[indx].prevY].nextY=space[indx].nextY;
		space[space[indx].nextY].prevY=space[indx].prevY;
	}
	
	private void leaveXY(int id) {
		leaveX(id);
		leaveY(id);
	}
	
	public void move(int id, double x, double y) {
		moveToX(id, x);
		moveToY(id, y);
	}
	
	public void move(int id) {
		double x=space[id].x+space[id].velocity.getCx();
		if(moveToX(id,x)!=x)
			space[id].velocity.bounceX();
		double y=space[id].y+space[id].velocity.getCy();
		if(moveToY(id,y)!=y)
			space[id].velocity.bounceY();
	}
	
	
	public Bag getObjectsAtRadio(int id, double radio) {
		Bag b=new Bag();
		int i;
		i=space[id].nextX; //Busca hacia arriba
		while(i!=1 && space[i].x-space[id].x<=radio) {
			if(getDistance(id,i)<=radio)
				b.add(space[i].o);
			i=space[i].nextX;
		}
		i=space[id].prevX; //Busca hacia abajo
		while(i!=0 && space[id].x-space[i].x<=radio) {
			if(getDistance(id,i)<=radio)
				b.add(space[i].o);
			i=space[i].prevX;
		}
		return b;
	}
	
	//Obtiene los objetos a una distancia de "radio" entre las periferias de los objetos
	public Bag getDimensionObjectsAtRadio(int id, double radio) {
		Bag b=new Bag();
		int i;
		i=space[id].nextX; //Busca hacia arriba
		while(i!=1 && space[i].x-space[id].x<=radio) {
			if(getDistance(id,i)-space[id].radio-space[i].radio<=radio)
				b.add(space[i].o);
			i=space[i].nextX;
		}
		i=space[id].prevX; //Busca hacia abajo
		while(i!=0 && space[id].x-space[i].x<=radio) {
			if(getDistance(id,i)-space[id].radio-space[i].radio<=radio)
				b.add(space[i].o);
			i=space[i].prevX;
		}
		return b;
	}
	
	public void remove(int id){
		if(id!=0 && id!=1) {
			
			/*System.out.println("Eliminar ID= "+id);
			System.out.println("Previo de ID = "+space[id].prevX+","+space[id].prevY);
			System.out.println("Siguiente de ID = "+space[id].nextX+","+space[id].nextY);*/
			
			leaveXY(id);
			pool.push(new Integer(id));
			centX=(centX*numObjs-space[id].x)/(numObjs-1);
			centY=(centY*numObjs-space[id].y)/(numObjs-1);
			numObjs--;
			/*
			System.out.println("Previos 0 = "+space[0].prevX+","+space[0].prevY);
			System.out.println("Siguientes 0 = "+space[0].nextX+","+space[0].nextY);
			System.out.println("Previos 1 = "+space[1].prevX+","+space[1].prevY);
			System.out.println("Siguientes 1 = "+space[1].nextX+","+space[1].nextY);
			System.out.println();*/
		}	
	}
	
	public Bag getAllObjects() {
		Bag b=new Bag();
		int id=space[0].nextX;
		while(id!=1) {
			b.add(space[id].o);
			id=space[id].nextX;
		}
		return b;
		
	}
	
	public double getDistance(int id1, int id2) {
		return Math.sqrt(Math.pow((space[id1].x-space[id2].x),2)+Math.pow((space[id1].y-space[id2].y),2));
	}
	
	public Velocity getDistanceVector(int id1, int id2) {
		return new Velocity(space[id1].x-space[id2].x, space[id1].y-space[id2].y);
	}
	
	public Velocity getDimensionDistanceVector(int id1, int id2) {
		Velocity v=this.getDistanceVector(id1, id2);
		v.setMagnitude(v.getMagnitude()-space[id1].radio-space[id2].radio);
		return v;
	}
	
    private final double borderX(double x) {    
        if(x<x1) 
    		return x1;
    	else
    		if(x>x2)
    			return x2;
        return x;
        }
    

    private final double borderY(double y) { 
    	if(y<y1) 
    		return y1;
    	else
    		if(y>y2)
    			return y2;
        return y;
    }

   
    public double getX(int id) {
    	return space[id].x;
    }
    
    public double getY(int id) {
    	return space[id].y;
    }
    
    public Velocity getVelocity(int id) {
    	return space[id].velocity;
    }
    
    public double getWidth() {
    	return x2-x1;
    }
    
    public double getHeight() {
    	return y2-y1;
    }
    
    private void printX() {
		int indx=0;
		while(space[indx].nextX!=1) {
			indx=space[indx].nextX;
			System.out.println("Index: "+ indx + " X= " + space[indx].x);
		}
	}

    private void printY() {
		int indx=0;
		while(space[indx].nextY!=1) {
			indx=space[indx].nextY;
			System.out.println("Index: "+ indx + " Y= " + space[indx].y);
		}
	}

  
	public static void main(String[] args) {
		/*Space s=new Space(-100,-100,100,100);
		Random r=new Random();
		for(int i=0; i<12;i++) {
			s.insert(r.nextFloat()*100-50, r.nextFloat()*100-50, null);
			//s.insertXY(i*10-50, i*10-50, null);	
		}

		s.printX();
		System.out.println();
		s.printY();
		System.out.println();
		//Stack t=s.getObjectsAtRadio(6, 400);
		int v;
		while(!t.empty()) {
			v=(Integer)(t.pop());
			System.out.println("Vecinos ID="+v+" Distance="+s.getDistance(6, v));
		}
		
		//t=s.getObjectsAtRadio(6, 50);
		
		System.out.println();
		
		while(!t.empty()) {
			v=(Integer)(t.pop());
			System.out.println("Vecinos ID="+v+" Distance="+s.getDistance(6, v));
		}
		System.out.println("Borrar=");
		s.remove(6);
		s.printX();
		System.out.println();
		s.printY();
		s.insert(15, 45, null);
		s.insert(25, 55, null);
		System.out.println();
		s.printX();
		System.out.println();
		s.printY();*/
		/*System.out.println("Move=");
		s.moveXY(6, 15, 45);
		System.out.println();
		s.printX();
		System.out.println();
		s.printY();*/
		//System.out.println();
		//System.out.println("CentX="+s.centX+" "+"CentY="+s.centY);*/
	}

}
