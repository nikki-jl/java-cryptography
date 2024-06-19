/**
* Name: Nikki liu
* Pennkey: nikkiliu
* Execution: java Caesar
*
* Program Description: Program uses preliminary functions of changing 
int arrays to strings or strings to int arrays in order to decrypt or encrypt
a string, and eventually crack any code by using the key with the lowest 
frequency
*/

public class Caesar {
    /*
    * Description: converts a string to a symbol array,
    *              where each element of the array is an
    *              integer encoding of the corresponding
    *              element of the string.
    * Input:  the message text to be converted
    * Output: integer encoding of the message
    */
    public static int[] stringToSymbolArray(String str) {
        str = str.toUpperCase();
        int outputLength = str.length();
        int[] symbolArray = new int[outputLength];
        // traverse thru each character of str and convert to numbers 0-25
        for (int i = 0; i < outputLength; i++) { //loop over an array, not a string
            char currentChar = str.charAt(i); //*addition here
            //A-A=0, B-A=1
            symbolArray[i] = (int) (currentChar - 'A');
            // to build a string, you concatenate onto a string
        }
        return symbolArray;
    }
    
    /*
    * Description: converts an array of symbols to a string,
    *              where each element of the array is an
    *              integer encoding of the corresponding
    *              element of the string.
    * Input:  integer encoding of the message
    * Output: the message string
    */
    public static String symbolArrayToString(int[] symbols) {
        String charStr = "";
        // loop over each element of the array and save characters 
        for (int i = 0; i < symbols.length; i++) {
            // convert integers to characters 
            int currentInt = symbols[i];
            char convertedChar = (char) (currentInt + 'A');
            charStr += convertedChar;
        }
        return charStr;
    }
    
    /*
    * Description: shifts a letter that is represented by its according
    *              integer value by another letter represented by its
    *              integer value and returns the shifter resulting letter as
    *              an integer.
    * Input:  integer encoding of the letter to be shifted, and
    integer encoding of a letter that indicates how much to shift by
    * Output: the integer encoding of the final shifted letter
    */
    public static int shift(int symbol, int offset) {
        // if symbol has an integer value between 0-25, shift
        int wrapper = 0;
        if (symbol >= 0 && symbol <= 25) {
            symbol = symbol + offset;
            if (symbol > 25) {
                wrapper = symbol % 26;
                symbol = wrapper;
            }
        }
        return symbol;
    }
    
    /*
    * Description: unshifts a letter that is represented by its according
    *              integer value by another letter represented by its
    *              integer value and returns the unshifted resulting letter as
    *              an integer.
    * Input:  integer encoding of the letter to be unshifted, and
    integer encoding of a letter that indicates how much to unshift by
    * Output: the integer encoding of the final unshifted letter
    */                           
    public static int unshift(int symbol, int offset) {
        int unwrapper = 0;
        if (symbol >= 0 && symbol <= 25) {
            symbol = symbol - offset;
            if (symbol < 0) {
                unwrapper = symbol + 26;
                symbol = unwrapper;
            }
        }
        return symbol;
    }
    
    /*
    * Description: converts a string, given an integer to shift by,
    *              to an encrypted string, where each element of the 
    *              array is a shifted version of the original
    *              letter in that index.
    * Input:  the message string to be converted, and an integer 
    * encoding of a letter that indicates how much to shift by
    * Output: string encryption of the message
    */
    public static String encrypt(String message, int key) {
        // Convert String message into array of encoded 
        //integers using stringToSymbolArray
        int[] encodedInt = stringToSymbolArray(message);
        // for each symbol in the array, shift it by the given offset
        for (int i = 0; i < encodedInt.length; i++) {
            int currentSymbol = encodedInt[i];
            currentSymbol = shift(currentSymbol, key);
            // saves shifted integer back into encodedInt array
            encodedInt[i] = currentSymbol;
        }
        // return a string of the integer array using symbolArrayToString
        String encryptedStr = symbolArrayToString(encodedInt);
        return encryptedStr;
    }
    
    /*
    * Description: converts a string back to the original, 
    *              given an integer to shift by,
    *              to an encrypted string, where each element of the 
    *              array is a deshifted version of the shifter
    *              letter in that index.
    * Input:  the message string to be translated, and an integer 
    * encoding of a letter that indicates how much to deshift by
    * Output: original string of the encrypted message
    */
    public static String decrypt(String cipher, int key) {
        // Convert String message into array of encoded integers 
        int[] encodedInt = stringToSymbolArray(cipher);
        // for each symbol in the array, deshift it by the given offset
        for (int i = 0; i < encodedInt.length; i++) {
            int currentSymbol = encodedInt[i];
            currentSymbol = unshift(currentSymbol, key);
            // saves shifted integer back into encodedInt array
            encodedInt[i] = currentSymbol;
        }
        // return a string of the integer array using symbolArrayToString
        String decryptedStr = symbolArrayToString(encodedInt);
        return decryptedStr;
    }
    
    /*
    * Description: takes the file name containing english frequencies to
    *              be read and returns a double array of the 
    *              frequencies in the text file where the 0 index
    *              is 'A'
    * Input:  the english file of frequencies to be read
    * Output: double array of the frequencies
    */
    public static double[] getDictionaryFrequencies(String filename) {
        // takes as input the name of the file containing frequencies to be read
        double[] alphabetFrequency = new double[26];
        In inStream = new In(filename);
        // stores frequency doubles into a double array
        for (int i = 0; i < 26; i++) {
            double currentFrequency = inStream.readDouble();
            inStream.readLine();
            alphabetFrequency[i] = currentFrequency;
        }
        return alphabetFrequency;
    }
    
    /*
    * Description: takes the file name containing the frequencies to
    *              be read and returns a double array of the 
    *              frequencies in the text file where the 0 index
    *              is 'A'
    * Input:  integer array representing the cipher text file
    * Output: double array of the cipher text frequencies
    */
    public static double[] findFrequencies(int[] symbols) {
        // return double array of frequencies in alphabetical order
        // frequency of each character is the number of times it appears 
        //in the cipher text, divided by total number of letters
        int[] appearanceCounter = new int[26];

        // counts how many elements are letters
        double counter = 0;
        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i] >= 0 && symbols[i] <= 25) {
                counter++;
                appearanceCounter[symbols[i]]++;
            }
        }

        //find the frequency by dividing each element in appearanceCounter 
        //by the total number of letters
        double[] frequency = new double[appearanceCounter.length];
        for (int i = 0; i < frequency.length; i++) {
            frequency[i] = appearanceCounter[i] / counter;
        }
        return frequency;
    }

    
    /*
    * Description: finds the absolute value of the difference between
    *              a double array of the cipher frequencies and the 
    *.             corresponding english frequencies of each letter
    *              then returns a double of the score indicating 
    *              how close it is to decrypted English
    *              is 'A'
    * Input:  a double array representing the english language frequencies
    * and a double array of the ciphertext frequencies
    * Output: double score of how close the message is to decrypted English
    */
    public static double scoreFrequencies(double[] english, double[] currentFreqs) {
        double score = 0.0;
        for (int i = 0; i < english.length; i++) {
            double currentScore = Math.abs(currentFreqs[i] - english[i]);
            score = score + currentScore;
        }
        return score;
    }

    /*
    * Description: takes an encoded string with an unknown key
    *              and tries to decrypt using all possible keys. 
    *.             At each key, it scores the frequency to see
    *              which one is the lowest and looks the closest 
    *              to english, then returns the original message
    *              using that key
    * Input:  a double array representing the english language frequencies
    * and a string of the ciphertext content
    * Output: string of the cracked message
    */
    public static String crack(double[] englishFreq, String cipherMsg) {
        // Decrypt the cipherMsg 
        double[] guessedScores = new double[26];
        int[] decryptedMsgArray = new int[cipherMsg.length()];
        for (int i = 0; i < englishFreq.length; i++) {
            String decryptedMsg = decrypt(cipherMsg, i);
            // for each decryptedMsg, find the frequencies of the letters 
            // find int array of decryptedMsg String
            decryptedMsgArray = stringToSymbolArray(decryptedMsg);
            // find frequency of decryptedMsgArray
            double[] foundFrequency = findFrequencies(decryptedMsgArray);
            // gives the frequency score for each possible key
            double currentFreqScore = scoreFrequencies(englishFreq, foundFrequency);
            //System.out.println("Current FREQ SCORE: " + currentFreqScore);
            guessedScores[i] = currentFreqScore;
        }
        // find the minimum currentFreqScore
        double minScoreSoFar = Double.MAX_VALUE;
        int indexCounter = -1;
        for (int i = 0; i < guessedScores.length; i++) {
            if (guessedScores[i] < minScoreSoFar) {
                minScoreSoFar = guessedScores[i];
                indexCounter = i;
            }
        }
        int currentKey = decryptedMsgArray[indexCounter]; 
        return decrypt(cipherMsg, indexCounter);
    }
    
    public static void main(String[] args) {
        String instruction = args[0];
        String filenameInput = args[1];
        In inStream = new In(filenameInput);
        String inputStr = args[2];
        String fileContent = inStream.readAll();

        // converts string to the inputed char, then converts to ASCII
        char shifterChar = inputStr.charAt(0);
        int shifterInt = shifterChar - 'A';

        if (instruction.equals("encrypt")) {
            String encrypted = encrypt(fileContent, shifterInt);
            System.out.println(encrypted);
        } else if (instruction.equals("decrypt")) {
            String decrypted = decrypt(fileContent, shifterInt);
            System.out.println(decrypted);
        } else {
            double[] englishArray = getDictionaryFrequencies(inputStr);
            String cracked = crack(englishArray, fileContent);
            System.out.println(cracked);
        }
    }
}

