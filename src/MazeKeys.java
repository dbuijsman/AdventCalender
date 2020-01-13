import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class MazeKeys {
    private TileMazeKey[][] maze;
    private LinkedList< StepCheckerMazeKeys > queue = new LinkedList<>();
    private LinkedList< TileMazeKey > start = new LinkedList<>();
    private int amountKeys=0;

    MazeKeys(char[][] mazeAsArray) {
        TileMazeKey[][] mazeTileArray = new TileMazeKey[mazeAsArray.length][mazeAsArray[0].length];
        // Adding Tiles.
        for (int y = 0; y < mazeAsArray.length; y++) {
            for (int x = 0; x < mazeAsArray[0].length; x++) {
                char tileChar = mazeAsArray[y][x];
                if(Character.isLowerCase(tileChar)){
                    amountKeys++;
                }
                if (tileChar != '#') {
                    mazeTileArray[y][x] = new TileMazeKey(tileChar);
                    if (tileChar == '@') {
                        start.offer(mazeTileArray[y][x]);
                    }
                    if (y > 0 && mazeAsArray[y - 1][x] != '#') {
                        mazeTileArray[y][x].addNeighbour(mazeTileArray[y - 1][x]);
                        mazeTileArray[y - 1][x].addNeighbour(mazeTileArray[y][x]);
                    }
                    if (x > 0 && mazeAsArray[y][x - 1] != '#') {
                        mazeTileArray[y][x].addNeighbour(mazeTileArray[y][x - 1]);
                        mazeTileArray[y][x - 1].addNeighbour(mazeTileArray[y][x]);
                    }
                }
            }
        }
        maze = mazeTileArray;
    }

    public int walk() {
        int foundKeys = 0;
        for (TileMazeKey tile : start) {
            tile.setStart();
        }
        queue.offer(new StepCheckerMazeKeys(start, new HashSet<>(), 0));
        LOOP: while (queue.size()!=0) {
            StepCheckerMazeKeys nextInLine = queue.poll();
            int numberOfSteps = nextInLine.getMinimalSteps();
            for(int numberRobot=0; numberRobot<4; numberRobot++){
                TileMazeKey tile = nextInLine.getTiles().poll();
                ArrayList< StepCheckerMazeKeys > extraQueue = tile.step(nextInLine, numberOfSteps);
                nextInLine.getTiles().offer(tile);
                for (StepCheckerMazeKeys nextEntry : extraQueue) {
                    if(nextEntry.getKeys().size()==amountKeys){
                        return nextEntry.getMinimalSteps();
                    }
                    int keysFound = nextEntry.getKeys().size();
                    if(keysFound>foundKeys){
                        System.out.println("Found " + keysFound + " keys.");
                        foundKeys = keysFound;
                        System.out.println(nextEntry.getKeys());
                        System.out.println("Now:     " + nextEntry.getMinimalSteps());
                    }
                    queue.offer(nextEntry);
                }
            }
        }
        return -1;
    }
}
