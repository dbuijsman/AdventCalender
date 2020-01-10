import java.util.ArrayList;

public class MazeKeys {
    private ArrayList<TileMazeKey> maze = new ArrayList<>();
    private TileMazeKey start;
    MazeKeys(char[][] mazeAsArray) {
        TileMazeKey[][] mazeTileArray = new TileMazeKey[mazeAsArray.length][mazeAsArray[0].length];
        // Adding Tiles.
        for (int y = 0; y < mazeAsArray.length; y++) {
            for (int x = 0; x < mazeAsArray[0].length; x++) {
                char tileChar = mazeAsArray[y][x];
                if (tileChar != '#') {
                    mazeTileArray[y][x] = new TileMazeKey(tileChar);
                    if(tileChar == '@'){
                        start = mazeTileArray[y][x];
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
        for(TileMazeKey[] row : mazeTileArray){
            for(TileMazeKey tile : row){
                if(tile != null){
                    maze.add(tile);
                }
            }
        }
    }
    public int walk(){
        start.setStart();
        while(!isDone()){
            for(TileMazeKey tile : maze){
                tile.walk();
            }
        }
        return TileMazeKey.getMinimum();
    }
    private boolean isDone(){
        for(TileMazeKey tile : maze){
            if(!tile.isDone()){
                return false;
            }
        }
        return true;
    }
}
