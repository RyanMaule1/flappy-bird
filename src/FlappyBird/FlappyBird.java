package FlappyBird;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.awt.Color;

public class FlappyBird {

    private CanvasWindow canvas;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private boolean gameOver;

    private Image background = new Image("game-icons/background.jpg");
    private Bird bird = new Bird();
    private PipeManager pipes = new PipeManager(background.getImageWidth(), 0);
    private boolean gameStarted = false;
    private final static double FullArea = 640;
    private int score = 0;
    private int highScore;
    private GraphicsText guiScore = new GraphicsText(String.valueOf(score));
    private GraphicsText finalScore = new GraphicsText("Score: " + String.valueOf(score));
    private GraphicsText guiHighScore = new GraphicsText("High Score: " + String.valueOf(highScore));
    private Rectangle startButton = new Rectangle(0,0,200,100);
    private GraphicsText startText = new GraphicsText("Press Space to Start");
    private GraphicsText restartText = new GraphicsText("Press Space for New Game");

    public FlappyBird() {

        addElements();

        /* defaults to the bird and pipes not having movement */
        bird.noGravity();
        pipes.notMoving();
        
        /* Once space is pressed, the game has started */
        canvas.onKeyDown((e) -> {
            startGame();
            gameStarted = true;
            
        });

        /* on canvas space & click*/
        canvas.onKeyDown((e) -> bird.flap());
        canvas.onClick((e) -> bird.flap());

        /* animates score counter and bird movement */
        canvas.animate(() -> incrementScore());
        canvas.animate((dt) -> bird.animate(dt));
        
        /* Only moves pipes once the game has begun. Stops the game's movement if the bird has hit the pipe and ground */
        canvas.animate(() -> {
            if (pipes.getMoving()) {
                pipes.moveContinuously();
            }

            bird.hits(pipes);

            if (hitGround()) {
                //turned this into a loseGame function that calls scoreScreen and writes score data to a file
                loseGame();
                scoreScreen();
                restartText.setFontSize(20);
                restartText.setCenter(startButton.getCenter().getX(), startButton.getCenter().getY());
                startButton.setSize(300,100);
                startButton.setCenter(background.getImageWidth()/2, 200);
                canvas.add(startButton);
                canvas.add(restartText);
                bird.noGravity();
                pipes.notMoving();
            } 
        }); 
    }

    /** Creates a canvas and adds all the separate starting Flappy Bird elements onto this canvas */
    public void addElements() {
        /* adds canvas elements */
        canvas = new CanvasWindow("Flappy Bird", background.getImageWidth(), background.getImageHeight());
        canvas.add(background);
        /* adds pipes elements */
        pipes.setX(background.getImageWidth());
        canvas.add(pipes);
        /* adds bird elements */
        bird.setCenter(background.getImageWidth()/2, background.getImageHeight()/2);
        bird.setScale(0.7);
        bird.addToCanvas(canvas);
        /* adds score elements */
        guiScore.setFontSize(30);
        guiScore.setFillColor(Color.WHITE);
        guiScore.setCenter(new Point(background.getWidth() / 2, 50));
        canvas.add(guiScore);
        /* adds start  elements */
        startButton.setCenter(background.getImageWidth()/2, 200);
        startButton.setFillColor(Color.ORANGE);
        canvas.add(startButton);
        startText.setFontSize(20);
        startText.setCenter(startButton.getCenter().getX(), startButton.getCenter().getY());
        canvas.add(startText);
    }

    /** Ensures the pipes and bird can only begin moving once this method is called. Also removes the start button only once, when the game has not started, to avoid errors */
    public void startGame() {
        if (!gameStarted) {
            canvas.remove(startButton);
            canvas.remove(startText); 
        } 
        pipes.yesMoving();
        bird.yesGravity();
    }

    /** If the bird successfully moves through the pipe, then the score counter will increment */
    public void incrementScore() {
        if (pipes.throughPipe(bird)) {
            score++; 
            guiScore.setText(String.valueOf(score));
        } 
    }

    /** If the bird ever falls below the 'ground', then the bird will maintain a fixed position on the ground*/
    public boolean hitGround() {
        if (bird.getY() + bird.getHeight() >= FullArea) {
            bird.setPosition(bird.getX(), FullArea-bird.getHeight());
            bird.setRotation(90);
            return true;
        }
        return false;
    }

    /** A score screen is used when the game has ended */
    public void scoreScreen() {
        Rectangle rectangle = new Rectangle(new Point(0,0), new Point(150,150));
        rectangle.setCenter(new Point(canvas.getWidth() / 2, canvas.getHeight() / 2));
        rectangle.setFillColor(Color.lightGray);

        finalScore.setText("Score: " + String.valueOf(score));

        finalScore.setPosition(new Point(canvas.getWidth() / 2 - 40, canvas.getHeight() / 2 - 40));
        guiHighScore.setPosition(new Point(canvas.getWidth() / 2 - 40, canvas.getHeight() / 2 + 50 - 40));

        canvas.add(rectangle);
        canvas.add(finalScore);
        canvas.add(guiHighScore);
        
        pipes.notMoving();
    }

    /** Gets highest score from previous scores and updates the high score accordingly */
    public void saveScoreToFile() {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter("scores.txt", true));
            bufferedReader = new BufferedReader(new FileReader("scores.txt"));
            int i;
            // get highest score from previous scores
            while ((i = bufferedReader.read()) != -1) {
                if (i > highScore) {
                    highScore = i;
                }
            }
            bufferedReader.close();
            // write new score
            bufferedWriter.write(score);
            bufferedWriter.close();
            // if score is better then previous scores set it to high score
            if (highScore < score) {
                highScore = score;
            }
            guiHighScore.setText("High Score: " + String.valueOf(highScore));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void loseGame() {
        if(gameOver){
            return;
        }
        gameOver = true;
        saveScoreToFile();
        scoreScreen();
        canvas.draw();
        canvas.onKeyDown((e) -> {
        canvas.closeWindow();
        new FlappyBird();
        });
    }

    public static void main(String[] args){
       new FlappyBird();
       
    }
}


