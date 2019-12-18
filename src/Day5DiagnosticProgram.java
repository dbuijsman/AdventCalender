public class Day5DiagnosticProgram {
    public static void main(String[] args){
        IntCode program = new IntCode(General.importIntCode("Day5 IntCode"));
        System.out.println("Final output for air vent is " + program.test(1));
        System.out.println("Final output for thermal radiators is " + program.test(5));
    }
}
