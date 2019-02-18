package General;

import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by aidan on 12/19/17.
 */
public class PowerUpBox {
    public Rectangle hitBox;
    int xLoc;
    int yLoc;
    int powerUpType;
    boolean canGivePowerUp;
    public PowerUpBox(int x, int y){
        xLoc = x;
        yLoc = y;

        powerUpType = (int)(Math.random()* Info.MODETYPES);
    }

    public PowerUpBox(ArrayList<Planet> planets){
        canGivePowerUp = true;
        boolean foundASpot = false;

        while(!foundASpot) {
            xLoc = (int) (Math.random() * (Info.SCREENWIDTH + 1));
            yLoc = (int) (Math.random() * (Info.SCREENHEIGHT + 1));
            hitBox = new Rectangle(xLoc, yLoc, 20, 20);

            foundASpot = true;
            for (Planet p : planets) {
                if (Info.rectangleCollidePlanet(hitBox, p)) {
                    foundASpot = false;
                }
                if (!foundASpot) {
                    break;
                }
            }
        }

        powerUpType = (int)(Math.random()* (Info.MODETYPES -1)) +1;
    }

    public int givePowerUp(){
        if(canGivePowerUp) {
            canGivePowerUp = false;
            return powerUpType;
        }
        return 0;
    }

    public void draw(Graphics g){
        g.fillRect(xLoc, yLoc, hitBox.width, hitBox.height);
        //g.drawString(powerUpType + "", xLoc,yLoc);
    }
}
