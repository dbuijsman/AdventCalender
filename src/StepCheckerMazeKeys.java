import java.util.HashSet;

public class StepCheckerMazeKeys {
    private boolean isChecked = false;
    private int minimalSteps;
    StepCheckerMazeKeys(int steps){
        this.minimalSteps = steps;
    }

    public void setMinimalSteps(int minimalSteps) {
        if(minimalSteps < this.minimalSteps){
            isChecked = false;
            this.minimalSteps = minimalSteps;
        }
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getMinimalSteps() {
        return minimalSteps;
    }
}
