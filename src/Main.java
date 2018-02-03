
public class Main {



    public static void main(String[] args){
        try {
            //System.out.print(args[0]);
            FileWorker  fileWorker= new FileWorker(args[0]);
            fileWorker.copyFiles();
        }
        catch (ArrayIndexOutOfBoundsException ex){

        }
    }

}
