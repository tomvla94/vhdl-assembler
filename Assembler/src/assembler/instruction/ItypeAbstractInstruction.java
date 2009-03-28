package assembler.instruction;

/**
 *
 * @author Billy Watson
 */
public abstract class ItypeAbstractInstruction extends HasRsAndRt {
    protected String immediate;

    public ItypeAbstractInstruction() {}

    public ItypeAbstractInstruction(String rs, String immediate) {
        setRs(rs);
        setImmediate(immediate);
    }

    public ItypeAbstractInstruction(String rs, String rt, String immediate) {
        setRs(rs);
        setRt(rt);
        setImmediate(immediate);
    }

    public String getImmediate() {
        return immediate;
    }

    public void setImmediate(String immediate) {
        this.immediate = immediate;
    }

    public String getBinary() {
        return getOpcode() + getRs() + getRt() + getImmediate();
    }
}
