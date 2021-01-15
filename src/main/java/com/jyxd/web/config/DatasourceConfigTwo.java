package com.jyxd.web.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 第二数据源配置
 * create by zhangjie on 2021-01-15
 */
@Configuration
@MapperScan(basePackages = DatasourceConfigTwo.PACKAGE, sqlSessionFactoryRef = "data2SqlSessionFactory")
public class DatasourceConfigTwo {
    // 精确到 mapper 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.jyxd.web.mapper";
    static final String MAPPER_LOCATION = "classpath:mapper/two/*.xml";

    @Value("${spring.datasource.data2.url}")
    private String url;

    @Value("${spring.datasource.data2.username}")
    private String user;

    @Value("${spring.datasource.data2.password}")
    private String password;

    @Value("${spring.datasource.data2.driver-class-name}")
    private String driverClass;

    @Bean(name = "data2DataSource")
    public DataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "data2TransactionManager")
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }

    @Bean(name = "data2SqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("data2DataSource") DataSource data2DataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(data2DataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(DatasourceConfigTwo.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
