import java.util.HashSet;
import java.util.LinkedList;

public class StepCheckerMazeKeys {
    private LinkedList<TileMazeKey> tiles;
    private HashSet<Character> keys;
    private LinkedList<Integer> minimalSteps;
    private int failedAttempts;
    StepCheckerMazeKeys(LinkedList<TileMazeKey> tile, HashSet<Character> keys, LinkedList<Integer> minimalSteps){
        this(tile,keys,minimalSteps,0);
    }
    StepCheckerMazeKeys(LinkedList<TileMazeKey> tile, HashSet<Character> keys, LinkedList<Integer> minimalSteps, int failedAttempts){
        this.tiles = tile;
        this.keys = keys;
        this.minimalSteps = minimalSteps;
        this.failedAttempts = failedAttempts;
    }

    public LinkedList<TileMazeKey> getTiles() {
        return tiles;
    }
    public void addFailedAttempt(){
        failedAttempts++;
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
