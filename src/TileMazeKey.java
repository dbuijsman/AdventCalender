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
    private StepCheckerMazeKeys setStep(StepCheckerMazeKeys stepChecker, int steps, TileMazeKey tileLastKey, int stepsToLastKey){
        LinkedList<TileMazeKey> robots = new LinkedList<>(stepChecker.getTiles());
        LinkedList<Integer> stepsPerRobot = new LinkedList<>(stepChecker.getMinimalSteps());
        LinkedList<TileMazeKey> tilesLastKey = new LinkedList<>(stepChecker.getTileLastKey());
        LinkedList<Integer> stepsToKeyPerRobot = new LinkedList<>(stepChecker.getMinimalStepsToLastKey());
        HashSet<Character> keys = stepChecker.getKeys();
        steps++;
        if(steps>=minimum){
            return null;
        }
        if(isKey() && !keys.contains(key)){
            HashSet<Character> newKeys = new HashSet<>(keys);
            newKeys.add(key);
            keys = newKeys;
            stepsToLastKey = steps;
            tileLastKey = this;
            for(int times = 0; times<4;times++){
                robots.poll();
                TileMazeKey newTile = tilesLastKey.poll();
                robots.offer(newTile);
                tilesLastKey.offer(newTile);
                stepsPerRobot.poll();
                int lastSteps = stepsToKeyPerRobot.poll();
                stepsPerRobot.offer(lastSteps);
                stepsToKeyPerRobot.offer(lastSteps);

            }

            if(keys.size()==26){
                minimum = steps;
                robots.offer(this);
                stepsPerRobot.offer(steps);
                tilesLastKey.offer(this);
                stepsToKeyPerRobot.offer(steps);
                System.out.print(stepsToKeyPerRobot);
                return new StepCheckerMazeKeys(robots,keys,stepsPerRobot,tilesLastKey,stepsToKeyPerRobot);
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
                    break;
                }
            }
        }
        this.minimalSteps.put(keys,steps);
        robots.offer(this);
        stepsPerRobot.offer(steps);
        tilesLastKey.offer(tileLastKey);
        stepsToKeyPerRobot.offer(stepsToLastKey);
        return new StepCheckerMazeKeys(robots,keys,stepsPerRobot,tilesLastKey,stepsToKeyPerRobot);
    }
    public ArrayList<StepCheckerMazeKeys> step(StepCheckerMazeKeys stepChecker, int steps, TileMazeKey tileLastKey, int stepsToLastKey){
        ArrayList<StepCheckerMazeKeys> queue = new ArrayList<>();
        for(TileMazeKey neighbour : neighbours){
            StepCheckerMazeKeys newEntry = neighbour.setStep(stepChecker, steps, tileLastKey, stepsToLastKey);
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
            stepChecker.getMinimalStepsToLastKey().offer(stepsToLastKey);
            stepChecker.getTileLastKey().offer(tileLastKey);
            queue.add(new StepCheckerMazeKeys(stepChecker));
        }
        return queue;
    }
    public static boolean isDone(){
        return minimum<Integer.MAX_VALUE;
    }
}
