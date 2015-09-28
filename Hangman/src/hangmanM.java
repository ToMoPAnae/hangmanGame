import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

//Hangman made by Patrick Fischer for AP Computer Science.

public class hangmanM
{
    java.util.Random randomWord = new java.util.Random();
    public String fileHome = "placeholder";
    public String filename = "placeholder";
    public String fileSrc = "placeholder";
    public String guess ="placeholder";
    public String noWords = "Please add words to word bank";
    public String pat = "placeholder";
    public String word ="placeholder";
    public String wordBankUpdate = "placeholder";
    public String wordBankUpdater = "placeholder";
    
    File home;
    
    int counter = 0;
    int deleterLocation;
    int guessLength;
    int hangCounter = 0;
    int location;
    int namingIsGettingHard = 0;
    int numOfCorrect = 0;
    int prevGuess;
    int tooLarge =0;
    int wordBankSize;
    int wordEqual;
    int wordIndex;
    int wordsOrNot = 0;
    int wrdOrLttr;

    char deleter = ' ';
    char guessChar;
    char letterInput;

    char[] guessCharArray;
    char[] hideWordChar;
    char[] letterInputChar;
    char[] lettersLeftChar;
    
    ImageIcon hangStart;
    ImageIcon hangOne;
    ImageIcon hangTwo;
    ImageIcon hangThree;
    ImageIcon hangFour;
    ImageIcon hangEnd;
    ImageIcon icon;

    String again = "Would you like to play again?\n";
    String guessLower;
    String guessUpper;
    String hideWord = "_____";
    String incorrect = "Sorry but that guess is incorrect.";
    String lettersLeft = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z";
    String loseMessage;
    String newWord = "placeholder";
    String thankYou = "Thank you for playing my game\n";
    String wordBankString;
    String winMessage;
    String hanged;
    String win;
    
    public StringBuilder strBld = new StringBuilder();
    
    ArrayList<String> wordBank = new ArrayList<String>();
    
    
    public void reset() //resets the game if the player wants to play twice
    {
        lettersLeft = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z";
        guess ="placceholder";
        numOfCorrect = 0;
        hangCounter = 0;
        word = "placeholder";
        whatImg();
    }
    
    public void playHangman() //actually play the game
    {
    	reset();
    	getClassLocation();
    	createWordFile();
    	getImg();
    	getTheFile();
        startMessage();
        wordOrLetter(); //asks the user if they are going to attempt to guess the whole word or a letter
        askGuess(); //asks the user to input letter or word based on their previous guess
        loseMessage = "Sorry, but you could not guess the correct word " + word + " in time\n";
        winMessage = "You correctly guessed the word " + word + "\n";
        hanged = loseMessage + thankYou + again;
        win = winMessage + thankYou + again;
        checkGuess();
        
        do
        {
            wordOrLetter();
            askGuess();
            checkGuess();
        }
        while (numOfCorrect < 5 && hangCounter < 5);
        
        if (numOfCorrect == 5)
        {
            gameWin();
        }
        else
        {
            gameOver();
        }

    }
    
    public void getClassLocation()
    {
    	URL location = getClass().getProtectionDomain().getCodeSource().getLocation();
    	File file = new File(location.getPath());
    	home = file;
    	filename = home.toString();
    }
    
    public void getImg() //please overlook this part. I know the code is complete crap but I just wanted to get it done.
    {
    	String imgStart = "hangmanstart.png";
    	int lnStart = imgStart.length();
    	
    	String imgOne = "hangmanone.png";
    	int ln1 = imgOne.length();
    	
    	String imgTwo = "hangmantwo.png";
    	int ln2 = imgTwo.length();
    	
    	String imgThree = "hangmanthree.png";
    	int ln3 = imgThree.length();
    	
    	String imgFour = "hangmanfour.png";
    	int ln4 = imgFour.length();
    	
    	String imgEnd = "hangmanend.png";
    	int lnEnd = imgEnd.length();
    	
    	StringBuilder imgSB = new StringBuilder();
    	imgSB.append(fileSrc);
    	imgSB.append(imgStart);
    	int indexer = 0;
    	int indexer2 = 0;
    	
    	while(indexer != -1)
    	{
    		indexer = imgSB.indexOf("\\", indexer2);
    		if (indexer != -1)
    		{
    		imgSB.insert(indexer, "\\");
    		indexer2 = indexer + 2;
    		}
    		else
    		{
    			break;
    		}
    		
    	}
    	
    	//oh man this is so horrible I am sorry
    	
    	imgStart = imgSB.toString();
    	hangStart = new ImageIcon(imgStart);
    	int lnSB = imgSB.length();
    	imgSB.delete((lnSB - lnStart), lnSB);
    	
    	imgSB.append(imgOne);
    	imgOne = imgSB.toString();
    	hangOne = new ImageIcon(imgOne);
    	lnSB = imgSB.length();
    	imgSB.delete((lnSB - ln1), lnSB);
    	
    	imgSB.append(imgTwo);
    	imgTwo = imgSB.toString();
    	hangTwo = new ImageIcon(imgTwo);
    	lnSB = imgSB.length();
    	imgSB.delete((lnSB - ln2), lnSB);
    	
    	imgSB.append(imgThree);
    	imgThree = imgSB.toString();
    	hangThree = new ImageIcon(imgThree);
    	lnSB = imgSB.length();
    	imgSB.delete((lnSB - ln3), lnSB);
    	
    	imgSB.append(imgFour);
    	imgFour = imgSB.toString();
    	hangFour = new ImageIcon(imgFour);
    	lnSB = imgSB.length();
    	imgSB.delete((lnSB - ln4), lnSB);
    	
    	imgSB.append(imgEnd);
    	imgEnd = imgSB.toString();
    	hangEnd = new ImageIcon(imgEnd);
    	lnSB = imgSB.length();
    	imgSB.delete((lnSB - lnEnd), lnSB);
    }
    
    public ImageIcon whatImg() //this bit is also quite horrible
    {
    switch(hangCounter)
{
case 0:
	icon = hangStasrt;
	break;
case 1:
	icon = hangOne;
	break;
case 2:
    icon = hangTwo;
	break;
case 3:
     icon = hangThree;
     break;
case 4:
      icon = hangFour;
      break;
case 5:
     icon = hangEnd;
       break;
default:
      JOptionPane.showMessageDialog(null, "Somenthing went Wrong");
      System.exit(0);
       break;	   	
}
    	return icon;
    }

    public void wordOrLetter() //ask if user wants to guess word or letter
    { 
    	whatImg();
    	
        Object[] options = {"Guess letter", "Guess word", "Quit"};
        int k = JOptionPane.showOptionDialog(null, "Please make a guess \n Number of Correct: " + numOfCorrect + "\n Number of incorrect guesses: " + hangCounter + "\n Word: " + hideWord + "\n Letters Left \n" + lettersLeft, "Hangman",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, whatImg(), options, options[1]);
        if (k == JOptionPane.YES_OPTION)
        {
            wrdOrLttr = 1; //letter
        }
        else if (k == JOptionPane.NO_OPTION)
        {
            wrdOrLttr = 0; //word
        }
        else if (k == JOptionPane.CLOSED_OPTION)
		{
			System.exit(0);
		}
        else
        {
        	JOptionPane.showMessageDialog(null, "Thank you for playing my game!");
        	System.exit(0);
        }
    }

    public void askGuess() //ask for the user's guess
    {
        Object[] options = {null};
        guess = (String)JOptionPane.showInputDialog(null, "Please make a guess", "Hangman",
            JOptionPane.INFORMATION_MESSAGE, whatImg(), null, options[0]);
        if (guess == null)
        {
        	wordOrLetter();
        }
        guessUpper = guess.toUpperCase();
        guessLower = guess.toLowerCase();
    }

    public void checkGuess() //check to see if the word or letter matches. 1 for letter, 0 for word
    {
        letterInputChar = guessLower.toCharArray();
        prevGuess = (lettersLeft.indexOf(guessUpper));
        guessLength = guess.length();
        if (wrdOrLttr == 1 && !guess.equals("") && prevGuess != -1 && guessLength == 1) //letter
        {
            letterInput = letterInputChar[0];
            location = (word.indexOf(letterInput));
            if (location != -1)
            {
                while (location < 5)
                {   
                    hideWordChar = hideWord.toCharArray();
                    hideWordChar[location] = letterInput;
                    hideWord = new String(hideWordChar);
                    location = (word.indexOf(letterInput, location+1));
                    numOfCorrect++;
                    if (location == -1)
                    {
                        break;
                    }
                }
                updateLetters();
                correctGuess();
            }
            else
            {
                incorrectGuess();
                updateLetters();
            }
        }
        else if (wrdOrLttr == 1 && guessLength > 1)
        {
            JOptionPane.showMessageDialog(null, "Please input only one letter");
        }
        else if (wrdOrLttr == 0 && !guess.equals("")) //whole word
        {
            wordEqual = guess.compareToIgnoreCase(word);
            if (wordEqual == 0)
            {
                gameWin();
            }
            else
            {
                incorrectGuess();
            }
        }
        else if (guess.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Please make a guess");
        }
        else if (prevGuess == -1)
        {
            JOptionPane.showMessageDialog(null, "That has already been guessed");
        }
        else
        {
        	JOptionPane.showMessageDialog(null, "I'm not sure how you got here but the game will quit now\nThanks for playing!");
            System.exit(0);
        }
    }

    public void gameWin() //what happens when the game is won by the player
    {
        int p = JOptionPane.showConfirmDialog(null, win,
                "Congrats!", JOptionPane.YES_NO_OPTION);
        if (p == JOptionPane.YES_OPTION)
        {
            playHangman();
        }
        else if (p == JOptionPane.CLOSED_OPTION)
		{
			System.exit(0);
		}
        else
        {
        	JOptionPane.showMessageDialog(null, "Thanks for playing!");
            System.exit(0);
        }
    }

    private void gameOver() //what happens when the game is lost
    {
    	JOptionPane.showMessageDialog(null, null, "Game over", JOptionPane.ERROR_MESSAGE, whatImg());
        int b = JOptionPane.showConfirmDialog(null, hanged,
                "Sorry", JOptionPane.ERROR_MESSAGE, JOptionPane.YES_NO_OPTION);
        if (b == JOptionPane.YES_OPTION)
        {
            playHangman();
        }
        else if (b == JOptionPane.CLOSED_OPTION)
		{
			System.exit(0);
		}
        else
        {
        	JOptionPane.showMessageDialog(null, "Thanks for playing!");
            System.exit(0);
        }
    }

    public void incorrectGuess() //when guess is incorrect
    {
    	hangCounter++;
        JOptionPane.showMessageDialog(null, incorrect, "Testing", JOptionPane.INFORMATION_MESSAGE, whatImg());
    }

    public void correctGuess() //when guess is correct
    {
        JOptionPane.showMessageDialog(null, "That is a correct guess!", "Correct!", JOptionPane.INFORMATION_MESSAGE, whatImg());
    }

    public void updateLetters() //update the list of letters left for the user to guess
    {
        deleterLocation = lettersLeft.indexOf(guessUpper);
        guessCharArray = guessUpper.toCharArray();
        guessChar = guessCharArray[0];
        lettersLeftChar = lettersLeft.toCharArray();
        lettersLeftChar[deleterLocation] = deleter;
        lettersLeft = new String(lettersLeftChar);
        guess.toLowerCase();
    }
    
    public void createWordFile()

    {
    	try
    	{
    		int hold = filename.lastIndexOf('\\');
    		char[] temp = filename.toCharArray();
    		StringBuilder stBl = new StringBuilder();
    		for (int n = 1; n < hold+2; n++)
    		{
    			stBl.append(temp[n-1]);
    		}
    		String locale = "wordBank.txt";
    		fileSrc = stBl.toString();
    		stBl.append(locale);
    		fileHome = stBl.toString();
    		File file = new File(fileHome);
    		if (!file.exists())
    		{
    			file.createNewFile();
    			FileWriter pf = new FileWriter(file.getAbsoluteFile());
    			BufferedWriter pt = new BufferedWriter(pf);
    			pt.write(noWords);
    			pt.close();
    			wordsOrNot = 1;
    		}
    	}
    	catch(IOException e)
    	{
    		e.printStackTrace();
    	}
    }
    
    public void addToWordBank() //input the word that is going to be added to the word bank
    {
    	Object[] options3 = {null};
        newWord = (String)JOptionPane.showInputDialog(null, "Please input a five letter word", "Hangman",
                JOptionPane.PLAIN_MESSAGE, null, null, options3[0]);
        strBld.append(newWord);
        String temp = strBld.toString();
        try
        {
        	File file = new File(fileHome);
        	if(!file.exists())
        	{
        		file.createNewFile();
        	}
        	FileWriter fw = new FileWriter(file.getAbsoluteFile());
        	BufferedWriter bw = new BufferedWriter(fw);
        	bw.write(temp);
        	bw.close();
        }
        catch(IOException e)
        {
        	e.printStackTrace();
        }
        getTheFile();
    }
    
    public void startMessage() //message displayed at the start of the game
    {
    	Object options[] = {"Play game", "Display word bank"};
    	int startMessage = JOptionPane.showOptionDialog(null,"Welcome to hangman! \n Written by Patrick Fischer \n 5 wrong guesses and you will lose the game", "Hangman",
    			JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, whatImg(), options, options[1]);
    	if (startMessage == JOptionPane.NO_OPTION) //Display word bank
    	{
    		startMessagePartTwo();
    	}
    	else if (startMessage == JOptionPane.CLOSED_OPTION)  //Hit the close button
		{
			System.exit(0);
		}
    	else //Hit the play game button
    	{
    	}
    }
    
    public void startMessagePartTwo() //I didn't know what to call this but wanted the option to add more than one word to the word bank at a time
    {

		Object patrick[] = {"Play game", "Add to word bank"};
		int seeWordBank = JOptionPane.showOptionDialog(null, wordBankUpdater, "Hangman", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, whatImg(), patrick, patrick[1]);
		if (seeWordBank == JOptionPane.NO_OPTION)
		{
			addToWordBank();
			if (newWord.length() != 5)
	        {
	        	JOptionPane.showMessageDialog(null, "Sorry, but the word must be five letters in length");
	        	startMessagePartTwo();
	        }
			else if (wordBank.indexOf(newWord) != -1)
			{
				JOptionPane.showMessageDialog(null, "Sorry, but that word is already in the word bank");
				addToWordBank();
	        	startMessagePartTwo();
			}
			else if (seeWordBank == JOptionPane.CLOSED_OPTION)
			{
				System.exit(0);
			}
			else
			{
				wordBank.add(newWord); //adds word to word bank
				startMessage(); //takes user back to start screen
			}
		}
	}
    
	public void getTheFile()
	{
		BufferedReader br = null;
		String sCurrentLine;
		try
		{
			br = new BufferedReader(new FileReader(fileHome));
			while ((sCurrentLine = br.readLine()) != null)
			{
				pat = sCurrentLine;
			}
			char[] wordTemp = pat.toCharArray();
			int length = wordTemp.length;
			int length2 = length/5;
			int size = 0;
			StringBuilder sb = new StringBuilder();
			String[] wordBank = new String[length2];
			for (int n = 0; n <wordTemp.length + 1; n = n++)
			{
				if (n % 5 != 0 || n == 0)
				{
					sb.append(wordTemp[n]);
					n++;
				}
				else
				{
					String blah = sb.toString();
					wordBank[size] = blah;
					size++;
					if(size == length2)
					{
						break;
					}
					sb.delete(0, 5);
					sb.append(wordTemp[n]);
					n++;
				}
			}
			int k = 0;
			int pa = 0;
			StringBuilder tempo = new StringBuilder();
			StringBuilder tempo2 = new StringBuilder();
			for (int n = 1; n<wordBank.length; n++)
			{
				tempo2.append(wordBank[n - 1]);
				pa++;
			}
			tempo2.append(wordBank[pa]);
			wordBankUpdate = tempo2.toString();
			
			for (int n = 1; n<wordBank.length; n++)
			{
				tempo.append(wordBank[n - 1]);
				if (n%10 == 0 && n != 0)
				{
					tempo.append("\n");
				}
				else
				{
					tempo.append(" ");
				}
				k++;
			}
			tempo.append(wordBank[k]);
			if (wordsOrNot == 0)
			{
				wordBankUpdater = tempo.toString();
			}
			else
			{
				wordBankUpdater = noWords;
			}
			strBld.append(wordBankUpdate);
			java.util.Random randomNum = new java.util.Random();
			int index = randomNum.nextInt(length2);
			word = wordBank[index];
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(br != null)
				{
					br.close();
				}
			}
			catch(IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}
}
			
		
    
