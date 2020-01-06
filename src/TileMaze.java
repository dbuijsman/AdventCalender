import java.util.ArrayList;
import java.util.Random;

public class TileMaze extends AbstractMazeTile{
    private ArrayList<Integer> minimalSteps= new ArrayList<>();
    private ArrayList<Boolean> checkedForMinimum = new ArrayList<>();
    private ArrayList<AbstractMazeTile> neighbours = new ArrayList<>();

    TileMaze(){
        //minimalSteps.add(Integer.MAX_VALUE);
    }

    public int getMinimalSteps(int level) {
        if(level>=minimalSteps.size()){
            return Integer.MAX_VALUE;
        } else {
            return minimalSteps.get(level);
        }
    }

    public ArrayList<Integer> getMinimalSteps() {
        return minimalSteps;
    }

    public static void setMax_level(int max_level) {
        AbstractMazeTile.max_level = max_level;
    }

    public void addNeighbour(AbstractMazeTile neighbour){
        neighbours.add(neighbour);
    }
    public void walk(){
        this.minimalSteps.add(0,0);
        for(AbstractMazeTile neighbour : neighbours){
            neighbour.walk(0,0, this);
        }
    }
    void walk(int steps, int level, AbstractMazeTile lastTile){
        if(level<minimalSteps.size() && steps>=minimalSteps.get(level)){
            System.out.println("Overstepped " + steps + " while founding this at " + minimalSteps.get(level));
            return;
        }
        if(steps>stepsToEnd){
            System.out.println("Uh.. oh... too far.");
        }
        if(level>max_level){
            System.out.println("Uh.. oh... too deep.");
            return;
        }
        steps = setSteps(steps+1, level);
        for(AbstractMazeTile neighbour : neighbours){
            if(neighbour == lastTile){
                continue;
            }
            neighbour.walk(steps, level, this);
        }
        if(stepsToEnd<Integer.MAX_VALUE){
            System.out.println("d");
        }
    }
    private int setSteps(int steps, int level){
        while(minimalSteps.size()<=level){
            minimalSteps.add(Integer.MAX_VALUE);
            checkedForMinimum.add(false);
        }
        if(minimalSteps.get(level)>steps){
            minimalSteps.set(level,steps);
            checkedForMinimum.set(level,false);
            return steps;
        } else {
            checkedForMinimum.set(level,true);
            return minimalSteps.get(level);
        }
    }
    void setStepsToEnd(){
        System.out.println("OOOO");
        stepsToEnd = minimalSteps.get(0);
    }

    public static int getStepsToEnd() {
        return stepsToEnd;
    }
    void walkOxygenStyle(){
        for(int level=0; level<minimalSteps.size(); level++){
            if(!checkedForMinimum.get(level) && minimalSteps.get(level) != Integer.MAX_VALUE){
                for(AbstractMazeTile neighbour : neighbours){
                    neighbour.walkOxygenStyle(minimalSteps.get(level),level);
                }
            }
        }
    }
    void setOxygenStyle(){
        minimalSteps.add(0,0);
        checkedForMinimum.add(0,false);
    }
    void walkOxygenStyle(int steps, int level){
        if(level>max_level){
            System.out.println("Uh.. oh... too deep.");
            return;
        }
        setSteps(steps+1, level);
    }
}
