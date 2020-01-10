import java.util.ArrayList;

public class Day18MazeWithKeys {
    public static void main(String[] args){
        String[] input = General.importResource("Day18 Maze with keys");
        char[][] mazeAsArray = new char[input.length][];
        for(int index=0;index<input.length;index++){
            mazeAsArray[index] = input[index].toCharArray();
        }
        MazeKeys maze = new MazeKeys(mazeAsArray);
        System.out.println("The minimum steps in order to get all the keys is " + maze.walk());
    }
}
