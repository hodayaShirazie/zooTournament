package Competitions;

import static java.lang.Thread.currentThread;

public class TournamentThread implements Runnable{

    private Scores scores;
    private Boolean startSignal;
    private int groups;


    public TournamentThread(Scores scores, Boolean startSignal, int groups) {
        this.scores = scores;
        this.startSignal = startSignal;
        this.groups = groups;
    }

    public TournamentThread(Scores scores, int groups) {
        this.scores = scores;
        this.startSignal = false;
        this.groups = groups;
    }

    public void run() {
        System.out.println("Tournament thread started");
        synchronized (this) {
            System.out.println("tournament thread started " + currentThread().getName());
            this.startSignal = true;
            this.notifyAll();
            System.out.println("notified");
        }
    }
}