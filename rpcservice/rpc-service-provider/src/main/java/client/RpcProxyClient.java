package client;

import java.lang.reflect.Proxy;
import java.rmi.server.RemoteObjectInvocationHandler;

/**
 * @Author: Brian
 * @Date: 2020/8/18 17:36
 */
public class RpcProxyClient {


    public <T> T clientProxy(final Class<T> interfaceCls, final String host, final int port) {
        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(), new Class<?>[]{interfaceCls},
                new RemoteInvocationHandler(host, port));
    }
}
