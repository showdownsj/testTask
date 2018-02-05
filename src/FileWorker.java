
import java.util.HashMap;
import java.io.*;

// implementation of file worker with:
// 1. setParseFile() - parsing config file to Map <parameter, value> (proposed what file has
//                     a view like 'sftpUser = "sftp_user" ' where in the left side
//                     before '=' there're unchangeable names of parameters and right of '='
//                     there're value of parameters);
// 2. copyFiles() - moving files from remote directory to local.
public class FileWorker {

    private String fileToParse;
    private HashMap<String,String> configs;

    private final static String sftpUser = "sftp_user";
    private final static String sftpPass = "sftp_password";
    private final static String sftpPort = "sftp_port";
    private final static String sftpRemDir = "sftp_remote_dir";
    private final static String sftpHost = "sftp_host";

    private final static String localDir = "local_dir";
    private final static String sqlUser = "sql_user";
    private final static String sqlPass = "sql_password";
    private final static String sqlDB = "sql_database";

    FileWorker(String toParse){
        this.fileToParse = toParse;

    }

   private void setParseFile(){
        HashMap<String, String> parsed = new HashMap<>();

        File toParse = new File(this.fileToParse);
        try {
            FileInputStream fileInputStream = new FileInputStream(toParse);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            String temp;


            while ((temp = reader.readLine()) !=null) {
                if (!temp.equals(""))
                    parsed.put(temp.split("=")[0].trim(), temp.split("=")[1].trim());

            }

            this.configs = parsed;


        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        catch (ArrayIndexOutOfBoundsException ex){
            System.out.println("Config file is incorrect");
            ex.printStackTrace();
        }




    }


    //copy files from remote directory to local
    //1. initiazle connection to database for writing logs of copying
    //2. create sftp connection and copying of files, then close connection to sql-base
    public void copyFiles(){
        setParseFile();

        if (this.configs !=null) {
            //1
            DataBaseConnection dbConnection = new DataBaseConnection();
            dbConnection.initConnection(this.configs.get(sqlDB), this.configs.get(sqlUser),
                    this.configs.get(sqlPass));

            //2
            if(dbConnection.getConnection() != null) {
                SftpConnection sftpConnection = new SftpConnection(this.configs.get(sftpHost),
                        Integer.parseInt(this.configs.get(sftpPort)),
                        this.configs.get(sftpUser),
                        this.configs.get(sftpPass));
                sftpConnection.setConnectionDB(dbConnection);
                int res = sftpConnection.setConnection(this.configs.get(sftpRemDir), this.configs.get(localDir));

                if (res >= 0)
                    dbConnection.closeConnection();
            }
        }
        else System.out.println("Parsed was failing");
    }


}
