public class Day2IntCode {
    public static void main(String[] args){
        IntCode program = new IntCode(General.importIntCode("Day2 IntCode"));
        System.out.println(program.runNounVerb(12,2));
        for(int noun=0;noun<100;noun++){
            for(int verb=0;verb<100;verb++){
                if(program.runNounVerb(noun,verb)==19690720){
                    System.out.println("Found noun=" + noun + " and verb=" + verb);
                    System.out.println("The answer is " + (100*noun+verb));
                }
            }
        }
    }
}
