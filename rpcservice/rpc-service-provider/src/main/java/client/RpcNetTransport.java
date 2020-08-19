package client;

import com.learn.RpcRequest;

import javax.print.attribute.standard.RequestingUserName;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @Author: Brian
 * @Date: 2020/8/18 17:28
 */
public class RpcNetTransport {

    private String host;
    private int port;

    public RpcNetTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object send(RpcRequest rpcRequest) {
        Socket socket = null;
        Object result = null;
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;

        try {
            socket = new Socket(host, port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(rpcRequest);
            outputStream.flush();

            inputStream = new ObjectInputStream(socket.getInputStream());
            result = inputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
