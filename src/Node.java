@SuppressWarnings("unchecked")


public class Node {
    public int row;
    public int col;
    public int click;
    public char value;

    public Node(int row,int col,char value){
        this.row=row;
        this.col=col;
        click=0;
        this.value=value;
    }
    public Node(int row,int col,char value,int click){
        this.row=row;
        this.col=col;
        this.click=click;
        this.value=value;
    }


    public boolean equals(Node g) {
        return row == g.row && col == g.col;
    }

    @Override
    public int hashCode() {
        return (row + "," + col+","+click).hashCode();
    }

    @Override
    public String toString() {
        return (row + "," + col + ","+ click);
    }
}

