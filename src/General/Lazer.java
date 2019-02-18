package General;

/**
 * Created by aidan on 12/17/17.
 */
public class Lazer extends Canonball {

    public Lazer(double xLoc, double yLoc, double xV, double yV, Spaceship s){
        super(xLoc, yLoc, xV, yV, s);
        affectedByGravity = false;
    }

    @Override
    public void updateAcceleration(Planet p){
        //do nothing
    }

    @Override
    public Lazer clone(){
        return new Lazer(this.xLoc, this.yLoc, this.xV, this.yV, this.creator);
    }
}
