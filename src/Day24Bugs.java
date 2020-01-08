import java.util.ArrayList;
import java.util.HashSet;

public class Day24Bugs {
    public static void main(String[] args){
        HashSet<Long> oldValues = new HashSet<>();
        String[] input = General.importResource("Day24 Bugs");
        BugsGrid grid = new BugsGrid(input);
        grid.print();
        oldValues.add(Long.parseLong(grid.getBinaryState(),2));
        while(true){
            long value = Long.parseLong(grid.addMinute(),2);
            if(oldValues.contains(value)){
                break;
            } else {
                oldValues.add(value);
            }
        }
        System.out.println("The first recurrent state is: ");
        grid.print();
        System.out.println("This state has value: " + Long.parseLong(grid.getBinaryState(),2));

        BugsGridMultidimensional gridDepth = new BugsGridMultidimensional(input);
        gridDepth.addMinutes(200);
        System.out.println("After 200 minutes there are " + gridDepth.getTotalBugs() + " bugs.");

    }
}
