
package config;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;


public class ConnectionPool {
    
    private final String db="bdnbelectronica";
    private final String user="root";   
    
    //localhost
    private final String url="jdbc:mysql://localhost:3306/"+db+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final String pass="";   
      
    //servidor  
    //private final String url="jdbc:mysql://node93497-env-1566785.jelastic.saveincloud.net:3306/"+db+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    //private final String pass="xbUgyWmCh6";
    
    private static ConnectionPool dataSource;
    private BasicDataSource basicDataSource=null;

//Constructor    
    private ConnectionPool(){
     
        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(pass);
        basicDataSource.setUrl(url);
        
        basicDataSource.setMinIdle(5);
        basicDataSource.setMaxIdle(20);
        basicDataSource.setMaxTotal(50);
        basicDataSource.setMaxWaitMillis(-1);
        
    }
    
    public static ConnectionPool getInstance() {
        if (dataSource == null) {
            dataSource = new ConnectionPool(); 
        } 
            return dataSource;
    }

    public Connection getConnection() throws SQLException{
        try {
            return this.basicDataSource.getConnection();
        } catch (SQLException e) {
            throw new SQLException("No se pudo conectar a la base de datos");
        }
      
    }
    
    public void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }    
    
}

