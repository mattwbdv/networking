import java.net.*; // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*; // for IOException
import java.nio.charset.StandardCharsets;

public class myFirstUDPServer {

    private static final int ECHOMAX = 255; // Maximum size of echo datagram

    public static void main(String[] args) throws IOException {

        if (args.length != 1) // Test for correct argument list
            throw new IllegalArgumentException("Parameter(s): <Port>");

        int servPort = Integer.parseInt(args[0]);

        DatagramSocket socket = new DatagramSocket(servPort);
        DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);

        for (;;) { // Run forever, receiving and echoing datagrams
            socket.receive(packet); // Receive packet from client
            System.out.println("Handling client at " +
                    packet.getAddress().getHostAddress() + " on port " + packet.getPort());

            byte[] buffer = new byte[1024];

            String v = new String(buffer, 0, packet.getLength());
            System.out.println(v.toUpperCase());

            socket.send(packet); // Send the same packet back to client

            packet.setLength(ECHOMAX); // Reset length to avoid shrinking buffer
        }
        /* NOT REACHED */
    }
}
