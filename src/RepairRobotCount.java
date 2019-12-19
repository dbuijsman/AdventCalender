import java.util.*;

public class RepairRobotCount {
    private IntCode program;
    private int posX = 0;
    private int posY = 0;
    private HashMap<Integer, HashMap<Integer,Integer>> mapGrid = new HashMap<>();
    RepairRobotCount(IntCode program){
        this.program = program;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0,0);
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
            if(statusCode !=0){
                posX = newX;
                posY = newY;
                count++;
                if(mapGrid.containsKey(newY)){
                    if(mapGrid.get(posY).containsKey(posX)){
                        count = mapGrid.get(posY).get(posX);
                    } else {
                        mapGrid.get(posY).put(posX,count);
                    }
                } else {
                    HashMap<Integer, Integer> map = new HashMap<>();
                    map.put(posX,count);
                    mapGrid.put(posY, map);
                }
            }
            if(statusCode == 2){
                return count;
            }
        }
        return count;
    }
    private int getNextOption(){
        return (new Random().nextInt(4))+1;
    }
}
