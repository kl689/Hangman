/*
 * Author: Me
 * Date: 9/5/2018
 * Description: "Renders the contents of the Hangman Model.
 * 		In other words, the view determines which events are passed to the 
 * 		controller."
 */

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class HangmanView extends JFrame implements ActionListener {
	private JFrame frame = new JFrame("Hangman Game"); /* Main frame for the Hangman game */
	private JFrame instructionsFrame = new JFrame("Rules"); /* Secondary frame used to display the Rules */
	private JPanel main = new JPanel(new CardLayout()); /* Main panel to hold the Splash, Difficulty,
														Game, Instructions, and Options screens */ 
	private JPanel titlePanel = new JPanel(); /* Panel to hold the "Let's Play Hangman!" label */
	private JMenuItem quit = new JMenuItem("Quit", KeyEvent.VK_Q);
	private JMenuItem newGame = new JMenuItem("New Game", KeyEvent.VK_N);
	private JMenuItem rules = new JMenuItem("Rules", KeyEvent.VK_R);
	private JMenuItem about = new JMenuItem("About", KeyEvent.VK_A);
	private JPanel splashScreen = new JPanel(); /* Holds the components of the Splash screen */
	private JPanel difficultyScreen = new JPanel(); /* Holds the components of the Difficulty screen */
	private String difficulty = "";
	private JPanel gameScreen = new JPanel(); /* Holds the components of the Game screen */
	private String word = "";
	private JPanel gameScreenTop = new JPanel(); /* Will house the Hangman and the blanks to fill in */
	private hangmanPanel hangmanPanel = new hangmanPanel(); /* Will house the Hangman */
	private int wrongGuesses = 0; /* Keeps track of the number of wrong guesses */
	private ArrayList<JLabel> blanksOrChars = new ArrayList<JLabel>(); /* Holds the current state of the blanks/letters as the player is playing */
	private JPanel blanksPanel = new JPanel(); /* Will house the blanks/letters */
	private JPanel gameScreenBottom = new JPanel(); /* Will house the keyboard */
	private JPanel temp = new JPanel(); /* Panel to hold the row 1/2/3 keyboard button panels */
	private JPanel rowOnePanel = new JPanel(); /* Holds the row 1 buttons */
	private JPanel rowTwoPanel = new JPanel(); /* Holds the row 2 buttons */
	private JPanel rowThreePanel = new JPanel(); /* Holds the row 3 buttons */
	private JButton[] rowOneButtons = new JButton[10]; /* There are 10 buttons in the first row */
	private JButton[] rowTwoButtons = new JButton[9]; /* There are 9 buttons in the second row */
	private JButton[] rowThreeButtons = new JButton[7]; /* There are 7 buttons in the third row */
	private JPanel northTemp = new JPanel(); /* Panel north of the on-screen keyboard */
	private JPanel southTemp = new JPanel(); /* Panel south of the on-screen keyboard */
	private JPanel eastTemp = new JPanel(); /* Panel east of the on-screen keyboard */ 
	private JPanel westTemp = new JPanel(); /* Panel west of the on-screen keyboard */
	private JPanel rulesScreen = new JPanel(); /* Holds the components of the Rules frame */
	private JPanel rulesInstructionsPanel = new JPanel();
	private JPanel rulesOKButtonPanel = new JPanel(); /* Holds the OK button on the Rules frame */
	private JButton rulesOKButton = new JButton("OK");
	private JPanel howToPlayScreen = new JPanel(); /* Holds the components of the How to Play screen */
	private JPanel howToPlayInstructionsPanel = new JPanel();
	private JButton howToPlayOKButton = new JButton("OK");
	private JPanel howToPlayOKButtonPanel = new JPanel(); /* Holds the OK button on the How to Play screen */
	private JPanel optionsScreen = new JPanel(); /* Holds the components of the Options screen */	
	private Color bgColor = Color.white; /* Default color in-game (can be changed, but radio buttons will have to be updated) */
	private JPanel rbPanel = new JPanel(); /* Holds the radio button group on the Options screen */
	private JPanel buttonsPanel = new JPanel(); /* Holds the "Save" and "Cancel" buttons on the Options screen */
	private JRadioButton white;
	private JRadioButton lightBlue;
	private JRadioButton lightRed;
	private JRadioButton lightGreen;
	private JPanel gameOverDialog = new JPanel(); /* Main panel of the Game Over dialog box */
	private JPanel gameOverDialogTop = new JPanel(); /* Holds the "You Win!/You Lose!" title and the correct word */
	private JPanel gameOverDialogBottom = new JPanel(); /* Holds the "Play Again/Quit" buttons */
	
	/* To handle when a "keyboard" button is pressed */
	protected ActionListener keyboardButtonAction = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton buttonPress = (JButton)e.getSource();
			buttonPress.setEnabled(false);
			try {
				updateHangman(buttonPress.getActionCommand());
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	};
	
	/***************************************************************************
	 * Summary: This is the View constructor
	 * 
	 * Argument(s): N/A
	 * Returns: N/A
	 ***************************************************************************/
	public HangmanView(){
		/* Initialize the GUI of the Hangman application */
		initTitle();
		initSplashScreen();
		initDifficultyScreen();
		initGameScreen();
		initHowToPlayScreen();
		initOptionsScreen();
		initMainWindow();
	}
	
	/***************************************************************************
	 * Summary: Method to create & register the Purisa font for use
	 * 
	 * Argument(s): N/A
	 * Returns: N/A
	 * 
	 * Impacts: N/A
	 **************************************************************************/
	private void setUpCustomFont(){
		try {		
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			Font Purisa = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("Purisa.ttf"));
			ge.registerFont(Purisa);
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		catch(FontFormatException ffe){
			ffe.printStackTrace();
		}
	}
	
	/***************************************************************************
	 * Summary: Method to initialize the title display for the main frame
	 * 
	 * Argument(s): N/A
	 * Returns: N/A
	 * 
	 * Impacts: titlePanel is used in the changeBGColor() method
	 ***************************************************************************/
	private void initTitle(){
		setUpCustomFont();
		JLabel titleLabel = new JLabel("Let's Play Hangman!");
		titleLabel.setFont(new Font("Purisa", Font.PLAIN, 60));
		
		titlePanel.add(titleLabel);
		titlePanel.setBackground(bgColor); 
	}
	
	/***************************************************************************
	 * Summary: Method to initialize the Splash screen and to set up the event 
	 * 		listeners of the buttons on this screen
	 * 
	 * Argument(s): N/A
	 * Returns: N/A
	 * 
	 * Impacts: Uses the setUpPanelForLabelAndThreeButtons() method and 
	 * 		splashScreen is used in the changeBGColor() method
	 ***************************************************************************/
	private void initSplashScreen(){
		JButton startGameButton = new JButton("Start Game");
		JButton howToPlayButton = new JButton("How to Play");
		JButton optionsButton = new JButton("Options");
		JLabel placeholder = new JLabel("");
		
		startGameButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				CardLayout cd = (CardLayout)main.getLayout();
				cd.show(main, "difficultyScreen");
			}
		});
		howToPlayButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				CardLayout cd = (CardLayout)main.getLayout();
				cd.show(main, "howToPlayScreen");
			}
		});
		optionsButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				CardLayout cd = (CardLayout)main.getLayout();
				cd.show(main, "optionsScreen");
			}
		});
		
		setUpPanelForLabelAndThreeButtons(splashScreen, placeholder, startGameButton, howToPlayButton, optionsButton);
	}
	
	/***************************************************************************
	 * Summary: Method to initialize the Difficulty Settings screen and the event 
	 * 		listeners of the buttons on this screen.
	 * 
	 * Note: Clicking on a difficulty button will use the setUpBlanks() method to
	 * 		initialize the blanks/dashes in the blanks panel
	 * 
	 * Argument(s): N/A
	 * Returns: N/A
	 * 
	 * Impacts: Uses the setUpPanelForLabelAndThreeButtons() method and 
	 * 		difficultyScreen is used in the changeBGColor() method
	 ***************************************************************************/
	private void initDifficultyScreen(){
		JButton easyButton = new JButton("Easy");
		JButton mediumButton = new JButton("Medium");
		JButton hardButton = new JButton("Hard");
		JLabel selection = new JLabel("Select the difficulty: ");
		HangmanModel hm = new HangmanModel();
		
		easyButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				difficulty = "easy";
				word = hm.getWord(difficulty);
				setUpBlanks(word);
				CardLayout cd = (CardLayout)main.getLayout();
				cd.show(main, "gameScreen");
			}
		});
		mediumButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				difficulty = "medium";
				word = hm.getWord(difficulty);
				setUpBlanks(word);
				CardLayout cd = (CardLayout)main.getLayout();
				cd.show(main, "gameScreen");
			}
		});
		hardButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				difficulty = "hard";
				word = hm.getWord(difficulty);
				setUpBlanks(word);
				CardLayout cd = (CardLayout)main.getLayout();
				cd.show(main, "gameScreen");
			}
		});
		
		setUpPanelForLabelAndThreeButtons(difficultyScreen, selection, easyButton, mediumButton, hardButton);
	}
	
	/***************************************************************************
	 * Summary: Method to initialize the panels needed for the Game screen and 
	 * 		the background color for the Game Over dialog box
	 * 
	 * Argument(s): N/A
	 * Returns: N/A
	 * 
	 * Impacts: gameScreen, gameScreenTop, gameScreenBottom, gameOverDialog,
	 * 		gameOverDialogTop, and gameOverDialogBottom are used in the 
	 * 		changeBGColor() method and this method uses the 
	 * 		addHangmanAndBlanksPanels() and setUpKeyboardDisplay() methods
	 ***************************************************************************/
	private void initGameScreen(){
		GridLayout gl = new GridLayout(2, 0);
		gameScreen.setLayout(gl);
		
		/* Divides the top panel of the Game screen into 2 panels:
		 * 		1 for the gallows and 1 for the blanks/characters to display
		 */
		addHangmanAndBlanksPanels(gameScreenTop);
		
		/* Sets up the keyboard display straight onto the given panel */
		setUpKeyboardDisplay(gameScreenBottom);
		
		/* Setting the background of the Game Over dialog window */
		gameOverDialog.setBackground(bgColor);
		gameOverDialogTop.setBackground(bgColor);
		gameOverDialogBottom.setBackground(bgColor);
		
		//gameScreen.setBorder(BorderFactory.createEtchedBorder());
		gameScreen.add(gameScreenTop);
		gameScreen.add(gameScreenBottom);
	}
	
	/***************************************************************************
	 * Summary: Method to initialize the How to Play screen GUI
	 * 
	 * Argument(s): N/A
	 * Returns: N/A
	 * 
	 * Impacts: howToPlayScreen, howToPlayInstructionsPanel, and howToPlayOKButtonPanel
	 * 		are used in the changBGColor() method
	 ***************************************************************************/
	private void initHowToPlayScreen(){
		BorderLayout bl = new BorderLayout();
		howToPlayScreen.setLayout(bl);
		howToPlayScreen.setBackground(bgColor);
		//howToPlayScreen.setBorder(BorderFactory.createEtchedBorder());
        
		setUpPanelForInstructions(howToPlayInstructionsPanel);
		
		/* Setting up the panel that houses the OK button on this screen */
		howToPlayOKButton.setPreferredSize(new Dimension(100, 30));
		howToPlayOKButton.setMinimumSize(new Dimension(100, 30));
		howToPlayOKButton.setFocusable(false);
		howToPlayOKButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		howToPlayOKButtonPanel.setBackground(bgColor);
		howToPlayOKButtonPanel.add(howToPlayOKButton);
		//okButtonPanel.setBorder(BorderFactory.createEtchedBorder());
		
		/* Setting up the OK button to listen to an event */
		howToPlayOKButton.setActionCommand("htpOKButton");
		howToPlayOKButton.addActionListener(this);
		
		/* Adding the Instructions panel and the OK button to the How to Play screen */
        howToPlayScreen.add(howToPlayInstructionsPanel, BorderLayout.CENTER);
        howToPlayScreen.add(howToPlayOKButtonPanel, BorderLayout.SOUTH);
	}
	
	/***************************************************************************
	 * Summary: Method to initialize the Options screen GUI
	 * 
	 * Argument(s): N/A
	 * Returns: N/A
	 * 
	 * Impacts: Any radio button changes here need to be fixed up in the 
	 * 		actionPerformed() and the changeBGColor() methods
	 ***************************************************************************/
	private void initOptionsScreen(){
		BorderLayout borderLayoutThree = new BorderLayout();
		optionsScreen.setLayout(borderLayoutThree);
		optionsScreen.setBackground(bgColor);
		//optionsScreen.setBorder(BorderFactory.createEtchedBorder());
		
		BoxLayout boxLayoutTwo = new BoxLayout(rbPanel, BoxLayout.X_AXIS);
		rbPanel.setLayout(boxLayoutTwo);
		rbPanel.setBackground(bgColor);
		
        	/* Setting up the radio buttons for background color */
			JLabel bg = new JLabel("Background Color: ");
			
			ButtonGroup bgColorRB = new ButtonGroup();
			white = new JRadioButton("White"); white.setSelected(true);
			white.setBackground(bgColor);
			lightBlue = new JRadioButton("Light Blue"); 
			lightBlue.setBackground(bgColor);
			lightRed = new JRadioButton("Light Red");
			lightRed.setBackground(bgColor);
			lightGreen = new JRadioButton("Light Green");
			lightGreen.setBackground(bgColor);
			bgColorRB.add(white);
			bgColorRB.add(lightBlue);
			bgColorRB.add(lightRed);
			bgColorRB.add(lightGreen);

			rbPanel.add(Box.createHorizontalGlue());
			rbPanel.add(bg);
			rbPanel.add(Box.createRigidArea(new Dimension(0, 10)));
			rbPanel.add(white);
			rbPanel.add(Box.createRigidArea(new Dimension(0, 10)));
			rbPanel.add(lightBlue);
			rbPanel.add(Box.createRigidArea(new Dimension(0, 10)));
			rbPanel.add(lightRed);
			rbPanel.add(Box.createRigidArea(new Dimension(0, 10)));
			rbPanel.add(lightGreen);
			rbPanel.add(Box.createHorizontalGlue());
		
		optionsScreen.add(rbPanel, BorderLayout.CENTER);
		
		JButton saveButton = new JButton("Save");
		JButton cancelButton = new JButton("Cancel");
		
			/* Setting up the Save and Cancel buttons on this screen*/
			buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			buttonsPanel.setBackground(bgColor);
			saveButton.setPreferredSize(new Dimension(100, 30));
			saveButton.setMinimumSize(new Dimension(100, 30));
			cancelButton.setPreferredSize(new Dimension(100, 30));
			cancelButton.setMinimumSize(new Dimension(100, 30));
			buttonsPanel.add(saveButton);
			buttonsPanel.add(cancelButton);
			//buttonsPanel.setBorder(BorderFactory.createEtchedBorder());
				
		/* Setting up the Save and Cancel buttons to listen to an event */
		saveButton.setActionCommand("saveButton");
		saveButton.addActionListener(this);
		cancelButton.setActionCommand("cancelButton");
		cancelButton.addActionListener(this);
			
		optionsScreen.add(buttonsPanel, BorderLayout.SOUTH);		
	}
	
	/***************************************************************************
	 * Summary: Method to implement listeners for the OK button on the 
	 * 		How to Play screen and the Save/Cancel buttons on the Options Screen
	 * 
	 * Note: This method was written to practice with a different format for 
	 * 		event listeners
	 * 
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 * Argument(s):
	 * 		@param evt
	 * Returns: N/A
	 ***************************************************************************/
	public void actionPerformed(ActionEvent evt){
		String actionCommand = evt.getActionCommand();
		try {
			if(actionCommand.equals("htpOKButton")){	/* Handles OK button on How to Play screen */
				CardLayout cd = (CardLayout)main.getLayout();
				cd.show(main, "splashScreen");
			}
			else if(actionCommand.equals("saveButton")){ /* Handles Save button on Options screen */
				if(lightBlue.isSelected()){
					changeBGColor(new Color(173, 216, 230));
					bgColor = new Color(173, 216, 230);
				}
				else if(lightRed.isSelected()){
					changeBGColor(new Color(240, 128, 128));
					bgColor = new Color(240, 128, 128);
				}
				else if(white.isSelected()){
					changeBGColor(Color.white);
					bgColor = Color.white;
				}	
				else if(lightGreen.isSelected()){
					changeBGColor(new Color(153, 255, 153));
					bgColor = new Color(153, 255, 153);
				}
				CardLayout cd = (CardLayout)main.getLayout();
				cd.show(main, "splashScreen");
			}
			else if(actionCommand.equals("cancelButton")){	/* Handles Cancel button on Options screen */
				if(bgColor.equals(Color.white)){
					white.setSelected(true);
				}
				else if(bgColor.equals(new Color(173, 216, 230))){
					lightBlue.setSelected(true);
				}
				else if(bgColor.equals(new Color(240, 128, 128))){
					lightRed.setSelected(true);
				}
				else if(bgColor.equals(new Color(153, 255, 153))){
					lightGreen.setSelected(true);
				}
				CardLayout cd = (CardLayout)main.getLayout();
				cd.show(main, "splashScreen");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/***************************************************************************
	 * Summary: Helper method to change the background of necessary panels
	 * 
	 * Note: Is used to help the Save button on the Options screen
	 * 
	 * Argument(s): 
	 * 		@param bgc - the background color to change to
	 * Returns: N/A
	 * 
	 ***************************************************************************/
	protected void changeBGColor(Color bgc){
		frame.setBackground(bgc);
		titlePanel.setBackground(bgc);
		splashScreen.setBackground(bgc);
		instructionsFrame.setBackground(bgc);
		rulesScreen.setBackground(bgc);
		rulesInstructionsPanel.setBackground(bgc);
		difficultyScreen.setBackground(bgc);
		rulesOKButtonPanel.setBackground(bgc);
		howToPlayScreen.setBackground(bgc);
		howToPlayInstructionsPanel.setBackground(bgc);
		howToPlayOKButtonPanel.setBackground(bgc);
		optionsScreen.setBackground(bgc);
		rbPanel.setBackground(bgc);
		hangmanPanel.setBackground(bgc);
		blanksPanel.setBackground(bgc);
		buttonsPanel.setBackground(bgc);
		white.setBackground(bgc);
		lightBlue.setBackground(bgc);
		lightRed.setBackground(bgc);
		lightGreen.setBackground(bgc);
		gameScreen.setBackground(bgc);
		temp.setBackground(bgc);
		rowOnePanel.setBackground(bgc);
		rowTwoPanel.setBackground(bgc);
		rowThreePanel.setBackground(bgc);
		gameScreenTop.setBackground(bgc);
		gameScreenBottom.setBackground(bgc);
		northTemp.setBackground(bgc);
		southTemp.setBackground(bgc);
		eastTemp.setBackground(bgc);
		westTemp.setBackground(bgc);
		gameOverDialog.setBackground(bgc);
		gameOverDialogTop.setBackground(bgc);
		gameOverDialogBottom.setBackground(bgc);
	}
	
	/***************************************************************************
	 * Summary: Helper method to format a panel with a label and 3 buttons 
	 * 		in the center of the panel
	 * 
	 * Note: Is used to help format the Splash and Difficulty screens
	 * 
	 * Argument(s): 
	 * 		@param p - the panel to be formatted
	 * 		@param l - the label to be added to panel p
	 * 		@param first - the first of the 3 buttons to be added to panel p
	 * 		@param second - the second of the 3 buttons to be added to panel p
	 * 		@param third - the third of the 3 buttons to be added to panel p
	 * Returns: N/A
	 ***************************************************************************/
	private void setUpPanelForLabelAndThreeButtons(JPanel p, JLabel l, JButton first, JButton second, JButton third){
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		p.setLayout(gbl);
		p.setBackground(bgColor);
		gbc.fill = GridBagConstraints.VERTICAL;
		
		l.setPreferredSize(new Dimension(150, 30));
		l.setMinimumSize(new Dimension(150, 30));
		l.setHorizontalAlignment(JLabel.CENTER);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbl.setConstraints(l, gbc);
		p.add(l);
		first.setFocusable(false);
		
		first.setPreferredSize(new Dimension(150, 30));
		first.setMinimumSize(new Dimension(150, 30));
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbl.setConstraints(first, gbc);
		p.add(first);
		first.setFocusable(false);
		
		second.setPreferredSize(new Dimension(150, 30));
		second.setMinimumSize(new Dimension(150, 30));
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbl.setConstraints(second, gbc);
		p.add(second);
		second.setFocusable(false);
		
		third.setPreferredSize(new Dimension(150, 30));
		third.setMinimumSize(new Dimension(150, 30));
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbl.setConstraints(third, gbc);
		p.add(third);
		third.setFocusable(false);
	}
	
	/***************************************************************************
	 * Summary: Helper method to format a panel with the instructions for the
	 * 		Hangman game
	 * 
	 * Note: Adds a Requirement title/label, a Game Play title/label, and 
	 * 		an Objective title/label
	 * 
	 * Argument(s): 
	 * 		@param instructions - the panel to format the instructions on
	 * Returns: N/A
	 ***************************************************************************/
	private void setUpPanelForInstructions(JPanel instructions){
		/* Sets up the given panel to house the instructions */
		instructions.setBackground(bgColor);
		BoxLayout boxLayout = new BoxLayout(instructions, BoxLayout.Y_AXIS);
		instructions.setLayout(boxLayout);
		//instructions.setBorder(BorderFactory.createEtchedBorder());
		
		/* Formats the Instructions to be displayed */
		JLabel requirementTitle = new JLabel("<html><p>Requires:</p></html>");
		JLabel requirement = new JLabel("<html><p>1 or more players</p>"
				+ "<p>Mouse or touchpad</p></html>");
		JLabel gamePlayTitle = new JLabel("<html><p>Game Play:</p></html>");
		JLabel gamePlay = new JLabel("<html><p>The system chooses a word; the player(s) try to guess what it is one letter at a time.</p>"
				+"<br>"
				+"<p>The system will draw the number of dashes/spaces needed for the mystery word. If the guessing player(s) click(s) a letter on the keyboard "
				+"that occurs in the word, the system will fill in the blanks with that letter in the right places. However, if that letter does not "
				+"occur in the word, the system will draw one element of the hangman's gallows.</p>"
				+"<p>In a standard game, completing a character in a noose allows a minimum of 6 wrong answers until the game ends.</p>"
				+"<p>The first player to guess the word or phrase wins the game.</p>"
				+"</html>");
		JLabel objectiveTitle = new JLabel("<html><p>Objective:</p></html>");
		JLabel objective = new JLabel("<html><p>Guess the word or phrase before the person gets hung!</p></html>");
		
		requirementTitle.setFont(new Font("Sans-Serif", Font.BOLD, 20));
		requirementTitle.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		gamePlayTitle.setFont(new Font("Sans-Serif", Font.BOLD, 20));
		gamePlayTitle.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		objectiveTitle.setFont(new Font("Sans-Serif", Font.BOLD, 20));
		objectiveTitle.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		
		requirement.setFont(new Font("Sans-Serif", Font.PLAIN, 15));
		requirement.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		gamePlay.setFont(new Font("Sans-Serif", Font.PLAIN, 15));
		gamePlay.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		objective.setFont(new Font("Sans-Serif", Font.PLAIN, 15));
		objective.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		
		/* Adds the formatted instructions to the panel given as an argument */
		instructions.add(requirementTitle);
		instructions.add(requirement);
		instructions.add(gamePlayTitle);
		instructions.add(gamePlay);
		instructions.add(objectiveTitle);
		instructions.add(objective);
	}
	
	/***************************************************************************
	 * Summary: Helper method to set up the given panel with the Hangman's gallows
	 * 		panel and the blanks panel
	 * 
	 * Argument(s): 
	 * 		@param hd - the panel to format the Hangman's gallows on
	 * Returns: N/A
	 ***************************************************************************/
	private void addHangmanAndBlanksPanels(JPanel hd){
		BorderLayout bl = new BorderLayout();
		hd.setLayout(bl);
		hd.setBackground(bgColor);
		
		hangmanPanel.setBackground(bgColor);
		//hangmanPanel.setBorder(BorderFactory.createEtchedBorder());
		hd.add(hangmanPanel, BorderLayout.CENTER);
		
		blanksPanel.setBackground(bgColor);
		//blanksPanel.setBorder(BorderFactory.createEtchedBorder());
		hd.add(blanksPanel, BorderLayout.SOUTH);
	}
	
	/***************************************************************************
	 * Summary: Helper method to set up the word blanks
	 * 
	 * Note: Used in the initDifficultyScreen() method
	 * 
	 * Argument(s): 
	 * 		@param word - is a string of the random word generated for the player 
	 * 						to guess
	 * Returns: N/A
	 ***************************************************************************/
	protected void setUpBlanks(String word) {
		/* Set up blanks in the Blanks panel */
		
		blanksOrChars.clear();
		
		for(int i = 0; i < word.length(); i++) {
			JLabel dash = new JLabel("_");
			dash.setFont(new Font("Verdana", Font.BOLD, 33));
			blanksOrChars.add(dash);
		}
		
		blanksPanel.removeAll();
		blanksPanel.revalidate();
		
		for(int i = 0; i < blanksOrChars.size(); i++) {
			blanksPanel.add(blanksOrChars.get(i));
		}
	}
	
	/***************************************************************************
	 * Summary: Method to set up the given panel with an on-screen keyboard
	 * 
	 * Argument(s): 
	 * 		@param kd - the panel to format the on-screen keyboard on
	 * Returns: N/A
	 ***************************************************************************/
	protected void setUpKeyboardDisplay(JPanel kd){
		northTemp.setPreferredSize(new Dimension(10, 40));
		//northTemp.setBorder(BorderFactory.createEtchedBorder());
		northTemp.setBackground(bgColor);
		eastTemp.setPreferredSize(new Dimension(100, 10));
		eastTemp.setBackground(bgColor);
		westTemp.setPreferredSize(new Dimension(100, 10));
		westTemp.setBackground(bgColor);
		southTemp.setBackground(bgColor);
		
		String rowOneLetters[] = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"};
		int rowOneLength = rowOneLetters.length;
		String rowTwoLetters[] = {"A", "S", "D", "F", "G", "H", "J", "K", "L"};
		int rowTwoLength = rowTwoLetters.length;
		String rowThreeLetters[] = {"Z", "X", "C", "V", "B", "N", "M"};
		int rowThreeLength = rowThreeLetters.length;
		
		GridLayout glOne = new GridLayout(1, rowOneLength);
		GridLayout glTwo = new GridLayout(1, rowTwoLength + 2);
		GridLayout glThree = new GridLayout(1, rowThreeLength + 4);
		GridLayout tl = new GridLayout(3, 1);
		BorderLayout ml = new BorderLayout();
		
		rowOnePanel = new JPanel(); rowOnePanel.setBackground(bgColor); rowOnePanel.setLayout(glOne);
		rowTwoPanel = new JPanel(); rowTwoPanel.setBackground(bgColor); rowTwoPanel.setLayout(glTwo);
		rowThreePanel = new JPanel(); rowThreePanel.setBackground(bgColor); rowThreePanel.setLayout(glThree);
		
		for(int i = 0; i < rowOneLength; i++){
			rowOneButtons[i] = new JButton(rowOneLetters[i]);
			rowOneButtons[i].addActionListener(keyboardButtonAction);
			rowOneButtons[i].setActionCommand(rowOneLetters[i]);
			rowOneButtons[i].setFocusable(false);
			rowOnePanel.add(rowOneButtons[i]);
		}
		rowTwoPanel.add(Box.createHorizontalGlue());
		for(int i = 0; i < rowTwoLength; i++){
			rowTwoButtons[i] = new JButton(rowTwoLetters[i]);
			rowOneButtons[i].setPreferredSize(new Dimension(10,10));
			rowTwoButtons[i].addActionListener(keyboardButtonAction);
			rowTwoButtons[i].setActionCommand(rowTwoLetters[i]);
			rowTwoButtons[i].setFocusable(false);
			rowTwoPanel.add(rowTwoButtons[i]);
		}
		rowTwoPanel.add(Box.createHorizontalGlue());
		rowThreePanel.add(Box.createHorizontalGlue());
		rowThreePanel.add(Box.createHorizontalGlue());
		for(int i = 0; i < rowThreeLength; i++){
			rowThreeButtons[i] = new JButton(rowThreeLetters[i]);
			rowThreeButtons[i].addActionListener(keyboardButtonAction);
			rowThreeButtons[i].setActionCommand(rowThreeLetters[i]);
			rowThreeButtons[i].setFocusable(false);
			rowThreePanel.add(rowThreeButtons[i]);
		}
		rowThreePanel.add(Box.createHorizontalGlue());
		rowThreePanel.add(Box.createHorizontalGlue());
		
		temp.setLayout(tl);
		temp.add(rowOnePanel);
		temp.add(rowTwoPanel);
		temp.add(rowThreePanel);
		
		kd.setBackground(bgColor);
		kd.setLayout(ml);
		kd.add(northTemp, BorderLayout.NORTH);
		kd.add(southTemp, BorderLayout.SOUTH);
		kd.add(temp, BorderLayout.CENTER);
		kd.add(westTemp, BorderLayout.WEST);
		kd.add(eastTemp, BorderLayout.EAST);
	}
	
	/***************************************************************************
	 * Summary: Helper method to enable all of the keys of the on-screen keyboard
	 * 
	 * Note: Used in the resetGameScreen() method
	 * 
	 * Argument(s): N/A
	 * Returns: N/A
	 ***************************************************************************/
	protected void enableAllKeyboardButtons() {
		for(int i = 0; i < rowOneButtons.length; i++) {
			rowOneButtons[i].setEnabled(true);
		}
		for(int i = 0; i < rowTwoButtons.length; i++) {
			rowTwoButtons[i].setEnabled(true);
		}
		for(int i = 0; i < rowThreeButtons.length; i++) {
			rowThreeButtons[i].setEnabled(true);
		}
	}
	
	/***************************************************************************
	 * Summary: Helper method to disable all of the keys of the on-screen keyboard
	 * 
	 * Note: Used in the endGame() method
	 * 
	 * Argument(s): N/A
	 * Returns: N/A
	 ***************************************************************************/
	protected void disableAllKeyboardButtons() {
		for(int i = 0; i < rowOneButtons.length; i++) {
			rowOneButtons[i].setEnabled(false);
		}
		for(int i = 0; i < rowTwoButtons.length; i++) {
			rowTwoButtons[i].setEnabled(false);
		}
		for(int i = 0; i < rowThreeButtons.length; i++) {
			rowThreeButtons[i].setEnabled(false);
		}
	}
	
	/***************************************************************************
	 * Summary: Helper method to update the gallows display and the 
	 * 		blanks/characters. Also checks to see if the game has ended.
	 * 
	 * Argument(s): 
	 * 		@param letter - a letter that the player has guessed
	 * Returns: N/A
	 ***************************************************************************/
	private void updateHangman(String letter) {
		char c = letter.charAt(0);
		
		/* If the player guesses incorrectly */
		if(!word.contains(letter)) {
			/* Increment wrongGuesses count */
			wrongGuesses++;
			
			switch(wrongGuesses) {
			case 1: /* Head */
				hangmanPanel.setHeadVisible(true);
				hangmanPanel.repaint();
				break;
			case 2: /* Body */
				hangmanPanel.setBodyVisible(true);
				hangmanPanel.repaint();
				break;
			case 3: /* Arm on our left */
				hangmanPanel.setLeftArmVisible(true);
				hangmanPanel.repaint();
				break;
			case 4: /* Arm on our right */
				hangmanPanel.setRightArmVisible(true);
				hangmanPanel.repaint();
				break;
			case 5: /* Leg on our left */
				hangmanPanel.setLeftLegVisible(true);
				hangmanPanel.repaint();
				break;
			case 6: /* Leg on our right */
				hangmanPanel.setRightLegVisible(true);
				hangmanPanel.repaint();
				break;
			}
			
			/* Checks to see if the player has lost the game */
			if(wrongGuesses == 6) {	
				disableAllKeyboardButtons();
				newGame.setEnabled(false);
				rules.setEnabled(false);
				endGame("You Lose!");
			}
		}
		/* If the player guesses correctly, update the blanksOrChars list */
		else {
			for(int i = 0; i < word.length(); i++) {
				if(c == word.charAt(i)) {
					JLabel l = new JLabel(letter);
					l.setFont(new Font("Purisa", Font.BOLD, 35));
					blanksOrChars.set(i, l);
				}
			}
		}
		
		blanksPanel.removeAll(); /* Remove all current components in the blanks panel */
		blanksPanel.revalidate();
		
		for(int i = 0; i < word.length(); i++) {
			blanksPanel.add(blanksOrChars.get(i)); /* Reprint the blanksOrChars array in the blanks panel */
			blanksPanel.revalidate();
		}
		
		blanksPanel.revalidate();
		
		/* Checks to see if the player has won the game */
		int count = 0; /* Holds the number of blanks/dashes */
		for(int i = 0; i < word.length(); i++) {
			JLabel l = blanksOrChars.get(i);
			String text = l.getText();
			if(text.equals("_")) {
				count++;
			}
		}
		if(count == 0) {
			disableAllKeyboardButtons();
			newGame.setEnabled(false);
			rules.setEnabled(false);
			endGame("You Win!");
		}
	}
	
	/***************************************************************************
	 * Summary: Helper method to display the dialog box for when the user 
	 * 		loses/wins the game
	 * 
	 * Argument(s): 
	 * 		@param eg - the string to display to tell the player that they have 
	 * 				either won or lost the game
	 * Returns: N/A
	 ***************************************************************************/
	private void endGame(String eg) {
		JDialog jd = new JDialog(frame, "Game Over");
		JLabel t = new JLabel(eg);
		JLabel correctWord = new JLabel("");
		
		jd.setLocationRelativeTo(frame);
		jd.setMinimumSize(new Dimension(400, 250));
		jd.setPreferredSize(new Dimension(400, 250));
		
		gameOverDialog.removeAll();
		gameOverDialogTop.removeAll();
		gameOverDialogBottom.removeAll();
		gameOverDialog.revalidate();
		gameOverDialogTop.revalidate();
		gameOverDialogBottom.revalidate();
		
		/* panel endGameDialog layout */
		BorderLayout bl = new BorderLayout();
		gameOverDialog.setLayout(bl);
		/* panel endGameDialogTop layout */
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.VERTICAL;
		gameOverDialogTop.setLayout(gbl);
		//ptop.setBorder(BorderFactory.createEtchedBorder());
		/* panel endGameDialogBottom layout */
		gameOverDialogBottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		t.setFont(new Font("Purisa", Font.BOLD, 60));
		t.setHorizontalAlignment(JLabel.CENTER);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbl.setConstraints(t, gbc);
		
		correctWord.setFont(new Font("Verdana", Font.PLAIN, 15));
		correctWord.setHorizontalAlignment(JLabel.CENTER);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(0, 10, 10, 10);
		gbl.setConstraints(correctWord, gbc);
		
		if(t.getText().equals("You Win!")) {
			t.setForeground(new Color(0, 100, 0)); /* Darkish green color */
		}
		else if(t.getText().equals("You Lose!")) {
			t.setForeground(Color.RED);
			/* If the player loses, the end game dialog window will display the correct word */
			correctWord.setText("The correct word is: " + word);
		}
		
		gameOverDialogTop.add(t);
		gameOverDialogTop.add(correctWord);
		
		JButton pa = new JButton("Play Again");
		JButton q = new JButton("Quit");
		
		pa.setPreferredSize(new Dimension(100, 30));
		pa.setMinimumSize(new Dimension(100, 30));
		pa.setHorizontalAlignment(JLabel.CENTER);
		pa.setFocusable(false);
		
		q.setPreferredSize(new Dimension(100, 30));
		q.setMinimumSize(new Dimension(100, 30));
		q.setHorizontalAlignment(JButton.CENTER);
		q.setFocusable(false);
		
		gameOverDialogBottom.add(pa);
		gameOverDialogBottom.add(q);
		
		pa.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				jd.dispose();
				
				/* Closes current frame and reopens frame
				frame.dispose();
				HangmanView hm = new HangmanView();	
				*/
				
				resetGameScreen();
				newGame.setEnabled(true);
				rules.setEnabled(true);
				
				CardLayout cd = (CardLayout)main.getLayout();
				cd.show(main, "difficultyScreen");
			}
		});
		q.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				jd.dispose();
				frame.dispose();
			}
		});
		
		gameOverDialog.add(gameOverDialogTop, BorderLayout.CENTER);
		gameOverDialog.add(gameOverDialogBottom, BorderLayout.SOUTH);
		jd.add(gameOverDialog);
		jd.setVisible(true);
	}
	
	/***************************************************************************
	 * Summary: Helper method to reset the gallows, the blanks for a new word,
	 * 		and the keyboard buttons
	 * 
	 * Note: Used in the endGame() method and for the "New Game" menu item
	 * 
	 * Argument(s): N/A
	 * Returns: N/A
	 ***************************************************************************/
	private void resetGameScreen() {
		/* reset the wrongGuesses count */
		wrongGuesses = 0;
		/* get new word and reset blanks */
		HangmanModel hm = new HangmanModel();
		word = hm.getWord(difficulty);
		setUpBlanks(word);
		/* reset keyboard buttons */
		enableAllKeyboardButtons();
		/* reset the hangman/gallows */
		hangmanPanel.setHeadVisible(false);
		hangmanPanel.setBodyVisible(false);
		hangmanPanel.setLeftArmVisible(false);
		hangmanPanel.setRightArmVisible(false);
		hangmanPanel.setLeftLegVisible(false);
		hangmanPanel.setRightLegVisible(false);
		hangmanPanel.repaint();
	}
	
	/***************************************************************************
	 * Summary: Helper method to add the "cards" to the main panel
	 * 
	 * Note: Used in the initMainWindow() method
	 * 
	 * Argument(s): N/A
	 * Returns: N/A
	 ***************************************************************************/
	private void addCardsToMainPanel(){
		main.add(splashScreen, "splashScreen");
		main.add(difficultyScreen, "difficultyScreen");
		main.add(gameScreen, "gameScreen");
		main.add(howToPlayScreen, "howToPlayScreen");
		main.add(optionsScreen, "optionsScreen");
	}
	
	/***************************************************************************
	 * Summary: Method to set up the format and the action listeners of a
	 * 		menu bar
	 * 
	 * Argument(s): 
	 * 		@parma menuBar - a menu bar to format
	 * Returns: N/A
	 ***************************************************************************/
	private void setUpMenuBar(JMenuBar menuBar){
		JMenu file = new JMenu("File");
		JMenu help = new JMenu("Help");
		
		file.add(newGame);
		file.add(quit);
		help.add(rules);
		help.add(about);
		
		menuBar.add(file);
		menuBar.add(help);
		
		/* To set up the secondary frame that will display when the "Rules" menu item is clicked */
		instructionsFrame.setSize(700, 600); /* Sets the default size of the window */
		instructionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container pane = instructionsFrame.getContentPane();
		
		BorderLayout bl = new BorderLayout();
		rulesScreen.setLayout(bl);
		
		setUpPanelForInstructions(rulesInstructionsPanel);
		
		/* Setting up the OK button panel on the secondary frame */
		rulesOKButton.setPreferredSize(new Dimension(100, 30));
		rulesOKButton.setMinimumSize(new Dimension(100, 30));
		rulesOKButton.setFocusable(false);
		rulesOKButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		rulesOKButtonPanel.setBackground(bgColor);
		rulesOKButtonPanel.add(rulesOKButton);
		//okButtonPanel.setBorder(BorderFactory.createEtchedBorder());
		
		/* Adding the instructions panel and the OK button to the secondary frame */
        rulesScreen.add(rulesInstructionsPanel, BorderLayout.CENTER);
        rulesScreen.add(rulesOKButtonPanel, BorderLayout.SOUTH);
        pane.add(rulesScreen);
		
		/* Setting up action listeners for the menu items */
        newGame.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		resetGameScreen();
        	}
        });
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		rules.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				instructionsFrame.setVisible(true);
			}
		});
		rulesOKButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					instructionsFrame.dispose();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		about.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JDialog jd = new JDialog(frame, "About");
				JPanel p = new JPanel();
				GridBagLayout gbl = new GridBagLayout();
				GridBagConstraints gbc = new GridBagConstraints();
				JLabel lTitle = new JLabel("Hangman");
				JLabel lText = new JLabel("Version 1.0");
				JButton ok = new JButton("OK");
				
				jd.setLocationRelativeTo(frame);
				jd.setMinimumSize(new Dimension(300, 200));
				jd.setPreferredSize(new Dimension(300, 200));
				
				p.setLayout(gbl);
				gbc.fill = GridBagConstraints.VERTICAL;
				
				lTitle.setPreferredSize(new Dimension(150, 30));
				lTitle.setMinimumSize(new Dimension(150, 30));
				lTitle.setHorizontalAlignment(JLabel.CENTER);
				gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.insets = new Insets(10, 10, 10, 10);
				gbl.setConstraints(lTitle, gbc);
				p.add(lTitle);
				
				lText.setPreferredSize(new Dimension(150, 30));
				lText.setMinimumSize(new Dimension(150, 30));
				lText.setHorizontalAlignment(JLabel.CENTER);
				gbc.gridx = 0;
				gbc.gridy = 2;
				gbc.insets = new Insets(10, 10, 10, 10);
				gbl.setConstraints(lText, gbc);
				p.add(lText);
				
				ok.setHorizontalAlignment(JButton.CENTER);
				ok.setFocusable(false);
				gbc.gridx = 0;
				gbc.gridy = 3;
				gbc.insets = new Insets(10, 10, 10, 10);
				gbl.setConstraints(ok, gbc);
				p.add(ok);
				
				ok.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						jd.dispose();
					}
				});
				
				jd.add(p);
				jd.setVisible(true);
			}
		});
	}
	
	/***************************************************************************
	 * Summary: Method to set up the main frame window
	 * 
	 * Argument(s): N/A
	 * Returns: N/A
	 ***************************************************************************/
	private void initMainWindow(){
		JMenuBar menuBar = new JMenuBar();

		addCardsToMainPanel(); 
		setUpMenuBar(menuBar);
		
		frame.setMinimumSize(new Dimension(900, 630)); /* Sets the default size of the main window */
		frame.setPreferredSize(new Dimension(900, 630));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(menuBar);
		
		BorderLayout borderLayout = new BorderLayout();
		Container pane = frame.getContentPane();
		pane.setLayout(borderLayout);

		/* Adding the title and main panel to the main frame's content pane */
		pane.add(titlePanel, BorderLayout.NORTH); 
		pane.add(main, BorderLayout.CENTER);
		
		/* Setting the frame to be visible */
		frame.setVisible(true);
	}
	
	public JButton[] getRowOneButtons() {
		return rowOneButtons;
	}
	
	public JButton[] getRowTwoButtons() {
		return rowTwoButtons;
	}
	
	public JButton[] getRowThreeButtons() {
		return rowThreeButtons;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public JLabel getTitleLabel() {
		JLabel label = null;
		for (Component c : titlePanel.getComponents()) {
	        if (c instanceof JLabel) {
	            label = (JLabel) c;
	        }
	    }
		
		return label;
	}
	
	public int getNumOfBlanks() {
		int n = 0;
		for (Component c : blanksPanel.getComponents()) {
	        n++;
	    }
		
		return n;
	}
}

/***************************************************************************
 * Summary: Class for the hangman panel to draw the gallows before adding 
 * 		the hangman to the display
 * 
 * Note: 
 * 		drawLine(x1, y1, x2, y2) method draws a line from (x1, y1) to (x2, y2)
 * 		drawOval(x, y, width, height)
 ***************************************************************************/
@SuppressWarnings("serial")
class hangmanPanel extends JPanel {
	Graphics2D g2;
	Dimension d;
	private boolean headVisible = false;
	private boolean bodyVisible = false;
	private boolean leftArmVisible = false;
	private boolean rightArmVisible = false;
	private boolean leftLegVisible = false;
	private boolean rightLegVisible = false;
	
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g2 = (Graphics2D) g;
		g2.setColor(Color.black);
	    g2.setStroke(new BasicStroke(5));
		
	    d = this.getSize();
	   
	    g2.drawLine(d.width/3, d.height - 2, d.width/2 - 30, d.height - 2); /* draw the bottom horizontal line of the gallows */
	    g2.drawLine(((d.width/3) + d.width/2 - 30) / 2, 0, ((d.width/3) + d.width/2 - 30) / 2, d.height); /* draw the main vertical line of the gallows */
	    g2.drawLine(((d.width/3) + d.width/2 - 30) / 2, 0, (((d.width/3) + d.width/2 - 30) / 2) + 180, 0); /* draw the top horizontal line of the gallows */
	    g2.drawLine(((d.width/3) + d.width/2 - 30) / 2, 40, (((d.width/3) + d.width/2 - 30) / 2) + 40, 0); /* draw the diagonal lone of the gallows */ 
	    g2.drawLine((((d.width/3) + d.width/2 - 30) / 2) + 160, 0, (((d.width/3) + d.width/2 - 30) / 2) + 160, 10); /* draw the tiny vertical line at end of gallows */
	    
	    if(isHeadVisible()) {
	    	g2.drawOval((((d.width/3) + d.width/2 - 30) / 2) + 135, 10, 50, 50);
	    }
	    if(isBodyVisible()) {
	    	g2.drawLine((((d.width/3) + d.width/2 - 30) / 2) + 160, 60, (((d.width/3) + d.width/2 - 30) / 2) + 160, d.height - 70);
	    }
	    if(isLeftArmVisible()) {
	    	g2.drawLine((((d.width/3) + d.width/2 - 30) / 2) + 160, 70, (((d.width/3) + d.width/2 - 30) / 2) + 130, 105);
	    }
	    if(isRightArmVisible()) {
	    	g2.drawLine((((d.width/3) + d.width/2 - 30) / 2) + 160, 70, (((d.width/3) + d.width/2 - 30) / 2) + 190, 105);
	    }
	    if(isLeftLegVisible()) {
	    	g2.drawLine((((d.width/3) + d.width/2 - 30) / 2) + 160, d.height - 70, (((d.width/3) + d.width/2 - 30) / 2) + 130, d.height - 30);
	    }
	    if(isRightLegVisible()) {
	    	g2.drawLine((((d.width/3) + d.width/2 - 30) / 2) + 160, d.height - 70, (((d.width/3) + d.width/2 - 30) / 2) + 190, d.height - 30);
	    }
    }
	
	public void setHeadVisible(boolean tf) {
		this.headVisible = tf;
	}
	
	public boolean isHeadVisible() {
        return headVisible;
    }
	
	public void setBodyVisible(boolean tf) {
		this.bodyVisible = tf;
	}
	
	public boolean isBodyVisible() {
        return bodyVisible;
    }
	
	public void setLeftArmVisible(boolean tf) {
		this.leftArmVisible = tf;
	}
	
	public boolean isLeftArmVisible() {
        return leftArmVisible;
    }
	
	public void setRightArmVisible(boolean tf) {
		this.rightArmVisible = tf;
	}
	
	public boolean isRightArmVisible() {
        return rightArmVisible;
    }
	
	public void setLeftLegVisible(boolean tf) {
		this.leftLegVisible = tf;
	}
	
	public boolean isLeftLegVisible() {
        return leftLegVisible;
    }
	
	public void setRightLegVisible(boolean tf) {
		this.rightLegVisible = tf;
	}
	
	public boolean isRightLegVisible() {
        return rightLegVisible;
    }
}