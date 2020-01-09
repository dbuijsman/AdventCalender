import java.util.ArrayList;
import java.util.Scanner;

public class Day25Cryo {
    public static void main(String[] args){
        ArrayList<Long> imp = General.importIntCode("Day25 Cryo");
        System.out.println("Starting...");
        do{
            IntCode program = new IntCode(imp);
            ASCII ascii = new ASCII(program);
//            ascii.run(new ArrayList<>());
//            ascii.print();
            ShipDroid droid = new ShipDroid(ascii);
            droid.run();
            while(!program.isFinished()){
                ArrayList<String> input = new ArrayList<>();
                String line = answer();
                if(line.equals("STAHP")){
                    break;
                }
                input.add(line);
                ascii.run(input);
                ascii.print();
            }
            System.out.println("Again? ");
        } while(answer().toLowerCase().startsWith("y"));
    }
    static String answer(){
        Scanner input = new Scanner(System.in);
        String ans = input.nextLine();
        return ans;
    }
}
