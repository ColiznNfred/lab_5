import java.util.*;
import java.net.*;
imnport java.io.*; // for files

public class Main
{
    private int instanceNumber = 6666;
    configFile = "configuration.txt";
    public static void main(String args)
    {

    }


    /********************************
     *
     * Helper Functions go here
     *
     ********************************/
    public static String getRouter()
    {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    private int[] fileAccess(String configFile) {
        try {
            FileReader fileReader = new FileReader(fileReader);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            StringBuilder sb = new StringBuilder();
            char ch = line.nextChar();
            while(ch!=' '){
                sb.append(ch);
                ch = line.nextChar();
            }

            bufferedReader.close();
        }
        catch(Exception ex){
            System.out.println("Error "+ex);
        }
    }
}
