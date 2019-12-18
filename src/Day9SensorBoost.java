
public class Day9SensorBoost {
    public static void main(String[] args){
        IntCode program = new IntCode(General.importIntCode("Day9 Sensor Boost"));
        System.out.println("We found the key code " + program.test(1));
    }
}
