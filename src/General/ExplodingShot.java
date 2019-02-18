package General;

import java.util.ArrayList;

/**
 * Created by aidan on 12/17/17.
 */
public class ExplodingShot extends Canonball {
boolean canExplode;
    public ExplodingShot(double xLoc, double yLoc, double xV, double yV, Spaceship s){
        super(xLoc, yLoc, xV, yV, s);
        canExplode = true;
    }

    public ArrayList<Canonball> explode(){
        ArrayList<Canonball> explosions = new ArrayList<>();
        /*
        take sin of angle
        add that time 5 to the x speed
        add that times 5 to the y speed
         */

        double angle = Info.angle(xLoc, yLoc, previousX, previousY);
        System.out.println(angle);
        double angle1 = Math.sin(angle);
        double angle2 = Math.cos(angle);
        explosions.add(new Canonball(xLoc, yLoc, angle2 * 3 + xV, -angle1 * 3 + yV, creator));
        explosions.add(new Canonball(xLoc, yLoc, -angle2 * 3 + xV, angle1 * 3 + yV, creator));

        canExplode = false;
        //System.out.println(angle);
        return explosions;
    }

    @Override
    public String toString(){
        if(canExplode && mobile){
            return "ExplodingShot";
        }
        return super.toString();
    }

    public boolean canExplode(){
        return canExplode;
    }

}
