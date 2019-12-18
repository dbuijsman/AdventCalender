public class Jupiter {
    Moon[] moons;
    Jupiter(int[][] coordinateMoons){
        this.moons = new Moon[coordinateMoons.length];
        for(int index=0;index<coordinateMoons.length;index++){
            moons[index] = new Moon(coordinateMoons[index]);
        }
    }
    public void reset(){
        for(Moon moon : moons){
            moon.reset();
        }
    }
    public void timeStep(int numberOfSteps){
        if(numberOfSteps==0){
            return;
        }
        for(Moon moon : moons){
            for(Moon moon2 : moons){
                moon.applyGravity(moon2);
            }
        }
        for(Moon moon : moons){
            moon.move();
        }
        timeStep(numberOfSteps-1);
    }
    public void timeStepAxis(int numberOfSteps){
        if(numberOfSteps==0){
            return;
        }
        for(Moon moon : moons){
            for(Moon moon2 : moons){
                moon.applyGravityAxis(moon2);
            }
        }
        for(Moon moon : moons){
            moon.moveAxis();
        }
        timeStepAxis(numberOfSteps-1);
    }

    public long totalEnergy(){
        long totalEnergy = 0;
        for(Moon moon : moons){
            totalEnergy += moon.totalEnergy();
        }
        return totalEnergy;
    }
    private long cycleAxis(int index){
        for(Moon moon: moons){
            moon.setAxis(index);
        }
        long cycle = 0;
        LOOP_IS_DONE: while(true){
            cycle+=moons[0].getVelocity()[0]/3+1;
            timeStepAxis(moons[0].getVelocity()[0]/3+1);
            for(Moon moon: moons){
                if(!moon.isBackAxis()){
                    continue LOOP_IS_DONE;
                }
            }
            break;
        }
        return cycle;
    }
    public long cycle(){
        long[] cycles = new long[3];
        for(int index=0; index<3;index++){
            cycles[index] = cycleAxis(index);
            System.out.println(cycles[index]);
        }
        long lcm = 1;
        for(int index=0;index<3;index++){
            lcm = Calc.lcm(lcm,cycles[index]);
        }
        return lcm;
    }



    public void print(){
        for(Moon moon : moons){
            int[] position = moon.getPosition();
            int[] velocity = moon.getVelocity();
            System.out.println("pos=<x=" + position[0] + ", y=" + position[1] + ", z=" + position[2] + ">, vel=<x=" + velocity[0] + ", y=" + velocity[1] + ", z=" + velocity[2] + ">");
        }
    }
}
