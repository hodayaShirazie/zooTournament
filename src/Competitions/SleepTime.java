package Competitions;

public class SleepTime {


    private static SleepTime instance = null;
    private int time;

    private SleepTime() {
        // קונסטרקטור פרטי למניעת יצירה חיצונית
    }

    public static synchronized SleepTime getInstance() {
        if (instance == null) {
            instance = new SleepTime();
        }
        return instance;
    }

    public synchronized void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public String toString(){
        return "Sleep time: " + this.time;
    }

}



