/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assembler.exception;

/**
 *
 * @author Billy Watson
 */
public class SymbolNotFoundException extends Exception {
    /**
     * Constructs an instance of <code>SymbolNotFoundException</code> with the specified detail message.
     * @param msg the symbol that wasn't found
     */
    public SymbolNotFoundException(String msg) {
        super("The symbol cannot be found in the symbols table " + msg);
    }
}
