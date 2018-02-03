import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.util.HashMap;
import java.util.Map;
import java.io.*;
import  com.jcraft.jsch.*;

public class FileWorker {

    private String fileToParse;
    private String fileToCopy;
    private String pathLocalCopy;
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

    public FileWorker(String toParse){
        this.fileToParse = toParse;

    }

   private void setParseFile(){
        HashMap<String, String> parsed = new HashMap<>();

        File toParse = new File(this.fileToParse);
        try {
            FileInputStream fileInputStream = new FileInputStream(toParse);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            String temp;
            while ((temp = reader.readLine()) !=null){
                parsed.put(temp.split("=")[0].trim(),temp.split("=")[1].trim());


            }


        }
        catch (IOException ex){
            ex.printStackTrace();
        }

       this.configs = parsed;

    }

    public void copyFiles(){
        setParseFile();

        SftpConnection sftpConnection = new SftpConnection(this.configs.get(sftpHost),
                Integer.parseInt(this.configs.get(sftpPort)),
                this.configs.get(sftpUser),
                this.configs.get(sftpPass));
        int res = sftpConnection.setConnection(this.configs.get(sftpRemDir), this.configs.get(localDir));
        System.out.println(res);
    }


}
