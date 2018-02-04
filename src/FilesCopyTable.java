import java.sql.Timestamp;


//implement table from database to object
public final class FilesCopyTable {
    private int id;
    private Timestamp dateOfCopy;
    private String nameOfFile;

    public void setId(int id){
        this.id=id;
    }

    public void  setDateOfCopy(java.util.Date dateOfCopy){
        this.dateOfCopy = new Timestamp(dateOfCopy.getTime());
    }

    public void setNameOfFile(String nameOfFile){
        this.nameOfFile = nameOfFile;
    }

    public  int getId(){
        return  this.id;
    }

    public String getNameOfFile(){
        return  this.nameOfFile;
    }

    public Timestamp getDateOfCopy(){

        return  this.dateOfCopy;
    }
}
