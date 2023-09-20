package spell;

public class Trie implements ITrie{

    private INode root = new Node();
    private int nodeCount = 1;
    private int wordCount = 0;

    @Override
    public void add(String word) {
        word = word.toLowerCase();
        INode temp = find(word);
        if(temp == null) {
            char letter = word.charAt(0);
            int ind = getIndex(letter);
            if (root.getChildren()[ind] == null){
                root.getChildren()[ind] = new Node();
                nodeCount++;
            }
            temp = root.getChildren()[ind];
            INode temp2;
            for(int i=1; i<word.length(); i++){
                letter = word.charAt(i);
                ind = getIndex(letter);
                temp2 = stepDown(temp, ind);
                if (temp2 == null){
                    temp.getChildren()[ind] = new Node();
                    nodeCount++;
                    temp = temp.getChildren()[ind];
                }
                else{
                    temp = temp2;
                }
            }
        }
        temp.incrementValue();
        if(temp.getValue() == 1)
            wordCount++;
    }

    private INode stepDown(INode rootNode, int ind){
        if(rootNode == null)
            return null;
        return rootNode.getChildren()[ind];
    }

    private int getIndex(char letter){
        return (letter - 'a');
    }

    @Override
    public INode find(String word) {
        word = word.toLowerCase();
        char letter = word.charAt(0);
        int ind = getIndex(letter);
        INode temp = stepDown(root, ind);
        for(int i=1; i<word.length(); i++){
            if(temp == null)
                return null;
            letter = word.charAt(i);
            ind = getIndex(letter);
        }
        if (temp == null || temp.getValue() == 0)
            return null;
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
}
