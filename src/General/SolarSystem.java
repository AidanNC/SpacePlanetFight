package General;

import java.util.ArrayList;

/**
 * Created by aidan on 11/16/17.
 */
public class SolarSystem {
    ArrayList<Planet> sS;
    ArrayList<Planet> remove;
    ArrayList<Planet> add;
    ArrayList<Integer> emptySpots;


    public SolarSystem(){
        sS = new ArrayList<>();
        remove = new ArrayList<>();
        add = new ArrayList<>();
        emptySpots = new ArrayList<>();
    }

    public void addPlanet(double xLoc, double yLoc, double xV, double yV, int radius, double mass, boolean mobile){
        sS.add(new Planet(xLoc, yLoc,xV, yV, radius, mass, mobile));
    }


    public void updateSolarSystem(){
        ArrayList<Planet> collided = new ArrayList<>();
        for(Planet p: sS){
            p.zeroAcceleration();
            /*
            for(Planet pp: sS){
                if(p!=pp){
                    p.updateAcceleration(pp);

                    boolean collisionsOn = true;

                    if(p.collides(pp) && !collided.contains(p) && !collided.contains(pp) && collisionsOn == true){

                        collided.add(p);
                        collided.add(pp);
                        remove.add(pp);
                        remove.add(p);
                        //emptySpots.add(sS.indexOf(p));
                        //emptySpots.add(sS.indexOf(pp));
                        System.out.println(p.xLoc + "," + p.yLoc);
                        System.out.println(pp.xLoc + "," + pp.yLoc);

                        add.add(p.merge(pp));
                    }


                }
            }
            */
        }


        for(Planet p: remove){
            sS.remove(p);
        }
        remove.clear();

        for (Planet p: add){
            sS.add(p);
        }
        add.clear();




        //System.out.println(sS.get(0));

        for(Planet p: sS){
            p.move();
            p.updateVelocity();
        }

    }

}
