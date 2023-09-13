package spell;

import java.io.IOException;

public class SpellCorrector implements ISpellCorrector{
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
//        if(dictionaryFileName.length() != 0){
//            var file = new File(dictionaryFileName);
//            if(file.exists()){
//                var scanner = new Scanner(file);
//
//            }
//        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        return null;
    }
}
