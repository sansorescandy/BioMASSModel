/**
 * 
 */
package biomass.simulator.utils;

import java.sql.*;
import java.util.*;
import org.apache.commons.lang3.StringUtils;

/**
 * @author candysansores
 *
 */
public class SqlUtils {
	String driver;
    String url;
	/**
	 * 
	 */
	public SqlUtils(String driver, String url){
        this.driver = driver;
        this.url = url;
    }

    public <T> ArrayList<T> executeList(String sql, IFunction<ResultSet, T> mapper) throws ClassNotFoundException, SQLException, Throwable {
        return executeList(sql, new ArrayList<Object>(), mapper);
    }

    public <T> ArrayList<T> executeList(String sql, ArrayList<Object> parameters, IFunction<ResultSet, T> mapper) throws ClassNotFoundException, SQLException, Throwable {
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url);
        PreparedStatement stat = conn.prepareStatement(sql);
        for (int i = 0; i < parameters.size(); ++i)
            stat.setObject(i + 1, parameters.get(i));
        ResultSet rs = stat.executeQuery();
        ArrayList<T> results = new ArrayList<T>();
        while (rs.next()) results.add(mapper.execute(rs));
        rs.close();
        conn.close();
        return results;
    }

    public String toFieldParam(String field) {
        return field + "=?";
    }

    public Collection<String> toFieldsParams(Collection<String> fields) throws Throwable {
        return CollectionUtils.select(fields, new IFunction<String, String>() {
            public String execute(String key) throws Throwable {
                return toFieldParam(key);
            }
        });
    }

    public int update(String table, HashMap<String, Object> values, String identity) throws Throwable {
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url);
        Collection<String> keys = values.keySet();
        Object idValue = values.get(identity);
        keys.remove(identity);
        String sql = "update " + table + " set " + StringUtils.join(toFieldsParams(keys), ",") + " where " + identity + "=?";
        PreparedStatement stat = conn.prepareStatement(sql);
        Object[] vs = values.values().toArray();
        int i;
        for (i = 0; i < vs.length; ++i)
            stat.setObject(i + 1, vs[i]);
        stat.setObject(i + 1, idValue);
        int retVal = stat.executeUpdate();
        conn.close();
        return retVal;
    }

    public Object insert(String table, HashMap<String, Object> values) throws Throwable {
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url);
        Collection<String> keys = values.keySet();
        ArrayList<String> params = new ArrayList<String>();
        int i;
        for (i = 0; i < keys.size(); ++i) params.add("?");
        String sql = "insert into " + table + " (" + StringUtils.join(keys.toArray(), ",") + ") values (" + StringUtils.join(params, ",") + ")";
        PreparedStatement stat = conn.prepareStatement(sql);
        Object[] vs = values.values().toArray();
        for (i = 0; i < vs.length; ++i)
            stat.setObject(i + 1, vs[i]);
        stat.executeUpdate();
        Object retVal = null;
        ResultSet rs = conn.createStatement().executeQuery("select last_insert_rowid() as last_insert_rowid");
        while (rs.next())
            retVal = rs.getObject(1);
        rs.close();
        conn.close();
        return retVal;
    }

    public int delete(String table, Object value, String identity) throws Throwable {
        HashMap<String, Object> identities = new HashMap<String, Object>();
        identities.put(identity, value);
        return delete(table, identities);
    }
    
    public int delete(String table, HashMap<String, Object> identities) throws Throwable {
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url);
        String sql = "delete from " + table + " where " + StringUtils.join(toFieldsParams(identities.keySet()), " and ");
        PreparedStatement stat = conn.prepareStatement(sql);
        Object[] vs = identities.values().toArray();
        for (int i = 0; i < vs.length; ++i)
            stat.setObject(i + 1, vs[i]);
        int retVal = stat.executeUpdate();
        conn.close();
        return retVal;
    }

}
