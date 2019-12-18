import java.util.ArrayList;

public class Day1FuelRequirements {
    public static void main(String[] args){
        int fuelRequired = 0;
        Integer[] arrayMasses = makeArrayOfMasses();
        for(int mass:arrayMasses){
            fuelRequired+= getFuelNeeded(mass);
        }
        System.out.println(fuelRequired);
    }
    private static int getFuelNeeded(int mass){
        int fuelNeeded = 0;
        while(mass>0){
            mass = Math.max(0,mass/3-2);
            fuelNeeded += mass;
        }
        return fuelNeeded;
    }
    private static Integer[] makeArrayOfMasses(){
        ArrayList<Integer> arrayMasses = new ArrayList<>();
        String[] input = General.importResource("Day1 modules");
        for(String line: input){
            arrayMasses.add(Integer.valueOf(line));
        }
        return arrayMasses.toArray(new Integer[0]);
    }

}
