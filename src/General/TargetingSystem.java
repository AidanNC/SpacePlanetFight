package General;

import java.awt.*;

/**
 * Created by aidan on 12/17/17.
 */
public class TargetingSystem {
    int xLoc;
    int yLoc;
    int radius;
    int dragRadius;         //how far the thing can be dragged away from the initial point
    Color color;
    boolean isSelected;


    public TargetingSystem(int x, int y){
        xLoc = x;
        yLoc = y;
        radius = 10;
        dragRadius = 100;
        color = new Color(2,200, 3);
        isSelected = false;
    }

    public boolean inClickerRadius(int x, int y){
        if(Info.distance(xLoc,yLoc,x,y) < radius){
            return true;
        }
        return false;
    }
}
