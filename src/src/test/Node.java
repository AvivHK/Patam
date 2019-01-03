package test;

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
    
    public Node(int row, int col) {
    	this.row=row;
    	this.col=col;
    }
    
	public Node(Node node) {
		this.row=node.getRow();
		this.col=node.getCol();
	}

    public Integer getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
		
	public Integer getCol() {
		return col;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	    public Node getUp() {
	        return new Node(this.getRow() - 1, this.col);
	    }

	    public Node getDown() {
	        return new Node(this.getRow() + 1, this.col);
	    }

	    public Node getRight() {
	        return new Node(this.row, this.col + 1);
	    }

	    public Node getLeft() {
	        return new Node(this.row, this.getCol() - 1);

	    }

	    public boolean equals(Node node) {
	        return row == node.row && col == node.col;
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

