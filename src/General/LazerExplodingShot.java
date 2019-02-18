package General;

import java.util.ArrayList;

/**
 * Created by aidan on 12/25/17.
 */
public class LazerExplodingShot extends Canonball {

    public boolean canExplode;
    public LazerExplodingShot(double xLoc, double yLoc, double xV, double yV, Spaceship s) {
        super(xLoc, yLoc, xV, yV, s);
        canExplode = true;
    }
/*
    public ArrayList<Canonball> explodeThings(){
        canExplode = false;
         for(int i =0; i < 9; i ++){

         }
    }
    */

    @Override
    public LazerExplodingShot clone(){
        return new LazerExplodingShot(xLoc,yLoc,xV,yV,creator);
    }
}
