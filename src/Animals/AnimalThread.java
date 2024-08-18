package Animals;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import Competitions.SleepTime;
import Mobility.Point;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.Timer;

/**
 * Represents a thread that handles the movement of an animal in a competition.
 * This thread will wait for a start signal, move the animal until it has covered
 * the required distance, and then notify that the animal has finished moving.
 */
public class AnimalThread implements Runnable {


    /**
     * The animal that this thread will operate on.
     */
    private Animal participant;

    /**
     * The distance the animal needs to cover.
     */
    private double neededDistance;

    /**
     * Flag indicating whether the thread should start processing.
     */
    private AtomicBoolean startFlag;

    /**
     * Flag indicating whether the thread has finished processing.
     */
    private AtomicBoolean finishFlag;

    /**
     * Flag indicating whether the thread has completed its work.
     */
    private AtomicBoolean isThreadFinished;

    /**
     * Constructs an AnimalThread with the given parameters.
     *
     * @param participant The animal that this thread will operate on.
     * @param neededDistance The distance the animal needs to cover.
     * @param startFlag A flag indicating whether the thread should start processing.
     * @param finishFlag A flag indicating whether the thread has finished processing.
     */
    public AnimalThread(Animal participant, double neededDistance, AtomicBoolean startFlag, AtomicBoolean finishFlag) {
        this.participant = participant;
        this.neededDistance = neededDistance;
        this.startFlag = startFlag;
        this.finishFlag = finishFlag;
        isThreadFinished = new AtomicBoolean(false);
    }

    /**
     * The main logic for the thread. Waits for the start signal, moves the animal,
     * and monitors its progress until the required distance is covered.
     */
    @Override
    public void run() {

        // Wait for the start signal
        synchronized (startFlag) {
            while (!startFlag.get()) {
                try {
                    startFlag.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }


        // Process animal movement
        synchronized (participant) {
            double oldDistance = participant.getTotalDistance();

            // Start moving the animal based on its type
            if ((participant.getAnimalAsNumber(participant.getCategory())) != 3)
                participant.startMoving();
            else
                participant.startMoveTerrestrial();


            // Monitor progress until the required distance is covered
            while (!isThreadFinished.get()) {

                if (participant.getAnimalAsNumber(participant.getCategory()) == 3){

                    if (participant.getLocation().equals(new Point(0,0))
                            && (participant.getTotalDistance() - oldDistance) > 0
                            && participant.getDestination().equals(new Point(0,0))
                            && participant.getCompetitionPanel().getRegularCourierTournament() == 2){
                        neededDistance = participant.getTotalDistance() - oldDistance;
                    }

                }

                if ((participant.getTotalDistance() - oldDistance) >= neededDistance) {

                    if ((participant.getAnimalAsNumber(participant.getCategory())) == 3){
                        if (participant.getTimer() != null) {
                            participant.getTimer().stop();
//                            participant.setTimer(null);
                        }
                        if(participant.getMoveTimer() != null) {
                            participant.getMoveTimer().stop();
//                            participant.setMoveTimer(null);
                        }
                    }
//                        participant.setDone(0);

                    // Notify that the movement is finished
                    finishFlag.set(true);

                    synchronized (finishFlag) {
                        finishFlag.notify();  // Notify waiting threads
                    }

                    participant.notify();// Notify the participant

                    try {
                        sleep(SleepTime.getInstance().getTime());
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                    participant.setInitialLocation();
                    isThreadFinished.set(true);
                    participant.setIsAvailable(true);
                    participant.setNeedToMove(false);
                }
            }

        }

    }








}
