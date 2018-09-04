/*
 * Author: Me
 * Date: 9/5/2018
 * Description: "Represents the data/rules that govern access/updates to this data.
 * 			In other words, the model passes its data to the view for rendering."
 */

public class HangmanModel {
	//private String[] test = {"MISSISSIPPI"};
	private String[] easyWordsToGuess = {"TELEVISION", "ARRANGEMENT", "PHILADELPHIA", "AGGRESSIVE", "MATHEMATICS", "OCCASIONALLY", "NEIGHBORHOOD", "INDEPENDENT"};
	private String[] mediumWordsToGuess = {"SPECIES", "EXCHANGE", "SIMPLEST", "ALGEBRA", "HANGMAN", "SUMMER", "MISSISSIPPI", "HAPPILY"};
	private String[] hardWordsToGuess = {"SYNDROME", "WITCHCRAFT", "ZEPHYR", "KIWIFRUIT", "JAWBREAKER", "KEYHOLE", "GLOWWORM", "JIUJITSU", "NUMBSKULL", "JAZZ"};
	public String wordToGuess = ""; /* Stores word chosen for the user to guess */
	
	/***************************************************************************
	 * Summary: Class constructor
	 * 
	 * Argument(s): N/A
	 * Returns: N/A
	 ***************************************************************************/
	public HangmanModel(){
		
	}
	
	/***************************************************************************
	 * Summary: Method to pick a random word for the player to guess
	 * 
	 * Argument(s): 
	 * 		@param difficulty - is a string that holds level of the difficulty
	 * 							the player chose to play on 
	 * Returns: a string that this method randomly chose for the player to guess
	 **************************************************************************/
	public String getWord(String difficulty){
		if(difficulty.equals("easy")) {
			//wordToGuess = test[(int) (Math.random() * test.length)];
			wordToGuess = easyWordsToGuess[(int) (Math.random() * easyWordsToGuess.length)];
		}
		else if(difficulty.equals("medium")) {
			wordToGuess = mediumWordsToGuess[(int) (Math.random() * mediumWordsToGuess.length)];
		}
		else if(difficulty.equals("hard")) {
			wordToGuess = hardWordsToGuess[(int) (Math.random() * hardWordsToGuess.length)];;
		}
		
		return wordToGuess;
	}
}
