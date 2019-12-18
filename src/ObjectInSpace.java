import java.util.ArrayList;

public class ObjectInSpace {
    private String name;
    private ObjectInSpace orbitsAround;
    private ArrayList<ObjectInSpace> orbits;
    ObjectInSpace(String name){
        this.name = name;
        this.orbits = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public ObjectInSpace getOrbitsAround() {
        return this.orbitsAround;
    }

    public void setOrbitsAround(ObjectInSpace orbitsAround){
        if(this.orbitsAround!=null) {
            throw new IllegalArgumentException("This object already orbits around something.");
        } else {
            this.orbitsAround = orbitsAround;
        }
    }
    public void addOrbit(ObjectInSpace orbit){
        this.orbits.add(orbit);
    }


    public int getOrbitCountCheckSum(){
        if(this.orbitsAround == null){
            return getTotalOrbits();
        } else {
            return this.orbitsAround.getOrbitCountCheckSum();
        }
    }
    private int getTotalOrbits(){
        int totalOrbits = getDistanceToCOM();
        for(ObjectInSpace orbit: orbits){
            totalOrbits += orbit.getTotalOrbits();
        }
        return totalOrbits;
    }
    public int getDistanceToCOM(){
        if(this.orbitsAround==null){
            return 0;
        } else {
            return orbitsAround.getDistanceToCOM()+1;
        }
    }
    public ObjectInSpace findObjectInSpaceFromCOM(String name){
        if(this.orbitsAround == null){
            return findObjectInSpace(name);
        } else {
            return this.orbitsAround.findObjectInSpaceFromCOM(name);
        }
    }
    private ObjectInSpace findObjectInSpace(String name){
        if(this.name.equals(name)){
            return this;
        } else {
            for(ObjectInSpace orbit: orbits){
                ObjectInSpace result = findObjectInSpace(name);
                if(result != null){
                    return result;
                }
            }
            return null;
        }
    }
    public int findDistanceToObject(ObjectInSpace otherObject){
        int travelOther = 0;
        while(otherObject.getDistanceToCOM()>this.getDistanceToCOM()){
            travelOther++;
            otherObject = otherObject.getOrbitsAround();
        }
        if(otherObject.equals(this)){
            return travelOther;
        } else {
            return travelOther + this.orbitsAround.findDistanceToObject(otherObject)+1;
        }
    }
}
