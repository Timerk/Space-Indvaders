import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpaceInvadersMenu extends JFrame {
    String userName;

    // Konstruktor für das Hauptmenü
    public SpaceInvadersMenu() {
        // Fenstereinstellungen
        setTitle("Space Invaders - Hauptmenü");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

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

        // Button "Spiel starten"
        JButton startButton = new JButton("Spiel starten");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setMaximumSize(new Dimension(200, 40));
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Spiel wird gestartet...");
                // Hier könnte man das Spiel starten
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
                JOptionPane.showMessageDialog(null, "Bestenliste anzeigen...");
                // Hier könnte die Bestenliste angezeigt werden
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
}

