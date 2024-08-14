package Competitions;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.currentThread;

public class Referee implements Runnable {

    private String name;
    private Scores scores;
    private AtomicBoolean finishFlag;

    Referee(String name,AtomicBoolean finishFlag, Scores scores) {
        this.name = name;
        this.scores = scores;
        this.finishFlag = finishFlag;
    }

    Referee(String name, Scores scores) {
        this.name = name;
        this.scores = scores;
    }

    @Override
    public void run() {
        synchronized (finishFlag) {
            while (!finishFlag.get()) {
                try {
                    System.out.println("wait that finishFlag will be true " + currentThread().getName());
//                    System.out.println("wait to scores");
                    finishFlag.wait();
                }
                catch (InterruptedException e) {
                    System.out.println(e.getMessage());

                }
            }
            System.out.println("added name to scores " + currentThread().getName());
            scores.add(name);
            synchronized (scores) {
                scores.notifyAll(); //added
            }
        }
    }

}