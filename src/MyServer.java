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
    ThreadPoolExecutor threadPoolExecutor;

    public MyServer(int port, int num) {
        this.port = port;
        this.threadPoolExecutor = new ThreadPoolExecutor(1, num, 10, TimeUnit.SECONDS,
                new PriorityBlockingQueue<Runnable>());
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
        serverSocket.setSoTimeout(10000);
        System.out.println("Start Server.");

        while (!stop) {
            try {
                Socket aClient = serverSocket.accept();
                System.out.println("Connect to client");
                int clientPriority = 0;
                do {
                    clientPriority = aClient.getInputStream().available();
                } while (clientPriority == 0);
                threadPoolExecutor.execute(new PriorityQRunnable(clientPriority) {

                    @Override
                    public void run() {
                        try {
                            clientHandler.handleClient(aClient.getInputStream(), aClient.getOutputStream());
                            aClient.close();
                            System.out.println("Disconnected.");
                        } catch (SocketTimeoutException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }catch (IOException e) {

            }
            System.out.println(threadPoolExecutor.getActiveCount());
            if(threadPoolExecutor.getActiveCount() == 0) {
                this.stop();
            }
        }

        threadPoolExecutor.shutdown();
        serverSocket.close();
        System.out.println("All clients had been handled");

        try {
            threadPoolExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
