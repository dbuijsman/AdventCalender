public class Day4Passwords {
    public static void main(String[] args){
        System.out.println("Number of possible passwords between 367479 and 893698 is " + getNumberOfPasswordsInRange(367479, 893698));
    }
    static int getNumberOfPasswordsInRange(int start, int end){
        int count = 0;
        LOOP_INT: for(int number=start;number<end;number++){
            int[] arrayNumber = intToArray(number);
            for(int index=0;index<5;index++){
                if(arrayNumber[index]>arrayNumber[index+1]){
                    continue LOOP_INT;
                }
            }
            for(int index=0;index<5;index++){
                if(arrayNumber[index]!=arrayNumber[index+1]){
                    continue;
                }
                if(index==4 || arrayNumber[index]!=arrayNumber[index+2]){
                    count++;
                    continue LOOP_INT;
                }
                while(index<5 && arrayNumber[index]==arrayNumber[index+1]){
                    index++;
                }

            }

        }
        return count;
    }
    private static int[] intToArray(int number){
        String numberString = Integer.toString(number);
        int[] array = new int[numberString.length()];
        for (int index = 0; index < numberString.length(); index++)
        {
            array[index] = numberString.charAt(index) - '0';
        }
        return array;
    }
}
