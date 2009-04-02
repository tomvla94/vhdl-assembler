package assembler.instruction;

/**
 *
 * @author Billy Watson
 */
public class NopInstruction extends Instruction {
    public NopInstruction() {
        setFunction("nop");
    }
    public String getBinary() {
        return "00011000000111110000001111111111";
    }
}