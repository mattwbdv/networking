import java.io.*; // for ByteArrayInputStream
import java.net.*; // for DatagramPacket

public class ResponseDecoderBin implements ResponseDecoder, ResponseBinConst {

    private String encoding; // Character encoding

    public ResponseDecoderBin() {
        encoding = DEFAULT_ENCODING;
    }

    public ResponseDecoderBin(String encoding) {
        this.encoding = encoding;
    }

    public Response decode(InputStream wire) throws IOException {
        DataInputStream src = new DataInputStream(wire);

        int reqLength = src.readInt();
        int id = src.readInt();
        int opCode = src.readInt();
        int numOperands = src.readInt();
        int op1 = src.readInt();
        if (numOperands > 1) {
            int op2 = src.readInt();
            return new Response(reqLength, id, opCode, numOperands, op1, op2);

        }

        return new Response(reqLength, id, opCode, numOperands, op1);
    }

    public Response decode(DatagramPacket p) throws IOException {
        ByteArrayInputStream payload = new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
        return decode(payload);
    }
}
