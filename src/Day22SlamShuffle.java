import java.util.ArrayList;

public class Day22SlamShuffle {
    static SpaceDeck deck;
    static long deckSize;
    static String[] shuffleRoutine;
    static long timesShuffle;
    static ArrayList<long[]> linearFunctionRoutine;
    public static void main(String[] args){
        shuffleRoutine = General.importResource("Day22 Slam Shuffle");
        deckSize = 10007;
        deck = new SpaceDeck(deckSize);
        long card = shuffle(2019);
        System.out.println("The card 2019 ends on position " + card);

        deckSize = 119315717514047L;
        deck = new SpaceDeck(deckSize);
        timesShuffle = 101741582076661L;
        makeLinearFunctionsForRepeating();
        String timesShuffleAsBinary = Long.toBinaryString(timesShuffle);
        card = 2020;
        for(int power=timesShuffleAsBinary.length()-1; power>=0; power--) {
            if (timesShuffleAsBinary.charAt(timesShuffleAsBinary.length()-1-power) == '1') {
                long[] linearFunction = linearFunctionRoutine.get(power);
                card = (General.multiplyModulo(linearFunction[0], card, deckSize) + linearFunction[1]) % deckSize;
            }
        }
        if(card<0){
            card+=deckSize;
        }
        System.out.println("On position 2020 we have the card " + card);
    }

    static long shuffle(long card){
        for(String shuffle : shuffleRoutine){
            card = deck.shuffle(shuffle, card);
            if(card<0){
                throw new IllegalStateException("Routine " + shuffle + " returns a negative card.");
            }
        }
        return card;
    }
    static long shuffleReverse(long card){
        for(int index = shuffleRoutine.length-1; index>=0; index--){
            card = deck.shuffleReverse(shuffleRoutine[index], card);
            if(card<0){
                throw new IllegalStateException("Routine " + shuffleRoutine[index] + " returns a negative card.");
            }
        }
        return card;
    }
    static void makeLinearFunctionsForRepeating(){
        linearFunctionRoutine = new ArrayList<>();
        long constant = shuffleReverse(0);
        long slope = shuffleReverse(1)-constant;
        linearFunctionRoutine.add(new long[]{slope,constant});
        long nextToAdd = 2;
        while(nextToAdd<=timesShuffle){
            constant = (constant + General.multiplyModulo(slope,constant,deckSize))%deckSize;
            slope = General.multiplyModulo(slope,slope,deckSize);
            linearFunctionRoutine.add(new long[]{slope,constant});
            nextToAdd *= 2;
        }
    }
}
