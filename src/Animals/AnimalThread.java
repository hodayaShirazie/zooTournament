package Animals;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import Competitions.SleepTime;

import java.util.concurrent.atomic.AtomicBoolean;

public class AnimalThread implements Runnable {

    private Animal participant;
    private double neededDistance;
    private AtomicBoolean startFlag;
    private AtomicBoolean finishFlag;

    public AnimalThread(Animal participant, double neededDistance, AtomicBoolean startFlag, AtomicBoolean finishFlag) {
        this.participant = participant;
        this.neededDistance = neededDistance;
        this.startFlag = startFlag;
        this.finishFlag = finishFlag;
    }

    public void run() {
        System.out.println("Animal thread is run");
        synchronized (startFlag) {  //todo why syncronize?
            System.out.println("syncro " + currentThread().getName());
            System.out.println(startFlag + " out");
            while (!startFlag.get()) {
                System.out.println(startFlag + " in");
                try {
                    System.out.println("im waitingggg as an animal thread");
                    startFlag.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                    System.out.println("catch");
                }
            }
        }

        System.out.println("done waiting");

        synchronized (participant) {
            System.out.println("in syncro participant");
            participant.startMoving();   //todo terrestrial animal!
            while (participant.getTotalDistance() < neededDistance);
            finishFlag.set(true);
            participant.notify();
            try {
                System.out.println("im sleeping");
                sleep(SleepTime.getInstance().getTime());
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}