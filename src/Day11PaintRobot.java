import java.util.HashMap;

public class Day11PaintRobot {
    public static void main(String[] args){
        IntCode program = new IntCode(General.importIntCode("Day11 PaintRobot"));
        GridHull grid = new GridHull();
        grid.paint(new int[]{0,0},1);
        PaintRobot robot = new PaintRobot(program, grid);
        robot.paint();
        HashMap<Integer, HashMap<Integer,Panel>> panels = grid.getPanels();
        int numberOfPanels = 0;
        for(HashMap<Integer,Panel> lineOfPanels : panels.values()){
            numberOfPanels += lineOfPanels.keySet().size();
        }
        System.out.println("The robot painted " + numberOfPanels + " panels at least once.");
        grid.print();

    }
}
