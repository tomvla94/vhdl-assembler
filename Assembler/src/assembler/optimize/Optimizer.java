package assembler.optimize;
import assembler.instruction.HasRsAndRt;
import assembler.instruction.Instruction;
import assembler.instruction.ItypeInstruction;
import assembler.instruction.LoadUpperImmediateInstruction;
import assembler.instruction.NopInstruction;
import assembler.instruction.RtypeInstruction;
import java.util.Vector;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author Jonathan Paik
 * @author William Watson
 */
public class Optimizer {

    Vector<Instruction> instructions;

    Logger logger = Logger.getLogger(Optimizer.class);

    public Optimizer(Vector<Instruction> v) {
        this.instructions = v;
    }

    public Vector<Instruction> work() throws Exception {
        Vector<Instruction> optimizedInstructions = new Vector<Instruction>(100,10);
        Iterator<Instruction> iter = instructions.iterator();
        int count = 0;
        String rd = "";
        String nrs = "";
        String nrt = "";
        int numr = 0;

        while(iter.hasNext()) {
            logger.debug("optimizer loop iteration " + count);

            Instruction[] inst = new Instruction[5];
            inst[0] = iter.next();
            final String fnctn = inst[0].getFunction();

            logger.debug("Instruction function (outer loop) is [" + fnctn + "]");
            optimizedInstructions.add(inst[0]);
            count++;

            if(fnctn.equals("nop")) {
                continue;
            }

            try {
                inst[1] = instructions.get(count);
                inst[2] = instructions.get(count+1);
                inst[3] = instructions.get(count+2);
                inst[4] = instructions.get(count+3);
            }
            catch(ArrayIndexOutOfBoundsException e) {
                logger.error(e);
                continue;
            }

            if(fnctn.equals("bne") || fnctn.equals("beq") || fnctn.equals("jump")) {
                optimizedInstructions.add(new NopInstruction());
                optimizedInstructions.add(new NopInstruction());
                optimizedInstructions.add(new NopInstruction());
                optimizedInstructions.add(new NopInstruction());
            } else if(fnctn.equals("add") || fnctn.equals("sub") ||
                    fnctn.equals("or") || fnctn.equals("and") ||fnctn.equals("slt")) {
                RtypeInstruction i = (RtypeInstruction) inst[0];
                rd = i.getRd();
            } else if(fnctn.equals("lw")) {
                HasRsAndRt i = (HasRsAndRt) inst[0];
                rd = i.getRt();
            } else if(fnctn.equals("lui")) {
                LoadUpperImmediateInstruction i = (LoadUpperImmediateInstruction) inst[0];
                rd = i.getRt();
            }

            for(int x = 1; x <= 4; x++) {
                final String functn = inst[x].getFunction();
                logger.debug("Instruction function (inner loop) is [" + functn + "]");
                if(functn.equals("nop")) {
                    continue;
                }
                if(functn.equals("add") || functn.equals("sub") ||
                        functn.equals("or") || functn.equals("and") || functn.equals("slt")) {
                    numr = 3;
                    RtypeInstruction i = (RtypeInstruction) inst[x];
                    nrs = i.getRs();
                    nrt = i.getRt();
                } else if(functn.equals("bne") || functn.equals("beq")) {
                    numr = 2;
                    ItypeInstruction i = (ItypeInstruction) inst[x];
                    nrs = i.getRs();
                    nrt = i.getRt();
                } else if(functn.equals("lw") || functn.equals("sw")) {
                    numr = 1;
                    ItypeInstruction i = (ItypeInstruction) inst[x];
                    nrt = i.getRt();
                } else if(functn.equals("lui")) {
                    numr = 1;
                    LoadUpperImmediateInstruction i = (LoadUpperImmediateInstruction) inst[x];
                    nrt = i.getRt();
                } else if(functn.equals("jump")) {
                    numr = 0;
                }
                    switch(numr) {
                        case 3:
                        /*if(rd.equals(nrs) || rd.equals(nrt)) {
                            for(int y = 0; y < 5-x; y++) {
                                optimizedInstructions.add(new NopInstruction());
                            }
                        }*/
                        break;
                        case 2:
                        if(rd.equals(nrs) || rd.equals(nrt)) {
                            for(int y = 0; y < 5-x; y++) {
                                optimizedInstructions.add(new NopInstruction());
                            }
                        }
                        break;
                        case 1:
                        /*if(rd.equals(nrt)) {
                            for(int y = 0; y < 5-x; y++) {
                                optimizedInstructions.add(new NopInstruction());
                            }
                        }*/
                        break;
                        default:
                            logger.warn("NO NUMR JUMP? [" + numr + "]");
                            break;
                    }//end switch
            }//end for
        }//end while

        return optimizedInstructions;
    }//end work
}//end class