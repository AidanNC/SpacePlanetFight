package General;

/**
 * Created by aidan on 12/17/17.
 */
public class SpeedingUpCanonball extends Canonball {

    public SpeedingUpCanonball(double xLoc, double yLoc, double xV, double yV, Spaceship s){
        super(xLoc, yLoc, xV, yV, s);

    }

    @Override

    public void updateVelocity(){
        super.updateVelocity();
        xV *= 1.05;
        yV *= 1.05;
    }

    @Override
    public SpeedingUpCanonball clone(){
        return new SpeedingUpCanonball(this.xLoc, this.yLoc, this.xV, this.yV, this.creator);
    }

}
