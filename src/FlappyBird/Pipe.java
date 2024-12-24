package FlappyBird;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Rectangle;

public class Pipe extends Rectangle{
    private double x;
    private double y;
    private double width;
    private double height;
    
    /** Create a rectangle witht he given coordinates, width, and height */
    public Pipe(double x, double y,double width,double height) {
        super(x, y, width, height);
        this.setFillColor(Color.green);
        this.setStroked(false);
        this.setFilled(true);
    }
    
}