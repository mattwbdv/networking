import java.net.*; // for Socket
import java.io.*; // for IOException and Input/OutputStream
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.net.InetAddress;

public class myFirstUDPClient {

    private static final int TIMEOUT = 3000; // Resend timeout (milliseconds)
    private static final int MAXTRIES = 5; // Maximum retransmissions

    public static void main(String[] args) throws IOException {

        if (args.length != 2) // Test for correct # of args
            throw new IllegalArgumentException("Parameter(s): <Server> <Port>");

        InetAddress server = InetAddress.getByName(args[0]); // Server name or IP address
        int servPort = Integer.parseInt(args[1]); // Port number

        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(TIMEOUT); // Maximum receive blocking time (milliseconds)

        // Using Scanner for Getting Input from User
        Scanner scanning = new Scanner(System.in);
        byte[] bytesToSend = null;

        // Convert input String to bytes using the default character encoding
        String stringToSend = "";
        long startTime = System.currentTimeMillis();

        while ((stringToSend = scanning.nextLine()) != "stop") {
            bytesToSend = stringToSend.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(bytesToSend, // Sending packet
                    bytesToSend.length, server, servPort);

            DatagramPacket receivePacket = // Receiving packet
                    new DatagramPacket(new byte[bytesToSend.length], bytesToSend.length);

            int tries = 0; // Packets may be lost, so we have to keep trying
            boolean receivedResponse = false;
            do {
                socket.send(sendPacket); // Send the echo string
                try {
                    socket.receive(receivePacket); // Attempt echo reply reception

                    if (!receivePacket.getAddress().equals(server)) // Check source
                        throw new IOException("Received packet from an unknown source");

                    receivedResponse = true;
                } catch (InterruptedIOException e) { // We did not get anything
                    tries += 1;
                    System.out.println("Timed out, " + (MAXTRIES - tries) + " more tries...");
                }
            } while ((!receivedResponse) && (tries < MAXTRIES));

            if (receivedResponse)
                System.out.println("Received: " + new String(receivePacket.getData()));
            else
                System.out.println("No response -- giving up.");

            long endTime = System.currentTimeMillis();
            long delayNS = endTime - startTime;

            System.out.println("The message was received by the server and it read: '" + new String(bytesToSend)
                    + "''. The message took " + delayNS + " milliseconds to be sent.");

        }

        socket.close();

    }
}
