package General;

import java.awt.*;

/**
 * Created by aidan on 12/25/17.
 */
public class ControlledRocket{
    double xLoc;
    double yLoc;
    double previousX;
    double previousY;
    double xV;      //          m/s
    double yV;         //       m/s
    double mass;
    int radius;         //who knows what units this is lmao, although in all honesty it is in whatever the scalevalue is
    double accelerationY;       //      m/s/s
    double accelerationX;       //      m/s/s
    double timerFireTimeValue;


    Color color;

    public ControlledRocket(int x, int y, int xV, int yV, int mass, int radius){
        xLoc = x;
        yLoc = y;
        this.xV = xV;
        this.yV = yV;
        this.mass = mass;
        this.radius = radius;
        timerFireTimeValue = 0.1;
    }

    public void move(){
        previousX = xLoc;
        previousY = yLoc;

        xLoc += (xV * timerFireTimeValue + 0.5 * accelerationX * timerFireTimeValue * timerFireTimeValue) ;
        yLoc += (yV * timerFireTimeValue + 0.5 * accelerationY * timerFireTimeValue * timerFireTimeValue) ;


    }

    public void updateVelocity(){
        //the second part of the if statement is checking to see if the acceleration is counteracting the velocity

        System.out.println(Math.sqrt(accelerationX * accelerationX + accelerationY * accelerationY));
        if(Math.sqrt(accelerationX * accelerationX + accelerationY * accelerationY) < 50 ) {

            xV += (accelerationX * timerFireTimeValue);

            yV += (accelerationY * timerFireTimeValue);
        }

        //|| accelerationX/ xV < 0 || accelerationY/yV < 0)

    }

    public void zeroAcceleration(){
        accelerationX = 0;
        accelerationY = 0;
    }

    //this will
    public void accelerateForward(){
      /*
       xV *= 1.1;
       yV *= 1.1;
       */
        double angle = Info.angle(xLoc,yLoc, previousX, previousY);
        //need to see what quadrant the angle is in and make adjustments if neessary because

        if(xV > 0 && yV > 0){
            angle += Math.PI;
        }
        else if(xV < 0 && yV > 0){
            angle += Math.PI;
        }
        int xDirection;
        if(previousX < xLoc){
             xDirection = 1;
        }
        else{
             xDirection = -1;
        }
        int yDirection;
        if(previousY < yLoc){
            yDirection = 1;
        }
        else{
            yDirection = -1;
        }
        double angle1 = Math.sin(angle);
        double angle2 = Math.cos(angle);

        accelerationX += xDirection * angle1 * 1.1;
        accelerationY += yDirection * angle2 * 1.1;



    }

    //this thing barely works
    /*
    public void accelerateBackwards(){

        double angle = Info.angle(xLoc,yLoc, previousX, previousY);
        //need to see what quadrant the angle is in and make adjustments if neessary because

        if(xV > 0 && yV > 0){
            angle += Math.PI;
        }
        else if(xV < 0 && yV > 0){
            angle += Math.PI;
        }
        int xDirection;
        if(previousX < xLoc){
            xDirection = -1;
        }
        else{
            xDirection = 1;
        }
        int yDirection;
        if(previousY < yLoc){
            yDirection = -1;
        }
        else{
            yDirection = 1;
        }


        double angle1 = Math.sin(angle);
        double angle2 = Math.cos(angle);
        accelerationX += xDirection *angle1 * 1.1;
        accelerationY += yDirection*angle2 * 1.1;


    }
    */
    public void accelerateBackwards(){
            double angle = Info.angle(xLoc,yLoc, previousX, previousY);
            //need to see what quadrant the angle is in and make adjustments if neessary because

            if(xV > 0 && yV > 0){
                angle += Math.PI;
            }
            else if(xV < 0 && yV > 0){
                angle += Math.PI;
            }
            int xDirection;
            if(previousX < xLoc){
                xDirection = 1;
            }
            else{
                xDirection = -1;
            }
            int yDirection;
            if(previousY < yLoc){
                yDirection = 1;
            }
            else{
                yDirection = -1;
            }
            double angle1 = Math.sin(angle);
            double angle2 = Math.cos(angle);

            accelerationX -= xDirection * angle1 * 1.1;
            accelerationY -= yDirection * angle2 * 1.1;


    }




    public void accelerateRightOfForward(){

        double angle = Info.angle(xLoc,yLoc, previousX, previousY);
        //need to see what quadrant the angle is in and make adjustments if neessary because

        if(xV < 0 && yV > 0){
            angle += Math.PI;
        }
        else if(xV < 0 && yV < 0){
            angle += Math.PI;
        }else if(previousX > xLoc && previousY > yLoc){
            angle += Math.PI;
        }//down to the left
        else if(previousX > xLoc && previousY < yLoc){
            angle += Math.PI;
        }


        //make it so that it is turning right
        angle -= 0.1;


        //now find the total velocity vector
        double velocityTotal = Math.sqrt(xV * xV + yV * yV) ;

        //now determine the new x and y velocity components

        //you have to multiply by negative because xV and yV are negative when going towards the top left of the screen
        double angle1 = Math.sin(angle);
        double angle2 = Math.cos(angle);


        xV = 1* angle1 * velocityTotal;
        yV = 1* angle2 * velocityTotal;

        double accelerationTotal = Math.sqrt(accelerationX * accelerationX + accelerationY * accelerationY);
        angle -= Math.PI/2;
        angle1 = Math.sin(angle);
        angle2 = Math.cos(angle);
        //accelerationX = 1 * angle1 * accelerationTotal;
        //accelerationY = 1 * angle2 * accelerationTotal;

    }

    public void accelerateLeftOfForward(){

        double angle = Info.angle(previousX,previousY, xLoc, yLoc);

        //need to see what quadrant the angle is in and make adjustments if neessary because

        if(xV < 0 && yV > 0){
            angle += Math.PI;
        }
        else if(xV < 0 && yV < 0){
            angle += Math.PI;
        }else if(previousX > xLoc && previousY > yLoc){
            angle += Math.PI;
        }//down to the left
        else if(previousX > xLoc && previousY < yLoc){
            angle += Math.PI;
        }

        //make it so that it is turning right
        angle += 0.1;



        //now find the total velocity vector
        double velocityTotal = Math.sqrt(xV * xV + yV * yV);


        //now determine the new x and y velocity components
        double angle1 = Math.sin(angle);
        double angle2 = Math.cos(angle);


        //you have to multiply by negative because xV and yV are negative when going towards the top left of the screen

        xV = 1* angle1 * velocityTotal;
        yV = 1* angle2 * velocityTotal;

        double accelerationTotal = Math.sqrt(accelerationX * accelerationX + accelerationY * accelerationY);

        angle += Math.PI/2;
        angle1 = Math.sin(angle);
        angle2 = Math.cos(angle);
        //accelerationX = 1 * angle1 * accelerationTotal;
        //accelerationY = 1 * angle2 * accelerationTotal;

    }

    public void draw(Graphics g){
        g.setColor(new Color(255, 6, 215));
        g.drawOval((int)(xLoc - radius), (int)(yLoc - radius), radius * 2, radius * 2);
        g.setColor(Color.RED);
        //g.drawLine((int)xLoc,(int)yLoc,(int)(xLoc + xV), (int)(yLoc + yV) );
        g.drawLine((int)xLoc,(int)yLoc,(int)(previousX), (int)(previousY) );

    }
}
