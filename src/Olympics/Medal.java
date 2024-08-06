package Olympics;

/**
 * Represents a medal.
 */

public class Medal {

    /**
     * The type of the medal (Gold, Silver, Bronze).
     */
    private MedalType type;

    /**
     * The name or type of the tournament where the medal was awarded.
     */
    private String tournament;

    /**
     * The year in which the medal was awarded.
     */
    private int year;


    /**
     * Constructs a new Medal with specified type, tournament, and year.
     *
     * @param type the type of the medal: bronze, silver or gold.
     * @param tournament the tournament in which the medal was won.
     * @param year the year the medal was won. if the year is less than or equal to 0, year is set to 2024.
     */
    public Medal(MedalType type, String tournament,int year){
        this.type = type;
        this.tournament = tournament;
        if(year <= 0)
            year = 2024;
        this.year = year;
    }

    /**
     * Constructs a new Medal with default values.
     */
    public Medal(){
        this.type = MedalType.bronze;
        this.tournament = "Tel aviv";
        this.year = 2024;
    }

    /**
     * Constructs a new Medal with values copied from the specified medal.
     *
     * @param medal the medal to duplicate.
     */
    public Medal(Medal medal){
        type = medal.type;
        tournament =  medal.tournament;;
        year = medal.year;
    }

    /**
     *
     * @return a string which describes the medal, including its type, tournament, and year.
     */
    public String toString()
    {
        return "Medal type: " + type + ", tournament: " + tournament + ", year: " + year;
    }

    /**
     * Check if medal object is equal to origin.
     *
     * @param obj the object to compare with.
     * @return true if medal is equal to origin and false otherwise.
     */
    public boolean equals(Object obj) {

        if (!(obj instanceof Medal)) {
            return false;
        }
        Medal other = (Medal) obj;

        return this.type == other.type && this.tournament.equals(other.tournament)  && this.year == other.year;
    }
}
