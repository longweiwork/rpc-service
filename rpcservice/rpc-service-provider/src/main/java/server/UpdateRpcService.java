package server;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Brian
 * @Date: 2020/8/18 17:12
 */

@Component
public class UpdateRpcService implements ApplicationContextAware, InitializingBean {

    ExecutorService executorService = Executors.newCachedThreadPool();

    private int port = 8080;
    private Map<String, Object> handlerMap = new HashMap<>();


    @Override
    public void afterPropertiesSet() throws Exception {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();

                executorService.execute(new ProcessorHandler(socket, handlerMap));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (!serviceBeanMap.isEmpty()) {
            for (Object serviceBean : serviceBeanMap.values()) {
                RpcService rpcService = serviceBean.getClass().getAnnotation(RpcService.class);
                String serviceName = rpcService.value().getName();
                handlerMap.put(serviceName, serviceBean);
            }
        }
    }
}
