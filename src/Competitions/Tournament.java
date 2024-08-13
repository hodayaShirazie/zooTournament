package Competitions;

import Animals.Animal;

public abstract class Tournament {

    private TournamentThread tournamentThread;

    public Tournament(Animal[][] animals) {
        setUp(animals);
    }

    public abstract void setUp(Animal[][] animals);

    protected int getAnimalAsNumber(String animalName) {
        switch (animalName) {
            case "Air":
                return 1;
            case "Water":
                return 2;
            case "Terrestrial":
                return 3;
        }
        return 0;
    }

    protected void setTournamentThread(TournamentThread tournamentThread) {
        this.tournamentThread = tournamentThread;
    }

    protected TournamentThread getTournamentThread() {
        return tournamentThread;
    }

}
