import java.net.*; // for DatagramSocket and DatagramPacket
import java.io.*; // for IOException

public class ServerUDP {

  public static void main(String[] args) throws Exception {
    boolean running = true;

    if (args.length != 1 && args.length != 2) // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port> [<encoding>]");

    int port = Integer.parseInt(args[0]); // Receiving Port

    DatagramSocket sock = new DatagramSocket(port); // UDP socket for receiving
    DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
    while (running) {
      sock.receive(packet);

      InetAddress destAddr = packet.getAddress();
      int destPort = packet.getPort();

      // Receive binary-encoded request
      // FriendDecoder decoder = new FriendDecoderBin();
      RequestDecoder decoder = (args.length == 2 ? // Which encoding
          new RequestDecoderBin(args[1]) : new RequestDecoderBin());

      Request receivedRequest = decoder.decode(packet);

      System.out.println("Received Binary-Encoded Request");

      System.out.println(receivedRequest);

      if (receivedRequest.numOperands > 1) {
        Response responseToSend = new Response(
            receivedRequest.reqLength,
            receivedRequest.id,
            receivedRequest.opCode,
            receivedRequest.numOperands,
            receivedRequest.op1,
            receivedRequest.op2);

        // Use the encoding scheme given on the command line (args[2])
        ResponseEncoder encoder = new ResponseEncoderBin();

        byte[] codedRequest = encoder.encode(responseToSend); // Encode request

        DatagramPacket message = new DatagramPacket(codedRequest,
            codedRequest.length,
            destAddr, destPort);
        sock.send(message);
      } else {
        Response responseToSend = new Response(
            receivedRequest.reqLength,
            receivedRequest.id,
            receivedRequest.opCode,
            receivedRequest.numOperands,
            receivedRequest.op1);

        // Use the encoding scheme given on the command line (args[2])
        ResponseEncoder encoder = new ResponseEncoderBin();

        byte[] codedRequest = encoder.encode(responseToSend); // Encode request

        DatagramPacket message = new DatagramPacket(codedRequest,
            codedRequest.length,
            destAddr, destPort);
        sock.send(message);
      }
    }

    // sock.close();
  }
}
