package assembler.symbolize;

import assembler.exception.SymbolNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author Billy Watson
 */
public class Symbolizer {
    Map<String, Integer> symbols = new HashMap<String, Integer>();
    Map<Integer, String> symbolsAtLine = new HashMap<Integer, String>();
    Logger logger = Logger.getLogger(Symbolizer.class);
    /**
     * 
     * @param label
     * @param line
     */
    public void insert(String label, int line) {
        logger.debug("Inserting symbol " + label + " found at line " + line);
        symbols.put(label, line);
        symbolsAtLine.put(line, label);
    }

    /**
     * Returns the address (line number) of the specified symbol declaration
     *
     * @param {String} label
     */
    public Integer lookup(String label) throws SymbolNotFoundException {
        logger.debug("Looking for symbol " + label);
        if(symbols.containsKey(label)) {
            Integer line = symbols.get(label);
            logger.debug("Symbol found at " + line);
            return line;
        } else {
            throw new SymbolNotFoundException(label);
        }
    }

    /**
     * Looks for a label at a line number.
     * @param lineNumber
     * @return null if no label found, otherwise returns the label
     */
    public String lookup(int lineNumber) {
        String label = null;
        logger.debug("Looking for a symbol on line " + lineNumber);
        if(symbolsAtLine.containsKey(lineNumber)) {
            label = symbolsAtLine.get(lineNumber);
            logger.debug("Label found at " + lineNumber + " : " + label);
        } else {
            logger.debug("No symbol found at line number " + lineNumber);
        }

        return label;
    }
}
