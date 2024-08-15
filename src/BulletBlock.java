import java.awt.Image;
public class BulletBlock extends Block{
    boolean used;

    public BulletBlock(int x, int y, int width, int height, Image img){
        super(x, y, width, height, img);
        this.used = false;
    }
}
