public abstract class AbstractMazeTile {
    static int max_level;
    static int stepsToEnd = Integer.MAX_VALUE;
    abstract void walkOxygenStyle(int steps, int level);
}
