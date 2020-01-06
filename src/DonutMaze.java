import java.util.ArrayList;
import java.util.HashMap;

public class DonutMaze {
    private TileMaze[][] maze;
    private HashMap<String, Portal> portals = new HashMap<>();
    private TileMaze start;
    private TileMaze end;
    private Portal testPortal;
    DonutMaze(char[][] mazeAsArray){
        maze = new TileMaze[mazeAsArray.length][mazeAsArray[0].length];
        // Adding Tiles.
        for(int y=0; y<mazeAsArray.length; y++){
            for(int x=0; x<mazeAsArray[0].length; x++){
                if(mazeAsArray[y][x]=='.'){
                    maze[y][x] = new TileMaze();
                    if(y>0 && mazeAsArray[y-1][x]=='.'){
                        maze[y][x].addNeighbour(maze[y-1][x]);
                        maze[y-1][x].addNeighbour(maze[y][x]);
                    }
                    if(x>0 && mazeAsArray[y][x-1]=='.'){
                        maze[y][x].addNeighbour(maze[y][x-1]);
                        maze[y][x-1].addNeighbour(maze[y][x]);
                    }
                }
            }
        }
        // Adding portals.
        for(int y=0; y<mazeAsArray.length;y++){
            for(int x=0; x<mazeAsArray[0].length;x++){
                if(isLetter(mazeAsArray[y][x])){
                    maze[y][x] = new TileMaze();
                    if(y>0 && isLetter(mazeAsArray[y-1][x])){
                        if(y>1 && mazeAsArray[y-2][x]=='.'){
                            addPortal(x,y-2,mazeAsArray[y-1][x],mazeAsArray[y][x]);
                        } else {
                            addPortal(x,y+1,mazeAsArray[y-1][x],mazeAsArray[y][x]);
                        }
                    }
                    if(x>0 && isLetter(mazeAsArray[y][x-1])){
                        if(x>1 && mazeAsArray[y][x-2]=='.'){
                            addPortal(x-2,y,mazeAsArray[y][x-1],mazeAsArray[y][x]);
                        } else {
                            addPortal(x+1,y,mazeAsArray[y][x-1],mazeAsArray[y][x]);
                        }
                    }
                }
            }
        }
        connectPortals();
    }
    private boolean isLetter(char character){
        return character>='A' && character<='Z';
    }
    private void addPortal(int x, int y, char char1, char char2){
        String name = "" + char1 + char2;
        String nameReverse = "" + char2 + char1;
        if(portals.containsKey(name)){
            portals.get(name).setEndPosition(x,y);
        } else if(portals.containsKey(nameReverse)){
            portals.get(nameReverse).setEndPosition(x,y);
        } else {
            portals.put(name, new Portal(name, x,y));
        }
    }
    private void connectPortals(){
        for(Portal portal : portals.values()){
            if(start==null && portal.getName().equals("AA")){
                int[] position = portal.getStartPosition();
                start = maze[position[1]][position[0]];
            } else if(end==null && portal.getName().equals("ZZ")){
                int[] position = portal.getStartPosition();
                end = maze[position[1]][position[0]];
                end.addNeighbour(portal);
                portal.setTiles(end, null);
            } else{
                int[] startPosition = portal.getStartPosition();
                int[] endPosition = portal.getEndPosition();
                TileMaze startTile = maze[startPosition[1]][startPosition[0]];
                TileMaze endTile = maze[endPosition[1]][endPosition[0]];
                startTile.addNeighbour(portal);
                endTile.addNeighbour(portal);
                if(startPosition[0]==2 || startPosition[0]==maze[0].length-3 || startPosition[1]==2 || startPosition[1]==maze.length-3){
                    System.out.println(portal.getName());
                    System.out.println("(" + startPosition[0] + ", " + startPosition[1] + ") and (" + endPosition[0] + ", " + endPosition[1] + ")");
                    portal.setTiles(startTile,endTile);
                } else {
                    System.out.println(portal.getName());
                    System.out.println("(" + endPosition[0] + ", " + endPosition[1] + ") and (" + startPosition[0] + ", " + startPosition[1] + ")");
                    portal.setTiles(endTile,startTile);
                }
            }
            if(portal.getName().equals("ZZ") || portal.getName().equals("AA")){
                testPortal = portal;
            }
        }
    }
    public int walk(){
        TileMaze.setMax_level(2400);
        try {
            //start.walk();
            start.setOxygenStyle();
            while(end.getMinimalSteps(0)==Integer.MAX_VALUE){
                for(TileMaze[] row : maze){
                    for(TileMaze tile : row){
                        if(tile != null){
                            tile.walkOxygenStyle();
                        }
                    }
                }
            }
            print(0);
        } catch(StackOverflowError e){
            System.out.println("StackOverflow... got: " + end.getMinimalSteps(0));
        }
        ArrayList<Integer> steps = end.getMinimalSteps();
        for (int key = 0; key < steps.size(); key++) {
            System.out.println("Level " + key + " reached at " + steps.get(key));
        }
        return end.getMinimalSteps(0);
    }
    public void print(int level){
        for(TileMaze[] row : maze){
            for(TileMaze tile : row){
                if(tile == null){
                    System.out.print("" + '\u2588');
                } else {
                    if(tile==testPortal.getInnerTile()){
                        System.out.print("U");
                    }else if(tile==testPortal.getOuterTile()){
                        System.out.print("L");
                    }else if(tile.getMinimalSteps(level)<Integer.MAX_VALUE){
                        System.out.print("O");
                    } else {
                        System.out.print(" ");
                    }
                }
            }
            System.out.println("");
        }
    }
}
