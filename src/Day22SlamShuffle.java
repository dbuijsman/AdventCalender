import java.util.ArrayList;
import java.util.Arrays;
//[10923173; 2, 2, 1, 1, 5, 1, 7, 3, 4, 1, 3, 1, 4, 1, 86, 1, 1, 3, 5, 1, 49, 1, 1, 1, 1, 7, 1, ...]
public class Day22SlamShuffle {
    public static void main(String[] args){
        String[] shuffleRoutine = General.importResource("Day 22 Slam Shuffle");
        long deckSize = 10007;
        SpaceDeck deck = new SpaceDeck(deckSize);
        long card = 2019;
        for(String shuffle : shuffleRoutine){
            card = deck.shuffle(shuffle, card);
        }
        System.out.println("The card 2019 ends on position " + card);
        deckSize = 119315717514047L;
        deck = new SpaceDeck(deckSize);
        card = 2020;
        for(long repeating=0; repeating<101741582076661L; repeating++){
            for(int index = shuffleRoutine.length-1; index>=0; index--){
                long prev = card;
                card = deck.shuffleReverse(shuffleRoutine[index],card);
                if(card<0){
                    throw new IllegalStateException("Routine " + shuffleRoutine[index] + " returns a negative card. Prev card was " + prev);
                }
            }
        }
        System.out.println("On position 2020 we have the card " + card);


    }
}
