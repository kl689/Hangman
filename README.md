# Hangman Application

## Some features of the program include:
- Rules of the game just in case a player forgets how to play.
- A setting to change the background color.
- Difficulty setting for easy, medium, or hard words to guess.
- On-screen keyboard that takes clicking input.
- "New Game" functionality that allows a player to guess a new word (on the same difficulty setting they chose before) during a current game.
- "Play Again" functionality that will take the user back to the difficulty screen.

## To Run The Program:
Double-click on the executable jar file named "Hangman" in the src folder.

## Some Notes:
> ### Bugs 
> - Sometimes the last blank/dash doesn't disappear all the way when a letter appears in its place.
>
> ### Note on mvc pattern 
> - Tried to separate the program into model-view-controller components.
>
> ### Note on "New Game" menu item 
> - The effects of clicking this menu item will only be seen on the main game screen (the screen that displays the gallows, the hangman, the word blanks, and the on-screen keyboard).
>
> ### Note on location of font file 
> - Put the .ttf file in the src folder so that the jar file could find it.
>
> ### Note on losing the game 
> - When a player loses the game, the system will display the word that they were unable to guess.
>
> ### Note on "Play Again" button 
> - Will take take the player back to the difficulty screen to set the difficulty again.
>
> ### Note on coding issues 
> - Using getGraphics() and drawing straight into a hangmanPanel did not keep those changes when window was resized. This also had a part in the issue where, first, parts of the hangman was drawn and then when the user guesses their first correct letter, the previous hangman parts erased.

## Possible Future Improvements:
- For the "New Game" menu item, could ask the user to confirm their choice to make sure the user didn't accidentally click on it. Same with the "Quit" menu utem.
- Difficulty level could be a setting instead of being selected before each game.
- How to Play and Rules should have the same name and take you to the same pop-up screen.
- The hangman drawing could be changed to not resize as the main window frame is resized.
