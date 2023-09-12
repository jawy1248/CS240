package spell;

import java.io.IOException;

/**
 * A main class for providing an entry point to your spelling corrector. This
 * class is not used by the passoff program. However, you will need to alter
 * this code to reference your SpellCorrector implementation.
 */
public class Main {

	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws IOException {
		String dictionaryFileName = null;
		String inputWord = null;

		if (args.length == 2) {
			dictionaryFileName = args[0];
			inputWord = args[1];
		}
		else{
			System.out.println("Invalid user input");
		}
		System.out.println("Spell <" + dictionaryFileName + "> <" + inputWord + ">");

		//
		// Create an instance of your corrector here
		//
		ISpellCorrector corrector = new SpellCorrector();

		corrector.useDictionary(dictionaryFileName);
		String suggestion = corrector.suggestSimilarWord(inputWord);
		if (suggestion == null) {
			suggestion = "No similar word found";
		}

		System.out.println("Suggestion is: " + suggestion);
	}

}
