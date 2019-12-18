import java.util.ArrayList;
import java.util.Arrays;

public class Day7Amplifiers {
    public static void main(String[] args){
        long maxOutput = 0;
        ArrayList<Long> input = General.importIntCode("Day7 Amplifiers");
        ArrayList<ArrayList<Integer>> permutations = getAllPermutations(5,9);
        Amplifier amplifier = new Amplifier(input, new ArrayList<>(Arrays.asList(0,0,0,0,0)));
        for(ArrayList<Integer> permutation : permutations){
            amplifier.setPhase(permutation);
            long output = amplifier.run(0);
            if (output > maxOutput) {
                maxOutput = output;
            }
        }

        System.out.println("Final output is " + maxOutput);
    }
    private static ArrayList<ArrayList<Integer>> getAllPermutations(int start, int end){
        if(start==end){
            ArrayList<ArrayList<Integer>> permutations = new ArrayList<>();
            permutations.add(new ArrayList<>(Arrays.asList(start)));
            return permutations;
        } else {
            ArrayList<ArrayList<Integer>> oldPermutations = getAllPermutations(start,end-1);
            ArrayList<ArrayList<Integer>> permutations = new ArrayList<>();
            for(ArrayList<Integer> permutation : oldPermutations){
                for(int index=0;index<=permutation.size();index++){
                    ArrayList<Integer> element = new ArrayList<>(permutation);
                    element.add(index,end);
                    permutations.add(element);
                }
            }
            return permutations;
        }
    }
}
