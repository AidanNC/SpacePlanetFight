package General;

import java.awt.*;

/**
 * Created by aidan on 11/16/17.
 */
public class Planet {
    double xLoc;
    double yLoc;
    double xV;      //          m/s
    double yV;         //       m/s
    double mass;
    int radius;         //who knows what units this is lmao, although in all honesty it is in whatever the scalevalue is
    double accelerationY;       //      m/s/s
    double accelerationX;       //      m/s/s
    double timerFireTimeValue;
    boolean mobile;
    double scaleValue;          //affects how much each pixel is worth, if it is 1 then each pixel is worth 1 meter
    Color color;

    public Planet(double xLoc, double yLoc, double xV, double yV, int radius, double mass, boolean mobile, Color color){
        this.color = color;
        //this is how many m a pixel is worth // this could use a lot of work it doesn't really funciton correctly rn
        scaleValue = 1;


        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.xV = xV;
        this.yV = yV;
        this.radius = radius;
        accelerationX = 0;
        accelerationY = 0;
        this.mass = mass;
        this.mobile = mobile;

        //this is how many seconds each timer fired is worth
        timerFireTimeValue= 0.1;


    }

    public Planet(double xLoc, double yLoc, double xV, double yV, int radius, double mass, boolean mobile){
        color = newColor();
        //this is how many km a pixel is worth // this could use a lot of work it doesn't really funciton correctly rn
        scaleValue = 1;


        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.xV = xV/scaleValue;
        this.yV = yV/scaleValue;
        this.radius = radius;
        accelerationX = 0;
        accelerationY = 0;
        this.mass = mass;
        this.mobile = mobile;

        //this is how many seconds each timer fired is worth
        timerFireTimeValue= 1;


    }
        // this give is a proportionate radius
    public Planet(double xLoc, double yLoc, double xV, double yV, double mass, boolean mobile){
        color = newColor();
        //this is how many km a pixel is worth // this could use a lot of work it doesn't really funciton correctly rn
         int radiusSizeScale = 1000;
        radius = (int)mass/ radiusSizeScale;
        scaleValue = 1;


        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.xV = xV/scaleValue;
        this.yV = yV/scaleValue;
        this.radius = radius;
        accelerationX = 0;
        accelerationY = 0;
        this.mass = mass;
        this.mobile = mobile;

        //this is how many seconds each timer fired is worth
        timerFireTimeValue= 1;


    }

    //this is for one second
    public void move(){
    if(mobile) {
        xLoc += (xV * timerFireTimeValue + 0.5 * accelerationX * timerFireTimeValue * timerFireTimeValue) ;
        yLoc += (yV * timerFireTimeValue + 0.5 * accelerationY * timerFireTimeValue * timerFireTimeValue) ;
    }

    }

    public void updateVelocity(){
        xV += (accelerationX * timerFireTimeValue)  ;
        yV += (accelerationY * timerFireTimeValue) ;
    }

    //this assumes that the acceleration equals 0
    public void updateAcceleration(Planet p){
        //turn this on if you want  shenenaigns
        double gravitationalConstant = 6.674e-11;
        gravitationalConstant =1;

        double distanceX = xLoc - p.xLoc;
        double distanceY = yLoc - p.yLoc;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        double currentPlanetAngle = Math.atan(distanceY / distanceX);

        double fGTrue = gravitationalConstant * mass * p.mass / (distance * distance);

        int directionX = 1;
        int directionY = 1;
        if(distanceX == Math.abs(distanceX)){
            directionX = -1;
        }
        if(distanceY == Math.abs(distanceY)){
            directionY = -1;
        }

        double fGY = Math.abs(fGTrue * Math.sin(currentPlanetAngle)) * directionY;
        double fGX = Math.abs(fGTrue * Math.cos(currentPlanetAngle)) * directionX;

        accelerationX += fGX / mass ;
        accelerationY += fGY / mass ;

    }

    public void zeroAcceleration(){
        accelerationY = 0;
        accelerationX = 0;
    }

    public boolean collides(Planet p){
        double distanceX = (xLoc - p.xLoc);
        double distanceY = (yLoc - p.yLoc);
        //double distanceX = (getDrawXLoc() - p.getDrawXLoc());
        //double distanceY = (getDrawYLoc() - p.getDrawYLoc());
        double distance = (Math.sqrt(distanceX * distanceX + distanceY * distanceY));

        if(distance < radius + p.radius){
            return true;
        }
        return false;
    }

    public Planet merge(Planet p){
        double tempX = (xLoc + p.xLoc) /2.0;
        double tempY = (yLoc + p.yLoc) /2.0;
        double tempVelocityX = (mass * xV + p.mass * p.xV) / (mass + p.mass);
        double tempVelocityY = (mass * yV + p.mass * p.yV) /(mass + p.mass) ;
        int tempRadius = (int)Math.sqrt(radius * radius + p.radius * p.radius);
        double tempMass = mass + p.mass;
        int tempR = (p.color.getRed() + color.getRed() ) /2;
        int tempG = (p.color.getGreen() + color.getGreen() ) /2;
        int tempB = (p.color.getBlue() + color.getBlue()) /2;
        Color tempColor = new Color(tempR, tempB, tempG);
        //return new Planet(tempX, tempY, tempVelocityX, tempVelocityY, tempRadius, tempMass, true);
        //return new Planet(tempX, tempY, tempVelocityX, tempVelocityY, tempRadius, tempMass, true);
        if(p.radius > radius){
            return new Planet(p.xLoc, p.yLoc, tempVelocityX, tempVelocityY, tempRadius, tempMass, true, tempColor);
        }
        return new Planet(tempX, tempY, tempVelocityX, tempVelocityY, tempRadius, tempMass, true, tempColor);



    }
    @Override
    public String toString(){
        return xLoc + " " + yLoc + " " + xV + " " +  yV + " " + radius + " " + mass;
    }

    public Color newColor(){

        int r = (int)(Math.random() * 255);
        int g = (int)(Math.random() * 255);
        int b = (int)(Math.random() * 255);
        return new Color(r,g,b);
    }

    public int getDrawXLoc(){
        return (int)(xLoc - radius);

    }
    public int getDrawYLoc(){
        return (int)(yLoc - radius);
    }

    public void draw(Graphics g){
        g.setColor(color);
        // g.drawOval((int)p.xLoc - p.radius,(int)p.yLoc - p.radius,p.radius *2,p.radius *2);
        g.drawOval(getDrawXLoc(),getDrawYLoc(),radius *2,radius *2);
        g.setColor(Color.black);
        g.drawOval((int)xLoc,(int)yLoc,1,1);

    }


}
