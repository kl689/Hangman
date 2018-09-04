# Hangman
For a GUI class

To Run The Program:
Double-click on the executable jar file named "Hangman" in the src folder.

Some Notes:
Bugs - Sometimes the last blank/dash doesn't disappear all the way when a letter appears in its place.

Note on mvc pattern - Tried to separate the program into model-view-controller components.

Note on "New Game" menu item - The effects of clicking this menu item will only be seen on the main game screen (the screen that displays the gallows, the hangman, the word blanks, and the on-screen keyboard).

Note on location of font file - Put the .ttf file in the src folder so that the jar file could find it.

Note on losing the game - When a player loses the game, the system will display the word that they were unable to guess.

Note on "Play Again" button - Will take take the player back to the difficulty screen to set the difficulty again.

Note on coding issues - Using getGraphics() and drawing straight into a hangmanPanel did not keep those changes when window was resized. This also had a part in the issue where, first, parts of the hangman was drawn and then when the user guesses their first correct letter, the previous hangman parts erased.