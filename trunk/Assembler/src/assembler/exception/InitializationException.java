package assembler.exception;

/**
 * Indicates that the initialization of the application did not go as planned.
 * Most likely thrown when a user passes the wrong number of command line args.
 * @author Billy Watson
 */
public class InitializationException extends Exception {

    /**
     * Creates a new instance of <code>InitializationException</code> without
     * detail message.
     */
    public InitializationException() {}


    /**
     * Constructs an instance of <code>InitializationException</code> with the 
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InitializationException(String msg) {
        super(msg);
    }
}
