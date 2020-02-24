package wyg.scw.scwproject.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wyg.scw.scwproject.utils.OssTemplate;

@Configuration
public class ProjectAppConfig {
    @Bean
    @ConfigurationProperties(prefix = "oss")
    public OssTemplate getOssTemplate(){
        return new OssTemplate();
    }
}
