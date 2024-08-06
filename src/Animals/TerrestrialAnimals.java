package Animals;

import Graphics.CompetitionPanel;
import Mobility.Point;
import Olympics.Medal;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
    public TerrestrialAnimals(String name, Gender gender, double weight, double speed, Medal[] medals, Point loc, int size, int id, Orientation orientation, int maxEnergy, int energyPerMeter, CompetitionPanel pan, double noLegs) {
        super(name, gender, weight, speed, medals, loc, size, id, orientation, maxEnergy, energyPerMeter, pan);

        setLocation(new Point(0, 0));
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
    public TerrestrialAnimals(String name, int speed, int energyPerMeter, int maxEnergy, CompetitionPanel panel) {
        super(name, speed, energyPerMeter, maxEnergy, panel);
        setLocation(new Point(0, 0));
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
        setLocation(new Point(0, 20));
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
            g.drawImage(img2, super.getLocationX(), super.getLocationY(), getSize(), getSize(), getPanel());
        }

        else if (getOrientation() == Orientation.WEST) {// animal move to the east side
            g.drawImage(img3, super.getLocationX(), super.getLocationY(), getSize(), getSize(), getPanel());
        }

        else if (getOrientation() == Orientation.NORTH) {// animal move to the east side
            g.drawImage(img4, super.getLocationX(), super.getLocationY(), getSize(), getSize(), getPanel());
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
    public void startMoving(){
        if (getOrientation() == Orientation.EAST)
            startMoving(new Point(900,0));
        else if (getOrientation() == Orientation.SOUTH)
            startMoving(new Point(900,450));
        else if (getOrientation() == Orientation.WEST)
            startMoving(new Point(0,450));
        else if (getOrientation() == Orientation.NORTH)
            startMoving(new Point(0,0));
//            setDone(4);
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
}
