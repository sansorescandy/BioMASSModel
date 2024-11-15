	package biomass.model.utils;


public class FuzzyFunctions {
//Functions to use
	 public FuzzyFunctions(){
	}
	
	//alfa es el valor en el que inicia la S, gamma el valor en el que termina
	//la funcion S se incrementa gradualmente de cero a 1 entre los valores alfa y gamma
	 public static double S(double x, double alfa, double gamma){
		double beta = (alfa+gamma)/2.0;
		if(x <= alfa)
	        return 0;
	    if(x <= beta)
	        return 2*(x-alfa)*(x-alfa)/((gamma-alfa)*(gamma-alfa));
	    if(x <= gamma)
	        return 1-2*(x-gamma)*(x-gamma)/((gamma-alfa)*(gamma-alfa));
	    else
	    	return 1;
	}	
	
	//alfa es el valor en el que inicia la S invertida, gamma el valor en el que termina
	//la funcion S invertida se decrementa gradualmente de 1 a cero entre los valores alfa y gamma
	 public static double SInv(double x,double alfa, double gamma){
	    double beta = (alfa+gamma)/2.0;

	    if(x <= alfa)
	        return 1;
	    if(x <= beta)
	        return 1-2*(x-alfa)*(x-alfa)/((gamma-alfa)*(gamma-alfa));
	    if(x <= gamma)
	        return 2*(gamma-x)*(gamma-x)/((gamma-alfa)*(gamma-alfa));
	    else
	        return 0;
	}
	
	//gamma es el valor en el que est‡ centrada la funci—n Pi.
	//halfwidth es la distancia desde el centro (gamma) hasta el extremo de la Pi.
	//La funci—n se construye mediante una SInv y una S
	 public static double Pi(double x, double x0, double halfwidth)
	{
	    if(x < x0-halfwidth || x > x0+halfwidth)
	        return 0;
	    else
	    {
	        if(x < x0)
	            return S(x,x0-halfwidth,x0);
	        else
	            return SInv(x,x0,x0+halfwidth);
	    }
	}
	
	 
	 
	 public static double Pi(double x, double x0, double w0, double w1)
	{
	    if(x < x0-w0 || x > x0+w1)
	        return 0;
	    else
	    {
	        if(x < x0)
	            return S(x,x0-w0,x0);
	        else
	            return SInv(x,x0,x0+w1);
	    }
	}
	
	 public static double ldown(double x, double x0, double xf)
	{
	    if(x < x0)
	        return 1;
	    else
	    {
	        if(x > xf)
	            return 0;
	        else
	        {
	            return (xf-x)/(xf-x0);
	        }
	    }
	}
	
	 public static double lup(double x, double x0, double xf)
	{
	    if(x < x0)
	        return 0;
	    else
	    {
	        if(x > xf)
	            return 1;
	        else
	        {
	            return (x-x0)/(xf-x0);
	        }
	    }
	}
	
	 public static double Triangle(double x, double x0, double xc, double xf)
	{
	    if(x < x0 || x > xf)
	        return 0;
	    else
	    {
	        if(x < xc)
	            return lup(x,x0,xc);
	        else
	            return ldown(x,xc,xf);
	    }
	}
	
	 public static double Min(double x, double y)
	{
	    if(x < y)
	        return x;
	    else
	        return y;
	}

	 public static double Max(double x, double y)
	{
	    if(x < y)
	        return y;
	    else
	        return x;
	}
	
}

