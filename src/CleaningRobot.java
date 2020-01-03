import java.util.ArrayList;

public class CleaningRobot {
    private ArrayList<ArrayList<Character>> map;
    private int[] location = new int[2];
    private int direction;
    private ArrayList<Character> route = new ArrayList<>();
    CleaningRobot(ArrayList<ArrayList<Character>> map){
        this.map = map;
    }
    public ArrayList<Character> getRoute() {
        return route;
    }
    private void findLocation(){
        for(int y=0; y<map.size();y++){
            for(int x=0; x<map.get(y).size(); x++){
                char point = map.get(y).get(x);
                if(point=='^' || point=='>' || point=='v' || point=='<'){
                    location[0] = x;
                    location[1] = y;
                    switch(point){
                        case '^':
                            direction = 0;
                            break;
                        case '>':
                            direction = 1;
                            break;
                        case 'v':
                            direction = 2;
                            break;
                        case '<':
                            direction = 3;
                            break;
                    }
                }
            }
        }
    }
    public ArrayList<Character> findRoute(){
        findLocation();
        route = new ArrayList<>();
        int steps = 0;
        while(true){
            if(positionFrontFree()){
                location = getPositionFront();
                route.add('M');
                steps ++;
            }else if(positionRightFree()){
                route.add('R');
                steps = 0;
                direction = (direction+1)%4;
            } else if(positionLeftFree()){
                route.add('L');
                steps = 0;
                direction = (direction+3)%4;
            }else{
                break;
            }
        }
        ArrayList<Character> function = getFunction(0,90);
        System.out.println(function.toString());
        System.out.println(function.size());
        System.out.println(functionFits(0,function));
        System.out.println(route.get(function.size()-1));
        return route;
    }
    private boolean positionFrontFree(){
        return positionIsFree(getPositionFront());
    }
    private boolean positionLeftFree(){
        return positionIsFree(getPositionLeft());
    }
    private boolean positionRightFree(){
        return positionIsFree(getPositionRight());
    }
    private boolean positionIsFree(int[] position){
        // y-position is unavailable.
        if(position[1]<0 || position[1]>=map.size()){
            return false;
        }
        // x-position is unavailable.
        if(position[0]<0 || position[0]>= map.get(position[1]).size()){
            return false;
        }
        return map.get(position[1]).get(position[0]) == '#' || map.get(position[1]).get(position[0]) == 'O';
    }
    private int[] getPositionFront(){return getPositionFront(direction);}
    private int[] getPositionLeft(){return getPositionFront(direction-1);}
    private int[] getPositionRight(){return getPositionFront(direction+1);}
    private int[] getPositionFront(int direction){
        int x = location[0];
        int y = location[1];
        switch(direction%4){
            case 0:
                y --;
                break;
            case 1:
                x++;
                break;
            case 2:
                y ++;
                break;
            case 3:
                x--;
                break;
        }
        return new int[]{x,y};
    }
    private ArrayList<Character> getFunction(int index, int maxLength){
        int lengthFunction = 0;
        ArrayList<Character> function = new ArrayList<>();
        while(index<route.size() && lengthFunction<10 && function.size()<maxLength){
            char step = route.get(index);
            if(step=='A' || step=='B' || step=='C'){
                break;
            } else {
                function.add(step);
                if(step=='M'){
                    int moveSteps=1;
                    while(moveSteps<9 && route.get(index+1)=='M'){
                        if(function.size()==maxLength){
                            break;
                        }
                        index++;
                        function.add('M');
                        moveSteps++;
                    }
                }
                lengthFunction++;
            }
            index++;
        }
        return function;
    }
    private boolean functionFits(int index, ArrayList<Character> function){
        for(int ind=0; ind<function.size(); ind++){
            if(index+ind>= route.size()){
                return false;
            }
            if(!route.get(index+ind).equals(function.get(ind))){
                return false;
            }
        }
        return true;
    }
}
