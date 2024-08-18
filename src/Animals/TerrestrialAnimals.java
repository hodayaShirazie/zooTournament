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


    /**
     * Constructs a new TerrestrialAnimal with default number of legs (4) and no panel.
     *
     * @param name            the name of the terrestrial animal
     * @param speed           the speed of the terrestrial animal
     * @param energyPerMeter  the energy consumed by the terrestrial animal per meter of movement
     * @param maxEnergy       the maximum energy the terrestrial animal can have
     */
    public TerrestrialAnimals(String name, int speed, int energyPerMeter, int maxEnergy) {
        super(name, speed, energyPerMeter, maxEnergy);
        setInitialLocation();
        canWalk = new CanWalk(4);

        this.img2 = null;
        this.img3 = null;
        this.img4 = null;
    }

    /**
     * Constructs a new TerrestrialAnimal with specified attributes.
     *
     * @param name            the name of the terrestrial animal
     * @param gender          the gender of the terrestrial animal
     * @param weight          the weight of the terrestrial animal; defaults to 10 if non-positive
     * @param speed           the speed of the terrestrial animal; defaults to 10 if non-positive
     * @param medals          an array of medals awarded to the terrestrial animal; duplicates are removed
     * @param loc             the initial location of the terrestrial animal
     * @param size            the size of the terrestrial animal
     * @param id              the unique identifier for the terrestrial animal
     * @param orientation     the orientation of the terrestrial animal
     * @param maxEnergy       the maximum energy the terrestrial animal can have
     * @param energyPerMeter  the energy consumed by the terrestrial animal per meter of movement
     * @param pan             the competition panel associated with the terrestrial animal
     * @param noLegs          the number of legs of the terrestrial animal; defaults to 4 if non-positive
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
     * Constructs a new TerrestrialAnimal with specified attributes and a default number of legs (4).
     *
     * @param name            the name of the terrestrial animal
     * @param speed           the speed of the terrestrial animal
     * @param energyPerMeter  the energy consumed by the terrestrial animal per meter of movement
     * @param maxEnergy       the maximum energy the terrestrial animal can have
     * @param panel           the competition panel associated with the terrestrial animal
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

    /**
     * Sets the initial location of the terrestrial animal on the screen.
     * The initial location is set to the top-left corner (0,0).
     */
    public void setInitialLocation(){
        setLocation(new Point(0, 0));

    }

    /**
     * Sets the destination for the terrestrial animal's movement.
     * The destination is set to the top-left corner (0,0).
     */
    public void setDestination() {
        super.setDestination(new Point(0,0));
    }

    /**
     * Returns the length of the route for the terrestrial animal's movement.
     * This length is based on the perimeter of the panel the animal is moving in.
     *
     * @return the length of the route, adjusted for screen size.
     */
    public int getLenOfRoute(){

        return (getZooPanel().getWidth()-(65))*2 + (getZooPanel().getHeight()- 65)*2;
    }

    /**
     * Starts the movement of the terrestrial animal.
     * The movement is based on the current orientation and the type of the tournament.
     */
    public void startMoving() {

        int type = getCompetitionPanel().getRegularCourierTournament();

        switch (type) {
            case 1:
                if (getOrientation() == Orientation.EAST){
                    setDestination(new Point(getZooPanel().getWidth() - 65, getLocationY()));
                    super.startMoving();
                }
                else if (getOrientation() == Orientation.SOUTH){
                    setDestination(new Point(getLocationX(), getZooPanel().getHeight() - 65));
                    super.startMoving();
                }
                else if (getOrientation() == Orientation.WEST){
                    setDestination(new Point(0, getLocationY()));
                    super.startMoving();
                }
                else if (getOrientation() == Orientation.NORTH){

                    setDestination(new Point(getLocationX(), 0));
                    super.startMoving();
                    setDone(4);
                }
                break;
            case 2:
                if (getOrientation() == Orientation.EAST){
                    if (getDestination().getX() < (getZooPanel().getWidth()-65) && getDestination().getY() == getLocationY()) {
                        super.startMoving(new Point(getDestination().getX(), getLocationY()));
                    }
                    else {
                        super.startMoving(new Point(getZooPanel().getWidth() - 65, getLocationY()));
                    }
                }
                else if (getOrientation() == Orientation.SOUTH){
                    if (getDestination().getY() < getZooPanel().getHeight() - 65 && getDestination().getX() == getLocationX()) {
                        super.startMoving(new Point(getLocationX(), getDestination().getY()));

                    }
                    else {
                        super.startMoving(new Point(getLocationX(), getZooPanel().getHeight() - 65));
                    }
                }
                else if (getOrientation() == Orientation.WEST){
                    if (getDestination().getX() > 0) {
                        super.startMoving(new Point(getDestination().getX(), getLocationY()));
                    }
                    else {
                        super.startMoving(new Point(0, getLocationY()));
                    }
                }
                else if (getOrientation() == Orientation.NORTH){
                    if (getDestination().getY() > 0) {
                        super.startMoving(new Point(getLocationX(), getDestination().getY()));

                    }
                    else {
                        super.startMoving(new Point(getDestination().getX(),0));
                    }
                }

        }

    }


}