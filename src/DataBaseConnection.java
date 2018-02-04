import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//implement connection to database
//initConnection() - open connection to database;
//closeConnection()- close opened connection;
public class DataBaseConnection {

    private Connection connection;
    private final static String dbURL = "jdbc:mysql://localhost:3306";

    public void initConnection( String dbName, String dbUser, String dbPassword){
        try
        {

            this.connection = DriverManager.getConnection(dbURL+"/"+dbName, dbUser, dbPassword);
            //System.out.println(this.connection);
        }
        catch( SQLException ex ) {
            ex.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            this.connection.close();
            System.out.println("End of connection");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return this.connection;
    }

}
