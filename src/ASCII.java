import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ASCII {
    private IntCode program;
    private ArrayList<ArrayList<Character>> output;
    private Integer[][] functions;
    ASCII(IntCode program){
        this(program,3);
    }
    ASCII(IntCode program, int functionLength){
        this.program = program;
        this.functions = new Integer[functionLength][];
    }
    public void addFunction(char name, String[] input){
        if(input.length>10){
            throw new IllegalArgumentException("Input is too long (max 10).");
        }
        functions[(int)name-'A'] = convertInput(input);
    }
    public void reset(){
        program.reset();
    }
    public boolean isFinished(){
        return program.isFinished();
    }
    public ArrayList<ArrayList<Character>> getOutput() {
        List<Long> outputArray = program.getNewOutputs();
        output = new ArrayList<>();
        ArrayList<Character> line = new ArrayList<>();
        for(long number : outputArray){
            if(number>128){
                break;
            }
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
        return program.run(functions[(int)function-'A']);
    }

    public long run(Character[] routine){
        if(routine.length>10){
            throw new IllegalArgumentException("Routine is too long (max 10).");
        }
        ArrayList<Integer> input = new ArrayList<>();
        for(char entry : routine){
            if(entry-'A' > functions.length || entry-'A'<0){
                throw new IllegalArgumentException("Main routine contains an invalid value." + entry);
            }
            input.add((int)entry);
            input.add((int)',');
        }
        input.set(input.size()-1, 10);
        long output = 0;
        try {
            for (int entry : input) {
                output = program.run(entry);
            }
            for (int index = 0; index < functions.length; index++) {
                output = run((char) ((int) 'A' + index));
            }
        } catch(OutOfMemoryError e){
            System.out.println(program.getLastOutput());
            print();
            e.printStackTrace();
        }
        return output;
    }
    public long run(ArrayList<String> stringArray){
        ArrayList<Integer> input = new ArrayList<>();
        for(String string : stringArray){
            char[] characters = string.toCharArray();
            for(char entry : characters){
                input.add((int) entry);
            }
            input.add(10);
        }
        if(input.size()==0){
            return program.run(Long.MAX_VALUE);
        }
        long output = 0;
        try {
            for (int entry : input) {
                output = program.run(entry);
            }
        } catch(OutOfMemoryError e){
            System.out.println(program.getLastOutput());
            print();
            e.printStackTrace();
        }
        return output;

    }
    public long runWithFeed(Character[] routine, char feedOption){
        if(feedOption != 'y' && feedOption != 'n'){
            throw new IllegalArgumentException("Please enter if you want a continuous feed.");
        }
        run(routine);
        program.run(feedOption);
        return program.run(10L);
    }
    public long runWithFeed(ArrayList<String> routine, char feedOption){
        if(feedOption != 'y' && feedOption != 'n'){
            throw new IllegalArgumentException("Please enter if you want a continuous feed.");
        }
        run(routine);
        program.run(feedOption);
        return program.run(10L);
    }
    private Integer[] convertInput(String[] inputChar){
        ArrayList<Integer> input = new ArrayList<>();
        for(String entry : inputChar){
            char[] charArray = entry.toCharArray();
            for(char character : charArray){
                input.add((int)character);
            }
            input.add((int)',');
        }
        input.set(input.size()-1, 10);
        return input.toArray(new Integer[0]);
    }
    public void print(){
        getOutput();
        for(ArrayList<Character> line : output){
            System.out.println(line.toString().replace(", ","").replace("[","").replace("]",""));
        }
    }
    public ArrayList<String> outputToStringArray(){
        getOutput();
        ArrayList<String> stringOutput = new ArrayList<>();
        for(ArrayList<Character> line : output){
            stringOutput.add(line.toString().replace(", ","").replace("[","").replace("]",""));
        }
        return stringOutput;
    }
}
