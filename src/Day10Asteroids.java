import java.util.Arrays;

public class Day10Asteroids {
    public static void main(String[] args){
        MapOfAsteroids mapAsteroids = new MapOfAsteroids(General.importResource("Day10 Asteroids"));
        Asteroid asteroid = mapAsteroids.getAsteroidsWithMostVisibleAsteroids();
        System.out.println("The maximum number of visible asteroids is " + asteroid.getNumberOfVisibleAsteroids() + " and this is on coordinate " + Arrays.toString(asteroid.getCoordinate()));
        Asteroid lastVaporized = mapAsteroids.vaporize(asteroid, 200);
        System.out.println(Arrays.toString(lastVaporized.getCoordinate()));
    }
}
