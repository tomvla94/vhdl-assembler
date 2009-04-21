package assembler.assembly;

import assembler.exception.UnrecognizedInstructionException;
import assembler.instruction.AddInstruction;
import assembler.instruction.AndInstruction;
import assembler.instruction.BranchNotEqualInstruction;
import assembler.instruction.BranchOnEqualInstruction;
import assembler.instruction.HasRsRtAndImmediate;
import assembler.instruction.Instruction;
import assembler.instruction.JumpInstruction;
import assembler.instruction.LoadUpperImmediateInstruction;
import assembler.instruction.LoadWordInstruction;
import assembler.instruction.NopInstruction;
import assembler.instruction.OrInstruction;
import assembler.instruction.SetLessThanInstruction;
import assembler.instruction.StoreWordInstruction;
import assembler.instruction.SubtractInstruction;
import assembler.symbolize.Symbolizer;
import java.io.File;
import java.io.FileWriter;
import java.security.InvalidParameterException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 * Assembler does all the nasty work of assembling a language into
 * binary code.
 * @author Billy Watson, Joe Jeffers
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

    public Vector<Instruction> work() throws Exception {
        Vector<Instruction> instructions = new Vector<Instruction>(5,10);
        Symbolizer symbolizer = new Symbolizer();

        String instructionWord;
        String labelWord = null;

        Scanner fileScanner = new Scanner(inputFile);

        logger.info("Starting work of assembly");

        // -- first pass, just to pull out all label positions and place in symbols table
        int i = 0;
        while(fileScanner.hasNext()){
            String s = new String(fileScanner.nextLine());
            Scanner lineScanner = new Scanner(s);

            int lineNumber = lineScanner.nextInt(); //move pointer past line #; won't use line number later
            String word = new String(lineScanner.next());
            int end = word.length() - 1;

            if(word.charAt(end) == ':') {
                labelWord = word.substring(0, end);
                logger.debug("Encountered a label " + labelWord);
                symbolizer.insert(labelWord, i);
            }
            i++;
        }

        // -- second pass, does all the grunt work and looks up symbols in the symbols table
        fileScanner = new Scanner(inputFile); //re-initialize scanner for second pass
        i=0;
        while(fileScanner.hasNext()){
            String s = new String(fileScanner.nextLine());
            Scanner lineScanner = new Scanner(s);

            int lineNumber = lineScanner.nextInt();
            String word = new String(lineScanner.next());

            int end = word.length();

            logger.debug("parsing line " + lineNumber);

            if(word.charAt(end - 1) == ':'){
                instructionWord = new String(lineScanner.next());
            } else {
                instructionWord = word;
            }
            instructionWord = instructionWord.toLowerCase();
            logger.debug("Encountered an instruction " + instructionWord);

            Instruction instr = null; //creating an instruction to add
                                              //the instruction vector
            String rs = null; //destination register
            String rt = null; //read register
            String rd = null; //read register
            String imm = null; //immediate value
            String addr = null; //address

            //comparing the instruction word with a list of MIPS instructions
            if(instructionWord.equals("add")){
                rd = lineScanner.next();
                rs = lineScanner.next();
                rt = lineScanner.next();

                instr = new AddInstruction(rs, rt, rd);
            }
            else if(instructionWord.equals("and")){
                rd = lineScanner.next();
                rs = lineScanner.next();
                rt = lineScanner.next();

                instr = new AndInstruction(rs, rt, rd);
            }
            else if(instructionWord.equals("bne")){
                rs = lineScanner.next();
                rt = lineScanner.next();
                addr = lineScanner.next();

                instr = new BranchNotEqualInstruction(rs, rt, addr);
            }
            else if(instructionWord.equals("beq")){
                rs = lineScanner.next();
                rt = lineScanner.next();
                addr = lineScanner.next();

                instr = new BranchOnEqualInstruction(rs, rt, addr);
            }
            else if(instructionWord.equals("jump")){
                addr = lineScanner.next();

                instr = new JumpInstruction(addr);
            }
            else if(instructionWord.equals("lui")){
                rt = lineScanner.next();
                imm = lineScanner.next();

                instr = new LoadUpperImmediateInstruction(rt, imm);
            }
            else if(instructionWord.equals("lw")){
                rt = lineScanner.next();
                addr = lineScanner.next();

                instr = new LoadWordInstruction(rt, addr);
            }
            else if(instructionWord.equals("nop")){
                instr = new NopInstruction();
            }
            else if(instructionWord.equals("or")){
                rd = lineScanner.next();
                rs = lineScanner.next();
                rt = lineScanner.next();

                instr = new OrInstruction(rs, rt, rd);
            }
            else if(instructionWord.equals("slt")){
                rd = lineScanner.next();
                rs = lineScanner.next();
                rt = lineScanner.next();

                instr = new SetLessThanInstruction(rs, rt, rd);
            }
            else if(instructionWord.equals("sw")){
                rs = lineScanner.next();
                addr = lineScanner.next();
                
                instr = new StoreWordInstruction(rs, addr);
            }
            else if(instructionWord.equals("sub")){
                rd = lineScanner.next();
                rs = lineScanner.next();
                rt = lineScanner.next();

                instr = new SubtractInstruction(rs, rt, rd);
            } else {
                throw new UnrecognizedInstructionException(instructionWord);
            }


            String labelAtLine = symbolizer.lookup(i);
            instr.setLabel(labelAtLine);
            instructions.add(instr);
            i++;
        }

        logger.debug("Finished parsing file");
        return instructions;
    }

	 public void writeBinaryToFile(File out, Vector<Instruction> v, File in) throws Exception {
	 	  FileWriter output = new FileWriter(out);

          Scanner input = new Scanner(in);
          Scanner binary = new Scanner(new File("/Users/williamwatson/desktop/properoutput.txt"));
          output.write("MEMORY_INITIALIZATION_RADIX=2;\n");
          output.write("MEMORY_INITIALIZATION_VECTOR=\n");
          try {
              Iterator<Instruction> iter = v.iterator();
              int ct = 0;
              while(iter.hasNext()) {
                  Instruction instr = iter.next();
                  output.write(instr.getBinary());
                  /* don't write a new line and a comma unless we have more
                        of a memory vector to write to the file */
                  if(iter.hasNext()) {
                      output.write(",");
                  } else {
                      //output.write(";");
                  }
                  output.write("\n");
                  if(binary.hasNext()) {
                      //output.write(binary.nextLine() + "\n");
                      //output.write(input.nextLine() + "\n");
                  }
                  //output.write(" " + (ct++) + "\n");
              }
          } catch (Exception e) {
              e.printStackTrace();
              logger.error("An error has occurred while printing the binary to file " + e.getMessage());
          } finally {
              output.close();
          }
	 }
}
