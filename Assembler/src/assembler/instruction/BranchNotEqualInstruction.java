package assembler.instruction;

/**
 *
 * @author Billy Watson
 */
public class BranchNotEqualInstruction extends ItypeAbstractInstruction {
    public BranchNotEqualInstruction(String rs, String rt, String immediate) {
        super(rs, rt, immediate);
        setOpcode("000100");
        setFunction("bne");
    }
}
