package utb.fai;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java App <port> <max_threads>");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);
        int maxThreads = Integer.parseInt(args[1]);

        ExecutorService threadPool = Executors.newFixedThreadPool(maxThreads);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");
                threadPool.execute(new ClientThread(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            threadPool.shutdown();
        }
    }
}
