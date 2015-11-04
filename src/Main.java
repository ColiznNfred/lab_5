import java.util.*;
import java.net.*;


public class Main
{
    public static int[] distances;
    public static String routerName;

    public static void main(String args)
    {
        distances = new int[] {-1, -1, -1};
        routerName = getRouter();
    }


    /********************************
     *
     * Helper Functions go here
     *
     ********************************/

    /**
     * This function will prepare this client to receive values
     * from its neighbors
     */
    public static void openPorts()
    {
        // TODO: open port listening with receiveDistVector
    }

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
        System.out.println("Distance vector on router " + routerName + " is: ");
        printCurrentDistances();
    }

    /**
     * This function will receive data from neighbors
     */
    public static int[] receiveDistVectors()
    {
        int[] distances = new int[] {-1,-1,-1};
        String name = "";

        // TODO: Add decrypting message here

        System.out.println("Receives distance vector from router " + name + ": <" + distances[0] + ", " + distances[1] + ", " + distances[2] + ">");
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
    public static void changeDistanceVectors()
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
}
