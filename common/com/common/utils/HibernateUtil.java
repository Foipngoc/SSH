package com.common.utils;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * 提供原始的Hibernate工具
 * 
 * @author DJ
 * 
 */
public class HibernateUtil {
	/**
	 * 最大连接数
	 */
	public final static String HIBERNATE_C3P0_MAX_SIZE = "hibernate.c3p0.max_size";

	/**
	 * 最小连接数
	 */
	public final static String HIBERNATE_C3P0_MIN_SIZE = "hibernate.c3p0.min_size";

	/**
	 * 获得连接的超时时间,如果超过这个时间,会抛出异常，单位毫秒
	 */
	public final static String HIBERNATE_C3P0_TIMEOUT = "hibernate.c3p0.timeout";

	/**
	 * 最大的PreparedStatement的数量
	 */
	public final static String HIBERNATE_C3P0_MAX_STATEMENTS = "hibernate.c3p0.max_statements";

	/**
	 * 每隔120秒检查连接池里的空闲连接 ，单位是秒
	 */
	public final static String HIBERNATE_C3P0_IDLE_TEST_PERIOD = "hibernate.c3p0.idle_test_period";

	/**
	 * 当连接池里面的连接用完的时候，C3P0一下获取的新的连接数
	 */
	public final static String HIBERNATE_C3P0_ACQUIRE_INCREMENT = "hibernate.c3p0.acquire_increment";

	/**
	 * 每次都验证连接是否可用
	 */
	public final static String HIBERNATE_C3P0_VALIDATE = "hibernate.c3p0.validate";

	/**
	 * 连接池配置
	 */
	public final static String HIBERNATE_CONNECTION_PROVIDER_CLASS = "hibernate.connection.provider_class";

	/**
	 * 数据库驱动类名
	 */
	public final static String HIBERNATE_CONNECTION_DRIVER_CLASS = "hibernate.connection.driver_class";

	/**
	 * 数据库方言
	 */
	public final static String HIBERNATE_DIALECT = "hibernate.dialect";

	/**
	 * 数据库连接URL
	 */
	public final static String HIBERNATE_CONNECTION_URL = "hibernate.connection.url";

	/**
	 * 数据库连接账号
	 */
	public final static String HIBERNATE_CONNECTION_USERNAME = "hibernate.connection.username";

	/**
	 * 数据库连接密码
	 */
	public final static String HIBERNATE_CONNECTION_PASSWORD = "hibernate.connection.password";

	/**
	 * 输出所有SQL语句到控制台
	 */
	public final static String HIBERNATE_SHOW_SQL = "hibernate.show_sql";

	/**
	 * 在log和console中打印出更漂亮的SQL
	 */
	public final static String HIBERNATE_FORMAT_SQL = "hibernate.format_sql";

	/**
	 * 在SessionFactory创建时，自动检查数据库结构，或者将数据库schema的DDL导出到数据库. 使用
	 * create-drop时,在显式关闭SessionFactory时，将drop掉数据库schema. 取值 validate | update |
	 * create | create-drop
	 */
	public final static String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

	/**
	 * 从Properties中加载配置获取SessionFactory
	 * 
	 * @param properties
	 * @return
	 */
	public static SessionFactory getSessionFactory(Properties properties) {
		Configuration config = new Configuration().setProperties(properties);

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(config.getProperties()).build();

		SessionFactory sessionFactory = config
				.buildSessionFactory(serviceRegistry);
		return sessionFactory;
	}

	/**
	 * 从属性文件加载配置获取SessionFactory
	 * 
	 * @param propertiesFilename
	 * @return
	 */
	public static SessionFactory getSessionFactory(String propertiesFilename) {
		Properties properties = PropertyLoader.getProperty(propertiesFilename);
		return getSessionFactory(properties);
	}

	/**
	 * 使用hibernate原始的连接池获取SessionFactory
	 * 
	 * 不推荐使用
	 * 
	 * @return
	 */
	public static SessionFactory getDefaultSessionFactory(String driver,
			String url, String username, String password, String dialect) {
		return getSessionFactory(getDefaultProperties(driver, url, username,
				password, dialect));
	}

	/**
	 * 使用c3p0连接池获取SessionFactory
	 * 
	 * @return
	 */
	public static SessionFactory getDefaultC3P0SessionFactory(String driver,
			String url, String username, String password, String dialect) {

		return getSessionFactory(getDefaultC3P0Properties(driver, url,
				username, password, dialect));
	}

	/**
	 * 获得默认的C3P0属性配置，你可以通过对返回的Properties进行修改来重新设置,
	 * 然后调用getSessionFactory(Properties)获取SessionFactory
	 * 
	 * @param driver
	 * @param url
	 * @param username
	 * @param password
	 * @param dialect
	 * @return
	 */
	public static Properties getDefaultC3P0Properties(String driver,
			String url, String username, String password, String dialect) {
		Properties properties = getDefaultProperties(driver, url, username,
				password, dialect);
		properties.setProperty(HIBERNATE_CONNECTION_PROVIDER_CLASS,
				"org.hibernate.c3p0.internal.C3P0ConnectionProvider");
		properties.setProperty(HIBERNATE_C3P0_MAX_SIZE, "20");
		properties.setProperty(HIBERNATE_C3P0_MIN_SIZE, "5");
		properties.setProperty(HIBERNATE_C3P0_TIMEOUT, "120");
		properties.setProperty(HIBERNATE_C3P0_MAX_STATEMENTS, "100");
		properties.setProperty(HIBERNATE_C3P0_IDLE_TEST_PERIOD, "120");
		properties.setProperty(HIBERNATE_C3P0_ACQUIRE_INCREMENT, "2");
		properties.setProperty(HIBERNATE_C3P0_VALIDATE, "true");

		return properties;
	}

	public static Properties getDefaultProperties(String driver, String url,
			String username, String password, String dialect) {
		Properties properties = new Properties();

		properties.setProperty(HIBERNATE_CONNECTION_DRIVER_CLASS, driver);
		properties.setProperty(HIBERNATE_DIALECT, dialect);
		properties.setProperty(HIBERNATE_CONNECTION_URL, url);
		properties.setProperty(HIBERNATE_CONNECTION_USERNAME, username);
		properties.setProperty(HIBERNATE_CONNECTION_PASSWORD, password);
		properties.setProperty(HIBERNATE_SHOW_SQL, "true");
		properties.setProperty(HIBERNATE_FORMAT_SQL, "true");
		properties.setProperty(HIBERNATE_HBM2DDL_AUTO, "validate");
		return properties;
	}
}
