package Competitions;

import Animals.Animal;
import Animals.AnimalThread;

import java.util.concurrent.atomic.AtomicBoolean;

public class RegularTournament extends Tournament {

    public RegularTournament(Animal[][] animals){
        super(animals);
    }

    //public AnimalThread(Animal participant, double neededDistance, Boolean startFlag, Boolean finishFlag) {
    public void setUp(Animal[][] animals){
        AtomicBoolean startFlag = new AtomicBoolean(false); //startFlag of all groups in RegularTournament
        Scores scores = new Scores(); //empty scores for all groups - every tournament has 1 scores

        if(animals == null) {
            System.out.println("cannot perform operation- array is null");
            return;
        }

        AtomicBoolean [] tournamentRouts = new AtomicBoolean[5];
        for(int i = 0; i < 5; i++){
            tournamentRouts[i] = new AtomicBoolean(false);
        }

        int type = getAnimalAsNumber(animals[0][0].getCategory());


        for(Animal[] animal:animals) {
            animal[0].setDestination();
            animal[0].setIsAvailable(false);

            if (type != 3) {
                int route = animal[0].getCompetitionRoute();
                tournamentRouts[route - 1].set(true);
            }

            AtomicBoolean finishFlag = new AtomicBoolean(false); //finishFlag for each animal
            AnimalThread animalThread = new AnimalThread(animal[0], animal[0].getDistance(), startFlag, finishFlag); //Todo add a function here or in animal thread that calc the distance per animal type

            System.out.println("regular needed distance: " + animal[0].getDistance());
            Thread animalTournamentThread = new Thread(animalThread);
            animalTournamentThread.start();

            Referee referee = new Referee(animal[0].getAnimalName(), finishFlag, scores);

            Thread refereeTournamentThread = new Thread(referee);
            refereeTournamentThread.start();
        }
        super.setTournamentThread(new TournamentThread(scores,startFlag,animals.length, type,1, tournamentRouts)); //creating tournamentThread

        Thread regularTournamentThread = new Thread(getTournamentThread());
        regularTournamentThread.start();

    }
}
