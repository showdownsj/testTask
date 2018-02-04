
public class Main {



    public static void main(String[] args){
        try {
            //System.out.print(args[0]);
            if (args.length>1)
                throw new Exception("There are too much arguments. Waited for one (path to config file)");
            FileWorker  fileWorker= new FileWorker(args[0]);
            fileWorker.copyFiles();
        }
        catch (ArrayIndexOutOfBoundsException ex){
                ex.printStackTrace();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
