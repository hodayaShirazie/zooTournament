//package Graphics;
//
//import Animals.*;
//import javax.swing.*;
//import javax.swing.border.TitledBorder;
//import javax.swing.text.NumberFormatter;
//import java.awt.*;
//import java.text.NumberFormat;
//import javax.swing.JDialog;
//
//
///**
// * Dialog for adding an animal to a competition.
// * Provides input fields and buttons for adding animal details.
// */
//public class AddAnimalDialog extends JDialog{
//
//    /**
//     * The animal being added to the competition.
//     */
//    private Animal animal;
//
//    /**
//     * Constructs the dialog and initializes the animal selection interface.
//     * @param window The CompetitionPanel instance used to determine the type of competition.
//     */
//    public AddAnimalDialog(CompetitionPanel window) {
//        chooseAnimal(window);
//    }
//
//    /**
//     * Creates the UI for selecting an animal.
//     * @param panel The CompetitionPanel instance used to determine the competition type.
//     */
//    public void chooseAnimal(CompetitionPanel panel) {
//        JFrame frame = new JFrame("Add Animal");
//        frame.setSize(500, 400); // Adjust frame size for consistency
//        frame.setLayout(new BorderLayout(10, 10)); // Padding around the edges
//
//        // Center the frame on the screen
//        frame.setLocationRelativeTo(null);
//
//        // Title/Header
//        JLabel titleLabel = new JLabel("Add Animal", SwingConstants.CENTER);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Enlarge title font
//        frame.add(titleLabel, BorderLayout.NORTH);
//
//        // Main Content Panel
//        JPanel mainPanel = new JPanel();
//        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the main panel
//
//        // Animal Selection Section
//        JPanel animalPanel = new JPanel(new GridLayout(0, 1, 0, 2)); // Reduced vertical gap
//        TitledBorder animalBorder = BorderFactory.createTitledBorder("Select an Animal");
//        animalBorder.setTitleFont(new Font("Arial", Font.BOLD, 14)); // Set font size for the border title
//        animalPanel.setBorder(animalBorder);
//        animalPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        // Radio buttons for animal selection
//        JRadioButton alligatorButton = new JRadioButton("Alligator");
//        JRadioButton dolphinButton = new JRadioButton("Dolphin");
//        JRadioButton whaleButton = new JRadioButton("Whale");
//        JRadioButton eagleButton = new JRadioButton("Eagle");
//        JRadioButton pigeonButton = new JRadioButton("Pigeon");
//        JRadioButton catButton = new JRadioButton("Cat");
//        JRadioButton dogButton = new JRadioButton("Dog");
//        JRadioButton snakeButton = new JRadioButton("Snake");
//
//        ButtonGroup animalGroup = new ButtonGroup();
//        animalGroup.add(alligatorButton);
//        animalGroup.add(dolphinButton);
//        animalGroup.add(whaleButton);
//        animalGroup.add(eagleButton);
//        animalGroup.add(pigeonButton);
//        animalGroup.add(catButton);
//        animalGroup.add(dogButton);
//        animalGroup.add(snakeButton);
//
//        animalPanel.add(alligatorButton);
//        animalPanel.add(dolphinButton);
//        animalPanel.add(whaleButton);
//        animalPanel.add(eagleButton);
//        animalPanel.add(pigeonButton);
//        animalPanel.add(catButton);
//        animalPanel.add(dogButton);
//        animalPanel.add(snakeButton);
//
//        mainPanel.add(animalPanel);
//
//        frame.add(mainPanel, BorderLayout.CENTER);
//
//        // Bottom Section: Action Buttons
//        JPanel buttonPanel = new JPanel();
//
//        JButton confirmButton = new JButton("Confirm");
//        JButton cancelButton = new JButton("Cancel");
//
//        confirmButton.addActionListener(e -> {
//            String selectedAnimal = null;
//            if (alligatorButton.isSelected()) selectedAnimal = "Alligator";
//            else if (dolphinButton.isSelected()) selectedAnimal = "Dolphin";
//            else if (whaleButton.isSelected()) selectedAnimal = "Whale";
//            else if (eagleButton.isSelected()) selectedAnimal = "Eagle";
//            else if (pigeonButton.isSelected()) selectedAnimal = "Pigeon";
//            else if (catButton.isSelected()) selectedAnimal = "Cat";
//            else if (dogButton.isSelected()) selectedAnimal = "Dog";
//            else if (snakeButton.isSelected()) selectedAnimal = "Snake";
//
//            if (selectedAnimal != null) {
//                int animalCode = switch (selectedAnimal) {
//                    case "Alligator" -> 1;
//                    case "Dolphin" -> 4;
//                    case "Whale" -> 8;
//                    case "Eagle" -> 5;
//                    case "Pigeon" -> 6;
//                    case "Cat" -> 2;
//                    case "Dog" -> 3;
//                    case "Snake" -> 7;
//                    default -> 0;
//                };
//                startBuildAnimal(animalCode, frame, panel);
//                frame.dispose(); // Close the frame after confirmation
//            } else {
//                JOptionPane.showMessageDialog(frame, "Please select an animal.", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        });
//
//        cancelButton.addActionListener(e -> frame.dispose());
//
//        buttonPanel.add(confirmButton);
//        buttonPanel.add(cancelButton);
//        frame.add(buttonPanel, BorderLayout.SOUTH);
//
//        frame.setVisible(true);
//    }
//
//    /**
//     * Initiates the process of building an animal by opening a new window.
//     * @param animalType The type of animal to be built.
//     * @param frame The parent JFrame to be disposed of after animal creation.
//     * @param panel The CompetitionPanel instance used for context.
//     */
//    private void startBuildAnimal(int animalType, JFrame frame, CompetitionPanel panel){
//        buildAnimal(animalType, panel);
//        frame.dispose();
//    }
//
//    /**
//     * Creates and displays a form for building an animal based on its type.
//     * @param animalType The type of animal to be built.
//     * @param panel The CompetitionPanel instance used for context.
//     */
//    private void buildAnimal(int animalType, CompetitionPanel panel) {
//        String animalStringType = getAnimalStringType(animalType);
//
//        JFrame frame = new JFrame(animalStringType);
//        frame.setSize(700, 500);
//        frame.setLayout(new BorderLayout(10, 10));
//
//        JPanel fieldsPanel = showAnimalForm(frame, animalStringType, panel);
//
//        addSubmitButton(animalType,fieldsPanel, frame, panel);
//
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
//
//    /**
//     * Creates and returns a JPanel containing the form fields for adding an animal.
//     * @param frame The JFrame that holds the form.
//     * @param animalStringType The type of animal being added.
//     * @param panel The CompetitionPanel instance used for context.
//     * @return A JPanel with the form fields.
//     */
//    private JPanel showAnimalForm(JFrame frame, String animalStringType, CompetitionPanel panel) {
//
//        JPanel fieldsPanel = new JPanel();
//        fieldsPanel.setLayout(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 10, 10, 10);
//        gbc.anchor = GridBagConstraints.WEST;
//
//        JLabel nameLabel = new JLabel("Name:");
//        JTextField nameField = new JTextField(20);
//
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        fieldsPanel.add(nameLabel, gbc);
//        gbc.gridx = 1;
//        fieldsPanel.add(nameField, gbc);
//
//
//        JLabel speedLabel = new JLabel("Speed:");
//        JFormattedTextField speedField = new JFormattedTextField(createNumberFormatter());
//        speedField.setColumns(20);
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        fieldsPanel.add(speedLabel, gbc);
//        gbc.gridx = 1;
//        fieldsPanel.add(speedField, gbc);
//
//
//        JLabel energyPerMeterLabel = new JLabel("Energy Per Meter:");
//        JFormattedTextField energyPerMeterField = new JFormattedTextField(createNumberFormatter());
//        energyPerMeterField.setColumns(20);
//
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        fieldsPanel.add(energyPerMeterLabel, gbc);
//        gbc.gridx = 1;
//        fieldsPanel.add(energyPerMeterField, gbc);
//
//
//        JLabel maxEnergyLabel = new JLabel("Max Energy:");
//        JFormattedTextField maxEnergyField = new JFormattedTextField(createNumberFormatter());
//        maxEnergyField.setColumns(20);
//
//        gbc.gridx = 0;
//        gbc.gridy = 3;
//        fieldsPanel.add(maxEnergyLabel, gbc);
//        gbc.gridx = 1;
//        fieldsPanel.add(maxEnergyField, gbc);
//
//        frame.add(fieldsPanel, BorderLayout.CENTER);
//
//
//
//        // Add competition-specific fields based on type
//        if(animalStringType == "Alligator" || animalStringType == "Dolphin" || animalStringType == "Whale") // Water competition
//        {
//            JLabel waterLabel = new JLabel("Competition route:");
//            String[] waterRoutOptions = {"1", "2", "3", "4"};
//            JComboBox<String> waterRoutComboBox = new JComboBox<>(waterRoutOptions);
//            waterRoutComboBox.setPreferredSize(new Dimension(200, 30));
//
//            gbc.gridx = 0;
//            gbc.gridy = 4;
//            fieldsPanel.add(waterLabel, gbc);
//            gbc.gridx = 1;
//            fieldsPanel.add(waterRoutComboBox, gbc);
//        }
//
//        if(animalStringType == "Eagle" || animalStringType == "Pigeon") // Air competition
//        {
//            JLabel airLabel = new JLabel("Competition route:");
//            String[] airRoutOptions = {"1", "2", "3", "4", "5"};
//            JComboBox<String> airRoutComboBox = new JComboBox<>(airRoutOptions);
//            airRoutComboBox.setPreferredSize(new Dimension(200, 30));
//
//            gbc.gridx = 0;
//            gbc.gridy = 5;
//            fieldsPanel.add(airLabel, gbc);
//            gbc.gridx = 1;
//            fieldsPanel.add(airRoutComboBox, gbc);
//        }
//        return fieldsPanel;
//    }
//
//    /**
//     * Adds a submit button to the given JPanel that validates and processes the input fields.
//     * @param animalType The type of animal being added.
//     * @param fieldsPanel The JPanel containing the input fields.
//     * @param frame The JFrame holding the form.
//     * @param panel The CompetitionPanel instance used for context.
//     */
//    private void addSubmitButton(int animalType,JPanel fieldsPanel, JFrame frame, CompetitionPanel panel) {
//        JButton submitButton = new JButton("Create");
//        submitButton.addActionListener(e -> {
//
//            if (validateFields(fieldsPanel)) {
//                JOptionPane.showMessageDialog(frame, "Animal was added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(frame, "Value must be at least 60.", "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            animal = getAnimalInput(animalType,frame, fieldsPanel, panel);
//            if (animal != null) {
//                panel.addAnimalToCompetition(animal);
//            }
//
//            frame.dispose();
//        });
//
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridx = 1;
//        gbc.gridy = 6;
//        gbc.gridwidth = 1;
//        fieldsPanel.add(submitButton, gbc);
//    }
//
//    /**
//     * Validates the input fields in the given JPanel to ensure no required fields are empty.
//     * @param fieldsPanel The JPanel containing the input fields.
//     * @return true if all fields are filled; false otherwise.
//     */
//    private boolean validateFields(JPanel fieldsPanel) {
//
//        for (Component component : fieldsPanel.getComponents()) {
//            if (component instanceof JTextField) {
//                JTextField textField = (JTextField) component;
//                if (textField.getText().trim().isEmpty()) {
//                    return false;
//                }
//            }
//            if (component instanceof JFormattedTextField) {
//                JFormattedTextField formattedTextField = (JFormattedTextField) component;
//                if (formattedTextField.getText().trim().isEmpty()) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    /**
//     * Retrieves input from the form fields and creates an Animal object based on the provided data.
//     * @param animalType The type of the animal being created.
//     * @param frame The JFrame containing the form.
//     * @param fieldsPanel The JPanel with the form fields.
//     * @param panel The CompetitionPanel instance used for context.
//     * @return An Animal object created from the input data, or null if input is invalid.
//     */
//    private Animal getAnimalInput(int animalType,JFrame frame, JPanel fieldsPanel, CompetitionPanel panel) {
//        // Extract input fields from the panel
//        JTextField nameField = (JTextField) fieldsPanel.getComponent(1);
//        JFormattedTextField speedField = (JFormattedTextField) fieldsPanel.getComponent(3);
//        JFormattedTextField energyPerMeterField = (JFormattedTextField) fieldsPanel.getComponent(5);
//        JFormattedTextField maxEnergyField = (JFormattedTextField) fieldsPanel.getComponent(7);
//        JComboBox<String> competitionRouteComboBox = null;
//        String selectedCompetitionRoute = null;
//
//        // Check if competition route is applicable
//        if(panel.getCompetitionType() == 1 || panel.getCompetitionType() == 2) {
//            competitionRouteComboBox = (JComboBox<String>) fieldsPanel.getComponent(9);
//            selectedCompetitionRoute = (String) competitionRouteComboBox.getSelectedItem();
//        }
//
//        // Get values from fields
//        String name = nameField.getText();
//        String speed = speedField.getText();
//        String energyPerMeter = energyPerMeterField.getText();
//        String maxEnergy = maxEnergyField.getText();
//
//        int speedInt = 0;
//        int energyPerMeterInt = 0;
//        int maxEnergyInt = 0;
//        int selectedCompetitionRouteInt = 0;
//
//        // Convert string inputs to integers and handle invalid inputs
//        try {
//            speedInt = Integer.parseInt(speed);
//            energyPerMeterInt = Integer.parseInt(energyPerMeter);
//            maxEnergyInt = Integer.parseInt(maxEnergy);
//
//        } catch (NumberFormatException exception) {
//            JOptionPane.showMessageDialog(frame, "Invalid Input", "Invalid Input", JOptionPane.ERROR_MESSAGE);
//        }
//
//
//
//
//        // Convert competition route input if applicable
//        if(animalType == 1 || animalType == 4 || animalType == 8|| animalType == 5|| animalType == 6) {
//            try {
//                selectedCompetitionRouteInt = Integer.parseInt(selectedCompetitionRoute);
//            }
//            catch (NumberFormatException exception) {
//                JOptionPane.showMessageDialog(frame, "Invalid Input", "Invalid Input", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//        // Create and return the Animal object
//        return createAnimal(animalType,name, speedInt, energyPerMeterInt, maxEnergyInt, selectedCompetitionRouteInt, panel);
//    }
//
//    /**
//     * Creates an Animal object based on the provided parameters and the competition type.
//     * @param animalType The type of the animal to create (e.g., Alligator, Dolphin, Eagle).
//     * @param name The name of the animal.
//     * @param speedInt The speed of the animal.
//     * @param energyPerMeterInt The energy consumption per meter for the animal.
//     * @param maxEnergyInt The maximum energy of the animal.
//     * @param selectedCompetitionRoute The competition route number (for water and air animals).
//     * @param panel The CompetitionPanel instance used for context.
//     * @return The created Animal object.
//     * @throws IllegalArgumentException If an invalid animal type or competition category is provided.
//     */
//    private Animal createAnimal(int animalType, String name, int speedInt, int energyPerMeterInt, int maxEnergyInt, int selectedCompetitionRoute, CompetitionPanel panel) throws IllegalArgumentException {
//        Animal animalObj = null;
//
//        switch (animalType) {
//            case 1:
//                animalObj = new Alligator(name, speedInt, energyPerMeterInt, maxEnergyInt, selectedCompetitionRoute, panel);
//                break;
//
//            case 4:
//                animalObj = new Dolphin(name, speedInt, energyPerMeterInt, maxEnergyInt, selectedCompetitionRoute, panel);
//                break;
//
//            case 8:
//                animalObj = new Whale(name, speedInt, energyPerMeterInt, maxEnergyInt, selectedCompetitionRoute, panel);
//                break;
//
//            case 5:
//                animalObj = new Eagle(name, speedInt, energyPerMeterInt, maxEnergyInt, selectedCompetitionRoute, panel);
//                break;
//
//            case 6:
//                animalObj = new Pigeon(name, speedInt, energyPerMeterInt, maxEnergyInt, selectedCompetitionRoute, panel);
//                break;
//
//            case 2:
//                animalObj = new Cat(name, speedInt, energyPerMeterInt, maxEnergyInt, panel);
//                break;
//
//            case 3:
//                animalObj = new Dog(name, speedInt, energyPerMeterInt, maxEnergyInt, panel);
//                break;
//
//            case 7:
//                animalObj = new Snake(name, speedInt, energyPerMeterInt, maxEnergyInt, panel);
//                break;
//
//            default:
//                throw new IllegalArgumentException("Invalid Category");
//        }
//
//
//        // Return the created Animal object
//        return animalObj;
//
//    }
//
//    /**
//     * Retrieves the current animal object.
//     * @return The animal object that was set in the dialog.
//     */
//    public Animal getAnimal() {
//        return animal;
//    }
//
//    /**
//     * Converts an animal type identifier to its corresponding animal name.
//     * @param type The identifier for the animal type (e.g., 1 for Alligator, 2 for Cat).
//     * @return The name of the animal corresponding to the given identifier.
//     * @throws IllegalArgumentException If the provided type does not match any known animal types.
//     */
//    private String getAnimalStringType(int type) throws IllegalArgumentException {
//        switch (type) {
//            case 1:
//                return "Alligator";
//            case 2:
//                return "Cat";
//            case 3:
//                return "Dog";
//            case 4:
//                return "Dolphin";
//            case 5:
//                return "Eagle";
//            case 6:
//                return "Pigeon";
//            case 7:
//                return "Snake";
//            case 8:
//                return "Whale";
//            default:
//                throw new IllegalArgumentException();
//        }
//    }
//
//    /**
//     * Creates a NumberFormatter configured for integer input with no grouping and validation.
//     * @return A NumberFormatter instance that formats integers, disallowing invalid input and grouping.
//     */
//    private NumberFormatter createNumberFormatter() {
//        NumberFormat format = NumberFormat.getIntegerInstance();
//
//        NumberFormatter formatter = new NumberFormatter(format);
//        format.setGroupingUsed(false);
//        formatter.setMinimum(1);
//        formatter.setAllowsInvalid(false);
//        return formatter;
//    }
//
//    /**
//     * Validates if the given animal type is appropriate for the specified competition panel type.
//     * @param animalType The type of the animal to validate.
//     * @param panel The competition panel that defines the competition type.
//     * @return {@code true} if the animal type is valid for the given competition type; {@code false} otherwise.
//     */
//    private boolean IsValidAnimalTypeFields( int animalType, CompetitionPanel panel) {
//        System.out.println("competitionType: " +panel.getCompetitionType() + " animalType " + animalType);
//
//        //Alligator is Water Animal and Terrestrial
//        if (animalType == 1 && (panel.getCompetitionType() == 1 || panel.getCompetitionType() == 3))
//            return true;
//
//        //only Dolphin and Wale(and Alligator) are Water Animal
//        if (panel.getCompetitionType() == 1) {
//            if (animalType == 4 || animalType == 8)
//                return true;
//        }
//        //only Eagle and Pigeon are Air Animal
//        else if (panel.getCompetitionType() == 2) {
//            if (animalType == 5 || animalType == 6)
//                return true;
//        }
//        //only Dog, Cat and Snake are Terrestrial Animal
//        else if (panel.getCompetitionType() == 3) {
//            if (animalType == 2 || animalType == 3 || animalType == 7)
//                return true;
//        }
//        return false;
//    }
//
//    /**
//     * Displays an information dialog to the user indicating that the selected animal type is not appropriate
//     * for the current competition type.
//     *
//     * @param frame The parent frame for the dialog.
//     * @param animalType The type of the animal being checked.
//     * @param panel The competition panel that defines the current competition type.
//     */
//    private void performMessageDialog(JFrame frame, int animalType, CompetitionPanel panel) {
//        String message = "";
//
//        if (panel.getCompetitionType() == 1) {
//            message = String.format("%s is not a Water Animal", getAnimalStringType(animalType));
//
//        } else if (panel.getCompetitionType() == 2) {
//            message = String.format("%s is not an Air Animal", getAnimalStringType(animalType));
//
//        } else if (panel.getCompetitionType() == 3) {
//            message = String.format("%s is not a Terrestrial Animal", getAnimalStringType(animalType));
//
//        }
//        JOptionPane.showMessageDialog(frame, message, "Input Values", JOptionPane.INFORMATION_MESSAGE);
//    }
//}












package Graphics;

import Animals.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import javax.swing.JDialog;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Dialog for adding an animal to a competition.
 * Provides input fields and buttons for adding animal details.
 */
public class AddAnimalDialog extends JDialog{

    /**
     * The animal being added to the competition.
     */
    private Animal animal;
    private int competitionType;

    /**
     * Constructs the dialog and initializes the animal selection interface.
     * @param window The CompetitionPanel instance used to determine the type of competition.
     */
    public AddAnimalDialog(ZooPanel window) {
//        this.competitionType = competitionType;
        chooseAnimal(window);
    }

    /**
     * Creates the UI for selecting an animal.
     * @param panel The CompetitionPanel instance used to determine the competition type.
     */
    public void chooseAnimal(ZooPanel panel) {
        JFrame frame = new JFrame("Add Animal");
        frame.setSize(500, 400); // Adjust frame size for consistency
        frame.setLayout(new BorderLayout(10, 10)); // Padding around the edges

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Title/Header
        JLabel titleLabel = new JLabel("Add Animal", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Enlarge title font
        frame.add(titleLabel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the main panel

        // Animal Selection Section
        JPanel animalPanel = new JPanel(new GridLayout(0, 1, 0, 2)); // Reduced vertical gap
        TitledBorder animalBorder = BorderFactory.createTitledBorder("Select an Animal");
        animalBorder.setTitleFont(new Font("Arial", Font.BOLD, 14)); // Set font size for the border title
        animalPanel.setBorder(animalBorder);
        animalPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Radio buttons for animal selection
        JRadioButton alligatorButton = new JRadioButton("Alligator");
        JRadioButton dolphinButton = new JRadioButton("Dolphin");
        JRadioButton whaleButton = new JRadioButton("Whale");
        JRadioButton eagleButton = new JRadioButton("Eagle");
        JRadioButton pigeonButton = new JRadioButton("Pigeon");
        JRadioButton catButton = new JRadioButton("Cat");
        JRadioButton dogButton = new JRadioButton("Dog");
        JRadioButton snakeButton = new JRadioButton("Snake");

        ButtonGroup animalGroup = new ButtonGroup();
        animalGroup.add(alligatorButton);
        animalGroup.add(dolphinButton);
        animalGroup.add(whaleButton);
        animalGroup.add(eagleButton);
        animalGroup.add(pigeonButton);
        animalGroup.add(catButton);
        animalGroup.add(dogButton);
        animalGroup.add(snakeButton);

        animalPanel.add(alligatorButton);
        animalPanel.add(dolphinButton);
        animalPanel.add(whaleButton);
        animalPanel.add(eagleButton);
        animalPanel.add(pigeonButton);
        animalPanel.add(catButton);
        animalPanel.add(dogButton);
        animalPanel.add(snakeButton);

        mainPanel.add(animalPanel);

        frame.add(mainPanel, BorderLayout.CENTER);

        // Bottom Section: Action Buttons
        JPanel buttonPanel = new JPanel();

        JButton confirmButton = new JButton("Confirm");
        JButton cancelButton = new JButton("Cancel");

        confirmButton.addActionListener(e -> {
            String selectedAnimal = null;
            if (alligatorButton.isSelected()) selectedAnimal = "Alligator";
            else if (dolphinButton.isSelected()) selectedAnimal = "Dolphin";
            else if (whaleButton.isSelected()) selectedAnimal = "Whale";
            else if (eagleButton.isSelected()) selectedAnimal = "Eagle";
            else if (pigeonButton.isSelected()) selectedAnimal = "Pigeon";
            else if (catButton.isSelected()) selectedAnimal = "Cat";
            else if (dogButton.isSelected()) selectedAnimal = "Dog";
            else if (snakeButton.isSelected()) selectedAnimal = "Snake";

            if (selectedAnimal != null) {
                int animalCode = switch (selectedAnimal) {
                    case "Alligator" -> 1;
                    case "Dolphin" -> 4;
                    case "Whale" -> 8;
                    case "Eagle" -> 5;
                    case "Pigeon" -> 6;
                    case "Cat" -> 2;
                    case "Dog" -> 3;
                    case "Snake" -> 7;
                    default -> 0;
                };
                startBuildAnimal(animalCode, frame, panel);
                frame.dispose(); // Close the frame after confirmation
            } else {
                JOptionPane.showMessageDialog(frame, "Please select an animal.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> frame.dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.dispose();
        frame.setVisible(true);
    }

    /**
     * Initiates the process of building an animal by opening a new window.
     * @param animalType The type of animal to be built.
     * @param frame The parent JFrame to be disposed of after animal creation.
     * @param panel The CompetitionPanel instance used for context.
     */
    private void startBuildAnimal(int animalType, JFrame frame, ZooPanel panel){
        buildAnimal(animalType, panel);
        frame.dispose();
    }

    /**
     * Creates and displays a form for building an animal based on its type.
     * @param animalType The type of animal to be built.
     * @param panel The CompetitionPanel instance used for context.
     */
    private void buildAnimal(int animalType, ZooPanel panel) {
        String animalStringType = getAnimalStringType(animalType);

        JFrame frame = new JFrame(animalStringType);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel fieldsPanel = showAnimalForm(frame, animalStringType, panel);

        addSubmitButton(animalType,fieldsPanel, frame, panel);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Creates and returns a JPanel containing the form fields for adding an animal.
     * @param frame The JFrame that holds the form.
     * @param animalStringType The type of animal being added.
     * @param panel The CompetitionPanel instance used for context.
     * @return A JPanel with the form fields.
     */
    private JPanel showAnimalForm(JFrame frame, String animalStringType, ZooPanel panel) {

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);

        JLabel speedLabel = new JLabel("Speed:");
        JFormattedTextField speedField = new JFormattedTextField(createNumberFormatter());
        speedField.setColumns(20);

        JLabel energyPerMeterLabel = new JLabel("Energy Per Meter:");
        JFormattedTextField energyPerMeterField = new JFormattedTextField(createNumberFormatter());
        energyPerMeterField.setColumns(20);

        JLabel maxEnergyLabel = new JLabel("Max Energy:");
        JFormattedTextField maxEnergyField = new JFormattedTextField(createNumberFormatter());
        maxEnergyField.setColumns(20);

        // Add key bindings for up and down arrow keys
        addArrowKeyNavigation(nameField, speedField);
        addArrowKeyNavigation(speedField, energyPerMeterField);
        addArrowKeyNavigation(energyPerMeterField, maxEnergyField);

        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldsPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        fieldsPanel.add(speedLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(speedField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        fieldsPanel.add(energyPerMeterLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(energyPerMeterField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        fieldsPanel.add(maxEnergyLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(maxEnergyField, gbc);

        frame.add(fieldsPanel, BorderLayout.CENTER);



        // Add competition-specific fields based on type

        // Water competition
        if(animalStringType == "Alligator" || animalStringType == "Dolphin" || animalStringType == "Whale") {
            JLabel waterLabel = new JLabel("Competition route:");
            String[] waterRoutOptions = {"1", "2", "3", "4"};
            JComboBox<String> waterRoutComboBox = new JComboBox<>(waterRoutOptions);
            waterRoutComboBox.setPreferredSize(new Dimension(200, 30));

            gbc.gridx = 0;
            gbc.gridy = 4;
            fieldsPanel.add(waterLabel, gbc);
            gbc.gridx = 1;
            fieldsPanel.add(waterRoutComboBox, gbc);
        }

        // Air competition
        if(animalStringType == "Eagle" || animalStringType == "Pigeon") {
            JLabel airLabel = new JLabel("Competition route:");
            String[] airRoutOptions = {"1", "2", "3", "4", "5"};
            JComboBox<String> airRoutComboBox = new JComboBox<>(airRoutOptions);
            airRoutComboBox.setPreferredSize(new Dimension(200, 30));

            gbc.gridx = 0;
            gbc.gridy = 5;
            fieldsPanel.add(airLabel, gbc);
            gbc.gridx = 1;
            fieldsPanel.add(airRoutComboBox, gbc);
        }
        return fieldsPanel;
    }

    private void addArrowKeyNavigation(JComponent currentField, JComponent nextField) {
        InputMap inputMap = currentField.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap actionMap = currentField.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "moveFocusDown");
        inputMap.put(KeyStroke.getKeyStroke("UP"), "moveFocusUp");

        actionMap.put("moveFocusDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextField.requestFocus();
            }
        });

        actionMap.put("moveFocusUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentField.transferFocusBackward();
            }
        });
    }

    /**
     * Adds a submit button to the given JPanel that validates and processes the input fields.
     * @param animalType The type of animal being added.
     * @param fieldsPanel The JPanel containing the input fields.
     * @param frame The JFrame holding the form.
     * @param panel The CompetitionPanel instance used for context.
     */
    private void addSubmitButton(int animalType,JPanel fieldsPanel, JFrame frame, ZooPanel panel) {
        JButton submitButton = new JButton("Create");
        submitButton.addActionListener(e -> {

            if (validateFields(fieldsPanel)) {
                JOptionPane.showMessageDialog(frame, "Animal was added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Value must be at least 60.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            animal = getAnimalInput(animalType,frame, fieldsPanel, panel);
            if (animal != null) {
                panel.addAnimalToCompetition(animal);
            }

            frame.dispose();
        });


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        fieldsPanel.add(submitButton, gbc);
    }

    /**
     * Validates the input fields in the given JPanel to ensure no required fields are empty.
     * @param fieldsPanel The JPanel containing the input fields.
     * @return true if all fields are filled; false otherwise.
     */
    private boolean validateFields(JPanel fieldsPanel) {

        for (Component component : fieldsPanel.getComponents()) {
            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                if (textField.getText().trim().isEmpty()) {
                    return false;
                }
            }
            if (component instanceof JFormattedTextField) {
                JFormattedTextField formattedTextField = (JFormattedTextField) component;
                if (formattedTextField.getText().trim().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Retrieves input from the form fields and creates an Animal object based on the provided data.
     * @param animalType The type of the animal being created.
     * @param frame The JFrame containing the form.
     * @param fieldsPanel The JPanel with the form fields.
     * @param panel The CompetitionPanel instance used for context.
     * @return An Animal object created from the input data, or null if input is invalid.
     */
    private Animal getAnimalInput(int animalType,JFrame frame, JPanel fieldsPanel, ZooPanel panel) {
        // Extract input fields from the panel
        JTextField nameField = (JTextField) fieldsPanel.getComponent(1);
        JFormattedTextField speedField = (JFormattedTextField) fieldsPanel.getComponent(3);
        JFormattedTextField energyPerMeterField = (JFormattedTextField) fieldsPanel.getComponent(5);
        JFormattedTextField maxEnergyField = (JFormattedTextField) fieldsPanel.getComponent(7);
        JComboBox<String> competitionRouteComboBox = null;
        String selectedCompetitionRoute = null;

        // Check if competition route is applicable
        if(animalType == 1 || animalType == 4 || animalType == 8|| animalType == 5|| animalType == 6) {
            competitionRouteComboBox = (JComboBox<String>) fieldsPanel.getComponent(9);
            selectedCompetitionRoute = (String) competitionRouteComboBox.getSelectedItem();
        }

        // Get values from fields
        String name = nameField.getText();
        String speed = speedField.getText();
        String energyPerMeter = energyPerMeterField.getText();
        String maxEnergy = maxEnergyField.getText();

        int speedInt = 0;
        int energyPerMeterInt = 0;
        int maxEnergyInt = 0;
        int selectedCompetitionRouteInt = 0;

        // Convert string inputs to integers and handle invalid inputs
        try {
            speedInt = Integer.parseInt(speed);
            energyPerMeterInt = Integer.parseInt(energyPerMeter);
            maxEnergyInt = Integer.parseInt(maxEnergy);

        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(frame, "Invalid Input", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }




        // Convert competition route input if applicable
        if(animalType == 1 || animalType == 4 || animalType == 8|| animalType == 5|| animalType == 6) {
            try {
                selectedCompetitionRouteInt = Integer.parseInt(selectedCompetitionRoute);
            }
            catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(frame, "Invalid Input bla bla", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
        // Create and return the Animal object
        return createAnimal(animalType,name, speedInt, energyPerMeterInt, maxEnergyInt, selectedCompetitionRouteInt, panel);
    }

    /**
     * Creates an Animal object based on the provided parameters and the competition type.
     * @param animalType The type of the animal to create (e.g., Alligator, Dolphin, Eagle).
     * @param name The name of the animal.
     * @param speedInt The speed of the animal.
     * @param energyPerMeterInt The energy consumption per meter for the animal.
     * @param maxEnergyInt The maximum energy of the animal.
     * @param selectedCompetitionRoute The competition route number (for water and air animals).
     * @param panel The CompetitionPanel instance used for context.
     * @return The created Animal object.
     * @throws IllegalArgumentException If an invalid animal type or competition category is provided.
     */
    private Animal createAnimal(int animalType, String name, int speedInt, int energyPerMeterInt, int maxEnergyInt, int selectedCompetitionRoute, ZooPanel panel) throws IllegalArgumentException {
        Animal animalObj = null;

        switch (animalType) {
            case 1:
                animalObj = new Alligator(name, speedInt, energyPerMeterInt, maxEnergyInt, selectedCompetitionRoute);
                break;

            case 4:
                animalObj = new Dolphin(name, speedInt, energyPerMeterInt, maxEnergyInt, selectedCompetitionRoute);
                break;

            case 8:
                animalObj = new Whale(name, speedInt, energyPerMeterInt, maxEnergyInt, selectedCompetitionRoute);
                break;

            case 5:
                animalObj = new Eagle(name, speedInt, energyPerMeterInt, maxEnergyInt, selectedCompetitionRoute);
                break;

            case 6:
                animalObj = new Pigeon(name, speedInt, energyPerMeterInt, maxEnergyInt, selectedCompetitionRoute);
                break;

            case 2:
                animalObj = new Cat(name, speedInt, energyPerMeterInt, maxEnergyInt);
                break;

            case 3:
                animalObj = new Dog(name, speedInt, energyPerMeterInt, maxEnergyInt);
                break;

            case 7:
                animalObj = new Snake(name, speedInt, energyPerMeterInt, maxEnergyInt);
                break;

            default:
                throw new IllegalArgumentException("Invalid Category");
        }


        // Return the created Animal object
        return animalObj;

    }

    /**
     * Retrieves the current animal object.
     * @return The animal object that was set in the dialog.
     */
    public Animal getAnimal() {
        return animal;
    }

    /**
     * Converts an animal type identifier to its corresponding animal name.
     * @param type The identifier for the animal type (e.g., 1 for Alligator, 2 for Cat).
     * @return The name of the animal corresponding to the given identifier.
     * @throws IllegalArgumentException If the provided type does not match any known animal types.
     */
    private String getAnimalStringType(int type) throws IllegalArgumentException {
        switch (type) {
            case 1:
                return "Alligator";
            case 2:
                return "Cat";
            case 3:
                return "Dog";
            case 4:
                return "Dolphin";
            case 5:
                return "Eagle";
            case 6:
                return "Pigeon";
            case 7:
                return "Snake";
            case 8:
                return "Whale";
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Creates a NumberFormatter configured for integer input with no grouping and validation.
     * @return A NumberFormatter instance that formats integers, disallowing invalid input and grouping.
     */
    private NumberFormatter createNumberFormatter() {
        NumberFormat format = NumberFormat.getIntegerInstance();

        NumberFormatter formatter = new NumberFormatter(format);
        format.setGroupingUsed(false);
        formatter.setMinimum(1);
        formatter.setAllowsInvalid(false);
        return formatter;
    }

    /**
     * Validates if the given animal type is appropriate for the specified competition panel type.
     * @param animalType The type of the animal to validate.
     * @param panel The competition panel that defines the competition type.
     * @return {@code true} if the animal type is valid for the given competition type; {@code false} otherwise.
     */
//    private boolean IsValidAnimalTypeFields( int animalType, ZooPanel panel) {
//        System.out.println("competitionType: " +panel.getCompetitionType() + " animalType " + animalType);
//
//        //Alligator is Water Animal and Terrestrial
//        if (animalType == 1 && (panel.getCompetitionType() == 1 || panel.getCompetitionType() == 3))
//            return true;
//
//        //only Dolphin and Wale(and Alligator) are Water Animal
//        if (panel.getCompetitionType() == 1) {
//            if (animalType == 4 || animalType == 8)
//                return true;
//        }
//        //only Eagle and Pigeon are Air Animal
//        else if (panel.getCompetitionType() == 2) {
//            if (animalType == 5 || animalType == 6)
//                return true;
//        }
//        //only Dog, Cat and Snake are Terrestrial Animal
//        else if (panel.getCompetitionType() == 3) {
//            if (animalType == 2 || animalType == 3 || animalType == 7)
//                return true;
//        }
//        return false;
//    }

    /**
     * Displays an information dialog to the user indicating that the selected animal type is not appropriate
     * for the current competition type.
     *
     * @param frame The parent frame for the dialog.
     * @param animalType The type of the animal being checked.
     * @param panel The competition panel that defines the current competition type.
     */
//    private void performMessageDialog(JFrame frame, int animalType, ZooPanel panel) {
//        String message = "";
//
//        if (panel.getCompetitionType() == 1) {
//            message = String.format("%s is not a Water Animal", getAnimalStringType(animalType));
//
//        } else if (panel.getCompetitionType() == 2) {
//            message = String.format("%s is not an Air Animal", getAnimalStringType(animalType));
//
//        } else if (panel.getCompetitionType() == 3) {
//            message = String.format("%s is not a Terrestrial Animal", getAnimalStringType(animalType));
//
//        }
//        JOptionPane.showMessageDialog(frame, message, "Input Values", JOptionPane.INFORMATION_MESSAGE);
//    }
}
