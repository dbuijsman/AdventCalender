import java.util.Arrays;

public class Asteroid {
    private int[] coordinate;
    private int numberOfVisibleAsteroids=0;
    private double angle;
    Asteroid(int coordinateX, int coordinateY){
        this.coordinate = new int[]{coordinateX,coordinateY};
    }
    public int[] getCoordinate() {
        return coordinate;
    }
    public int getNumberOfVisibleAsteroids() {
        return numberOfVisibleAsteroids;
    }
    public double getAngle() {
        return angle;
    }

    void addOneToNumberOfVisibleAsteroids(){
        this.numberOfVisibleAsteroids += 1;
    }
    int[] getDirection(Asteroid asteroid){
        int directionY = asteroid.getCoordinate()[0]-coordinate[0];
        int directionX = asteroid.getCoordinate()[1]-coordinate[1];
        return new int[]{directionY/General.gcd(directionY,directionX), directionX/General.gcd(directionY,directionX)};
    }
    void setAngleFromAsteroid(Asteroid asteroid){
        if(Arrays.equals(coordinate, asteroid.getCoordinate())){
            return;
        }
        int[] direction = asteroid.getDirection(this);
        angle = Math.toDegrees(Math.atan2(direction[1], -direction[0]));

        if(angle < 0){
            angle += 360;
        }
    }
}
