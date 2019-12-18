import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Arcade {
    private int score;
    private IntCode program;
    private HashMap<Integer, HashMap<Integer,Integer>> mapGrid = new HashMap<>();
    private int[][] grid;
    private int directionBall=0;
    private int ballX=0;
    private int paddleX=0;
    Arcade(IntCode program){
        this.program = program;
        program.run(0);
        makeField();
    }
    private void makeField(){
        ArrayList<Long> outputs = program.getOutputs();
        int index=0;
        while(index<outputs.size()-2){
            if(outputs.get(index)==-1){
                score = (int)(long)outputs.get(index+2);
                index+=3;
                continue;
            }
            if(mapGrid.containsKey((int)(long)outputs.get(index))){
                mapGrid.get((int)(long)outputs.get(index)).put((int)(long)outputs.get(index+1),(int)(long)outputs.get(index+2));
            } else {
                HashMap<Integer, Integer> map = new HashMap<>();
                map.put((int)(long)outputs.get(index+1),(int)(long)outputs.get(index+2));
                mapGrid.put((int)(long)outputs.get(index), map);
            }
            index += 3;
        }
        toGrid(mapGrid);

    }
    private void toGrid(HashMap<Integer, HashMap<Integer,Integer>> mapGrid){
        int minimalX = General.minimum(mapGrid.keySet());
        int maximalX = General.maximum(mapGrid.keySet());
        int minimalY = Integer.MAX_VALUE;
        int maximalY = Integer.MIN_VALUE;
        for(HashMap<Integer,Integer> values : mapGrid.values()){
            minimalY = Math.min(minimalY, General.minimum(values.keySet()));
            maximalY = Math.max(maximalY, General.maximum(values.keySet()));
        }
        this.grid = new int[maximalY-minimalY+1][maximalX-minimalX+1];
        for(int coordinateX : mapGrid.keySet()){
            for(int coordinateY: mapGrid.get(coordinateX).keySet()){
                grid[coordinateY-minimalY][coordinateX-minimalX] = mapGrid.get(coordinateX).get(coordinateY);
                if(mapGrid.get(coordinateX).get(coordinateY)==4){
                    ballX = coordinateX;
                }
                if(mapGrid.get(coordinateX).get(coordinateY)==3){
                    paddleX = coordinateX;
                }
            }
        }
    }
    public void start(){
        while(!program.isFinished()){
            if(ballX<paddleX){
                program.run(-1);
            } else if(ballX>paddleX){
                program.run(1);
            } else {
                program.run(0);
            }
            makeField();
        }
        print();
    }
    public void startStepByStep(){
        while(!program.isFinished()){
            print();
            answer();
            if(ballX<paddleX){
                program.run(-1);
            } else if(ballX>paddleX){
                program.run(1);
            } else {
                program.run(0);
            }
            makeField();
        }
        print();

    }
    private String answer(){
        Scanner input = new Scanner(System.in);
        String ans = input.nextLine().toLowerCase();
        return ans;
    }

    public void print(){
        char[][] printedGrid = new char[grid.length][grid[0].length];
        for(int coordinateY=0;coordinateY<grid.length;coordinateY++){
            for(int coordinateX=0;coordinateX<grid[0].length;coordinateX++){
                printedGrid[coordinateY][coordinateX] = toTile(grid[coordinateY][coordinateX]);
            }
        }
        for(char[] row : printedGrid){
            System.out.println(Arrays.toString(row).replace("[","").replace("]","").replace(", ",""));
        }
        System.out.println("Score " + score);
    }
    public char toTile(int number){
        switch(number){
            case 0: return 0;
            case 1: return '\u2588';
            case 2: return 'X';
            case 3: return '-';
            case 4: return 'O';
            default:
                throw new IllegalArgumentException("Number must have a value of 0, 1, 2, 3 or 4.");
        }
    }
}
