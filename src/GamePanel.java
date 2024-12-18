import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GamePanel extends JPanel implements ActionListener,KeyListener{
    int tileSize;
    int colums;
    int rows;
    int boardHeight, boardWidth;
    int shipVelocity;
    int alienVelocity;
    int bulletVelocity;
    int alienCount;
    int score;
    int alienAmount = 1;
    int alienRowAmount;
    String userName;
    boolean gameOver;

    Image alienCyanImg,alienMagentaImg,alienYellowImg,alienWhiteImg;
    Image shipImg;
    MainFrame mainFrame;
    SpaceInvadersMenu menuFrame;
    ArrayList<Image> alienImgList = new ArrayList<>();
    ArrayList<AlienBlock> aliens = new ArrayList<>();
    ArrayList<BulletBlock> bullets = new ArrayList<>();
    Font scoreFont = new Font("Arial", Font.BOLD, 20);

    Block shipBlock;
    AlienBlock alienBlock;
    int difficulty;

    Timer gameLoop;
    public GamePanel(int tileSize, int colums, int rows, int shipVelocity, String userName, MainFrame mainFrame, SpaceInvadersMenu menuFrame, int difficulty){
        this.tileSize = tileSize;
        this.rows = rows;
        this.colums = colums;
        this.boardHeight = tileSize * rows;
        this.boardWidth = tileSize * colums;
        this.shipVelocity = shipVelocity;
        this.difficulty = difficulty;
        this.alienVelocity = difficulty;
        this.bulletVelocity = 5;
        this.score = 0;
        this.alienRowAmount = (tileSize*2) / colums;
        this.userName = userName;
        this.mainFrame = mainFrame;
        this.menuFrame = menuFrame;

        this.shipImg = new ImageIcon(getClass().getResource("images/ship.png")).getImage();
        this.alienCyanImg = new ImageIcon(getClass().getResource("images/alien-cyan.png")).getImage();
        this.alienMagentaImg = new ImageIcon(getClass().getResource("images/alien-magenta.png")).getImage();
        this.alienYellowImg = new ImageIcon(getClass().getResource("images/alien-yellow.png")).getImage();
        this.alienWhiteImg = new ImageIcon(getClass().getResource("images/alien.png")).getImage();
        this.gameOver = false;
        alienImgList.add(alienCyanImg);
        alienImgList.add(alienMagentaImg);
        alienImgList.add(alienYellowImg);
        alienImgList.add(alienWhiteImg);

        this.setPreferredSize(new Dimension(boardWidth, boardHeight));
        this.setBackground(Color.BLACK);

        this.shipBlock = new Block(boardWidth/2 - tileSize , boardHeight - tileSize*2, tileSize*2, tileSize, shipImg);
        generateAliens(alienAmount);
        
        addKeyListener(this);
        setFocusable(true);
        
        this.gameLoop = new Timer(1000/60, this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.drawImage(shipBlock.img, shipBlock.x, shipBlock.y, shipBlock.width, shipBlock.height, null);

        g.setColor(Color.white);
        g.setFont(scoreFont);
        g.drawString(String.valueOf(this.score), tileSize/2, tileSize);

        for (AlienBlock alien : aliens) {
            if (alien.alive) {
                g.drawImage(alien.img, alien.x, alien.y, alien.width, alien.height, null);
            }
        }

        g.setColor(Color.white);
        for (BulletBlock bullet : bullets) {
            if (!bullet.used) {
                g.fillRect(bullet.x, bullet.y, bullet.width, bullet.height);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveBullets();
        moveAliens();
        if (alienCount == 0) {
            aliens.clear();
            bullets.clear();
            alienAmount += 2;
            generateAliens(alienAmount);
            alienVelocity = difficulty;
        }

        if (!gameOver) {
            repaint();
        }else{
            gameLoop.stop();
            saveScore();
            mainFrame.dispose();
            menuFrame.setVisible(true);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                if (shipBlock.x - shipVelocity >= 0) {
                    shipBlock.x -= shipVelocity; 
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (shipBlock.x + shipBlock.width + shipVelocity <= boardWidth) {
                    shipBlock.x += shipVelocity; 
                }
                break;
            case KeyEvent.VK_SPACE:
                bullets.add(new BulletBlock(shipBlock.x + shipBlock.width*15/32, shipBlock.y - tileSize + tileSize/3, tileSize/10, tileSize/2, null));
            default:
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void generateAliens(int amount){
        alienCount = amount;
        int x = 0;
        int y = tileSize;
        int aliensAdded = 0;
        int rows = (int) Math.ceil(amount/alienRowAmount);
        Random random = new Random();

        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j < alienRowAmount; j++) {
                if (aliensAdded < amount) {
                    aliens.add(new AlienBlock(x, y, tileSize*2, tileSize, alienImgList.get(random.nextInt(alienImgList.size()))));
                    aliensAdded++;
                    x += tileSize*2;
                }else{
                    break;
                }
            }
            x  = 0;
            y += tileSize;
        }
    }

    public void moveAliens(){
        boolean hitBoundary = false;

        for (AlienBlock alien : aliens) {
            if (alien.alive) {
                alien.x += alienVelocity;
                if (alien.x + alien.width >= boardWidth || alien.x <= 0) {
                    hitBoundary = true;
                }
            }
        }

        if (hitBoundary) {
            alienVelocity = -alienVelocity; 
            for (AlienBlock alien : aliens) {
                alien.y += alien.height;
            }
        }

        for (AlienBlock alien : aliens) {
            if (alien.alive && alien.y >= shipBlock.y) {
                this.gameOver = true;
            }
        }
    }

    public void saveScore(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/scores/scores.txt",true));

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

            StringBuilder formatedScore = new StringBuilder(String.valueOf(this.score)).append(";");
            formatedScore.append(this.userName).append(";");
            formatedScore.append(currentDateTime.format(formatter)).append(";");

            formatedScore.append(String.valueOf(this.difficulty));

            writer.write(formatedScore.toString());
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveBullets(){
        for (BulletBlock bullet : bullets) {
            bullet.y -= bulletVelocity;

            for (AlienBlock alien : aliens) {
                if (!bullet.used && alien.alive && checkHit(bullet, alien)) {
                    bullet.used = true;
                    alien.alive = false;
                    score++;
                    alienCount--;
                }
            }
        }
    }

    public boolean checkHit(Block a, Block b){
        return a.x < b.x + b.width &&
               a.x + a.width > b.x &&
               a.y < b.y + b.height &&
               a.y + a.height > b.y;
    }

    public void startGameLoop(){
        this.gameLoop.start();
    }
}
