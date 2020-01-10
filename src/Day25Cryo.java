import java.util.ArrayList;

public class Day25Cryo {
    public static void main(String[] args){
        ArrayList<Long> imp = General.importIntCode("Day25 Cryo");
        IntCode program = new IntCode(imp);
        ASCII ascii = new ASCII(program);
        ShipDroid droid = new ShipDroid(ascii);
        droid.run();
    }
}
