import java.util.Arrays;
import java.util.HashMap;

public class GridHull {
    private HashMap<Integer, HashMap<Integer,Panel>> panels = new HashMap<>();
    public HashMap<Integer, HashMap<Integer,Panel>> getPanels() {
        return panels;
    }
    public int getColor(int[] position){
        if(panels.containsKey(position[0]) && panels.get(position[0]).containsKey(position[1])){
            return panels.get(position[0]).get(position[1]).getColor();
        } else {
            return 0;
        }
    }
    void paint(int[] position, int color){
        if(position.length!=2 || color<0 || color>1){
            throw new IllegalArgumentException("Position must be of length 2 and color must be 0 or 1.");
        }
        if(panels.containsKey(position[0])){
            panels.get(position[0]).put(position[1],new Panel(color));
        } else {
            HashMap<Integer,Panel> newEntry = new HashMap<>();
            newEntry.put(position[1],new Panel(color));
            panels.put(position[0],newEntry);
        }
    }
    public void print(){
        int minimalY = General.minimum(panels.keySet());
        int maximalY = General.maximum(panels.keySet());
        int minimalX = Integer.MAX_VALUE;
        int maximalX = Integer.MIN_VALUE;
        for(HashMap<Integer,Panel> values : panels.values()){
            minimalX = Math.min(minimalX, General.minimum(values.keySet()));
            maximalX = Math.max(maximalX, General.maximum(values.keySet()));
        }
        char[][] hull = new char[maximalX-minimalX+1][maximalY-minimalY+1];
        for(int coordinateY : panels.keySet()){
            for(int coordinateX: panels.get(coordinateY).keySet()){
                int color = panels.get(coordinateY).get(coordinateX).getColor();
                hull[maximalX-coordinateX][coordinateY-minimalY] = (color==1)? '\u2588' : 0;
            }
        }
        for(char[] row : hull){
            System.out.println(Arrays.toString(row).replace("[","").replace("]","").replace(", ",""));
        }
    }
}
