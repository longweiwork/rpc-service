package client;

import com.learn.IHelloService;

/**
 * @Author: Brian
 * @Date: 2020/8/18 17:32
 */
public class ClientApplication {

    public static void main(String[] args) {
        RpcProxyClient rpcProxyClient = new RpcProxyClient();
        IHelloService iHelloService = rpcProxyClient.clientProxy(IHelloService.class, "localhost", 8080);
        iHelloService.sayHello("hello");
    }
}
