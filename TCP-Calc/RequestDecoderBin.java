import java.io.*; // for ByteArrayInputStream
import java.net.*; // for DatagramPacket

public class RequestDecoderBin implements RequestDecoder, RequestBinConst {

  private String encoding; // Character encoding

  public RequestDecoderBin() {
    encoding = DEFAULT_ENCODING;
  }

  public RequestDecoderBin(String encoding) {
    this.encoding = encoding;
  }

  public Request decode(InputStream wire) throws IOException {
    DataInputStream src = new DataInputStream(wire);

    int reqLength = src.readInt();
    int id = src.readInt();
    int opCode = src.readInt();
    int numOperands = src.readInt();
    int op1 = src.readInt();
    if (numOperands > 1) {
      int op2 = src.readInt();
      return new Request(reqLength, id, opCode, numOperands, op1, op2);

    }

    return new Request(reqLength, id, opCode, numOperands, op1);
  }

  public Request decode(DatagramPacket p) throws IOException {
    ByteArrayInputStream payload = new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
    return decode(payload);
  }
}
