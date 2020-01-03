import java.util.ArrayList;

public class Day17SolarFlare {
    public static void main(String[] args){
        ArrayList<Long> intCode = General.importIntCode("Day17 Solar Flare");
        IntCode program = new IntCode(intCode);
        program.run(0);
        ArrayList<Long> output = program.getOutputs();
        ArrayList<ArrayList<Character>> map = new ArrayList<>();
        ArrayList<Character> line = new ArrayList<>();
        for(long number : output){
            if(number == 10){
                map.add(line);
                line = new ArrayList<>();
                System.out.println("");
            } else {
                line.add((char) number);
                System.out.print((char) number);
            }
        }
        long sum = 0;
        for(int indexY=1; indexY<map.size()-1;indexY++){
            for(int indexX=1; indexX<Math.min(map.get(indexY).size(),map.get(indexY+1).size())-1;indexX++){
                if(map.get(indexY).get(indexX) != '.' && map.get(indexY-1).get(indexX) != '.' && map.get(indexY+1).get(indexX) != '.' && map.get(indexY).get(indexX-1) != '.' && map.get(indexY).get(indexX+1) != '.'){
                    sum += indexX*indexY;
                }
            }
        }
        System.out.println("The sum of the intersections is " + sum);
        CleaningRobot robot = new CleaningRobot(map);
        ArrayList<Character> route = robot.findRoute();
        FittingFunctions17 fitting = new FittingFunctions17(route,10);
        //fitting.makeRoutine();
        intCode.set(0,2L);
        ASCII ascii = new ASCII(new IntCode(intCode));
        ascii.addFunction('A',new char[]{'1'});
        ascii.addFunction('B',new char[]{'R'});

        ascii.runWithFeed(new char[]{'B','C'},'n');
        ascii.print();
    }
}
