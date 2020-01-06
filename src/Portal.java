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

    void walk(int steps, int level, AbstractMazeTile lastTile){
        if(level>max_level){
            System.out.println("Uh.. oh... too deep.");
            return;
        }
        System.out.println("Reached portal " + name + " at level " + level + " at " + steps + " steps.");
        if(name.equals("AA")){
            System.out.println("Back at the start at level: " + level);
            return;
        }
        if(name.equals("ZZ")){
            System.out.println("Found end at level: " + level);
            if(level==0){
                outerTile.setStepsToEnd();
            }
            return;
        }
        if(outerTile.getMinimalSteps(level)==steps){
            if(level == 0){
                System.out.println("This ain\'t no portal anymore.");
                return;
            }
            level--;
            innerTile.walk(steps, level, this);
        } else {
            level++;
            outerTile.walk(steps, level, this);
        }
    }
    void walkOxygenStyle(int steps, int level){
        if(level>max_level){
            System.out.println("Uh.. oh... too deep.");
            return;
        }
        System.out.println("Reached portal " + name + " at level " + level + " at " + steps + " steps.");
        if(name.equals("AA")){
            System.out.println("Back at the start at level: " + level);
            return;
        }
        if(name.equals("ZZ")){
            System.out.println("Found end at level: " + level);
            if(level==0){
                outerTile.setStepsToEnd();
            }
            return;
        }
        if(outerTile.getMinimalSteps(level)==steps){
            if(level == 0){
                System.out.println("This ain\'t no portal anymore.");
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
