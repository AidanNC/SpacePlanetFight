package General;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

/**
 * JFrame with buttons and JPanel canvas
 * Moving mouse over canvas will draw things
 * May include timer to update Canvas data and call repaint (if there are objects
 * moving automatically)
 */
public class GUI extends JFrame implements ActionListener, MouseMotionListener, KeyListener, MouseListener
{
    //declare instance variables
    //including declarations of Canvas, JPanels, JButtons, etc.
    JPanel pane;
    Canvas canvas;
    boolean shipSelected;
    boolean targetingSystemSelected;



    final int TIMER_DELAY = 25;
    Timer t;




    //constructor creates all user interface objects and places them,
    //using appropriate layoutmanagers
    public GUI(){
        shipSelected = false;
        targetingSystemSelected = false;
        pane = new JPanel(new BorderLayout());
        canvas = new Canvas();
        pane.add(canvas, BorderLayout.CENTER);
        addMouseMotionListener(this);
        addMouseListener(this);
        addActionListener(this);
        addKeyListener(this);

        add(canvas);

        t = new Timer(TIMER_DELAY ,this);
        t.setActionCommand("timerFired");
        t.start();

        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


    //along with any other needed methods, write actionPerformed, and mouseMoved and mouseDragged methods
    //some will call update and repaint on the Canvas object

    @Override
    public void mouseDragged(MouseEvent e) {
        canvas.mouseX = e.getX();
        canvas.mouseY = e.getY() -22;

    }

    @Override
    public void mouseMoved(MouseEvent e) {
            canvas.mouseX = e.getX();
            canvas.mouseY = e.getY() -22;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("timerFired"))
        {

            canvas.update();
            canvas.repaint();
        }

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_SPACE){
           deselectShip();
        }
        if(e.getKeyCode() == KeyEvent.VK_E){
            canvas.doProjectileAction();
            System.out.println("asdfasdfasdf");
        }
        if(e.getKeyCode() == KeyEvent.VK_N){
            canvas.nextMode();
        }

        if(e.getKeyCode() == KeyEvent.VK_UP){
            canvas.cRAccelerateForward = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            canvas.cRAccelerateBackwards = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            canvas.cRAccelerateLeftForwards = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            canvas.cRAccelerateRightForward = true;
        }


    }

    public void deselectShip(){
        shipSelected = false;       //we wan't to be able to select a different ship
        canvas.clearActiveShip();
        canvas.activeShip = null;
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP){
            canvas.cRAccelerateForward = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            canvas.cRAccelerateBackwards = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            canvas.cRAccelerateLeftForwards = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            canvas.cRAccelerateRightForward = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == 1) {
            if(shipSelected) {
                if(canvas.tS.inClickerRadius(e.getX(), e.getY()-22)){
                    canvas.tS.isSelected = true;
                }
                //canvas.addProjectile(e.getX(), e.getY() -22, canvas.activeShip);
                //System.out.println("no erros here");2
            }else{
                shipSelected = canvas.isOnShip(e.getX(), e.getY() -22);

                //System.out.println("lmasdof");
            }
        }else if(e.getButton() == 3){
            if(shipSelected) {

                canvas.activeShip.move(e.getX(), e.getY() - 22, canvas.sS.sS);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == 1){
            if(canvas.tS.isSelected) {
                canvas.tS.isSelected = false;
                if(Info.distance(e.getX(), e.getY() -22, canvas.activeShip.xLoc, canvas.activeShip.yLoc) > 10) {
                    if(canvas.activeShip.canShoot(canvas.mode)) {
                        //canvas.addProjectileTargetingSystem(e.getX(), e.getY() - 22, canvas.activeShip);
                        canvas.addProjectile(e.getX(), e.getY() - 22, canvas.activeShip);
                    }
                    canvas.tS.xLoc = canvas.activeShip.xLoc;
                    canvas.tS.yLoc = canvas.activeShip.yLoc;
                    deselectShip();
                }
            }

        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
