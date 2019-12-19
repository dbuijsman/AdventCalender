import java.util.HashMap;

public class TractorBeam {
    private IntCode program;
    private HashMap<Integer, Integer> map = new HashMap<>();
    TractorBeam(IntCode program){
        this.program = program;
    }
    public int attraction(int positionX, int positionY){
        program.reset();
        program.run(positionX);
        program.run(positionY);
        return (int)(long) program.getLastOutput();
    }
    public int[] getClosestDistance(int width, int length){
        int coordinateX = 0;
        int coordinateY = 10;
        boolean widthEnough = false;
        while(true){
            coordinateX = getFirstPositionOnHorizontalLine(coordinateX,coordinateY);
            if(!scanHorizontalLine(coordinateX,coordinateY,width)){
                coordinateY++;
                continue;
            }
            coordinateX = getFirstPositionOnHorizontalLine(coordinateX,coordinateY+length);
            if(!widthEnough){
                if(!scanHorizontalLine(coordinateX,coordinateY+length, width)){
                    coordinateY += length;
                } else {
                    widthEnough = true;
                }
            }
            if(scanHorizontalLine(coordinateX,coordinateY,width)){
                return new int[]{coordinateX, coordinateY};
            }
            System.out.println("Looking at (" + coordinateX + ", " + coordinateY + ")");
        }
    }

    public int getFirstPositionOnHorizontalLine(int coordinateX, int coordinateY){
        if(coordinateY==540){
            return coordinateX+1;
        }
        while(attraction(coordinateX,coordinateY) != 1){
            coordinateX ++;
            System.out.println("Looking at (" + coordinateX + ", " + coordinateY + ")");
        }
        return coordinateX;
    }
    public boolean scanHorizontalLine(int coordinateX, int coordinateY, int width){
        return attraction(coordinateX+width, coordinateY)==1;
    }
}
