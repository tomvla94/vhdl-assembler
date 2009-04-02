package assembler.instruction;

/**
 *
 * @author Billy Watson
 */
public class LoadUpperImmediateInstruction extends ItypeInstruction  {
    public LoadUpperImmediateInstruction(String rt, String immediate) {
        super(rt,immediate);
        setFunction("lui");
        setOpcode("000110");
    }
}
