import java.util.ArrayList;
import java.util.Arrays;

public class FittingFunctions17 {
    private ArrayList<Character> route = new ArrayList<>();
    private Character[] mainRoutine;
    private int maxLengthFunction;
    private Character[] functionA;
    private Character[] functionB;
    private Character[] functionC;
    FittingFunctions17(ArrayList<Character> route, int maxLengthFunction){
        this.route = route;
        this.maxLengthFunction = maxLengthFunction;
    }

    public Character[] getMainRoutine() {
        return mainRoutine;
    }
    public Character[] getFunctionA() {
        return functionA;
    }
    public Character[] getFunctionB() {
        return functionB;
    }
    public Character[] getFunctionC() {
        return functionC;
    }

    public void makeRoutine(){
        for(int lengthA = 9*maxLengthFunction; lengthA>0; lengthA--){
            int index = 0;
            functionA = getFunction(index, lengthA);
            System.out.println(Arrays.toString(functionA));
            lengthA = functionA.length;
            for(int lengthB = 9*maxLengthFunction; lengthB>0; lengthB--){
                while(functionFits(index,functionA)){
                    index += lengthA;
                }
                functionB = getFunction(index, lengthB);
                lengthB = functionB.length;
                for(int lengthC = 9*maxLengthFunction; lengthC>0; lengthC--){
                    while(functionFits(index,functionA) || functionFits(index,functionB) ){
                        if(functionFits(index,functionA)){
                            index += lengthA;
                        } else {
                            index += lengthB;
                        }
                    }
                    functionC = getFunction(lengthA+lengthB, lengthC);
                    lengthC = functionC.length;
                    if(functionsFitInRoute()){
                        return;
                    }
                }
            }
        }
        System.out.println(functionA.length + ", " + functionB.length + ", " + functionC.length);
        throw new IllegalStateException("Can\'t find a fitting solution.");
    }

    /**
     * Checks if the functions A, B and C can fill the whole route.
     * @return true if possible and main is not too long.
     */
    private boolean functionsFitInRoute(){
        int index = 0;
        ArrayList<Character> main = new ArrayList<>();
        while(index<route.size()){
            if(functionFits(index,functionA)){
                main.add('A');
                index+= functionA.length;
            } else if(functionFits(index,functionB)){
                main.add('B');
                index+= functionB.length;
            } else if(functionFits(index,functionC)){
                main.add('C');
                index+= functionC.length;
            } else {
                return false;
            }
        }
        System.out.println(main.size());
        if(main.size()>maxLengthFunction){
            return false;
        } else {
            mainRoutine = main.toArray(new Character[0]);
            return true;
        }
    }

    /**
     * Builds a function bound by a given length and the final function should not be longer than the max length of a function.
     * @param index Starting index.
     * @param maxLength Maximum amount of steps to be taken.
     * @return char array that represents the function.
     */
    private Character[] getFunction(int index, int maxLength){
        int lengthFunction = 0;
        ArrayList<Character> function = new ArrayList<>();
        while(index<route.size() && lengthFunction<maxLengthFunction && function.size()<maxLength){
            char step = route.get(index);
            if(step=='A' || step=='B' || step=='C'){
                break;
            } else {
                function.add(step);
                if(step=='M'){
                    int moveSteps=1;
                    while(moveSteps<9 && index<route.size()-1 &&  route.get(index+1)=='M'){
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
        return function.toArray(new Character[0]);
    }
    private boolean functionFits(int index, Character[] function){
        if(function.length==0){
            return false;
        }
        for(int ind=0; ind<function.length; ind++){
            if(index+ind>= route.size()){
                return false;
            }
            if(!route.get(index+ind).equals(function[ind])){
                return false;
            }
        }
        return true;
    }
}
