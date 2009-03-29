/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assembler.exception;

/**
 *
 * @author Billy Watson
 */
public class UnrecognizedInstructionException extends Exception {
    /**
     * Constructs an instance of <code>UnrecognizedInstructionException</code> with the specified detail message.
     * @param msg the unrecognized instruction
     */
    public UnrecognizedInstructionException(String msg) {
        super("The instruction " + msg + " was not recognized.");
    }
}
