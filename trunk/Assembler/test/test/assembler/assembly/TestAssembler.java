package test.assembler.assembly;

import assembler.AssemblerMain;

/**
 *
 * @author Billy Watson
 */
public class TestAssembler {
    public TestAssembler(){}

    public static void main(String[] args) throws Exception{
        String[] argsArr = {
            "/Users/williamwatson/desktop/lab4code.txt",
            "/Users/williamwatson/desktop/out.txt"};

        AssemblerMain.main(argsArr);
    }
}
