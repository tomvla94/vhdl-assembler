package assembler.instruction;

/**
 *
 * @author Billy Watson
 */
public abstract class RtypeAbstractInstruction extends HasRsAndRt {
    private String rd;
    private String funct;
    private String shamt = "00000";

    public RtypeAbstractInstruction(){}

    public RtypeAbstractInstruction(String rs, String rt, String rd) {
        setRs(rs);
        setRt(rt);
        setRd(rd);
    }

    public String getRd() {
        return rd;
    }

    public void setRd(String rd) {
        this.rd = rd;
    }

    public String getFunctionCode() {
        return funct;
    }

    public void setFunctionCode(String funct) {
        this.funct = funct;
    }

    public String getShamt() {
        return shamt;
    }

    public void setShamt(String shamt) {
        this.shamt = shamt;
    }

    public String getBinary() {
        return getOpcode() + getRs() + getRt() + getRd() + getShamt() + getFunctionCode();
    }
}
