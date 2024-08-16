//Todo ensure competitions input is valid
//Todo fix clear
package Graphics;

import Animals.Animal;
import Mobility.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Submitted by:
 * (1) Shulamit Mor Yossef  Id:206576977
 * (2) Hodaya Shirazie Id: 213907785
 */

/**
 * The CompetitionFrame class represents the main window of the application.
 * It sets up the GUI components including the menu bar and control buttons.
 */
public class CompetitionFrame extends JFrame {

    /**
     * The panel for managing and displaying the competition.
     */
    private ZooPanel zooPanel;

    /**
     * The width of the frame.
     * This static field is used to keep track of the current width of the frame for layout adjustments.
     */
    private static int frameWidth;

    /**
     * The height of the frame.
     * This static field is used to keep track of the current height of the frame for layout adjustments.
     */
    private static int frameHeight;

    /**
     * Constructs the CompetitionFrame, initializing the GUI components and setting up event handlers.
     * The frame includes a menu bar with options for file operations and help, as well as a button panel
     * for various actions related to competitions and animals.
     * <p>
     * The frame setup includes:
     * - A menu bar with "File" and "Help" menus.
     *   - "File" menu: Contains an "Exit" menu item that closes the application.
     *   - "Help" menu: Contains a "Help" menu item that shows a help message.
     * - A panel with buttons for different actions:
     *   - "Add Competition" button: Opens a dialog to add a competition.
     *   - "Add Animal" button: Adds a new animal to the zoo panel.
     *   - "Sleep" button: Sets animals to sleep mode.
     *   - "Scores" button: Displays the current competition scores.
     *   - "Clear" button: Clears the list of animals.
     *   - "Eat" button: Makes all animals eat.
     *   - "Info" button: Shows information about all animals.
     *   - "Exit" button: Closes the application.
     */
    public CompetitionFrame() {

        setTitle("Competitions");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            /**
             * Handles the window closing event by freeing memory and exiting the application.
             * This method is invoked when the user attempts to close the window.
             *
             * @param e the window event
             */
            @Override
            public void windowClosing(WindowEvent e) {
                freeMemory();
                System.exit(0);
            }
        });


        setSize(1000, 600);
        setLocationRelativeTo(null);

        frameHeight = getHeight();
        frameWidth = getWidth();

        zooPanel = new ZooPanel();
        zooPanel.setLayout(null);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {


                //size of frame was changed
                Dimension newSize = getSize();
                zooPanel.updateLocation(frameWidth, frameHeight);

                frameWidth = newSize.width;
                frameHeight = newSize.height;

            }
        });


        //TOdo test if can be replaced with above
//        addComponentListener(new ComponentAdapter() {
//            /**
//             * Handles component resizing by updating the location of the zoo panel.
//             * This method is invoked when the frame is resized.
//             *
//             * @param e the component event
//             */
//            @Override
//            public void componentResized(ComponentEvent e) {
//                Dimension newSize = getSize();
//                zooPanel.updateLocation(frameWidth, frameHeight);
//
//                frameWidth = newSize.width;
//                frameHeight = newSize.height;
//            }
//        });


        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu help = new JMenu("Help");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> {
            freeMemory();
            System.exit(0);
        });

        JMenuItem helpItem = new JMenuItem("Help");
        helpItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Home Work 2\nGUI"));

        help.add(helpItem);
        file.add(exitItem);
        menuBar.add(file);
        menuBar.add(help);
        setJMenuBar(menuBar);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 6));
        buttonsPanel.setPreferredSize(new Dimension(getWidth(), 30));

        // AddCompetition Button
        JButton addCompetitionButton = new JButton("Add Competition");
        addCompetitionButton.addActionListener(e -> addCompetition());
        buttonsPanel.add(addCompetitionButton);

        // AddAnimal Button
        JButton addAnimalButton = new JButton("Add Animal");
        addAnimalButton.addActionListener(e -> zooPanel.addAnimal());
        buttonsPanel.add(addAnimalButton);

        // SetSleep Button
        JButton setSleepButton = new JButton("Sleep");
        setSleepButton.addActionListener(e -> zooPanel.setSleep());
        buttonsPanel.add(setSleepButton);


        // Scores Button
        JButton scoresButton = new JButton(" Scores");
        scoresButton.addActionListener(e -> zooPanel.showScoresInfo());
        buttonsPanel.add(scoresButton);


        // Clear Button
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clear());
        buttonsPanel.add(clearButton);

        // Eat Button
        JButton eatButton = new JButton("Eat");
        eatButton.addActionListener(e -> zooPanel.eatAnimal());
        buttonsPanel.add(eatButton);

        // Info Button
        JButton infoButton = new JButton("Info");
        infoButton.addActionListener(e -> zooPanel.showAnimalsInfo());
        buttonsPanel.add(infoButton);

        // Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e ->{
            freeMemory();
            System.exit(0);
        });

        buttonsPanel.add(exitButton);

        add(buttonsPanel, BorderLayout.SOUTH);
        add(zooPanel, BorderLayout.CENTER);
    }

    /**
     * Opens a dialog for adding a new competition.
     * <ul>
     *   <li>Checks if there are animals in the zoo; shows a warning if none.</li>
     *   <li>Displays a frame for selecting animal and competition types.</li>
     *   <li>Validates user input and updates competition details if valid.</li>
     *   <li>Shows error messages if inputs are incomplete or there aren't enough animals.</li>
     * </ul>
     */
    public void addCompetition() {

        CompetitionPanel tournament = new CompetitionPanel();

        tournament.setPreferredSize(new Dimension(1000, 600));

        if(zooPanel.getPlayers() == null) {
            JOptionPane.showMessageDialog(this, "No participate yet", "Invalid operation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFrame frame = new JFrame("Add Competition");
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout(10, 10));

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Title/Header
        JLabel titleLabel = new JLabel("Add Competition", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Enlarge title font
        frame.add(titleLabel, BorderLayout.NORTH);

        // Middle Section: Main Content
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the main panel

        // Animal Type Selection
        JPanel animalPanel = new JPanel(new GridLayout(3, 1, 0, 2)); // Reduced vertical gap
        TitledBorder animalBorder = BorderFactory.createTitledBorder("Select Animal Type");
        animalBorder.setTitleFont(new Font("Arial", Font.BOLD, 14)); // Set font size for the border title
        animalPanel.setBorder(animalBorder);
        animalPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JRadioButton airButton = new JRadioButton("Air");
        JRadioButton waterButton = new JRadioButton("Water");
        JRadioButton terrestrialButton = new JRadioButton("Terrestrial");

        ButtonGroup animalGroup = new ButtonGroup();
        animalGroup.add(airButton);
        animalGroup.add(waterButton);
        animalGroup.add(terrestrialButton);

        animalPanel.add(airButton);
        animalPanel.add(waterButton);
        animalPanel.add(terrestrialButton);

        mainPanel.add(animalPanel);

        // Add minimal vertical space between the groups
        mainPanel.add(Box.createVerticalStrut(20)); // Increased spacing to push the competition section down

        // Competition Type Selection
        JPanel competitionPanel = new JPanel(new GridLayout(2, 1, 0, 2)); // Reduced vertical gap
        TitledBorder competitionBorder = BorderFactory.createTitledBorder("Select Competition Type");
        competitionBorder.setTitleFont(new Font("Arial", Font.BOLD, 14)); // Set font size for the border title
        competitionPanel.setBorder(competitionBorder);
        competitionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JRadioButton courierButton = new JRadioButton("Courier");
        JRadioButton regularButton = new JRadioButton("Regular");

        ButtonGroup competitionGroup = new ButtonGroup();
        competitionGroup.add(courierButton);
        competitionGroup.add(regularButton);

        competitionPanel.add(courierButton);
        competitionPanel.add(regularButton);

        mainPanel.add(competitionPanel);

        frame.add(mainPanel, BorderLayout.CENTER);

        // Bottom Section: Action Buttons
        JPanel buttonPanel = new JPanel();

        JButton confirmButton = new JButton("Confirm");
        JButton cancelButton = new JButton("Cancel");
        frame.getRootPane().setDefaultButton(confirmButton);

        confirmButton.addActionListener(e -> {
            if (animalGroup.getSelection() == null || competitionGroup.getSelection() == null) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields before submitting.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            frame.dispose();
            tournament.updateCompetitionType(animalGroup);
            tournament.updateCourierTournament(competitionGroup);
            if(!isCompetitionValid(tournament)){
                JOptionPane.showMessageDialog(frame, "There are not enough animals to start a new competition.\n please add animals and try again ", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            createAndShowGUI(tournament);
        });
        cancelButton.addActionListener(e -> {frame.dispose();});

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    /**
     * Checks if the competition can be started based on the number of available animals.
     * Validates whether there are enough animals for the selected competition type.
     *
     * @param tournament The CompetitionPanel object containing the competition type information.
     * @return true if there are enough animals for the competition, false otherwise.
     */
    private boolean isCompetitionValid(CompetitionPanel tournament) {
        int availableAnimals = zooPanel.availableAnimals();
        int regularCourierTournament = tournament.getRegularCourierTournament();
        switch (regularCourierTournament){
            case 1: // Regular competition
                if(availableAnimals < 1)
                    return false;
                break;
            case 2: // Courier competition
                if(availableAnimals < 2)
                    return false;
                break;
        }
        return true;
    }

    /**
     * Calculates the size of the competition panel based on the competition type.
     *
     * @param competitionType The type of competition (1 for Water, 2 for Air, 3 for Terrestrial).
     * @return A Point object representing the dimensions (width, height) of the panel.
     */
    private Point calculateSize(int competitionType){
        Point point = null;
        switch (competitionType)
        {
            case 1:
                point = new Point(810, 300);
                break;
            case 2:
                point = new Point(653, 300);
                break;
            case 3:
                point = new Point(490, 300);
                break;
        }
        return point;
    }

    /**
     * Creates and displays the competition management GUI.
     *
     * This method sets up a JFrame with components to manage a competition, including:
     * - A panel with "Add Group" and "Start Competition" buttons.
     * - A center panel for displaying groups and animals.
     *
     * @param tournament The CompetitionPanel instance managing the competition.
     */
    public void createAndShowGUI(CompetitionPanel tournament) {
        JFrame frame = new JFrame("Competition Management");

        Point point = calculateSize(tournament.getCompetitionType());
        frame.setSize(point.getX(), point.getY());

        frame.setLayout(new BorderLayout(10, 10));

        // Top: Panel containing both "Add Group" and "Start Competition" buttons
        JPanel topPanel = new JPanel(new BorderLayout());

        // Top right: Button to add group
        JButton addGroupButton = new JButton("Add Group");
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topRightPanel.add(addGroupButton);
        topPanel.add(topRightPanel, BorderLayout.EAST);

        // Top left: Button to start competition
        JButton startCompetitionButton = new JButton("Start Competition");
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.add(startCompetitionButton);
        topPanel.add(topLeftPanel, BorderLayout.WEST);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.getRootPane().setDefaultButton(startCompetitionButton);


        // Center: Panel for groups and animals
        JPanel groupPanel = new JPanel();
        groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.X_AXIS)); // Horizontal layout for columns
        frame.add(groupPanel, BorderLayout.CENTER);

        addGroupButton.addActionListener(e -> {

            int availableAnimals = getAvailableAnimals(tournament);
            int regularCourierTournament = tournament.getRegularCourierTournament();

            // Check if there are enough animals and valid group number
            if (availableAnimals == 0 || (regularCourierTournament == 2 && availableAnimals == 1)){
                JOptionPane.showMessageDialog(frame, "There are not enough animals to add a new group ", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            if (!tournament.isGroupNumberValid()) {
                JOptionPane.showMessageDialog(frame, "Invalid operation. This competition cannot have more than " + (tournament.getGroupNumber()) + " groups", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(addGroupColumn(frame,groupPanel, tournament.getGroupNumber() + 1, tournament))
                tournament.increaseGroupNumber(); // Increase variable to count number of groups
        });

        startCompetitionButton.addActionListener(e -> {

            if(isCompetitionEmpty(tournament)){
                System.out.println("emptyyyyyyy");
                JOptionPane.showMessageDialog(frame, "competition is empty\nplease add groups to competition before starting", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(!validateAnimalCount(tournament.getGroupNumber() + 1,tournament)) {
                JOptionPane.showMessageDialog(frame, "Invalid operation. current group is Invalid", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            zooPanel.addTournament(tournament);
            frame.dispose();

        });

        zooPanel.repaint();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Checks if the last added group meets the animal count requirements based on competition type.
     *
     * @param groupNumber The index of the group to validate.
     * @param tournament  The CompetitionPanel instance.
     * @return true if valid; false otherwise.
     */
    private boolean validateAnimalCount(int groupNumber, CompetitionPanel tournament){
        if(groupNumber == 1)
            return true;
        int regularCourierTournament = tournament.getRegularCourierTournament();

        switch (regularCourierTournament){
            case 1: //Regular Tournament
                if(tournament.getCurrentAnimalsInGroups()[groupNumber - 2] < 1)
                    return false;

                break;
            case 2: //Courier Tournament
                if(tournament.getCurrentAnimalsInGroups()[groupNumber - 2] < 2)
                    return false;

                break;
        }
        return true;

    }

    /**
     * Returns the number of available animals for the specified competition type.
     *
     * @param tournament The CompetitionPanel instance.
     * @return The count of available animals.
     */
    private int getAvailableAnimals(CompetitionPanel tournament){

        int availableAnimals = 0;
        if(tournament == null) {
            availableAnimals = 0;

        }
        else{
            availableAnimals = zooPanel.countAvailableAnimalsFromType(tournament.getCompetitionType());
        }

        return availableAnimals;
    }

    /**
     * Checks if the competition is empty based on the number of groups and participants.
     *
     * @param tournament The CompetitionPanel instance.
     * @return True if the competition has no participants or groups, otherwise false.
     */
    private boolean isCompetitionEmpty(CompetitionPanel tournament){

        if(tournament.getParticipates() == null)
            return true;
        if(tournament.getGroupNumber() == 0)
            return true;
        return false;
    }

    /*

//        if (currentAnimalsInGroups == null)
//            return true;
//        int currentAnimalsInGroupsLen = currentAnimalsInGroups.length;
//        if (currentAnimalsInGroupsLen == 0)
//            return true;
//        boolean isEmpty = true;
//        for (int i=0; i < currentAnimalsInGroupsLen; ++i)
//            if (currentAnimalsInGroups[i] > 0)
//                isEmpty = false;
//        return isEmpty;


     */

    /**
     * Adds a new group column to the competition panel.
     *
     * @param frame The parent JFrame.
     * @param groupPanel The panel to which the group column will be added.
     * @param groupNumber The number of the group to be added.
     * @param tournament The CompetitionPanel instance managing the tournament.
     * @return True if the group column was successfully added, otherwise false.
     */
    public boolean addGroupColumn(JFrame frame,JPanel groupPanel, int groupNumber, CompetitionPanel tournament) {
        if(!validateAnimalCount(groupNumber,tournament)) {
            JOptionPane.showMessageDialog(frame, "Invalid operation. current groups are Invalid", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        String groupName = "Group " + (groupNumber);

        JPanel columnPanel = new JPanel();
        columnPanel.setLayout(new BoxLayout(columnPanel, BoxLayout.Y_AXIS));
        columnPanel.setBorder(BorderFactory.createTitledBorder(groupName));

        // Set a fixed width and allow height to grow as animals are added
        columnPanel.setMaximumSize(new Dimension(150, Integer.MAX_VALUE));

        JComboBox<String> addAnimalComboBox = zooPanel.selectAnimalToAddIfAvailable(tournament.getCompetitionType()); //selecting animal only from animals that do not participate in another competition and have the same competition type

        addAnimalComboBox.setMaximumSize(new Dimension(150, 25));

        columnPanel.add(addAnimalComboBox);

        addAnimalComboBox.addActionListener(e -> {
            if(tournament.getRegularCourierTournament() == 1) {

                if (tournament.getCurrentAnimalsInGroups()[groupNumber - 1] == 1) {
                    JOptionPane.showMessageDialog(frame, "Invalid operation. current groups are Invalid", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            String selectedAnimal = (String) addAnimalComboBox.getSelectedItem();
            if (selectedAnimal != null && !selectedAnimal.equals("Select Animal")) {

                // first animal in the group, allow any animal with the correct competition type
                if (tournament.getCurrentAnimalsInGroups()[groupNumber - 1] == 0) {
                    // No restrictions on route for the first animal
                    tournament.getGroupRoutes()[groupNumber - 1] = zooPanel.findAnimal(selectedAnimal).getCompetitionRoute();
                } else {
                    // Restrict animals to the same route as the first one
                    int route = zooPanel.findAnimal(selectedAnimal).getCompetitionRoute();
                    if (route != tournament.getGroupRoutes()[groupNumber - 1]) {
                        JOptionPane.showMessageDialog(frame, "Animal route must match the first animal in the group.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                tournament.getCurrentAnimalsInGroups()[groupNumber-1]++; //count the new animal that was added
                System.out.println("just added 1 to group " + (groupNumber) );

                setAnimalNotAvailable(selectedAnimal);
                JLabel animalLabel = new JLabel(selectedAnimal);
                columnPanel.add(animalLabel);
                updateAnimalInCompetition(selectedAnimal,groupNumber, tournament);

                // Remove the selected animal from the combo box
                ((DefaultComboBoxModel<String>) addAnimalComboBox.getModel()).removeElement(selectedAnimal);

                // Adjust the height of the column panel to match the content
                columnPanel.revalidate();
                columnPanel.repaint();
            }
        });

        groupPanel.add(columnPanel);
        groupPanel.revalidate();
        groupPanel.repaint();
        return true;
    }

    /**
     * Marks the selected animal as not available.
     *
     * @param selectedAnimal The name of the animal to be marked as unavailable.
     */
    private void setAnimalNotAvailable(String selectedAnimal) {
        Animal animal = zooPanel.findAnimal(selectedAnimal);
        animal.setIsAvailable(false);
    }

    /**
     * Updates the competition with the selected animal for the specified group.
     *
     * @param selectedAnimal The name of the animal to be added to the competition.
     * @param groupNumber    The group number to which the animal is being added.
     * @param tournament     The competition panel to update.
     */
    private void updateAnimalInCompetition(String selectedAnimal,int groupNumber,CompetitionPanel tournament){
        Animal animal = zooPanel.findAnimal(selectedAnimal);
        tournament.addAnimalToGroup(animal, groupNumber);
    }

    /**
     * Frees up memory by clearing resources and setting the zooPanel to null.
     */
    public void freeMemory() {
        clear();
        zooPanel = null;
        System.out.println("i just cleared our memory in exit");
    }

    /**
     * Clears the resources by nullifying references in the zooPanel.
     */
    public void clear(){

        if (zooPanel.getPlayers() != null) {
            for (Animal animal : zooPanel.getPlayers()) {
                animal = null;
            }
            zooPanel.setPlayers(null);
        }

        if (zooPanel.getPanels() != null){
            for (CompetitionPanel panel : zooPanel.getPanels()) {
                panel = null;
            }
            zooPanel.setPanels(null);
        }

    }

    /**
     * The main entry point of the application.
     * Initializes and displays the main window of the application by creating
     * an instance of CompetitionFrame and setting it visible.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            CompetitionFrame frame = new CompetitionFrame();
            frame.setVisible(true);
        });
    }
}