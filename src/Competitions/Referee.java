package Competitions;

public class Referee implements Runnable {

    private String name;
    private Scores scores;
    private Boolean finishFlag;

    Referee(String name,Boolean finishFlag, Scores scores) {
        this.name = name;
        this.scores = scores;
        this.finishFlag = finishFlag;
    }

    Referee(String name, Scores scores) {
        this.name = name;
        this.scores = scores;
    }

    @Override
    public void run() {
        synchronized (scores)
        {
            scores.add(name);
        }
    }

}