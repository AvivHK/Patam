package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unchecked")


public class PipeGameBoard implements Searchable<Node> {
	String id;
	private char[][] board;
	private int row = 0;
	private int col = 0;
	private String stringBoard;
	private int rowS;
	private int colS;
	private int colE;
	private int rowE;
    HashMap<String,HashMap<Character,String>> allowedSteps;
    
    
    public void setId(String board) {
        this.id = String.valueOf(board.hashCode());
    }
    
    public void setId(PipeGameBoard board) {
        this.id = String.valueOf(board.getId());
    }
    
    public String getId() {
    	return id;
    }
    
	public PipeGameBoard(){}

	public PipeGameBoard(String game) {
		stringBoard = game;
		this.setId(stringBoard);
		getStart();
		for (int i=0;i<game.length();i++) {
			if (game.charAt(i)=='\n') {
				row++;
			}
		}
		for(;col<game.length();col++){
			if((game.charAt(col)=='\n')||(game.charAt(col)=='\0'))
				break;
		}
		row++;
		board = new char[row][col];
		int l=0;
		for (int i=0;i<row;i++)
			for (int j=0;j<col;j++) {
				board[i][j] = game.charAt(l);
				if (game.charAt(l)=='\n')
					j--;

				l++;
			}
	}
	public PipeGameBoard(char[][] game){
		row=game.length;
		col=game[0].length;
		board=game;
		getStart();
	}
	public char[][] getBoard() {
		return board;
	}

	private class Pointer {
		int row, col;

		public Pointer(int r, int c) {
			row = r;
			col = c;
		}
	}

	@Override
	public State<Node> getInitialState() {
		getStart();
		return new State(new Node(rowS,colS,'s'),null,calcCost(rowS,colS));
	}

	@Override
	public boolean isGoalState(State<Node> s) {
		if (s.getState().value=='g')
			return true;
		return false;
	}
	

	@Override
	public List<State<Node>> getPossibleStates(State<Node> s) {
		List<State<Node>> possibleStates = new ArrayList<State<Node>>();
		int row=0,col=0;
		if(s.getCameFrom()!=null) {
			row = s.getCameFrom().getState().row - s.getState().row;
			col = s.getCameFrom().getState().col - s.getState().col;
		}
		if(row!=1)
			possibleStates.addAll(getNeighbors(s, 1, 0));
		if (row!=-1)
			possibleStates.addAll(getNeighbors(s, -1, 0));
		if (col!=-1)
			possibleStates.addAll(getNeighbors(s, 0, -1));
		if(col!=1)
			possibleStates.addAll(getNeighbors(s, 0, 1));
		return possibleStates;
	}

	public List<State<Node>> getNeighbors(State<Node> s,int row,int col) {
		char val;
		int itr;
		char[][] board_clone=getBoard();
		List<State<Node>> possibleStates=new ArrayList<State<Node>>();
		if (isValid(s.getState().row + row, s.getState().col + col)) {
			val = getValue(s.getState().row + row, s.getState().col+col);
				itr = howManyTimeToRotate(val);
				for (int i = 0; i < itr; i++) {
					if (isFit(s.getState().value, val, row, col))
						possibleStates.add(new State<Node>(new Node(s.getState().row+row,s.getState().col+col,val,i), s, calcCost(s.getState().row+row,s.getState().col+col)));
					val=rotate(val);
				}
		}
		return possibleStates;
	}

	private char[][] createClone(char[][] board){
		char[][] a=new char[row][col];
		for(int i=0;i<row;i++)
			for(int j=0;j<col;j++)
				a[i][j]=board[i][j];
		return a;
	}

	public static char rotate(char value) {
		switch(value) {
			case '-':
				return '|';
			case '|':
				return '-';
			case 'F':
				return '7';
			case '7':
				return 'J';
			case 'J':
				return 'L';
			case 'L':
				return 'F';
			case 's':
				return 's';
			case 'g':
				return 'g';
			default:
				return ' ';
		}
	}

	public boolean isFit(char start, char toCheck,int dir_y,int dir_x) {
        if (dir_x == 0) {
            switch (dir_y) {
                case 1:
					return isFitDown(start, toCheck);
                case -1:
					return isFitUp(start, toCheck);

            }
        } else {
            switch (dir_x) {
                case 1:
                    return isFitRight(start, toCheck);
                case -1:
					return isFitLeft(start, toCheck);

            }
        }
        return false;
    }

	public boolean isFitUp(char start, char toCheck) {
		if((start=='s')||(start == '|')||(start=='L')||(start=='J'))
			if ((toCheck == 'g')||(toCheck =='7')||(toCheck=='F')||(toCheck=='|'))
				return true;
		return false;
	}

	public boolean isFitDown(char start, char toCheck) {
		if((start=='s')||(start == '|')||(start=='7')||(start=='F'))
			if ((toCheck == 'g')||(toCheck =='J')||(toCheck=='L')||(toCheck=='|'))
				return true;
		return false;
	}

	public boolean isFitRight(char start, char toCheck) {
		if((start=='s')||(start == '-')||(start=='L')||(start=='F'))
			if ((toCheck == 'g')||(toCheck =='J')||(toCheck=='7')||(toCheck=='-'))
				return true;
		return false;
	}

	public boolean isFitLeft(char start, char toCheck) {
		if((start=='s')||(start == '-')||(start=='7')||(start=='J'))
			if ((toCheck == 'g')||(toCheck =='F')||(toCheck=='L')||(toCheck=='-'))
				return true;
		return false;
	}

	public boolean isValid(int s_row,int s_col) {
		if ((s_row<0)||(s_row >row-1))
			return false;
		if ((s_col<0)||(s_col >col-1))
			return false;
		if (board[s_row][s_col]==' ')
			return false;
		return true;
	}

	public void getStart() {
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++)
				if (board[i][j] == 's') {
					rowS = i;
					colS = j;
				}
				else if (board[i][j] == 'g'){
					rowE=i;
					colE=j;
				}
	}

	public int getRowS() {
		return rowS;
	}

	public int getColS() {
		return colS;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public char getValue(int row, int col){
		return board[row][col];
	}

	public void setBoard(int row, int col, char value) {
		board[row][col]=value;
	}

	public int howManyTimeToRotate(char value) {
		if((value=='|')||(value=='-'))
			return 2;
		else if((value=='s')||(value=='g'))
			return 1;
		else
			return 4;
	}

	public int calcCost(int s_row,int s_col) {
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++)
				if (board[i][j] == 'g')
					return (Math.abs(s_row - i) + Math.abs(s_col - j));
		return -1;
	}
}

	



