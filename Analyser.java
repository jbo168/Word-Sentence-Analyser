import javax.swing.JOptionPane;

public class Analyser
{
  public static void main(String [] args)
  {
    int     choice;
    String  menuOption = "";  
    while ((menuOption != null) && (!(menuOption.equals("0"))))
    {  
      menuOption = getMenuOption();
      if (menuOption != null)
      {
	    choice = Integer.parseInt(menuOption);  
	    if (choice != 0)
      {
	      switch(choice)
	      {
	        case 1: analyzeVowelContentOfWordPhrase();   break;
          case 2: countConsonants();                   break;
          case 3: characterContent();                  break;
          case 4: keyboardRows();                      break;
          case 5: alternatingVowelsAndConsonants();    break;
	        case 6: determineLongestAndShortestWords();  break;
          case 7: anagramChecker();                    break;
          case 8: palindrome();                        break;  
        }
      }
     }
    }  
  }
 
  public static String getMenuOption()
  {
    String menuOptions = "1. Analyze vowel content of word/phrase." +	  
     					 "\n2. Analyze consonant content of word/phrase"    +
     					 "\n3. Analyze character content of word/phrase" +
     					 "\n4. Determine which row(s) of keys on QWERTY keyboard need to be used to type word/phrase" +
      				 "\n5. Analyze vowel and consonant content of word/phrase" +
     					 "\n6. Length of shortest/longest word and matches of each." +
     					 "\n7. Are two words/phrases anagrams of each other ?" +
               "\n8. Is word/phrase a palindrome ?" + 
     					 "\n0. Exit.";
    String menuMessage = "Choose number of option you wish to have executed";
    String errorMessage =  "Invalid menu selection.\n\nValid options are 0 to 8 inclusive.";
       	   errorMessage += "\nSelect OK to retry.";
    String errorHeader = "Error in user input";
    boolean validInput = false;
    String selection = "", menuChoicePattern = "[0-8]{1}";
    
    while (!(validInput))
    {
      selection = JOptionPane.showInputDialog(null, menuOptions, menuMessage, 3);
      if (selection == null || selection.matches(menuChoicePattern))
       validInput = true;
      else
       JOptionPane.showMessageDialog(null, errorMessage, errorHeader, 2);
    }				   
    return selection;
  }	

  // John Long-12132306 
  public static void analyzeVowelContentOfWordPhrase()
  {
    String validation[] = new String[4];
    validation[0] = "Enter word/phrase/sentence";
    validation[1] = "Vowel content analyzer";
    validation[2] = "([a-zA-Z]+)|((([a-zA-Z]+\\s)+)[a-zA-Z]+)";
    validation[3] = "Invalid input.";
    validation[3] += "\n\nEnter a word or phrase comprising ";
    validation[3] += "alphabetic/numeric characters only.";
    validation[3] += "\nSelect OK to retry.";

    String vowels = "aeiou";
    String input = getWordOrPhraseFromEndUser(validation);
    String tempInput = input.toLowerCase();
    tempInput = tempInput.replaceAll(" ","");

    boolean allPresent = false;
    String report = "";
    String aVowel;
    int vCount [] = {0,0,0,0,0}; 
    char vChar;
    String vowelPtrn = "[^aeiou]+";
    String alphaPattern = "[^aeiou]*[a]+[^aeiou]*[e]{1}[^aeiou]*[i]{1}[^aeiou]*[o]{1}[^aeiou]*[u]{1}[^aeiou]*";
    String reverseAlphaPattern = "[^aeiou]*[u]+[^aeiou]*[o]{1}[^aeiou]*[i]{1}[^aeiou]*[e]{1}[^aeiou]*[a]{1}[^aeiou]*";
    boolean inOrder = false;
         
    // check for any vowels
    if(tempInput.matches(vowelPtrn))
      report += "Input contains no vowels\n";

    // check for all vowels presence
    for(int i=0; i<vowels.length(); i++)
    {
     aVowel = vowels.substring(i,i+1);
     if(tempInput.indexOf(aVowel) == -1)
      allPresent = false;
     else
      allPresent = true;
    }


    report += "Vowel frequencies are\n";
    // count vowels for which there is at least 1 occurrence
     for(int i=0; i<vowels.length(); i++)
     {
      vChar = vowels.charAt(i);
      for(int j=0; j<tempInput.length(); j++)
      {
        if(vChar == tempInput.charAt(j))
        vCount[i]++;
      }

      if(vCount[i] > 0)
        report += vowels.substring(i,i+1) + " = "+ vCount[i] + "\n";
     }

    // check for alphabetic normal+reverse order
    if(allPresent)
    {
     report += "All vowels are present\n"; 
     
     if(tempInput.matches(alphaPattern))
      report += "Input contains vowels in alphabetic order";
     
     if(tempInput.matches(reverseAlphaPattern))
      report += "Input contains vowels in reverse alphabetic order";  

    }else
     report += "All vowels not present\n";  

    JOptionPane.showMessageDialog(null,report,"Report",1); 
  }


  // John Long-12132306
  public static void determineLongestAndShortestWords()
  {
    String validation[] = new String[4];
    validation[0] = "Enter word/phrase/sentence";
    validation[1] = "Word length/match analyzer";
    validation[2] = "([a-zA-Z0-9]+)|((([a-zA-Z0-9]+\\s)+)[a-zA-Z0-9]+)";
    validation[3] = "Invalid input.";
    validation[3] += "\n\nEnter a word or phrase comprising ";
    validation[3] += "alphabetic/numeric characters only.";
    validation[3] += "\nSelect OK to retry.";


    String input = getWordOrPhraseFromEndUser(validation);
    String tempInput = input.toLowerCase();
    tempInput = tempInput.trim(); // for leading or trailing spaces
    String words [] = tempInput.split(" ");
    String report = "";
    String longestMatches = "";
    String shortestMatches = "";
    
    // assume 1st word is longest and shortest
    // longword/shortword are placeholders for the longest/shortest words
    String longWord = words[0];
    String shortWord = words[0];

    int longest = longWord.length();
    int shortest = shortWord.length();

    // iterate to find longest and shortest sizes first
    for(int i=0; i<words.length; i++)
    {
      // compare and update longest/shortest word
      if(words[i].length() > longest)
        {
          longWord = words[i];
          longest = longWord.length();
        }
      else if(words[i].length() < shortest)
       {
        shortWord = words[i];
        shortest = shortWord.length();
       }
    }

    // iterate to find matches for shortest/longest sizes in input
    for(int i=0; i<words.length; i++)
    {
      // if word matches longest/shortest then populate appropriate array without duplicates
      if((words[i].length() == longest) && (words[i].equalsIgnoreCase(longWord) == false))
        longestMatches += words[i] + " ";
        
      else if((words[i].length() == shortest) && (words[i].equalsIgnoreCase(shortWord) == false))
        shortestMatches += words[i] + " ";  
    }
        
    report += "Max length = " + longest + "\n" + "Matching word(s) are\n"+ longWord + " " + longestMatches + "\n";
    report += "Min length = " + shortest + "\n" + "Matching word(s) are\n" + shortWord + " " + shortestMatches + "\n";

    JOptionPane.showMessageDialog(null,report,"Report",1);       
  }
      
    

   

  public static void keyboardRows() 
  {
        //Jamie McLoughlin
        String tempInput = "";
        String validation[] = new String[4];
        validation[0] = "Please enter words/phrases \n with alphabetic characters only.";
        validation[1] = "Alternating Vowels and Consonants.";
        validation[2] = "([a-zA-Z]+)|((([a-zA-Z]+\\s)+)[a-zA-Z]+)";
        validation[3] = "Invalid input.";
        validation[3] += "\n\nEnter a word or phrase comprising ";
        validation[3] += "alphabetic characters only.";
        validation[3] += "\nSelect OK to retry.";

        //Get word from end used and cheak to see if it only Alphabetic Characters Only.
        String userInput = getWordOrPhraseFromEndUser(validation);
        String message = "To tpye \"" + userInput + "\" you need to use";

        //Get rid of any white spaces and turn any uppercase to lowercase.
        tempInput = userInput.replaceAll("[\\s]", "");
        tempInput = tempInput.toLowerCase();

        /*This regular expression starts with 0 or more character followed by 1 or more of the first row letter . 
         Then by 0 or more character all this happens 1 or more time. then its followed by the same thing 2 time more but for
         letters in row2 and in row3. */
        String pattern1 = "(\\w*[qwertyuiop]+\\w*)+(\\w*[asdfghjkl]+\\w*)+(\\w*[zxcvbnm]+\\w*)+";
        String pattern2 = "\\w*[qwertyuiop]+\\w*";
        String pattern3 = "\\w*[asdfghjkl]+\\w*";
        String pattern4 = "\\w*[zxcvbnm]+\\w*";

        //Checks the regular expression matches the user's input.
        if (tempInput.matches(pattern1))
            message = "\nAll rows are need 1. 2. and 3.";

        else {
            if (tempInput.matches(pattern2))
                message += "\nRow 1.";

            if (tempInput.matches(pattern3))
                message += "\nRow 2.";

            if (tempInput.matches(pattern4))
                message += "\nRow 3.";
        }
        JOptionPane.showMessageDialog(null, message, validation[1], 1);
    }
    public static void alternatingVowelsAndConsonants() 
  {
        //Jamie McLoughlin
        String tempInput = "";
        String validation[] = new String[4];
        validation[0] = "Please enter words/phrases \n with alphabetic characters only.";
        validation[1] = "Alternating Vowels and Consonants.";
        validation[2] = "([a-zA-Z]+)|((([a-zA-Z]+\\s)+)[a-zA-Z]+)";
        validation[3] = "Invalid input.";
        validation[3] += "\n\nEnter a word or phrase comprising ";
        validation[3] += "alphabetic characters only.";
        validation[3] += "\nSelect OK to retry.";

        //Get word from end used and cheak to see if it only Alphabetic Characters Only.
        String userInput = getWordOrPhraseFromEndUser(validation);
        String message1 = userInput + "\n\nDoes include\nAlternating Vowels and Consonants.";
        String message2 = userInput + "\n\nDoes not include\nAlternating Vowels and Consonants.";

        //Get rid of any white spaces and turn any uppercase to lowercase.
        tempInput = userInput.replaceAll("[\\s]", "");
        tempInput = tempInput.toLowerCase();

        /*This regular expression starts with a Consonants followed by a Vowels and alternating until it either ends with 
         or without a Consonants or it will do the complete opposite starting with a Vowel.*/
        String pattern = "(([^aeiou][aeiou])+[^aeiou]?)|(([aeiou][^aeiou])+[aeiou]?)";

        //Checks the regular expression matches the user's input.
        if (tempInput.matches(pattern))
            JOptionPane.showMessageDialog(null, message1, validation[1], 1);
        else
            JOptionPane.showMessageDialog(null, message2, validation[1], 1);
    }

  //Daire Lavelle-16192249
  public static void characterContent()
  {
    String validation[] = new String[4];
    validation[0] = "Enter a word/phrase to have its character content analysed";
    validation[1] = "Character Content";
    validation[2] = ",*";
    validation[3] = "Invalid input.";
    validation[3] += "\n\nEnter a word or phrase comprising ";
    validation[3] += "alphabetic characters only.";
    validation[3] += "\nSelect OK to retry.";

    String phrase=getWordOrPhraseFromEndUser(validation);
    String out="";
    String symbols="";
    int numberOfDigits=0; 
    int numberOfLetters=0; 
    int numberOfSymbols=0;
    int[] digitFrequency=new int[10]; 
    int[] letterFrequency=new int[52]; 
    boolean symbolBool=false, isValidInput=true;

    //all input is valid except an empty string
    if (phrase==null)
      isValidInput=false;
    if (isValidInput)
    {
      phrase=phrase.trim();
      for (int i=0; i<phrase.length(); i++)  //loop through each character in the phrase
      {
        char currentChar=phrase.charAt(i);
        if (Character.getType(currentChar)==9)
        {
          //code for decimal digit
          numberOfDigits++;
          digitFrequency[(int)currentChar-48]++;
        }
        else if ((int)currentChar>=97 && (int)currentChar<=122)
        {
          //code for a lower case letter
          numberOfLetters++;
          letterFrequency[(int)currentChar-97]++;
        }
        else if ((int)currentChar>=65 && (int)currentChar<=90)
        {
          //code for upper case letter
          numberOfLetters++;
          letterFrequency[(int)currentChar-39]++;
        }
        else if ((int)currentChar!=32)
        {
          //code for other symbol
          symbols+=currentChar; //creates a string of just the symbols
          numberOfSymbols++;
        }
      }

      char[] symbolArray=symbols.toCharArray();
      int[] symbolFrequency=new int[symbolArray.length];
      //The frequency of the first occurence of a symbol in symbolArray will be 
      //stored in the corresponding index of symbolFrequency. When the output
      //String is constructed, it will ignore any symbols whose frequency is 0  
      for (int i=0; i<symbols.length(); i++) 
      {
          int j=0;
          while (symbolArray[j]!=(symbolArray[i])) 
            j++;
          symbolFrequency[j]++;
      }

      //The rest of the code constructs the output String
      if (numberOfLetters>0)
      {
        out+="Number of letters: "+numberOfLetters+"\nFrequency of letters:\n";
        for (int i=0; i<26; i++)
        {
          if (letterFrequency[i]!=0)
            out+=String.format("%1$c:%2$3d\n", (char)(i+97), letterFrequency[i]);
        }
        for (int i=26; i<letterFrequency.length; i++)
        {
          if (letterFrequency[i]!=0)
            out+=String.format("%1$c:%2$3d\n", (char)(i+39), letterFrequency[i]);
        }
      }

      if (numberOfDigits>0)
      {
        out+="Number of digits: "+numberOfDigits+"\nFrequency of digits:\n";
        for (int i=0; i<digitFrequency.length; i++)
        {
          if (digitFrequency[i]!=0)
            out+=String.format("%1$c:%2$3d\n", i, digitFrequency[i]);
        }
      }

      if (numberOfSymbols>0)
      {
        out+="Number of other symbols: "+numberOfSymbols+"\nFrequency of other symbols:\n";
        for (int i=0; i<symbolFrequency.length; i++)
        {
          if (symbolFrequency[i]!=0)
            out+=String.format("%1$c:%2$3d\n", symbolArray[i], symbolFrequency[i]);
        }
      }
      JOptionPane.showMessageDialog(null, out, "Character Frequency",1);
    }
    else
      JOptionPane.showMessageDialog(null, "Invalid input. A word or phrase is required.", "Error", 2);
  }


//Daire Lavelle-16192249
  public static void palindrome()
  {   
    String validation[] = new String[4];
    validation[0] = "The program will determine whether or not the phrase you enter is a palindrome";
    validation[1] = "Palindrome";
    validation[2] = "([a-zA-Z]+)|((([a-zA-Z]+\\s)+)[a-zA-Z]+)";
    validation[3] = "Invalid input.";
    validation[3] += "\n\nEnter a word or phrase comprising ";
    validation[3] += "alphabetic/numeric characters only.";
    validation[3] += "\nSelect OK to retry.";

    String phrase=getWordOrPhraseFromEndUser(validation);
    phrase = phrase.toLowerCase();
    String phraseLetters="";
    String phraseWords;
    String out="";
    String[] phraseWordsArray;
    char currentChar;
    boolean lettersArePalindrome=true, wordsArePalindrome=true, stop=false, isValidInput=true;

    phrase=phrase.trim();
    //make a new string without symbols, numbers or whitespace.
    phraseLetters=phrase.replaceAll("\\s","");
    //compare the first letter to the last, then the second letter to the second last
    //until they're not the same
    char[] phraseLettersArray=phraseLetters.toCharArray();
    int i=0;
    while(phraseLettersArray[i]==phraseLettersArray[phraseLettersArray.length-i-1] && i<=(phraseLettersArray.length)/2)
    {
      i++;
    }
    //if the letters were all the same by the time the middle characters were 
    //checked, then it's a palindrome. No need to compare characters a second
    //time.
    if (!(i>=(phraseLettersArray.length)/2))
      lettersArePalindrome=false;
    phraseWords=phrase.replaceAll("[^a-z^A-Z^0-9^\\s]",""); //remove all punctuation from the phrase
    phraseWords=phrase.replaceAll("\\s+"," ");  
    phraseWordsArray=phraseWords.split(" ");  //split the phrase into an array of words
    //if the array is of length 1, the while will attempt to comare the 0th
    // index with the -1st index which crashes
    if (phraseWordsArray.length>1)
    {
      i=0;
      while(phraseWordsArray[i].equals(phraseWordsArray[phraseWordsArray.length-i-1]) && i<=(phraseWordsArray.length)/2)
      {
        i++;
      }
      if (!(i>=(phraseWordsArray.length)/2))
        wordsArePalindrome=false;
    }
    else
      wordsArePalindrome=false;
    //construct the output String
    if (lettersArePalindrome)
      out+="This phrase's letters form a palindrome.\n";
    if (wordsArePalindrome && phraseWordsArray.length>1)
      out+="This phrases's words form a palindrome.\n";
    if (!wordsArePalindrome && !lettersArePalindrome)
      out+="This phrse is not a palindrome.\n";
    JOptionPane.showMessageDialog(null, out, "Is the phrase a palidrome",1);
  }

  public static void countConsonants()
{

  String validation[] = new String[4];
         validation[0] = "The program will analyze consonant content of input";
         validation[1] = "Count Consonants";
         validation[2] = "([a-zA-Z]+)|((([a-zA-Z]+\\s)+)[a-zA-Z]+)";
         validation[3] = "Invalid input.";
         validation[3] += "\n\nEnter a word or phrase comprising ";
         validation[3] += "alphabetic/numeric characters only.";
         validation[3] += "\nSelect OK to retry.";

  String word = getWordOrPhraseFromEndUser(validation);
  int length = word.length();
  int[] frequency = new int[length];
  int[] cons = new int[length];
  String result = "";
  int count = 0;
  int car;
  
  word = word.toLowerCase();                         //The word is changed to lowercase
  word = word.replaceAll("[\\W]","");                                    //The word is left with letters only
  
  for(int i = 0; i<length;i++)                                           
  {
    car=(int)word.charAt(i);                       //stores character at index i in temp variable 'car'
    if(!(car == 97 ||car == 101 ||car == 105||car == 111||car == 117)) //if the char is not a vowel put it into the array cons
    {
      cons[count] = car;
      count++;                          
    }
  }
  
  sorting(cons);
  frequency = counting(cons,frequency);

  for(int i = 0;i<cons.length;i++)      //For the amount of consonants
  {
    if (cons[i]!=0 && frequency[i]!=0)    //Due to the counting method this if is necessary
      result += ((char)cons[i] + "   " + frequency[i] + "   time(s)\n");
  }
  
  JOptionPane.showMessageDialog(null,result);
}
public static void sorting(int[] cons)      //This method sorts the characters using their ASCII code
{                       //Bubble sort
  int pass, comparison, temp;
  boolean sorted = false;
  for (pass = 1; pass <= (cons.length - 1) && !sorted; pass++)
  {
    sorted = true;  
    for (comparison = 1; comparison <= (cons.length - pass); comparison++)
    {
      if (cons[comparison - 1] < cons[comparison])
      {
        temp                 = cons[comparison - 1];
        cons[comparison - 1] = cons[comparison];
        cons[comparison]     = temp;
        sorted                 = false;
      } 
    } 
  }
} 
public static int[] counting(int[] cons,int[] frequency) //This method counts the frequency of the consonants and returns that array
{
for (int i = 0; (i < cons.length - 1); i++)         
  {
  int j=0;                      
  while (cons[j]!=cons[i] && cons[j]!=0)        
    j++;                      
  frequency[j]++;
  } 
  return frequency;
}


public static void anagramChecker()
{
  String validation[] = new String[4];
  validation[0] = "Enter a word/phrase to check if it's an anagram";
  validation[1] = "Anagram Checker";
  validation[2] = "([a-zA-Z]+)|((([a-zA-Z]+\\s)+)[a-zA-Z]+)";
  validation[3] = "Invalid input.";
  validation[3] += "\n\nEnter a word or phrase comprising ";
  validation[3] += "alphabetic characters only.";
  validation[3] += "\nSelect OK to retry.";

  String one = getWordOrPhraseFromEndUser(validation);
  String two = getWordOrPhraseFromEndUser(validation);
  
  one = one.toLowerCase();
  two = two.toLowerCase();          //puts both strings into lower case
  
  one = one.replaceAll("[\\W]","");
  two = two.replaceAll("[\\W]","");     //replaces both strings with letters only
  
  one=sorting(one);
  two=sorting(two);             //sorts strings by character ASCII code
  
  boolean check = check(one,two);       //simple method to make check more efficient
  if(check && one.equals(two))        //compares both arrays nad if they are equal they are anagrams
    JOptionPane.showMessageDialog(null,"These words/phrases are anagrams of each other");
  else
    JOptionPane.showMessageDialog(null,"These are not anagrams");
    
}

public static boolean check(String one,String two)
{
  if(one.length() != two.length())
    return false;
  else
    return true;
}

public static String sorting(String word)   //bubble sort to organise characters by their ASCII code
{
  int pass, comparison;
  char temp;
  boolean sorted = false;
  char[] chars = word.toCharArray();
  for (pass = 1; pass <= (chars.length - 1) && !sorted; pass++)
  {
    sorted = true;  
    for (comparison = 1; comparison <= (chars.length - pass); comparison++)
    {
      if (chars[comparison - 1] < chars[comparison])
      {
        temp                 = chars[comparison - 1];
        chars[comparison - 1] = chars[comparison];
        chars[comparison]     = temp;
        sorted                 = false;
      } 
    } 
  }
  String stringSorted = new String(chars);
  return stringSorted;
}


  
  public static String getWordOrPhraseFromEndUser(String validation[])
  {
    /*  Jamie McLoughlin
        validation [0] = The Window Message.
        validation [1] = The Window Title.
        validation [2] = The Regular expression.
        validation [3] = The Error Message.
    */
    boolean validInput = false;
    String userInput = ""; 
    	
   while (!(validInput)) 
    {
      userInput = JOptionPane.showInputDialog(null, validation[0], validation[1], 3);
      if (userInput == null || userInput.matches(validation[2]))
       validInput = true;
      else
       JOptionPane.showMessageDialog(null, validation[3], "Error in user input", 2);
    }
    
    return userInput;
  }

   
} 