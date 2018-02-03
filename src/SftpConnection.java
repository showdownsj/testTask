import com.jcraft.jsch.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
public class SftpConnection {

    private String hostName;
    private int port;
    private String user;
    private  String password;

    public SftpConnection(String hostName, int port, String user, String pass){
        this.hostName = hostName;
        this.port = port;
        this.password = pass;
        this.user =user;
    }

    public int   setConnection(String source, String dest){
        JSch jsch = new JSch();
        Session session = null;
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


           // System.out.println(channelSftp.ls(source).elements());
            List<ChannelSftp.LsEntry> listOfFiles = channelSftp.ls(source+"/*.*");
            for(ChannelSftp.LsEntry file : listOfFiles ){
               // System.out.println(file.getFilename());
                channelSftp.get(source+"/"+file.getFilename(),dest+"/"+file.getFilename());
                // to add sql tomorrow here
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


}
