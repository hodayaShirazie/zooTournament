package Competitions;

import Animals.Animal;
import Animals.AnimalThread;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class RegularTournament extends Tournament {

    public RegularTournament(Animal[][] animals){
        super(animals);
    }

    //public AnimalThread(Animal participant, double neededDistance, Boolean startFlag, Boolean finishFlag) {
    public void setUp(Animal[][] animals){
        AtomicBoolean startFlag = new AtomicBoolean(false); //startFlag of all groups in RegularTournament
        Scores scores = new Scores(); //empty scores for all groups - every tournament has 1 scores

        int type = getAnimalAsNumber(animals[0][0].getCategory());


        for(Animal[] animal:animals) {
            AtomicBoolean finishFlag = new AtomicBoolean(false); //finishFlag for each animal
            AnimalThread animalThread = new AnimalThread(animal[0], animal[0].getDistance(), startFlag, finishFlag); //Todo add a function here or in animal thread that calc the distance per animal type

            Thread animalTournamentThread = new Thread(animalThread);
            animalTournamentThread.start();

            Referee referee = new Referee(animal[0].getAnimalName(), finishFlag, scores);

            Thread refereeTournamentThread = new Thread(referee);
            refereeTournamentThread.start();


        }
        super.setTournamentThread(new TournamentThread(scores,startFlag,animals.length, type)); //creating tournamentThread

        Thread regularTournamentThread = new Thread(getTournamentThread());
        regularTournamentThread.start();

    }
}
