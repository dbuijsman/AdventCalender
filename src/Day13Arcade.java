public class Day13Arcade {
    public static void main(String[] args){
        IntCode program = new IntCode(General.importIntCode("Day13 Arcade"));
        Arcade arcade = new Arcade(program);
        arcade.startStepByStep();
    }
}
