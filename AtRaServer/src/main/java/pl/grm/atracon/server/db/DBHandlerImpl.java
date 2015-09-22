/**
 * 
 */
package pl.grm.atracon.server.db;

import java.util.*;

import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import pl.grm.atracon.lib.*;
import pl.grm.atracon.lib.conf.ConfigDB;
import pl.grm.atracon.lib.rmi.DBHandler;
import pl.grm.atracon.server.conf.ConfigParams;
import pl.grm.atracon.server.devices.*;

/**
 * @author Levvy055
 *
 */
public class DBHandlerImpl implements DBHandler {

	private ConfigDB configDB;
	private SessionFactory factory;

	public DBHandlerImpl(ConfigDB configDB) {
		this.configDB = configDB;
	}

	public void initConnection() throws HibernateException {
		if (factory == null || factory.isClosed()) {
			String url = configDB.get(ConfigParams.DB_HOST.toString()).getValue();
			String db = configDB.get(ConfigParams.DB_NAME.toString()).getValue();
			String user = configDB.get(ConfigParams.DB_USERNAME.toString()).getValue();
			String passwd = configDB.get(ConfigParams.DB_PASSWORD.toString()).getValue();

			Configuration configuration = new Configuration().configure();
			configuration.setProperty("hibernate.connection.url", url + "/" + db);
			configuration.setProperty("hibernate.connection.username", user);
			configuration.setProperty("hibernate.connection.password", passwd);

			Properties properties = configuration.getProperties();
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(properties).build();

			MetadataSources metadata = new MetadataSources(serviceRegistry);
			metadata.addAnnotatedClass(RaspPi.class);
			metadata.addAnnotatedClass(Atmega.class);
			metadata.addAnnotatedClass(Register.class);

			factory = metadata.buildMetadata().buildSessionFactory();
		}
	}

	public void closeConnection() {

		if (factory != null) {
			Session session;
			try {
				if ((session = factory.getCurrentSession()) != null && session.isConnected()) {
					session.disconnect();
					session.close();
				}
			}
			catch (Exception e) {}
			factory.close();
		}

	}

	public static void closeSession(Session session) {
		if (session != null && (session.isConnected() || session.isOpen())) {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<RaspPi> getRaspPiDevices() {
		List<RaspPi> devs = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			devs = session.createQuery("FROM RaspPi").list();
			tx.commit();
		}
		catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			ARCLogger.error(e);
		}
		finally {
			closeSession(session);
		}
		return devs;
	}

	@SuppressWarnings("unchecked")
	public List<Atmega> getAtmegaDevices(int raspPiId) {
		List<Atmega> devs = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			devs = session.createQuery("FROM Atmega atmega WHERE atmega.raspPi = " + raspPiId).list();
			tx.commit();
		}
		catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			ARCLogger.error(e);
		}
		finally {
			closeSession(session);
		}
		return devs;
	}

	@SuppressWarnings("unchecked")
	public List<Register> getAllRegistry() {
		List<Register> devs = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			devs = session.createQuery("FROM Register").list();
			tx.commit();
		}
		catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			ARCLogger.error(e);
		}
		finally {
			closeSession(session);
		}
		return devs;
	}
}
