package com.luban.config;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


@Configuration
@ComponentScan(value = "com.luban")
@PropertySource("jdbc.properties")
public class SpringConfig {
		
    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.driverClassName}")
    private String jdbcDriverClassName;

    @Value("${jdbc.username}")
    private String jdbcUsername;

    @Value("${jdbc.password}")
    private String jdbcPassword;
    
	@Bean
	public DataSource dataSource(){
		
		DruidDataSource druidDataSource = new DruidDataSource();	
		druidDataSource.setDriverClassName(jdbcDriverClassName);		
		druidDataSource.setUrl(jdbcUrl);
		druidDataSource.setUsername(jdbcUsername);
		druidDataSource.setPassword(jdbcPassword);
				
		return druidDataSource;
	}

	@Bean
	public CommonsMultipartResolver commonsMultipartResolver(){
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxInMemorySize(10485760);
		commonsMultipartResolver.setDefaultEncoding("utf-8");
		return commonsMultipartResolver;
	}

}
