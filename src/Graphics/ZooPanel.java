package Graphics;

import Animals.Animal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

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
    private static final int columns = 8;

    public ZooPanel(){
        loadImage("src/Images/competitionBackground.png");

        panels = null;
        players = null;

    }
    /**
     * //     * Opens a dialog to add a new animal to the competition.
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
    public void showInfo() {

        JFrame frame = new JFrame("Participate Animals Information");
        frame.setSize(900, 200);

        // Show a warning if no participants are available
        if (players == null) {
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
        if (players == null)
            return null;

        int playersLen = players.length;

        Object[][] table = new Object[playersLen][columns];

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
    public void eatAnimal() {//Todo new function............

        JFrame frame = new JFrame("Feed Animal");
        frame.setSize(500, 200);
        frame.setLayout(new BorderLayout());


        if (players == null) {
            JOptionPane.showMessageDialog(frame, "No participate yet", "Invalid operation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        else {

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
    private void increaseEnergy(JFrame frame, String energy) throws IllegalStateException {

        try {
            if (players == null) {
                throw new IllegalStateException("Participants array is null.");
            }
            int participatesLen = players.length;
            if (participatesLen == 0) {
                throw new IllegalStateException("Participants array is empty.");
            } else {

                try {

                    int energyValue = Integer.parseInt(energy);

                    int index = participatesLen - 1;
                    while (players[index].isAvailable() && index > 0)
                        --index;
                    if (players[0].isAvailable() && index == 0)
                        throw new IllegalStateException("Participants array is empty.");


                    if (players[index].eat(energyValue)) {
                        JOptionPane.showMessageDialog(frame, "Animal was fed successfully.\ncurrent " +
                                        players[index].getType() + " energy: " + players[index].getCurrentEnergy(),
                                players[index].getType() + " Feeding", JOptionPane.INFORMATION_MESSAGE);


//                        startMove();  //make the animal start moving on screen


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

    public Animal findAnimal(String animalName){
        for (int i=0; i<players.length; ++i)
            if(players[i].getAnimalName().equals(animalName))
                return players[i];
        return null;
    }

    public void deleteMe(){
//        if (tournaments )
        int len = panels.length;
        int animalLen;
        int groupLen;
        System.out.println(len);
        for (int i=0; i<len; ++i)
        {
            System.out.println("tournament number: " + (i+1));
            groupLen = panels[i].participates.length;
            System.out.println(groupLen);

            for(int k=0; k<groupLen; ++k)
            {
                System.out.println("group number: " + (k+1));
                animalLen = panels[i].participates[k].length;
                System.out.println(animalLen);

                for (int j = 0; j< animalLen; ++j)
//                for (Animal animal : tournaments[i].participates[k])
                {
                    System.out.println(panels[i].participates[k][j].toString());
                }
            }
        }
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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        System.out.println("im in zoooooooooo");

        if (players != null) {
            for (Animal animal : players) {
                if (animal != null) {
                    if (!(animal.isAvailable()))
                        animal.drawObject(g);
                }
            }
        }
        if (panels != null) {
            for (CompetitionPanel panel : panels) {
                if (panel != null) {
                    panel.repaint();
                    System.out.println("im in zoooooooooo bifnocooooooooo");
                }
            }
        }
    }
}
