import java.util.*;
import java.net.*;
import java.io.*; // for files

public class Main
{
    private static int instanceNumber = 6666;
    public static String configFile = "configuration.txt";
    public static int[] distances;
    public static String routerName;
    public static int portNumb;
    public static int neighbor1Port;
    public static int neighbor2Port;
    public static DatagramSocket me;
    public static InetAddress IPAddress;
    public static DatagramPacket packet;

    public static void main(String args)
    {
        distances = new int[] {-1, -1, -1};

        // TODO: read in values from file

        routerName = getRouter();
        try { IPAddress = InetAddress.getByName("localhost"); me = new DatagramSocket(portNumb); } catch (Exception e) { System.out.println("Error: " + e); }

        printDistances();

        sendDistances();

        while (true)
        {
            receiveDistVectors();
        }
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
        try { me.receive(packet); } catch (Exception e) { System.out.println("Error: " + e); }

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

    private static int[] fileAccess(String configFile) {
        FileReader fileReader = new FileReader(fileReader);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
                
    }
}
