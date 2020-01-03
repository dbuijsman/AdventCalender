import java.util.ArrayList;
import java.util.HashMap;

public class ASCII {
    private IntCode program;
    private ArrayList<ArrayList<Character>> output;
    private char[][] functions;
    ASCII(IntCode program){
        this(program,3);
    }
    ASCII(IntCode program, int functionLength){
        this.program = program;
        this.functions = new char[functionLength][];
        this.functions[2] = new char[]{'1','1',10};
    }
    public void addFunction(char name, char[] input){
        if(input.length>10){
            throw new IllegalArgumentException("Input is too long (max 10).");
        }
        functions[(int)name-'A'] = input;
    }
    public void reset(){
        program.reset();
    }

    public ArrayList<ArrayList<Character>> getOutput() {
        ArrayList<Long> outputArray = program.getOutputs();
        output = new ArrayList<>();
        ArrayList<Character> line = new ArrayList<>();
        for(long number : outputArray){
            if(number == 10){
                output.add(line);
                line = new ArrayList<>();
            } else {
                line.add((char) number);
            }
        }
        return this.output;
    }

    private long run(char function){
        char[] inputChar = functions[(int)function-'A'];
        if(function=='C'){
            ArrayList<Long> inp = new ArrayList<>();
            inp.add((long)'1');
            inp.add((long)'1');
            inp.add(10L);
            return program.run(inp);
        }
        return program.run(convertInput(inputChar));
    }

    public long run(char[] routine){
        if(routine.length>10){
            throw new IllegalArgumentException("Routine is too long (max 10).");
        }
        ArrayList<Long> input = convertInput(routine);
        long output = 0;
        for(Long entry : input){
            output = program.run(entry);
        }
        for(int index=0;index<functions.length;index++){
            output = run((char)((int)'A'+index));
        }
        return output;
    }
    public long runWithFeed(char[] routine, char feedOption){
        if(feedOption != 'y' && feedOption != 'n'){
            throw new IllegalArgumentException("Please enter if you want a continuous feed.");
        }
        run(routine);
        program.run(feedOption);
        return program.run(10L);
    }
    private ArrayList<Long> convertInput(char[] inputChar){
        ArrayList<Long> input = new ArrayList<>();
        for(char entry : inputChar){
            input.add((long)entry);
            input.add((long)',');
        }
        input.set(input.size()-1, 10L);
        return input;
    }
    public void print(){
        if(output==null){
            getOutput();
        }
        for(ArrayList<Character> line : output){
            System.out.println(line.toString().replace(", ","").replace("[","").replace("]",""));
        }
    }
}
