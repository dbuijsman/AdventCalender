import java.util.ArrayList;

public class Amplifier {
    private IntCode program;
    private int phase;
    private Amplifier nextAmplifier;
    private boolean isLast = false;
    private int usedInputs = 0;
    Amplifier(ArrayList<Long> program, ArrayList<Integer> phaseSettings){
        this.program = new IntCode(program);
        try {
            this.program.run(phase);
        }
        finally {
            this.phase = phaseSettings.get(0);
            phaseSettings.remove(0);
        }
        this.nextAmplifier = new Amplifier(program, this, phaseSettings);
    }
    Amplifier(ArrayList<Long> program, Amplifier firstAmplifier, ArrayList<Integer> phaseSettings){
        this.program = new IntCode(program);
        try {
            this.program.run(phase);
        }
        finally {
            this.phase = phaseSettings.get(0);
            phaseSettings.remove(0);
        }
        if(phaseSettings.size()==0){
            this.nextAmplifier = firstAmplifier;
            this.isLast = true;
        } else {
            this.nextAmplifier = new Amplifier(program, firstAmplifier, phaseSettings);
        }
    }
    public void setPhase(ArrayList<Integer> phaseSettings){
        this.usedInputs = 0;
        this.phase = phaseSettings.get(0);
        phaseSettings.remove(0);
        this.program.reset();
        this.program.run(phase);
        if(phaseSettings.size()>0){
            nextAmplifier.setPhase(phaseSettings);
        }
    }
    private long getOutputLastAmplifier(){
        if(isLast){
            return this.program.getOutputs().get(program.getOutputs().size()-1);
        }
        else {
            return nextAmplifier.getOutputLastAmplifier();
        }
    }
    public long run(int input){
        this.program.run(input);
        return nextAmplifier.run(this.program.getOutputs());
    }
    private long run(ArrayList<Long> outputsLastAmplifier){
        if(isLast && this.program.isFinished()){
            return getOutputLastAmplifier();

        } else{
            if(!this.program.isFinished() && usedInputs<outputsLastAmplifier.size()){
                this.program.run(outputsLastAmplifier.get(usedInputs));
                usedInputs++;
            }
            return nextAmplifier.run(this.program.getOutputs());
        }
    }
}
