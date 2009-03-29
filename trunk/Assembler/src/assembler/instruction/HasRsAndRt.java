/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assembler.instruction;

/**
 *
 * @author Billy Watson
 */
public abstract class HasRsAndRt extends Instruction {
    private String rs;
    private String rt;

    public String getRs() {
        return rs;
    }

    public String getRt() {
        return rt;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }
}
