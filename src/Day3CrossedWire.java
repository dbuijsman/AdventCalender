import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Day3CrossedWire {
    public static void main(String[] args){
        ArrayList<Wire> wires = importWires();
        System.out.println(getClosestIntersectionManhattanDistance(wires.get(0),wires.get(1)));
        System.out.println(getClosestIntersectionTotalSteps(wires.get(0),wires.get(1)));
    }
    private static ArrayList<Wire> importWires(){
        ArrayList<Wire> wires = new ArrayList<>();
        String[] input = General.importResource("Day3 wires");
        for(String line: input){
            wires.add(new Wire(line.split(",")));
        }
        return wires;
    }
    static int[][] getIntersectionPoints(Wire wire1, Wire wire2){
        ArrayList<int[]> intersectionPoints = new ArrayList<>();
        HashMap<Integer, HashSet<Integer>> wire1Coordinates = wire1.getCoordinates();
        HashMap<Integer, HashSet<Integer>> wire2Coordinates = wire2.getCoordinates();
        for(int x_coordinate : wire1Coordinates.keySet()){
            HashSet<Integer> y_coordinatesWire1 = wire1Coordinates.get(x_coordinate);
            HashSet<Integer> y_coordinatesWire2 = wire2Coordinates.get(x_coordinate);
            if(y_coordinatesWire2!=null) {
                for (int y_coordinate : y_coordinatesWire2) {
                    if (y_coordinatesWire1.contains(y_coordinate)) {
                        intersectionPoints.add(new int[]{x_coordinate, y_coordinate});
                    }
                }
            }
        }
        return intersectionPoints.toArray(new int[0][]);
    }
    static int getClosestIntersectionManhattanDistance(Wire wire1, Wire wire2){
        int distance = Integer.MAX_VALUE;
        int[][] intersectionPoints = getIntersectionPoints(wire1,wire2);
        for(int[] intersection : intersectionPoints){
            distance = Math.min(distance,Math.abs(intersection[0])+Math.abs(intersection[1]));
        }
        return distance;
    }
    static int getClosestIntersectionTotalSteps(Wire wire1, Wire wire2){
        int distance = Integer.MAX_VALUE;
        int[][] intersectionPoints = getIntersectionPoints(wire1,wire2);
        for(int[] intersection : intersectionPoints){
            int stepsWire1 = wire1.getStepsUntilCoordinate(intersection);
            int stepsWire2 = wire2.getStepsUntilCoordinate(intersection);
            if(stepsWire1>0 && stepsWire2>0) {
                distance = Math.min(distance, stepsWire1+stepsWire2);
            }
        }
        return distance;
    }
}
