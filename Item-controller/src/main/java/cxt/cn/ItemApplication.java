package cxt.cn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Auther: ChengXiaotian
 * @Date: 2020-06-26 17:27
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cxt.cn.mapper")
@EnableScheduling
public class ItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemApplication.class);
    }
}
