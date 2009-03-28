package assembler.instruction;

/**
 *
 * @author Billy Watson
 */
public class AddInstruction extends RtypeAbstractInstruction {
    public AddInstruction(String rs, String rt, String rd) {
        super(rs, rt, rd);
        setFunctionCode("000000");
        setFunction("add");
    }
}
