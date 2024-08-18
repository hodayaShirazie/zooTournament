package Competitions;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.currentThread;

/**
 * The Referee class represents a referee in a tournament who monitors the completion of a race.
 * The referee waits until the finishFlag is set to true, indicating that a group has finished the race,
 * and then adds the group's name to the scores.
 */
public class Referee implements Runnable {

    /**
     * The name of the group that this referee is monitoring.
     */
    private String name;

    /**
     * The Scores object where the group's result will be recorded.
     */
    private Scores scores;

    /**
     * An AtomicBoolean that signals the completion of the group's race.
     * When this flag is set to true, the referee records the group's result in the Scores object.
     */
    private AtomicBoolean finishFlag;

    /**
     * Constructs a Referee with the given name, finish flag, and scores.
     *
     * @param name       The name of the group or participant the referee is monitoring.
     * @param finishFlag An AtomicBoolean flag that indicates when the race has finished.
     * @param scores     The Scores object where the name will be added when the race is complete.
     */
    public Referee(String name, AtomicBoolean finishFlag, Scores scores) {
        this.name = name;
        this.scores = scores;
        this.finishFlag = finishFlag;
    }

    /**
     * Constructs a Referee with the given name and scores, without a finish flag.
     *
     * @param name   The name of the group or participant the referee is monitoring.
     * @param scores The Scores object where the name will be added when the race is complete.
     */
    public Referee(String name, Scores scores) {
        this.name = name;
        this.scores = scores;
    }

    /**
     * The run method of the Referee class. It waits for the finishFlag to be set to true,
     * indicating that the race is complete. Once the race is complete, the referee adds
     * the group's name to the scores and notifies any waiting threads that the race is finished.
     */
    @Override
    public void run() {
        synchronized (finishFlag) {
            while (!finishFlag.get()) {
                try {
                    finishFlag.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
            scores.add(name);
            synchronized (scores) {
                scores.notifyAll(); // Notify all waiting threads that the score has been updated
            }
        }
    }

}
