package assembler.instruction;

/**
 *
 * @author Billy Watson
 */
public class AndInstruction extends RtypeInstruction {
    public AndInstruction(String rs, String rt, String rd) {
        super(rs, rt, rd);
        setFunctionCode("000100");
        setFunction("and");
    }
}
