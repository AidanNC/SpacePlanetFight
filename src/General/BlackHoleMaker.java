package General;

import java.awt.*;

/**
 * Created by aidan on 12/17/17.
 */
public class BlackHoleMaker extends Canonball {
    boolean canMakeBlackHole;
    public BlackHoleMaker(double xLoc, double yLoc, double xV, double yV, Spaceship s){
        super(xLoc,yLoc,xV,yV,s);
        canMakeBlackHole = true;
        //canMakeBlackHole = true;
    }


    public BlackHoleMaker clone(){
        return new BlackHoleMaker(this.xLoc, this.yLoc, this.xV, this.yV, this.creator);
    }

    public Planet makeBlackHole(){
        mobile = false;
        canMakeBlackHole = false;
        return new Planet(xLoc,yLoc,0,0, 10,50000, false, Color.black);

    }

    @Override
    public String toString() {
        if(canMakeBlackHole && mobile) {
            return "BlackHoleMaker";
        }else{return "Canonball";}
    }
}

