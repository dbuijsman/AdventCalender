public class Wall extends  TileOxygenSystem{
    Wall(){

    }
    Wall(int direction, FreeTile neighbour){
        neighbour.addNeighbour(direction, this);
    }
    public boolean isExplored(int previousSteps){
        return true;
    }
    public boolean setAmountSteps(){
        return false;
    }
    public void setOxygen(int time) {
        return;
    }
    public int getOxygen() {
        return 0;
    }
}
