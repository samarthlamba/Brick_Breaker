# Game Plan
## NAMEs Sam Lamba && Matt Bowman


### Breakout Variation Ideas

### Interesting Existing Game Variations

 * Game 1
 Super Breakout
    * We liked the powerup idea in this version. It adds variety to the game and makes it interesting. 

 * Game 2
Brick Breaker Escape
    * We liked the idea of being able to purchase powerups and boosts as we progress in the game. 


#### Block Ideas

 * Block 1 (Basic Block): Hit once and it disappears

 * Block 2 (Shielded Block): Unbreakable when it is a certain color

 * Block 3 (Boss Block): Requires multiple hits to break -- might drop powerups. 


#### Power Up Ideas

 * Power Up 1: Paddle size increase - Increases paddle size temporarily

 * Power Up 2: Increases paddle speed - Increase the paddle speeed temporarily

 * Power Up 3: Slow down ball speed


#### Cheat Key Ideas

 * Cheat Key 1: "S": Speeds up the paddle

 * Cheat Key 2: "R": Resets everything

 * Cheat Key 3: " ": Pauses ball

 * Cheat Key 4: "M": Gives you money


#### Level Descriptions

 * Level 1
   * Block Configuration: Starter level - Simple multiple layers of blocks

   * Variation features: Some randomly allocated blocks will have powerups and money

 * Level 2
   * Block Configuration: Apple shaped  

   * Variation features: Boss blocks and shield blocks in the middle --> seeds of apple. 

 * Level 3
   * Block Configuration: Angry face emoticon.

   * Variation features: Blocks coming downwards --> get it soon


### Possible Classes

 * Class 1: Paddle Class
   * Purpose: Creates paddle controlled by player and determines attributes of it

   * Method: getX() - returns location of the paddle. changeColor() - changes color (powerup), setLength()- changes length of paddle for powerups. 

 * Class 2: Balls Class
   * Purpose: Creates ball and determines attributes of it

   * Method: getCenterX() - returns X coordinate of center location of ball, getCenterY() - returns Y coordinate of center location of ball, changeDirection() - changesDirection of ball.

 * Class 3: Block Class
   * Purpose: Creates blocks and determines attributes of it

   * Method: getTypeOfBlock() - returns the type of block (shield, boss, normal)

 * Class 4: Store Class
   * Purpose: Creates and controls the "store" and what is sold

   * Method: purchase() - purchases a product and modifies attributes of neccessary objects 

 * Class 5: Block Reader
   * Purpose: Reads text file and creates block layout from it

   * Method: getBlockList() - returns a list of all block objects created for a level
