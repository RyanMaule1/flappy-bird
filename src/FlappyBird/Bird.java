package FlappyBird;


import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;

import edu.macalester.graphics.Image;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle;

public class Bird extends Image{
    private double ySpeed = 0;
    private double gravity = 900;
    private Double flapSpeed = -320.0;
    private final double hitRadius = 20;
    private boolean flap = true;
    private boolean gravityAffected = true;

    private double x;

        
    public Bird() {
        super( "game-icons/realbird.png");
        x = this.getX();
    }

    /** Adds the bird to the canvas */
    public void addToCanvas(CanvasWindow canvas) {
        canvas.add(this);
    }

    /** To be used in the main Flappy Bird class to determine that the bird is not being affected by gravity and not moving */
    public boolean noGravity() {
        return gravityAffected = false;
    }

    /** To be used in the main Flappy Bird class to determine that the bird is being affected by gravity/moving */
    public boolean yesGravity() {
        return gravityAffected = true;
    }

    /** If the bird is affected by gravity, then moves the bird and adjusts its position based on ySpeed, and also applies gravity to increase ySpeed */
    public void animate(double dt) {
        if (gravityAffected) {
            setY(getY() + ySpeed * dt);
            ySpeed += gravity * dt;
        }
    }

    /** Set ySpeed to “flap up” constant value */
    public void flap() {
        if (flap) {
            ySpeed = flapSpeed;
        }
    }

    /** Changes speed and gravity after checking for a collision with pipe */
    public boolean hits(PipeManager pipes) {
        for (Rectangle aPipe : pipes.getPipeList()) {
            if(hitsPipe(aPipe, pipes)) {
                flap = false;
                gravity = 10000;
                return true;
            }
        }
        return false;
    }

    /** Checks collision of pipes with calculated set of points in bird */
    private boolean hitsPipe(Rectangle aPipe, GraphicsGroup pipeGroup) {
        for(int i = 0; i < 360; i += 30) {
            Point testPoint = getCenter().add(new Point(hitRadius, 0).rotate(i));
            if(pointHitsPipe(testPoint, aPipe, pipeGroup)) {
                return true;
            }
        }
        return false;
    }

    /** Tests hit of pipes with given point */
    private boolean pointHitsPipe(Point point, Rectangle aPipe, GraphicsGroup pipeGroup) {
        Point pointInGroup = point.subtract(pipeGroup.getPosition());
        return aPipe.testHit(pointInGroup.getX(), pointInGroup.getY());
    }

}


    


