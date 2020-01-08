import java.util.HashSet;

public class NAT {
    private NIC[] network;
    private long X;
    private long Y;
    private long lastSendedY;
    NAT(NIC[] network){
        this.network = network;
    }

    public long getY() {
        return Y;
    }

    boolean checkPossibleIdle(){
        for(NIC computer : network){
            if(!computer.isQueueEmpty()){
                return false;
            }
        }
        return true;
    }
    void save(long[] packet){
        this.X = packet[1];
        this.Y = packet[2];
    }
    boolean send(){
        if(Y==lastSendedY){
            return false;
        } else {
            network[0].addPackage(new long[]{0,X,Y});
            lastSendedY = Y;
            return true;
        }
    }
}
