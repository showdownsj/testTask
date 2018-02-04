import java.sql.*;

//implementation of worker with database table (there's only insert method)
//insertLogs() - writing data from object (FilesCopyTable) to table connected database;
public class FilesCopyTableWorker {
    private Connection connection;

    public FilesCopyTableWorker(Connection connection){
        this.connection=connection;
        PreparedStatement preparedStatement;
        try{
            preparedStatement = this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS LogOfCopy (\n" +
                    " FIleId INT NOT NULL AUTO_INCREMENT,\n" +
                    " FileName VARCHAR(255) NOT NULL,\n" +
                    " DateOfCopy DATETIME NOT NULL,\n" +
                    "PRIMARY KEY (FileId));");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }


    public void insertLogs( FilesCopyTable filesCopyTable )
    {
        try
        {
           PreparedStatement preparedStatement;
           //System.out.println(this.connection);
           //System.out.println(filesCopyTable.getNameOfFile()+"   "+filesCopyTable.getDateOfCopy());

           preparedStatement = this.connection.prepareStatement( "INSERT INTO LogOfCopy " +
                   "(FileName, DateOfCopy) VALUES (?,?)" );
           preparedStatement.setString( 1, filesCopyTable.getNameOfFile() );
           preparedStatement.setTimestamp( 2,  filesCopyTable.getDateOfCopy() );
           preparedStatement.executeUpdate();
           preparedStatement.close();
        }
        catch( SQLException e )
        {
            e.printStackTrace();
        }
    }
}
