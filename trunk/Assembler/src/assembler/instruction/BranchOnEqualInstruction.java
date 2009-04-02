package assembler.instruction;

/**
 *
 * @author Billy Watson
 */
public class BranchOnEqualInstruction extends ItypeInstruction {
    public BranchOnEqualInstruction(String rs, String rt, String immediate) {
        super(rs, rt, immediate);
        setOpcode("000011");
        setFunction("beq");
    }
}
