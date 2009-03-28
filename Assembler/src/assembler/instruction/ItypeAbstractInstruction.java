package assembler.instruction;

import assembler.exception.NonConvertibleStringException;

/**
 *
 * @author Billy Watson
 */
public abstract class ItypeAbstractInstruction extends HasRsAndRt {
    protected String immediate;

    public ItypeAbstractInstruction() {}

    public ItypeAbstractInstruction(String rt, String immediate) {
        setRs("00000");
        setRt(rt);
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

    public String getBinary() throws NonConvertibleStringException {
        return getOpcode() + asBinary(getRs(), 5) + asBinary(getRt(), 5) + asBinary(getImmediate(), 16);
    }
}
