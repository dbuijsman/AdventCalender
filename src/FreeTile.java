public class FreeTile extends TileOxygenSystem{
    private TileOxygenSystem tileNorth;
    private TileOxygenSystem tileSouth;
    private TileOxygenSystem tileWest;
    private TileOxygenSystem tileEast;
    private int amountSteps = -1;
    private int timeOxygen = -1;
    private boolean allDirectionsExplored = false;
    FreeTile(){
        this.amountSteps = 0;
    }
    FreeTile(int direction, FreeTile neighbour){
        switch(direction){
            case 1:
                this.tileSouth = neighbour;
                break;
            case 2:
                this.tileNorth = neighbour;
                break;
            case 3:
                this.tileEast = neighbour;
                break;
            case 4:
                this.tileWest = neighbour;
                break;
        }
        this.amountSteps = neighbour.getAmountSteps()+1;
    }
    public boolean setAmountSteps(){
        boolean isChanged = false;
        if(tileNorth instanceof FreeTile && ((FreeTile) tileNorth).getAmountSteps()+1<amountSteps){
            amountSteps = ((FreeTile) tileNorth).getAmountSteps()+1;
            isChanged = true;
        }
        if(tileSouth instanceof FreeTile && ((FreeTile) tileSouth).getAmountSteps()+1<amountSteps){
            amountSteps = ((FreeTile) tileSouth).getAmountSteps()+1;
            isChanged = true;
        }
        if(tileWest instanceof FreeTile && ((FreeTile) tileWest).getAmountSteps()+1<amountSteps){
            amountSteps = ((FreeTile) tileWest).getAmountSteps()+1;
            isChanged = true;
        }
        if(tileEast instanceof FreeTile && ((FreeTile) tileEast).getAmountSteps()+1<amountSteps){
            amountSteps = ((FreeTile) tileEast).getAmountSteps()+1;
            isChanged = true;
        }
        return isChanged;
    }
    public int getAmountSteps() {
        return amountSteps;
    }
    public void setOxygen(){
        timeOxygen = 0;
        tileNorth.setOxygen(0);
        tileSouth.setOxygen(0);
        tileWest.setOxygen(0);
        tileEast.setOxygen(0);
    }

    public int getOxygen() {
        return timeOxygen;
    }

    public void setOxygen(int time){
        if(timeOxygen<0){
            timeOxygen = time +1;
            tileNorth.setOxygen(timeOxygen);
            tileSouth.setOxygen(timeOxygen);
            tileWest.setOxygen(timeOxygen);
            tileEast.setOxygen(timeOxygen);
        }
    }

    public boolean isExplored(int previousSteps){
        if(allDirectionsExplored || previousSteps>amountSteps){
            return true;
        }
        if(tileNorth != null && tileSouth != null && tileWest != null && tileEast != null){
            TileOxygenSystem[] array = new TileOxygenSystem[4];
            array[0] = tileNorth;
            array[1] = tileSouth;
            array[2] = tileWest;
            array[3] = tileEast;
            for(TileOxygenSystem tile : array){
                if(!tile.isExplored(amountSteps)){
                    return false;
                }
            }
            this.allDirectionsExplored = true;
            return true;
        }
        return false;
    }
    public int getDirection(){
        // Checking if a direction is unexplored.
        if(tileNorth == null){
            return 1;
        } else if(tileSouth == null){
            return 2;
        }else if(tileWest == null){
            return 3;
        }else if(tileEast == null){
            return 4;
        // Checking if a direction needs more steps to reach but all higher directions are not yet reached.
        }else if(!tileNorth.isExplored(amountSteps)){
            return 1;
        }else if(!tileSouth.isExplored(amountSteps)){
            return 2;
        }else if(!tileWest.isExplored(amountSteps)){
            return 3;
        }else if(!tileEast.isExplored(amountSteps)){
            return 4;
        // If all paths further away of the start are explored then it will go back.
        }else if(tileNorth instanceof FreeTile && ((FreeTile) tileNorth).getAmountSteps()<amountSteps){
            return 1;
        }else if(tileSouth instanceof FreeTile && ((FreeTile) tileSouth).getAmountSteps()<amountSteps){
            return 2;
        }else if(tileWest instanceof FreeTile && ((FreeTile) tileWest).getAmountSteps()<amountSteps){
            return 3;
        }else if(tileEast instanceof FreeTile && ((FreeTile) tileEast).getAmountSteps()<amountSteps){
            return 4;
        // Returns -1 if all paths are explored and the tile is the starting tile.
        } else {
            return -1;
        }
    }

    void addNeighbour(int direction, TileOxygenSystem neighbour){
        switch(direction){
            case 1:
                this.tileNorth = neighbour;
                break;
            case 2:
                this.tileSouth = neighbour;
                break;
            case 3:
                this.tileWest = neighbour;
                break;
            case 4:
                this.tileEast = neighbour;
                break;
        }
        if(neighbour instanceof FreeTile){
            amountSteps = Math.min(amountSteps, ((FreeTile) neighbour).getAmountSteps()+1);
        }
    }
}
