package Competitions;

import Animals.Animal;
import Animals.AnimalThread;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The RegularTournament class represents a tournament where each group of animals competes in a race.
 * In this type of tournament, each group consists of a single animal that races individually.
 */
public class RegularTournament extends Tournament {

    /**
     * Constructs a RegularTournament object with the given array of animals.
     * Each group in the tournament contains a single animal.
     *
     * @param animals A 2D array where each row represents a group of animals participating in the tournament.
     */
    public RegularTournament(Animal[][] animals) {
        super(animals);
    }

    /**
     * Sets up the tournament by initializing and starting the threads for each animal's race and its corresponding referee.
     * Each animal races individually on its assigned route, and a referee monitors the completion of the race.
     *
     * @param animals A 2D array where each row contains a single animal participating in the tournament.
     */
    public void setUp(Animal[][] animals) {
        // Start flag for all groups in RegularTournament
        AtomicBoolean startFlag = new AtomicBoolean(false);

        // Empty scores for all groups - every tournament has one Scores object
        Scores scores = new Scores();

        // Check if the animals array is null
        if (animals == null) {
            return;
        }

        // Initialize route flags for up to 5 routes
        AtomicBoolean[] tournamentRoutes = new AtomicBoolean[5];
        for (int i = 0; i < 5; i++) {
            tournamentRoutes[i] = new AtomicBoolean(false);
        }

        // Determine the type of animals in the tournament based on the category of the first animal
        int type = getAnimalAsNumber(animals[0][0].getCategory());

        // Set up and start threads for each animal and its corresponding referee
        for (Animal[] animalGroup : animals) {
            Animal animal = animalGroup[0];
            animal.setDestination();
            animal.setIsAvailable(false);

            if (type != 3) {
                int route = animal.getCompetitionRoute();
                tournamentRoutes[route - 1].set(true);
            }

            // Finish flag for each animal
            AtomicBoolean finishFlag = new AtomicBoolean(false);

            // Create and start a thread for the animal's race
            AnimalThread animalThread = new AnimalThread(animal, animal.getLenOfRoute(), startFlag, finishFlag);
            Thread animalTournamentThread = new Thread(animalThread);
            animalTournamentThread.start();

            // Create and start a thread for the referee monitoring the animal's race
            Referee referee = new Referee(animal.getAnimalName(), finishFlag, scores);
            Thread refereeTournamentThread = new Thread(referee);
            refereeTournamentThread.start();
        }

        // Create and start the tournament thread
        super.setTournamentThread(new TournamentThread(scores, startFlag, animals.length, type, 1, tournamentRoutes));
        Thread regularTournamentThread = new Thread(getTournamentThread());
        regularTournamentThread.start();
    }
}
