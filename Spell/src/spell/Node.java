package spell;

public class Node implements INode{

    private Node[] children = new Node[26];
    private int value = 0;

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void incrementValue() {
        value++;
    }

    @Override
    public INode[] getChildren() {
        return children;
    }
}
