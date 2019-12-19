public class Day15RepairRobot {
    public static void main(String[] args){
        RepairRobotCount robot = new RepairRobotCount(new IntCode(General.importIntCode("Day15 RepairRobot")));
        int steps = robot.start();
        System.out.println("The number of needed steps is: " + steps);
    }
}
