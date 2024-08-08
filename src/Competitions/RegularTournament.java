package Competitions;

import Animals.Animal;
import Animals.AnimalThread;

public class RegularTournament extends Tournament {

    public RegularTournament(Animal[][] animals){
        super(animals);
    }

    //public AnimalThread(Animal participant, double neededDistance, Boolean startFlag, Boolean finishFlag) {
    public void setUp(Animal[][] animals){
        Boolean startFlag = false; //startFlag of all groups in RegularTournament
        Scores scores = new Scores(); //empty scores for all groups


        for(Animal[] animal:animals) {
            Boolean finishFlag = false; //finishFlag for each animal
            //Todo where animalThread is saved? //start animalThread?
            AnimalThread animalThread = new AnimalThread(animal[0], 500, startFlag, finishFlag); //Todo add a function here or in animal thread that calc the distance per animal type
            Referee referee = new Referee(animal[0].getAnimalName(), finishFlag, scores);

            Thread animalTournamentThread = new Thread(animalThread);
            animalTournamentThread.start();

        }
        tournamentThread = new TournamentThread(scores,startFlag,animals.length); //creating tournamentThread

        Thread regularTournamentThread = new Thread(tournamentThread);
        regularTournamentThread.start();

    }
}
