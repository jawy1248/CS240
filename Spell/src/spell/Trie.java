package spell;

public class Trie implements ITrie{

    private INode root = new Node();
    private int nodeCount = 1;
    private int wordCount = 0;

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
            if (root.getChildren()[index] == null) {
                root.getChildren()[index] = new Node();
                nodeCount++;
            }
            temp = root.getChildren()[index];
            INode temp2;
            for(int i=1; i<word.length(); i++){
                letter = word.charAt(i);
                index = getIndex(letter);
                temp2 = step(temp, letter);
                if(temp2 == null){
                    temp.getChildren()[index] = new Node();
                    nodeCount++;
                    temp = temp.getChildren()[index];
                }
                else{
                    temp = temp2;
                }
            }
        }
        // increase counter of the word
        temp.incrementValue();
        if(temp.getValue() == 1)
            wordCount++;
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
        if(temp == null || temp.getValue() == 0)
            return null;
        // return found Trie
        return temp;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public String toString() {
        StringBuilder curWord = new StringBuilder();
        StringBuilder output = new StringBuilder();
        stringHelper(root, curWord, output);
        return output.toString();
    }

    private void stringHelper(INode rootNode, StringBuilder curWord, StringBuilder output){
        INode child;
        char childLetter;

        if(rootNode.getValue() > 0){
            // Append nodes word to output
            output.append(curWord.toString());
            output.append("\n");
        }

        // look at all the children
        for(int i=0; i<26; i++){
            child = rootNode.getChildren()[i];
            if(child != null) {
                childLetter = (char) ('a' + i);
                curWord.append(childLetter);
                stringHelper(child, curWord, output);
                curWord.deleteCharAt(curWord.length() - 1);
            }
        }
    }


    @Override
    public int hashCode() {
        // values to consider
        // word count, node count, index of the root node's non-null children
        int sumInd = 0;
        for(int i=0; i<26; i++){
            if(root.getChildren()[i] != null)
                    sumInd += i;
        }
        // return unique combination of values
        return (3*wordCount + 5*nodeCount + 7*sumInd);
    }

    @Override
    public boolean equals(Object o){
        // check if o is null, or doesn't have the same class ----- check if o is the exact same object
        if(o == null || o.getClass() != this.getClass())
            return false;
        if(o == this)
            return true;

        Trie dict = (Trie)o;

        int oWord = dict.getWordCount();
        int thisWord = this.getWordCount();
        int oNode = dict.getNodeCount();
        int thisNode= this.getNodeCount();

        if(oWord != thisWord || oNode != thisNode)
            return false;

        return eqHelper(this.root, dict.root);
    }

    private boolean eqHelper(INode n1, INode n2){
        // compare n1 and n2
            // are the counts the same
            // do the nodes have non-null children in the same location
        if(n1.getValue() != n2.getValue())
            return false;
        for(int i=0; i<26; i++){
            INode child1 = n1.getChildren()[i];
            INode child2 = n2.getChildren()[i];

            if(child1 == null){
                if(child2 != null)
                    return false;
            }else if(child2 == null) {
                return false;
            }
        }

        // recurse on children and compare the child subtrees
        boolean finalTest = true;
        for(int i=0; i<26; i++){
            INode child1 = n1.getChildren()[i];
            INode child2 = n2.getChildren()[i];
            if(child1 != null)
                finalTest = eqHelper(child1, child2);
            if(!finalTest)
                break;
        }
        return finalTest;
    }
}

