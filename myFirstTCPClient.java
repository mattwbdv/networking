import java.net.*; // for Socket
import java.io.*; // for IOException and Input/OutputStream
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class myFirstTCPClient {

    public static void main(String[] args) throws IOException {

        if (args.length != 2) // Test for correct # of args
            throw new IllegalArgumentException("Parameter(s): <Server> <Port>");

        String server = args[0]; // Server name or IP address
        int servPort = Integer.parseInt(args[1]); // Port number

        // Create socket that is connected to server on specified port
        Socket socket = new Socket(server, servPort);
        System.out.println("Connected to server...sending echo string");

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        // Using Scanner for Getting Input from User
        Scanner scanning = new Scanner(System.in);
        byte[] byteBuffer = null;

        // Convert input String to bytes using the default character encoding
        String stringToSend = "";
        while ((stringToSend = scanning.nextLine()) != "stop") {
            byteBuffer = stringToSend.getBytes();

            long startTime = System.nanoTime();
            out.write(byteBuffer); // Send the encoded string to the server

            // Receive the same string back from the server
            int totalBytesRcvd = 0; // Total bytes received so far
            int bytesRcvd; // Bytes received in last read
            while (totalBytesRcvd < byteBuffer.length) {
                if ((bytesRcvd = in.read(byteBuffer, totalBytesRcvd,
                        byteBuffer.length - totalBytesRcvd)) == -1)
                    throw new SocketException("Connection close prematurely");
                totalBytesRcvd += bytesRcvd;
            }
            long endTime = System.nanoTime();
            long delayNS = endTime - startTime;
            long time = TimeUnit.MILLISECONDS.convert(delayNS, TimeUnit.NANOSECONDS);

            System.out.println("The message was received by the server and it read: '" + new String(byteBuffer)
                    + "''. The message took " + time + " milliseconds to be sent.");

        }

        scanning.close(); // close scanner
        socket.close(); // Close the socket and its streams
    }
}
