public class Moon {
    private int[] originalPosition = new int[3];
    private int[] position = new int[3];
    private int[] originalVelocity = new int[3];
    private int[] velocity = new int[3];
    private int originalPositionAxis;
    private int positionAxis;
    private int originalVelocityAxis;
    private int velocityAxis;
    Moon(int[] position){
        if(position.length!=3){
            throw new IllegalArgumentException("Position must have length 3.");
        }
        for(int index=0;index<3;index++) {
            this.position[index] = position[index];
            originalPosition[index] = position[index];
        }
    }

    public int[] getPosition() {
        return position;
    }
    public int[] getVelocity() {
        return velocity;
    }
    public int getPositionAxis() {
        return positionAxis;
    }
    public int getVelocityAxis() {
        return velocityAxis;
    }

    public void setAxis(int index){
        this.positionAxis = position[index];
        originalPositionAxis = positionAxis;
        this.velocityAxis = velocity[index];
        originalVelocityAxis = velocityAxis;
    }
    public void reset(){
        for(int index=0;index<3;index++) {
            position[index] = originalPosition[index];
            velocity[index] = originalVelocity[index];
        }
    }

    void applyGravity(Moon moon2){
        int[] positionMoon2 = moon2.getPosition();
        for(int index=0; index<3; index++){
            if(position[index] < positionMoon2[index]){
                velocity[index]++;
            }
            if(position[index] > positionMoon2[index]){
                velocity[index]--;
            }
        }
    }
    void move(){
        for(int index=0;index<3;index++){
            position[index]+=velocity[index];
        }
    }
    void applyGravityAxis(Moon moon2){
        int positionMoon2 = moon2.getPositionAxis();
        if(positionAxis < positionMoon2){
            velocityAxis++;
        }
        if(positionAxis > positionMoon2){
            velocityAxis--;
        }
    }
    void moveAxis(){
        positionAxis+=velocityAxis;
    }
    boolean isBackAxis(){
        return velocityAxis== originalVelocityAxis && positionAxis== originalPositionAxis;
    }


    long totalEnergy(){
        long potentialEnergy = 0;
        long kineticEnergy = 0;
        for(int index=0;index<3;index++){
            potentialEnergy += Math.abs(position[index]);
            kineticEnergy += Math.abs(velocity[index]);
        }
        return potentialEnergy*kineticEnergy;
    }

}
