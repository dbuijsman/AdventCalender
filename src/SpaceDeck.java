import java.util.ArrayList;
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
        boolean isDone = false;
        long result = 0;
        int power = 11;
        long powerFrom10 = (long)Math.pow(10,power);
        int count = 1;
        while(!isDone){
            try{
                long mult = Math.multiplyExact(inverse,positionCard)%deckSize;
                isDone = true;
                result = (result+mult)%deckSize;
            } catch(ArithmeticException e){
                count++;
                long subtract = inverse/powerFrom10;
                long newMultiple = Math.multiplyExact(subtract,positionCard)%deckSize;
                for(int n=0;n<power;n++){
                    newMultiple = Math.multiplyExact(newMultiple,10)%deckSize;
                }
                result = (result + newMultiple)%deckSize;
                inverse = inverse%powerFrom10;
                powerFrom10 /= 1_000;
                power -= 3;
            }
        }
        return result;
//        try{
//            System.out.println("Jeeeej");
//            return Math.multiplyExact(inverse,positionCard)%deckSize;
//        } catch(ArithmeticException e){
//            e.printStackTrace();
//            System.out.println(new String("" + inverse).length() + "       " + inverse);
//            try{
//                long next = inverse/1000_000_000;
//                System.out.println("Lowering order: " + Math.multiplyExact(next,positionCard)%deckSize);
//                System.out.println(next);
//                long nogNext = Long.MAX_VALUE/positionCard;
//                System.out.println("With max possible value for inverse " + Math.multiplyExact(nogNext,positionCard)%deckSize);
//                System.out.println(nogNext);
//            } catch(ArithmeticException r){
//                r.printStackTrace();
//                System.out.println("Again....");
//            }
//            long maxAdding = Long.MAX_VALUE/positionCard;
//            if(maxAdding>inverse){
//                return (inverse*positionCard)%deckSize;
//            } else {
//                long result = 0;
//                while(inverse>maxAdding){
//                    result = (result + (positionCard*77301))%deckSize;
//                    inverse-=77301;
//                }
//                System.out.println("Done");
//                return (result+inverse*positionCard)%deckSize;
//            }
//        }
    }
}
