package assembler;

import assembler.assembly.Assembler;
import assembler.exception.InitializationException;
import assembler.instruction.HasRsRtAndImmediate;
import assembler.instruction.Instruction;
import assembler.optimize.Optimizer;
import assembler.symbolize.Symbolizer;
import java.io.File;
import java.rmi.AccessException;
import java.util.Iterator;
import java.util.Vector;

/**
 * AssemblerMain kicks the assembly process off with its main method
 * @author Billy Watson
 */
public class AssemblerMain {
    static String OUTPUT_FILE_NAME = "out.txt";
    final static String USAGE_INSTRUCTIONS = "You must instantiate this project " +
            "in one of the following manners: \n" +
            "AssemblerMain inputFile\n" +
            "AssemblerMain inputFile outputFile\n";

    /**
     * Can start using one of two formats:
     * <i>inputFilename</i> (output file will be out.txt)
     * <i>inputFilename</i> <i>outputFilename</i>
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        final int length = args.length;

        if (length > 2) {
                throw new InitializationException("You must not pass in more than" +
                        "two arguments. " + USAGE_INSTRUCTIONS);
        } else if(length > 0) {
            //main work of this class is done here
            if(length == 2)
                OUTPUT_FILE_NAME = args[1];

            File inputFile = new File(args[0]);
            File outputFile = new File(OUTPUT_FILE_NAME);
            Symbolizer symbolizer = new Symbolizer();
            Assembler assembler = new Assembler(inputFile);

            Vector<Instruction> instructions = assembler.work();

            Optimizer optimizer = new Optimizer(instructions);
            instructions = optimizer.work();

            //put labels into a new symbols table for lookup after optimization
            Iterator<Instruction> iter1 = instructions.iterator();
            int i=0;
            while(iter1.hasNext()) {
                Instruction instr = iter1.next();

                if(instr.getLabel() != null) {
                    String label = instr.getLabel();
                    symbolizer.insert(label, i);
                }
                i++;
            }

            //lookup symbols, if appropriate
            Iterator<Instruction> iter2 = instructions.iterator();
            i=0;
            while(iter2.hasNext()) {
                Instruction instr = iter2.next();
                String function = instr.getFunction();

                if(function.equals("jump") ||
                        function.equals("bne") ||
                        function.equals("beq")/* && addr != null*/) {
                    //could possibly have a symbol, so replace immediate if a symbol exists
                    HasRsRtAndImmediate instrWithImmediate = (HasRsRtAndImmediate) instr;
                    int lineNumOfSymbol = symbolizer.lookup(instrWithImmediate.getImmediate());
                    int address = 256 - (i - lineNumOfSymbol);//requires a 0-based line numbering to be accurate
                    System.out.println("**Setting immediate value to " + address);
                    instrWithImmediate.setImmediate(Integer.toString(address));
                }

                i++;
            }

            outputFile.createNewFile(); //only creates if it doesn't exist
            if(outputFile.canWrite()) {
                assembler.writeBinaryToFile(outputFile, instructions, inputFile);

                System.out.println("Assembly complete. You may now check " +
                        OUTPUT_FILE_NAME + " for your binary code");
            } else {
                throw new AccessException("Cannot write to specified output file");
            }

        } else {
            throw new InitializationException("You must pass at least the " +
                    "input file name. " + USAGE_INSTRUCTIONS);
        }
    }

}
