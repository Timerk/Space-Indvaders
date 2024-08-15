import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener,KeyListener{
    int tileSize;
    int colums;
    int rows;
    int boardHeight, boardWidth;
    int shipVelocity;
    int alienVelocity;
    int bulletVelocity;

    Image alienCyanImg,alienMagentaImg,alienYellowImg,alienWhiteImg;
    Image shipImg;
    ArrayList<Image> alienImgList = new ArrayList<>();
    ArrayList<AlienBlock> aliens = new ArrayList<>();
    ArrayList<BulletBlock> bullets = new ArrayList<>();

    Block shipBlock;
    AlienBlock alienBlock;

    Timer gameLoop;
    public GamePanel(int tileSize, int colums, int rows, int shipVelocity){
        this.tileSize = tileSize;
        this.rows = rows;
        this.colums = colums;
        this.boardHeight = tileSize * rows;
        this.boardWidth = tileSize * colums;
        this.shipVelocity = shipVelocity;
        this.alienVelocity = tileSize/10;
        this.bulletVelocity = tileSize/10;

        this.shipImg = new ImageIcon(getClass().getResource("images/ship.png")).getImage();
        this.alienCyanImg = new ImageIcon(getClass().getResource("images/alien-cyan.png")).getImage();
        this.alienMagentaImg = new ImageIcon(getClass().getResource("images/alien-magenta.png")).getImage();
        this.alienYellowImg = new ImageIcon(getClass().getResource("images/alien-yellow.png")).getImage();
        this.alienWhiteImg = new ImageIcon(getClass().getResource("images/alien.png")).getImage();
        alienImgList.add(alienCyanImg);
        alienImgList.add(alienMagentaImg);
        alienImgList.add(alienYellowImg);
        alienImgList.add(alienWhiteImg);

        this.setPreferredSize(new Dimension(boardWidth, boardHeight));
        this.setBackground(Color.BLACK);

        this.shipBlock = new Block(boardWidth/2 - tileSize , boardHeight - tileSize*2, tileSize*2, tileSize, shipImg);
        generateAliens(10);
        
        addKeyListener(this);
        setFocusable(true);
        
        this.gameLoop = new Timer(1000/60, this);
        this.gameLoop.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.drawImage(shipBlock.img, shipBlock.x, shipBlock.y, shipBlock.width, shipBlock.height, null);

        for (AlienBlock alien : aliens) {
            if (alien.alive) {
                g.drawImage(alien.img, alien.x, alien.y, alien.width, alien.height, null);
            }
        }

        g.setColor(Color.white);
        for (BulletBlock bullet : bullets) {
            if (!bullet.used) {
                g.drawRect(bullet.x, bullet.y, bullet.width, bullet.height);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveBullets();
        moveAliens();
        if (!checkGameOver()) {
            repaint();
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
        int x = 0;
        int y = tileSize;
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            aliens.add(new AlienBlock(x, y, tileSize*2, tileSize, alienImgList.get(random.nextInt(alienImgList.size()))));
            x += tileSize*2;
            if (i == (amount/2) - 1) {
                x  = 0;
                y += tileSize;
            }
        }
    }

    public void moveAliens(){
        for (AlienBlock alien : aliens) {
            if (alien.alive) {
                alien.x += alienVelocity;
                if (alien.x + alien.width >= boardWidth || alien.x <= 0) {
                    this.alienVelocity *= -1;
                    alien.x += alienVelocity*2;

                    for (AlienBlock alienY : aliens) {
                        alienY.y += alien.height;
                    }
                }

            }
        }
    }

    public boolean checkGameOver(){
        for (AlienBlock alien : aliens) {
            if (alien.y >= shipBlock.y + tileSize) {
                return true;
            }
        }

        return false;
    }

    public void moveBullets(){
        for (BulletBlock bullet : bullets) {
            bullet.y -= bulletVelocity;

            for (AlienBlock alien : aliens) {
                if (!bullet.used && alien.alive && checkHit(alien, bullet)) {
                    bullet.used = true;
                    alien.alive = false;
                }
            }
        }
    }

    public boolean checkHit(AlienBlock alien, BulletBlock bullet){
        return alien.x < bullet.x + bullet.width &&
               alien.x + alien.width > bullet.x &&
               alien.y < bullet.y + bullet.height &&
               alien.y + alien.height > bullet.height;
    }
}
