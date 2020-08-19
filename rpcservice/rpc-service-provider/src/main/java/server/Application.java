package server;

import com.learn.IHelloService;

/**
 * @Author: Brian
 * @Date: 2020/8/18 16:42
 */
public class Application {

    public static void main(String[] args) {
        IHelloService helloService = new HelloServiceImpl();
        RpcProxyServer proxyServer = new RpcProxyServer();
        proxyServer.publisher(helloService, 8080);
    }
}
