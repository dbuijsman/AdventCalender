import java.util.ArrayList;

public class BugsGridMultidimensional {
    private int rows;
    private int columns;
    private ArrayList<Integer>[][] grid;
    BugsGridMultidimensional(String[] gridAsStringArray){
        rows = gridAsStringArray.length;
        columns = gridAsStringArray[0].length();
        if(rows%2==0 || columns%2==0){
            throw new IllegalArgumentException("The length of the sides must be uneven.");
        }
        grid = new ArrayList[rows][columns];
        for(int row=0; row<rows; row++){
            for(int column=0; column<columns; column++){
                if(row==2 && column == 2){
                    continue;
                }
                ArrayList<Integer> tile = new ArrayList<>();
                tile.add(gridAsStringArray[row].charAt(column)=='#'? 1 : 0);
                grid[row][column] = tile;
            }
        }
    }

    public ArrayList< Integer >[][] getGrid() {
        return grid;
    }

    public void addMinutes(int minutes){
        for(int minute=0; minute<minutes; minute++){
            addMinute();
        }
    }
    public void addMinute(){
        if(needExtraLayer()){
            addLayer();
        }
        ArrayList<Integer>[][] newGrid = new ArrayList[rows][columns];
        for(int row=0; row<rows; row++){
            for(int column=0; column<columns; column++){
                if(row==2 && column == 2){
                    continue;
                }
                ArrayList<Integer> newValues = new ArrayList<>();
                for(int depth = 0; depth<grid[0][0].size(); depth++){
                    if(depth==0 && (row==0 || row==rows-1)){
                        newValues.add(0);
                        continue;
                    } else if (depth == 0 && (column == 0 || column == columns - 1)) {
                        newValues.add(0);
                        continue;
                    } else if (depth == grid[0][0].size()-1 && row==2 && (column==1 || column==3)){
                        newValues.add(0);
                        continue;
                    } else if (depth == grid[0][0].size()-1 && column==2 && (row==1 || row==3)){
                        newValues.add(0);
                        continue;
                    }
                    int sum = sumLeft(row,column,depth) + sumRight(row,column,depth) + sumUp(row,column,depth) + sumDown(row,column,depth);
                    switch(sum){
                        case 1:
                            newValues.add(1);
                            break;
                        case 2:
                            newValues.add(1-grid[row][column].get(depth));
                            break;
                        default:
                            newValues.add(0);
                    }
                }
                newGrid[row][column] = newValues;
            }
        }
        grid = newGrid;
    }

    public int getTotalBugs(){
        int sum = 0;
        for(int row=0; row<rows; row++){
            for(int column=0; column<columns; column++) {
                if(row==2 && column ==2){
                    continue;
                }
                for(int tile : grid[row][column]){
                    sum += tile;
                }
            }
        }
        return sum;
    }
    private boolean needExtraLayer(){
        for(int row=0; row<rows; row++){
            for(int column=0; column<columns; column++) {
                if(row==2 && column ==2){
                    continue;
                }
                if(grid[row][column].get(0)==1 || grid[row][column].get(grid[row][column].size()-1)==1){
                    return true;
                }
            }
        }
        return false;
    }
    private void addLayer(){
        for(int row=0; row<rows; row++){
            for(int column=0; column<columns; column++) {
                if(row==2 && column ==2){
                    continue;
                }
                grid[row][column].add(0,0);
                grid[row][column].add(0);
            }
        }
    }
    private int sumLeft(int row, int column, int depth){
        if(column==0){
            return grid[2][1].get(depth-1);
        } else if(column==3 && row==2){
            return getSumRight(depth+1);
        } else {
            return grid[row][column-1].get(depth);
        }
    }
    private int sumRight(int row, int column, int depth){
        if(column==columns-1){
            return grid[2][3].get(depth-1);
        } else if(column==1 && row==2){
            return getSumLeft(depth+1);
        } else {
            return grid[row][column+1].get(depth);
        }
    }
    private int sumUp(int row, int column, int depth){
        if(row==0){
            return grid[1][2].get(depth-1);
        } else if(row==3 && column==2){
            return getSumDown(depth+1);
        } else {
            return grid[row-1][column].get(depth);
        }
    }
    private int sumDown(int row, int column, int depth){
        if(row==rows-1){
            return grid[3][2].get(depth-1);
        } else if(row==1 && column==2){
            return getSumUp(depth+1);
        } else {
            return grid[row+1][column].get(depth);
        }
    }

    private int getSumLeft(int depth){
        int sum = 0;
        for(int row=0; row<rows; row++){
            sum += grid[row][0].get(depth);
        }
        return sum;
    }
    private int getSumRight(int depth){
        int sum = 0;
        for(int row=0; row<rows; row++){
            sum += grid[row][columns-1].get(depth);
        }
        return sum;
    }
    private int getSumUp(int depth){
        int sum = 0;
        for(int column=0; column<columns; column++){
            sum += grid[0][column].get(depth);
        }
        return sum;
    }
    private int getSumDown(int depth){
        int sum = 0;
        for(int column=0; column<columns; column++){
            sum += grid[rows-1][column].get(depth);
        }
        return sum;
    }
}
