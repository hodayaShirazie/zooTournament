package Animals;

import Mobility.Point;
import Olympics.Medal;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import Graphics.ZooPanel;


/*
img 1 =  right down
img2 = down left
img 3 = left down
img4 = up left
 */


/**
 * Represents a terrestrial animal.
 * cannot perform instances from this class.
 */
public abstract class TerrestrialAnimals extends Animal implements ICanWalk {


    /**
     * Interface for walking capability. Used to define how the animal walks.
     */
    private ICanWalk canWalk;

    /**
     * Image for the terrestrial animal moving down.
     */
    private BufferedImage img2;

    /**
     * Image for the terrestrial animal moving left.
     */
    private BufferedImage img3;

    /**
     * Image for the terrestrial animal moving up.
     */
    private BufferedImage img4;


    public TerrestrialAnimals(String name, int speed, int energyPerMeter, int maxEnergy) {
        super(name, speed, energyPerMeter, maxEnergy);
        setInitialLocation();
        canWalk = new CanWalk(4);

        this.img2 = null;
        this.img3 = null;
        this.img4 = null;
    }

    /**
     * Constructs a new TerrestrialAnimals with the specified attributes.
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
     * @param noLegs          the number of legs the animal has; if non-positive, defaults to 4
     */
    public TerrestrialAnimals(String name, Gender gender, double weight, double speed, Medal[] medals, Point loc, int size, int id, Orientation orientation, int maxEnergy, int energyPerMeter, ZooPanel pan, double noLegs) {
        super(name, gender, weight, speed, medals, loc, size, id, orientation, maxEnergy, energyPerMeter, pan);

        setInitialLocation();
        if (noLegs <= 0)
            noLegs = 4;
        canWalk = new CanWalk(noLegs);

        this.img2 = null;
        this.img3 = null;
        this.img4 = null;
    }

    /**
     * Constructs a new TerrestrialAnimals with default values for certain attributes.
     *
     * @param name            the name of the animal
     * @param speed           the speed of the animal; if non-positive, defaults to 10
     * @param energyPerMeter  the energy consumed by the animal per meter of movement
     * @param maxEnergy       the maximum energy the animal can have
     * @param panel           the competition panel associated with the animal
     */
    public TerrestrialAnimals(String name, int speed, int energyPerMeter, int maxEnergy, ZooPanel panel) {
        super(name, speed, energyPerMeter, maxEnergy, panel);
        setInitialLocation();

        canWalk = new CanWalk(4);

        this.img2 = null;
        this.img3 = null;
        this.img4 = null;
    }

    /**
     * Construct a new TerrestrialAnimals with default values.
     */
    public TerrestrialAnimals() {
        super();
        setInitialLocation();
        canWalk = new CanWalk(4);

        this.img2 = null;
        this.img3 = null;
        this.img4 = null;
    }

    /**
     * @return a string which describes the TerrestrialAnimals object, including its name, gender, weight, speed, medals, position, total distance, number of legs.
     * the function calls Animal toString function.
     */
    public String toString() {
        return super.toString() + canWalk.toString();
    }

    /**
     * Check if terrestrialAnimals is equal to origin.
     *
     * @param obj the object to compare with.
     * @return true if terrestrialAnimals is equal to origin and false otherwise.
     */
    public boolean equals(Object obj) {
        if (obj instanceof TerrestrialAnimals) {
            return (super.equals(obj) && canWalk.equals(((TerrestrialAnimals) obj).canWalk));
        }
        return false;
    }

    /**
     * Loads the images for the terrestrial animal based on the given image name.
     * The images represent different directions the animal can face:
     * <ul>
     *   <li>img1 - Right down</li>
     *   <li>img2 - Down left</li>
     *   <li>img3 - Left up</li>
     *   <li>img4 - Up right</li>
     * </ul>
     *
     * @param imageName the base name of the images to be loaded
     */
    public void loadImages(String imageName) {

        try {
            super.loadImages(imageName);
            img2 = ImageIO.read(new File(PICTURE_PATH + imageName + "_down_left.png"));
            img3 = ImageIO.read(new File(PICTURE_PATH + imageName + "_left_up.png"));
            img4 = ImageIO.read(new File(PICTURE_PATH + imageName + "_up_right.png"));

        } catch (IOException e) {
            System.out.println("Cannot load images for " + imageName);
        }
    }

    /**
     * Draws the animal on the given graphics context based on its orientation.
     * The appropriate image is selected and drawn according to the current orientation of the animal.
     *
     * @param g the graphics context to draw on
     */
    public void drawObject(Graphics g) {
        if (getOrientation() == Orientation.EAST)
            super.drawObject(g);

        else if (getOrientation() == Orientation.SOUTH) {// animal move to the east side
            g.drawImage(img2, super.getLocationX(), super.getLocationY(), getSize(), getSize(), getZooPanel());
        }

        else if (getOrientation() == Orientation.WEST) {// animal move to the east side
            g.drawImage(img3, super.getLocationX(), super.getLocationY(), getSize(), getSize(), getZooPanel());
        }

        else if (getOrientation() == Orientation.NORTH) {// animal move to the east side
            g.drawImage(img4, super.getLocationX(), super.getLocationY(), getSize(), getSize(), getZooPanel());
        }


    }

    /**
     * Starts moving the terrestrial animal based on its current orientation.
     * The animal moves towards specific points on the screen depending on its orientation:
     * <ul>
     *   <li>East - Moves towards (900, 0)</li>
     *   <li>South - Moves towards (900, 450)</li>
     *   <li>West - Moves towards (0, 450)</li>
     *   <li>North - Moves towards (0, 0)</li>
     * </ul>
     */
    public void startMoving() {

//        startMoving(new Point(getZooPanel().getWidth() - 65, getLocationY()));
//        System.out.println("go to " + new Point(getPanel().getWidth() - 65, getLocationY()).toString() + " and the distance will be " + calcDistancePoint(new Point(getPanel().getWidth() - 65, getLocationY())));

        if (getOrientation() == Orientation.EAST){
            System.out.println("set destianation in East---------");
            setDestination(new Point(getZooPanel().getWidth() - 65, getLocationY()));
            System.out.println("get destianation : " + getDestination());
            super.startMoving();
        }
        else if (getOrientation() == Orientation.SOUTH){
            System.out.println("set destianation in South---------");
            setDestination(new Point(getLocationX(), getZooPanel().getHeight() - 65));
            System.out.println("get destianation : " + getDestination());
            super.startMoving();
        }
        else if (getOrientation() == Orientation.WEST){
            System.out.println("set destianation in west---------");
            setDestination(new Point(0, getLocationY()));
            System.out.println("get destianation : " + getDestination());

            super.startMoving();
        }
        else if (getOrientation() == Orientation.NORTH){
            System.out.println("set destianation in north---------");

            setDestination(new Point(getLocationX(), 0));
            System.out.println("get destianation : " + getDestination());
            super.startMoving();
            setDone(4);

        }
    }


    /**
     * Sets the number of legs of the animal to the default value using the {@link ICanWalk#setNoLegsToDefault()} method.
     *
     * @return true if the number of legs was set to default; false otherwise
     */
    public boolean setNoLegsToDefault() {
        return canWalk.setNoLegsToDefault();
    }

    /**
     * Returns the category of the animal, which is "Terrestrial" for this class.
     *
     * @return the category of the animal
     */
    public String getCategory() {
        return "Terrestrial";
    }

    public void setLocation(int width, int height){

//        System.out.println("old width: " + width);
//        System.out.println("New width: " + getPanel().getWidth());
//
//        System.out.println("current x" + getLocationX());

        double x = (double) getLocationX()/width;

//        System.out.println("x/width:  " + x);

        int newX = (int) (x * getZooPanel().getWidth());

//        System.out.println("new x" + newX);

        super.setLocation(new Point(newX, 0));

    }

    public double getDistance() {

        return (921*2 + 445*2);
//        System.out.println("distance of animal is  " + super.calcDistancePoint((new Point(getZooPanel().getWidth() - 65, getLocationY()))));
//        return super.calcDistancePoint((new Point(getZooPanel().getWidth() - 65, getLocationY())));
    }

    public void setInitialLocation(){
        setLocation(new Point(0, 0));

    }

    public void setDestination() {
        System.out.println("setDestination in teress is called --------------- " + new Point(getZooPanel().getWidth() - 65, getLocationY()));
        super.setDestination(new Point(getZooPanel().getWidth() - 65, getLocationY()));
    }

    public int getLenOfRoute(){//Todo match to every size os screen and take all route
        return (921*2 + 445*2);
    }

}