package server;

import com.learn.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @Author: Brian
 * @Date: 2020/8/18 16:29
 */
public class ProcessorHandler implements Runnable {

    private Socket socket;
    private Object target;

    public ProcessorHandler(Socket socket, Object target) {
        this.socket = socket;
        this.target = target;
    }

    public void run() {
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();

            Object result = invoke(rpcRequest);

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private Object invoke(RpcRequest rpcRequest) throws InvocationTargetException, IllegalAccessException,
            NoSuchMethodException, ClassNotFoundException {
        Object[] args = rpcRequest.getParameters();
        Class<?>[] types = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            types[i] = args[i].getClass();
        }
        Class<?> clazz = Class.forName(rpcRequest.getClassName());
        Method method = clazz.getMethod(rpcRequest.getMethodName(), types);
        Object result = method.invoke(target, args);
        return result;

    }
}
