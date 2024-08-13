package Competitions;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.currentThread;

public class TournamentThread implements Runnable{

    private Scores scores;
    private AtomicBoolean startSignal;
    private int groups;

    /**
     * Type of competition.
     * 1 - water, 2 - air, 3 - terrestrial.
     */
    private int competitionType;

    private static AtomicBoolean terrestrialSignal = new AtomicBoolean(true);

    public TournamentThread(Scores scores, AtomicBoolean startSignal, int groups, int competitionType) {
        this.scores = scores;
        this.startSignal = startSignal;
        this.groups = groups;
        this.competitionType = competitionType;

    }

    public TournamentThread(Scores scores, int groups) {
        this.scores = scores;
        this.startSignal.set(false);
        this.groups = groups;
    }

    public void run() {

        if (competitionType == 3) {
            synchronized (terrestrialSignal) {
                while (!terrestrialSignal.get()) {
                    try {
                        System.out.println("im waitingggg in terrestrial signal");
                        terrestrialSignal.wait();
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                        System.out.println("catch");
                    }
                }
                terrestrialSignal.set(false);
            }
        }

        System.out.println("Tournament thread started");

        synchronized (startSignal) {
            System.out.println("tournament thread started " + currentThread().getName());
            this.startSignal.set(true);
            startSignal.notifyAll();
            System.out.println("notified");
        }

        synchronized (scores) {
            while (scores.getAll().size() < groups){
                System.out.println("terrestrial signal is false");
                try {
                    scores.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
            terrestrialSignal.set(true);
            System.out.println("terrestrial signal is true");
        }
    }

    public Scores getScores() {
        return scores;
    }
}