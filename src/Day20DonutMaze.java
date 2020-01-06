import java.util.Arrays;

public class Day20DonutMaze {
    public static void main(String[] args){
        String[] imported = General.importResource("Day20 DonutMaze");
        char[][] mazeAsArray = new char[imported.length][];
        for(int index=0;index<imported.length;index++){
            mazeAsArray[index] = imported[index].toCharArray();
        }
        DonutMaze maze = new DonutMaze(mazeAsArray);
        System.out.println("The minimal needed steps are " + maze.walk());
    }
}
