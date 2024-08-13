package Competitions;

import Animals.Animal;
import Animals.AnimalThread;

import java.util.concurrent.atomic.AtomicBoolean;

//Todo implement a function that calculate the length of path in Animal
public class CourierTournament extends Tournament {

    public CourierTournament(Animal[][] animals) {
        super(animals);
    }

    //public AnimalThread(Animal participant, double neededDistance, Boolean startFlag, Boolean finishFlag) {
    public void setUp(Animal[][] animals) {
        AtomicBoolean startFlag = new AtomicBoolean(false); //startFlag of all groups in CourierTournament
        int numberOfGroups = animals.length;
        int numberOfAnimalsMembers;

        int type = getAnimalAsNumber(animals[0][0].getCategory());

        Scores groupsScores = new Scores();

        for (int k = 0; k < numberOfGroups; ++k) {

            numberOfAnimalsMembers = animals[k].length; //saving number of animal members //its like n

            AtomicBoolean [] flags = new AtomicBoolean[numberOfAnimalsMembers]; //creating flags for animals in group
            for (int i = 0; i < numberOfAnimalsMembers; ++i)
                flags[i] = new AtomicBoolean(false);

            int neededDistance = 1000 / numberOfAnimalsMembers;  //Todo: change 1000 to size of competition path

            AnimalThread animalThreadFirst = new AnimalThread(animals[k][0], neededDistance, startFlag, flags[0]);
            Thread threadFirst = new Thread(animalThreadFirst);
            threadFirst.start();

            for (int i = 1; i < numberOfAnimalsMembers; i++) {
                AnimalThread animalThread = new AnimalThread(animals[k][i], neededDistance, flags[i - 1], flags[i]);
                Thread thread = new Thread(animalThread);
                thread.start();

            }

            Referee finishReferee = new Referee(("group " + (k + 1)), flags[numberOfAnimalsMembers-1], groupsScores);
            Thread finishThread = new Thread(finishReferee);
            finishThread.start();

        }

        super.setTournamentThread(new TournamentThread(groupsScores, startFlag, numberOfGroups, type));        //creating tournamentThread

    }
}
