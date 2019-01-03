package test;

import java.io.IOException;

public interface Server{

	public void start(ClientHandler clientHandler) throws IOException;

	public void runServer(ClientHandler clientHandler) throws IOException;

	public void stop();

}

