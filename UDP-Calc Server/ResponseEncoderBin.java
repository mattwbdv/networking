import java.io.*; // for ByteArrayOutputStream and DataOutputStream

public class ResponseEncoderBin implements ResponseEncoder, ResponseBinConst {

    private String encoding; // Character encoding

    public ResponseEncoderBin() {
        encoding = DEFAULT_ENCODING;
    }

    public ResponseEncoderBin(String encoding) {
        this.encoding = encoding;
    }

    public byte[] encode(Response response) throws Exception {

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(buf);
        out.writeInt(response.reqLength);
        out.writeInt(response.id);
        out.writeInt(response.opCode);
        out.writeInt(response.numOperands);
        out.writeInt(response.op1);
        if (response.numOperands > 1) {
            out.writeInt(response.op2);
        }
        out.writeDouble(response.answer);
        out.flush();
        return buf.toByteArray();
    }
}
