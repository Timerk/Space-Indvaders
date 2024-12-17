import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class SpaceInvadersMenu extends JFrame {
    String userName;
    MainFrame mainFrame;
    BufferedReader scoreReader;
    String scoresPath = "src/scores/scores.txt";
    File scoresFile = new File(scoresPath);
    ArrayList<Score> scores;
    int difficulty;

    // Konstruktor für das Hauptmenü
    public SpaceInvadersMenu() {
        // Fenstereinstellungen
        setTitle("Space Invaders - Hauptmenü");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        this.difficulty = 4;

        // Haupt-Panel für das Menü
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLACK);

        // Titel
        JLabel titleLabel = new JLabel("SPACE INVADERS");
        titleLabel.setForeground(Color.GREEN);
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 50))); // Abstand
        panel.add(titleLabel);

        this.userName = "guest";

        if(scoresFile.exists()){
            try {
                this.scoreReader = new BufferedReader(new FileReader(scoresFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        this.scores = new ArrayList<>();

        // Button "Spiel starten"
        JButton startButton = new JButton("Spiel starten");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setMaximumSize(new Dimension(200, 40));
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Abstand
        panel.add(startButton);

        // Button "Bestenliste"
        JButton highScoreButton = new JButton("Bestenliste");
        highScoreButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        highScoreButton.setMaximumSize(new Dimension(200, 40));
        highScoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    readScores();
                    ScoresFrame scoresFrame = new ScoresFrame(scores);
                } catch (NumberFormatException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Abstand
        panel.add(highScoreButton);

        // Button "Benutzername eingeben"
        JButton usernameButton = new JButton("Benutzername eingeben");
        usernameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameButton.setMaximumSize(new Dimension(200, 40));
        usernameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog(null, "Benutzername eingeben:");
                if (username != null && !username.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Willkommen, " + username + "!");
                    setUserName(username);
                }
            }
        });
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Abstand
        panel.add(usernameButton);

        // Button "Schwierigkeit einstellen"
        JButton difficultyButton = new JButton("Schwierigkeit einstellen");
        difficultyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficultyButton.setMaximumSize(new Dimension(200, 40));
        difficultyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] options = {"Leicht", "Mittel", "Schwer"};
                int choice = JOptionPane.showOptionDialog(null, "Schwierigkeit wählen:",
                        "Schwierigkeit", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[1]);
                if (choice >= 0) {
                    switch (choice) {
                        case 0:
                            setDifficulty(3);
                            break;
                        case 1:
                            setDifficulty(4);
                            break;
                        case 2:
                            setDifficulty(6);
                            break;
                        default:
                            setDifficulty(4);
                            break;
                    }
                    JOptionPane.showMessageDialog(null, "Schwierigkeit auf " + options[choice] + " gesetzt.");
                }
            }
        });
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Abstand
        panel.add(difficultyButton);

        // Panel ins Fenster hinzufügen
        add(panel);

        this.setVisible(true);
    }

    private void setUserName(String userName){
        this.userName = userName;
    }

    public void startGame(){
        this.mainFrame = new MainFrame(32, 16, 16, this.userName, this, this.difficulty);  
        this.mainFrame.setVisible(true);
        this.setVisible(false);
        this.mainFrame.startGame();
    }

    public void readScores() throws NumberFormatException, IOException{
        if (scoresFile.exists() && scoreReader != null) {
            String line;
                while ((line = scoreReader.readLine()) != null) {
                    String[] parts = line.split(";");
                    scores.add(new Score(Integer.parseInt(parts[0]), parts[1], parts[2])); 
                }

            Collections.sort(scores, new Comparator<Score>() {
                @Override
                public int compare(Score score1, Score score2) {
                    return Integer.compare(score2.score, score1.score);
                }
            });
        }
    }

    private void setDifficulty(int difficulty){
        this.difficulty = difficulty;
    }
}

