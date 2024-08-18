package Competitions;

import Animals.Animal;
import Animals.AnimalThread;
import Animals.Orientation;
import Mobility.Point;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The CourierTournament class represents a tournament in which animals compete in a courier-style race.
 * Each group of animals is assigned a route, and the race begins with each animal moving along its route.
 * The tournament manages the setup and coordination of animal threads for the race.
 */
public class CourierTournament extends Tournament {

    /**
     * Constructs a CourierTournament with the given animals.
     *
     * @param animals A 2D array of Animal objects, where each row represents a group of animals.
     */
    public CourierTournament(Animal[][] animals) {
        super(animals);
    }

    /**
     * Sets up the tournament by initializing flags, setting animal locations, and starting the animal threads.
     * Each group of animals is assigned a route, and the race is initiated.
     *
     * @param animals A 2D array of Animal objects, where each row represents a group of animals.
     */
    public void setUp(Animal[][] animals) {
        AtomicBoolean startFlag = new AtomicBoolean(false); // startFlag of all groups in CourierTournament
        int numberOfGroups = animals.length;
        int type = getAnimalAsNumber(animals[0][0].getCategory());

        Scores groupsScores = new Scores();
        AtomicBoolean[] tournamentRouts = new AtomicBoolean[5];
        for (int i = 0; i < 5; i++) {
            tournamentRouts[i] = new AtomicBoolean(false);
        }

        for (int k = 0; k < numberOfGroups; ++k) {

            if (type != 3) {
                int route = animals[k][0].getCompetitionRoute();
                tournamentRouts[route - 1].set(true);
            }

            int numberOfAnimalsMembers = animals[k].length; // Saving number of animal members in a group
            AtomicBoolean[] flags = new AtomicBoolean[numberOfAnimalsMembers]; // Creating flags for animals in group
            for (int i = 0; i < numberOfAnimalsMembers; ++i)
                flags[i] = new AtomicBoolean(false);

            int neededDistance = animals[k][0].getLenOfRoute() / numberOfAnimalsMembers;

            animals[k][0].setOrientation(Orientation.EAST);

            // Start the first animal in the group
            AnimalThread animalThreadFirst = new AnimalThread(animals[k][0], neededDistance, startFlag, flags[0]);
            animals[k][0].setIsAvailable(false);
            Thread threadFirst = new Thread(animalThreadFirst);
            threadFirst.start();

            setLocation(animals[k], neededDistance);

            for (int i = 1; i < numberOfAnimalsMembers; i++) {
                animals[k][i - 1].setDestination(animals[k][i].getLocation());
                animals[k][i].setIsAvailable(false);

                AnimalThread animalThread = new AnimalThread(animals[k][i], neededDistance, flags[i - 1], flags[i]);
                Thread thread = new Thread(animalThread);
                thread.start();
            }

            animals[k][numberOfAnimalsMembers - 1].setDestination();

            // Create and start a referee for the group
            Referee finishReferee = new Referee("group " + (k + 1), flags[numberOfAnimalsMembers - 1], groupsScores);
            Thread finishThread = new Thread(finishReferee);
            finishThread.start();
        }

        // Set up and start the tournament thread
        super.setTournamentThread(new TournamentThread(groupsScores, startFlag, numberOfGroups, type, 2, tournamentRouts));
        Thread courierTournamentThread = new Thread(getTournamentThread());
        courierTournamentThread.start();
    }

    /**
     * Sets the initial location and orientation of each animal in the group based on its distance from the start.
     *
     * @param animals        An array of Animal objects in the group.
     * @param neededDistance The distance each animal should travel within the group.
     */
    private void setLocation(Animal[] animals, int neededDistance) {
        for (int i = 1; i < animals.length; i++) {
            if (i * neededDistance < (animals[i].getZooPanel().getWidth()-65)) {
                animals[i].setLocation(new Point(i * neededDistance + animals[i].getXinit(), animals[i].getLocation().getY()));
                animals[i].setOrientation(Orientation.EAST);
            } else if (i * neededDistance < ((animals[i].getZooPanel().getWidth()-65) +  (animals[i].getZooPanel().getHeight()-65))) {
                animals[i].setLocation(new Point((animals[i].getZooPanel().getWidth()-65), i * neededDistance - (animals[i].getZooPanel().getWidth()-65)));
                animals[i].setOrientation(Orientation.SOUTH);

            } else if (i * neededDistance < (2 * (animals[i].getZooPanel().getWidth()-65) +  (animals[i].getZooPanel().getHeight()-65))) {
                animals[i].setLocation(new Point((animals[i].getZooPanel().getWidth()-65) - (i * neededDistance - ((animals[i].getZooPanel().getWidth()-65) +  (animals[i].getZooPanel().getHeight()-65))),  (animals[i].getZooPanel().getHeight()-65)));
                animals[i].setOrientation(Orientation.WEST);

            } else {
                animals[i].setLocation(new Point(0, (animals[i].getZooPanel().getHeight()-65)- (i * neededDistance - (2 * (animals[i].getZooPanel().getWidth()-65) + (animals[i].getZooPanel().getHeight()-65)))));
                animals[i].setOrientation(Orientation.NORTH);

            }


        }
    }

}
