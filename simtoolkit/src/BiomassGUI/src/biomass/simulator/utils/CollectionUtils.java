package biomass.simulator.utils;

import java.util.ArrayList;
import java.util.Collection;

public final class CollectionUtils {
		public static <T> T firstOrDefault(Collection<T> list) {
	        if (list.isEmpty()) return null;
	        return list.iterator().next();
	    }

	    public static <T> T firstOrDefault(Collection<T> list, IFunction<T, Boolean> func) throws Throwable {
	        if (list.isEmpty()) return null;
	        for (T o : list)
	            if (func.execute(o))
	                return o;
	        return null;
	    }

	    public static <T, U> Collection<U> select(Collection<T> list, IFunction<T, U> func) throws Throwable {
	        ArrayList<U> newList = new ArrayList<U>();
	        for (T o : list)
	            newList.add(func.execute(o));
	        return newList;
	    }
}
