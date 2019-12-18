import java.util.ArrayList;
import java.util.Arrays;

public class PaintRobot {
    static final String[] Directions = {"Up", "Right", "Down", "Left"};
    private int direction = 0;
    private final int[] position = new int[]{0,0};
    private int lengthOldOutput = 0;
    private GridHull grid;
    private IntCode program;
    PaintRobot(IntCode program, GridHull grid){
        this.program = program;
        this.grid = grid;
    }
    void paint(){
        while(!program.isFinished()){
            int color = grid.getColor(position);
            program.run(color);
            ArrayList<Long> output = program.getOutputs();
            if(output.size()!=lengthOldOutput+2){
                throw new IllegalStateException("Program is invalid. It needs to output 2 outputs at a time");
            }
            grid.paint(position,(int)(long)output.get(lengthOldOutput));
            move((int)(long)output.get(lengthOldOutput+1));
            lengthOldOutput+=2;
        }
    }
    private void move(int turn){
        if(turn<0 || turn>1){
            throw new IllegalStateException("Program is invalid. It needs to output 0 or 1 as direction.");
        }
        this.direction = (direction+2*turn+3)%4;
        switch(direction){
            case 0:
                position[1]++;
                break;
            case 1:
                position[0]++;
                break;
            case 2:
                position[1]--;
                break;
            case 3:
                position[0]--;
                break;
        }
    }
}
