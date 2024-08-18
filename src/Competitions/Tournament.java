package Competitions;

import Animals.Animal;

/**
 * Abstract class representing a generic tournament involving animals.
 * This class sets up and manages the tournament thread.
 */
public abstract class Tournament {

    /**
     * The thread responsible for running the tournament
     */
    private TournamentThread tournamentThread;

    /**
     * Constructs a Tournament and sets it up with the given animals.
     *
     * @param animals A 2D array of Animal objects representing the participants in the tournament.
     */
    public Tournament(Animal[][] animals) {
        setUp(animals);
    }

    /**
     * Abstract method to set up the tournament with the given animals.
     * This method must be implemented by subclasses to define the specifics of the tournament setup.
     *
     * @param animals A 2D array of Animal objects representing the participants in the tournament.
     */
    public abstract void setUp(Animal[][] animals);

    /**
     * Converts an animal's category name to a corresponding integer value.
     *
     * @param animalName The name of the animal's category.
     * @return An integer representing the animal's category. Returns 0 if the category name does not match any known categories.
     */
    protected int getAnimalAsNumber(String animalName) {
        switch (animalName) {
            case "Water":
                return 1;
            case "Air":
                return 2;
            case "Terrestrial":
                return 3;
            case "Terrestrial+Water":
                return 1;
            default:
                return 0;
        }
    }

    /**
     * Sets the TournamentThread for this tournament.
     *
     * @param tournamentThread The TournamentThread to set.
     */
    protected void setTournamentThread(TournamentThread tournamentThread) {
        this.tournamentThread = tournamentThread;
    }

    /**
     * Gets the TournamentThread associated with this tournament.
     *
     * @return The TournamentThread associated with this tournament.
     */
    public TournamentThread getTournamentThread() {
        return tournamentThread;
    }
}
