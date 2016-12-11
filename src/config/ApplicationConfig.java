package config;

import static java.lang.Integer.parseInt;

import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import cn.drizzt.common.datasource.MultiDataSource;

/**
 * ApplicationConfig Spring配置类
 */
@Configuration
@ComponentScan(basePackages = "cn.drizzt", excludeFilters = { @ComponentScan.Filter(value = { Controller.class }) })
@MapperScan("cn.drizzt.mapper")
@PropertySource({ "classpath:config/properties/dbconfig.properties",
		"classpath:config/properties/freemarker.properties" })
@EnableTransactionManagement
public class ApplicationConfig {
	@Autowired
	private Environment env;

	/**
	 * 数据源
	 * 
	 * @return
	 */
	@Bean
	public DataSource dataSource() throws PropertyVetoException {
		MultiDataSource bean = new MultiDataSource();
		HashMap<Object, Object> targetDataSources = new HashMap<Object, Object>();
		ComboPooledDataSource database = new ComboPooledDataSource();
		database.setDriverClass(env.getProperty("jdbc.driverClassName"));
		database.setJdbcUrl(env.getProperty("jdbc.url"));
		database.setUser(env.getProperty("jdbc.username"));
		database.setPassword(env.getProperty("jdbc.password"));
		database.setAutoCommitOnClose(Boolean.parseBoolean(env.getProperty("cpool.autoCommitOnClose")));
		database.setCheckoutTimeout(parseInt(env.getProperty("cpool.checkoutTimeout")));
		database.setInitialPoolSize(parseInt(env.getProperty("cpool.minPoolSize")));
		database.setMinPoolSize(parseInt(env.getProperty("cpool.minPoolSize")));
		database.setMaxPoolSize(parseInt(env.getProperty("cpool.maxPoolSize")));
		database.setMaxIdleTime(parseInt(env.getProperty("cpool.maxIdleTime")));
		database.setAcquireIncrement(parseInt(env.getProperty("cpool.acquireIncrement")));
		database.setMaxIdleTimeExcessConnections(parseInt(env.getProperty("cpool.maxIdleTimeExcessConnections")));
		targetDataSources.put("default", database);
		bean.setTargetDataSources(targetDataSources, database);
		return bean;
	}

	/**
     * <p>
     * Mybatis 会话工厂
     * </p>
     * <p>
     * Mybatis Session Factory
     * </p>
     * 
     * @return
	 * @throws Exception 
     */
    @Bean
    public SqlSessionFactory mybatisSqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCacheEnabled(true);
        configuration.setLazyLoadingEnabled(false);
        bean.setConfiguration(configuration);
        return bean.getObject();
    }
    
	/**
	 * 事务
	 */
	@Bean
	public DataSourceTransactionManager txManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	/**
	 * FreeMarker配置工厂
	 *
	 * @return
	 */
	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer() {
		FreeMarkerConfigurer bean = new FreeMarkerConfigurer();
		bean.setTemplateLoaderPath("/WEB-INF/");
		Properties properties = new Properties();
		properties.setProperty("newBuiltinClassResolver",
				env.getProperty("freemarkerSettings.newBuiltinClassResolver"));
		properties.setProperty("templateUpdateDelay", env.getProperty("freemarkerSettings.templateUpdateDelay"));
		properties.setProperty("defaultEncoding", env.getProperty("freemarkerSettings.defaultEncoding"));
		properties.setProperty("urlEscapingCharset", env.getProperty("freemarkerSettings.urlEscapingCharset"));
		properties.setProperty("locale", env.getProperty("freemarkerSettings.locale"));
		properties.setProperty("booleanFormat", env.getProperty("freemarkerSettings.booleanFormat"));
		properties.setProperty("datetimeFormat", env.getProperty("freemarkerSettings.datetimeFormat"));
		properties.setProperty("dateFormat", env.getProperty("freemarkerSettings.dateFormat"));
		properties.setProperty("timeFormat", env.getProperty("freemarkerSettings.timeFormat"));
		properties.setProperty("numberFormat", env.getProperty("freemarkerSettings.numberFormat"));
		bean.setFreemarkerSettings(properties);
		return bean;
	}

	/**
	 * json消息转换适配器，用于支持RequestBody、ResponseBody
	 *
	 * @return
	 */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		return new MappingJackson2HttpMessageConverter();
	}
}