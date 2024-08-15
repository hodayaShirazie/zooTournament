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
            case "Water":
                return 1;
            case "Air":
                return 2;
            case "Terrestrial":
                return 3;
            case "Terrestrial+Water":
                return 1;
        }
        return 0;
    }

    protected void setTournamentThread(TournamentThread tournamentThread) {
        this.tournamentThread = tournamentThread;
    }

    public TournamentThread getTournamentThread() {
        return tournamentThread;
    } //Todo changes from protected to publick. checkk

}
