package Graphics;

import Animals.AnimalThread;
import Animals.Dolphin;
import Competitions.Scores;
import Competitions.SleepTime;

import javax.swing.*;
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
public class CompetitionFrame extends JFrame implements Runnable{

    /**
     * The panel where the competition takes place.
     */
    private CompetitionPanel competitionPanel;

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

        competitionPanel = new CompetitionPanel();
        competitionPanel.setLayout(null);

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
        addCompetitionButton.addActionListener(e -> competitionPanel.addCompetitionNew());
        buttonsPanel.add(addCompetitionButton);

        // AddAnimal Button
        JButton addAnimalButton = new JButton("Add Animal");
        addAnimalButton.addActionListener(e -> competitionPanel.addAnimal());
        buttonsPanel.add(addAnimalButton);


        // Clear Button
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> competitionPanel.clearAnimals());
        buttonsPanel.add(clearButton);

        // Eat Button
        JButton eatButton = new JButton("Eat");
        eatButton.addActionListener(e -> competitionPanel.eatAnimal());
        buttonsPanel.add(eatButton);

        // Info Button
        JButton infoButton = new JButton("Info");
        infoButton.addActionListener(e -> competitionPanel.showInfo());
        buttonsPanel.add(infoButton);

        // Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        buttonsPanel.add(exitButton);

        add(buttonsPanel, BorderLayout.SOUTH);
        add(competitionPanel, BorderLayout.CENTER);
    }

    public void run() {
        setVisible(true);
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

    private static void testScores()
    {
        Scores scores = new Scores();
        System.out.printf(scores.toString());
        scores.add("Hodaya");
        System.out.printf(scores.toString());

    }

    private static void checks() {
//        SwingUtilities.invokeLater(() -> {
//            CompetitionFrame frame = new CompetitionFrame();
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

}