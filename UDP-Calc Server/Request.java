public class Request {
    public int reqLength;
    public int id;
    public int opCode;
    public int numOperands;
    public int op1;
    public int op2;

    public Request(int reqLength, int id, int opCode, int numOperands, int op1, int op2) {
        this.reqLength = reqLength;
        this.id = id;
        this.opCode = opCode;
        this.numOperands = numOperands;
        this.op1 = op1;
        this.op2 = op2;
    }

    public Request(int reqLength, int id, int opCode, int numOperands, int op1) {
        this.reqLength = reqLength;
        this.id = id;
        this.opCode = opCode;
        this.numOperands = numOperands;
        this.op1 = op1;
    }

    public String decToHex() {
        String finalHex = "";
        finalHex += Integer.toHexString(reqLength);
        finalHex += Integer.toHexString(id);
        finalHex += Integer.toHexString(opCode);
        finalHex += Integer.toHexString(numOperands);
        finalHex += Integer.toHexString(op1);
        if (numOperands > 1) {
            finalHex += Integer.toHexString(op2);
        }
        return finalHex;
    }

    public String toString() {
        String hex = decToHex();
        final String EOLN = java.lang.System.getProperty("line.separator");
        String value = "The request, in hex is: 0x" + hex + EOLN +
                "The message has a byte length of: " + reqLength + EOLN +
                "Request ID = " + id + EOLN +
                "Operation code = " + opCode + EOLN +
                "Number of operands  = " + numOperands + EOLN +
                "Operator one = " + op1 + EOLN +
                "Operator two = " + op2 + EOLN;
        return value;
    }

}
