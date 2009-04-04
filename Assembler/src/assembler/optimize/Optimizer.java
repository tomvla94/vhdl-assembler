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
        String rs = "";
        String rt = "";
        String rd = "";
        String nrs = "";
        String nrt = "";
        String nrd = "";
        int numr = 0;
        int nnumr = 0;

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
                numr = 0;
            } else if(fnctn.equals("add") || fnctn.equals("sub") ||
                    fnctn.equals("or") || fnctn.equals("and") ||fnctn.equals("slt")) {
                numr = 3;
                RtypeInstruction i = (RtypeInstruction) inst[0];
                rs = i.getRs();
                rt = i.getRt();
                rd = i.getRd();
            } else if(fnctn.equals("lw") || fnctn.equals("sw")) {
                numr = 2;
                HasRsAndRt i = (HasRsAndRt) inst[0];
                rs = i.getRs();
                rt = i.getRt();
            } else if(fnctn.equals("lui")) {
                numr = 1;
                LoadUpperImmediateInstruction i = (LoadUpperImmediateInstruction) inst[0];
                rs = i.getRs();
            }

            for(int x = 1; x <= 4; x++) {
                final String functn = inst[x].getFunction();
                logger.debug("Instruction function (inner loop) is [" + functn + "]");
                if(functn.equals("nop")) {
                    continue;
                }
                if(functn.equals("add") || functn.equals("sub") ||
                        functn.equals("or") || functn.equals("and") || functn.equals("slt")) {
                    nnumr = 3;
                    RtypeInstruction i = (RtypeInstruction) inst[x];
                    nrs = i.getRs();
                    nrt = i.getRt();
                    nrd = i.getRd();
                } else if(functn.equals("lw") || functn.equals("sw") ||
                            functn.equals("bne") || functn.equals("beq")) {
                    nnumr = 2;
                    ItypeInstruction i = (ItypeInstruction) inst[x];
                    nrs = i.getRs();
                    nrt = i.getRt();
                } else if(functn.equals("lui")) {
                    nnumr = 1;
                    LoadUpperImmediateInstruction i = (LoadUpperImmediateInstruction) inst[x];
                    nrs = i.getRs();
                    nrs = i.getRt();
                } else if(functn.equals("jump")) {
                    nnumr = 0;
                }

                if(numr == 3) {
                    switch(nnumr) {
                        case 3:
                        if(rs.equals(nrs) || rs.equals(nrt) || rs.equals(nrd) || rt.equals(nrs) || rt.equals(nrt) || rt.equals(nrd) || rd.equals(nrs) || rd.equals(nrt) || rd.equals(nrd)) {
                            for(int y = 0; y < 5-x; y++) {
                                optimizedInstructions.add(new NopInstruction());
                            }
                        }
                        break;
                        case 2:
                        if(rs.equals(nrs) || rs.equals(nrt) || rt.equals(nrs) || rt.equals(nrt) || rd.equals(nrs) || rd.equals(nrt)) {
                            for(int y = 0; y < 5-x; y++) {
                                optimizedInstructions.add(new NopInstruction());
                            }
                        }
                        break;
                        case 1:
                        if(rs.equals(nrs) || rt.equals(nrs) || rd.equals(nrs)) {
                            for(int y = 0; y < 5-x; y++) {
                                optimizedInstructions.add(new NopInstruction());
                            }
                        }
                        break;
                    }//end switch
                }//end if
                else if(numr == 2) {
                    switch(nnumr) {
                        case 3:
                        if(rs.equals(nrs) || rs.equals(nrt) || rs.equals(nrd) || rt.equals(nrs) || rt.equals(nrt) || rt.equals(nrd)) {
                            for(int y = 0; y < 5-x; y++) {
                                optimizedInstructions.add(new NopInstruction());
                            }
                        }
                        break;
                        case 2:
                        if(rs.equals(nrs) || rs.equals(nrt)|| rt.equals(nrs) || rt.equals(nrt)) {
                            for(int y = 0; y < 5-x; y++) {
                                optimizedInstructions.add(new NopInstruction());
                            }
                        }
                        break;
                        case 1:
                        if(rs.equals(nrs) || rt.equals(nrs)) {
                            for(int y = 0; y < 5-x; y++) {
                                optimizedInstructions.add(new NopInstruction());
                            }
                        }
                        break;
                    }//end switch
                }//end if
                else if(numr == 1) {
                    switch(nnumr) {
                        case 3:
                        if(rs.equals(nrs) || rs.equals(nrt) || rs.equals(nrd)) {
                            for(int y = 0; y < 5-x; y++) {
                                optimizedInstructions.add(new NopInstruction());
                            }
                        }
                        break;
                        case 2:
                        if(rs.equals(nrs) || rs.equals(nrt)) {
                            for(int y = 0; y < 5-x; y++) {
                                optimizedInstructions.add(new NopInstruction());
                            }
                        }
                        break;
                        case 1:
                        if(rs.equals(nrs)) {
                            for(int y = 0; y < 5-x; y++) {
                                optimizedInstructions.add(new NopInstruction());
                            }
                        }
                        break;
                    }//end switch
                }//end if
            }//end for
        }//end while

        return optimizedInstructions;
    }//end work
}//end class