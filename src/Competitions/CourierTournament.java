package Competitions;

import Animals.Animal;
import Animals.AnimalThread;

//Todo implement a function that calculate the length of path in Animal
public class CourierTournament extends Tournament {

    public CourierTournament(Animal[][] animals) {
        super(animals);
    }

    //public AnimalThread(Animal participant, double neededDistance, Boolean startFlag, Boolean finishFlag) {
    public void setUp(Animal[][] animals) {
        Boolean startFlag = false; //startFlag of all groups in CourierTournament
        Scores scores = new Scores(); //empty scores for all groups
        int numberOfGroups = animals.length;
        int numberOfAnimalsMembers;
        for (int k = 0; k < numberOfGroups; ++k) {
            numberOfAnimalsMembers = animals[k].length; //saving number of animal members //its like n

            Boolean[] flags = new Boolean[numberOfAnimalsMembers]; //creating flags for animals in group
            for (int i = 0; i < numberOfAnimalsMembers; ++i)
                flags[i] = false;

            int neededDistance = 1000 / numberOfAnimalsMembers;  //Todo: change 1000 to size of competition path

            AnimalThread animalThread = new AnimalThread(animals[k][0], neededDistance, startFlag, flags[0]);
            for (int i = 1; i < numberOfAnimalsMembers; i++) {
                animalThread = new AnimalThread(animals[k][i], neededDistance, flags[i - 1], flags[i]);
            }

            Referee referee = new Referee("group " + (k + 1), flags[numberOfAnimalsMembers - 1], scores);
        }
        tournamentThread = new TournamentThread(scores, startFlag, numberOfGroups); //creating tournamentThread
    }
}
