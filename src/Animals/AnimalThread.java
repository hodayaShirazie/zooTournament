package Animals;

//TODO fix competition routs for air and water

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import Competitions.SleepTime;
import Mobility.Point;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.Timer;

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
            if ((participant.getAnimalAsNumber(participant.getCategory())) != 3)
                participant.startMoving();
            else
                startMoveTerrestrial();


//            participant.startMoving();   //todo terrestrial animal!

            if (!isThreadFinished.get()) System.out.println("!isThreadFinished.get()");else System.out.println("isThreadFinished is true");
            while (!isThreadFinished.get()) {

                if ((participant.getTotalDistance() - oldDistance) == neededDistance) {

                    if ((participant.getAnimalAsNumber(participant.getCategory())) == 3)
                        participant.setDone(0);

                    System.out.println("done moving--");

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
                    participant.setNeedToMove(false);
                    System.out.println("animal is available " + currentThread().getName());

                }
            }

        }

    }

    /**
     * Starts the movement of the last participant in the competition.
     * If the type of competition is not terrestrial, the participant starts moving directly.
     * If the type of competition is terrestrial, a timer is set to update the movement of the participant
     * Along a rectangular path.
     */
    private void startMoveTerrestrial() {

        System.out.println("startMoveTerrestrial");
        participant.setMoveTimer(new Timer(1000 / 60, e -> updateSide()));
        participant.getMoveTimer().start();

        if (participant.isDone() < 4)
            if (participant.getMoveTimer() != null)
                participant.startMoving();

    }

    /**
     * Updates the orientation and position of the latest participant based on their current location.
     * The participant moves along a rectangular path and updates its orientation at each corner.
     * Stops the movement when the participant returns to the starting point.
     */
    private void updateSide() {

        System.out.println("MyLocation: " + participant.getLocation());
        System.out.println("done is: " + participant.isDone());
        System.out.println("width - 65:  " + (participant.getZooPanel().getWidth() - 65) );
        System.out.println("height - 65:  " + (participant.getZooPanel().getHeight() - 65) );


        if (participant.getLocation().equals(new Point(participant.getZooPanel().getWidth() - 65,0))) {
            participant.setOrientation(Orientation.SOUTH);
//            setNeededDistance(neededDistance+participant.calcDistancePoint(new Point(participant.getLocation().getX(), participant.getZooPanel().getHeight() - 65)));
            System.out.println("Change Or to " + participant.getOrientation());
            participant.startMoving();
            participant.setDone(1);


        } else if (participant.getLocation().equals(new Point(participant.getZooPanel().getWidth() - 65, participant.getZooPanel().getHeight() - 65))) {
            participant.setOrientation(Orientation.WEST);
//            setNeededDistance(neededDistance+participant.calcDistancePoint(new Point(0, participant.getLocation().getY())));
            System.out.println("Change Or to " + participant.getOrientation());
            participant.startMoving();
            participant.setDone(2);

        } else if (participant.getLocation().equals(new Point(0, participant.getZooPanel().getHeight() - 65))) {
            participant.setOrientation(Orientation.NORTH);
//            setNeededDistance(neededDistance+participant.calcDistancePoint(new Point(0, 0)));
            System.out.println("Change Or to " + participant.getOrientation());

            participant.startMoving();
            participant.setDone(3);

        } else if (participant.getLocation().equals(new Point(0, 0))) {
            participant.setOrientation(Orientation.EAST);
            System.out.println("Change Or to " + participant.getOrientation());


            if (participant.isDone() > 0) {
                if (participant.getMoveTimer() != null) {
                    participant.getMoveTimer().stop();
                    participant.setMoveTimer(null);
                    participant.setDone(4);
                }

            }

        }

    }






}
