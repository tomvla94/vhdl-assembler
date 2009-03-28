package assembler.instruction;

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
    protected String label;
    protected String opcode = "000000";
    protected String function = "";

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

    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Returns the binary of the instruction
     *
     * @return
     */
    public abstract String getBinary();
}
