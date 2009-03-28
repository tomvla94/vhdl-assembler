package assembler.assembly;

import assembler.instruction.AddInstruction;
import assembler.instruction.AndInstruction;
import assembler.instruction.BranchNotEqualInstruction;
import assembler.instruction.BranchOnEqualInstruction;
import assembler.instruction.Instruction;
import assembler.instruction.JumpInstruction;
import assembler.instruction.LoadUpperImmediateInstruction;
import assembler.instruction.LoadWordInstruction;
import assembler.instruction.NopInstruction;
import assembler.instruction.OrInstruction;
import assembler.instruction.SetLessThanInstruction;
import assembler.instruction.StoreWordInstruction;
import assembler.instruction.SubtractInstruction;
import assembler.symbolize.SymbolizerImpl;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 * Assembler does all the nasty work of assembling a language into
 * binary code.
 * @author Billy Watson
 */
public class Assembler {
     File inputFile;
     File outputFile;
     Logger logger = Logger.getLogger(Assembler.class);

    /**
     * Default constructor
     */
    public Assembler(){};

    /**
     * Constructs an Assembler and sets the inputFile
     * @param inputFile
     */
    public Assembler(File inputFile) throws InvalidParameterException {
        if(inputFile == null)
            throw new InvalidParameterException("inputFile cannot be null");

        this.inputFile = inputFile;
    }

    public Vector<Instruction> work() throws FileNotFoundException {
        Vector<Instruction> instructions = new Vector<Instruction>(5,10);
        SymbolizerImpl symbolizer = new SymbolizerImpl();

        String instructionWord;
        String labelWord;

        Scanner fileScanner = new Scanner(inputFile);

        logger.info("Starting work of assembly");
        while(fileScanner.hasNext()){
            String s = new String(fileScanner.nextLine());
            Scanner lineScanner = new Scanner(s);

            int lineNumber = lineScanner.nextInt();
            String word = new String(lineScanner.next());

            int end = word.length();

            logger.debug("parsing line " + lineNumber);

            if(word.charAt(end - 1) == ':'){
                labelWord = new String(word);
                logger.debug("Encountered a label " + labelWord);
                symbolizer.insert(labelWord, lineNumber);
                String word2 = new String(lineScanner.next());
                instructionWord = new String(word2);
            }
            else{
                instructionWord = new String(word);
                logger.debug("Encountered an instruction " + instructionWord);
            }

            Instruction instr = null; //creating an instruction to add
                                              //the instruction vector
            String rs; //destination register
            String rt; //read register
            String rd; //read register
            String imm; //immediate value
            String addr; //address

            //comparing the instruction word with a list of MIPS instructions
            if(instructionWord.equalsIgnoreCase("add")){
                rs = lineScanner.next();
                rt = lineScanner.next();
                rd = lineScanner.next();

                instr = new AddInstruction(rs, rt, rd);
            }
            else if(instructionWord.equalsIgnoreCase("and")){
                rs = lineScanner.next();
                rt = lineScanner.next();
                rd = lineScanner.next();

                instr = new AndInstruction(rs, rt, rd);

            }
            else if(instructionWord.equalsIgnoreCase("bne")){
                rs = lineScanner.next();
                rt = lineScanner.next();
                addr = lineScanner.next();

                instr = new BranchNotEqualInstruction(rs, rt, addr);

            }
            else if(instructionWord.equalsIgnoreCase("beq")){
                rs = lineScanner.next();
                rt = lineScanner.next();
                addr = lineScanner.next();

                instr = new BranchOnEqualInstruction(rs, rt, addr);

            }
            else if(instructionWord.equalsIgnoreCase("jump")){
                addr = lineScanner.next();

                instr = new JumpInstruction(addr);

            }
            else if(instructionWord.equalsIgnoreCase("lui")){
                rs = lineScanner.next();
                imm = lineScanner.next();

                instr = new LoadUpperImmediateInstruction(rs, imm);

            }
            else if(instructionWord.equalsIgnoreCase("lw")){
                rs = lineScanner.next();
                addr = lineScanner.next();

                instr = new LoadWordInstruction(rs, addr);
            }
            else if(instructionWord.equalsIgnoreCase("nop")){
                instr = new NopInstruction();

            }
            else if(instructionWord.equalsIgnoreCase("or")){
                rs = lineScanner.next();
                rt = lineScanner.next();
                rd = lineScanner.next();

                instr = new OrInstruction(rs, rt, rd);

            }
            else if(instructionWord.equalsIgnoreCase("slt")){
                rs = lineScanner.next();
                rt = lineScanner.next();
                rd = lineScanner.next();

                instr = new SetLessThanInstruction(rs, rt, rd);

            }
            else if(instructionWord.equalsIgnoreCase("sw")){
                rs = lineScanner.next();
                addr = lineScanner.next();
                logger.debug("constructing a store word instruction object with rs of " +
                        rs + " and addr of " + addr);

                instr = new StoreWordInstruction(rs, addr);

            }
            else if(instructionWord.equalsIgnoreCase("sub")){
                rs = lineScanner.next();
                rt = lineScanner.next();
                rd = lineScanner.next();

                instr = new SubtractInstruction(rs, rt, rd);

            }

            instructions.add(instr);
        }

        logger.debug("Finished parsing file");
        return instructions;
    }

    /**
     * Does the work and sets the inputFile
     *
     * @param inputFile
     * @return
     * @throws java.security.InvalidParameterException
     */
    public Vector<Instruction> work(File inputFile) throws InvalidParameterException, FileNotFoundException {
        if(inputFile == null)
            throw new InvalidParameterException("inputFile cannot be null");

        this.inputFile = inputFile;

        return work();
    }

	 public void writeBinaryToFile(File f, Vector<Instruction> v) throws Exception {

	 	  FileWriter output = new FileWriter(f);
          output.write("starting parsing\n\n");
          try {
              Iterator<Instruction> iter = v.iterator();
              while(iter.hasNext()) {
                  Instruction instr = iter.next();
                  output.write(instr.getBinary() + ",\n");
              }
          } catch (Exception e) {
              e.printStackTrace();
              logger.error("An error has occurred while printing the binary to file " + e.getMessage());
          } finally {
              output.close();
          }
	 }
}