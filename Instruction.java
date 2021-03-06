import java.util.HashMap;

public abstract class Instruction {
    String instructionName;
    String opcode;

    Instruction(String _instructionName, String _opcode) {
        this.instructionName = _instructionName;
        this.opcode = _opcode;
    }

    public String getOpcode()
    {
        return(this.opcode);
    }

    public String getName()
    {
        return this.instructionName;
    }

    abstract String getMachineCode(String registersOrConstants, HashMap<String,String> registerNameToBinaryMap);
}