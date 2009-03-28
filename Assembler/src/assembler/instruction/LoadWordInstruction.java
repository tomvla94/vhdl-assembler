package assembler.instruction;

/**
 *
 * @author Billy Watson
 */
public class LoadWordInstruction extends ItypeAbstractInstruction {

    public LoadWordInstruction(String rt, String immediate) {
        super(rt,immediate);
        setOpcode("000001");
        setFunction("lw");
    }


    public LoadWordInstruction(String rs, String rt, String immediate) {
        super(rs, rt, immediate);
        setOpcode("000001");
        setFunction("lw");
    }
}
