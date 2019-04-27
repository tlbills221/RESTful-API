package edu.uky.cs405g.sample.database;

import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DBEngine {

    private DataSource ds;

    public boolean isInit = false;

    public DBEngine(String host, String database, String login, String password) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            String dbConnectionString = null;


            if(database == null) {

                dbConnectionString ="jdbc:mysql://" + host + "?" +
                        "user=" + login  +"&password=" + password + "&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            } else {

                dbConnectionString ="jdbc:mysql://" + host + "/" + database  + "?" +
                        "user=" + login  +"&password=" + password + "&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            }

            ds = setupDataSource(dbConnectionString);


            isInit = true;


        }

        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static DataSource setupDataSource(String connectURI) {
        //
        // First, we'll create a ConnectionFactory that the
        // pool will use to create Connections.
        // We'll use the DriverManagerConnectionFactory,
        // using the connect string passed in the command line
        // arguments.
        //
        ConnectionFactory connectionFactory = null;
            connectionFactory = new DriverManagerConnectionFactory(connectURI, null);


        //
        // Next we'll create the PoolableConnectionFactory, which wraps
        // the "real" Connections created by the ConnectionFactory with
        // the classes that implement the pooling functionality.
        //
        PoolableConnectionFactory poolableConnectionFactory =
                new PoolableConnectionFactory(connectionFactory, null);

        //
        // Now we'll need a ObjectPool that serves as the
        // actual pool of connections.
        //
        // We'll use a GenericObjectPool instance, although
        // any ObjectPool implementation will suffice.
        //
        ObjectPool<PoolableConnection> connectionPool =
                new GenericObjectPool<>(poolableConnectionFactory);

        // Set the factory's pool property to the owning pool
        poolableConnectionFactory.setPool(connectionPool);

        //
        // Finally, we create the PoolingDriver itself,
        // passing in the object pool we created.
        //
        PoolingDataSource<PoolableConnection> dataSource =
                new PoolingDataSource<>(connectionPool);

        return dataSource;
    }

    public Map<String,String> getLocations() {
        Map<String,String> teamIdMap = new HashMap<>();

        Statement stmt = null;
        try
        {
            Connection conn = ds.getConnection();
            String queryString = null;

            queryString = "SELECT * FROM location";

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(queryString);

            while (rs.next()) {
                String teamId = rs.getString("lid");
                String teamName = rs.getString("address");

                teamIdMap.put(teamId, teamName);
            }

            rs.close();
            stmt.close();
            conn.close();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return teamIdMap;
    }

    //CHANGING THIS INTO GetService -Lee
    public Map<String,String> getService(String id) {
        Map<String,String> serviceMap = new HashMap<>();

        Statement stmt = null;
        try
        {
            Connection conn = ds.getConnection();
            String queryString = null;

            queryString = "SELECT * FROM Service WHERE service_id='" + id + "'";

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(queryString);

            while (rs.next()) {
                /*
                    address
                    department id
                    institution id
                    service id
                 */
                String address = rs.getString("address");
                String dept_id = rs.getString("department_id");
                String inst_id = rs.getString("taxid");
                String serviceid = rs.getString("service_id");
                serviceMap.put("address", address);
                serviceMap.put("department_id", dept_id);
                serviceMap.put("taxid", inst_id); //taxid is the institution id
                serviceMap.put("service_id", serviceid);
            }

            rs.close();
            stmt.close();
            conn.close();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return serviceMap;
    }



    public Map<String,String> getProv(String npi) {  //NEW
        Map<String,String> provMap = new HashMap<>();

        Statement stmt = null;
        try
        {
            Connection conn = ds.getConnection();
            String queryString = null;

            queryString = "SELECT * FROM Provider WHERE provider_id='" + npi + "'";

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(queryString);

            while (rs.next()) {
                /*
                    department id
                    npi
                 */
                String dept_id = rs.getString("department_id");
                String prov_id = rs.getString("provider_id");
                provMap.put("department_id", dept_id);
                provMap.put("provider_id", prov_id);
            }

            rs.close();
            stmt.close();
            conn.close();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return provMap;
    }

    public Map<String,String> getPati(String pid) {  //NEW
        Map<String,String> patiMap = new HashMap<>();

        Statement stmt = null;
        try
        {
            Connection conn = ds.getConnection();
            String queryString = null;

            queryString = "SELECT * FROM Patient WHERE pid='" + pid + "'";

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(queryString);

            while (rs.next()) {
                /*
                    address
                    provider id
                    patient id
                    ssn
                 */
                String address = rs.getString("address");
                String npi = rs.getString("provider_id");
                String pat_id = rs.getString("pid");
                String ssn = rs.getString("ssn");
                patiMap.put("address", address);
                patiMap.put("provider_id", npi);
                patiMap.put("pid", pat_id);
                patiMap.put("ssn", ssn);
            }

            rs.close();
            stmt.close();
            conn.close();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return patiMap;
    }

    public Map<String,String> getData(String id) {  //NEW
        Map<String,String> dataMap = new HashMap<>();

        Statement stmt = null;
        try
        {
            Connection conn = ds.getConnection();
            String queryString = null;

            queryString = "SELECT * FROM Data_Sources WHERE id='" + id + "'";

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(queryString);

            while (rs.next()) {
                /*
                    data
                    patient id
                    service id
                    data id
                    timestamp
                 */
                String data = rs.getString("data");
                String pid = rs.getString("patient_id");
                String sid = rs.getString("service_id");
                String dat_id = rs.getString("id");
                String ts = rs.getString("ts");
                dataMap.put("data", data);
                dataMap.put("patient_id", pid);
                dataMap.put("service_id", sid);
                dataMap.put("id", dat_id);
                dataMap.put("ts", ts);
            }

            rs.close();
            stmt.close();
            conn.close();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return dataMap;
    }



    public int executeUpdate(String stmtString) {
        int result = -1;
        try {
            Connection conn = ds.getConnection();
            Statement stmt = conn.createStatement();
            result = stmt.executeUpdate(stmtString);
            stmt.close();
            conn.close();

        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return  result;
    }

    public int dropTable(String tableName) {
        int result = -1;
        try {
            Connection conn = ds.getConnection();
            String stmtString = null;

            stmtString = "DROP TABLE " + tableName;

            Statement stmt = conn.createStatement();

            result = stmt.executeUpdate(stmtString);

            stmt.close();
            conn.close();

        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


    //utility functions

    public boolean databaseExist(String databaseName)  {
        boolean exist = false;

        Statement stmt = null;
        ResultSet rs = null;

        try {

            Connection conn = ds.getConnection();
            String queryString = null;

            queryString = "SELECT COUNT(1) FROM INFORMATION_SCHEMA.SCHEMATA " +
                    "WHERE SCHEMA_NAME  = N'" + databaseName + "'";

            stmt = conn.createStatement();

            rs = stmt.executeQuery(queryString);
            rs.next();
            exist = rs.getBoolean(1);

            rs.close();
            stmt.close();
            conn.close();

            //todo likely better way to do this hack to let derby work
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return exist;
    }

    public List<String> getDatabaseNames()  {

        List<String> databaseNames = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Connection conn = ds.getConnection();
            databaseNames = new ArrayList<>();

            String queryString = null;

            queryString = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA";

            stmt = conn.createStatement();

            rs = stmt.executeQuery(queryString);

            while (rs.next()) {
                databaseNames.add(rs.getString(1));
            }
            rs.close();
            stmt.close();
            conn.close();

        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        return databaseNames;

    }

    public List<String> getTableNames(String database)  {

        List<String> tableNames = null;

        Statement stmt = null;
        ResultSet rs = null;

        try {

            Connection conn = ds.getConnection();
            tableNames = new ArrayList<>();
            String queryString = null;

            queryString = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '" + database + "'";

            stmt = conn.createStatement();

            rs = stmt.executeQuery(queryString);

            while (rs.next()) {
                tableNames.add(rs.getString(1));
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return tableNames;
    }

    public List<String> getColumnNames(String database, String table)  {

        List<String> columnNames = null;

        Statement stmt = null;
        ResultSet rs = null;

        try {
            Connection conn = ds.getConnection();
            columnNames = new ArrayList<>();
            String queryString = null;

            queryString = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA='" + database + "' AND TABLE_NAME='" + table + "'";

            stmt = conn.createStatement();

            rs = stmt.executeQuery(queryString);

            while (rs.next()) {
                columnNames.add(rs.getString(1));
            }
            rs.close();
            stmt.close();
            conn.close();

        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return columnNames;
    }

    public boolean tableExist(String tableName)  {
        boolean exist = false;

        Statement stmt = null;
        ResultSet rs = null;

        try {
            Connection conn = ds.getConnection();
            String queryString = null;

            queryString = "SELECT COUNT(1) FROM INFORMATION_SCHEMA.TABLES " +
                    "WHERE TABLE_NAME = N'" + tableName + "'";

            stmt = conn.createStatement();

            rs = stmt.executeQuery(queryString);
            rs.next();
            exist = rs.getBoolean(1);

            rs.close();
            stmt.close();
            conn.close();


            //todo likely better way to do this hack to let derby work
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return exist;
    }

    //Below here are helper functions for department, institution, and location
    public Map<String,String> getDept(String deptId) {
        Map<String,String> deptMap = new HashMap<>();

        Statement stmt = null;
        try
        {
            Connection conn = ds.getConnection();
            String queryString = null;

            queryString = "SELECT * FROM Departments WHERE department_id='" + deptId + "'";

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(queryString);

            while (rs.next()) {
                /*
                    inst id
                    department id
                 */
                //String dept_id = rs.getString("department_id");
                String inst_id = rs.getString("taxid");
                //id is input
                deptMap.put("department_id", deptId);
                deptMap.put("taxid", inst_id); //taxid is the institution id
                //deptMap.put("department_id", deptId);
            }

            rs.close();
            stmt.close();
            conn.close();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return deptMap;
    }

    public Map<String,String> getInst(String instId) {
        Map<String,String> instMap = new HashMap<>();

        Statement stmt = null;
        try
        {
            Connection conn = ds.getConnection();
            String queryString = null;

            queryString = "SELECT * FROM Institution WHERE taxid='" + instId + "'";

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(queryString);

            while (rs.next()) {
                /*
                    taxid
                 */
                //String dept_id = rs.getString("department_id");
                String inst_id = rs.getString("taxid");
                //id is input
                //deptMap.put("department_id", deptId);
                instMap.put("taxid", inst_id); //taxid is the institution id
                //deptMap.put("department_id", deptId);
            }

            rs.close();
            stmt.close();
            conn.close();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return instMap;
    }

    public Map<String,String> getLoc(String locID) {
        Map<String,String> locMap = new HashMap<>();

        Statement stmt = null;
        try
        {
            Connection conn = ds.getConnection();
            String queryString = null;

            queryString = "SELECT * FROM Location WHERE address='" + locID + "'";

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(queryString);

            while (rs.next()) {
                /*
                    address
                    taxid
                 */
                //String taxid = rs.getString("taxid");
                String address = rs.getString("address");
                String taxid = rs.getString("taxid");
                //id is input
                locMap.put("address", address);
                locMap.put("taxid", taxid); //taxid is the institution id
                //deptMap.put("department_id", deptId);
            }

            rs.close();
            stmt.close();
            conn.close();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return locMap;
    }
    
    public Map<String,String> getremloc(String address) {
        Map<String,String> rlocMap = new HashMap<>();

        Statement stmt = null;
        try
        {
            Connection conn = ds.getConnection();
            String queryString = null;

            queryString = "SELECT address FROM Patient WHERE address='" + address + "' UNION SELECT address FROM Service WHERE address='" + address + "'";

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(queryString);

            while (rs.next()) {
                /*
                    inst id
                    department id
                 */
                //String dept_id = rs.getString("department_id");
                String address = rs.getString("address");
                //id is input
                locMap.put("address", address); //taxid is the institution id
                //deptMap.put("department_id", deptId);
            }

            rs.close();
            stmt.close();
            conn.close();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return deptMap;
    }
}
