import java.util.HashMap;

public class SpaceDeck {
    private long deckSize;
    private HashMap<Long, Long> multiplicativeInverses = new HashMap<>();
    SpaceDeck(long deckSize){
        this.deckSize = deckSize;
    }

    public long shuffle(String shuffleMethod, long positionCard){
        if(shuffleMethod.equals("deal into new stack")){
            return dealIntoNewStack(positionCard);
        } else if(shuffleMethod.startsWith("cut ")){
            return cut(Integer.parseInt(shuffleMethod.substring(4)), positionCard);
        } else if(shuffleMethod.startsWith("deal with increment ")){
            return dealWithIncrement(Integer.parseInt(shuffleMethod.substring(20)), positionCard);
        } else {
            throw new IllegalArgumentException(shuffleMethod + " is an invalid way to shuffle.");
        }
    }
    public long shuffleReverse(String shuffleMethod, long positionCard){
        if(shuffleMethod.equals("deal into new stack")){
            return dealIntoNewStack(positionCard);
        } else if(shuffleMethod.startsWith("cut ")){
            return cutInverse(Integer.parseInt(shuffleMethod.substring(4)), positionCard);
        } else if(shuffleMethod.startsWith("deal with increment ")){
            return dealWithIncrementInverse(Integer.parseInt(shuffleMethod.substring(20)), positionCard);
        } else {
            throw new IllegalArgumentException(shuffleMethod + " is an invalid way to shuffle.");
        }
    }

    private long dealIntoNewStack(long positionCard){
        return deckSize-1-positionCard;
    }
    private long cut(int N, long positionCard){
        return (positionCard-N+deckSize)%deckSize;
    }
    private long cutInverse(int N, long positionCard){
        return (positionCard+N+deckSize)%deckSize;
    }
    private long dealWithIncrement(int N, long positionCard){
        return (N*positionCard)%deckSize;
    }
    private long dealWithIncrementInverse(long N, long positionCard){
        long inverse = 0;
        if(multiplicativeInverses.containsKey(N)){
            inverse = multiplicativeInverses.get(N);
        } else {
            inverse = General.multiplicativeInverseModulo(N,deckSize);
            multiplicativeInverses.put(N,inverse);
            multiplicativeInverses.put(inverse,N);
        }
        return General.multiplyModulo(inverse,positionCard,deckSize);
    }
}
