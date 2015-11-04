import java.util.*;
import java.net.*;
import java.io.*; // for files

public class Main
{
    private static int instanceNumber = 6667;
    public static String configFile = "configuration.txt";
    public static int[] distances;
    public static String routerName;
    public static int portNumb;
    public static int neighbor1Port;
    public static int neighbor2Port;
    public static DatagramSocket me;
    public static InetAddress IPAddress;
    public static DatagramPacket packet;

    public static void main(String[] args)
    {
        distances = getDistances();

        // TODO: read in values from file

//        routerName = getRouter();
//        try { IPAddress = InetAddress.getByName("localhost"); me = new DatagramSocket(portNumb); } catch (Exception e) { System.out.println("Error: " + e); }
//
//        printDistances();
//
//        sendDistances();
//
//        while (true)
//        {
//            receiveDistVectors();
//        }
    }


    /********************************
     *
     * Helper Functions go here
     *
     ********************************/

    /**
     * This function will send its distances to its neighbors
     */
    public static void sendDistances()
    {
        // TODO: send distance vectors to neighbors
    }

    /**
     * This function will print stuff
     */
    public static void printDistances()
    {
        System.out.println("Router " + routerName + " is running on port " + portNumb);
        System.out.println("Distance vector on router " + routerName + " is: ");
        printCurrentDistances();
    }

    /**
     * This function will receive data from neighbors
     */
    public static void receiveDistVectors()
    {
        int[] distances = new int[] {-1,-1,-1};
        String name = "";

        // TODO: Add decrypting message here
        byte[] data = new byte[3];
        packet = new DatagramPacket(data, data.length);
        try { me.receive(packet); } catch (Exception e) { System.out.println("Error: " + e);
        }

        System.out.println("Receives distance vector from router " + name + ": <" + distances[0] + ", " + distances[1] + ", " + distances[2] + ">");
        changeDistanceVectors(distances);
    }

    /**
     * This function prints the current distances
     */
    public static void printCurrentDistances()
    {
        System.out.println("<" + distances[0] + "," + distances[1] + "," + distances[2] + ">");
    }

    /**
     * This function calculates the new distance vector
     */
    public static void changeDistanceVectors(int[] neighborVector)
    {
        boolean updated = false;

        // TODO: do distance vector updates


        if (updated)
        {
            System.out.println("Distance vector on router " + routerName + " is updated to:");
            printCurrentDistances();
        }
        else
        {
            System.out.println("Distance vector on router " + routerName + " is not updated");
        }
    }

    public static String getRouter()
    {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    private static int[] getDistances() {
        int[] toReturn = new int[3];
        try {
            FileReader fileReader = new FileReader(configFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            StringBuilder sb = new StringBuilder();
            int i=0;
            char ch = line.charAt(i);
            while (ch != '\t') {
                sb.append(ch);
                i++;
                ch = line.charAt(i);
            }
            String firstNumber = sb.toString();
            int number = Integer.parseInt(firstNumber);
            String line2 = bufferedReader.readLine();
            String line3 = bufferedReader.readLine();
            String line4 = bufferedReader.readLine();
            if(number==instanceNumber){ // if the router is the first in the list, get the first column of numbers
                    toReturn[0] = (int)line2.charAt(0);
                    toReturn[1] = (int)line3.charAt(0);
                    toReturn[2] = (int)line4.charAt(0);
            }
            else if((number+1)==instanceNumber){ // second router gets second column
                toReturn[0] = (int)line2.charAt(2);
                toReturn[1] = (int)line3.charAt(2);
                toReturn[2] = (int)line4.charAt(2);
            }
            else if((number+2)==instanceNumber){ // third router gets third column
                toReturn[0] = (int)line2.charAt(4);
                toReturn[1] = (int)line3.charAt(4);
                toReturn[2] = (int)line4.charAt(4);
            }
            bufferedReader.close();
            for(i=0;i<toReturn.length;i++){
                toReturn[i]-=0x30;
            }
        } catch (Exception ex) {
            System.out.println("Error " + ex);
        }
        return  toReturn;
    }
}
