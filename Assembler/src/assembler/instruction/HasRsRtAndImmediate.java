/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assembler.instruction;

/**
 *
 * @author Billy Watson
 */
public abstract class HasRsRtAndImmediate extends HasRsAndRt {
    private String immediate;

    public String getImmediate() {
        return immediate;
    }

    public void setImmediate(String immediate) {
        this.immediate = immediate;
    }
}
