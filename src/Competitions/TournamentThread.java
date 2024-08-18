package Competitions;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.currentThread;

/**
 * Class representing a thread responsible for running a tournament.
 * This class handles the logic for different types of competitions and manages signals for synchronization.
 */
public class TournamentThread implements Runnable {

    /**
     * The scores of the tournament.
     */
    private Scores scores;

    /**
     * AtomicBoolean signal used to start the tournament.
     */
    private AtomicBoolean startSignal;

    /**
     * Number of groups participating in the tournament.
     */
    private int groups;

    /**
     * Type of competition:
     * 1 - water, 2 - air, 3 - terrestrial.
     */
    private int competitionType;

    /**
     * Regular courier type. This is currently used as a placeholder for the competition type.
     */
    private int regularCourier;

    /**
     * Static signals for synchronization:
     * - terrestrialSignal: Used for synchronization in terrestrial competitions.
     * - airSignal: Array of AtomicBoolean for air competition routes.
     * - waterSignal: Array of AtomicBoolean for water competition routes.
     */
    private static AtomicBoolean terrestrialSignal = new AtomicBoolean(true);
    private static AtomicBoolean[] airSignal = null;
    private static AtomicBoolean[] waterSignal = null;

    /**
     * Tournament routes:
     * An array of AtomicBoolean representing the availability of routes for the tournament.
     */
    private AtomicBoolean[] tournamentRouts = new AtomicBoolean[5];

    /**
     * Constructs a TournamentThread with the specified parameters.
     * Initializes the signals for air and water competitions if they are null.
     *
     * @param scores The Scores object to keep track of the scores.
     * @param startSignal The signal to start the tournament.
     * @param groups The number of groups participating in the tournament.
     * @param competitionType The type of competition (1 for water, 2 for air, 3 for terrestrial).
     * @param regularCourier The type of regular courier.
     * @param tournamentRouts Array of AtomicBoolean representing the routes for the tournament.
     */
    public TournamentThread(Scores scores, AtomicBoolean startSignal, int groups, int competitionType, int regularCourier, AtomicBoolean[] tournamentRouts) {
        this.scores = scores;
        this.startSignal = startSignal;
        this.groups = groups;
        this.competitionType = competitionType;
        this.regularCourier = competitionType;
        this.tournamentRouts = tournamentRouts;

        // Initialize airSignal array if it's null
        if (airSignal == null) {
            airSignal = new AtomicBoolean[5];
            for (int i = 0; i < 5; i++) {
                airSignal[i] = new AtomicBoolean(true);
            }
        }

        // Initialize waterSignal array if it's null
        if (waterSignal == null) {
            waterSignal = new AtomicBoolean[4];
            for (int i = 0; i < 4; i++) {
                waterSignal[i] = new AtomicBoolean(true);
            }
        }
    }

    /**
     * Alternative constructor to create a TournamentThread with only scores and groups.
     * Initializes the startSignal to false.
     *
     * @param scores The Scores object to keep track of the scores.
     * @param groups The number of groups participating in the tournament.
     */
    public TournamentThread(Scores scores, int groups) {
        this.scores = scores;
        this.startSignal = new AtomicBoolean(false);
        this.groups = groups;
    }

    /**
     * Runs the tournament based on the competition type.
     * Depending on the competition type, it delegates to the appropriate method.
     */
    public void run() {
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
        }
    }

    /**
     * Gets the Scores object associated with this tournament.
     *
     * @return The Scores object.
     */
    public Scores getScores() {
        return scores;
    }

    /**
     * Runs a generic tournament process.
     * Waits until all scores are collected and then processes the results.
     */
    private void runTournament() {

        synchronized (startSignal) {
            this.startSignal.set(true);
            startSignal.notifyAll();
        }

        synchronized (scores) {
            while (scores.getAll().size() < groups) {
                try {
                    scores.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    /**
     * Runs the terrestrial tournament.
     * Handles synchronization and signaling for terrestrial competitions.
     */
    private void runTerrestrialTournament() {
        synchronized (terrestrialSignal) {
            while (!terrestrialSignal.get()) {
                try {
                    terrestrialSignal.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
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

    /**
     * Runs the air tournament.
     * Handles synchronization and signaling for air competitions.
     */
    private void runAirTournament() {
        synchronized (this) {
            while (!checkRoutsAvailability(2)) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
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

    }

    /**
     * Runs the water tournament.
     * Handles synchronization and signaling for water competitions.
     */
    private void runWaterTournament() {
        synchronized (this) {
            while (!checkRoutsAvailability(1)) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
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

    }

    /**
     * Checks the availability of routes for a given competition type.
     *
     * @param competitionType The type of competition (1 for water, 2 for air).
     * @return True if all routes are available, false otherwise.
     */
    private boolean checkRoutsAvailability(int competitionType) {
        switch (competitionType) {
            case 1:
                for (int i = 0; i < waterSignal.length; ++i) {
                    if (tournamentRouts[i].get() && !waterSignal[i].get()) {
                        return false;
                    }
                }
                return true;
            case 2:
                for (int i = 0; i < airSignal.length; ++i) {
                    if (tournamentRouts[i].get() && !airSignal[i].get()) {
                        return false;
                    }
                }
                return true;
            default:
                return false;
        }
    }
}
