import java.net.*; // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*; // for IOException
import java.util.Scanner;

public class ClientTCP {

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

    Socket sock = new Socket(destAddr, destPort);

    if (multiVariables) {
      Request request = new Request(TML, requestID, opCode, numOperands, op1, op2);
      RequestEncoder encoder = (new RequestEncoderBin());

      OutputStream out = sock.getOutputStream(); // Get a handle onto Output Stream
      out.write(encoder.encode(request)); // Encode and send

      ResponseDecoder decoder = new ResponseDecoderBin();
      Response receivedResponse = decoder.decode(sock.getInputStream());

      System.out.println("Received Binary-Encoded Response");

      System.out.println(receivedResponse);

    } else {
      Request request = new Request(TML, requestID, opCode, numOperands, op1);
      RequestEncoder encoder = (new RequestEncoderBin());

      OutputStream out = sock.getOutputStream(); // Get a handle onto Output Stream
      out.write(encoder.encode(request)); // Encode and send

      ResponseDecoder decoder = new ResponseDecoderBin();
      Response receivedResponse = decoder.decode(sock.getInputStream());

      System.out.println("Received Binary-Encoded Response");

      System.out.println(receivedResponse);

    }
    sock.close();

  }
}
