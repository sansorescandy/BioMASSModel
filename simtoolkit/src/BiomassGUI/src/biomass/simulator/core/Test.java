/**
 * 
 */
package biomass.simulator.core;

import java.util.ArrayList;
import java.util.Stack;


/**
 * @author candysansores
 *
 */
public class Test {
	/**
	 * 
	 */
	public Test() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Stack s, s2;
		ArrayList a;
		Bag b;
		Object o;
		int n=100000;
		Object[] arrayo = new Object[100000];
		 
		
		
		s=new Stack();
		s2=new Stack();
		a=new ArrayList();
		b=new Bag();
		long t,t1,t2;
		
		t1=System.currentTimeMillis();
		for(int i=0;i<n;i++)
			s.push(new Object());
		//s2=new Stack();
		//s2.addAll(s);
		while(!s.isEmpty())
			o=s.pop();
		//while(!s2.isEmpty())
			//o=s2.pop();
		t2=System.currentTimeMillis();
		t=t2-t1;
		System.out.println("TIEMPO STACK: " + t);
		
		
		t1=System.currentTimeMillis();
		
		for(int i=0;i<n;i++)
			a.add(new Object());
		for(int i=0;i<a.size();i++)
			o=a.get(i);
		//for(int i=0;i<a.size();i++)
			//o=a.get(i);
		
		t2=System.currentTimeMillis();
		t=t2-t1;
		System.out.println("TIEMPO ARRAYLIST: " + t);
		
		
		t1=System.currentTimeMillis();
		
		for(int i=0;i<n;i++)
			b.add(new Object());
		for(int i=0;i<b.numObjs;i++)
			o=b.objs[i];
		//for(int i=0;i<b.numObjs;i++)
			//o=b.objs[i];
		
		t2=System.currentTimeMillis();
		t=t2-t1;
		System.out.println("TIEMPO BAG: " + t);
		
		t1=System.currentTimeMillis();
		
		for(int i=0;i<n;i++)
			arrayo[i]=new Object();
		for(int i=0;i<arrayo.length;i++)
			o=arrayo[i];
		//for(int i=0;i<b.numObjs;i++)
			//o=b.objs[i];
		
		t2=System.currentTimeMillis();
		t=t2-t1;
		System.out.println("TIEMPO Array: " + t);
	}

}
