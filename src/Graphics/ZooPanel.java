package Graphics;

import Animals.Animal;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Map;

import Competitions.Scores;
import Competitions.SleepTime;

//Todo free memory


/**
 * The ZooPanel class represents a custom JPanel used for displaying and managing the zoo's graphical interface.
 * This panel is responsible for managing and rendering various competitions and activities within the zoo.
 * It extends JPanel to provide a specialized panel for the zoo's graphical needs.
 */
public class ZooPanel extends JPanel{

    private CompetitionPanel [] panels;
    private Image backgroundImage;
    private Animal[] players;
    /**
     * Number of columns in the competition table.
     */
    private static final int animalsTableColumns = 8;
    private static final int tournamentTableColumns = 2;


    public ZooPanel(){
        Timer timer = new Timer(1000 / 60, e -> repaint());
        timer.start();
        panels = null;
        players = null;
        loadImage("src/Images/competitionBackground.png");
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        if (players != null) {
            for (Animal animal : players) {
                if (animal != null)
                    if (!(animal.isAvailable())) {
                        animal.drawObject(g);
                    }
            }
        }
    }

    /**
     *      * Opens a dialog to add a new animal to the competition.
     */
    public void addAnimal() {
        AddAnimalDialog animalDialog = new AddAnimalDialog(this);
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


        if (players == null) {
            players = new Animal[1];
            players[0] = animal;
        } else {
            int len = players.length;
            Animal[] tmp = new Animal[len + 1];
            for (int i = 0; i < len; ++i) {
                tmp[i] = players[i];
            }
            tmp[len] = animal;
            players = tmp;
        }

    }

    public Animal[] getPlayers() {
        return players;
    }

    public void addTournament(CompetitionPanel panel){
        if (panels == null) {
            panels = new CompetitionPanel[1];
            panels[0] = panel;
        }
        else{
            CompetitionPanel [] temp = new CompetitionPanel[panels.length+1];
            for (int i = 0; i < panels.length; ++i) {
                temp[i] = panels[i];
            }
            temp[panels.length] = panel;
            panels = temp;

        }


        repaint();
        panel.createTournament();

//        deleteMe();


    }

    /**
     * Displays information about the participating animals in a table format.
     * <p>
     * This method creates a GUI frame that shows a table with details about each participating animal.
     * If no animals are participating, it shows a warning message.
     * The table includes columns for the animal's name, category, type, speed, energy, amount, distance, and energy consumption.
     * </p>
     */
    public void showAnimalsInfo() {

        JFrame frame = new JFrame("Participate Animals Information");
        frame.setSize(900, 200);

        // Show a warning if no participants are available
        if (players == null) {
            JOptionPane.showMessageDialog(frame, "No participate yet", "Invalid operation", JOptionPane.WARNING_MESSAGE);
            return;
        }


        // Define the column names for the table
        String[] columnNames = {"Animal", "Category", "Type", "Speed", "Energy", "Amount", "Distance", "Energy Consuming"};


        Object[][] competitionTable = createAnimalsTable();

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
    public Object[][] createAnimalsTable() {
        if (players == null)
            return null;

        int playersLen = players.length;

        Object[][] table = new Object[playersLen][animalsTableColumns];

        for (int i = 0; i < playersLen; ++i) {
            table[i][0] = players[i].getAnimalName();
            table[i][1] = players[i].getType();
            table[i][2] = players[i].getCategory();
            table[i][3] = players[i].getSpeed();
            table[i][4] = players[i].getCurrentEnergy();
            table[i][5] = players[i].getTotalEnergyFromEating();
            table[i][6] = players[i].getTotalDistance();
            table[i][7] = players[i].getTotalEnergyFromEating() - players[i].getCurrentEnergy();
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
    private void increaseEnergy(JFrame frame, String energy, String animalName) throws IllegalStateException {

        try {
            if (players == null) {
                throw new IllegalStateException("Participants array is null.");
            }
            Animal animalToFeed = findAnimal(animalName);

            try {

                int energyValue = Integer.parseInt(energy);

                if (animalToFeed.eat(energyValue)) {
                    JOptionPane.showMessageDialog(frame, "Animal was fed successfully.\ncurrent " +
                                    animalToFeed.getAnimalName() + " energy: " + animalToFeed.getCurrentEnergy(),
                            animalToFeed.getType() + " Feeding", JOptionPane.INFORMATION_MESSAGE);


                    if(animalToFeed.isNeedToMove()) {
                        if(animalToFeed.getAnimalAsNumber(animalToFeed.getCategory()) != 3)
                            animalToFeed.startMoving();
                        else
                            animalToFeed.startMoveTerrestrial();

                    }


                } else {
                    JOptionPane.showMessageDialog(frame, "Animal eating failed.\n can not feed animal above its maximum energy", "Error", JOptionPane.ERROR_MESSAGE);
                }
                frame.dispose();

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid energy value entered.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalStateException e) {
                JOptionPane.showMessageDialog(frame, "An unexpected error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(frame, "An unexpected error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public Animal findAnimal(String animalName){
        for (int i=0; i<players.length; ++i)
            if(players[i].getAnimalName().equals(animalName))
                return players[i];
        return null;
    }

    public CompetitionPanel[] getPanels() {
        return panels;
    }

    private void loadImage(String path) {
        try {
            backgroundImage = new ImageIcon(path).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        repaint();
    }


    public JComboBox<String> selectAnimalToAdd() {
        if (players == null)
            return new JComboBox<>(new String[]{"No Animals Available"});

        String[] animalsNames = new String[players.length + 1];
        animalsNames[0] = "Select Animal";

        int i = 1;
        for (Animal animal : players) {
            if (animal != null) {
                animalsNames[i] = animal.getAnimalName();
                ++i;
            }
        }
        JComboBox<String> animalsNamesComboBox = new JComboBox<>(animalsNames);
        animalsNamesComboBox.setPreferredSize(new Dimension(150, 25));

        return animalsNamesComboBox;
    }

    public JComboBox<String> selectAnimalToAddIfAvailable(int competitionType, int competitionRout) {

        if (players == null)
            return new JComboBox<>(new String[]{"No Animals Available"});

        String[] animalsNames = new String[countAvailableAnimalsFromType(competitionType) + 1];
        animalsNames[0] = "Select Animal";

        switch (competitionType) {
            case 1: //selects from Water animals
            {
                int i = 1;
                for (Animal animal : players) {
                    if (animal != null)
                        if (animal.isAvailable())
                            if (animal.getCategory().equals("Water") || animal.getCategory().equals("Terrestrial+Water")) {
                                if (animal.getCompetitionRoute() == competitionRout) {
                                    animalsNames[i] = animal.getAnimalName();
                                    ++i;
                                }
                            }
                }

                break;
            }
            case 2: //selects from Air animals
            {
                int i = 1;
                for (Animal animal : players) {
                    if (animal != null)
                        if (animal.isAvailable())
                            if (animal.getCategory().equals("Air")) {
                                if (animal.getCompetitionRoute() == competitionRout) {
                                    animalsNames[i] = animal.getAnimalName();
                                    ++i;
                                }
                            }
                }
                break;
            }
            case 3: //selects from Terrestrial animals
            {
                int i = 1;
                for (Animal animal : players) {
                    if (animal != null)
                        if (animal.isAvailable())
                            if (animal.getCategory().equals("Terrestrial") || animal.getCategory().equals("Terrestrial+Water")) {
                                animalsNames[i] = animal.getAnimalName();
                                ++i;
                            }

                }

                break;
            }
            default:
                System.out.println("Error accorded");
                break;
        }

        JComboBox<String> animalsNamesComboBox = new JComboBox<>(animalsNames);
        animalsNamesComboBox.setPreferredSize(new Dimension(150, 25));

        return animalsNamesComboBox;
    }

    public JComboBox<String> selectAnimalToAddIfAvailable(int competitionType) {

        if (players == null)
            return new JComboBox<>(new String[]{"No Animals Available"});

        String[] animalsNames = new String[countAvailableAnimalsFromType(competitionType) + 1];
        animalsNames[0] = "Select Animal";

        switch (competitionType) {
            case 1: //selects from Water animals
            {
                int i = 1;
                for (Animal animal : players) {
                    if (animal != null)
                        if (animal.isAvailable())
                            if (animal.getCategory().equals("Water") || animal.getCategory().equals("Terrestrial+Water")) {
                                animalsNames[i] = animal.getAnimalName();
                                ++i;
                            }
                }

                break;
            }
            case 2: //selects from Air animals
            {
                int i = 1;
                for (Animal animal : players) {
                    if (animal != null)
                        if (animal.isAvailable())
                            if (animal.getCategory().equals("Air")) {
                                animalsNames[i] = animal.getAnimalName();
                                ++i;
                            }
                }
                break;
            }
            case 3: //selects from Terrestrial animals
            {
                int i = 1;
                for (Animal animal : players) {
                    if (animal != null)
                        if (animal.isAvailable())
                            if (animal.getCategory().equals("Terrestrial") || animal.getCategory().equals("Terrestrial+Water")) {
                                animalsNames[i] = animal.getAnimalName();
                                ++i;
                            }

                }

                break;
            }
            default:
                System.out.println("Error accorded");
                break;
        }

        JComboBox<String> animalsNamesComboBox = new JComboBox<>(animalsNames);
        animalsNamesComboBox.setPreferredSize(new Dimension(150, 25));

        return animalsNamesComboBox;
    }

    public int countAvailableAnimalsFromType(int competitionType) {

        int count = 0;
        switch (competitionType) {
            case 1: //selects from Water animals
            {
                for (Animal animal : players) {
                    if (animal != null) {
                        if (animal.isAvailable())
                            if (animal.getCategory().equals("Water") || animal.getCategory().equals("Terrestrial+Water"))
                                ++count;
                    }
                }

                break;
            }
            case 2: //selects from Water animals
            {
                for (Animal animal : players) {
                    if (animal != null)
                        if (animal.isAvailable())
                            if (animal.getCategory().equals("Air")) {
                                ++count;
                            }
                }
                break;
            }
            case 3: //selects from Water animals
            {
                for (Animal animal : players) {
                    if (animal != null) {
                        if (animal.isAvailable())
                            if (animal.getCategory().equals("Terrestrial") || animal.getCategory().equals("Terrestrial+Water"))
                                ++count;

                    }
                }
                break;
            }
            default:
                System.out.println("Error accorded");
                break;
        }
        return count;
    }

    public int availableAnimals() {
        if (players == null)
            return 0;

        int availableCount = 0;

        for (Animal animal : players) {
            if (animal != null) {
                if (animal.isAvailable())
                    ++availableCount;
            }
        }
        return availableCount;
    }

    public void eatAnimal() {

        if (players == null) {
            JOptionPane.showMessageDialog(this, "No animals available to feed", "Invalid operation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFrame frame = new JFrame("Feed Animal");
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Feed Animal", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel selectAnimalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        TitledBorder selectAnimalBorder = BorderFactory.createTitledBorder("Select Animal");
        selectAnimalBorder.setTitleFont(new Font("Arial", Font.BOLD, 14));
        selectAnimalPanel.setBorder(selectAnimalBorder);

        JComboBox<String> animalComboBox = selectAnimalToAdd();
        selectAnimalPanel.add(animalComboBox);

        mainPanel.add(selectAnimalPanel);

        mainPanel.add(Box.createVerticalStrut(20));

        JPanel energyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        TitledBorder energyBorder = BorderFactory.createTitledBorder("Enter Energy Amount");
        energyBorder.setTitleFont(new Font("Arial", Font.BOLD, 14));
        energyPanel.setBorder(energyBorder);

        JFormattedTextField foodEnergyField = new JFormattedTextField(createNumberFormatter());
        foodEnergyField.setPreferredSize(new Dimension(150, 25));
        energyPanel.add(foodEnergyField);

        mainPanel.add(energyPanel);

        frame.add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton feedButton = new JButton("Feed");
        JButton cancelButton = new JButton("Cancel");
        frame.getRootPane().setDefaultButton(feedButton);

        feedButton.addActionListener(e -> {
            String selectedAnimal = (String) animalComboBox.getSelectedItem();
            String energyInput = foodEnergyField.getText();

            if (selectedAnimal == null || selectedAnimal.equals("Select Animal") || energyInput.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please select an animal and enter a valid energy amount", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int energyAmount = Integer.parseInt(energyInput);

                try {
                    increaseEnergy(frame, energyInput,selectedAnimal);
                } catch (IllegalStateException exception) {
                    JOptionPane.showMessageDialog(frame, "An unexpected error occurred: " + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

                frame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number for the energy amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> frame.dispose());

        buttonPanel.add(feedButton);
        buttonPanel.add(cancelButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void setPanels(CompetitionPanel[] panels) {
        this.panels = panels;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void setPlayers(Animal[] players) {
        this.players = players;
    }

    public void setSleep() {
        JFrame frame = new JFrame("Sleep time Animal");
        frame.setSize(500, 200);
        frame.setLayout(new BorderLayout());

        JLabel foodEnergyLabel = new JLabel("Enter the time that the animal will sleep after a competition\n");
        foodEnergyLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        JFormattedTextField sleepTimeField = new JFormattedTextField(createNumberFormatter1());
        sleepTimeField.setColumns(20);

        JButton updateSleeptime = new JButton("Update sleep time");
        updateSleeptime.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        fieldsPanel.add(foodEnergyLabel, gbc);

        // Add text field
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        fieldsPanel.add(sleepTimeField, gbc);

        // Add button
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        fieldsPanel.add(updateSleeptime, gbc);

        // Add panel to frame
        frame.add(fieldsPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Define the action to be performed
        ActionListener updateAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int time = Integer.parseInt(sleepTimeField.getText());
                    if (time > 0) {
                        SleepTime sleepTime = SleepTime.getInstance();
                        sleepTime.setTime(time);
                        System.out.println(time);
                        System.out.println(SleepTime.getInstance().getTime());
                        JOptionPane.showMessageDialog(frame, "Time updated successfully!");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter a positive integer greater than 0.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid integer.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        // Attach the action to both the button and the text field
        updateSleeptime.addActionListener(updateAction);
        sleepTimeField.addActionListener(updateAction);

        // Ensuring Enter key triggers the update action
        sleepTimeField.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "updateAction");
        sleepTimeField.getActionMap().put("updateAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAction.actionPerformed(e);
            }
        });

        System.out.println(SleepTime.getInstance().getTime());
    }

    private NumberFormatter createNumberFormatter1() {
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(1); // Ensure only positive integers greater than 0
        formatter.setAllowsInvalid(false); // Disallow invalid characters
        formatter.setCommitsOnValidEdit(true);
        return formatter;
    }

    public void updateLocation(int width, int height){
        if (players != null) {
            for (Animal animal : players) {
                animal.setLocation(width, height);
            }
        }
    }

    public void printZooPanels(){
        System.out.println("--------------------printing panels and scores---------------");
        for (int i=0; i<panels.length; ++i)
        {
            System.out.println("********************score number " + (i+1) + "********************");
//            int len = panels[i].getTournament().getTournamentThread().getScores().getAll().size();
            System.out.println(panels[i].getTournament().getTournamentThread().getScores().getAll().toString());
            System.out.println("*******************************************************************");


        }
    }



    public void showScoresInfo(){
        if (panels == null) {
            JOptionPane.showMessageDialog(this, "No Groups available", "Invalid operation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFrame frame = new JFrame("Animals scores");
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Animals scores", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel selectGroupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        TitledBorder selectGroupBorder = BorderFactory.createTitledBorder("Select group");
        selectGroupBorder.setTitleFont(new Font("Arial", Font.BOLD, 14));
        selectGroupPanel.setBorder(selectGroupBorder);


        JComboBox<String> tournamentComboBox = selectTournamentToAdd();

        selectGroupPanel.add(tournamentComboBox);

        mainPanel.add(selectGroupPanel);

        mainPanel.add(Box.createVerticalStrut(20));


        frame.add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton ShowScoresButton = new JButton("Show Scores");
        JButton cancelButton = new JButton("Cancel");
        frame.getRootPane().setDefaultButton(ShowScoresButton);

        ShowScoresButton.addActionListener(e -> {

            String tournamentNumString = (String)tournamentComboBox.getSelectedItem();

            if (tournamentNumString == null || tournamentNumString.equals("Select Tournament")) {
                JOptionPane.showMessageDialog(frame, "Please select tournament", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int groupNumInt = 0;

            groupNumInt = ((String) tournamentComboBox.getSelectedItem()).charAt(11) - '0';

            scoresInfo(groupNumInt);
        });

        cancelButton.addActionListener(e -> frame.dispose());

        buttonPanel.add(ShowScoresButton);
        buttonPanel.add(cancelButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JComboBox<String> selectTournamentToAdd(){
        if (panels == null)
            return new JComboBox<>(new String[]{"No Tournament Are Available"});

        String[] TournamentNames = new String[panels.length + 1];
        TournamentNames[0] = "Select Tournament";

        int i = 1;
        for (CompetitionPanel panel : panels) {
            if (panel != null) {
                TournamentNames[i] = "Tournament " + (i);
                ++i;
            }
        }
        JComboBox<String> tournamentNamesComboBox = new JComboBox<>(TournamentNames);
        tournamentNamesComboBox.setPreferredSize(new Dimension(150, 25));

        return tournamentNamesComboBox;
    }

    public void scoresInfo(int tournamentNumber) {

        JFrame frame = new JFrame("tournament number " + tournamentNumber + " Scores");
        frame.setSize(450, 150);

        // Show a warning if no participants are available
        if (panels == null) {
            JOptionPane.showMessageDialog(frame, "No tournament yet", "Invalid operation", JOptionPane.WARNING_MESSAGE);
            return;
        }


        // Define the column names for the table
        String[] columnNames = {"Group Name", "Date"};


        Object[][] competitionTable = createTournamentTable(tournamentNumber);

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

    public Object[][] createTournamentTable(int tournamentNumber) {
        if (panels == null)
            return null;

        CompetitionPanel selectedPanel = panels[tournamentNumber - 1];

        // Get the scores for the selected group
        Scores scores = selectedPanel.getTournament().getTournamentThread().getScores();
        Map<String, Date> scoresMap = scores.getAll();
        int numOfScores = scoresMap.size();

        // Create a 2D array with the correct size
        Object[][] table = new Object[numOfScores][tournamentTableColumns];

        int groupsNumber = selectedPanel.getParticipates().length;

        System.out.println("num groups  = " + groupsNumber);
        System.out.println("num scores = " + scoresMap.size());

        int rowIndex = 0;


        for (Map.Entry<String, Date> entry : scoresMap.entrySet()) {

            table[rowIndex][0] = entry.getKey(); // Animal Name
            table[rowIndex][1] = entry.getValue(); // Date

            rowIndex++;

        }

        return table;
    }

    public int countAvailableAnimalsFromTypeAndRout(int competitionType, int competitionRout) {

        int count = 0;

        switch (competitionType) {
            case 1: //selects from Water animals
            {
                for (Animal animal : players) {
                    if (animal != null) {
                        if (animal.isAvailable())
                            if (animal.getCategory().equals("Water") || animal.getCategory().equals("Terrestrial+Water"))
                                if (animal.getCompetitionRoute() == competitionRout)
                                    ++count;
                    }
                }

                break;
            }
            case 2: //selects from Air animals
            {
                for (Animal animal : players) {
                    if (animal != null)
                        if (animal.isAvailable())
                            if (animal.getCategory().equals("Air")) {
                                if (animal.getCompetitionRoute() == competitionRout)
                                    ++count;
                            }
                }
                break;
            }
            case 3: //selects from Terrestrial animals
            {
                for (Animal animal : players) {
                    if (animal != null) {
                        if (animal.isAvailable())
                            if (animal.getCategory().equals("Terrestrial") || animal.getCategory().equals("Terrestrial+Water"))
                                ++count;

                    }
                }
                break;
            }
            default:
                System.out.println("Error accorded");
                break;
        }
        return count;
    }







}


