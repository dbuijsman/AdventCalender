import java.util.HashMap;

public class Day6Orbits {
    public static void main(String[] args){
        ObjectInSpace COM = null;
        HashMap<String, ObjectInSpace> objects = importOrbits();
        for(ObjectInSpace object:objects.values()){
            if(object.getOrbitsAround()==null){
                COM = object;
                break;
            }
        }
        System.out.println("The total number of direct and indirect orbits is " + COM.getOrbitCountCheckSum());
        ObjectInSpace objectYou = objects.get("YOU").getOrbitsAround();
        ObjectInSpace objectSanta = objects.get("SAN").getOrbitsAround();
        System.out.println("The minimum number of orbit transfers required is " + objectYou.findDistanceToObject(objectSanta));
    }

    private static HashMap<String, ObjectInSpace> importOrbits(){
        HashMap<String, ObjectInSpace> objects = new HashMap<>();
        String[] input = General.importResource("Day6 Orbits");
        for(String line: input){
            String[] lineSplit = line.split("\\)");
            if(lineSplit.length!=2){
                throw new IllegalArgumentException("Every line must contain exactly one orbit");
            } else {
                for(int index=0;index<2;index++){
                    if(!objects.keySet().contains(lineSplit[index])){
                        objects.put(lineSplit[index], new ObjectInSpace(lineSplit[0]));
                    }
                }
                Orbit.add(objects.get(lineSplit[0]),objects.get(lineSplit[1]));
            }
        }
        return objects;
    }
}
