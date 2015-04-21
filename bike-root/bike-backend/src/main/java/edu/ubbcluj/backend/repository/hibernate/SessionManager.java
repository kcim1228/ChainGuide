package edu.ubbcluj.backend.repository.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import edu.ubbcluj.backend.util.PropertyProvider;

@SuppressWarnings("deprecation")
public class SessionManager {
	private static final SessionFactory SESSIONFACTORY = buildSessionFactory();
	
	private static SessionFactory buildSessionFactory() {
	
		try {
			final Configuration configuration = new Configuration();
			configuration.configure();
			configuration.setProperty("hibernate.connection.username",
					PropertyProvider.getProperty("username"));
			configuration.setProperty("hibernate.connection.password",
					PropertyProvider.getProperty("password"));
			configuration.setProperty("hibernate.connection.url",
					PropertyProvider.getProperty("location"));
			configuration.setProperty("hibernate.connection.pool_size",
					PropertyProvider.getProperty("poolSize"));
			
			final ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
			.applySettings(configuration.getProperties())
			.buildServiceRegistry();
			return configuration.buildSessionFactory(serviceRegistry);
		} catch (final Throwable ex) {
			throw new ExceptionInInitializerError(ex);			
		}
	}

	public static SessionFactory getSessionFactory() {
		return SESSIONFACTORY;
	}
}
