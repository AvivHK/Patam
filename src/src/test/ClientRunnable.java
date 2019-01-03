package test;

import test.ClientHandler;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientRunnable implements Runnable {
    String QUIT_WORD = "done";
    private Socket clientSocket;
    private OutputStream out;
    private String board;
    private BufferedReader in;
    ClientHandler clientHandler;

    public ClientRunnable(Socket cs,ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.clientSocket = cs;
        try {
            this.out = this.clientSocket.getOutputStream();
        } catch (IOException e) {
           throw new RuntimeException(e.toString());
        }
        this.getBoard();
    }

    public int getBoardSize()
    {
        String[] rows = this.board.split("\n");
        return rows[0].length() * rows.length;
    }

    private void getBoard() {
        ArrayList<String> lines = new ArrayList<>();
        String line;
        try {

            this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            while (!(line = this.in.readLine()).equals(QUIT_WORD)) {
                lines.add(line);
            }
            this.board = String.join(System.lineSeparator(), lines);

        } catch (IOException exception) {
           // System.out.println(exception.toString());
        }
    }

    @Override
    public void run() {
        try {
            String tempBoard = "";
            for(int i=0;i<board.length();i++) {
                if (!(board.charAt(i) == '\r'))
                    tempBoard = tempBoard + board.charAt(i);
            }
            board=tempBoard;
            this.clientHandler.handle(new PrintWriter(this.out), this.board);
            this.clientSocket.close();
            this.in.close();
        } catch (Exception ex) {
            //System.out.println(ex.getMessage());
        }
    }
}