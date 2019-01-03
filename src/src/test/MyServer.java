package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
public class MyServer implements Server {
    private ServerSocket serverSocket;
    private int port;
    private boolean stop = false;
    final int threadPoolSize;
    ThreadPoolExecutor threadPoolExecutor;
    ShortestJobFirst threadQueueHandler;
    final int QUEUE_SIZE = 100;



    public MyServer(int port, int num) {
        this.port = port;
        this.threadPoolSize=num;
        this.threadQueueHandler = new ShortestJobFirst(threadPoolSize,QUEUE_SIZE);

    }

    @Override
    public void stop() {
        stop = true;
    }

    public void start(ClientHandler clientHandler) throws IOException {
        new Thread(() -> {
            try {
                runServer(clientHandler);
            } catch (Exception e) {
            }
        }).start();
    }


    public void runServer(ClientHandler clientHandler) throws IOException {

        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(1000000);
        System.out.println("Start Server.");

        while (!stop) {
            try {
                Socket aClient = serverSocket.accept();
                System.out.println("Connect to client");
                threadQueueHandler.getPriorityQueue().add(new ClientRunnable(aClient,clientHandler));
            }catch (IOException e) {
                System.out.println("MyServer.begin() Error: " + e.getMessage());
            }
        }
        serverSocket.close();
    }

}
