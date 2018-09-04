/*
 * Author: Me
 * Date: 9/5/2018
 * Description: Instantiates and starts the Hangman game
 */

import java.awt.EventQueue;

public class Hangman {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HangmanController controller = new HangmanController();
					controller.startGame(); /* Starts the hangman game */
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		});
	}
}
