import java.util.HashMap;

public class IFormatInstruction extends Instruction 
{
    IFormatInstruction(String _instructionName, String _opcode)
    {
        super(_instructionName, _opcode);
    }

    public String getMachineCode(String line, HashMap<String, String> registerNameToBinaryMap)
    {
        String[] words = line.split(" ");

        if(this.instructionName.equals("addi") || this.instructionName.equals("andi") || this.instructionName.equals("ori") || this.instructionName.equals("beq") ||
            this.instructionName.equals("bne") || this.instructionName.equals("slti") || this.instructionName.equals("sltiu"))
        {
            String[] registersOrConstants = words[1].split(",");
            for (int i = 0; i < registersOrConstants.length; i++)
                registersOrConstants[i] = registersOrConstants[i].trim();

            String firstSourceRegister = registerNameToBinaryMap.get(registersOrConstants[1]);
            String secondSourceRegister = registerNameToBinaryMap.get(registersOrConstants[0]);

            String immediateValueOrOffset = Integer.toBinaryString(Integer.parseInt(registersOrConstants[2]));
            if(immediateValueOrOffset.length() < 16)
            {
                immediateValueOrOffset = ("0000000000000000").substring(immediateValueOrOffset.length())+immediateValueOrOffset;
            }

            String correspondingMachineCode = this.opcode + firstSourceRegister + secondSourceRegister + immediateValueOrOffset;
            return(correspondingMachineCode);
        }
        else if(this.instructionName.equals("lui"))
        {
            String[] registersOrConstants = words[1].split(",");
            String secondSourceRegister = registerNameToBinaryMap.get(registersOrConstants[0]);

            String immediateValue = Integer.toBinaryString(Integer.parseInt(registersOrConstants[1]));
            if(immediateValue.length() < 16)
            {
                immediateValue = ("0000000000000000").substring(immediateValue.length())+immediateValue;
            }

            String correspondingMachineCode = this.opcode + "00000" + secondSourceRegister + immediateValue;
            return(correspondingMachineCode);
        }
        else
        {
            String[] registersOrConstants = words[1].split(",");
            for (int i = 0; i < registersOrConstants.length; i++)
                registersOrConstants[i] = registersOrConstants[i].trim();
            
            String secondSourceRegister = registerNameToBinaryMap.get(registersOrConstants[0]);

            int indexOfOpeningBracket =registersOrConstants[1].indexOf("(");
            int indexOfClosingBracket =registersOrConstants[1].indexOf(")");

            String offset = registersOrConstants[1].substring(0, indexOfOpeningBracket);
            String firstSourceRegister = registerNameToBinaryMap.get(registersOrConstants[1].substring(indexOfOpeningBracket+1, indexOfClosingBracket));

            offset = Integer.toBinaryString(Integer.parseInt(offset));
            if(offset.length() < 16)
            {
                offset = ("0000000000000000").substring(offset.length())+offset;
            }

            String correspondingMachineCode = this.opcode + firstSourceRegister + secondSourceRegister + offset;
            return(correspondingMachineCode);
        }
    }
}
