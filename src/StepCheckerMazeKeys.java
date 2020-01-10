import java.util.HashSet;
import java.util.LinkedList;

public class StepCheckerMazeKeys {
    private LinkedList<TileMazeKey> tiles;
    private HashSet<Character> keys;
    private LinkedList<Integer> minimalSteps;
    private LinkedList<TileMazeKey> tileLastKey;
    private LinkedList<Integer> minimalStepsToLastKey;
    private int failedAttempts;
    StepCheckerMazeKeys(LinkedList<TileMazeKey> tile, HashSet<Character> keys, LinkedList<Integer> minimalSteps,LinkedList<TileMazeKey> tileLastKey, LinkedList<Integer> minimalStepsToLastKey){
        this(tile,keys,minimalSteps,tileLastKey,minimalStepsToLastKey,0);
    }
    StepCheckerMazeKeys(LinkedList<TileMazeKey> tile, HashSet<Character> keys, LinkedList<Integer> minimalSteps,LinkedList<TileMazeKey> tileLastKey, LinkedList<Integer> minimalStepsToLastKey, int failedAttempts){
        this.tiles = tile;
        this.keys = keys;
        this.minimalSteps = minimalSteps;
        this.tileLastKey = tileLastKey;
        this.minimalStepsToLastKey = minimalStepsToLastKey;
        this.failedAttempts = failedAttempts;
    }
    StepCheckerMazeKeys(StepCheckerMazeKeys stepChecker){
        this(stepChecker.getTiles(),stepChecker.getKeys(),stepChecker.getMinimalSteps(),stepChecker.getTileLastKey(),stepChecker.getMinimalStepsToLastKey(),stepChecker.getFailedAttempts());
    }

    public LinkedList<TileMazeKey> getTiles() {
        return tiles;
    }
    public void addFailedAttempt(){
        failedAttempts++;
    }

    public LinkedList< TileMazeKey > getTileLastKey() {
        return tileLastKey;
    }

    public LinkedList< Integer > getMinimalStepsToLastKey() {
        return minimalStepsToLastKey;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public HashSet< Character > getKeys() {
        return keys;
    }
    public LinkedList<Integer> getMinimalSteps() {
        return minimalSteps;
    }
}
