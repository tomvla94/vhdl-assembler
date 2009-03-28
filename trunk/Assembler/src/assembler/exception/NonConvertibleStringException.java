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
     * Creates a new instance of <code>NonConvertibleStringException</code> without detail message.
     */
    public NonConvertibleStringException() {
        super("The string cannot be converted into binary.");
    }


    /**
     * Constructs an instance of <code>NonConvertibleStringException</code> with the specified detail message.
     * @param str the string that could not be converted into binary.
     */
    public NonConvertibleStringException(String str) {
        super("The string "+ str +" cannot be converted into binary.");
    }
}
