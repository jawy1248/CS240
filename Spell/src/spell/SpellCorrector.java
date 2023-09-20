package spell;

import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector{

    private ITrie dict = new Trie();

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        if(!dictionaryFileName.isEmpty()){
            var file = new File(dictionaryFileName);
            if(file.exists()){
                var scanner = new Scanner(file);
                while(scanner.hasNext())
                    dict.add(scanner.next());
            }
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        if(inputWord.isEmpty())
            return null;

        inputWord = inputWord.toLowerCase();
        INode choice;
        // look for word in trie, if it is there...return it. It is a valid word.
        choice = dict.find(inputWord);
        if(choice != null)
            return inputWord;

        // if not, create change 1 candidates and make sure they are valid
        String ch1Can = getCandidates(inputWord);
        StringBuilder validCans = new StringBuilder(getValidCandidates(ch1Can));

        // if there are words in the validCans list, return the best
        if(!validCans.isEmpty())
            return chooseBest(validCans.toString());

        // if no valid change 1 candidates, get change 2 candidates and validate them
        Scanner scanner = new Scanner(ch1Can);
        String tempScan;
        StringBuilder ch2Can = new StringBuilder();
        while(scanner.hasNext()) {
            tempScan = scanner.next();
            ch2Can.append(getCandidates(tempScan));
        }
        validCans = new StringBuilder(getValidCandidates(ch2Can.toString()));

        // if there are words in the validCans list, return the best
        if(!validCans.isEmpty())
            return chooseBest(validCans.toString());

        // if no best choices now, return null
        return null;
    }

    private String getValidCandidates(String cans){
        INode tempNode;
        String tempScan;
        StringBuilder validCans = new StringBuilder();
        var scanner = new Scanner(cans);
        while(scanner.hasNext()){
            tempScan = scanner.next();
            tempNode = dict.find(tempScan);
            if(tempNode != null) {
                // if it is a valid word, add the word to valid word canidates.
                validCans.append(tempScan);
                validCans.append("\n");
            }
        }
        return validCans.toString();
    }

    private String chooseBest(String validCans){
        // from valid candidates, check frequency and return the one with the highest
        int val;
        int maxVal = 0;
        String tempScan;
        INode tempNode;
        String currBest = null;
        Scanner scanner = new Scanner(validCans);

        while (scanner.hasNext()) {
            tempScan = scanner.next();
            tempNode = dict.find(tempScan);
            val = tempNode.getValue();
            if (val > maxVal) {
                maxVal = val;
                currBest = tempScan;
            }
        }
        return currBest;
    }

    private String getCandidates(String word){
        StringBuilder candidates = new StringBuilder();

        candidates.append(delete(word));
        candidates.append(insert(word));
        candidates.append(swap(word));
        candidates.append(replace(word));

        return candidates.toString();
    }

    private String delete(String word){
        StringBuilder candidates = new StringBuilder();

        for(var i=0; i<word.length(); i++){
            StringBuilder temp = new StringBuilder(word);
            temp.deleteCharAt(i);

            candidates.append(temp.toString());
            candidates.append("\n");
        }
        return candidates.toString();
    }

    private String insert(String word){
        StringBuilder candidates = new StringBuilder();

        for(var i=0; i<word.length()+1; i++){
            StringBuilder temp = new StringBuilder(word);

            char varA = 'a';
            for(var j=0; j<26; j++){
                temp.insert(i, (char)(varA+j));
                candidates.append(temp.toString());
                candidates.append("\n");
                temp.deleteCharAt(i);
            }
        }
        return candidates.toString();
    }

    private String swap(String word){
        StringBuilder candidates = new StringBuilder();

        for(var i=0; i<word.length()-1; i++){
            StringBuilder temp = new StringBuilder(word);
            char chInd1 = temp.charAt(i);
            char chInd2 = temp.charAt(i+1);

            temp.setCharAt(i, chInd2);
            temp.setCharAt(i+1, chInd1);

            candidates.append(temp.toString());
            candidates.append("\n");
        }
        return candidates.toString();
    }

    private String replace(String word){
        StringBuilder candidates = new StringBuilder();

        for(var i=0; i<word.length(); i++){
            StringBuilder temp = new StringBuilder(word);

            char varA = 'a';
            for(var j=0; j<26; j++){
                temp.setCharAt(i, (char)(varA+j));
                candidates.append(temp.toString());
                candidates.append("\n");
            }
        }
        return candidates.toString();
    }
}