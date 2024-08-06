package Graphics;

import Animals.*;
import Mobility.Point;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.*;
import java.text.NumberFormat;
import javax.swing.text.NumberFormatter;


/**
 * The CompetitionPanel class represents the main panel where the competition takes place.
 * It manages the type of competition, participating animals, and the competition table data.
 */
public class CompetitionPanel extends JPanel {

    private static int groupNumber = 0;


    /**
     * Type of competition.
     * 1 - water, 2 - air, 3 - terrestrial.
     */
    private int competitionType;

    /**
     * Array of animals participating in the competition.
     */
    private Animal[] participates;

    /**
     * Number of columns in the competition table.
     */
    private static final int columns = 8;

    /**
     * Background image for the competition panel.
     */
    private Image backgroundImage;


    /**
     * Constructs the CompetitionPanel, initializing the participants array,
     * starting a timer to repaint the panel, and loading the background image.
     */
    public CompetitionPanel() {
        participates = null;
        Timer timer = new Timer(1000 / 60, e -> repaint());
        timer.start();
        loadImage("src/Images/competitionBackground.png");
    }

    /**
     * Loads an image from the specified path and sets it as the background image.
     *
     * @param path The path to the image file.
     */
    private void loadImage(String path) {
        try {
            backgroundImage = new ImageIcon(path).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        repaint();
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
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        if (participates != null) {
            for (Animal animal : participates) {
                if (animal != null) {
                    if (!animal.isRemovedFromCompetition()) //paint animal only of was not removed from competition
                        animal.drawObject(g);
                }
            }
        }
    }

    /**
     * Clears the last animal from the competition.
     * <p>
     * This method updates the status of the last animal in the participants array to indicate it has been removed from the competition.
     * If the participants array is null, it shows a warning message indicating no animals are participating yet.
     * Otherwise, it sets the last animal's removed-from-competition status to true and displays a success message.
     * </p>
     */
    public void clearAnimals() {

        JFrame frame = new JFrame("Participate Animals Information");
        frame.setSize(900, 200);

        if (participates == null) {
            JOptionPane.showMessageDialog(frame, "No participate yet", "Invalid operation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //set animal field that indicates it has been removed from competition to false
        int index = participates.length - 1;
        while (participates[index].isRemovedFromCompetition() && index > 0)
            --index;
        participates[index].setRemovedFromCompetition();


        JOptionPane.showMessageDialog(frame, "Last animal was removed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

        repaint();

    }

    /**
     * Displays information about the participating animals in a table format.
     * <p>
     * This method creates a GUI frame that shows a table with details about each participating animal.
     * If no animals are participating, it shows a warning message.
     * The table includes columns for the animal's name, category, type, speed, energy, amount, distance, and energy consumption.
     * </p>
     */
    public void showInfo() {

        JFrame frame = new JFrame("Participate Animals Information");
        frame.setSize(900, 200);

        // Show a warning if no participants are available
        if (participates == null) {
            JOptionPane.showMessageDialog(frame, "No participate yet", "Invalid operation", JOptionPane.WARNING_MESSAGE);
            return;
        }


        // Define the column names for the table
        String[] columnNames = {"Animal", "Category", "Type", "Speed", "Energy", "Amount", "Distance", "Energy Consuming"};


        Object[][] competitionTable = createTable();

        DefaultTableModel tableModel = new DefaultTableModel(competitionTable, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are not editable
            }
        };


        JTable table = new JTable(tableModel);

        // Disable column reordering
        table.getTableHeader().setReorderingAllowed(false);


        // Customize the table
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 12));


        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);


        // Set the frame visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    /**
     * Creates a table with information about the participating animals.
     * <p>
     * This method constructs a 2D object array containing details of each participating animal.
     * Each row in the array represents an animal, and the columns represent various attributes such as the animal's name, type, category, speed, current energy,
     * total energy from eating, total distance, and energy consumption.
     * If the participants array is null, the method returns null.
     * </p>
     *
     * @return A 2D object array containing animal details, or null if the participants array is null.
     */
    public Object[][] createTable() {
        if (participates == null)
            return null;

        int participatesLen = participates.length;

        Object[][] table = new Object[participatesLen][columns];

        for (int i = 0; i < participatesLen; ++i) {
            table[i][0] = participates[i].getAnimalName();
            table[i][1] = participates[i].getType();
            table[i][2] = participates[i].getCategory();
            table[i][3] = participates[i].getSpeed();
            table[i][4] = participates[i].getCurrentEnergy();
            table[i][5] = participates[i].getTotalEnergyFromEating();
            table[i][6] = participates[i].getTotalDistance();
            table[i][7] = participates[i].getTotalEnergyFromEating() - participates[i].getCurrentEnergy();
        }

        return table;
    }

    /**
     * Opens a dialog to feed the last participating animal.
     * <p>
     * This method creates a GUI frame that allows the user to enter an integer value to increase the energy of the last animal in the participants array.
     * If no animals are participating or the competition type is not set, it shows a warning message.
     * The energy value entered by the user is validated and then used to increase the animal's energy.
     * </p>
     */
    public void eatAnimal() {

        JFrame frame = new JFrame("Feed Animal");
        frame.setSize(500, 200);
        frame.setLayout(new BorderLayout());


        if (participates == null || competitionType == 0) {
            JOptionPane.showMessageDialog(frame, "No participate yet", "Invalid operation", JOptionPane.WARNING_MESSAGE);
            return;
        } else {

            JLabel foodEnergyLabel = new JLabel("Enter an integer to increase the animal's energy level\n");
            foodEnergyLabel.setFont(new Font("Arial", Font.PLAIN, 15));

            JFormattedTextField foodEnergyField = new JFormattedTextField(createNumberFormatter());
            foodEnergyField.setColumns(20);
            JButton updateEnergy = new JButton("Update energy");
            updateEnergy.setFont(new Font("Arial", Font.BOLD, 14));
            updateEnergy.addActionListener(e ->
            {
                try {
                    increaseEnergy(frame, foodEnergyField.getText());
                } catch (IllegalStateException exception) {
                    JOptionPane.showMessageDialog(frame, "An unexpected error occurred: " + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

            });

            // Create and configure panel for layout
            JPanel fieldsPanel = new JPanel();
            fieldsPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10); // Add padding around components

            // Add label
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.gridwidth = GridBagConstraints.REMAINDER; // Span across all columns
            fieldsPanel.add(foodEnergyLabel, gbc);

            // Add text field
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridwidth = GridBagConstraints.REMAINDER; // Span across all columns
            fieldsPanel.add(foodEnergyField, gbc);

            // Add button
            gbc.gridy = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridwidth = GridBagConstraints.REMAINDER; // Span across all columns
            fieldsPanel.add(updateEnergy, gbc);


            // Add panel to frame
            frame.add(fieldsPanel, BorderLayout.CENTER);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }

    /**
     * Increases the energy of the last participating animal.
     * <p>
     * This method updates the energy of the last animal in the participants array based on the provided energy amount.
     * If the array of participating animals is null or empty, it throws an {@link IllegalStateException}.
     * If the animal's energy is successfully increased, it starts the animal's movement on the screen and displays a success message.
     * If the energy increase fails, it displays an error message.
     * </p>
     *
     * @param frame  The frame used to display messages.
     * @param energy The amount of energy to increase, as a string.
     * @throws IllegalStateException If the participants array is null or empty.
     */
    private void increaseEnergy(JFrame frame, String energy) throws IllegalStateException {

        try {
            if (participates == null) {
                throw new IllegalStateException("Participants array is null.");
            }
            int participatesLen = participates.length;
            if (participatesLen == 0) {
                throw new IllegalStateException("Participants array is empty.");
            } else {

                try {

                    int energyValue = Integer.parseInt(energy);

                    int index = participatesLen - 1;
                    while (participates[index].isRemovedFromCompetition() && index > 0)
                        --index;
                    if (participates[0].isRemovedFromCompetition() && index == 0)
                        throw new IllegalStateException("Participants array is empty.");


                    if (participates[index].eat(energyValue)) {
                        JOptionPane.showMessageDialog(frame, "Animal was fed successfully.\ncurrent " +
                                        participates[index].getType() + " energy: " + participates[index].getCurrentEnergy(),
                                participates[index].getType() + " Feeding", JOptionPane.INFORMATION_MESSAGE);


                        startMove();  //make the animal start moving on screen


                    } else {
                        JOptionPane.showMessageDialog(frame, "Animal eating failed.\n can not feed animal above its maximum energy", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    frame.dispose();

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Invalid energy value entered.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalStateException e) {
                    JOptionPane.showMessageDialog(frame, "An unexpected error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(frame, "An unexpected error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Starts the movement of the last participant in the competition.
     * If the type of competition is not terrestrial, the participant starts moving directly.
     * If the type of competition is terrestrial, a timer is set to update the movement of the participant
     * Along a rectangular path.
     */
    private void startMove() {

        int index = participates.length - 1;
        while (participates[index].isRemovedFromCompetition() && index > 0)
            --index;
        if (participates[0].isRemovedFromCompetition() && index == 0)
            return;

        if (getCompetitionType() != 3) {
            participates[index].startMoving();
        } else {
            participates[index].setMoveTimer(new Timer(1000 / 60, e -> updateSide()));
            participates[index].getMoveTimer().start();

            if (participates[index].isDone() < 4)
                if (participates[index].getMoveTimer() != null)
                    participates[index].startMoving();
        }

    }

    /**
     * Updates the orientation and position of the latest participant based on their current location.
     * The participant moves along a rectangular path and updates its orientation at each corner.
     * Stops the movement when the participant returns to the starting point.
     */
    private void updateSide() {


        int index = participates.length - 1;
        while (participates[index].isRemovedFromCompetition() && index > 0)
            --index;
        if (participates[0].isRemovedFromCompetition() && index == 0)
            return;

        if (participates[index].getLocation().equals(new Point(900, 0))) {
            participates[index].setOrientation(Orientation.SOUTH);
            participates[index].startMoving();
            participates[index].setDone(1);

        } else if (participates[index].getLocation().equals(new Point(900, 450))) {
            participates[index].setOrientation(Orientation.WEST);
            participates[index].startMoving();
            participates[index].setDone(2);

        } else if (participates[index].getLocation().equals(new Point(0, 450))) {
            participates[index].setOrientation(Orientation.NORTH);
            participates[index].startMoving();
            participates[index].setDone(3);

        } else if (participates[index].getLocation().equals(new Point(0, 0))) {
            participates[index].setOrientation(Orientation.EAST);

            if (participates[index].isDone() > 0) {
                if (participates[index].getMoveTimer() != null) {
                    participates[index].getMoveTimer().stop();
                    participates[index].setMoveTimer(null);
                    participates[index].setDone(4);
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

    /**
     * //     * Opens a dialog to add a new animal to the competition.
     */
    public void addAnimal() {
        AddAnimalDialog animalDialog = new AddAnimalDialog(this);

//        deleteMe();
    }

    /**
     * Adds an animal to the competition.
     *
     * @param animal The Animal to be added. If null, an error message is logged.
     */
    public void addAnimalToCompetition(Animal animal) {
        if (animal == null) {
            System.out.println("Animal creation failed. Cannot add null animal to the competition.");
            return;
        }


        if (participates == null) {
            participates = new Animal[1];
            participates[0] = animal;
        } else {
            int len = participates.length;
            Animal[] tmp = new Animal[len + 1];
            for (int i = 0; i < len; ++i) {
                tmp[i] = participates[i];
            }
            tmp[len] = animal;
            participates = tmp;
        }

    }

    /**
     * Opens a dialog for adding a new competition.
     * <p>
     * This method displays a window where the user can select the type of competition (Water, Air, Terrestrial).
     * It first checks if a competition is already in progress. If so, it shows a warning message and does not proceed.
     * The user can choose the competition type by clicking one of the provided buttons.
     * </p>
     */
    public void addCompetition() {

        JFrame frame = new JFrame("Add competition");
        frame.setSize(700, 500);
        BorderLayout borderLayout = new BorderLayout();
        frame.setLayout(borderLayout);

        // Check if a competition is already in progress
        if (competitionType != 0) {
            JOptionPane.showMessageDialog(frame, "A competition is already in progress. Please complete the current competition before starting a new one.", "Invalid Operation", JOptionPane.WARNING_MESSAGE);
            return;
        }


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel title = new JLabel("Add Competition");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(title);

        mainPanel.add(Box.createVerticalStrut(50)); // Adjust height as needed

        JPanel addCompetitionButtons = new JPanel();
        addCompetitionButtons.setLayout(new FlowLayout());//6, 5
        addCompetitionButtons.setPreferredSize(new Dimension(600, 50));

        // Create buttons for each competition type
        JButton water = new JButton("Water");
        JButton air = new JButton("Air");
        JButton terrestrial = new JButton("Terrestrial");

        water.setPreferredSize(new Dimension(100, 30));
        air.setPreferredSize(new Dimension(100, 30));
        terrestrial.setPreferredSize(new Dimension(100, 30));

        // Add action listeners to buttons
        water.addActionListener(e -> setCompetitionType(1, frame));
        air.addActionListener(e -> setCompetitionType(2, frame));
        terrestrial.addActionListener(e -> setCompetitionType(3, frame));

        // Add buttons to panel
        addCompetitionButtons.add(water);
        addCompetitionButtons.add(air);
        addCompetitionButtons.add(terrestrial);

        mainPanel.add(titlePanel);
        mainPanel.add(addCompetitionButtons);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    /**
     * Sets the competition type and closes the given frame.
     *
     * @param type  The type of competition (1 - Water, 2 - Air, 3 - Terrestrial).
     * @param frame The JFrame to be closed after setting the type.
     */
    private void setCompetitionType(int type, JFrame frame) {
        competitionType = type;
        JOptionPane.showMessageDialog(frame, "New competition was added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        frame.dispose();
    }

    /**
     * Creates a NumberFormatter for integer input, allowing only valid integers and disallowing invalid input.
     *
     * @return A NumberFormatter configured for integer input.
     */
    private NumberFormatter createNumberFormatter() {
        NumberFormat format = NumberFormat.getIntegerInstance();

        NumberFormatter formatter = new NumberFormatter(format);
        format.setGroupingUsed(false);
        formatter.setMinimum(1);
        formatter.setAllowsInvalid(false);
        return formatter;
    }


    public void addCompetitionNew() {

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

        confirmButton.addActionListener(e -> {
            if (animalGroup.getSelection() == null || competitionGroup.getSelection() == null) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields before submitting.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else
                frame.dispose();
            updateCompetitionType(animalGroup);
            createAndShowGUI();
        });
        cancelButton.addActionListener(e -> {
            frame.dispose();
        });

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Competition Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        switch (competitionType) {
            case 1:
                frame.setSize(820, 200);
                break;
            case 2:
                frame.setSize(663, 200);
                break;
            case 3:
                frame.setSize(500, 200);
                break;
        }


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

        // Center: Panel for groups and animals
        JPanel groupPanel = new JPanel();
        groupPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Horizontal flow with padding
        frame.add(groupPanel, BorderLayout.CENTER);

        addGroupButton.addActionListener(e -> {
            ++groupNumber; // Increase static variable to count number of groups
            if (!isGroupNumberValid()) {
                JOptionPane.showMessageDialog(frame, "Invalid operation. this competition can not have more than " + (groupNumber - 1) + " groups", "Error", JOptionPane.ERROR_MESSAGE);

                return;
            }
            addGroupColumn(groupPanel, "Group " + groupNumber);
        });

        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }

    private void addGroupColumn(JPanel groupPanel, String groupName) {
        JPanel columnPanel = new JPanel();
        columnPanel.setLayout(new BoxLayout(columnPanel, BoxLayout.Y_AXIS));
        columnPanel.setBorder(BorderFactory.createTitledBorder(groupName));
        columnPanel.setPreferredSize(new Dimension(150, 50)); // Set a preferred size for uniformity

        JComboBox addAnimalComboBox = selectAnimalToAdd();

//        JButton addAnimalButton = new JButton("Add Animal");
        columnPanel.add(addAnimalComboBox);


//        addAnimalComboBox.addActionListener(e -> selectAnimalToAdd());

        groupPanel.add(columnPanel);
        groupPanel.revalidate();
        groupPanel.repaint();
    }

    private boolean isGroupNumberValid() {
        switch (competitionType) {
            case 1:
                if (groupNumber > 5)
                    return false;
                break;
            case 2:
                if (groupNumber > 4)
                    return false;
                break;
            case 3:
                if (groupNumber > 3)
                    return false;
                break;
        }
        return true;
    }

    private JComboBox selectAnimalToAdd() {

        if (participates == null)
            return null;

        String[] animalsNames = new String[participates.length + 1];
        animalsNames[0] = "Select Animal";

        int i = 1;

        for (Animal animal : participates) {
            if (animal == null)
                return null;
            animalsNames[i] = animal.getAnimalName();
            ++i;

        }
        JComboBox<String> animalsNamesComboBox = new JComboBox<>(animalsNames);
        animalsNamesComboBox.setPreferredSize(new Dimension(20, 5));

        return animalsNamesComboBox;

    }

    // Helper method to get selected radio button text
    private void updateCompetitionType(ButtonGroup buttonGroup) {
        for (java.util.Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                switch (button.getText()) {
                    case "Air":
                        this.competitionType = 1;
                        break;
                    case "Water":
                        this.competitionType = 2;
                        break;
                    case "Terrestrial":
                        this.competitionType = 3;
                        break;

                }
                System.out.println("selected: " + button.getText());
//                return button.getText();
            }
        }
//        return null; // No button selected
    }

    private void deleteMe() {
        if (participates == null)
            return;
        for (Animal animal : participates)
            System.out.println(animal.toString());
    }


//    public static void main(String[] args) {
//        new AddCompetitionFrame();
//    }
//


}
