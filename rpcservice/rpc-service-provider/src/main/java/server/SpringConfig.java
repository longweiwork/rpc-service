package server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Brian
 * @Date: 2020/8/18 17:08
 */


@Configuration
@ComponentScan(basePackages = "com.learn")
public class SpringConfig {

    @Bean
    public UpdateRpcService updateRpcService() {
        return new UpdateRpcService();
    }
}
