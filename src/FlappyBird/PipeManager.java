package FlappyBird;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Rectangle;


public class PipeManager extends GraphicsGroup {
    private Random random = new Random();
    private Pipe pipe;
    private Pipe pipe2;
    private List<Rectangle> pipeList = new ArrayList<>();
    private final static double FullArea = 640;
    private final static double HoleSpacing = 130;
    private final static double pipeSpacing = 200;
    private final static double Width = 80;
    private final static double moveFactor = 4;
    private boolean isMoving = true;
    private double x;
    private double y;

    public PipeManager(double x, double y) {
        makePipes();
        this.x = x;
        this.y = y;
    }

    /** Makes the pipes using the Pipe class, with the calculations to randomize the top and bottom pipe combos, for 100 pipes. 
     * Adds these 100 combos to this graphicsGroup and pipelist instance */
    public void makePipes() {
        for (int i = 0 ; i<100 ; i++) {
            double r = random.nextDouble(0.5, 1.5);
            pipe = new Pipe(i*Width + i*pipeSpacing, 0, Width, r*(FullArea/2)-(HoleSpacing/2));
            
            pipe2 = new Pipe(i*Width + i*pipeSpacing, 
            r*(FullArea/2)+(HoleSpacing/2), 
            Width, 
            FullArea - r*(FullArea/2)-(HoleSpacing/2));
            
            pipeList.add(pipe);  
            pipeList.add(pipe2);  
            add(pipe);
            add(pipe2);
        }
    }

    /** To be used in the main Flappy Bird class to determine that the pipes are not moving */
    public void notMoving() {
        isMoving = false;
    }

    /** To be used in the main Flappy Bird class to determine that the pipes are moving */
    public void yesMoving() {
        isMoving = true;
    }

    /** Returns whether the pipes are moving or not */
    public boolean getMoving() {
        return isMoving;
    }

    
    /** This continously updates the x position so that this graphicsgroup of pipes always moves to the left. */
    public void moveContinuously() {
        if (isMoving) {
            x -= moveFactor;
            this.setPosition(x, y);     
        } 
    }

    /** Returns the graphics group Pipes x position */
    public double getPipeX() {
        return this.getX();
    }

    /** Returns a list of unmodifiable pipes made from this pipemanager*/
    public List<Rectangle> getPipeList() {
        return Collections.unmodifiableList(pipeList);
    }

    /** Checks if bird went through the pipe by checking if the bird's x position matches each pipe's (x + pipe width) position */
    public boolean throughPipe(Bird bird) {
        for (int i =0; i<=(pipeList.size())/2; i++) {
            if (bird.getX() == (this.getX() + Width + ((pipeSpacing+Width)*i))) {
            return true;
        }
    }
        return false;
    }

}
