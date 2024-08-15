import java.awt.Image;
public class AlienBlock extends Block{
    boolean alive;

    public AlienBlock(int x, int y, int width, int height, Image img){
        super(x, y, width, height, img);
        this.alive = true;
    }

    public void dead() {
        this.alive = false;
    }
}