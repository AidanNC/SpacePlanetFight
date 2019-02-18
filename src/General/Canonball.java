package General;

import sun.jvm.hotspot.memory.Space;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created by aidan on 12/4/17.
 */
public class Canonball extends Planet {
    double previousX;
    double previousY;
    Spaceship creator;
    boolean affectedByGravity;
    int indicatorRange;     //this is how long the targeting trail will be for this weapon

    public Canonball(double xLoc, double yLoc, double xV, double yV, Spaceship s){
        super( xLoc,  yLoc,  xV,  yV,  3,  40,  true, Color.BLACK);
        previousX = xLoc;
        previousY = yLoc;
        creator = s;
        timerFireTimeValue = 0.5;
        affectedByGravity = true;
        indicatorRange = 20;

    }

    @Override
    public void move(){
        previousX = xLoc;
        previousY = yLoc;
        super.move();
    }

    public boolean collideWithShip(Spaceship spaceship){

        if(spaceship == creator){
            return false;
        }
        Rectangle temp = new Rectangle(getDrawXLoc(),getDrawYLoc(),radius * 2, radius * 2);
        if(temp.intersects(spaceship.hitbox)){
            return true;
        }
        if(passedTrough(spaceship)){
            return true;
        }
        return false;


    }

    public boolean passedTrough(Spaceship ship){
        ///Rectangle r1 = new Rectangle(100, 100, 100, 100);
        Line2D l1 = new Line2D.Float((float)previousX, (float)previousY, (float)xLoc, (float)yLoc);
        //System.out.println("l1.intsects(r1) = " + l1.intersects(ih));
        return l1.intersects(ship.hitbox);
    }

    //returns true if c is between a and b
    public boolean isBetween(double a, double b, double c){
        if(c < b && c > a){
            return true;
        }
        if(c < a && c > b){
            return true;
        }
        return false;
    }


    public Canonball clone(){
        return new Canonball(this.xLoc, this.yLoc, this.xV, this.yV, this.creator);

    }

    @Override
    public String toString(){
        return "Canonball";
    }

    public int getIndicatorRange(){
        return indicatorRange;
    }

    @Override
    public void draw(Graphics g){
        g.setColor(color);
        // g.drawOval((int)p.xLoc - p.radius,(int)p.yLoc - p.radius,p.radius *2,p.radius *2);
        g.fillOval(getDrawXLoc(),getDrawYLoc(),radius *2,radius *2);

    }

}
