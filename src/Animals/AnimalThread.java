package Animals;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class AnimalThread implements Runnable {

    private Animal participant;
    private double neededDistance;
    private Boolean startFlag;
    private Boolean finishFlag;

    public AnimalThread(Animal participant, double neededDistance, Boolean startFlag, Boolean finishFlag) {
        this.participant = participant;
        this.neededDistance = neededDistance;
        this.startFlag = startFlag;
        this.finishFlag = finishFlag;
    }

    public void run(){
        System.out.println("Animal thread is run");
        synchronized (startFlag) {  //todo why syncronize?
            System.out.println("syncro " + currentThread().getName());
            System.out.println(startFlag);
            while (!startFlag) {
                System.out.println(startFlag);
                try {
                    System.out.println("im waitingggg");
                    startFlag.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                    System.out.println("catch");
                }
            }
            if (Thread.interrupted()) {
                System.out.println("Thread is interrupted");
            }
            else
                System.out.println("Thread is running");
        }

        synchronized (participant) {
            participant.startMoving();   //todo terrestrial animal!
            if (participant.getTotalDistance() >= neededDistance) {
                finishFlag = true;
                notify();
                try {
                    sleep(10000);  //Todo input fro user
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
