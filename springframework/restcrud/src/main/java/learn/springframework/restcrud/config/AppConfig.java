package learn.springframework.restcrud.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@EnableWebMvc                       // alias for -> <mvc:annotation-driven/>
@EnableTransactionManagement        // alias for -> <tx:annotation-driven transaction-manager="myTransactionManager"/>
@ComponentScan("spring.rest.crud")  // alias for -> <context:component-scan base-package="spring.mvc"/>
@PropertySource(value = "/../resources/mysql.properties")
public class AppConfig implements WebMvcConfigurer {

    // "mysql.properties" file is automatically bind to this 'env' variable via @PropertySource
    @Autowired
    Environment env;

    private Logger logger = Logger.getLogger(getClass().getName());

    /*
    * Alias for following xml
    * <bean id="myDataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource"
          destroy-method="close">
        <property name="driverClass" value="com.mysql.cj.jdbc.Driver"/>
        <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/web_customer_tracker?useSSL=false"/>
        <property name="user" value="root"/>
        <property name="password" value="ahroo"/>

        <!-- these are connection pool properties for C3P0 -->
        <property name="initialPoolSize" value="5"/>
        <property name="minPoolSize" value="5"/>
        <property name="maxPoolSize" value="20"/>
        <property name="maxIdleTime" value="30000"/>
    </bean>
    * */
    @Bean("dataSource")
    // DataSource class represents C3P0
    public DataSource myDataSource() {

        // create connection pool -> use C3P0
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        // set jdbc driver
        try {
            dataSource.setDriverClass(env.getProperty("jdbc.driver"));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

        // for sanity's sake log the details
        logger.info("jdbc.url=" + env.getProperty("jdbc.url"));
        logger.info("jdbc.user=" + env.getProperty("jdbc.user"));

        // set connection
        dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        dataSource.setUser(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.pwd"));

        // set connection pool properties
        dataSource.setInitialPoolSize(getIntProperty(env.getProperty("connection.pool.initialPoolSize")));
        dataSource.setMinPoolSize(getIntProperty(env.getProperty("connection.pool.minPoolSize")));
        dataSource.setMaxPoolSize(getIntProperty(env.getProperty("connection.pool.maxPoolSize")));
        dataSource.setMaxIdleTime(getIntProperty(env.getProperty("connection.pool.maxIdleTime")));

        return dataSource;
    }

    private int getIntProperty(String propName) {

        // get property value as String from @PropertySource
        String propVal = env.getProperty(propName);

        // convert that to int
        int intPropVal = Integer.parseInt(propVal);

        return intPropVal;

    }

    /*
    * Alias for following xml
    * <bean id="sessionFactory"
          class="org.springframework.orm.hibernate5.LocalSessionFactoryBean">
        <property name="dataSource" ref="myDataSource"/>
        <property name="packagesToScan" value="spring.mvc.entity"/>
        <property name="hibernateProperties">
            <props>
                <prop key="hibernate.dialect">org.hibernate.dialect.MySQLDialect</prop>
                <prop key="hibernate.show_sql">true</prop>
            </props>
        </property>
    </bean>
    * */
    @Bean("sessionFactory")
    // usual way of setting up session factory
    public LocalSessionFactoryBean sessionFactory() {

        // create session factory
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        // set properties
        sessionFactory.setDataSource(myDataSource());
        sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
        sessionFactory.setHibernateProperties(getHibernateProperties());

        return sessionFactory;
    }

    public Properties getHibernateProperties() {

        Properties props = new Properties();
        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));

        return props;
    }

    /*
    * alias for following xml
    * <bean id="myTransactionManager"
          class="org.springframework.orm.hibernate5.HibernateTransactionManager">
        <property name="sessionFactory" ref="sessionFactory"/>
      </bean>
    * */
    @Bean("transactionManager")
    // setting up transaction manager
    public HibernateTransactionManager transactionManager(@Autowired SessionFactory sessionFactory) {

        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);

        return transactionManager;
    }
}


//package spring.rest.crud.config;
//
//import com.mchange.v2.c3p0.ComboPooledDataSource;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.hibernate5.HibernateTransactionManager;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import javax.sql.DataSource;
//import java.beans.PropertyVetoException;
//import java.util.Properties;
//import java.util.logging.Logger;
//
//@Configuration
//@EnableWebMvc
//@EnableTransactionManagement
//@ComponentScan("spring.rest.crud")
//public class AppConfig implements WebMvcConfigurer {
//
//    private static final String jdbc_driver = "com.mysql.cj.jdbc.Driver";
//    private static final String jdbc_url = "jdbc:mysql://localhost:3306/customer_tracker";
//    private static final String jdbc_user = "root";
//    private static final String jdbc_pwd = "ahroo";
//
//    private static final int connection_pool_initialPoolSize = 5;
//    private static final int connection_pool_minPoolSize = 5;
//    private static final int connection_pool_maxPoolSize = 20;
//    private static final int connection_pool_maxIdleTime = 3000;
//
//    private static final String hibernate_dialect = "org.hibernate.dialect.MySQLDialect";
//    private static final String hibernate_show_sql = "true";
//    private static final String hibernate_packagesToScan = "spring.rest.crud.entity";
//
//    private Logger logger = Logger.getLogger(getClass().getName());
//
//    @Bean("dataSource")
//    public DataSource myDataSource() {
//
//        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//
//        try {
//            dataSource.setDriverClass(jdbc_driver);
//        } catch (PropertyVetoException e) {
//            e.printStackTrace();
//        }
//
//        logger.info("jdbc.url=" + jdbc_url);
//        logger.info("jdbc.user=" + jdbc_user);
//
//        dataSource.setJdbcUrl(jdbc_url);
//        dataSource.setUser(jdbc_user);
//        dataSource.setPassword(jdbc_pwd);
//
//        dataSource.setInitialPoolSize(connection_pool_initialPoolSize);
//        dataSource.setMinPoolSize(connection_pool_minPoolSize);
//        dataSource.setMaxPoolSize(connection_pool_maxPoolSize);
//        dataSource.setMaxIdleTime(connection_pool_maxIdleTime);
//
//        return dataSource;
//    }
//
//    @Bean("sessionFactory")
//    public LocalSessionFactoryBean sessionFactory() {
//
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//
//        sessionFactory.setDataSource(myDataSource());
//        sessionFactory.setPackagesToScan(hibernate_packagesToScan);
//        sessionFactory.setHibernateProperties(getHibernateProperties());
//
//        return sessionFactory;
//    }
//
//    public Properties getHibernateProperties() {
//
//        Properties props = new Properties();
//        props.setProperty("hibernate.dialect", hibernate_dialect);
//        props.setProperty("hibernate.show_sql", hibernate_show_sql);
//
//        return props;
//    }
//
//    @Bean("transactionManager")
//    public HibernateTransactionManager transactionManager(@Autowired SessionFactory sessionFactory) {
//
//        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
//        transactionManager.setSessionFactory(sessionFactory);
//
//        return transactionManager;
//    }
//}
