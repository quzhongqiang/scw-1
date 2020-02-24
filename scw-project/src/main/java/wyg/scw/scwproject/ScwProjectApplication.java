package wyg.scw.scwproject;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients //远程调用
@EnableCircuitBreaker //熔断保护
@EnableDiscoveryClient //eureka客户端
@MapperScan("wyg.scw.scwproject.mapper")
@SpringBootApplication
public class ScwProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScwProjectApplication.class, args);
    }

}
