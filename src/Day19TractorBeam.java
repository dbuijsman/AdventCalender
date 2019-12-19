public class Day19TractorBeam {
    public static void main(String[] args){
        TractorBeam biem = new TractorBeam(new IntCode(General.importIntCode("Day19 TractorBeam")));
//        for(int i =0; i<50;i++){
//            for(int j=0; j<50;j++){
//                if(biem.attraction(i,j)==1){
//                    System.out.println(i + ", " +j);
//                }
//            }
//        }
        int[] closestCoordinates = biem.getClosestDistance(100,100);
        System.out.println("Santa\'s ship fits on (" + closestCoordinates[0] + ", " + closestCoordinates[1]);
    }
}
