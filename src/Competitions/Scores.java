package Competitions;
import java.util.Date;
import java.util.Collections;
import java.util.HashMap;

import java.util.Map;

public class Scores {
    private Map<String,Date>scores;


    public Scores() {
        this.scores = Collections.synchronizedMap(new HashMap<>());
    }

    public Scores(Map<String,Date>scores) {
        this.scores = Collections.synchronizedMap(scores);

    }

    public void add(String name)
    {
        this.scores.put(name,new Date());
    }

    public Map<String,Date> getAll()
    {
        return Collections.unmodifiableMap(scores);
    }

    @Override
    public String toString() {
        System.out.println("Scores to stringg");
        String str = "";
        for(Map.Entry<String,Date> entry : scores.entrySet())
        {
            str += entry.getKey() + " " + entry.getValue() + "\n";
        }
        return str;

    }
}