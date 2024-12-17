import javax.swing.*;
import java.awt.event.*;

public class MainFrame extends JFrame{
    int tileSize;
    int colums;
    int rows;
    int frameWidth,frameHeight;
    GamePanel gamePanel;
    SpaceInvadersMenu menuFrame;
    String userName;
    int difficulty;

    public MainFrame(int tileSize, int colums, int rows, String userName, SpaceInvadersMenu menuFrame, int difficulty){
        this.tileSize = tileSize;
        this.colums = colums;
        this.rows = rows;
        this.frameWidth = tileSize * colums;
        this.frameHeight = tileSize * rows; 
        this.menuFrame = menuFrame;
        this.userName = userName;
        this.difficulty = difficulty;

        this.setTitle("Space Invaders");
        this.setSize(frameWidth, frameHeight);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.gamePanel = new GamePanel(this.tileSize, this.colums, this.rows, this.tileSize, this.userName, this, menuFrame, this.difficulty);
        this.add(gamePanel);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stopGame();
                makeMenuVisible();
            }
        });
        this.pack();
    }

    public void startGame(){
        this.gamePanel.startGameLoop();
    }

    private void stopGame(){
        this.gamePanel.gameLoop.stop();
    }

    private void makeMenuVisible(){
        this.menuFrame.setVisible(true);
    }
}
