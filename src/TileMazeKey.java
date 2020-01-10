import java.util.*;

public class TileMazeKey {
    private static int minimum = Integer.MAX_VALUE;
    private char key;
    private char door;
    private ArrayList< TileMazeKey > neighbours;
    private HashMap< HashSet<Character>, StepCheckerMazeKeys> minimalSteps= new HashMap<>();
    TileMazeKey(char value){
        if(Character.isLowerCase(value)){
            key = value;
        }
        if(Character.isUpperCase(value)){
            door = value;
        }
        neighbours = new ArrayList<>();
    }

    public static int getMinimum() {
        return minimum;
    }

    private boolean isKey(){
        return key!='\u0000';
    }
    private boolean isDoor(){
        return door!='\u0000';
    }
    public void addNeighbour(TileMazeKey neighbour){
        neighbours.add(neighbour);
    }
    public void setStart(){
        minimalSteps.put(new HashSet<>(), new StepCheckerMazeKeys(0));
    }
    private void setStep(HashSet<Character> keys, int steps){
        steps++;
        if(steps>=minimum){
            return;
        }
        if(isKey() && !keys.contains(key)){
            HashSet<Character> newKeys = new HashSet<>(keys);
            newKeys.add(key);
            keys = newKeys;
            if(keys.size()==26){
                minimum = steps;
            }
        }
        if(isDoor() && !keys.contains(Character.toLowerCase(door))){
            return;
        }
        for(HashSet<Character> existingKeys : minimalSteps.keySet()){
            if(existingKeys.containsAll(keys)){
                int oldSteps = minimalSteps.get(existingKeys).getMinimalSteps();
                if(oldSteps < steps){
                    return;
                }
                if(keys.containsAll(existingKeys) && oldSteps==steps){
                    return;
                }
            }
        }
        this.minimalSteps.put(keys,new StepCheckerMazeKeys(steps));
    }
    public void walk(){
        for(HashSet<Character> keys : minimalSteps.keySet()){
            StepCheckerMazeKeys values = minimalSteps.get(keys);
            if(values.getMinimalSteps()==100_000){
                System.out.println(keys.size());
            }
            if(values.isChecked()){
                continue;
            }
            if(values.getMinimalSteps()+1>=minimum){
                values.setChecked(true);
                continue;
            }
            for(TileMazeKey neighbour : neighbours){
                neighbour.setStep(keys, values.getMinimalSteps());
            }
            values.setChecked(true);
        }
    }
    public boolean isDone(){
        for(StepCheckerMazeKeys values : minimalSteps.values()){
            if(!values.isChecked()){
                return false;
            }
        }
        return true;
    }
}
