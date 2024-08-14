
//Todo ensure competitions input is valid
//Todo fix clear
package Graphics;

import Animals.Animal;
import Competitions.Scores;
import Competitions.SleepTime;
import Mobility.Point;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;


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
     * The panel where the competition takes place.
     */
    private ZooPanel zooPanel;
    private int[] currentAnimalsInGroups;

    private static int frameWidth, frameHeight;

    /**
     * Constructs the CompetitionFrame, setting up the GUI components.
     *
     * Components include:
     * - JMenuBar with "File" and "Help" menus.
     *   - "File" menu: "Exit" item to close the application.
     *   - "Help" menu: "Help" item to show a help message.
     * - JPanel with buttons:
     *   - "Add Competition" button to add a competition.
     *   - "Add Animal" button to add an animal.
     *   - "Clear" button to clear animals.
     *   - "Eat" button to make animals eat.
     *   - "Info" button to show competition info.
     *   - "Exit" button to close the application.
     */
    public CompetitionFrame() {

        setTitle("Competitions");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        frameHeight = getHeight();
        frameWidth = getWidth();


        zooPanel = new ZooPanel();

        //initialize count for animals in group
        currentAnimalsInGroups = new int[5];
        for (int i=0; i<5; ++i)
            currentAnimalsInGroups[i] = 0;

        zooPanel.setLayout(null);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // התבצע שינוי בגודל המסך
                Dimension newSize = getSize();
                zooPanel.updateLocation(frameWidth, frameHeight);

                frameWidth = newSize.width;
                frameHeight = newSize.height;

            }
        });
        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu help = new JMenu("Help");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
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
        addCompetitionButton.addActionListener(e -> addCompetitionNew());
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
        scoresButton.addActionListener(e -> zooPanel.showScores());
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
        infoButton.addActionListener(e -> zooPanel.showInfo());
        buttonsPanel.add(infoButton);

        // Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        buttonsPanel.add(exitButton);

        add(buttonsPanel, BorderLayout.SOUTH);
        add(zooPanel, BorderLayout.CENTER);
//        add(competitionPanel, BorderLayout.CENTER);
    }

    public void addCompetitionNew() {

        CompetitionPanel tournament = new CompetitionPanel();

        tournament.setPreferredSize(new Dimension(1000, 600));

        System.out.println("tournament width: "+  tournament.getWidth());

        if(zooPanel.getPlayers() == null) {
            JOptionPane.showMessageDialog(this, "No participate yet", "Invalid operation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFrame frame = new JFrame("Add Competition");
        frame.setSize(500, 400); // Frame size adjustment
        frame.setLayout(new BorderLayout(10, 10)); // Padding around the edges

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

    private boolean isCompetitionValid(CompetitionPanel tournament) {
        int availableAnimals = zooPanel.availableAnimals();
        int regularCourierTournament = tournament.getRegularCourierTournament();
        switch (regularCourierTournament){
            case 1:
                if(availableAnimals < 1)
                    return false;
                break;
            case 2:
                if(availableAnimals < 2)
                    return false;
                break;
        }
        return true;
    }

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

    public void createAndShowGUI(CompetitionPanel tournament) {
        JFrame frame = new JFrame("Competition Management");

        Point point = calculateSize(tournament.getCompetitionType());
        frame.setSize(point.getX(), point.getY());

        frame.setLayout(new BorderLayout(10, 10)); // Add some padding between components

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

            int availableAnimals = zooPanel.countAvailableAnimalsFromType(tournament.getCompetitionType());
            int regularCourierTournament = tournament.getRegularCourierTournament();
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

//            if(isCompetitionEmpty()){
//                JOptionPane.showMessageDialog(frame, "competition is empty\nplease add groups to competition before starting", "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }

            if(!validateAnimalCount(tournament.getGroupNumber() + 1,tournament)) {
                JOptionPane.showMessageDialog(frame, "Invalid operation. current groups are Invalid", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


//            zooPanel.add(tournament);
            zooPanel.addTournament(tournament);
            frame.dispose();

        });

        zooPanel.repaint();

        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }

    //function that check if last group added is valid in comparison to competition type and number animals participating
    private boolean validateAnimalCount(int groupNumber, CompetitionPanel tournament){
        if(groupNumber == 1)
            return true;
        int regularCourierTournament = tournament.getRegularCourierTournament();

        switch (regularCourierTournament){
            case 1:
                System.out.println("=======animals in group " + (groupNumber-1) + " " + currentAnimalsInGroups[groupNumber-2] + "========");
                if(currentAnimalsInGroups[groupNumber - 2] < 1)
                    return false;

                break;
            case 2:
                if(currentAnimalsInGroups[groupNumber - 2] < 2)
                    return false;

                break;
        }
        return true;

    }

    private boolean  isCompetitionEmpty(){
        if (currentAnimalsInGroups == null)
            return false;
        if (currentAnimalsInGroups.length == 0)
            return false;
        return true;
    }

    public boolean addGroupColumn(JFrame frame,JPanel groupPanel, int groupNumber, CompetitionPanel tournament) {
        if(!validateAnimalCount(groupNumber,tournament)) {
            JOptionPane.showMessageDialog(frame, "Invalid operation. current groups are Invalid", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        String groupName = "Group " + (groupNumber);

        JPanel columnPanel = new JPanel();
        columnPanel.setLayout(new BoxLayout(columnPanel, BoxLayout.Y_AXIS)); // Vertical layout for column
        columnPanel.setBorder(BorderFactory.createTitledBorder(groupName));

        // Set a fixed width and allow height to grow as animals are added
        columnPanel.setMaximumSize(new Dimension(150, Integer.MAX_VALUE));


        JComboBox<String> addAnimalComboBox = zooPanel.selectAnimalToAddIfAvailable(tournament.getCompetitionType()); //selecting animal only from animals that do not participate in another competition

        addAnimalComboBox.setMaximumSize(new Dimension(150, 25)); // Fix the size of the combo box

        columnPanel.add(addAnimalComboBox);

        addAnimalComboBox.addActionListener(e -> {
            String selectedAnimal = (String) addAnimalComboBox.getSelectedItem();
            if (selectedAnimal != null && !selectedAnimal.equals("Select Animal")) {

                currentAnimalsInGroups[groupNumber-1]++; //add 1 to count for the new animal that was added
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

    private void setAnimalNotAvailable(String selectedAnimal) {
        Animal animal = zooPanel.findAnimal(selectedAnimal);
        animal.setIsAvailable(false);
    }

    private void updateAnimalInCompetition(String selectedAnimal,int groupNumber,CompetitionPanel tournament){
        Animal animal = zooPanel.findAnimal(selectedAnimal);
        tournament.addAnimalToGroup(animal, groupNumber);
    }

    public void clear(){

        currentAnimalsInGroups = new int[5];
        for (int i=0; i<5; ++i)
            currentAnimalsInGroups[i] = 0;

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

//        zooPanel.setBackgroundImage(null);
    }

    /**
     * The main entry point of the application.
     * Initializes and displays the CompetitionFrame.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            CompetitionFrame frame = new CompetitionFrame();
            frame.setVisible(true);
        });

//        checks();
//        testScores();


    }

    private static void testScores() {
        Scores scores = new Scores();
        System.out.printf(scores.toString());
        scores.add("Hodaya");
        System.out.printf(scores.toString());

    }

    private static void checks() {
//        SwingUtilities.invokeLater(() -> {
        CompetitionFrame frame = new CompetitionFrame();
////            CompetitionFrame frame1 = new CompetitionFrame();
//
//            Thread thread = new Thread(frame);
////            Thread thread1 = new Thread(frame1);
//
//            thread.start();
////            thread1.start();
//
//        });

//        AnimalThread animalThread = new AnimalThread(new Dolphin(), 1000, true, false);
//
//        Thread thread = new Thread(animalThread);
//
//        System.out.println();
//        thread.setPriority(10);
//        thread.start();
//
//        System.out.println(thread.activeCount());

        SleepTime sleepTime = SleepTime.getInstance();
        System.out.println(sleepTime);
        sleepTime.setTime(89);
        System.out.println(sleepTime.toString());


    }

    public static int getFrameWidth() {
        return frameWidth;
    }

    public static int getFrameHeight() {
        return frameHeight;
    }
}