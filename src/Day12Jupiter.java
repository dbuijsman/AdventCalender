public class Day12Jupiter {
    public static void main(String[] args){
        int[][] coordinateMoons = new int[4][3];
        coordinateMoons[0] = new int[]{4, 12, 13};
        coordinateMoons[1] = new int[]{-9, 14, -3};
        coordinateMoons[2] = new int[]{-7, -1, 2};
        coordinateMoons[3] = new int[]{-11, 17, -1};
        Jupiter jupiter = new Jupiter(coordinateMoons);
        System.out.println("After 0 steps:");
        jupiter.print();
        jupiter.timeStep(1000);
        System.out.println("After 1000 steps:");
        jupiter.print();
        System.out.println("The total energy of Jupiter is " + jupiter.totalEnergy());
        jupiter.reset();
        System.out.println("One cycle consists of " + jupiter.cycle() + " steps.");

    }
}
