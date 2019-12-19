import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class RepairRobot {
    private IntCode program;
    private int posX = 0;
    private int posY = 0;
    private HashMap<Integer, HashMap<Integer,Integer>> mapGrid = new HashMap<>();
    RepairRobot(IntCode program){
        this.program = program;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0,1);
        mapGrid.put(0, map);

    }
    public int start(){
        int count = 0;
        int newX = 0;
        int newY = 0;
        while(!program.isFinished()){
            int ans = getNextOption();
            program.run(ans);
            long statusCode = program.getLastOutput();
            switch(ans){
                case 1:
                    newX = posX;
                    newY = posY-1;
                    break;
                case 2:
                    newX = posX;
                    newY = posY+1;
                    break;
                case 3:
                    newX = posX-1;
                    newY = posY;
                    break;
                case 4:
                    newX = posX+1;
                    newY = posY;
                    break;
            }
            if(mapGrid.containsKey(newY)){
                mapGrid.get(newY).put(newX,(int)statusCode);
            } else {
                HashMap<Integer, Integer> map = new HashMap<>();
                map.put(newX,(int)statusCode);
                mapGrid.put(newY, map);
            }
            if(statusCode !=0){
                posX = newX;
                posY = newY;
                count++;
            }
            if(statusCode == 2){
                return count;
            }
            if(count%1_000==0){
                print();
                System.out.println("Location is (" + posX + ", " + posY + ").");
            }
        }
        return count;
    }
    private int getNextOption(){
        if(!mapGrid.containsKey(posY-1) || !mapGrid.get(posY-1).containsKey(posX)){
            return 1;
        }
        else if(!mapGrid.containsKey(posY+1) || !mapGrid.get(posY+1).containsKey(posX)){
            return 2;
        }
        else if(!mapGrid.get(posY).containsKey(posX-1)){
            return 3;
        }
        else if(!mapGrid.get(posY).containsKey(posX+1)){
            return 4;
        }
        else {
            print();
            throw new IllegalStateException("Already checked all sides of this tile! @pos (" + posX + ", " + posY + ")");
        }
    }
    public void startStepByStep(){
        while(!program.isFinished()){
            int ans = Integer.parseInt(answer());
            program.run(ans);
            long statusCode = program.getLastOutput();
            if(mapGrid.containsKey(posY+(4-ans)/2 * (2*ans-3))){
                mapGrid.get(posY+(4-ans)/2 * (2*ans-3)).put(posX+(ans-1)/2 * (2*ans-7),(int)statusCode);
            } else {
                HashMap<Integer, Integer> map = new HashMap<>();
                map.put(posX+(ans-1)/2 * (2*ans-7),(int)statusCode);
                mapGrid.put(posY+(4-ans)/2 * (2*ans-3), map);
            }
            if(statusCode !=0){
                posX += (ans-1)/2 * (2*ans-7);
                posY += (4-ans)/2 * (2*ans-3);
            }
            print();
        }
        print();
    }
    private String answer(){
        Scanner input = new Scanner(System.in);
        String ans = input.nextLine().toLowerCase();
        while(ans.equals("")){
            ans = input.nextLine().toLowerCase();
        }
        return ans;
    }
    private void print(){
        int minimalY = General.minimum(mapGrid.keySet());
        int maximalY = General.maximum(mapGrid.keySet());
        int minimalX = Integer.MAX_VALUE;
        int maximalX = Integer.MIN_VALUE;
        for(HashMap<Integer,Integer> values : mapGrid.values()){
            minimalX = Math.min(minimalY, General.minimum(values.keySet()));
            maximalX = Math.max(maximalY, General.maximum(values.keySet()));
        }
        char[][] grid = new char[maximalY-minimalY+1][maximalX-minimalX+1];
        for(int coordinateY : mapGrid.keySet()){
            for(int coordinateX: mapGrid.get(coordinateY).keySet()){
                grid[coordinateY-minimalY][coordinateX-minimalX] = toTile(mapGrid.get(coordinateY).get(coordinateX));
            }
        }
        grid[posY-minimalY][posX-minimalX] = 'D';
        for(char[] row : grid){
            System.out.println(Arrays.toString(row).replace("[","").replace("]","").replace(", ",""));
        }
        System.out.println("Standing on " + toTile(mapGrid.get(posY).get(posX)));
    }
    private char toTile(int statusCode){
        switch(statusCode){
            case 0:
                return '#';
            case 1:
                return '.';
            case 2:
                return 'O';
            default:
                return '\u0000';
        }
    }
}
