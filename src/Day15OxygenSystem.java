public class Day15OxygenSystem {
    static int robotX = 0;
    static int robotY = 0;
    public static void main(String[] args){
        IntCode program = new IntCode(General.importIntCode("Day15 Oxygen System"));
        FreeTile oxygenSystem = null;
        GridOxygen grid = new GridOxygen();
        while(true){
            int direction = grid.getDirection(robotX,robotY);
            if(direction == -1){
                break;
            }
            int[] position = nextPosition(direction);
            int tileType = (int) program.run(direction);
            grid.addTile(position[0],position[1],(FreeTile)grid.getTile(robotX,robotY),tileType,direction);
            if(tileType!=0){
                robotX = position[0];
                robotY = position[1];
            }
            if(tileType == 2){
                oxygenSystem = (FreeTile)grid.getTile(robotX,robotY);
                System.out.println("Found oxygen at " + oxygenSystem.getAmountSteps());
            }
        }
        grid.setSteps();
        System.out.println("Needed steps to get to the oxygen system is " + oxygenSystem.getAmountSteps());
        oxygenSystem.setOxygen();
        System.out.println("Time to fill space with oxygen is " + grid.getMaxOxygen());
    }
    static int[] nextPosition(int direction){
        int[] position = new int[]{robotX,robotY};
        switch (direction){
            case 1:
                position[0]+=1;
                break;
            case 2:
                position[0]-=1;
                break;
            case 3:
                position[1]+=1;
                break;
            case 4:
                position[1]-=1;
                break;
        }
        return position;
    }
}
