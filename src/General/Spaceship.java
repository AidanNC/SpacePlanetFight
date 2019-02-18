package General;

import sun.jvm.hotspot.memory.Space;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by aidan on 12/6/17.
 */
public class Spaceship {
    int xLoc;
    int yLoc;
    Rectangle hitbox;
    int health;
    boolean activeShip;
    Color color;
    int travelRange;
    boolean canMove;
    ArrayList<Integer> inventory;

    public Spaceship(int xLoc, int yLoc, int width, int height, Color color){
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        hitbox = new Rectangle(xLoc, yLoc, width, height);
        health = 2;
        activeShip = false;
        this.color = color;
        travelRange = 100;
        canMove = true;

        inventory = new ArrayList<>();
        for(int i = 0; i < Info.MODETYPES; i ++){
            inventory.add(0);
        }
        System.out.println(inventory.size());

    }

    public void move(int x, int y, ArrayList<Planet> planets){

        boolean wouldHitPlanet = false;
        double distance = Info.distance(xLoc,yLoc, x,y);
        if(distance <= travelRange && canMove){

            hitbox.x = x;
            hitbox.y = y;
        }

        for(Planet p: planets){
            if(Info.rectangleCollidePlanet(hitbox, p)){
                wouldHitPlanet = true;
            }

            if(wouldHitPlanet){
                hitbox.x = xLoc;
                hitbox.y = yLoc;
                break;
            }
        }
        if(!wouldHitPlanet){
            xLoc = x;
            yLoc = y;
        }


    }

    public void loseHealth(){
        health --;
        if(health == 0){
            color = Color.black;
            canMove =false;
        }
    }

    public void draw(Graphics g){
        g.setColor(color);
        if(activeShip) {
            g.fillRect(xLoc, yLoc, hitbox.width, hitbox.height);
            g.drawOval(xLoc - travelRange, yLoc - travelRange, travelRange * 2, travelRange * 2);
        }
        else{
            g.drawRect(xLoc, yLoc, hitbox.width, hitbox.height);
        }
        g.drawString(health + "", xLoc-10, yLoc +20);
    }

    public void addAmmo(int typeOfProjectile){
        System.out.println(typeOfProjectile);
        int temp = inventory.get(typeOfProjectile).intValue();
        temp ++;

        inventory.remove(typeOfProjectile);
        inventory.add(typeOfProjectile, new Integer(temp));

    }

    public void removeAmmo(int typeOfProjectile){
        System.out.println(typeOfProjectile);
        int temp = inventory.get(typeOfProjectile).intValue();
        temp --;

        inventory.remove(typeOfProjectile);
        inventory.add(typeOfProjectile, new Integer(temp));

    }

    public int ammountOfAmmo(int typeOfProjectile){
        return inventory.get(typeOfProjectile);
    }

    public boolean canShoot(int typeOfProjectile){
        if(typeOfProjectile == 0){
            return true;
        }
        if(inventory.get(typeOfProjectile).intValue() > 0){
            return true;
        }
        return false;
    }


}
