package Competitions;

import java.util.concurrent.atomic.AtomicBoolean;

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
        synchronized (scores) {
            while (!finishFlag.get()) {
                try {
                    scores.wait();
                }
                catch (InterruptedException e) {
                    System.out.println(e.getMessage());

                }
            }
            scores.add(name);
            scores.notifyAll();
        }
    }

}