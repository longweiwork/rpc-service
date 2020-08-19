package server;

import com.learn.IHelloService;

/**
 * @Author: Brian
 * @Date: 2020/8/18 16:23
 */

@RpcService(IHelloService.class)
public class HelloServiceImpl implements IHelloService {
    public String sayHello(String name) {
        System.out.println("request in " + name);
        return "Say Hello" + name;
    }
}
