import com.jcraft.jsch.*;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

//implement sftp connection
// setConnection (String src, String dst) - copying files from src to dst and writing logs
//                                          to table of connected database
// setConnectionDB () - setting here reference of connected database;
public class SftpConnection {

    private String hostName;
    private int port;
    private String user;
    private String password;
    private Connection connectionDB;


    SftpConnection(String hostName, int port, String user, String pass){
        this.hostName = hostName;
        this.port = port;
        this.password = pass;
        this.user =user;
    }

    public int setConnection(String source, String dest){
        JSch jsch = new JSch();
        Session session;
        try{
            session = jsch.getSession(this.user,this.hostName,this.port);
            session.setPassword(this.password);

            Properties configForConnection = new java.util.Properties();
            configForConnection.put("StrictHostKeyChecking", "no");
            session.setConfig(configForConnection);
            session.connect();

            Channel channel=session.openChannel("sftp");
            channel.connect();
            ChannelSftp channelSftp =(ChannelSftp)channel;

            FilesCopyTable filesCopyTable = new FilesCopyTable();
            FilesCopyTableWorker filesCopyTableWorker = new FilesCopyTableWorker(this.connectionDB);

            List<ChannelSftp.LsEntry> listOfFiles = channelSftp.ls(source+"/*");

            for(ChannelSftp.LsEntry file : listOfFiles ){
               // System.out.println(file.getFilename());
                try {
                    channelSftp.get(source + "/" + file.getFilename(), dest + "/" + file.getFilename());

                    Date dateNow = new Date();
                    filesCopyTable.setNameOfFile(file.getFilename());
                    filesCopyTable.setDateOfCopy(dateNow);
                    //System.out.println(dateNow);

                    filesCopyTableWorker.insertLogs(filesCopyTable);
                }
                catch (SftpException ex){
                    System.out.println("Directory can't be copied");
                }
            }


            channelSftp.exit();
            session.disconnect();
            return 1;
        }
        catch(JSchException jschEx){
            jschEx.printStackTrace();
        }

        catch (SftpException sftpEx){
            sftpEx.printStackTrace();
        }
        return 0;
    }

    public void setConnectionDB(DataBaseConnection connection){
        this.connectionDB = connection.getConnection();
    }

}
