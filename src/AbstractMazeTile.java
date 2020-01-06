public abstract class AbstractMazeTile {
    static int level=0;
    static int max_level;
    static int stepsToEnd = Integer.MAX_VALUE;
    abstract void walk(int steps, int level, AbstractMazeTile lastTile);
    abstract void walkOxygenStyle(int steps, int level);
}
