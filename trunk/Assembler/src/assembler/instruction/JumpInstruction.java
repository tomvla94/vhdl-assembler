package assembler.instruction;

import assembler.exception.NonConvertibleStringException;

/**
 *
 * @author Billy Watson
 */
public class JumpInstruction extends HasRsRtAndImmediate {
    public JumpInstruction(String address) {
        logger.debug("Constructing a jump instruction with jump address of " + address);
        setOpcode("000101");
        setFunction("jump");
        setImmediate(address);
    }

    public String getBinary() throws NonConvertibleStringException {
        return getOpcode() + asBinary(getImmediate(),26);
    }
}
