package assembler.instruction;

/**
 *
 * @author Billy Watson
 */
public class StoreWordInstruction extends ItypeAbstractInstruction {
    public StoreWordInstruction(String rs, String immediate) {
        super(rs, immediate);
        setOpcode("000010");
        setFunction("sw");
    }

    public StoreWordInstruction(String rs, String rt, String immediate) {
        super(rs, rt, immediate);
        setOpcode("000010");
        setFunction("sw");
    }
}
