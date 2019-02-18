package General;

import sun.jvm.hotspot.memory.Space;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Canvas to display lines, circles, squares, images, text...
 */



public class Canvas extends JPanel
{

    ArrayList<ArrayList<Point>> trail;
    ArrayList<Color> colorTrails;
    ArrayList<PointColorPair> pointAndColor;
    ArrayList<Canonball> canonballs;
    ArrayList<Canonball> aimIndicator;
    ArrayList<PowerUpBox> powerUpBoxes;
    SolarSystem sS;
    int trailWidth;
    int trailCount;
    Target target;
    Spaceship activeShip;
    int mouseX;
    int mouseY;
    ArrayList<ArrayList<Spaceship>> allTeams;

    ArrayList<Spaceship> team1;
    ArrayList<Spaceship> team2;
    ArrayList<Spaceship> team3;

    double forceModifier;

    int mode;
    int numberOfModes;

    TargetingSystem tS;

    ControlledRocket cR;
    ImprovedControlRocket iCR;
    boolean cRAccelerateForward;
    boolean cRAccelerateBackwards;
    boolean cRAccelerateRightForward;
    boolean cRAccelerateLeftForwards;

    //constructor
    public Canvas(){

        cR = new ControlledRocket(400,600, 0,2,20,20);
        iCR = new ImprovedControlRocket(100, 100, 2, -1);

        cRAccelerateForward = false;
        cRAccelerateBackwards = false;
        cRAccelerateRightForward =false;
        cRAccelerateLeftForwards= false;


        forceModifier = 0.4;
        tS = new TargetingSystem(0,0);
        mode = 0;
        //have to change this is adding a new type of projectile
        numberOfModes = Info.MODETYPES;


        allTeams = new ArrayList<>();

        team1 = new ArrayList<>();
        team1.add(new Spaceship(500, 700, 20, 50, Color.blue));
        team1.add(new Spaceship(200, 700, 10, 20, Color.blue));

        team2 = new ArrayList<>();
        team2.add(new Spaceship(700, 100, 20, 50, Color.red));
        team2.add(new Spaceship(600, 100, 10, 20, Color.red));

        team3 = new ArrayList<>();
        Color greenTeam = new Color(68, 153, 75);
        team3.add(new Spaceship(100, 100, 20, 50, greenTeam));
        team3.add(new Spaceship(900, 200, 10, 20, greenTeam));

        allTeams.add(team1);
        allTeams.add(team2);
        //allTeams.add(team3);

        aimIndicator = new ArrayList<>();

        trailCount = 0;
        pointAndColor = new ArrayList<>();
        trailWidth = 2;

        setPreferredSize(new Dimension(Info.SCREENWIDTH,Info.SCREENHEIGHT));
        sS = new SolarSystem();


        randomPopulatePlanets(10);

        trail = new ArrayList<>();
        colorTrails = new ArrayList<>();
        canonballs = new ArrayList<>();
        powerUpBoxes = new ArrayList<>();
        for(int i = 0; i < 10; i ++){
            powerUpBoxes.add(new PowerUpBox(sS.sS));
        }

        for(Planet p: sS.sS){
            trail.add(new ArrayList<Point>());
            colorTrails.add(newColor());
        }

        target = new Target(800, 100, 100,300);
    }

    public void update(){
        iCR.move();

         if(cRAccelerateForward){
             cR.accelerateForward();
             //System.out.println("acelerate");
         }
         if(cRAccelerateBackwards){
             cR.accelerateBackwards();
             //System.out.println("acelerate");
         }
         if(cRAccelerateLeftForwards){
             cR.accelerateLeftOfForward();
             //System.out.println("acelerate");
             iCR.moveLeft();
         }
         if(cRAccelerateRightForward){
             cR.accelerateRightOfForward();
             //System.out.println("acelerate");
             iCR.moveRight();
         }

         //cR.zeroAcceleration();
         cR.updateVelocity();
         cR.move();

        for(Canonball c: canonballs){
            c.zeroAcceleration();
            if(c.affectedByGravity) {
                for (Planet p : sS.sS) {
                    c.updateAcceleration(p);
                }
            }
        }
        for(Canonball c: canonballs){
            c.move();
            c.updateVelocity();
            for(Planet p: sS.sS){
                if(c.collides(p)){
                    c.mobile = false;
                    c.color = Color.red;
                }
            }
            for(ArrayList<Spaceship> ar: allTeams){
                for(Spaceship s: ar){
                    if(c.collideWithShip(s) && c.mobile){
                        c.mobile = false;
                        c.color = Color.red;
                        s.loseHealth();
                    }
                }
            }
        }
        target.move();

        for(ArrayList<Spaceship> ar: allTeams){
            for(Spaceship s: ar){
                if(s.activeShip){
                    activeShip=s;
                }
            }
        }


        if(activeShip != null){
            aimIndicator.clear();
            Canonball temp = createProjectileTargetingSystem(mouseX, mouseY, activeShip, mode);
            System.out.println(" mode" + mode);
            for(int i = 0; i < temp.getIndicatorRange() * 2; i++){
                aimIndicator.add(temp);
                temp = temp.clone();


                for(int j = 0; j < 1; j ++){
                    temp.zeroAcceleration();
                    for(Planet p: sS.sS){
                        temp.updateAcceleration(p);
                    }
                    temp.move();
                    temp.updateVelocity();
                }

            }


        }



        if(tS.isSelected){
            if(Info.distance(mouseX,mouseY, activeShip.xLoc,activeShip.yLoc) < tS.dragRadius) {
                tS.xLoc = mouseX;
                tS.yLoc = mouseY;
            }else{
                double angle = Math.abs(Info.angle(activeShip.xLoc,activeShip.yLoc,mouseX,mouseY));

                //double distance = Info.distance(activeShip.xLoc,activeShip.yLoc,mouseX,mouseY);

                tS.xLoc = (int)(activeShip.xLoc + (Math.sin(angle) * tS.dragRadius * Info.directionX(mouseX, activeShip.xLoc)));
                tS.yLoc = (int)(activeShip.yLoc + (Math.cos(angle) * tS.dragRadius * Info.directionY(mouseY, activeShip.yLoc)));

            }

        }else if(activeShip !=null){
            tS = new TargetingSystem(activeShip.xLoc, activeShip.yLoc);
        }
        else if(activeShip == null){
            tS.xLoc = -20;
            tS.yLoc = -20;
        }

        //check if the powerupbox is on a ship and if it is give it to the ship
        for(PowerUpBox pUB : powerUpBoxes){
            for(ArrayList<Spaceship> ar: allTeams){
                for(Spaceship s: ar){
                    if(pUB.hitBox.intersects(s.hitbox)){
                        System.out.println("this ship just hiw a powerupbox!@!!");
                        s.addAmmo(pUB.givePowerUp());
                    }
                }
            }
        }

        //clean up the powerupboxes
        ArrayList<PowerUpBox> tempArray = new ArrayList<>();
        for(PowerUpBox p: powerUpBoxes){
            if(!p.canGivePowerUp){
                tempArray.add(p);
            }
        }
        for(PowerUpBox p: tempArray){
            powerUpBoxes.remove(p);
        }

    }

    public void paintComponent(Graphics g){

        //cR.draw(g);
        iCR.draw(g);

        for(int i = 0; i < Info.MODETYPES  ; i ++){
            if(i == 0 && activeShip != null){
                g.drawString(getSpecificMode(i) + "  ∞" , 20, 20 + 20 * i);
            }
            else if(activeShip != null) {
                g.drawString(getSpecificMode(i) + "  " + activeShip.ammountOfAmmo(i), 20, 20 + 20 * i);
            }
            else{
                g.drawString(getSpecificMode(i), 20, 20 + 20 * i);
            }
            if(i == mode){
                //int shift = modeToString().length() * 10;
                g.drawPolygon(new int[] { 15, 2,2}, new int[] {20 + i *20 - 5,  20 + i *20 -9, 20 + i*20 -1}, 3);
            }
        }

        g.setColor(Color.BLACK);
        //g.drawRect(target.xLoc, target.yLoc, 10,100);   //the target


        for(Planet p: sS.sS){
           p.draw(g);
        }


        g.setColor(Color.ORANGE);
        boolean exit = false;
        if(activeShip != null) {
            for (Canonball c : aimIndicator) {
                if (Info.distance(c.xLoc, c.yLoc, activeShip.xLoc, activeShip.yLoc) < (c.getIndicatorRange() * 10)) {
                    for (Planet p : sS.sS) {
                        if (c.collides(p)) {
                            g.setColor(Color.RED);
                            g.drawLine(c.getDrawXLoc() +5, c.getDrawYLoc() + 5, c.getDrawXLoc() - 5, c.getDrawYLoc() - 5);
                            g.drawLine(c.getDrawXLoc() + 5, c.getDrawYLoc()- 5, c.getDrawXLoc() - 5, c.getDrawYLoc() + 5);
                            exit = true;
                        }
                    }
                    if (!exit ){
                        g.fillOval(c.getDrawXLoc(), c.getDrawYLoc(), c.radius *2, c.radius*2);
                    }

                }
                else{exit = true;}
                if (exit ) {
                    break;
                }

            }
        }

        for(Canonball c: canonballs){
            c.draw(g);

        }


        for(ArrayList<Spaceship> ar: allTeams){
            for(Spaceship s: ar){

                s.draw(g);

            }
        }
        g.setColor(tS.color);
        g.fillOval(tS.xLoc - tS.radius,tS.yLoc - tS.radius, tS.radius * 2, tS.radius * 2);

        g.setColor(Color.ORANGE);
        for(PowerUpBox pUP: powerUpBoxes){
           pUP.draw(g);
        }



    }


    public Color newColor(){

        int r = (int)(Math.random() * 255);
        int g = (int)(Math.random() * 255);
        int b = (int)(Math.random() * 255);

        return new Color(new Color(r,g,b).getRGB()^new Color(255,255,255).getRGB());
    }



    public void addCanonball(double x, double y, Spaceship ship){

        canonballs.add(createCanonball( x,  y,  ship));

    }

    public void addExplodingShot(double x, double y, Spaceship ship){
        canonballs.add(createExplodingShot(x,y,ship));
    }

    public void addProjectile(double x, double y, Spaceship ship){
        canonballs.add(createProjectile(x,y,ship, mode));
    }

    public void addProjectileTargetingSystem(double x, double y, Spaceship s){
        canonballs.add(createProjectileTargetingSystem(x,y,s, mode));
    }


    public void activeShipMove(int x, int y){
        for(Planet p: sS.sS){

        }
    }

    public ExplodingShot createExplodingShot(double x, double y, Spaceship ship){

        double distanceX = x - ship.xLoc;
        double distanceY = y - ship.yLoc;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        double force = distance * forceModifier;

        double currentPlanetAngle = Math.atan(distanceY / distanceX);

        int directionX = -1;
        int directionY = -1;
        if(distanceX == Math.abs(distanceX)){
            directionX = 1;
        }
        if(distanceY == Math.abs(distanceY)){
            directionY = 1;
        }
        double fY = Math.abs(force * Math.sin(currentPlanetAngle)) * directionY;
        double fX = Math.abs(force * Math.cos(currentPlanetAngle)) * directionX;

        return new ExplodingShot(ship.xLoc, ship.yLoc,fX, fY, activeShip);
    }

    public Canonball createCanonball(double x, double y, Spaceship ship){
        double forceModifier = 0.1;
        double distanceX = x - ship.xLoc;
        double distanceY = y - ship.yLoc;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        double force = distance * forceModifier;

        double currentPlanetAngle = Math.atan(distanceY / distanceX);

        int directionX = -1;
        int directionY = -1;
        if(distanceX == Math.abs(distanceX)){
            directionX = 1;
        }
        if(distanceY == Math.abs(distanceY)){
            directionY = 1;
        }
        double fY = Math.abs(force * Math.sin(currentPlanetAngle)) * directionY;
        double fX = Math.abs(force * Math.cos(currentPlanetAngle)) * directionX;

        return new Canonball(ship.xLoc, ship.yLoc,fX, fY, activeShip);
    }

    public boolean isOnShip(int x, int y){

        for(ArrayList<Spaceship> ar: allTeams)
        {
            for(Spaceship s: ar){
                s.activeShip=false;
            }
            for(Spaceship s: ar) {
                if ((s.xLoc <= x && x <= s.xLoc + s.hitbox.width) && (s.yLoc <= y && y <= s.yLoc + s.hitbox.height)) {
                    s.activeShip = true;
                    return true;
                }
            }
        }

        return false;
    }

    public Spaceship getActiveShip(){
        Spaceship returner = null;

        for(ArrayList<Spaceship> ar: allTeams)
        {
            for(Spaceship s: ar){

                if(s.activeShip){
                    returner = s;
                }
            }
        }

        if(returner == null){
            System.out.println("asdfasdf");
        }

        return returner;

    }
    public void clearActiveShip(){
        for(ArrayList<Spaceship> ar: allTeams){
            for(Spaceship p: ar){
                p.activeShip = false;
            }
        }
    }

    public void randomPopulatePlanets(int numberOfPlanets){
        for(int i = 0; i < numberOfPlanets; i ++){
            int xLoc = (int)(Math.random() * 1400);
            int yLoc = (int)(Math.random() * 800);
            int mass = (int)(Math.random() * 100000) + 100;
            Planet temp = new Planet(xLoc, yLoc, 0, 0, mass, false);
            boolean willAdd = true;
            for(Planet p: sS.sS){
                if(temp.collides(p)){
                    //willAdd = false;
                }

            }
            for(ArrayList<Spaceship> ar : allTeams){
                for(Spaceship s: ar){
                    if(Info.rectangleCollidePlanet(s.hitbox, temp)){
                        willAdd = false;
                    }
                }
            }
            if(willAdd){
                sS.sS.add(temp);
            }
        }
    }

    public void addBlackHole(){
        for(Canonball c: canonballs){
            System.out.println(c.toString());
            if(c.toString().equals("BlackHoleMaker")){
                addBlackHole(((BlackHoleMaker)c));
                System.out.println("2222222");
                break;
            }
        }
    }
    public void addBlackHole(BlackHoleMaker bhm){
        sS.sS.add(bhm.makeBlackHole());
    }
    public void explodeAllShots(){
        for(Canonball c: canonballs){
            if(c.toString().equals("ExplodingShot")){
                explodeShot((ExplodingShot)c);
                break;
            }
        }
    }

    public void explodeShot(ExplodingShot es){
        canonballs.addAll(es.explode());
    }

    //modify all these methods when adding a new projectile and make sure to change the int at the beginning that is "number of modes"

    public void nextMode(){
        if(mode < numberOfModes -1){
            mode ++;
        }
        else{mode = 0;}
    }

    //add to this when adding projectile
    public Canonball createProjectileTargetingSystem(double x, double y, Spaceship ship, int type) {
        double forceModifier = 0.4;
        double distanceX = tS.xLoc - ship.xLoc;
        double distanceY = tS.yLoc - ship.yLoc;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        double force = distance * forceModifier;

        double currentPlanetAngle = Math.atan(distanceY / distanceX);

        int directionX = 1;
        int directionY = 1;
        if (distanceX == Math.abs(distanceX)) {
            directionX = -1;
        }
        if (distanceY == Math.abs(distanceY)) {
            directionY = -1;
        }
        double fY = Math.abs(force * Math.sin(currentPlanetAngle)) * directionY;
        double fX = Math.abs(force * Math.cos(currentPlanetAngle)) * directionX;

        if(activeShip.canShoot(type)) {
            switch (type) {
                case 0:
                    return new Canonball(ship.xLoc, ship.yLoc, fX, fY, activeShip);

                case 1:
                    return new ExplodingShot(ship.xLoc, ship.yLoc, fX, fY, activeShip);

                case 2:
                    return new Lazer(ship.xLoc, ship.yLoc, fX, fY, activeShip);
                case 3:
                    return new SpeedingUpCanonball(ship.xLoc, ship.yLoc, fX, fY, activeShip);
                case 4:
                    return new BlackHoleMaker(ship.xLoc, ship.yLoc, fX, fY, activeShip);
                case 5:
                    return new SniperCanonball(ship.xLoc, ship.yLoc, fX, fY, activeShip);

                default:
                    return null;


            }
        }
        else {return new Canonball(ship.xLoc, ship.yLoc, fX, fY, activeShip);}
    }


    //add new projectile edit this
    public Canonball createProjectile(double x, double y, Spaceship ship, int type){

        double distanceX = x - ship.xLoc;
        double distanceY = y - ship.yLoc;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        double force = distance * forceModifier;

        double currentPlanetAngle = Math.atan(distanceY / distanceX);

        int directionX = 1;
        int directionY = 1;
        if(distanceX == Math.abs(distanceX)){
            directionX = -1;
        }
        if(distanceY == Math.abs(distanceY)){
            directionY = -1;
        }
        double fY = Math.abs(force * Math.sin(currentPlanetAngle)) * directionY;
        double fX = Math.abs(force * Math.cos(currentPlanetAngle)) * directionX;


        activeShip.removeAmmo(type);

        switch (type){
            case 0:
                return new Canonball(ship.xLoc, ship.yLoc,fX, fY, activeShip);

            case 1:
                return new ExplodingShot(ship.xLoc, ship.yLoc,fX, fY, activeShip);

            case 2:
                return new Lazer(ship.xLoc, ship.yLoc,fX, fY, activeShip);
            case 3:
                return new SpeedingUpCanonball(ship.xLoc, ship.yLoc, fX, fY, activeShip);
            case 4:
                return new BlackHoleMaker(ship.xLoc, ship.yLoc, fX,fY,activeShip);
            case 5:
                return new SniperCanonball(ship.xLoc, ship.yLoc, fX,fY,activeShip);

            default:
                return null;


        }

    }

    //add to this when adding a new projectile
    public String modeToString(){
        switch (mode){
            case 0: return "Canonball";
            case 1: return "ExplodingShot";
            case 2: return "Lazer";
            case 3: return "SpeedingUpCanonball";
            case 4: return "BlackHoleMaker";
            case 5: return "SniperCanonball";
            default: return null;
        }
    }

    public String getSpecificMode(int a) {
        switch (a) {
            case 0:
                return "Canonball";
            case 1:
                return "ExplodingShot";
            case 2:
                return "Lazer";
            case 3:
                return "SpeedingUpCanonball";
            case 4:
                return "BlackHoleMaker";
            case 5:
                return "SniperCanonball";
            default:
                return null;
        }
    }
    public void doProjectileAction() {
        switch (mode) {
            case 0:
                break;
            case 1:
                explodeAllShots();
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                addBlackHole();
                break;

            default:
                break;

        }
    }

}
