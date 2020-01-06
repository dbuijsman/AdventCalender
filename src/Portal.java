import java.util.ArrayList;

public class Portal  extends AbstractMazeTile{
    private String name;
    private int[] startPosition;
    private int[] endPosition;
    private TileMaze innerTile;
    private TileMaze outerTile;
    Portal(String name, int x, int y){
        this.name = name;
        startPosition = new int[]{x,y};
    }

    public void setEndPosition(int x, int y) {
        this.endPosition = new int[]{x,y};
    }

    public String getName() {
        return name;
    }
    public int[] getStartPosition() {
        return startPosition;
    }
    public int[] getEndPosition() {
        return endPosition;
    }
    // LowerTile stands on the outside.
    public void setTiles(TileMaze outerTile, TileMaze innerTile){
        this.innerTile = innerTile;
        this.outerTile = outerTile;
    }

    public TileMaze getInnerTile() {
        return innerTile;
    }

    public TileMaze getOuterTile() {
        return outerTile;
    }

    void walkOxygenStyle(int steps, int level){
        if(level>max_level){
            return;
        }
        if(name.equals("AA")){
            return;
        }
        if(name.equals("ZZ")){
            if(level==0){
                outerTile.setStepsToEnd();
            }
            return;
        }
        if(outerTile.getMinimalSteps(level)==steps){
            if(level == 0){
                return;
            }
            level--;
            innerTile.walkOxygenStyle(steps, level);
        } else {
            level++;
            outerTile.walkOxygenStyle(steps, level);
        }

    }
}
