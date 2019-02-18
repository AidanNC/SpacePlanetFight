package General;

import java.awt.*;

/**
 * Created by aidan on 2/25/18.
 */
public class ImprovedControlRocket {
    double xLoc;
    double yLoc;
    double speed;
    double angle;
    int radius;

    public ImprovedControlRocket(int x, int y, int speed, int angle){
        xLoc = x;
        yLoc = y;
        this.speed = speed;
        this.angle = angle;
        radius = 10;
    }

    public void move() {

        yLoc += Math.sin(angle) * speed;
        xLoc += Math.cos(angle) * speed;
    }

    public void moveRight(){
        angle += 0.05;
    }

    public void moveLeft(){
        angle -= 0.05;
    }
    public void draw(Graphics g){
        g.setColor(new Color(255, 6, 215));
        g.drawOval((int)(xLoc - radius), (int)(yLoc - radius), radius * 2, radius * 2);
        //g.setColor(Color.RED);



    }
}
