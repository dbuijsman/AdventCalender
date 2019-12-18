import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Wire {
    private int[] position = {0,0};
    private String[] route;
    private HashMap<Integer, HashSet<Integer>> coordinates = new HashMap<>();
    Wire(String[] route){
        this.route = route;
        for(String direction : route){
            int distance = Integer.parseInt(direction.substring(1));
            switch(direction.charAt(0)){
                case 'U':
                    Up(distance);
                    break;
                case 'D':
                    Down(distance);
                    break;
                case 'L':
                    Left(distance);
                    break;
                case 'R':
                    Right(distance);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }
    private void Up(int distance){
        if(distance>0) {
            this.position[1] += 1;
            addCoordinate(this.position);
            Up(distance-1);
        }
    }
    private void Down(int distance){
        if(distance>0) {
            this.position[1] -= 1;
            addCoordinate(this.position);
            Down(distance-1);
        }
    }
    private void Left(int distance){
        if(distance>0) {
            this.position[0] -= 1;
            addCoordinate(this.position);
            Left(distance-1);
        }
    }
    private void Right(int distance){
        if(distance>0) {
            this.position[0] += 1;
            addCoordinate(this.position);
            Right(distance-1);
        }
    }


    private void addCoordinate(int[] coordinate){
        HashSet<Integer> Y_Coordinates = this.coordinates.get(coordinate[0]);
        if(Y_Coordinates == null){
            this.coordinates.put(coordinate[0],new HashSet<>(Arrays.asList(coordinate[1])));
        } else {
            Y_Coordinates.add(coordinate[1]);
        }
    }

    public HashMap<Integer, HashSet<Integer>> getCoordinates() {
        return this.coordinates;
    }
    public int getStepsUntilCoordinate(int[] coordinate){
        this.position = new int[]{0,0};
        int steps = 0;
        for(String direction: route){
            int distance = Integer.parseInt(direction.substring(1));
            switch(direction.charAt(0)){
                case 'U':
                    steps = goUp(distance, coordinate, steps);
                    break;
                case 'D':
                    steps = goDown(distance, coordinate, steps);
                    break;
                case 'L':
                    steps = goLeft(distance, coordinate, steps);
                    break;
                case 'R':
                    steps = goRight(distance, coordinate, steps);
                    break;
            }
            if(this.position[0]==coordinate[0] && this.position[1]==coordinate[1]){
                return steps;
            }
        }
        return -1;
    }
    private int goUp(int distance, int[] endPoint, int numberOfSteps){
        if(distance>0) {
            this.position[1] += 1;
            numberOfSteps += 1;
            if(this.position[0]==endPoint[0] && this.position[1]==endPoint[1]){
                return numberOfSteps;
            } else {
                return goUp(distance - 1,endPoint, numberOfSteps);
            }
        } else {
            return numberOfSteps;
        }
    }
    private int goDown(int distance, int[] endPoint, int numberOfSteps){
        if(distance>0) {
            this.position[1] -= 1;
            numberOfSteps += 1;
            if(this.position[0]==endPoint[0] && this.position[1]==endPoint[1]){
                return numberOfSteps;
            } else {
                return goDown(distance - 1,endPoint, numberOfSteps);
            }
        } else {
            return numberOfSteps;
        }
    }
    private int goLeft(int distance, int[] endPoint, int numberOfSteps){
        if(distance>0) {
            this.position[0] -= 1;
            numberOfSteps += 1;
            if(this.position[0]==endPoint[0] && this.position[1]==endPoint[1]){
                return numberOfSteps;
            } else {
                return goLeft(distance - 1,endPoint, numberOfSteps);
            }
        } else {
            return numberOfSteps;
        }
    }
    private int goRight(int distance, int[] endPoint, int numberOfSteps) {
        if (distance > 0) {
            this.position[0] += 1;
            numberOfSteps += 1;
            if (this.position[0] == endPoint[0] && this.position[1] == endPoint[1]) {
                return numberOfSteps;
            } else {
                return goRight(distance - 1, endPoint, numberOfSteps);
            }
        } else {
            return numberOfSteps;
        }
    }
}
