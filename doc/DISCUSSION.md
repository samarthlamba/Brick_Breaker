## Lab Discussion
### Team 20
### Names Sam Lamba; Matt Bowman


### Issues in Current Code

#### Method or Class
 * Design issues: if statements for cheat code is poor design. It should be replaced with inheritance heirarchy. 

 * Design issue: magic numbers in the initialized texts which can be replaced with usage of vbox/stackpanes

#### Method or Class
 * Design issues: myScene should be refactored to be a local variable 

 * Design issue: change myPaddle/myBall name to something better


### Refactoring Plan

 * What are the code's biggest issues?
        * Long if statements for cheat code affects readability and has poor design. 

 * Which issues are easy to fix and which are hard?
        * The refactoring of names and myScenes are easier to fix. VBox/stackPane might be harder to fix due to the complexity associated with it. 

 * What are good ways to implement the changes "in place"?
        * We can use Intellij's built in tools for refactoring to make the changes in place rather than doing a lot of mini changes. 

### Refactoring Work

 * Issue chosen: Fix and Alternatives
    * refactor myPaddle/myBall name to something better. For example: myPaddle could become paddleNode and myBall could become ballNode. 


 * Issue chosen: Fix and Alternatives
    *  Magic numbers in the initialized texts which can be replaced with usage of vbox/stackpanes. We will use certain area locators to put the text (like bottom left)