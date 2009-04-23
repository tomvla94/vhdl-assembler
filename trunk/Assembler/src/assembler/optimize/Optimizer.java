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
        int size = instructions.size();

        while(iter.hasNext()) {
            String rd = ""; //a primary instruction's rd value

            System.out.println("\n\n");
            logger.debug("optimizer loop iteration " + count);

            Instruction[] inst = new Instruction[4];
            Instruction current = iter.next();
            final String fnctn = current.getFunction();

            optimizedInstructions.add(current);
            count++;

            if(fnctn.equals("nop")) {
                continue;
            }

            int lookAheadCount;
            try {
                lookAheadCount = (4 - size + count < 0) ? 4 : size - count;
                logger.debug("Look " + lookAheadCount + " size " + size + " count " + count);
                for(int n=0; n<lookAheadCount; n++) {
                    inst[n] = instructions.get(count + n);
                }
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
                continue;
            } /*else if(fnctn.equals("add") || fnctn.equals("sub") ||
                fnctn.equals("or") || fnctn.equals("and") ||fnctn.equals("slt")) {
                RtypeInstruction i = (RtypeInstruction) current;
                rd = i.getRd();
            } */else if(fnctn.equals("lw") || fnctn.equals("lui")) {
                HasRsAndRt i = (HasRsAndRt) current;
                rd = i.getRt();
            } else {
                continue;
            }

            for(int x = 0; x < lookAheadCount; x++) {
                int numr = 0; //which optimization to perform
                final String functn = inst[x].getFunction();
                String nrs = ""; //a secondary instruction's rs value
                String nrt = ""; //a secondary instruction's rt value

                logger.debug("Instruction function (inner loop) is [" + functn + "]");
                if(functn.equals("nop")) {
                    continue;
                }
                //taken out due to optimizations in the processor
                /*if(functn.equals("add") || functn.equals("sub") ||
                        functn.equals("or") || functn.equals("and") || functn.equals("slt")) {
                    numr = 3;
                    RtypeInstruction i = (RtypeInstruction) inst[x];
                    nrs = i.getRs();
                    nrt = i.getRt();
                } else*/ if(functn.equals("bne") || functn.equals("beq")) {
                    numr = 2;
                    ItypeInstruction i = (ItypeInstruction) inst[x];
                    nrs = i.getRs();
                    nrt = i.getRt();
                } else if(functn.equals("lw") || functn.equals("sw") || functn.equals("lui")) {
                    numr = 1;
                    ItypeInstruction i = (ItypeInstruction) inst[x];
                    nrt = i.getRt();
                    
                } /* else if(functn.equals("jump")) {
                    numr = 0;
                }*/
                logger.debug("outer function is " + fnctn + " and inner function is " + functn);
                logger.debug("outer rd is [" + rd + "] and inner rs is [" + nrs + "] and inner rt is [" + nrt + "]");

                if(rd.startsWith("$")) { //if we're not specifying a register, no need to insert no-ops
                    switch(numr) {
                        case 3:
                            logger.debug("Inserting No no-ops");
                            //taken out due to the optimization in the processor
                            /*if(rd.equals(nrs) || rd.equals(nrt)) {
                                for(int y = 0; y < 4-x; y++) {
                                    optimizedInstructions.add(new NopInstruction());
                                }
                            }*/
                            break;
                        case 2:
                            if(rd.equals(nrs) || rd.equals(nrt)) {
                                logger.debug("Inserting " + (4-x) + " no ops");
                                for(int y = 0; y < 4-x; y++) {
                                    optimizedInstructions.add(new NopInstruction());
                                }
                            }
                            break;
                        case 1:
                            if(rd.equals(nrt)) {
                                logger.debug("Inserting " + (4-x) + " no ops");
                                for(int y = 0; y < 4-x; y++) {
                                    optimizedInstructions.add(new NopInstruction());
                                }
                            }
                            break;
                        default:
                            logger.debug("Inserting No no-ops");
                            break;
                    }//end switch
                }
            }//end for
        }//end while

        return optimizedInstructions;
    }//end work
}//end class