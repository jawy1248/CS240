package spell;

public class Trie implements ITrie{

    private INode root = new Node();

    @Override
    public void add(String word) {
        // lowercase word
        word = word.toLowerCase();
        // try to find word in current trie
        INode temp = find(word);
        // if word is not in trie, add it
        if(temp == null){
            char letter = word.charAt(0);
            int index = getIndex(letter);
            root.getChildren()[index] = new Node();
            temp = root.getChildren()[index];
            INode temp2;
            for(int i=1; i<word.length(); i++){
                letter = word.charAt(i);
                index = getIndex(letter);
                temp2 = step(temp, letter);
                if(temp2 == null){
                    temp.getChildren()[index] = new Node();
                    temp = temp.getChildren()[index];
                }
                else{
                    temp = temp2;
                }
            }
        }
        // increase counter of the word
        temp.incrementValue();
    }

    public int getIndex(char letter){
        return (letter - 'a');
    }

    public INode step(INode rootNode, char letter){
        // get index from ascii
        int index = getIndex(letter);

        // if root is null, return null
        if(rootNode == null)
            return null;

        // else, step into child
        return rootNode.getChildren()[index];
    }

    @Override
    public INode find(String word) {
        // lowercase word and parse, letter by letter
        word = word.toLowerCase();
        char letter = word.charAt(0);
        // step through Trie and try to find words
        INode temp = step(root, letter);

        for(int i=1; i<word.length(); i++){
            // if we ever reach null, the tree is over and the word isn't there
            if(temp == null)
                return null;

            // look at each letter and step through
            letter = word.charAt(i);
            temp = step(temp, letter);
            if(temp == null)
                return null;
        }
        // if the value is 0, the word isn't really there
        if(temp.getValue() == 0)
            return null;
        // return found Trie
        return temp;
    }

    @Override
    public int getWordCount() {
        return wordCountHelper(root);
    }

    public int wordCountHelper(INode rootNode){
        // if we hit an empty node, return 0
        if(rootNode == null)
            return 0;
        // otherwise, look at each of the children
        int sum = 0;
        INode child;
        for(int i=0; i<26; i++){
            // for each child, check children and so on
            child = rootNode.getChildren()[i];
            sum += nodeCountHelp(child);
        }
        if(rootNode.getValue() == 0)
            return 0;
        if(sum != 0)
            return 1 + sum;
        return 1;
    }

    @Override
    public int getNodeCount() {
        return nodeCountHelp(root);
    }

    public int nodeCountHelp(INode rootNode){
        if(rootNode == null)
            return 0;

        int sum = 0;
        for(int i=0; i<26; i++){
            sum += nodeCountHelp(rootNode.getChildren()[i]);
        }
        if(sum != 0)
            return 1 + sum;
        return 1;
    }
}
