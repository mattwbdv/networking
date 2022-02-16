import java.net.*; // for DatagramSocket and DatagramPacket
import java.io.*; // for IOException
import java.nio.charset.StandardCharsets;

public class ServerTCP {
  private static final int BUFSIZE = 32; // Size of receive buffer

  public static void main(String[] args) throws Exception {
    boolean running = true;

    if (args.length != 1 && args.length != 2) // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port> [<encoding>]");

    int port = Integer.parseInt(args[0]); // Receiving Port

    ServerSocket servSock = new ServerSocket(port);
    Socket clntSock = servSock.accept();

    // Figure out client
    // InetSocketAddress sockaddr = (InetSocketAddress)
    // clntSock.getRemoteSocketAddress();
    // InetAddress destAddr = sockaddr.getAddress();
    // int destPort = sockaddr.getPort();

    while (running) {
      try {

        // Receive binary-encoded request
        RequestDecoder decoder = new RequestDecoderBin();
        Request receivedRequest = decoder.decode(clntSock.getInputStream());

        System.out.println("Received Binary-Encoded Request");
        System.out.println(receivedRequest);
        // Socket sock = new Socket(destAddr, destPort);

        if (receivedRequest.numOperands > 1) {
          Response responseToSend = new Response(
              receivedRequest.reqLength,
              receivedRequest.id,
              receivedRequest.opCode,
              receivedRequest.numOperands,
              receivedRequest.op1,
              receivedRequest.op2);

          ResponseEncoder encoder = new ResponseEncoderBin();

          OutputStream out = clntSock.getOutputStream(); // Get a handle onto Output Stream
          out.write(encoder.encode(responseToSend)); // Encode and send

        } else {
          Response responseToSend = new Response(
              receivedRequest.reqLength,
              receivedRequest.id,
              receivedRequest.opCode,
              receivedRequest.numOperands,
              receivedRequest.op1);

          ResponseEncoder encoder = new ResponseEncoderBin();

          OutputStream out = clntSock.getOutputStream(); // Get a handle onto Output Stream
          out.write(encoder.encode(responseToSend)); // Encode and send

          clntSock.close();
          servSock.close();

          // clntSock.close(); // Close the socket. We are done with this client!
        }
      } catch (Exception ignore) {

      }
    }
  }
}
