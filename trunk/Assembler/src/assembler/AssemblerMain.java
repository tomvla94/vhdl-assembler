package assembler;

import assembler.assembly.Assembler;
import assembler.exception.InitializationException;
import assembler.instruction.Instruction;
import java.io.File;
import java.io.FileWriter;
import java.rmi.AccessException;
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
            Assembler assembler = new Assembler(inputFile);

            Vector<Instruction> instructions = assembler.work();

            outputFile.createNewFile(); //only creates if it doesn't exist
            if(outputFile.canWrite()) {
                assembler.writeBinaryToFile(outputFile, instructions);

                System.out.println("Assembly complete. You may now check the " +
                        "output file for your binary code");
            } else {
                throw new AccessException("Cannot write to specified output file");
            }

        } else {
            throw new InitializationException("You must pass at least the " +
                    "input file name. " + USAGE_INSTRUCTIONS);
        }
    }

}
