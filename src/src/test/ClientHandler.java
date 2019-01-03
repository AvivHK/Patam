package test;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public interface ClientHandler {

	String newGame();
	String loadGame(String path);
	void getTimer();
	void getHistory();
	void play();
	String solve(String game);
	void start();
	void terminate();
	void run();
	void handle(PrintWriter p, String s);
	void handleClient(InputStream inputStream, OutputStream outputStream);
}
