package assembler.instruction;

/**
 *
 * @author Billy Watson
 */
public class LoadUpperImmediateInstruction extends ItypeAbstractInstruction  {
    public LoadUpperImmediateInstruction(String rs, String immediate) {
        setRs(rs);
        setImmediate(immediate);
        setFunction("lui");
        setOpcode("000110");
    }

    /**
     * @override Overrides the getBinary function of the Itype abstract class.
     * Load upper immediate does not have an RT value so the proper thing to do
     * is to pad the RS with leading 0's. 
     * @return
     */
    @Override
    public String getBinary() {
        return opcode + "00000" + getRs() + getImmediate();
    }
}
