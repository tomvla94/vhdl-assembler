package assembler.instruction;

/**
 *
 * @author Billy Watson
 */
public class SetLessThanInstruction extends RtypeAbstractInstruction {
    public SetLessThanInstruction(String rs, String rt, String rd) {
        super(rs, rt, rd);
        setFunctionCode("000010");
        setFunction("slt");
    }
}
