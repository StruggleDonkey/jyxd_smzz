package com.jyxd.web.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 第一数据源配置
 * create by zhangjie on 2021-01-15
 */
@Configuration
@MapperScan(basePackages = DatasourceConfigOne.PACKAGE, sqlSessionFactoryRef = "data1SqlSessionFactory")
public class DatasourceConfigOne {
    // 精确到 mapper 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.jyxd.web.dao";
    static final String MAPPER_LOCATION = "classpath:mapper/one/*.xml";

    @Value("${spring.datasource.data1.url}")
    private String url;

    @Value("${spring.datasource.data1.username}")
    private String user;

    @Value("${spring.datasource.data1.password}")
    private String password;

    @Value("${spring.datasource.data1.driver-class-name}")
    private String driverClass;


    @Bean(name = "data1DataSource")
    @Primary
    public DataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "data1TransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }

    @Bean(name = "data1SqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("data1DataSource") DataSource data1rDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(data1rDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(DatasourceConfigOne.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
