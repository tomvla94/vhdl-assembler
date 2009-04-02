package assembler.instruction;

/**
 *
 * @author Billy Watson
 */
public class SubtractInstruction extends RtypeInstruction {
    public SubtractInstruction(String rs, String rt, String rd) {
        super(rs, rt, rd);
        setFunctionCode("000001");
        setFunction("sub");
    }
}
