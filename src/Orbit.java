public class Orbit {
    static void add(ObjectInSpace mass, ObjectInSpace orbit){
        mass.addOrbit(orbit);
        orbit.setOrbitsAround(mass);
        if(orbit.getOrbitsAround()==null){
            System.out.println(orbit.getName());
        }
    }
}
