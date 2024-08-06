package Competitions;

import Animals.Animal;

public abstract class Tournament {

    protected TournamentThread tournamentThread;

    public Tournament(Animal[][] animals) {
        setUp(animals);
    }

    public void setUp(Animal[][] animals){}

    public TournamentThread getTournamentThread() {
        return tournamentThread;
    }
}
