import java.util.*;

public class TileMazeKey {
    private static int minimum = Integer.MAX_VALUE;
    private char key;
    private char door;
    private ArrayList< TileMazeKey > neighbours;
    private HashMap< HashSet<Character>, Integer> minimalSteps= new HashMap<>();
    TileMazeKey(char value){
        if(Character.isLowerCase(value)){
            key = value;
        }
        if(Character.isUpperCase(value)){
            door = value;
        }
        neighbours = new ArrayList<>();
    }
    public static int getMinimum() {
        return minimum;
    }

    private boolean isKey(){
        return key!='\u0000';
    }
    private boolean isDoor(){
        return door!='\u0000';
    }
    public void addNeighbour(TileMazeKey neighbour){
        neighbours.add(neighbour);
    }
    public void setStart(){
        minimalSteps.put(new HashSet<>(), 0);
    }
    private StepCheckerMazeKeys setStep(StepCheckerMazeKeys stepChecker, int steps){
        LinkedList<TileMazeKey> robots = new LinkedList<>(stepChecker.getTiles());
        LinkedList<Integer> stepsPerRobot = new LinkedList<>(stepChecker.getMinimalSteps());
        HashSet<Character> keys = stepChecker.getKeys();
        steps++;
        if(steps>=minimum){
            return null;
        }
        if(isKey() && !keys.contains(key)){
            HashSet<Character> newKeys = new HashSet<>(keys);
            newKeys.add(key);
            keys = newKeys;
            if(keys.size()==26){
                minimum = steps;
                robots.offer(this);
                stepsPerRobot.offer(steps);
                return new StepCheckerMazeKeys(robots,keys,stepsPerRobot);
            }
        }
        if(isDoor() && !keys.contains(Character.toLowerCase(door))){
            return null;
        }
        for(HashSet<Character> existingKeys : minimalSteps.keySet()){
            if(existingKeys.containsAll(keys)){
                int oldSteps = minimalSteps.get(existingKeys);
                if(oldSteps < steps){
                    return null;
                }
                if(keys.containsAll(existingKeys) && oldSteps==steps){
                    return null;
                }
            }
        }
        this.minimalSteps.put(keys,steps);
        robots.offer(this);
        stepsPerRobot.offer(steps);
        return new StepCheckerMazeKeys(robots,keys,stepsPerRobot);
    }
    public ArrayList<StepCheckerMazeKeys> step(StepCheckerMazeKeys stepChecker, int steps){
        ArrayList<StepCheckerMazeKeys> queue = new ArrayList<>();
        for(TileMazeKey neighbour : neighbours){
            StepCheckerMazeKeys newEntry = neighbour.setStep(stepChecker, steps);
            if(newEntry!=null){
                queue.add(newEntry);
            }
        }
        if(queue.size()==0){
            stepChecker.addFailedAttempt();
            if(stepChecker.getFailedAttempts()==4){
                return queue;
            }
            stepChecker.getTiles().offer(this);
            stepChecker.getMinimalSteps().offer(steps);
            queue.add(stepChecker);
        }
        return queue;
    }
    public static boolean isDone(){
        return minimum<Integer.MAX_VALUE;
    }
}
