import java.net.*; // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*; // for IOException
import java.util.Scanner;

public class ClientUDP {

  public static void main(String args[]) throws Exception {
    int TML;
    int opCode;
    int numOperands = 2;
    int op1 = 0;
    int op2 = 0;
    boolean multiVariables = true;
    int requestID = 0;

    if (args.length != 2 && args.length != 3) // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Destination>" +
          " <Port> [<encoding]");

    InetAddress destAddr = InetAddress.getByName(args[0]); // Destination address
    int destPort = Integer.parseInt(args[1]); // Destination port

    Scanner in = new Scanner(System.in);
    System.out.println("Please state the total message length: ");
    TML = in.nextInt();

    System.out.println("Please state the operation code: ");
    opCode = in.nextInt();
    if (opCode == 6) {
      multiVariables = false;
      numOperands = 1;
    }

    System.out.println("Please state Operand 1: ");
    op1 = in.nextInt();

    if (multiVariables) {
      System.out.println("Please state Operand 2: ");
      op2 = in.nextInt();

    }

    if (multiVariables) {
      DatagramSocket sock = new DatagramSocket(); // UDP socket for sending
      Request request = new Request(TML, requestID, opCode, numOperands, op1, op2);
      RequestEncoder encoder = (args.length == 3 ? new RequestEncoderBin(args[2]) : new RequestEncoderBin());
      byte[] codedRequest = encoder.encode(request); // Encode request
      DatagramPacket message = new DatagramPacket(codedRequest,
          codedRequest.length,
          destAddr, destPort);
      sock.send(message);

      DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
      sock.receive(packet);

      // Receive binary-encoded request
      // FriendDecoder decoder = new FriendDecoderBin();
      ResponseDecoder decoder = (args.length == 2 ? // Which encoding
          new ResponseDecoderBin(args[1]) : new ResponseDecoderBin());

      Response receivedResponse = decoder.decode(packet);

      System.out.println("Received Binary-Encoded Response");

      System.out.println(receivedResponse);

    } else {
      DatagramSocket sock = new DatagramSocket(); // UDP socket for sending
      Request request = new Request(TML, requestID, opCode, numOperands, op1);
      RequestEncoder encoder = (args.length == 3 ? new RequestEncoderBin(args[2]) : new RequestEncoderBin());
      byte[] codedRequest = encoder.encode(request); // Encode request
      DatagramPacket message = new DatagramPacket(codedRequest,
          codedRequest.length,
          destAddr, destPort);
      sock.send(message);

      DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
      sock.receive(packet);

      // Receive binary-encoded request
      // FriendDecoder decoder = new FriendDecoderBin();
      ResponseDecoder decoder = (args.length == 2 ? // Which encoding
          new ResponseDecoderBin(args[1]) : new ResponseDecoderBin());

      Response receivedResponse = decoder.decode(packet);

      System.out.println("Received Binary-Encoded Response");

      System.out.println(receivedResponse);
    }

  }
}
