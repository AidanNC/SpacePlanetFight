package General;

/**
 * Created by aidan on 12/18/17.
 */
public class SniperCanonball extends Canonball {
    public SniperCanonball(double xLoc, double yLoc, double xV, double yV, Spaceship s){
        super(xLoc,yLoc,xV,yV,s);
        indicatorRange = 200;
    }

    @Override
    public SniperCanonball clone(){
        return new SniperCanonball(xLoc,yLoc,xV,yV,creator);
    }
}
