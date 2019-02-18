package General;

import javafx.scene.shape.Circle;

import java.awt.*;

/**
 * Created by aidan on 12/4/17.
 */
public class Target {
    public int xLoc;
    public int yLoc;
    public int startY;
    public int endY;
    boolean towardsEnd;
    int width;
    int height;

    public Target(int x, int y, int start, int end){
        xLoc = x;
        yLoc = y;
        startY = start;
        endY = end;
        towardsEnd = true;
        width = 20;
        height = 60;
    }

    public void move(){
        //int move = 1;
        if(yLoc == endY){
            towardsEnd = false;
        }
        if(yLoc == startY){
            towardsEnd = true;
        }
        if(yLoc < endY && towardsEnd){
            yLoc += 1;
        }
        if(yLoc > startY && !towardsEnd){
            yLoc -= 1;
        }


    }

    public boolean collideWithPlanet(Planet p){
        //Circle temp = new Circle(p.getDrawXLoc(), p.getDrawYLoc(), p.radius);
        Rectangle temp = new Rectangle(p.getDrawXLoc(),p.getDrawYLoc(),p.radius * 2, p.radius * 2);
        Rectangle temp1 = new Rectangle(xLoc, yLoc, width, height);
        return temp.intersects(temp1);


    }


}
