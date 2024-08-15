package Competitions;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.currentThread;

public class TournamentThread implements Runnable {

    private Scores scores;
    private AtomicBoolean startSignal;
    private int groups;

    /**
     * Type of competition.
     * 1 - water, 2 - air, 3 - terrestrial.
     */
    private int competitionType;
    private int regularCourier;

    private static AtomicBoolean terrestrialSignal = new AtomicBoolean(true);
    private static AtomicBoolean[] airSignal = null;
    private static AtomicBoolean[] waterSignal = null;

    private AtomicBoolean [] tournamentRouts = new AtomicBoolean[5];

    public TournamentThread(Scores scores, AtomicBoolean startSignal, int groups, int competitionType, int regularCourier, AtomicBoolean[] tournamentRouts) {
        this.scores = scores;
        this.startSignal = startSignal;
        this.groups = groups;
        this.competitionType = competitionType;
        this.regularCourier = competitionType;
        this.tournamentRouts = tournamentRouts;

        if (airSignal == null) {
            System.out.println("airSignal is null");
            airSignal = new AtomicBoolean[5];
            for (int i = 0; i < 5; i++) {
                airSignal[i] = new AtomicBoolean(true);
            }
//            for (AtomicBoolean airFlag : airSignal)
//                airFlag = new AtomicBoolean(true);
        }
        if (waterSignal == null) {
            waterSignal = new AtomicBoolean[4];
            for (int i = 0; i < 4; i++) {
                waterSignal[i] = new AtomicBoolean(true);
            }
//            for (AtomicBoolean waterFlag : waterSignal)
//                waterFlag = new AtomicBoolean(true);
        }
    }

    public TournamentThread(Scores scores, int groups) {
        this.scores = scores;
        this.startSignal.set(false);
        this.groups = groups;
    }

    public void run() {
        System.out.println("entering into tournament thread----------");
        switch (competitionType) {
            case 1:
                runWaterTournament();
                break;
            case 2:
                runAirTournament();
                break;
            case 3:
                runTerrestrialTournament();
                break;
            default:
                System.out.println("defaulttttttttt");
        }



    }

    public Scores getScores() {
        return scores;
    }

    private void runTournament() {

        System.out.println("Tournament thread started " + currentThread().getName());

        synchronized (startSignal) {
            System.out.println("tournament thread started " + currentThread().getName());
            this.startSignal.set(true);
            startSignal.notifyAll();
            System.out.println("notified started tournament " + currentThread().getName());
        }

        synchronized (scores) {
            while (scores.getAll().size() < groups) {
                System.out.println("num of scores = " + scores.getAll().size());
                System.out.println("terrestrial signal is false because scores.getAll().size() < groups " + currentThread().getName());
                System.out.println("---------\n" + scores.toString());
                try {
                    scores.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
            //TODO never comes to here
            System.out.println("num of scores after loop = " + scores.getAll().size() + " " + currentThread().getName());
            System.out.println("printing scores after loop-- " + scores.getAll());
        }
    }

    private void runTerrestrialTournament() {
        System.out.println("competitionType == 3-----");
        synchronized (terrestrialSignal) {
            System.out.println("in syncro terrestrialSignal-----");
            while (!terrestrialSignal.get()) {
                try {
                    System.out.println("im waitingggg in terrestrial signal " + currentThread().getName());
                    terrestrialSignal.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                    System.out.println("catch");
                }
            }
            terrestrialSignal.set(false);

        }

        runTournament();

        terrestrialSignal.set(true);
        synchronized (terrestrialSignal) {
            terrestrialSignal.notifyAll();
        }

    }

    private void runAirTournament() {
        System.out.println("competitionType == 2-----");
        synchronized (this) {
            System.out.println("in syncro this - air tournament-----");
            while (!checkRoutsAvailability(2)) {
                try {
                    System.out.println("im waitingggg in air signal " + currentThread().getName());
                    this.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                    System.out.println("catch");
                }
            }
            for (int i = 0; i < tournamentRouts.length; i++) {
                if (tournamentRouts[i].get()) {
                    airSignal[i].set(false);
                }
            }
        }
        runTournament();

        for (int i = 0; i < tournamentRouts.length; i++) {
            if (tournamentRouts[i].get()) {
                airSignal[i].set(true);
            }
        }
        synchronized (this) {
            this.notifyAll();
        }

        System.out.println("air signal is true " + currentThread().getName());

    }

    private void runWaterTournament() {
        System.out.println("competitionType == 1-----");
        synchronized (this) {
            System.out.println("in syncro this - water tournament-----");
            while (!checkRoutsAvailability(1)) {
                try {
                    System.out.println("im waitingggg in water signal " + currentThread().getName());
                    this.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                    System.out.println("catch");
                }
            }
            for (int i = 0; i < tournamentRouts.length; i++) {
                if (tournamentRouts[i].get()) {
                    waterSignal[i].set(false);
                }
            }
        }
        runTournament();

        for (int i = 0; i < tournamentRouts.length; i++) {
            if (tournamentRouts[i].get()) {
                waterSignal[i].set(true);
            }
        }
        synchronized (this) {
            this.notifyAll();
        }

        System.out.println("water signal is true " + currentThread().getName());


    }

    private boolean checkRoutsAvailability(int competitionType) {

        switch (competitionType) {
            case 1:
                for (int i = 0; i < waterSignal.length; ++i) {
                    if (tournamentRouts[i].get() && !(waterSignal[i].get())) {
                        return false;
                    }
                    return true;
                }
            case 2:
                for (int i = 0; i < airSignal.length; ++i) {
                    if (tournamentRouts[i].get() && !(airSignal[i].get())) {
                        return false;
                    }
                    return true;
                }
            default:
                return false;
        }
    }
}
