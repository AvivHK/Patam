import java.io.InputStream;
import java.io.OutputStream;

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
	void handleClient(InputStream inputStream, OutputStream outputStream);
}
