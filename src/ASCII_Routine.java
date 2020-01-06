import java.util.ArrayList;

public class ASCII_Routine {
    private ArrayList<String> route;
    private Character[] mainRoutine;
    private int maxLengthFunction;
    private String[] functionA;
    private String[] functionB;
    private String[] functionC;
    private int closestToEnd;
    ASCII_Routine(ArrayList<String> route, int maxLengthFunction){
        this.route = route;
        this.maxLengthFunction = maxLengthFunction+1;
    }

    public Character[] getMainRoutine() {
        return mainRoutine;
    }
    public String[] getFunctionA() {
        return functionA;
    }
    public String[] getFunctionB() {
        return functionB;
    }
    public String[] getFunctionC() {
        return functionC;
    }

    public void makeRoutine(){
        closestToEnd = route.size();
        for(int lengthA = maxLengthFunction; lengthA>0; lengthA--){
            int index = 0;
            functionA = getFunction(index, lengthA);
            for(int lengthB = maxLengthFunction; lengthB>0; lengthB--){
                index = 0;
                while(functionFits(index,functionA)){
                    index += functionA.length;
                }
                functionB = getFunction(index, lengthB);
                for(int lengthC = maxLengthFunction; lengthC>0; lengthC--){
                    index = 0;
                    while(functionFits(index,functionA) || functionFits(index,functionB) ){
                        if(functionFits(index,functionA)){
                            index += functionA.length;
                        } else {
                            index += functionB.length;
                        }
                    }
                    functionC = getFunction(index, lengthC);
                    if(functionsFitInRoute()){
                        return;
                    }
                }
            }
        }
        throw new IllegalStateException("Can\'t find a fitting solution. Closest option got to " + closestToEnd + " of " + route.size());
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
                if(route.size()-index<closestToEnd){
                    closestToEnd = route.size()-index;
                }
                return false;
            }
        }
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
    private String[] getFunction(int index, int maxLength){
        int lengthFunction = 0;
        ArrayList<String> function = new ArrayList<>();
        while(index<route.size() && lengthFunction<maxLength){
            String step = route.get(index);
            function.add(step);
            lengthFunction+=step.length()+1;
            index++;
        }
        return function.toArray(new String[0]);
    }
    private boolean functionFits(int index, String[] function){
        if(function.length==0){
            return false;
        }
        for(int ind=0; ind<function.length; ind++){
            if(index+ind>=route.size()){
                return false;
            }
            if(!route.get(index+ind).equals(function[ind])){
                return false;
            }
        }
        return true;
    }
    private int getLength(String[] function){
        int length = function.length;
        for(String str : function){
            length += str.length();
        }
        return length;
    }
}
