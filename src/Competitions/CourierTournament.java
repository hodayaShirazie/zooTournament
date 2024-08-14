package Competitions;

import Animals.Animal;
import Animals.AnimalThread;
import Mobility.Point;

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

        AtomicBoolean [] tournamentRouts = new AtomicBoolean[5];
        for(int i = 0; i < 5; i++){
            tournamentRouts[i] = new AtomicBoolean(false);
        }

        for (int k = 0; k < numberOfGroups; ++k) {

            if (type != 3) {
                int route = animals[k][0].getCompetitionRoute();
                tournamentRouts[route - 1].set(true);
            }

            numberOfAnimalsMembers = animals[k].length; //saving number of animal members //its like n

            AtomicBoolean [] flags = new AtomicBoolean[numberOfAnimalsMembers]; //creating flags for animals in group
            for (int i = 0; i < numberOfAnimalsMembers; ++i)
                flags[i] = new AtomicBoolean(false);

            int neededDistance = animals[k][0].getLenOfRoute() / numberOfAnimalsMembers;  //Todo: change 1000 to size of competition path

            AnimalThread animalThreadFirst = new AnimalThread(animals[k][0], neededDistance, startFlag, flags[0]);
            animals[k][0].setIsAvailable(false);

            Thread threadFirst = new Thread(animalThreadFirst);
            threadFirst.start();
            System.out.println("initial location for animal is 0 " + (animals[k][0].getLocation()));


            System.out.println("needed distance = " + neededDistance);
            for (int i = 1; i < numberOfAnimalsMembers; i++) {
                System.out.println("initial location for animal is " + (new Point(i*neededDistance,animals[k][i].getLocation().getY())).toString());
                animals[k][i].setLocation(new Point(i*neededDistance + animals[k][i].getXinit(),animals[k][i].getLocation().getY()));
                System.out.println("animal location " + animals[k][i].getLocation());

                animals[k][i-1].setDestination(animals[k][i].getLocation());
                System.out.println("name: " + animals[k][i-1].getAnimalName()+ " destination " +animals[k][i-1].getDestination() );


                animals[k][i].setIsAvailable(false);

                AnimalThread animalThread = new AnimalThread(animals[k][i], neededDistance, flags[i - 1], flags[i]);
                Thread thread = new Thread(animalThread);
                thread.start();

            }
            animals[k][numberOfAnimalsMembers-1].setDestination();
            System.out.println("name: " + animals[k][numberOfAnimalsMembers-1].getAnimalName()+ " destination " +animals[k][numberOfAnimalsMembers-1].getDestination() );


            Referee finishReferee = new Referee(("group " + (k + 1)), flags[numberOfAnimalsMembers-1], groupsScores);
            Thread finishThread = new Thread(finishReferee);
            finishThread.start();

        }

        super.setTournamentThread(new TournamentThread(groupsScores, startFlag, numberOfGroups, type, 2, tournamentRouts));        //creating tournamentThread
        Thread courierTournamentThread = new Thread(getTournamentThread());
        courierTournamentThread.start();

    }
}
