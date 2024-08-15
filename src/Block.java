import java.awt.Image;
public class Block {
    int x,y;
    int width,height;
    Image img;

    public Block(int x, int y, int width, int height, Image img){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.img = img;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
}
