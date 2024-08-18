package Competitions;

/**
 * Singleton class for managing the sleep time duration.
 * This class ensures that only one instance of SleepTime is created and provides methods to set and get the sleep time.
 */
public class SleepTime {

    /**
     * Static instance of SleepTime for Singleton pattern
     */
    private static SleepTime instance = null;

    /**
     * Variable to store the sleep time duration
     */
    private int time;

    /**
     * Private constructor to prevent external instantiation.
     */
    private SleepTime() {
        // Private constructor to prevent external instantiation
    }

    /**
     * Gets the single instance of the SleepTime class.
     * If the instance does not exist, it is created and initialized.
     *
     * @return The single instance of the SleepTime class.
     */
    public static synchronized SleepTime getInstance() {
        if (instance == null) {
            instance = new SleepTime();
        }
        instance.setTime(2000); // Default initialization value
        return instance;
    }

    /**
     * Sets the sleep time duration.
     *
     * @param time The sleep time duration in milliseconds.
     */
    public synchronized void setTime(int time) {
        this.time = time;
    }

    /**
     * Gets the current sleep time duration.
     *
     * @return The current sleep time duration in milliseconds.
     */
    public int getTime() {
        return time;
    }

    /**
     * Returns a string representation of the SleepTime instance.
     *
     * @return A string representing the sleep time duration.
     */
    @Override
    public String toString() {
        return "Sleep time: " + this.time;
    }
}
