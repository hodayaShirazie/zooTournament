package Animals;

//TODO fix competition routs for air and water


import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import Competitions.SleepTime;

import java.util.concurrent.atomic.AtomicBoolean;

public class AnimalThread implements Runnable {

    private Animal participant;
    private double neededDistance;
    private AtomicBoolean startFlag;
    private AtomicBoolean finishFlag;
    private AtomicBoolean isThreadFinished;

    public AnimalThread(Animal participant, double neededDistance, AtomicBoolean startFlag, AtomicBoolean finishFlag) {
        this.participant = participant;
        this.neededDistance = neededDistance;
        this.startFlag = startFlag;
        this.finishFlag = finishFlag;
        isThreadFinished = new AtomicBoolean(false);
    }

    public void run() {
        System.out.println("Animal thread is run " + currentThread().getName());
        synchronized (startFlag) {  //todo why syncronize?
            System.out.println("syncro current thread name: " + currentThread().getName());
            System.out.println(startFlag + " out");
            while (!startFlag.get()) {
                System.out.println(startFlag + " in");
                try {
                    System.out.println("im waitingggg as an animal thread to start tournament " + currentThread().getName());
                    startFlag.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                    System.out.println("catch as an animal thread " + currentThread().getName());
                }
            }
        }

        System.out.println("done waiting as an animal thread " + currentThread().getName());

        System.out.println("syncro over participate" + currentThread().getName());
        synchronized (participant) {
            double oldDistance = participant.getTotalDistance();
            System.out.println("old distance: " + oldDistance);
            System.out.println("in syncro participant " + currentThread().getName());

//            if (participant.getCompetitionPanel().getRegularCourierTournament() == 2) //courier tournament
            participant.startMoving();


//            participant.startMoving();   //todo terrestrial animal!
            System.out.println("done moving--");

            if (!isThreadFinished.get()) System.out.println("!isThreadFinished.get()");else System.out.println("isThreadFinished is true");
            while (!isThreadFinished.get()) {

                if ((participant.getTotalDistance() - oldDistance) == neededDistance) {

                    finishFlag.set(true);
                    if (finishFlag.get())
                        System.out.println("finish flag is true-- " + currentThread().getName());

                    synchronized (finishFlag) {
                        finishFlag.notify(); //added
                    }

                    participant.notify();
                    System.out.println("notified participant----- " + currentThread().getName());

//            participant.notify();
                    try {
                        System.out.println("im sleeping cause done moving " + currentThread().getName());
                        sleep(SleepTime.getInstance().getTime());
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                    participant.setInitialLocation();
                    System.out.println("done sleeping------ " + currentThread().getName());
                    isThreadFinished.set(true);
                    System.out.println("isThreadFinished is done " + currentThread().getName());
                    participant.setIsAvailable(true);
                    System.out.println("animal is available " + currentThread().getName());

                }
            }


//            participant.setIsAvailable(true);
        }

    }
}

