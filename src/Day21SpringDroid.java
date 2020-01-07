import java.util.ArrayList;
import java.util.Scanner;

public class Day21SpringDroid {
    public static void main(String[] args){
        // Walking has logic: (not A or not C) and D.
        // Running has logic: (not(A and B) or (H and not C) or not A) and D.
        ArrayList<Long> imp = General.importIntCode("Day21 Springdroid");
        System.out.println("Springscript:");
        do{
            IntCode program = new IntCode(imp);
            ASCII ascii = new ASCII(program);
            ArrayList<String> input = new ArrayList<>();
            String line = "";
            while(!line.equals("WALK") && !line.equals("RUN")){
                line = answer();
                input.add(line);
            }
            long output = ascii.run(input);
            ascii.print();
            System.out.println("The last output was " + output);
            System.out.println("Again? ");
        } while(answer().toLowerCase().startsWith("y"));
    }
    static String answer(){
        Scanner input = new Scanner(System.in);
        String ans = input.nextLine();
        return ans;
    }
}
