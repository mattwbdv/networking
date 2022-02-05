public class Response {
    public int reqLength;
    public int id;
    public int opCode;
    public int numOperands;
    public int op1;
    public int op2;
    public double answer;

    // instantiating with answer for response
    public Response(int reqLength, int id, int opCode, int numOperands, int op1, int op2) {
        this.reqLength = reqLength;
        this.id = id;
        this.opCode = opCode;
        this.numOperands = numOperands;
        this.op1 = op1;
        this.op2 = op2;
        this.answer = getAnswer2(opCode, op1, op2);
    }

    // instantiating with answer for response
    public Response(int reqLength, int id, int opCode, int numOperands, int op1) {
        this.reqLength = reqLength;
        this.id = id;
        this.opCode = opCode;
        this.numOperands = numOperands;
        this.op1 = op1;
        this.answer = getAnswer(op1);
    }

    public double getAnswer2(int opCode, int op1, int op2) {
        double finalAns = 0.0;
        if (opCode == 0) {
            finalAns = addition(op1, op2);
        } else if (opCode == 1) {
            finalAns = subtraction(op1, op2);
        } else if (opCode == 2) {
            finalAns = op1 | op2;
        } else if (opCode == 3) {
            finalAns = op1 & op2;
        } else if (opCode == 4) {
            finalAns = op1 >> op2;
        } else {
            finalAns = op1 >> op2;
        }
        return finalAns;
    }

    public double getAnswer(int op1) {
        return ~this.op1;
    }

    public static int addition(int x, int y) {
        int carry;
        while (y != 0) {
            carry = x & y;
            x = x ^ y;
            y = carry << 1;
        }
        return x;
    }

    public static int subtraction(int x, int y) {
        while (y != 0) {
            int borrow = (~x) & y;
            x = x ^ y;
            y = borrow << 1;
        }
        return x;
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
        finalHex += Double.toHexString(answer);
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
                "Operator two = " + op2 + EOLN +
                "Final answer = " + answer + EOLN;
        return value;
    }

}
