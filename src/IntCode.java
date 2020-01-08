import java.util.ArrayList;
import java.util.List;

public class IntCode {
    private ArrayList<Long> program;
    private ArrayList<Long> currentProgram;
    private ArrayList<Long> outputs = new ArrayList<>();
    private int index=0;
    private boolean finished = false;
    private int relativeBase = 0;
    private int indexFirstNewOutput = 0;
    IntCode(ArrayList<Long> program){
        this.program = program;
        this.currentProgram = new ArrayList<>(program);
    }

    public ArrayList<Long> getOutputs() {
        return outputs;
    }
    public List<Long> getNewOutputs(){
        List<Long> newOutputs = outputs.subList(indexFirstNewOutput, outputs.size());
        indexFirstNewOutput = outputs.size();
        return newOutputs;
    }
    public Long getLastOutput() {
        return outputs.get(outputs.size()-1);
    }

    public ArrayList<Long> getProgram() {
        return program;
    }

    public ArrayList<Long> getCurrentProgram() {
        return currentProgram;
    }

    public boolean isFinished() {
        return finished;
    }
    public void reset(){
        this.currentProgram = new ArrayList<>(program);
        this.outputs.clear();
        this.index = 0;
        this.finished = false;
        this.relativeBase = 0;
        this.indexFirstNewOutput = 0;
    }
    public long runNounVerb(int noun, int verb){
        ArrayList<Long> code = new ArrayList<>(this.program);
        code.set(1,(long) noun);
        code.set(2,(long) verb);
        int index = 0;
        LOOP: while(code.get(index)!=99){
            switch((int)(long)code.get(index)){
                case 1:
                    code.set((int)(long)code.get(index+3),code.get((int)(long)code.get(index+1))+code.get((int)(long)code.get(index+2)));
                    index += 4;
                    break;
                case 2:
                    code.set((int)(long)code.get(index+3),code.get((int)(long)code.get(index+1))*code.get((int)(long)code.get(index+2)));
                    index += 4;
                    break;
                case 99:
                    break LOOP;
                default:
                    throw new IllegalArgumentException();
            }
        }
        return code.get(0);
    }

    public long run(long input){
        boolean usedInput = false;
        int[] modes = new int[3];
        LOOP: while(true){
            int instruction = (int)(long)currentProgram.get(index);
            for(int numberParameter=0;numberParameter<modes.length;numberParameter++){
                modes[numberParameter] = getDigit (instruction,numberParameter+2);
            }
            switch(instruction%100){
                case 1: // Addition
                    this.set(currentProgram.get(index+3),getValue(index+1, modes[0])+getValue(index+2, modes[1]), modes[2]);
                    index += 4;
                    break;
                case 2: // Multiply
                    this.set(currentProgram.get(index+3),getValue(index+1, modes[0])*getValue(index+2, modes[1]), modes[2]);
                    index += 4;
                    break;
                case 3: // Set input
                    if(usedInput){
                        return outputs.size()>0 ? outputs.get(outputs.size()-1) : -1;
                    } else {
                        this.set(currentProgram.get(index+1),input, modes[0]);
                        index += 2;
                        usedInput = true;
                    }
                    break;
                case 4: // Output test
                    outputs.add(getValue(index + 1, modes[0]));
                    this.index += 2;
                    if(outputs.get(outputs.size()-1).equals((long)'?')){
                        return Long.MAX_VALUE;
                    }
                    break;
                case 5: // Jump-if-true
                    if(getValue(index+1, modes[0])!=0){
                        index = (int)getValue(index+2, modes[1]);
                    } else {
                        index += 3;
                    }
                    break;
                case 6: // Jump-if-false
                    if(getValue(index+1, modes[0])==0){
                        index = (int)getValue(index+2, modes[1]);
                    } else {
                        index += 3;
                    }
                    break;
                case 7: // Less than
                    if(getValue(index+1, modes[0])<getValue(index+2, modes[1])){
                        this.set(currentProgram.get(index+3),1, modes[2]);
                    } else {
                        this.set(currentProgram.get(index+3),0, modes[2]);
                    }
                    index += 4;
                    break;
                case 8: // Equals
                    if(getValue(index+1, modes[0])==getValue(index+2, modes[1])){
                        this.set(currentProgram.get(index+3),1, modes[2]);
                    } else {
                        this.set(currentProgram.get(index+3),0, modes[2]);
                    }
                    index += 4;
                    break;
                case 9:
                    relativeBase = (int)(relativeBase + getValue(index+1, modes[0]));
                    index += 2;
                    break;
                case 99:
                    index = 0;
                    relativeBase = 0;
                    finished = true;
                    break LOOP;
                default:
                    throw new IllegalArgumentException("Found illegal upcode on index " + index);
            }
        }
        return outputs.get(outputs.size()-1);
    }
    public long test(int input){
        long output = 0;
        while(!finished){
            output = this.run(input);
            if(!finished && output!=0){
                throw new IllegalArgumentException("Diagnostic tests failed. Output nr. " + index + " equals " + outputs.get(index) + " instead of 0.");

            }
        }
        this.reset();
        return output;
    }
    public long run(Integer[] input){
        long output =0;
        for(Integer entry : input){
            output = run(entry);
        }
        return output;
    }

    /**
     * Gets the value of a parameter on the requested position.
     * @param positionParameter Position of the parameter.
     * @param mode Parameter mode. 0 for positional mode and 1 for immediate mode.
     * @return Value of the parameter.
     */
    private long getValue(int positionParameter, int mode){
        int position;
        switch(mode){
            case 0:
                position = (int)(long) currentProgram.get(positionParameter);
                if(position>=currentProgram.size()){
                    return 0;
                }
                return currentProgram.get(position);
            case 1:
                position = positionParameter;
                if(position>=currentProgram.size()){
                    return 0;
                }
                return currentProgram.get(position);
            case 2:
                position = relativeBase + (int)(long) currentProgram.get(positionParameter);
                if(position>=currentProgram.size()){
                    return 0;
                }
                return currentProgram.get(position);
            default:
                throw new IllegalArgumentException();
        }
    }
    private void set(long index, long value, int mode){
        if(mode==1){
            this.index = 0;
            throw new IllegalArgumentException("Parameter mode of parameter 3 can\'t be immediate mode.");
        }
        index += relativeBase*(mode/2);
        while(currentProgram.size()<=index){
            currentProgram.add(0L);
        }
        currentProgram.set((int)index, value);
    }

    /**
     * Gets the digit on a requested position.
     * @param number Number.
     * @param position Positions goes from left to right. So ones will be on position 0, tens will be on position 1, etc.
     * @return Digit on the requested position.
     */
    private int getDigit(int number, int position){
        return (number/(int)Math.pow (10,position) )%10;
    }
}
