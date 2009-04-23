package assembler.instruction;

import assembler.exception.NonConvertibleStringException;
import org.apache.log4j.Logger;

/**
 * Any assembly instruction will inherit from this interface. These
 * instructions will never actually do what they say, but they are simply
 * utility classes for converting an assembly instruction into binary.
 *
 * For instance, if a jmp instruction is encountered in a VHDL source file,
 * then the JumpInstr class will be instantiated with the jmp instruction's
 * parameters. Then an appropriate method will be called to convert that jmp
 * instruction into binary.
 *
 * Example:
 *
 * from source file: jmp #100
 *
 * In Java: new JumpInstr("jmp #100").getBinary()
 *
 * @author Billy Watson
 */
public abstract class Instruction {
    private String label;
    private String opcode = "000000";
    private String function = "";
    Logger logger = Logger.getLogger(Instruction.class);

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Returns the binary of the instruction
     *
     * @return
     */
    public abstract String getBinary() throws NonConvertibleStringException;

    /**
     * Builds binary from a given string, padding to the number of digits specified
     * @param nonBinaryString
     * @param digits
     * @return
     * @throws assembler.exception.NonConvertibleStringException
     */
    protected String asBinary(String nonBinaryString, int digits) throws NonConvertibleStringException {
        try {
            String decimalString = "";
            if(nonBinaryString.startsWith("$")) { // $ means register
                decimalString = nonBinaryString.substring(1);
            } else {
                decimalString = nonBinaryString;
            }
            long decimal = Long.parseLong(decimalString);
            //logger.debug("decimal number is " + decimal + " from " + nonBinaryString);

            String binary = Long.toBinaryString(decimal).trim();
            String paddedBinary = String.format("%1$#" + digits + "s", binary).replace(' ','0');
            //logger.debug("binary is " + paddedBinary + " from decimal " + decimal);
            return paddedBinary;
        } catch(Exception e) {
            e.printStackTrace();
            return "ERR";
            //throw new NonConvertibleStringException(nonBinaryString);
        }
    }
}
