# Game Design Final
### Names: Matt Bowman, Sam Lamba
## Team Roles and Responsibilities

 * Team Member #1: Matt
 Block, Level, Splash Screen, some core Game functionality

 * Team Member #2
 Ball, Paddle, Store, Powerups, some core game functionality


## Design goals
We wanted to make a simple game that was easy to make larger and add more complex, fun features to.

#### What Features are Easy to Add
Adding new levels, blocks, powerups, cheat keys/key inputs, and types of level should be very easy.

## High-level Design

#### Core Classes
The main game loop is called in Game.java. We have some boilerplate code that makes JavaFX work, but the meat of the class
is the step() method. This calls a few key update methods that update various game objects. Update methods are private method which call
other classes as needed.

The Physics Engine class is created at the game start and is used to check collision between game objects, primarily the ball and other objects. It is called most critically in updateBallAndPaddle() to see if the ball should bounce. We also update the list of blocks the physics engine keeps track of every time a major change in the block list of the level takes place, like when the level changes.

The Level class and its extensions keep track of the list of blocks active on the screen and perform modifications to them. Since blocks are closely
associated with powerups, Levels also add powerups to the screen. Extensions of the Abstract Level class have a special mechanic that makes
levels functionally different, like blocks randomly swapping places or gradually lowering.

The Abstract Block class represents any object that can be hit and broken by the Ball. It is provided so that the game is open to extension, but only
has one current implementation, the BasicBlock and its extensions. Basic Blocks have rectangular display objects and do not move (except on the DescendLevel). AbstractBlocks are kept track of by the Level and interact with the ball via the Physics Engine primarily.

The Powerup class represents a powerup. Powerups have a fixed chance of being droped from any AbstractBlock when it is broken. They are created at the previous location of the block, and are represented on screen by a circle. Powerups are updated in the updatePowerup method, which is called every step. All powerups in the game are kept track of in a list of current Powerups.

## Assumptions that Affect the Design
One assumption we made was that the image files we use to display the store and splash screen are present when the game runs.

It is also assumed that the format of the Level text files will be a comma-separated list of characters representing currently accepted blocks.
#### Features Affected by Assumptions
The store and splash screen always use the same images, and they don't have to take any user inputs. However, if the iamge files are unavailable for some reason, the display looks fairly bad. It does still function, however, and the game does not crash.

If there are bad or unknown characters in the text file, they will be rendered as blank spaces or "air". This includes empty text files; in the case of an empty text file, there will be no blocks in the level's block list, which will result in the level immediately being "won". Also, if a level is created from a text file that isn't found, it will also render an empty level.

## New Features HowTo

#### Easy to Add Features
Adding a new level involves creating a new text file in data.
The format is simply a series of comma separated characters representing blocks,
where each line is a row of blocks. 0s represent a space, 1s are basic blocks,
B is for boss block and S is shield blocks.

Adding new level variants is also as simple as creating a new class that extends level and implementing
the doLevelMechanic() method that acts on the block list or some other attribute of the Level.

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

#### Other Features not yet Done
Instead of having splash screen/store display a static image, the code in their constructor could be changed to read text in from a file specified on their initialization as a JavaFX Text object rather than an ImageView. It seemed unlikely that we would change the basic rules of the game so that we needed a new rules screen, so we didn't make this a priority, and it would require a lot of effort to change.

