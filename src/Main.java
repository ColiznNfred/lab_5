/**
 *
 * Fred Kneeland and Collin Moore
 * CSCI 466
 * Lab #5
 *
 * Running Instructions:
 * Start up instance one give it 'X' or 'Y' or 'Z' in console
 * Start up instance two give it 'X' or 'Y' or 'Z' in console
 * Start up instance three give it 'X' or 'Y' or 'Z' in console
 *
 * and the three routers will communicate and update automatically, printing out when they receive
 * a vector from a neighbor and whether or not they update
 *
 */



import java.util.*;
import java.net.*;
import java.io.*; // for files

public class Main
{
    private static int instanceNumber = 6667;
    public static String configFile = "configuration.txt";
    public static int[][] distances;
    public static String routerName;
    public static int portNumb;
    public static int neighbor1Port;
    public static int neighbor2Port;
    public static DatagramSocket me;
    public static InetAddress IPAddress;
    public static DatagramPacket packet;
    public static int usIndex;
    public static int neighbor1Index;
    public static int neighbor2Index;


    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        distances = getDistances();

//        for (int i = 0; i < distances.length; i++)
//        {
//            for (int j = 0; j < distances[i].length; j++)
//            {
//                System.out.print(distances[i][j] + ", ");
//            }
//            System.out.println();
//        }

        // get which router we are
        System.out.println("Enter the router’s ID: ");
        String next = in.nextLine();
//        System.out.println(next);


        if (next.equals("X")||next.equals("x"))
        {
            usIndex = 0;
        }
        else if (next.equals("Y")||next.equals("y"))
        {
            usIndex = 1;
        }
        else if(next.equals("Z")||next.equals("z"))
        {
            usIndex = 2;
        }
        else{
            return;// leave if not x, y, or z
        }

        neighbor1Index = (usIndex + 1) % 3;
        neighbor2Index = (usIndex + 2) % 3;
        neighbor1Port = distances[0][neighbor1Index];
        neighbor2Port = distances[0][neighbor2Index];

        portNumb = distances[0][usIndex];

//        System.out.println(portNumb);

        routerName = next;
        try { IPAddress = InetAddress.getByName("localhost"); me = new DatagramSocket(portNumb); } catch (Exception e) { System.out.println("Error: " + e); }

        boolean ack1 = false;
        boolean ack2 = false;

        byte[] data = new byte[1];
        data[0] = (byte) (usIndex + 20);
        byte[] recieveData = new byte[1];

        while (!ack1 || !ack2)
        {
            if (!ack1)
            {
                DatagramPacket neighbor1 = new DatagramPacket(data, data.length, IPAddress, neighbor1Port);

                try { me.send(neighbor1); } catch (Exception e) { System.out.println(e); }
            }

            if (!ack2)
            {
                DatagramPacket neighbor2 = new DatagramPacket(data, data.length, IPAddress, neighbor2Port);

                try { me.send(neighbor2); } catch (Exception e) { System.out.println(e); }
            }

            packet = new DatagramPacket(recieveData, recieveData.length);
            try { me.receive(packet); } catch (Exception e) { System.out.println("Error: " + e); }

            if (recieveData[0] == neighbor1Index + 20)
            {
                ack1 = true;
                DatagramPacket neighbor1 = new DatagramPacket(data, data.length, IPAddress, neighbor1Port);

                try { me.send(neighbor1); } catch (Exception e) { System.out.println(e); }
            }
            else if (recieveData[0] == neighbor2Index + 20)
            {
                ack2 = true;
                DatagramPacket neighbor2 = new DatagramPacket(data, data.length, IPAddress, neighbor2Port);

                try { me.send(neighbor2); } catch (Exception e) { System.out.println(e); }
            }
        }

//        System.out.println("After Acks: " + System.currentTimeMillis());

        printDistances();

        sendDistances();

        int counter = 0;
        while (counter<50) // only try  for 5 seconds
        {
            receiveDistVectors();
            try {
                Thread.sleep(100);
            }
            catch (Exception e) { System.out.println("Error: " + e); }
            counter++;
        }

        // counter has now expired
        if(usIndex==0){ // if this is router X
            distances[usIndex + 1][1]=60; // x-y link changes to 60
        }
        if(usIndex==0){
            distances[usIndex + 1][0]=60; // y-x link changes to 60
        }
        printDistances();

        sendDistances();

        while(true){
            receiveDistVectors();
            try {
                Thread.sleep(100);
            }
            catch (Exception e) { System.out.println("Error: " + e); }
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
        byte[] data = new byte[4];

        data[0] = (byte) usIndex;
        data[1] = (byte) distances[usIndex + 1][0];
        data[2] = (byte) distances[usIndex + 1][1];
        data[3] = (byte) distances[usIndex + 1][2];

        DatagramPacket neighbor1 = new DatagramPacket(data, data.length, IPAddress, neighbor1Port);
        DatagramPacket neighbor2 = new DatagramPacket(data, data.length, IPAddress, neighbor2Port);

        try { me.send(neighbor1); me.send(neighbor2); } catch (Exception e) { System.out.println(e); }
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
        int[] receivedDistances = new int[] {-1,-1,-1};

        // TODO: Add decrypting message here
        byte[] data = new byte[4];
        packet = new DatagramPacket(data, data.length);
        try { me.receive(packet); } catch (Exception e) { System.out.println("Error: " + e); }

        int neighbor = (int) data[0];
        if (neighbor > 15)
        {
            //sendDistances();
            return;
        }
        receivedDistances[0] = (int) data[1];
        receivedDistances[1] = (int) data[2];
        receivedDistances[2] = (int) data[3];

        System.out.println("Receives distance vector from router " + neighbor + ": <" + receivedDistances[0] + ", " + receivedDistances[1] + ", " + receivedDistances[2] + ">");
        changeDistanceVectors(receivedDistances, neighbor);
    }

    /**
     * This function prints the current distances
     */
    public static void printCurrentDistances()
    {
        System.out.println("<" + distances[usIndex + 1][0] + "," + distances[usIndex + 1][1] + "," + distances[usIndex + 1][2] + ">");
    }

    /**
     * This function calculates the new distance vector
     */
    public static void changeDistanceVectors(int[] neighborVector, int neighbor)
    {
        boolean updated = false;

        int distToNeighbor = distances[usIndex + 1][neighbor];

        if (neighbor == neighbor1Index)
        {
            int newDist = distToNeighbor + neighborVector[neighbor2Index];
            // if it is fastor to go through this neighbor than current method update
            if (newDist < distances[usIndex + 1][neighbor2Index])
            {
                distances[usIndex + 1][neighbor2Index] = newDist;
                updated = true;
            }
        }
        else if (neighbor == neighbor2Index)
        {
            int newDist = distToNeighbor + neighborVector[neighbor1Index];
            // if it is fastor to go through this neighbor than current method update
            if (newDist < distances[usIndex + 1][neighbor1Index])
            {
                distances[usIndex + 1][neighbor1Index] = newDist;
                updated = true;
            }
        }


        if (updated)
        {
            System.out.println("Distance vector on router " + routerName + " is updated to:");
            printCurrentDistances();
            sendDistances();
        }
        else
        {
            System.out.println("Distance vector on router " + routerName + " is not updated");
        }
    }


    private static int[][] getDistances() {
        int[][] toReturn = new int[4][3];
        try {
            FileReader fileReader = new FileReader(configFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            StringBuilder sb = new StringBuilder();
            int i=0;
            char ch = line.charAt(i);
            while (ch >= '0' && ch <= '9') {
                sb.append(ch);
                i++;
                ch = line.charAt(i);
            }
            String firstNumber = sb.toString();
            int number = Integer.parseInt(firstNumber);
            toReturn[0][0] = number;
            while (ch < '0' || ch > '9')
            {
                i++; // move i past the \t
                ch = line.charAt(i);
            }
            sb = new StringBuilder();
            while (ch >= '0' && ch <= '9') {
                sb.append(ch);
                i++;
                ch = line.charAt(i);
            }
            firstNumber = sb.toString();
            number = Integer.parseInt(firstNumber);
            toReturn[0][1]=number; // port of the second router
            while (ch < '0' || ch > '9')
            {
                i++; // move i past the \t
                ch = line.charAt(i);
            }
            sb = new StringBuilder();
            while (ch >= '0' && ch <= '9' && i < line.length()) {
                sb.append(ch);
                i++;
                if (i < line.length())
                {
                    ch = line.charAt(i);
                }
            }
            firstNumber = sb.toString();
            number = Integer.parseInt(firstNumber);
            toReturn[0][2]=number; // port of the third router
            String line2 = bufferedReader.readLine();
            String line3 = bufferedReader.readLine();
            String line4 = bufferedReader.readLine();
            toReturn[1][0] = ((int)line2.charAt(0)-0x30);
            toReturn[2][0] = ((int)line3.charAt(0)-0x30);
            toReturn[3][0] = ((int)line4.charAt(0)-0x30);
            toReturn[1][1] = ((int)line2.charAt(2)-0x30);
            toReturn[2][1] = ((int)line3.charAt(2)-0x30);
            toReturn[3][1] = ((int)line4.charAt(2)-0x30);
            toReturn[1][2] = ((int)line2.charAt(4)-0x30);
            toReturn[2][2] = ((int)line3.charAt(4)-0x30);
            toReturn[3][2] = ((int)line4.charAt(4)-0x30);
            // reads all the distance values, as long as they are one char long
            bufferedReader.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return  toReturn;
    }
}
