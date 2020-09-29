game
====

This project implements the game of Breakout.

Name: Matt Bowman (mab181), Sam Lamba (sl562)

### Timeline

Start Date: 9/14

Finish Date: 9/28

Hours Spent: ~30

### Resources Used
JavaFX Basics: https://www.tutorialspoint.com/javafx/index.htm
JavaFX example: https://coursework.cs.duke.edu/compsci307_2020fall/lab_javafx
Creating Buttons: https://www.geeksforgeeks.org/javafx-button-with-examples/
Arcade Font Generator: http://arcade.photonstorm.com/

### Running the Program

Main class:
Game.java

Data files needed: 
Level data files are .txt files in data; they consist of a series of characters
separated by commas corresponding to block types.There are also several .png files
used for various images located in data.

Key/Mouse inputs:
The left and right arrows control the paddle. The only other core control is that the
ball will pause when it has been reset until the stage is clicked.

Cheat keys:
L: Increases the number of lives
S: Increases the paddle's speed
R: Resets the ball and paddle to their starting locations and speeds
D: Deletes the first (top leftmost) block
P: Increases the paddle's width
Z: Makes the ball a random color
1: Changes to the first level in the level sequence
2: Changes to the second level in the level sequence
3: Changes to the third level in the level sequence
SPACE: Pauses the game.

Known Bugs:
Someitmes the ball can bounce into a block or paddle if it hits it at the right angle.


Extra credit:
We added a store between levels to serve as a transition, as well as
a starting splash screen that tells the rules of the game.

### Notes/Assumptions
Adding levels or using new levels involves creating a new text file in data.
The format is simply a series of comma separated characters representing blocks,
where each line is a row of blocks. 0s represent a space, 1s are basic blocks,
B is for boss block and S is shield blocks.

To add new blocks, choose a new symbol to represent it. Add the class in the blocks
folder. If the block is a basic rectangle, it should extend basicBlock. if it uses
some other node or has more complicated features, it should just extend Abstract Block.
Once a new block is added, add its symbol to the conditional statement in Level's
createBlockOfCorrectType() method; once done, the block can be read in from files with
its chosen symbol.

To add a new powerup, add a new class in Powerups folder that extends powerup. If it
acts on some object other than the ball or paddle, that object will need to be
added to the doPowerup method in Powerup class. Once added, add the powerup to the
list of possibl powerups in AbstractBlock's spawnPowerup() method.

To add specify a new sequence of levels (assuming the level text files exist),
put the names of their text files in a list and call .setLevelList() on the Game, 
passing in the list.

To add a new key input (including cheat keys), add a new entry to the keyMap() by
calling .put(KeyCode, (game) -> FUNCTION) in initializeKeyMap().

On errors:
Errors caused by missing data files should cause no issues. There are tests checking for empty text files; in that case,
the level is simply considered beaten and the game proceeds to the next. In the case of msising image files, which is hardly possible since we don't
accept any image files as inputs, the game simply will not display anything in their place (splash screen, store) but will still function.



### Impressions
I think this was a very good project for emphasizing design and polymorphism in
particular. The project went well with what we were going over in lecture.
I do think that the basic for this project was a lot of work with not a lot of time
to do it, and I felt that the rest of the milestones were relatively smaller. It
may be better to rebalance the workload across the milestones so that te work
isn't frontloaded.
