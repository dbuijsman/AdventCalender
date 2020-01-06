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
}
