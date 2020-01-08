import java.util.ArrayList;

public class Category6Network {
    private NIC[] network;
    private NAT monitor;
    private int stopAtAddress;
    private boolean sending;
    Category6Network(ArrayList<Long> program, int size){
        network = new NIC[size];
        for(int address=0; address<size; address++){
            network[address] = new NIC(program,address);
        }
        this.monitor = new NAT(this.network);
    }
    public long runUntilAddress(int address){
        this.stopAtAddress = address;
        sending = true;
        while(sending){
            boolean isIdle = monitor.checkPossibleIdle();
            for(NIC computer : network){
                ArrayList<long[]> packets = computer.run();
                if(isIdle && packets.size()!=0){
                    isIdle = false;
                }
                sendPackets(packets);
            }
            if(isIdle){
                sending = monitor.send();
            }
        }
        return monitor.getY();
    }
    private void sendPackets(ArrayList<long[]> packets){
        for(long[] packet : packets){
            if(packet[0]==stopAtAddress){
                monitor.save(packet);
            } else {
                network[(int)packet[0]].addPackage(packet);
            }
        }
    }
}
