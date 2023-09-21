package spell;

import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector{

    Trie dict = new Trie();

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        if(!dictionaryFileName.isEmpty()){
            File file = new File(dictionaryFileName);
            if(file.exists()){
                Scanner scanner = new Scanner(file);
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
        INode temp = dict.find(inputWord);
        if(temp != null)
            return inputWord;

        // Change 1 candidates
        String cans = getCans(inputWord);
        String validCans = getValidCans(cans);
        if(!validCans.isEmpty())
            return bestChoice(validCans);

        // Change 2 candidates
        StringBuilder ch2Cans = new StringBuilder();
        Scanner scanner = new Scanner(cans);
        while(scanner.hasNext())
            ch2Cans.append(getCans(scanner.next()));
        validCans = getValidCans(ch2Cans.toString());
        if(!validCans.isEmpty())
            return bestChoice(validCans);

        return null;
    }

    private String getCans(String word){
        StringBuilder cans = new StringBuilder();

        cans.append(insert(word));
        cans.append(delete(word));
        cans.append(swap(word));
        cans.append(replace(word));

        return cans.toString();
    }

    private String getValidCans(String cans){
        StringBuilder validCans = new StringBuilder();
        Scanner scanner = new Scanner(cans);
        while(scanner.hasNext()){
            String tempScan = scanner.next();
            INode tempNode = dict.find(tempScan);
            if(tempNode != null) {
                validCans.append(tempScan);
                validCans.append("\n");
            }
        }
        return validCans.toString();
    }

    private String bestChoice(String validCans){
        int maxVal = 0;
        String bestCurr = null;
        String tempScan;
        INode tempNode;

        Scanner scanner = new Scanner(validCans);
        while(scanner.hasNext()){
            tempScan = scanner.next();
            tempNode = dict.find(tempScan);
            if(tempNode.getValue() > maxVal){
                maxVal = tempNode.getValue();
                bestCurr = tempScan;
            }
        }
        return bestCurr;
    }

    private String insert(String word){
        StringBuilder cans = new StringBuilder();
        for(int i=0; i<word.length()+1; i++){
            StringBuilder temp = new StringBuilder(word);
            for(int j=0; j<26; j++) {
                temp.insert(i, (char)(j + 'a'));
                cans.append(temp);
                cans.append("\n");
                temp.deleteCharAt(i);
            }
        }
        return cans.toString();
    }

    private String delete(String word){
        StringBuilder cans = new StringBuilder();
        for(int i=0; i<word.length(); i++){
            StringBuilder temp = new StringBuilder(word);
            temp.deleteCharAt(i);
            cans.append(temp);
            cans.append("\n");
        }
        return cans.toString();
    }

    private String swap(String word){
        StringBuilder cans = new StringBuilder();
        for(int i=0; i<word.length()-1; i++){
            StringBuilder temp = new StringBuilder(word);
            temp.setCharAt(i, word.charAt(i+1));
            temp.setCharAt((i+1), word.charAt(i));
            cans.append(temp);
            cans.append("\n");
        }
        return cans.toString();
    }

    private String replace(String word){
        StringBuilder cans = new StringBuilder();
        for(int i=0; i<word.length(); i++){
            StringBuilder temp = new StringBuilder(word);
            for(int j=0; j<26; j++){
                temp.setCharAt(i, (char)(j + 'a'));
                cans.append(temp);
                cans.append("\n");
            }
        }
        return cans.toString();
    }

}
