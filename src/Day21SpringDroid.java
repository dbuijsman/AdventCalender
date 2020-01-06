import java.util.ArrayList;
import java.util.Scanner;

public class Day21SpringDroid {
    public static void main(String[] args){
        ArrayList<Long> input = General.importIntCode("Day21 Springdroid");
        System.out.println("WALK or RUN");
        String ans = answer();
        if(ans.toUpperCase().equals("WALK")){
            long output = walking(input);
            System.out.println("The last output was " + output);

        }
        else {
            do{
                long output = running(input);
                System.out.println("The last output was " + output);
                System.out.println("Again? ");
            } while(answer().toLowerCase().startsWith("y"));
        }
    }
    static String answer(){
        Scanner input = new Scanner(System.in);
        String ans = input.nextLine();
        return ans;
    }
    static long walking(ArrayList<Long> imp){
        IntCode program = new IntCode(imp);
        ASCII ascii = new ASCII(program);
        ArrayList<String> input = new ArrayList<>();
        String line = "";
        while(!line.equals("WALK")){
            line = answer();
            input.add(line);
        }
        long output = ascii.run(input);
        ascii.print();
        return output;
    }
    static long running(ArrayList<Long> imp){
        IntCode program = new IntCode(imp);
        ASCII ascii = new ASCII(program);
        ArrayList<String> input = new ArrayList<>();
        String line = "";
        while(!line.equals("RUN")){
            line = answer();
            input.add(line);
        }
        long output = ascii.run(input);
        ascii.print();
        return output;
    }
}
