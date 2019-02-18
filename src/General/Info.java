package General;

import java.awt.*;

/**
 * Created by aidan on 12/12/17.
 */
public class Info {
    public static int MODETYPES = 6;
    public static int SCREENWIDTH = 1400;
    public static int SCREENHEIGHT = 800;
    public Info(){

    }

    public static double distance(double x1, double y1, double x2, double y2){
        double distanceX = x1 - x2;
        double distanceY = y1 - y2;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        return distance;
    }

    //returns the angle of the hypotenuse between these two points
    public static double angle(double x1, double y1, double x2, double y2){
        double distanceX = x1 - x2;
        double distanceY = y1 - y2;
        return Math.atan(distanceX/distanceY);
    }

    public static int directionX(int x1, int x2){
        int distanceX = x1 - x2;
        int directionX = -1;
        if(distanceX == Math.abs(distanceX)){
            directionX = 1;
        }
        return directionX;
    }

    //returns 1 if y1 is greater than y2
    public static int directionY(int y1, int y2){
        int distanceY = y1 - y2;
        int directionY = -1;
        if(distanceY == Math.abs(distanceY)){
            directionY = 1;
        }
        return directionY;
    }


    public static boolean rectangleCollidePlanet(Rectangle r, Planet p){
        //first find the distance
        double distanceBetweenCenters = Math.abs(Info.distance((r.getX() + 0.5 * r.width), (r.getY() + 0.5 * r.height), (p.xLoc),(p.yLoc)));
        if(distanceBetweenCenters < p.radius){
            //check if the planets y loc is between the upper and lower side of the rectangle
            if(p.yLoc < r.getY() + r.getHeight() && p.yLoc > r.getY()){
                //if both the conditions above are true it means that the planet is colliding with the hitbox
                return true;
            }
            //now check if the planet is between the x locs which is the left and right sides of the rectangle
            if(p.xLoc < r.getX() + r.getWidth() && p.yLoc > r.getX()){
                //if both the conditions above are true it means that the planet is colliding with the hitbox
                return true;
            }
        }
        //now check to see if the corner of the rectangle is within the circle
        if(distance(r.getX(), r.getY(), p.xLoc, p.yLoc) < p.radius){
            return true;
        }
        if(distance(r.getX(), r.getY() + r.getHeight(), p.xLoc, p.yLoc) < p.radius){
            return true;
        }
        if(distance(r.getX() + r.getWidth(), r.getY(), p.xLoc, p.yLoc) < p.radius){
            return true;
        }
        if(distance(r.getX() + r.getWidth(), r.getY() + r.getHeight(), p.xLoc, p.yLoc) < p.radius){
            return true;
        }

        return false;
    }

}
