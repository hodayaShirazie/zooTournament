package Animals;

import java.lang.Cloneable;
import java.awt.Graphics;
import javax.swing.Timer;
import Mobility.ILocatable;
import Mobility.Mobile;
import Mobility.Point;
import Olympics.Medal;
import Graphics.IDrawable;
import Graphics.IMovable;
import Graphics.IAnimal;
import Graphics.CompetitionPanel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import Graphics.ZooPanel;

/**
 * Represents an animal.
 * cannot perform instances from this class.
 */
public abstract class Animal extends Mobile implements IAnimal,IMovable, Cloneable, IDrawable, ILocatable {


    /**
     * Represents the cloneable state of the object.
     */
    private Cloneable cloneable;

    /**
     * Represents the drawable state of the object.
     */
    private IDrawable drawable;

    /**
     * Represents the locatable state of the object.
     */
    private ILocatable locatable;

    /**
     * Represents the size of the object.
     */
    private int size;

    /**
     * Represents the unique identifier for the object.
     */
    private int id;

    /**
     * Represents the orientation of the animal.
     */
    private Orientation orientation;

    /**
     * Represents the maximum energy the animal can have.
     */
    private int maxEnergy;

    /**
     * Represents the current energy of the animal.
     */
    private int currentEnergy;

    /**
     * Represents the total energy the animal has gained from eating.
     */
    private int totalEnergyFromEating;

    /**
     * Represents the energy consumed per meter of movement.
     */
    private int energyPerMeter;

    /**
     * Represents the competition panel associated with the animal.
     */
    private ZooPanel zooPanel;

    /**
     * The CompetitionPanel associated with this animal.
     * This panel represents the specific competition that the animal is participating in.
     */
    private CompetitionPanel panel;

    /**
     * Represents the first buffered image associated with the animal.
     */
    private BufferedImage img1;

    /**
     * Timer used for moving the animal along a straight line.
     */
    private Timer timer;

    /**
     * Timer used for moving the animal along a complex path.
     */
    private Timer moveTimer;

    /**
     * Indicates the completion status of the animal in a process or competition.
     */
    private int done;

    /**
     * The name of the animal.
     */
    private String name;

    /**
     * The gender of the animal.
     */
    private Gender gender;

    /**
     * The weight of the animal.
     */
    private double weight;

    /**
     * The speed of the animal.
     */
    private double speed;

    /**
     * An array of medals of the animal.
     */
    private Medal [] medals;

    /**
     * Indicates whether the animal has been removed from the competition.
     */
    private boolean isAvailable;

    /**
     * The destination point for the animal's movement.
     * This represents the target location the animal is moving towards.
     */
    private Point destination;

    /**
     * Indicates whether the animal needs to move.
     * This flag is used to determine if the animal should start or continue moving.
     */
    private boolean needToMove;

    /**
     * Constructs an Animal with the specified name, speed, energy per meter, and maximum energy.
     * <p>
     * The animal is initialized with default values for other attributes:
     * - Gender is set to Hermaphrodite
     * - Weight is set to 10
     * - Medals are set to null
     * - Size is set to 65
     * - ID is set to 000000000
     * - Orientation is set to EAST
     * - ZooPanel is set to null
     * - Image is set to null
     * - Availability is set to true
     * - Done is set to 0
     * - Drawable, Locatable, and Cloneable are set to null
     * </p>
     *
     * @param name the name of the animal
     * @param speed the speed of the animal in units per second
     * @param energyPerMeter the energy consumed by the animal per meter
     * @param maxEnergy the maximum energy the animal can have
     */
    public Animal(String name, int speed, int energyPerMeter, int maxEnergy) {
        super();
        this. name = name;
        this.speed = speed;
        this.maxEnergy = maxEnergy;
        this.currentEnergy = 0;
        this.energyPerMeter = energyPerMeter;
        this.totalEnergyFromEating = 0;

        this.gender = Gender.Hermaphrodite;
        this.weight = 10;
        this.medals = null;

        this.size = 65;
        this.id = 000000000;
        this.orientation = Orientation.EAST;
        this.zooPanel = null;
        this.img1 = null;

        isAvailable = true;
        done  = 0;

        drawable = null;
        locatable = null;
        cloneable = null;


    }

    /**
     * Constructs a new Animal with the specified attributes.
     * <p>
     * Initializes the animal with the given name, speed, energy per meter, and maximum energy. Other attributes are set to default values.
     * </p>
     *
     * @param name            the name of the animal
     * @param speed           the speed of the animal
     * @param energyPerMeter  the energy consumed by the animal per meter of movement
     * @param maxEnergy       the maximum energy the animal can have
     * @param zooPanel        the competition panel associated with the animal
     */
    public Animal(String name, int speed, int energyPerMeter, int maxEnergy, ZooPanel zooPanel) {
        super();
        this. name = name;
        this.speed = speed;
        this.maxEnergy = maxEnergy;
        this.currentEnergy = 0;
        this.energyPerMeter = energyPerMeter;
        this.totalEnergyFromEating = 0;

        this.gender = Gender.Hermaphrodite;
        this.weight = 10;
        this.medals = null;

        this.size = 65;
        this.id = 000000000;
        this.orientation = Orientation.EAST;
        this.zooPanel = zooPanel;
        this.panel = null;
        this.img1 = null;
        this.destination = null;

        this.isAvailable = true;
        this.done  = 0;

        this.drawable = null;
        this.locatable = null;
        this.cloneable = null;

        this.needToMove = false;


    }

    /**
     * Constructs a new Animal with the specified attributes.
     * <p>
     * Initializes the animal with detailed attributes including name, gender, weight, speed, medals, location, size, ID, orientation,
     * maximum energy, and energy per meter. Processes the medals array to remove duplicates. Sets default values for weight and speed if non-positive values are provided.
     * </p>
     *
     * @param name            the name of the animal
     * @param gender          the gender of the animal
     * @param weight          the weight of the animal; if non-positive, defaults to 10
     * @param speed           the speed of the animal; if non-positive, defaults to 10
     * @param medals          an array of medals awarded to the animal; duplicates are removed
     * @param loc             the initial location of the animal
     * @param size            the size of the animal
     * @param id              the unique identifier for the animal
     * @param orientation     the orientation of the animal
     * @param maxEnergy       the maximum energy the animal can have
     * @param energyPerMeter  the energy consumed by the animal per meter of movement
     * @param pan             the competition panel associated with the animal
     */
    public Animal(String name, Gender gender, double weight, double speed, Medal[] medals   ,Point loc ,int size, int id, Orientation orientation,int maxEnergy, int energyPerMeter, ZooPanel pan) {
        super(loc,0);
        this.name = name;
        this.gender = gender;

        // Validate and set default values for weight and speed
        if(weight <= 0 || speed <= 0)
        {
            this.weight = 10;
            this.speed = 10;
        }
        else{
            this.weight = weight;
            this.speed = speed;
        }

        // Process the medals array to remove duplicates and ensure uniqueness
        if(medals != null)
        {
            // Calculate the actual size of the unique medals array
            int medalSize = medals.length;
            boolean isExists;
            for (int i=0; i<medals.length; i++)
            {
                isExists = false;
                for (int j = i+1; j < medals.length && !isExists; j++) {
                    if (medals[i].equals(medals[j])) {
                        --medalSize;
                        isExists = true;
                    }
                }
            }

            // Create a new array to store unique Medal objects
            this.medals = new Medal[medalSize];
            this.medals[0] = new Medal(medals[0]); // Copy the first element
            int k=1;
            for (int i=1; i<medals.length; i++)
            {
                isExists = false;
                for (int j = 0; j < k && k<medalSize && !isExists; j++)
                {
                    if (this.medals[j].equals(medals[i]))
                    {
                        isExists = true;
                    }
                    if (!isExists)
                    {
                        this.medals[k] = new Medal(medals[i]);
                        ++k;
                    }
                }
            }
        }
        else
            this.medals = medals; // Copy the first element



        this.size = size;
        this.id = id;
        this.orientation = orientation;
        this.maxEnergy = maxEnergy;
        this.currentEnergy = 0;
        this.energyPerMeter = energyPerMeter;
        this.totalEnergyFromEating = 0;

        this.zooPanel = pan;

        this.img1 = null;
        isAvailable = true;

        drawable = null;
        locatable = null;
        cloneable = null;

    }

    /**
     * Constructs a new Animal with default values.
     * <p>
     * Initializes the animal with default values for all attributes.
     * </p>
     */
    public Animal() {
        super();
        done  = 0;
        this. name = "raxi";
        this.gender = Gender.Hermaphrodite;
        this.weight = 10;
        this.speed = 10;
        this.medals = null;


        //added
        this.size = 65;
        this.id = 000000000;
        this.orientation = Orientation.EAST;
        this.maxEnergy = 200;
        this.currentEnergy = 0;
        this.energyPerMeter = 30;
        this.totalEnergyFromEating = 0;
        this.zooPanel = null;

        this.img1 = null;
        isAvailable = true;

        drawable = null;
        locatable = null;
        cloneable = null;
    }

    /**
     * Function that prints the name and sound of animal.
     * the function calls sound function that prints the sound.
     */
    public final void makeSound(){
        sound();
    }

    /**
     * Produces a sound characteristic of the animal.
     * This method should be implemented by subclasses to specify the sound.
     */
    protected abstract void sound();

    /**
     * @return a string which describes the Animal object, including its name, gender, weight, speed, medals, position, total distance
     */
    public String toString() {
        String string =  "name: " + name + "\ngender: " + gender + "\nweight: " + weight + "\nspeed: " + speed;
        if(medals != null) {
            if (medals.length > 0) {
                string += "\n---***--medals---***--\n";
                for (int i = 0; i < medals.length; ++i)
                    string += "(" + (i + 1) + ") " + medals[i].toString() + "\n";
                string += "---***----------***--\n";
            }
            else if (medals.length == 0) {
                string += "\n";

            }
        }
        else  {
            string += "\n";

        }
        string += super.toString();

        return string;
    }

    /**
     * Checks if the medals of this animal object are equal to another object's medals.
     * Equality is determined by comparing the content of the medals arrays; they are considered
     * equal if they contain the same medals, regardless of their indexed positions.
     *
     @param obj the object to compare with
     @return true if the medals arrays of both objects contain identical medals,
     false otherwise or if the comparison object is not an instance of Animal
     */
    private boolean isMedalsEqual(Object obj) {
        boolean isExists;
        // Check if both this animal and the other object have non-null medals arrays
        if(medals != null && ((Animal)obj).medals != null)
        {
            // Check if the lengths of the medals arrays are equal
            if (medals.length != ((Animal) obj).medals.length)
                return false;
            else
                // Compare each medal in this animal's medals array with the other object's medals array
                for (int i = 0; i < medals.length; ++i)
                {
                    isExists = false;
                    for (int j = 0; j < medals.length && !isExists; ++j)
                        if (medals[i].equals(((Animal) obj).medals[j]))
                            isExists = true;
                    // If a medal in this animal's array is not found in the other object's array, return false
                    if (!isExists)
                        return false;
                }
        }
        // Check if one has null medals array while the other has non-null medals array
        else if((medals != null && ((Animal)obj).medals == null) || (medals == null && ((Animal)obj).medals != null))
            return false;
        // If both have null medals arrays or both have non-null and equal length medals arrays, return true
        return true;
    }

    /**
     * Check if animal is equal to origin.
     *
     * @param obj the object to compare with.
     * @return true if animal is equal to origin and false otherwise.
     */
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if(obj instanceof Animal) {
            isEqual = name.equals (((Animal) obj).name);
            isEqual = isEqual && gender == ((Animal)obj).gender;
            isEqual = isEqual && weight == ((Animal)obj).weight;
            isEqual = isEqual && speed == ((Animal)obj).speed;
            isEqual = isEqual && isMedalsEqual(obj);
            isEqual = isEqual && super.equals((obj));
        }
        return isEqual;
    }

    /**
     * Loads the image for the animal based on the given image name.
     * If the image cannot be loaded, it prints an error message.
     *
     * @param imageName the name of the image file to load (excluding file extension)
     */
    public void loadImages(String imageName) {
        try {
            img1 = ImageIO.read(new File(PICTURE_PATH + imageName + "_right_down.png"));
        }
        catch (IOException e) {
            System.out.println("Cannot load images for " + imageName); }
    }

    /**
     * Draws the animal on the given graphics context.
     * The image will only be drawn if it has been loaded successfully.
     *
     * @param g the graphics context to draw on
     */
    public void drawObject (Graphics g) {
        if(img1 != null)
            g.drawImage(img1, super.getLocationX(), super.getLocationY(), size, size, zooPanel);
    }

    /**
     * Creates and returns a copy of this animal.
     *
     * @return a clone of this animal
     * @throws AssertionError if cloning is not supported
     *///
    @Override
    public Animal clone() throws AssertionError {
        try{
            return (Animal)super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            throw new AssertionError();
        }

    }

    /**
     * Increases the animal's current energy by the specified amount if the amount is positive
     * and does not exceed the maximum energy limit. Updates the total energy gained from eating.
     *
     * @param energy the amount of energy to be added
     * @return true if the energy was successfully added, false otherwise
     */
    public boolean eat(int energy) {
        if (energy <= 0)
            return false;
        if (currentEnergy + energy > maxEnergy)
            return false;
        currentEnergy += energy; //update current energy
        totalEnergyFromEating += energy; //update total energy from eating
        return true;
    }

    /**
     * Abstract method to start moving the animal.
     * This method must be implemented by subclasses to define how the animal starts moving.
     */
//    public abstract void startMoving();

    /**
     * Starts moving the animal toward a specified destination.
     * A timer is used to periodically move the animal toward the destination.
     *
     */
    public void startMoving() {
        timer = new Timer(100 / 60, e -> moveToward());
        timer.start();
    }

    /**
     * Moves the animal toward the specified destination point.
     * <p>
     * The animal moves in the direction of its current orientation (East, West, North, South) until it reaches
     * the destination or runs out of energy. If the animal's energy drops below zero, it stops moving and energy is set to zero.
     * </p>
     *
     */
    protected void moveToward() {
        double frameSpeed = speed / 60;
        if (frameSpeed < 1)
            frameSpeed = 1;
        // && timer != null && moveTimer != null
        if (currentEnergy <= 0 ) {
            timer.stop();
            if(moveTimer != null)
                moveTimer.stop();
            currentEnergy = 0;
            setNeedToMove(true);
            return;
        }
        if (orientation == Orientation.EAST || orientation == Orientation.WEST) {
            if (Math.abs(getLocationX() - destination.getX()) <= frameSpeed) {
                move(destination);
                timer.stop();

            } else {
                if (destination.getX() > getLocationX())
                    move(new Point((int) (getLocationX() + frameSpeed), getLocationY()));
                else
                    move(new Point((int) (getLocationX() - frameSpeed), getLocationY()));
            }

        }
        else if (orientation == Orientation.SOUTH || orientation == Orientation.NORTH) {

            if (Math.abs(getLocationY() - destination.getY()) <= frameSpeed) {
                move(destination);
                timer.stop();

            } else {
                if (destination.getY() > getLocationY())
                    move(new Point(getLocationX(), (int)(getLocationY() + frameSpeed)));
                else
                    move(new Point(getLocationX(), (int)(getLocationY() - frameSpeed)));
            }

        }

        currentEnergy -= frameSpeed*energyPerMeter;


    }

    /**
     * Checks if the animal is currently moving.
     *
     * @return true if the animal is moving, false otherwise
     */
    public boolean isMoving() {
        if (timer != null) {
            return timer.isRunning();
        }
        return false;
    }

    /**
     * Returns the competition panel associated with the animal.
     *
     * @return the competition panel
     */
    public ZooPanel getZooPanel() {
        return zooPanel;
    }

    /**
     * Returns the maximum energy the animal can have.
     *
     * @return the maximum energy
     */
    public int getMaxEnergy() {
        return maxEnergy;
    }

    /**
     * Returns the current energy of the animal.
     *
     * @return the current energy
     */
    public int getCurrentEnergy() {
        return currentEnergy;
    }

    /**
     * Returns the category of the animal.
     *
     * @return the category of the animal
     */
    public abstract String getCategory();

    /**
     * Returns the type of the animal.
     *
     * @return the type of the animal
     */
    public abstract String getType();

    /**
     * Returns the name of the animal.
     *
     * @return the name of the animal
     */
    public String getAnimalName() {
        return name;
    }

    /**
     * Gets the current speed of the animal.
     *
     * @return the current speed of the animal.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Returns the completion status of the animal.
     *
     * @return the completion status
     */
    public int isDone() {
        return done;
    }

    /**
     * Returns the orientation of the animal.
     *
     * @return the orientation of the animal
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Returns the total energy the animal has gained from eating.
     *
     * @return the total energy from eating
     */
    public int getTotalEnergyFromEating() {
        return totalEnergyFromEating;
    }

    /**
     * Returns the size of the animal.
     *
     * @return the size of the animal
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the move timer used for moving the animal along a complex path.
     *
     * @return the move timer
     */
    public Timer getMoveTimer() {
        return moveTimer;
    }

    /**
     * Sets the orientation of the animal.
     *
     * @param orientation the orientation to set
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * Sets the speed of the animal if the given speed is greater than 0.
     *
     * @param speed the speed to set.
     * @return true if the speed was set, false otherwise.
     */
    protected boolean setSpeed(double speed) {
        if(speed > 0) {
            this.speed = speed;
            return true;
        }
        return false;
    }

    /**
     * Sets the move timer used for moving the animal along a complex path.
     *
     * @param moveTimer the move timer to set
     */
    public void setMoveTimer(Timer moveTimer) {
        this.moveTimer = moveTimer;
    }

    /**
     * Sets the completion status of the animal.
     *
     * @param done the completion status to set
     */
    public void setDone(int done) {
        this.done = done;
    }

    /**
     * Returns whether the animal has been removed from the competition.
     *
     * @return {@code true} if removed, {@code false} otherwise.
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Sets the availability status of the animal.
     * @param available true if the animal is available, false otherwise
     */
    public void setIsAvailable(boolean available) {
        isAvailable = available;
    }

    /**
     * Sets the ZooPanel associated with the animal.
     * @param zooPanel the ZooPanel to be set
     */
    public void setZooPanel(ZooPanel zooPanel) {
        this.zooPanel = zooPanel;
    }

//    public void setLocation(int width, int height){
//
//
//        double locx = (double) getLocationX()/width;
//        double locy = (double) getLocationY()/height;
//        int newlocX = (int) (locx * getZooPanel().getWidth());
//        int newlocY = (int) (locy * getZooPanel().getHeight());
//
//        super.setLocation(new Point(newlocX, newlocY));
//
//        double desx = (double) getLocationX()/width;
//        double desy = (double) getLocationY()/height;
//        int newdesX = (int) (desx * getZooPanel().getWidth());
//        int newdesY = (int) (desy * getZooPanel().getHeight());
//
//        this.setDestination(new Point(newdesX, newdesY));
//
//    }

    /**
     * Gets the distance the animal has traveled.
     * @return the distance traveled
     */
    public double getDistance(){
        return 0;
    }

    /**
     * Gets the Timer associated with the animal.
     * @return the Timer object
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Abstract method to set the initial location of the animal.
     */
    public abstract void setInitialLocation();

    /**
     * Gets the CompetitionPanel associated with the animal.
     * @return the CompetitionPanel object
     */
    public CompetitionPanel getCompetitionPanel() {
        return panel;
    }

    /**
     * Sets the CompetitionPanel associated with the animal.
     * @param panel the CompetitionPanel to be set
     */
    public void setCompetitionPanel(CompetitionPanel panel){
        this.panel = panel;
    }

    /**
     * Gets the destination point of the animal.
     * @return the destination point
     */
    public Point getDestination() {
        return destination;
    }

    /**
     * Abstract method to set the destination of the animal.
     */
    public abstract void setDestination();

    /**
     * Sets the destination point of the animal.
     * @param destination the destination point to be set
     */
    public void setDestination(Point destination) {
        this.destination = destination;
    }

    /**
     * Gets the competition route of the animal.
     * @return the competition route
     */
    public int getCompetitionRoute(){
        return 0;
    }

    /**
     * Abstract method to get the length of the route for the animal.
     * @return the length of the route
     */
    public abstract int getLenOfRoute();

    /**
     * Gets the initial X coordinate of the animal.
     * @return the initial X coordinate
     */
    public int getXinit(){
        return 0;
    }

    /**
     * Converts an animal category to a corresponding number.
     * @param animalCategory the category of the animal
     * @return the corresponding number
     */
    public int getAnimalAsNumber(String animalCategory) {
        switch (animalCategory) {
            case "Water":
                return 1;
            case "Air":
                return 2;
            case "Terrestrial":
                return 3;
            case "Terrestrial+Water":
                return 1;
        }
        return 0;
    }

    /**
     * Checks if the animal needs to move.
     * @return true if the animal needs to move, false otherwise
     */
    public boolean isNeedToMove() {
        return needToMove;
    }

    /**
     * Sets the need to move status of the animal.
     * @param needToMove true if the animal needs to move, false otherwise
     */
    public void setNeedToMove(boolean needToMove) {
        this.needToMove = needToMove;
    }

    /**
     * Starts moving the animal along a predefined rectangular path.
     */
    public void startMoveTerrestrial() {

        this.setMoveTimer(new Timer(1000 / 60, e -> updateSide()));
        this.getMoveTimer().start();

        if (this.isDone() < 4)
            if (this.getMoveTimer() != null)
                this.startMoving();

    }

    /**
     * Updates the direction of the animal and handles movement along the rectangular path.
     */
    private void updateSide() {

        if (this.getLocation().equals(new Point(this.getZooPanel().getWidth() - 65,0))) {
            this.setOrientation(Orientation.SOUTH);
            this.startMoving();
            this.setDone(1);


        } else if (this.getLocation().equals(new Point(this.getZooPanel().getWidth() - 65, this.getZooPanel().getHeight() - 65))) {
            this.setOrientation(Orientation.WEST);
            this.startMoving();
            this.setDone(2);
        }
        else if (this.getLocation().equals(new Point(0, this.getZooPanel().getHeight() - 65))) {

            this.setOrientation(Orientation.NORTH);
//            setNeededDistance(neededDistance+participant.calcDistancePoint(new Point(0, 0)));

            this.startMoving();
            this.setDone(3);

        } else if (this.getLocation().equals(new Point(0, 0))) {
            this.setOrientation(Orientation.EAST);
            if (this.isDone() > 0) {
                if (this.getMoveTimer() != null) {
                    this.getMoveTimer().stop();
                    this.setMoveTimer(null);
                    this.setDone(0);
                }

            }

        }

    }

    /**
     * Starts moving the animal towards a specified destination.
     * @param destination the point to move towards
     */
    public void startMoving(Point destination) {
        timer = new Timer(100 / 60, e -> moveToward(destination));
        timer.start();
    }

    /**
     * Moves the animal towards a specified destination.
     * @param destination the point to move towards
     */
    protected void moveToward(Point destination) {
        double frameSpeed = speed / 60;
        if (frameSpeed < 1)
            frameSpeed = 1;
        // && timer != null && moveTimer != null
        if (currentEnergy <= 0 ) {
            if (timer != null) {
                timer.stop();
//                timer = null;
            }
            if(moveTimer != null)
                moveTimer.stop();
            currentEnergy = 0;
            setNeedToMove(true);
            return;
        }
        if (orientation == Orientation.EAST || orientation == Orientation.WEST) {
            if (Math.abs(getLocationX() - destination.getX()) <= frameSpeed) {
                move(destination);
                if (timer != null) {
                    timer.stop();
//                    timer = null;
                }


            } else {
                if (destination.getX() > getLocationX())
                    move(new Point((int) (getLocationX() + frameSpeed), getLocationY()));
                else
                    move(new Point((int) (getLocationX() - frameSpeed), getLocationY()));
            }

        }
        else if (orientation == Orientation.SOUTH || orientation == Orientation.NORTH) {

            if (Math.abs(getLocationY() - destination.getY()) <= frameSpeed) {
                move(destination);
                if (timer != null) {
                    timer.stop();
//                    timer = null;
                }


            } else {
                if (destination.getY() > getLocationY())
                    move(new Point(getLocationX(), (int)(getLocationY() + frameSpeed)));
                else
                    move(new Point(getLocationX(), (int)(getLocationY() - frameSpeed)));
            }

        }

        currentEnergy -= frameSpeed*energyPerMeter;


    }

}