import java.util.ArrayList;

public class Day23NIC {
    public static void main(String[] args){
        ArrayList<Long> program = General.importIntCode("Day23 Category Six");
        Category6Network network = new Category6Network(program,50);
        long result = network.runUntilAddress(255);
        System.out.println("The first Y value that is send twice in a row by the NAT is " + result);
    }
}
