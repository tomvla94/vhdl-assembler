package assembler.instruction;

/**
 *
 * @author Billy Watson
 */
public class OrInstruction extends RtypeInstruction {
    public OrInstruction(String rs, String rt, String rd) {
        super(rs, rt, rd);
        setFunctionCode("000011");
        setFunction("or");
    }
}
