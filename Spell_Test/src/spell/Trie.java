package spell;

public class Trie implements ITrie{

    private Node root = new Node();
    private int wordCount = 0;
    private int nodeCount = 1;

    @Override
    public void add(String word) {
        word = word.toLowerCase();
        INode temp = find(word);
        if(temp == null) {
            int ind = getIndex(word, 0);
            temp = root.getChildren()[ind];
            if (temp == null) {
                root.getChildren()[ind] = new Node();
                nodeCount++;
            }
            temp = root.getChildren()[ind];
            INode temp2;
            for (int i = 1; i < word.length(); i++) {
                ind = getIndex(word, i);
                temp2 = temp.getChildren()[ind];
                if(temp2 == null){
                    temp.getChildren()[ind] = new Node();
                    nodeCount++;
                    temp = temp.getChildren()[ind];
                }else
                    temp = temp2;
            }
        }
        temp.incrementValue();
        if(temp.getValue() == 1)
            wordCount++;
    }

    @Override
    public INode find(String word) {
        word = word.toLowerCase();
        int ind = getIndex(word, 0);
        INode temp = root.getChildren()[ind];
        for(int i=1; i<word.length(); i++){
            if(temp == null)
                return null;
            ind = getIndex(word, i);
            temp = stepDown(temp, ind);
        }
        if(temp == null || temp.getValue()==0)
            return null;
        return temp;
    }

    private int getIndex(String word, int ind){
        char letter = word.charAt(ind);
        int charInd = (letter- 'a');
        return charInd;
    }

    private INode stepDown(INode n1, int ind){
        if(n1 == null)
            return null;
        return n1.getChildren()[ind];
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    public int hashCode(){
        int sumInd = 0;
        for(int i=0; i<26; i++){
            if(root.getChildren()[i] != null)
                sumInd += i;
        }
        return (nodeCount + 3*wordCount + 5*sumInd);
    }

    public String toString(){
        StringBuilder curr = new StringBuilder();
        StringBuilder output = new StringBuilder();
        strHelper(root, curr, output);
        return output.toString();
    }

    private void strHelper(INode n1, StringBuilder curr, StringBuilder output){
        if(n1.getValue() != 0){
            output.append(curr);
            output.append("\n");
        }

        for(int i=0; i<26; i++) {
            INode child = n1.getChildren()[i];
            if(child != null){
                curr.append((char)('a'+i));
                strHelper(child, curr, output);
                curr.deleteCharAt(curr.length()-1);
            }
        }
    }

    public boolean equals(Object o){
        if(o == null || o.getClass() != this.getClass())
            return false;
        if(o == this)
            return true;
        Trie dict = (Trie)o;
        if(dict.getNodeCount() != this.getNodeCount() || dict.getWordCount() != this.getWordCount())
            return false;
        return eqHelper(this.root, dict.root);
    }
    private boolean eqHelper(INode n1, INode n2){
        if(n1.getValue() != n2.getValue())
            return false;
        for(int i=0; i<26; i++){
            INode child1 = n1.getChildren()[i];
            INode child2 = n2.getChildren()[i];
            if(child1 == null){
                if(child2 != null)
                    return false;
            }else if(child2 == null)
                return false;
        }
        boolean temp = true;
        for(int i=0; i<26; i++){
            INode child1 = n1.getChildren()[i];
            INode child2 = n2.getChildren()[i];
            if(child1 != null)
                temp = eqHelper(child1, child2);
            if(!temp)
                break;
        }
        return temp;
    }
}
