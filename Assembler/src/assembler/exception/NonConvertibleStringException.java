/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assembler.exception;

/**
 *
 * @author Billy Watson
 */
public class NonConvertibleStringException extends Exception {
    /**
     * Constructs an instance of <code>NonConvertibleStringException</code> with the specified detail message.
     * @param str the string that could not be converted into binary.
     */
    public NonConvertibleStringException(String str) {
        super("The string "+ str +" cannot be converted into binary.");
    }
}
