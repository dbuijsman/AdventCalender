import java.util.Arrays;
import java.util.HashMap;

public class GridOxygen {
    private HashMap<Integer, HashMap<Integer,TileOxygenSystem>> tiles = new HashMap<>();
    GridOxygen(){
        HashMap<Integer,TileOxygenSystem> newEntry = new HashMap<>();
        newEntry.put(0,new FreeTile());
        tiles.put(0,newEntry);
    }

    public HashMap<Integer, HashMap<Integer,TileOxygenSystem>> getTiles() {
        return tiles;
    }

    public TileOxygenSystem getTile(int x, int y){
        if(tiles.containsKey(y) && tiles.get(y).containsKey(x)){
            return tiles.get(y).get(x);
        } else {
            return null;
        }
    }
    void addTile(int x, int y, FreeTile currentTile, int tileType, int direction){
        if(tiles.containsKey(y)){
            if(!tiles.get(y).containsKey(x)){
                if(tileType == 0){
                    Wall wall = new Wall();
                    tiles.get(y).put(x, wall);
                    currentTile.addNeighbour(direction,wall);
                } else {
                    FreeTile newTile = new FreeTile(direction,currentTile);
                    tiles.get(y).put(x,newTile);
                    currentTile.addNeighbour(direction,newTile);

                }
            } else {
                currentTile.addNeighbour(direction,getTile(x,y));
            }
        } else {
            HashMap<Integer,TileOxygenSystem> newEntry = new HashMap<>();
            if(tileType == 0){
                Wall wall = new Wall();
                newEntry.put(x, wall);
                currentTile.addNeighbour(direction,wall);
            } else {
                FreeTile newTile = new FreeTile(direction,currentTile);
                newEntry.put(x,newTile);
                currentTile.addNeighbour(direction,newTile);
            }
            tiles.put(y,newEntry);
        }
    }
    int getDirection(int x, int y){
        return ((FreeTile) tiles.get(y).get(x)).getDirection();
    }
    public void print(){
        int minimalY = General.minimum(tiles.keySet());
        int maximalY = General.maximum(tiles.keySet());
        int minimalX = Integer.MAX_VALUE;
        int maximalX = Integer.MIN_VALUE;
        for(HashMap<Integer,TileOxygenSystem> values : tiles.values()){
            minimalX = Math.min(minimalX, General.minimum(values.keySet()));
            maximalX = Math.max(maximalX, General.maximum(values.keySet()));
        }
        char[][] hull = new char[maximalX-minimalX+1][maximalY-minimalY+1];
        for(int coordinateY : tiles.keySet()){
            for(int coordinateX: tiles.get(coordinateY).keySet()){
                boolean isFreeTile = tiles.get(coordinateY).get(coordinateX) instanceof FreeTile;
                hull[maximalX-coordinateX][coordinateY-minimalY] = (isFreeTile)? 0: '\u2588';
            }
        }
        for(char[] row : hull){
            System.out.println(Arrays.toString(row).replace("[","").replace("]","").replace(", ",""));
        }
    }
    public void setSteps(){
        boolean isChanged = true;
        while(isChanged){
            isChanged = false;
            for(int coordinateY : tiles.keySet()){
                for(int coordinateX: tiles.get(coordinateY).keySet()){
                    TileOxygenSystem tile = tiles.get(coordinateY).get(coordinateX);
                    if(tile.setAmountSteps()){
                        isChanged = true;
                    }
                }
            }
        }
    }
    public int getMaxOxygen(){
        int max = 0;
        for(int coordinateY : tiles.keySet()){
            for(int coordinateX: tiles.get(coordinateY).keySet()){
                TileOxygenSystem tile = tiles.get(coordinateY).get(coordinateX);
                max = Math.max(max,tile.getOxygen());
            }
        }
        return max;
    }
}
