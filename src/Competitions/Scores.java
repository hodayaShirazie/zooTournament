package Competitions;

import java.util.Date;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The Scores class maintains a record of scores, where each score is associated with a name and a timestamp.
 * Scores are stored in a thread-safe manner using a synchronized map.
 */
public class Scores {

    /**
     * Map to store scores, where the key is the name and the value is the timestamp of the score
     * */
    private Map<String, Date> scores;

    /**
     * Constructs an empty Scores object with a synchronized map.
     */
    public Scores() {
        this.scores = Collections.synchronizedMap(new HashMap<>());
    }

    /**
     * Constructs a Scores object with the given map of scores.
     *
     * @param scores A map of scores where the key is the name and the value is the timestamp.
     */
    public Scores(Map<String, Date> scores) {
        this.scores = Collections.synchronizedMap(scores);
    }

    /**
     * Adds a new score with the current timestamp for the given name.
     *
     * @param name The name associated with the score.
     */
    public void add(String name) {
        this.scores.put(name, new Date());
    }

    /**
     * Retrieves all scores in the form of a map.
     *
     * @return A map of all scores, where the key is the name and the value is the timestamp.
     */
    public Map<String, Date> getAll() {
        return scores;  // Consider returning an unmodifiable view if the map should not be modified outside.
    }

    /**
     * Returns a string representation of all scores.
     *
     * @return A string representing all scores, including names and timestamps.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<String, Date> entry : scores.entrySet()) {
            str.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
        }
        return str.toString();
    }
}
