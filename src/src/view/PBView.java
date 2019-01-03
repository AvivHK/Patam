package view;

import test.Node;
import test.PipeGameBoard;

public class PBView {

    private String id;
    private PipeView[][] pipeGame;
    private Double height;
    private Double width;
    private Node start;
    private Node end;


    public PBView(Double height, Double width, String pipeGame) {
        setWidthAndHeight(height, width);
        setPipeGame(this.toPipeGame(pipeGame));
        setId(pipeGame);
        setStart(this.findStartNode());
        setEnd(this.findEndNode());
    }

    private void setWidthAndHeight(Double height, Double width) {
        this.height = height;
        this.width = width;
    }

    private void setId(String pipeGame) {
        this.id = String.valueOf(pipeGame.hashCode());
    }


    public PipeView[][] getPipeGame() {
        return pipeGame;
    }



    public PipeView getPipeView(Integer row, Integer col) {
        if (row < this.pipeGame.length && col < this.pipeGame[0].length && row >= 0 && col >= 0) {
            return this.pipeGame[row][col];
        }
        return null;
    }

    public Node findStartNode() {
        for (int i = 0; i < this.pipeGame.length; i++) {
            for (int j = 0; j < this.pipeGame[0].length; j++) {
                if (this.pipeGame[i][j].getVal() == 's') {
                    return new Node(i, j);
                }
            }
        }
        return null;
    }

    public Node findEndNode() {
        for (int i = 0; i < this.pipeGame.length; i++) {
            for (int j = 0; j < this.pipeGame[0].length; j++) {
                if (this.pipeGame[i][j].getVal() == 'g') {
                    return new Node(i, j);
                }
            }
        }
        return null;
    }


    public void setStart(Node start) {
        this.start = new Node(start);
    }


    public void setEnd(Node end) {
        this.end = new Node(end);
    }

    void setPipeGame(PipeView[][] tmpPipeGame) {
        try {
            Integer row = tmpPipeGame.length;
            Integer col = tmpPipeGame[0].length;
            this.pipeGame = new PipeView[row][col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    this.pipeGame[i][j] = new PipeView(height, width, tmpPipeGame[i][j]);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public PipeView[][] toPipeGame(String stringPipeGame) {
        PipeView[][] changedPipeGame = null;
        try {
            String[] lines = stringPipeGame.split("\n");
            Integer rows = lines[lines.length - 1].equals("done") ? lines.length - 1 : lines.length;
            Integer cols = lines[0].length();

            if (rows <= 0) {
                throw new Exception("Wrong rows");
            }

            if (cols <= 0) {
                throw new Exception("Wrong cols");
            }

            changedPipeGame = new PipeView[rows][cols];

            for (int i = 0; i < rows; i++) {
                String line = lines[i];
                if (line == null) {
                    throw new Exception("");
                }
                for (int j = 0; j < cols; j++) {
                    changedPipeGame[i][j] = new PipeView(height, width, line.charAt(j), new Node(i, j));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return changedPipeGame;
    }




    public PipeView getPipeViewByPixels(double x, double y){
        int rows = this.getPipeGame().length;
        int cols = this.getPipeGame()[0].length;

        for (int i= 0 ; i < rows; i++){
            for ( int j = 0; j < cols; j++){
                if (x > this.getPipeGame()[i][j].pixels.get("startX")
                        && x < this.getPipeGame()[i][j].pixels.get("endX")
                        && y > this.getPipeGame()[i][j].pixels.get("startY")
                        && y < this.getPipeGame()[i][j].pixels.get("endY")){
                    return this.getPipeGame()[i][j];
                }
            }
        }

        return null;
    }

    public String toString(){
        int rows = pipeGame.length;
        int cols = pipeGame[0].length;

        String stringPipeGame = "";

        for (int i= 0 ; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                stringPipeGame += pipeGame[i][j].getVal();
            }

            stringPipeGame+= "\n";
        }

        return stringPipeGame;
    }
}