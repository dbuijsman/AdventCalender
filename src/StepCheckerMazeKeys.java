import java.util.HashSet;
import java.util.LinkedList;

public class StepCheckerMazeKeys {
    private LinkedList<TileMazeKey> tiles;
    private HashSet<Character> keys;
    private int minimalSteps;
    StepCheckerMazeKeys(LinkedList<TileMazeKey> tile, HashSet<Character> keys, int minimalSteps){
        this.tiles = tile;
        this.keys = keys;
        this.minimalSteps = minimalSteps;
    }
    StepCheckerMazeKeys(StepCheckerMazeKeys stepChecker){
        this(stepChecker.getTiles(),stepChecker.getKeys(),stepChecker.getMinimalSteps());
    }

    public LinkedList<TileMazeKey> getTiles() {
        return tiles;
    }

    public HashSet< Character > getKeys() {
        return keys;
    }
    public int getMinimalSteps() {
        return minimalSteps;
    }
}
