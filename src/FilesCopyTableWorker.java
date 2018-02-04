import java.sql.*;

//implementation of worker with database table (there's only insert method)
//insertLogs() - writing data from object (FilesCopyTable) to table connected database;
public class FilesCopyTableWorker {
    private Connection connection;

    public FilesCopyTableWorker(Connection connection){
        this.connection=connection;
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
