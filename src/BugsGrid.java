import java.util.Arrays;

public class BugsGrid {
    private int[][] grid;
    BugsGrid(String[] gridAsStringArray){
        grid = new int[gridAsStringArray.length][gridAsStringArray[0].length()];
        for(int row=0; row<grid.length; row++){
            for(int column=0; column<grid[0].length; column++){
                grid[row][column] = gridAsStringArray[row].charAt(column)=='#'? 1 : 0;
            }
        }
    }
    public String getBinaryState(){
        StringBuilder binaryString = new StringBuilder();
        for(int row=grid.length-1; row>=0; row--){
            for(int column=grid[0].length-1; column>=0; column--){
                binaryString.append(grid[row][column]);
            }
        }
        return binaryString.toString();
    }
    public String addMinute(){
        int[][] newGrid = new int[grid.length][grid[0].length];
        for(int row=0; row<grid.length; row++){
            for(int column=0; column<grid[0].length; column++){
                int sum =0;
                sum += getValue(row-1,column);
                sum += getValue(row+1,column);
                sum += getValue(row,column-1);
                sum += getValue(row,column+1);
                switch(sum){
                    case 1:
                        newGrid[row][column] = 1;
                        break;
                    case 2:
                        newGrid[row][column] = 1-grid[row][column];
                        break;
                    default:
                        newGrid[row][column] = 0;
                }
            }
        }
        grid = newGrid;
        return getBinaryState();
    }
    private int getValue(int row, int column){
        if(row<0 || row>=grid.length || column<0 || column>=grid[0].length){
            return 0;
        } else {
            return grid[row][column];
        }
    }
    public void print(){
        for(int[] row : grid){
            for(int tile : row){
                System.out.print(tile==0 ? '.' : '#');
            }
            System.out.println("");
        }
    }
}
