Flappy Bird

Dureti, Katherene, Ryan

This is a recreation of the game, Flappy Bird, which involves attempting to successfully have a bird move through pipes without hitting the pipes. The requirements for this game is having Java 17/21 installed. To run the program, a user must press the run button in the main FlappyBird class, then follow the instructions in the opened window by pressing space to play the game. Help was received from the preceptors, as well as the professor of COMP 127. References to the edu.macalester.graphics website were used to study methods for this code. The inspiration for this program was the official Flappy Bird game. The bird and background images from the official Flappy Bird game were used, the author of Flappy Bird being Dong Nguyen.
Here is the website for the background image: https://www.pinterest.com/pin/416794140487100613/ 
Here is the link to the direct background asset: https://i.pinimg.com/originals/b2/b0/84/b2b084ad6061dfe2122302266ea8af58.jpg 
Here is the website for the bird image: https://flappybird.io/.
Here is the direct link to the bird asset, which was aquired by going to the above website and using the sources tab of inspect elements to locate the bird.png in the img folder: https://flappybird.io/img/bird.png.

Known issues involve that occasionally the score counter malfunctions by continuously counting. This occurs when the bird collides and falls such that it's x position exactly overlapped with the pipe's (x + width) position. This is because the code is written such that the score increments if the bird's x position is equal to the pipe's (x + width) position. 
Rotation of the bird is another design limitation. It was initially planned for the program to be able to rotate the bird upon pressing space. Additionally, the program begins to lag after about the 10th time restarting the game by pressing spacebar.

Societal impacts involve the importance of considering any negative consequences of this game. Ethical considerations involve the people who may or may not be able to play this game. This game excludes people who cannot see the game, or cannot use the keyboard. There may be other unintended consequences of this game, as it lacks accessibility to people who would need assistive devices. The game would be oppressive in this way. 
