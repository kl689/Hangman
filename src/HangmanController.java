/*
 * Author: Me
 * Date: 9/5/2018
 * Description: "Translates interactions with the View into actions the Model 
 * 			will perform. In other words, the controller updates the model 
 * 			based on the events received."
 */

public class HangmanController {
	
	HangmanView v;
	
	/***************************************************************************
	 * Summary: Class constructor
	 * 
	 * Argument(s): N/A
	 * Returns: N/A
	 ***************************************************************************/
	public HangmanController(){
		
	}
	
	/***************************************************************************
	 * Summary: Method to start the game by instantiating the method
	 * 
	 * Argument(s): N/A
	 * Returns: N/A
	 ***************************************************************************/
	public void startGame(){
		v = new HangmanView();
	}
}
