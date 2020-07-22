package cn.xuan.springcloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"cn.xuan.springcloud.dao"})
public class MyBatisConfig {

}
