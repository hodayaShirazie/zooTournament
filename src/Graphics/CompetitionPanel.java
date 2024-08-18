package Graphics;

import Animals.*;
import Competitions.CourierTournament;
import Competitions.RegularTournament;
import Competitions.Tournament;
import javax.swing.*;
import javax.swing.JPanel;
import java.awt.*;
import java.util.Enumeration;

/**
 * Represents the main competition panel, managing competition type, animals, and table data.
 */
public class CompetitionPanel extends JPanel {

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
     * The current tournament being managed.
     */
    private Tournament tournament;

    /**
     * The number assigned to the group.
     */
    private int groupNumber;

    /**
     * Array storing the number of animals in each group.
     */
    private int[] currentAnimalsInGroups;

    /**
     * Array representing the routes assigned to each group.
     */
    private int[] groupRoutes;


    /**
     * Initializes the CompetitionPanel by setting up arrays for animal counts and group routes,
     * and configuring initial panel settings. Also sets default values for group number,
     * competition type, and tournament type.
     */
    public CompetitionPanel() {

        //initialize count for animals in group
        currentAnimalsInGroups = new int[5];
        for (int i=0; i<5; ++i)
            currentAnimalsInGroups[i] = 0;

        //initialize groupRoutes for animals in group
        groupRoutes = new int[5];
        for (int i=0; i<5; ++i)
            groupRoutes[i] = 0;

        participates = null;
        groupNumber = 0;
        competitionType = 0;
        regularCourierTournament = 0;
    }

    /**
     * Paints the component by rendering the background image and the participating animals.
     * This method is called whenever the panel needs to be redrawn.
     *
     * @param g The <code>Graphics</code> object used to draw the background image and animals.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (participates != null) {
            for (Animal [] animals : participates) {
                for (Animal animal : animals) {
                    if (animal != null) {
                        animal.drawObject(g);
                    }
                }
            }
        }
    }

    /**
     * Validates the group number based on the current competition type.
     * @return true if the group number is valid for the competition type; false otherwise.
     */
    public boolean isGroupNumberValid() {
        switch (competitionType) {
            case 1: //Water
                if(groupNumber > 5)
                    return false;
                break;
            case 2: //Air
                if(groupNumber > 4)
                    return false;
                break;
            case 3: //Terrestrial
                if(groupNumber > 3)
                    return false;
                break;
        }
        return true;
    }

    /**
     * Increases the group number and expands the participants array to accommodate the new group.
     * Initializes the new group with a null entry and triggers a repaint of the panel.
     */
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

    /**
     * Updates the competition type based on the selected button in the provided ButtonGroup.
     * @param competitionGroup The ButtonGroup containing competition type options.
     * Shows an error message if no competition type is selected.
     */
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
            this.regularCourierTournament = selectedCompetitionType;
        }
    }

    /**
     * Updates the animal type based on the selected button in the provided ButtonGroup.
     * @param animalGroup The ButtonGroup containing animal type options.
     * Shows an error message if no animal type is selected.
     */
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
                    case "Terrestrial+Water":
                        selectedAnimalType = 1;
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

    /**
     * Adds an animal to a specified group.
     * @param animal The Animal object to be added.
     * @param groupNumber The group number to which the animal should be added (1-based index).
     */
    public void addAnimalToGroup(Animal animal, int groupNumber){

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

    /**
     * Creates a tournament based on the current competition type.
     * If the competition type is "Courier", a `CourierTournament` is created.
     * Otherwise, a `RegularTournament` is created.
     */
    public void createTournament(){
        if (regularCourierTournament == 2)
            tournament = new CourierTournament(participates);
        else
            tournament = new RegularTournament(participates);
    }

    /**
     * Returns the current competition type.
     * @return An integer representing the competition type:
     *         1 for Regular, 2 for Courier.
     */
    public int getRegularCourierTournament() {
        return regularCourierTournament;
    }

    /**
     * Retrieves the current tournament instance.
     * @return The Tournament object representing the ongoing tournament.
     */
    public Tournament getTournament() {
        return tournament;
    }

    /**
     * Retrieves the array of participating animals grouped by their respective groups.
     * @return A 2D array of Animal objects representing the animals in each group.
     */
    public Animal[][] getParticipates() {
        return participates;
    }

    /**
     * Retrieves the routes assigned to each group.
     * @return An array of integers where each element represents the route of a group.
     */
    public int[] getGroupRoutes() {
        return groupRoutes;
    }

    /**
     * Retrieves the number of animals currently in each group.
     * @return An array of integers where each element represents the count of animals in a group.
     */
    public int[] getCurrentAnimalsInGroups() {
        return currentAnimalsInGroups;
    }

    /**
     * Returns the type of competition.
     *
     * @return the competition type as an integer.
     */
    public int getCompetitionType() {
        return competitionType;
    }

    /**
     * Returns the current group number.
     * @return The group number.
     */
    public int getGroupNumber() {
        return groupNumber;
    }
}
















