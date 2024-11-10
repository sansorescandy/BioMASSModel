package multiagent.model.environment;

import java.util.ArrayList;

import multiagent.model.utils.Vector2d;

/**
 * @author candysansores
 *
 */
public class Space {

	PhysicalObject space[]=new PhysicalObject[10000];
	int index;
	int numObjs=0;
	double x1, y1, x2, y2;
	boolean torus=true;

	public Space(double x1, double y1, double x2, double y2) {
		this.x1=x1;
		this.y1=y1;
		this.x2=x2;
		this.y2=y2;
		space[0]=new PhysicalObject(x1,y1);
		space[0].nextX=1;
		space[0].nextY=1;
		space[0].prevX=0;
		space[0].prevY=0;
		space[1]=new PhysicalObject(x2,y2);
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
		moveToI(id, findXIdxUp(0,space[id].x));
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
		moveToJ(id, findYIdxUp(0,space[id].y));
	}


	public synchronized int insert(PhysicalObject o) {
		int id;

		id=index++;
		o.x=borderX(o.x);
		o.y=borderY(o.y);
		o.id=id;
		space[id]=o;
		insertX(id);
		insertY(id);
		numObjs++;
		return id;
	}

	public void setRadio(int id, double radio) {
		space[id].radio=radio;		
	}

	public synchronized int insert(int refid, PhysicalObject o) {
		int id;

		id=index++;
		o.x=borderX(space[refid].x);
		o.y=borderY(space[refid].y);
		o.id=id;
		space[id]=o;
		moveToI(id,refid);
		moveToJ(id,refid);
		numObjs++;
		return id;
	}


	private void moveToI(int id, int refid) {	
		space[id].nextX=space[refid].nextX;
		space[id].prevX=refid;
		space[refid].nextX=id;
		space[space[id].nextX].prevX=id;		
	}

	private void moveToJ(int id, int refid) {
		space[id].nextY=space[refid].nextY;
		space[id].prevY=refid;
		space[refid].nextY=id;
		space[space[id].nextY].prevY=id;
	}

	private double moveToX(int id, double x){
		int i;
		x=toroX(x);
		if(x-space[id].x>0) {
			i=findXIdxUp(id, x);
			space[id].x=x;
			if(i!=id) {
				leaveX(id);
				moveToI(id,i);
			}
		}
		else {
			i=findXIdxDown(space[id].prevX,x);
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
		y=toroY(y);
		if(y-space[id].y>0) {
			j=findYIdxUp(id, y);
			space[id].y=y;
			if(j!=id) {
				leaveY(id);
				moveToJ(id,j);
			}
		}
		else {
			j=findYIdxDown(space[id].prevY,y);
			space[id].y=y;
			if(j!=space[id].prevY) {
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

	public synchronized void move(int id, double x, double y) {
		moveToX(id, x);
		moveToY(id, y);
	}

	public synchronized void moveByDisplacement(int id, double x, double y) {
		moveToX(id, space[id].x+x);
		moveToY(id, space[id].y+y);
	}


	public synchronized ArrayList<Object> getObjectsAtRadio(int id, double radio) {
		ArrayList<Object> b=new ArrayList<Object>();
		int i;
		i=space[id].nextX;
		while(i!=1 && space[i].x-space[id].x<=radio) {
			if(getDistance(id,i)<=radio)
				b.add(space[i]);
			i=space[i].nextX;
		}
		i=space[id].prevX;
		while(i!=0 && space[id].x-space[i].x<=radio) {
			if(getDistance(id,i)<=radio)
				b.add(space[i]);
			i=space[i].prevX;
		}
		return b;
	}


	public synchronized ArrayList<Object> getObjectsAtRadioTorus(int id, double radio) {
		ArrayList<Object> b=new ArrayList<Object>();
		int i;
		i=space[id].nextX;
		while(i!=1 && space[i].x-space[id].x<=radio) {
			if(getSqDistanceTorus(i,id)<=radio*radio)
				b.add(space[i]);
			i=space[i].nextX;
		}
		if(i==1){
			i=space[0].nextX;
			while(getSqDistanceTorus(i,id)<=radio*radio)  {
				b.add(space[i]);
				i=space[i].nextX;
			}
		}

		i=space[id].prevX;
		while(i!=0 && space[id].x-space[i].x<=radio) {
			if(getSqDistanceTorus(i,id)<=radio*radio)
				b.add(space[i]);
			i=space[i].prevX;
		}
		if(i==0){
			i=space[1].prevX;
			while(getSqDistanceTorus(i,id)<=radio*radio)  {
				b.add(space[i]);
				i=space[i].prevX;
			}
		}
		return b;
	}

	public synchronized ArrayList <PhysicalObject> getDimensionObjectsAtRadio(int id, double radio) {
		ArrayList<PhysicalObject> b=new ArrayList<PhysicalObject>();
		int i;
		i=space[id].nextX;
		while(i!=1 && space[i].x-space[id].x<=radio) {
			if(getTouchDistance(id,i)<=radio)
				b.add(space[i]);
			i=space[i].nextX;
		}
		i=space[id].prevX;
		while(i!=0 && space[id].x-space[i].x<=radio) {
			if(getTouchDistance(id,i)<=radio)
				b.add(space[i]);
			i=space[i].prevX;
		}
		return b;
	}

	public synchronized void remove(int id){
		if(id!=0 && id!=1) {
			leaveXY(id);
			space[id]=null;
			numObjs--;
		}	
	}

	public synchronized ArrayList<PhysicalObject> getAllObjects() {
		ArrayList<PhysicalObject> b=new ArrayList<PhysicalObject>();
		int id=space[0].nextX;
		while(id!=1) {
			b.add(space[id]);
			id=space[id].nextX;
		}
		return b;		
	}

	public double getDistance(int id1, int id2) {
		return Math.sqrt(Math.pow((space[id1].x-space[id2].x),2)+Math.pow((space[id1].y-space[id2].y),2));
	}

	public double getSqDistance(int id1, int id2) {
		double diffx=space[id1].x-space[id2].x;
		double diffy=space[id1].y-space[id2].y;
		if(torus) {
			double absDiffx = Math.abs(diffx);
			if (absDiffx > x2 / 2){
				diffx = x2 - absDiffx;
			}
			double absDiffy = Math.abs(diffy);
			if (absDiffy > y2 / 2){
				diffy = y2 - absDiffy;
			}
		}

		return diffx*diffx+diffy*diffy;
	}

	public double getSqDistanceTorus(int id1, int id2) {
		double diffx=space[id1].x-space[id2].x;
		double diffy=space[id1].y-space[id2].y;
		double absDiffx = Math.abs(diffx);
		if (absDiffx > x2 / 2){
			diffx = x2 - absDiffx;
		}
		double absDiffy = Math.abs(diffy);
		if (absDiffy > y2 / 2){
			diffy = y2 - absDiffy;
		}
		return diffx*diffx+diffy*diffy;
	}

	public Vector2d getVector2dTorus(int id1, int id2) {
		double diffx=space[id1].x-space[id2].x;
		double absDiffx = Math.abs(diffx);
		if (absDiffx > x2 / 2){
			if(diffx>0)
				diffx = (x2 - absDiffx)*-1;
			else
				diffx = (x2- absDiffx);
		}
		double diffy=space[id1].y-space[id2].y;
		double absDiffy = Math.abs(diffy);
		if (absDiffy > y2 / 2){
			if(diffy>0)
				diffy = (y2 - absDiffy)*-1;
			else
				diffy = (y2- absDiffy);
		}
		return new Vector2d(diffx, diffy);

	}

	public double getTouchDistance(int id1, int id2) {
		double dist=getDistance(id1, id2)-space[id1].radio-space[id2].radio;
		if(dist<0)
			return 0;
		else
			return dist;
	}

	public double getInternalDistance(int id1, int id2) {
		double dist=getDistance(id1, id2)+space[id1].radio-space[id2].radio;
		if(dist<0)
			return 0;
		else
			return dist;
	}

	public Vector2d getDistanceVector(int id1, int id2) {
		return new Vector2d(space[id2].x-space[id1].x, space[id2].y-space[id1].y);
	}

	public Vector2d getTouchDistanceVector(int id1, int id2) {
		Vector2d v=this.getDistanceVector(id1, id2);
		double magnitude=v.magnitude()-space[id1].radio-space[id2].radio;
		if(magnitude>0) {
			v.normalize();
			v.scale(magnitude);
		}
		else
			v.set(0.0, 0.0);			
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

	private final double toroX(double x) {    
		final double width = x2;
		if (x >= x1 && x < width) return x;
		x = x % width;
		if (x < x1) x = x + width;
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

	private final double toroY(double y) { 
		final double height = y2;
		if (y >= y1 && y < height) return y;
		y = y % height;
		if (y < y1) y = y + height;
		return y;
	}

	public double getX(int id) {
		return space[id].x;
	}

	public double getY(int id) {
		return space[id].y;
	}

	public double getWidth() {
		return x2-x1;
	}

	public double getHeight() {
		return y2-y1;
	}
}
