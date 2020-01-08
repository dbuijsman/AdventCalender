import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class General {
    private String getDir(){
        return this.getClass().getName();
    }
    static String[] importResource(String fileName){
        ArrayList<String> ListOfLines = new ArrayList<>();
        String pathName = General.class.getResource("").getPath();
        try(Scanner resource = new Scanner( new FileReader(pathName.substring(0,pathName.indexOf("AdventCalender")) + "\\AdventCalender\\resources\\" + fileName + ".txt"))){
            while (resource.hasNext()) {
                String line = resource.nextLine();
                ListOfLines.add(line);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return ListOfLines.toArray(new String[0]);
    }
    static ArrayList<Long> importIntCode(String fileName){
        ArrayList<Long> intCode = new ArrayList<>();
        String[] input = General.importResource(fileName);
        for(String line: input){
            String[] parameters = line.split(",");
            for(String parameter: parameters){
                intCode.add(Long.parseLong(parameter));
            }
        }
        return intCode;
    }
    static int gcd(int number1, int number2){
        number1 = Math.abs(number1);
        number2 = Math.abs(number2);
        while(number1*number2!=0){
            if(number1>number2){
                number1 -= number2;
            } else {
                number2 -= number1;
            }
        }
        return Math.max(1,Math.max(number1,number2));
    }
    static boolean isCloseTo(double number1, double number2){
            return Math.abs(number1-number2)<0.0005;
    }
    static int minimum(Collection<Integer> numbers){
        int minimum = Integer.MAX_VALUE;
        for(int number : numbers){
            if(number<minimum){
                minimum = number;
            }
        }
        return minimum;
    }
    static int maximum(Collection<Integer> numbers){
        int minimum = Integer.MIN_VALUE;
        for(int number : numbers){
            if(number>minimum){
                minimum = number;
            }
        }
        return minimum;
    }
    static long multiplicativeInverseModulo(long number, long modulo){
        if(number<0){
            while(number<0){
                number+= modulo;
            }
        }
        if(number>modulo){
            number = number%modulo;
        }
        long r_0 = modulo;
        long r_1 = number;
        long r_2 = 1;
        long t_0 = 0;
        long t_1 = 1;
        long t_2;
        long q;
        while(r_2 > 0){
            r_2 = r_0%r_1;
            q = (r_0-r_2)/r_1;
            t_2 = t_0-t_1*q;
            r_0 = r_1;
            r_1 = r_2;
            t_0 = t_1;
            t_1 = t_2;
        }
        while(t_0<0){
            t_0+= modulo;
        }
        return t_0%modulo;
    }
    static long multiplyModulo(long a, long b, long modulo){
        long result = 0;
        boolean isDone = false;
        int lengthModulo = ("" + modulo).length();
        int subtractPower = Math.min(lengthModulo,18-lengthModulo);
        int power = lengthModulo/subtractPower*subtractPower;
        while(!isDone){
            try{
                long mult = Math.multiplyExact(a,b)%modulo;
                isDone = true;
                result = (result+mult)%modulo;
            } catch(ArithmeticException e){
                long subtract = a/(long)Math.pow(10,power);
                long newMultiple = Math.multiplyExact(subtract,b)%modulo;
                for(int n=0;n<power;n++){
                    newMultiple = Math.multiplyExact(newMultiple,10)%modulo;
                }
                result = (result + newMultiple)%modulo;
                a = a%(long)Math.pow(10,power);
                power -= subtractPower;
            }
        }
        return result;
    }
}
