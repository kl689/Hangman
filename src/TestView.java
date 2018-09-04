import static org.junit.jupiter.api.Assertions.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

class TestView {
	
	HangmanView v = new HangmanView();
	
	@Test
	/* Testing setUpCustomFont() method */
	void setUpCustomFontTest() {
		String expectedFont = "Purisa";
		JLabel l;
		
		l = v.getTitleLabel();
		
		assertEquals(expectedFont, l.getFont().getName());
	}
	
	@Test
	/* Testing changeBGColor() method */
	void changeBGColorTest() {
		Color expectedColor = Color.black;
		JFrame f;
		
		v.changeBGColor(Color.black);
		f = v.getFrame();
		
		assertEquals(expectedColor, f.getBackground());
	}
	
	@Test
	/* Testing setUpBlanks() method */
	void setUpBlanksTest() {
		String word = "super";
		int expectedNumOfLabels = word.length();
		
		v.setUpBlanks(word);
		expectedNumOfLabels = v.getNumOfBlanks();
		
		assertEquals(expectedNumOfLabels, v.getNumOfBlanks());
	}
	
	@Test
	/* Testing setUpKeyboardDisplay() method */
	void setUpKeyboardDisplayTest() {
		JButton[] rOne = new JButton[10];
		
		v.setUpKeyboardDisplay(new JPanel());
		rOne = v.getRowOneButtons();
		
		for(int i = 0; i < rOne.length; i++) {
			System.out.println(rOne[i].getActionCommand());
		}
	}
	
	@Test
	/* Testing enableAllKeyboardButtons() method */
	void enableAllKeyboardButtonsTest() {
		JButton[] rOne = new JButton[10];
		JButton[] rTwo = new JButton[9];
		JButton[] rThree = new JButton[7];
		
		v.enableAllKeyboardButtons();
		rOne = v.getRowOneButtons();
		rTwo = v.getRowTwoButtons();
		rThree = v.getRowThreeButtons();
		
		for(int i = 0; i < rOne.length; i++) {
			assertTrue(rOne[i].isEnabled());
		}
		for(int i = 0; i < rTwo.length; i++) {
			assertTrue(rTwo[i].isEnabled());
		}
		for(int i = 0; i < rThree.length; i++) {
			assertTrue(rThree[i].isEnabled());
		}
	}
	
	@Test
	/* Testing enableAllKeyboardButtons() method */
	void disableAllKeyboardButtonsTest() {
		JButton[] rOne = new JButton[10];
		JButton[] rTwo = new JButton[9];
		JButton[] rThree = new JButton[7];
		
		v.disableAllKeyboardButtons();
		rOne = v.getRowOneButtons();
		rTwo = v.getRowTwoButtons();
		rThree = v.getRowThreeButtons();
		
		for(int i = 0; i < rOne.length; i++) {
			assertFalse(rOne[i].isEnabled());
		}
		for(int i = 0; i < rTwo.length; i++) {
			assertFalse(rTwo[i].isEnabled());
		}
		for(int i = 0; i < rThree.length; i++) {
			assertFalse(rThree[i].isEnabled());
		}
	}
}
