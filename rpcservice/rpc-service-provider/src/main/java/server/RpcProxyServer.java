package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Brian
 * @Date: 2020/8/18 16:27
 */
public class RpcProxyServer {

    ExecutorService executorService = Executors.newCachedThreadPool();

    public void publisher(Object target, int port) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);

            while (true) {
                Socket socket = serverSocket.accept();

                executorService.execute(new ProcessorHandler(socket, target));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
