
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;




public class MyServer implements Server {
    private ServerSocket serverSocket;
    private int port;
    private volatile boolean stop = false;

    public MyServer(){}

    public MyServer(int port) {
        this.port = port;
    }

    @Override
    public void start(ClientHandler clientHandler) {
        new Thread(() -> {
            try {
                runServer(clientHandler);
            } catch (Exception e) {
            }
        }).start();
    }


    @Override
    public void stop() {
        this.stop = true;
    }

    @Override
    public void runServer(ClientHandler clientHandler) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(5000);

        while (!stop) {
            try {
                Socket aClient = serverSocket.accept();

                clientHandler.handleClient(aClient.getInputStream(), aClient.getOutputStream());

                aClient.close();

            } catch (SocketTimeoutException e) {
                //System.out.println(e.getMessage());
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        serverSocket.close();
    }




}

