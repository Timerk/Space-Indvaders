import javax.swing.JFrame;

public class MainFrame extends JFrame{
    int tileSize;
    int colums;
    int rows;
    int frameWidth,frameHeight;
    GamePanel gamePanel;

    public MainFrame(int tileSize, int colums, int rows, String userName){
        this.tileSize = tileSize;
        this.colums = colums;
        this.rows = rows;
        this.frameWidth = tileSize * colums;
        this.frameHeight = tileSize * rows; 

        this.setTitle("Space Invaders");
        this.setSize(frameWidth, frameHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.gamePanel = new GamePanel(this.tileSize, this.colums, this.rows, this.tileSize, userName);
        this.add(gamePanel);
        this.pack();
        this.setVisible(true);
    }
}
