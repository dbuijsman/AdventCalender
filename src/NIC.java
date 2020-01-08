import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NIC {
    private IntCode program;
    private int networkAddress;
    private ArrayList<long[]> queue = new ArrayList<>();
    private int attempts = 0;
    NIC(ArrayList<Long> program, int address){
        this.program = new IntCode(program);
        this.networkAddress = address;
        this.program.run(address);
    }
    public boolean isQueueEmpty(){
        return queue.isEmpty();
    }
    public void addPackage(long[] pack){
        this.queue.add(pack);
    }
    public ArrayList<long[]> run(){
        if(program.isFinished()){
            System.out.println(networkAddress);
            return new ArrayList<>();
        }
        if(queue.size()==0){
            program.run(-1);
        } else {
            attempts = 0;
            long[] pack = queue.get(0);
            program.run(pack[1]);
            program.run(pack[2]);
            queue.remove(0);
        }
        List<Long> outputs = program.getNewOutputs();
        ArrayList<long[]> packagesToSend = new ArrayList<>();
        int index=0;
        while(index<outputs.size()){
            packagesToSend.add(new long[]{outputs.get(index),outputs.get(index+1),outputs.get(index+2)});
            index += 3;
        }
        return packagesToSend;

    }
}
