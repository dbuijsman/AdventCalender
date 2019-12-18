import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MapOfAsteroids {
    private double offset = 0.05;
    private ArrayList<Asteroid> map;
    MapOfAsteroids(String[] mapAsString){
        this.map = new ArrayList<>();
        for(int row=0; row<mapAsString.length; row++){
            for(int column=0; column<mapAsString[0].length(); column++){
                if(mapAsString[row].charAt(column)=='#'){
                    map.add(new Asteroid(row,column));
                }
            }
        }
        addVisibleAsteroids();
    }
    private void addVisibleAsteroids(){
        for(Asteroid asteroid: map){
            for(Asteroid otherAsteroid: map){
                if(asteroid == otherAsteroid){
                    continue;
                }
                if(otherAsteroid == getFirstAsteroid(asteroid, otherAsteroid)){
                    asteroid.addOneToNumberOfVisibleAsteroids();
                }
            }
        }
    }
    private Asteroid getFirstAsteroid(Asteroid asteroidFrom, Asteroid asteroidTo){
        int[] startCoordinate = new int[]{asteroidFrom.getCoordinate()[0], asteroidFrom.getCoordinate()[1]};
        int[] direction = asteroidFrom.getDirection(asteroidTo);
        while(true){
            startCoordinate[0] += direction[0];
            startCoordinate[1] += direction[1];
            for(Asteroid asteroid : map){
                if(Arrays.equals(startCoordinate, asteroid.getCoordinate())){
                    return asteroid;
                }
            }
        }
    }
    public Asteroid getAsteroidsWithMostVisibleAsteroids(){
        int maxAmount = 0;
        Asteroid maxAsteroid = null;
        for(Asteroid asteroid : map){
            if(asteroid.getNumberOfVisibleAsteroids()>maxAmount){
                maxAmount = asteroid.getNumberOfVisibleAsteroids();
                maxAsteroid = asteroid;
            }
        }
        return maxAsteroid;
    }
    public void addAngles(Asteroid center){
        for(Asteroid asteroid : map){
            asteroid.setAngleFromAsteroid(center);
        }
    }

    /**
     *
     * @param center Asteroid where the laser is on.
     * @param amount Amount of asteroids to vaporize.
     * @return Last vaporized asteroid.
     */
    public Asteroid vaporize(Asteroid center, int amount){
        Asteroid lastVaporized = null;
        addAngles(center);
        map.remove(center);
        int count = 0;
        double angle = 0- 2*offset;
        while(count<amount){
            Asteroid asteroid = findAsteroidWithSmallestAngle(angle);
            angle = asteroid.getAngle();
            lastVaporized = getFirstAsteroid(center, asteroid);
            count++;
            map.remove(lastVaporized);
        }
        map.add(center);
        return lastVaporized;
    }
    private Asteroid findAsteroidWithSmallestAngle(double oldAngle){
        Asteroid returnAsteroid = null;
        if(map.size()==0){
            throw new IllegalStateException();
        }
        double newAngle = 366;
        for(Asteroid asteroid : map){
            double asteroidAngle = asteroid.getAngle();
            if(asteroidAngle< newAngle-offset && asteroidAngle>oldAngle+offset){
                returnAsteroid = asteroid;
                newAngle = asteroidAngle;
            }
        }
        return (returnAsteroid!=null) ? returnAsteroid : findAsteroidWithSmallestAngle(-1);
    }
}
