package assembler.instruction;

/**
 *
 * @author Billy Watson
 */
public class JumpInstruction extends Instruction {
    String whereToJump;

    public JumpInstruction(String address) {
        setOpcode("000001");
        setFunction("j");
        this.whereToJump = address;
    }

    public String getBinary() {
        return getOpcode() + whereToJump;
    }
}
