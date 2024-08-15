

package Graphics;

import Animals.*;
import Competitions.CourierTournament;
import Competitions.RegularTournament;
import Competitions.Scores;
import Competitions.Tournament;

import javax.swing.*;
import javax.swing.JPanel;
import java.awt.*;
import java.util.Enumeration;


/**
 * The CompetitionPanel class represents the main panel where the competition takes place.
 * It manages the type of competition, participating animals, and the competition table data.
 */
public class CompetitionPanel extends JPanel {

    private int groupNumber;

    /**
     * Type of competition.
     * 1 - water, 2 - air, 3 - terrestrial.
     */
    private int competitionType;

    /**
     * Type options.
     * 1 - regular, 2 - courier.
     */
    private int regularCourierTournament;

    /**
     * Array of animals participating in the competition.
     */
    private Animal[][]participates;

    /**
     * Background image for the competition panel.
     */

    private Tournament tournament;

    /**
     * Constructs the CompetitionPanel, initializing the participants array,
     * starting a timer to repaint the panel, and loading the background image.
     */
    public CompetitionPanel() {
        participates = null;
        groupNumber = 0;
        competitionType = 0;
        regularCourierTournament = 0;
//        Timer timer = new Timer(1000 / 60, e -> repaint());
//        timer.start();
    }

    /**
     * Loads an image from the specified path and sets it as the background image.
     *
     * @param path The path to the image file.
     */

    /**
     * Paints the component by rendering the background image and the participating animals.
     * This method is called whenever the panel needs to be redrawn.
     *
     * @param g The <code>Graphics</code> object used to draw the background image and animals.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        System.out.println("im in panellllllll");

        if (participates != null) {
            for (Animal [] animals : participates) {
                for (Animal animal : animals) {
                    if (animal != null) {
                        animal.drawObject(g);
                        System.out.println(animal.toString());
                    }
                }
            }
        }
    }

    /**
     * Returns the type of competition.
     *
     * @return the competition type as an integer.
     */
    public int getCompetitionType() {
        return competitionType;
    }

    public boolean isGroupNumberValid() {
        switch (competitionType) {
            case 1:
                if(groupNumber > 5)
                    return false;
                break;
            case 2:
                if(groupNumber > 4)
                    return false;
                break;
            case 3:
                if(groupNumber > 3)
                    return false;
                break;
        }
        return true;
    }

    public void increaseGroupNumber() {
        groupNumber++;

        if(participates == null) {
            participates = new Animal[1][];
            participates[0] = null;
        }
        else {
            int len = participates.length;
            Animal[][] tmpAnimal = new Animal[len + 1][];
            for (int i = 0; i < len; ++i) {
                tmpAnimal[i] = participates[i];
            }
            tmpAnimal[len] = null;
            participates = tmpAnimal;
        }

        repaint();

    }

    public int getGroupNumber() {
        return groupNumber;
    }

    // Helper method to get selected radio button text
//    public void updateCompetitionType(ButtonGroup buttonGroup) {
//        for (java.util.Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
//            AbstractButton button = buttons.nextElement();
//            if (button.isSelected()) {
//                switch (button.getText()) {
//                    case "Water":
//                        this.competitionType = 1;
//                        break;
//                    case "Air":
//                        this.competitionType = 2;
//                        break;
//                    case "Terrestrial":
//                        this.competitionType = 3;
//                        break;
//                }
//                System.out.println("selected: " + button.getText());
////                return button.getText();
//            }
//        }
////        return null; // No button selected
//    }

    public void updateCourierTournament(ButtonGroup competitionGroup) {
        // Get the selected competition type
        Enumeration<AbstractButton> competitionButtons = competitionGroup.getElements();
        int selectedCompetitionType = -1; // Default to an invalid type

        while (competitionButtons.hasMoreElements()) {
            AbstractButton button = competitionButtons.nextElement();
            if (button.isSelected()) {
                switch (button.getText()) {
                    case "Courier":
                        selectedCompetitionType = 2; // Courier competition
                        break;
                    case "Regular":
                        selectedCompetitionType = 1; // Regular competition
                        break;
                }
                break;
            }
        }

        if (selectedCompetitionType == -1) {
            // Handle the case where no competition type is selected
            JOptionPane.showMessageDialog(null, "Please select a competition type.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Update the competition panel with the selected competition type
            this.regularCourierTournament = selectedCompetitionType; // Assuming `regularCourierTournament` is a field in `CompetitionPanel`
        }
    }

    public void updateCompetitionType(ButtonGroup animalGroup) {
        // Get the selected animal type
        Enumeration<AbstractButton> animalButtons = animalGroup.getElements();
        int selectedAnimalType = -1; // Default to an invalid type

        while (animalButtons.hasMoreElements()) {
            AbstractButton button = animalButtons.nextElement();
            if (button.isSelected()) {
                switch (button.getText()) {
                    case "Air":
                        selectedAnimalType = 2; // Air
                        break;
                    case "Water":
                        selectedAnimalType = 1; // Water
                        break;
                    case "Terrestrial":
                        selectedAnimalType = 3; // Terrestrial
                        break;
                }
                break;
            }
        }

        if (selectedAnimalType == -1) {
            // Handle the case where no animal type is selected
            JOptionPane.showMessageDialog(null, "Please select an animal type.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Update the competition panel with the selected animal type
            this.competitionType = selectedAnimalType; // Assuming `animalType` is a field in `CompetitionPanel`
        }
    }

    public void addAnimalToGroup(Animal animal, int groupNumber){
//        animal.setIsAvailable(false);

        if(participates[groupNumber-1] == null) {
            participates[groupNumber - 1] = new Animal[1];
            participates[groupNumber - 1][0] = animal;
        }
        else {
            int len = participates[groupNumber - 1].length;
            Animal[] tmpParticipate = new Animal[len + 1];
            for (int i = 0; i < len; ++i)
                tmpParticipate[i] = participates[groupNumber - 1][i];
            tmpParticipate[len] = animal;
            participates[groupNumber - 1] = tmpParticipate;

        }
        animal.setCompetitionPanel(this);
        repaint();
    }

    public void createTournament(){
        if (regularCourierTournament == 2)
            tournament = new CourierTournament(participates);
        else
            tournament = new RegularTournament(participates);



    }

    public int getRegularCourierTournament() {
        return regularCourierTournament;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public Animal[][] getParticipates() {
        return participates;
    }

//    public int getRegularCourierTournament() {
//        return regularCourierTournament;
//    }

//
//    /**
//     * Starts the movement of the last participant in the competition.
//     * If the type of competition is not terrestrial, the participant starts moving directly.
//     * If the type of competition is terrestrial, a timer is set to update the movement of the participant
//     * Along a rectangular path.
//     */
//    private void startMove() {
//
//        int index = participates.length - 1;
//        while (participates[index].isRemovedFromCompetition() && index > 0)
//            --index;
//        if (participates[0].isRemovedFromCompetition() && index == 0)
//            return;
//
//        if (getCompetitionType() != 3) {
//            participates[index].startMoving();
//        } else {
//            participates[index].setMoveTimer(new Timer(1000 / 60, e -> updateSide()));
//            participates[index].getMoveTimer().start();
//
//            if (participates[index].isDone() < 4)
//                if (participates[index].getMoveTimer() != null)
//                    participates[index].startMoving();
//        }
//
//    }
//
//    /**
//     * Updates the orientation and position of the latest participant based on their current location.
//     * The participant moves along a rectangular path and updates its orientation at each corner.
//     * Stops the movement when the participant returns to the starting point.
//     */
//    private void updateSide() {
//
//
//        int index = participates.length - 1;
//        while (participates[index].isRemovedFromCompetition() && index > 0)
//            --index;
//        if (participates[0].isRemovedFromCompetition() && index == 0)
//            return;
//
//        if (participates[index].getLocation().equals(new Point(900, 0))) {
//            participates[index].setOrientation(Orientation.SOUTH);
//            participates[index].startMoving();
//            participates[index].setDone(1);
//
//        } else if (participates[index].getLocation().equals(new Point(900, 450))) {
//            participates[index].setOrientation(Orientation.WEST);
//            participates[index].startMoving();
//            participates[index].setDone(2);
//
//        } else if (participates[index].getLocation().equals(new Point(0, 450))) {
//            participates[index].setOrientation(Orientation.NORTH);
//            participates[index].startMoving();
//            participates[index].setDone(3);
//
//        } else if (participates[index].getLocation().equals(new Point(0, 0))) {
//            participates[index].setOrientation(Orientation.EAST);
//
//            if (participates[index].isDone() > 0) {
//                if (participates[index].getMoveTimer() != null) {
//                    participates[index].getMoveTimer().stop();
//                    participates[index].setMoveTimer(null);
//                    participates[index].setDone(4);
//                }
//
//            }
//
//        }
//
//    }

}
















